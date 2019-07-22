package com.m4rc310.rcp.reports;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.EventTopic;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.osgi.util.NLS;

import com.m4rc310.rcp.reports.i18n.Messages;
import com.m4rc310.rcp.statusbar.services.StatusImpl;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import reports.utils.Ref;
import reports.utils.ReportInfo;
import reports.utils.ReportMD5Utils;

@Creatable
@Singleton
@SuppressWarnings("restriction")
public class MReport {

	@Inject
	private StatusImpl status;

	@Inject
	@Translation
	Messages m;

	private final String THIS_PLUGIN_ID = "com.m4rc310.rcp.reports";

	@Inject
	@Preference(nodePath = THIS_PLUGIN_ID)
	IEclipsePreferences prefs;
	

	private final String REPORT_PATH = "reports";

	private final String KEY_INFO_REPORTS = "key.info.reports";

	private boolean forceCompile = false;

//	private String pluginTarget;

	@Inject
	public MReport() {

	}

	public void clearReportRegister() {
		try {
			prefs.remove(KEY_INFO_REPORTS);
			prefs.flush();
		} catch (Exception e) {
		}
	}

	public void recompileReports(String name) {
		XStream stream = new XStream(new DomDriver());
		String infoXML = prefs.get(KEY_INFO_REPORTS, "<reports.utils.ReportInfo><reports></reports></reports.utils.ReportInfo>");
		ReportInfo info = (ReportInfo) stream.fromXML(infoXML);
		for (Ref ref : info.getReports()) {
			if (ref.getName().equals(name)) {
				forceCompile = true;
				compileReports(ref.getPluginId(), REPORT_PATH);
			}
		}
	}

	public void compileReports(String pluginID) {
		compileReports(pluginID, REPORT_PATH);
	}

	public void compileReports(String pluginID, String dirName) {

		status.info("initing plugin compiler report...");

		try {
			
			XStream stream = new XStream(new DomDriver());
			String infoXML = prefs.get(KEY_INFO_REPORTS, "<reports.utils.ReportInfo><reports></reports></reports.utils.ReportInfo>");

			ReportInfo info;

			if (infoXML.isEmpty()) {
				info = new ReportInfo();
				info.setReports(new ArrayList<Ref>());

				infoXML = stream.toXML(info);
			} else {
				info = (ReportInfo) stream.fromXML(infoXML);
			}

//			err = NLS.bind(m.error_dir_not_found, dirName, pluginID);

			URL bundle = Platform.getBundle(pluginID).getResource(dirName);
			String path = FileLocator.toFileURL(bundle).getFile();
			ArrayList<File> files = new ArrayList<File>();
			listf(path, files);

			files.stream().filter((file) -> (file.getName().endsWith(".jrxml"))).forEachOrdered((file) -> {

				
				boolean compile = true;

				String md5 = ReportMD5Utils.getHashMD5(file);
				String name = file.getName().replace(".jrxml", "");

				List<Ref> reports = info.getReports();

				for (Ref ref : reports) {
					if (ref.getMd5().equals(md5) && !forceCompile) {
						compile = false;
					}

					if (ref.getName().equals(name) && !ref.getPluginId().equals(pluginID)) {
						String err_ = NLS.bind(m.error_equals_report_name_other_plugin, name, ref.getPluginId());
						throw new UnsupportedOperationException(err_);
					}
				}

				if (compile) {
					File dfile = compile(file);
					Ref ref = null;

					int i = -1;

					for (Ref ref_ : reports) {
						if (ref_.getName().equals(name)) {
							i = reports.indexOf(ref_);
							ref = ref_;
							break;
						}
					}

					ref = ref == null ? new Ref(name, md5, file.getAbsolutePath(), dfile.getAbsolutePath()) : ref;
					ref.setPluginId(pluginID);

					if (i < 0) {
						reports.add(ref);
					} else {
						ref.setMd5(md5);
						reports.set(i, ref);
					}

					try {
						String xml = stream.toXML(info);
						prefs.put(KEY_INFO_REPORTS, xml);
						prefs.flush();
						prefs.sync();
					} catch (Exception e) {
						throw new UnsupportedOperationException(e);
					}

				}

			});
			
						

		} catch (Exception e) {
//			MessageDialog.openInformation(shell, m.title_error, err);
		}

	}
	
	
	
	@Inject
	@Optional
	public void clear(@EventTopic("clear_reports") String pluginId) {
		try {
			
			
			clearReportRegister();
			
			
			URL bundle = Platform.getBundle(pluginId).getResource(REPORT_PATH);
			String path = FileLocator.toFileURL(bundle).getFile();
			
			
			List<File> files = new ArrayList<File>();
			
			listf(path, files);
			

			XStream stream = new XStream(new DomDriver());
			String infoXML = prefs.get(KEY_INFO_REPORTS, "<reports.utils.ReportInfo><reports></reports></reports.utils.ReportInfo>");
			
			ReportInfo info = (ReportInfo) stream.fromXML(infoXML);
			Iterator<Ref> itrs = info.getReports().iterator();
			
			while (itrs.hasNext()) {
				Ref ref = itrs.next();
				
				for (File file : files) {
					String name = file.getName().replace(".jrxml", "");
					if(name.equals(ref.getName())) {
						itrs.remove();
					}
				}
			}
			
			while (itrs.hasNext()) {
				Ref ref = itrs.next();
				System.out.println(ref.getName());
			}
		} catch (Exception e) {
		}
	}
	

	@Inject
	@Optional
	public void compile(@EventTopic("compile_reports") String pluginId) {
		compileReports(pluginId);
		
		
		
	}
	
	
	
	

	private File compile(File file) {
		try {
			JasperDesign design = JRXmlLoader.load(file);
			URL bundle = Platform.getBundle(THIS_PLUGIN_ID).getResource(REPORT_PATH);

			File destPath = new File(FileLocator.toFileURL(bundle).getFile());
			

			String dest = String.format("%s/%s.jasper", destPath.getAbsolutePath(),
					file.getName().replaceAll(".jrxml", ""));
			JasperCompileManager.compileReportToFile(design, dest);

			return new File(dest);

		} catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}

	}

	

	public static void listf(String directoryName, List<File> files) {
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
