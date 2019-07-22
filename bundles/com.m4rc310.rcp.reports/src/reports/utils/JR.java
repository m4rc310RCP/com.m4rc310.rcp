package reports.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;

import com.jasperassistant.designer.viewer.IReportViewer;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;



public class JR {

	public static final String LOAD_JASPER_INFO = "load_jasper_info";

	private static final String PLUGIN_ID = "com.m4rc310.rcp.reports";
	private static final File pathLocal;
	private static final File pathInfoXml;

	private static List<Ref> reports = new ArrayList<Ref>();

	static {

		try {
			URL bundle = Platform.getBundle(PLUGIN_ID).getResource("creports");
			pathLocal = new File(FileLocator.toFileURL(bundle).getFile());
			pathInfoXml = new File(String.format("%s/report.xml", FileLocator.toFileURL(bundle).getFile()));
			if (pathInfoXml.exists()) {
				ReportInfo info = (ReportInfo) new XStream(new DomDriver()).fromXML(pathInfoXml);
				reports = info.getReports();
				reports = reports == null ? new ArrayList<Ref>() : reports;
			} else {
				store();
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new UnsupportedOperationException(e.getMessage());
		}
	}

	public static void prepareJR(String pluginId, String path) {
		try {
			URL bundle = Platform.getBundle(pluginId).getResource(path);
			File file = new File(FileLocator.toFileURL(bundle).getFile());

			ArrayList<File> files = new ArrayList<File>();

			if (file.exists()) {
				listf(file.getAbsolutePath(), files);
			}

			for (File f : files) {
				String name = f.getName().replaceAll(".jrxml", "");
				String md5 = ReportMD5Utils.getHashMD5(f);

				if (compile(name, md5)) {
					File res = compile(f);
					Ref ref = new Ref(name, md5, f.getAbsolutePath(), res.getAbsolutePath());
					ref.setPluginId(pluginId);
					put(ref);
				}
			}

			store();

			clear(files, pluginId);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void clear(ArrayList<File> files, String pluginId) {

		List<File> blackList = new ArrayList<>();
		for (File file : files) {
			boolean contains = false;
			for (Ref ref : reports) {
				if (!ref.getPluginId().equalsIgnoreCase(pluginId)) {
					continue;
				}

				if (ref.getFileJrxml().equalsIgnoreCase(file.getAbsolutePath())) {
					contains = true;
					break;
				}
			}
			
			if(!contains) {
				blackList.add(file);
			}
		}
		
		clear(blackList);
	}

	private static void clear(List<File> blackList) {
		
		for (File file : blackList) {
			System.out.println("Remove: " + file);
		}
		
		
		
//		for (String path : blackList) {
//			File file = new File(path);
//			file.delete();
//		}
	}

	private static JasperPrint getJasperPrint(JasperReport report, Map<String, Object> params, List<?> values) {
		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(report, params,
					new JRBeanCollectionDataSource(values));
			return jasperPrint;
		} catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	private static ReportInfo load() {

		ReportInfo info;
		if (!pathInfoXml.exists()) {
			info = new ReportInfo();
		} else {
			info = (ReportInfo) new XStream(new DomDriver()).fromXML(pathInfoXml);
		}
		info.setReports(reports);

		return info;
	}

	private static void store() {
		ReportInfo info = load();
		try (Writer writer = new FileWriter(pathInfoXml)) {
			new XStream(new DomDriver()).toXML(info, writer);
		} catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	private static boolean compile(String name, String md5) {
		for (Ref r : reports) {
			if (r.getMd5().equals(md5)) {
				return false;
			}
		}

		Ref similar = getRef(reports, name);
		if (similar == null) {
			return true;
		}
		return true;
	}

	public static void put(Ref ref) {
		Ref similar = getRef(reports, ref.getName());

		if (similar != null) {
			if (!similar.getFileJrxml().equalsIgnoreCase(ref.getFileJrxml())) {
				String message = String.format("Relatório {0} está em duplicidade!", ref.getName());
				throw new UnsupportedOperationException(message);
			}
			int index = reports.indexOf(similar);
			reports.set(index, ref);
		} else {
			reports.add(ref);
		}
	}

	public static void loadReport(IReportViewer view, String name, Map<String, Object> params, List<?> values) {
		JasperReport report = getReport(name);
		JasperPrint print = getJasperPrint(report, params, values);
		view.setDocument(print);
	}

	public static JasperPrint getJasperPrint(String name, Map<String, Object> params, List<?> values) {
		JasperReport report = getReport(name);
		JasperPrint print = getJasperPrint(report, params, values);
		return print;
	}

	public static JasperReport getReport(String reportName) {
		try {
			ReportInfo info = load();
			reports = info.getReports();

			Ref ref = getRef(reports, reportName);
			if (ref == null) {
				String message = String.format("Report '%s' not found!", reportName);
				throw new Exception(message);
			}

			return (JasperReport) JRLoader.loadObject(new File(ref.getFileJasper()));
		} catch (Exception e) {
			throw new UnsupportedOperationException(e.getMessage());
		}
	}

	private static Ref getRef(List<Ref> reports, String name) {
		for (Ref ref : reports) {
			if (ref.getName().equalsIgnoreCase(name)) {
				return ref;
			}
		}
		return null;
	}

	private static File compile(File file) {
		try {
			JasperDesign design = JRXmlLoader.load(file);
			String dest = String.format("%s/%s.jasper", pathLocal.getAbsolutePath(),
					file.getName().replaceAll(".jrxml", ""));
			JasperCompileManager.compileReportToFile(design, dest);
			return new File(dest);
		} catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

//	private static void clean()

	public static void listf(String directoryName, ArrayList<File> files) {
		File directory = new File(directoryName);
		File[] fList = directory.listFiles();
		for (File file : fList) {
			if (file.isDirectory()) {
				listf(file.getAbsolutePath(), files);
			} else if (file.getAbsolutePath().endsWith(".jasper")) {
			} else if (file.getAbsolutePath().endsWith(".jrxml")) {
				files.add(file);
			}
		}
	}

}
