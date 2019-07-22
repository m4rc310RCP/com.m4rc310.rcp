package com.m4rc310.rcp.reports;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

import com.jasperassistant.designer.viewer.IReportViewer;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRRewindableDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import reports.utils.Ref;
import reports.utils.ReportInfo;

@SuppressWarnings("restriction")
public class R {

	static {

	}

	private final String THIS_PLUGIN_ID = "com.m4rc310.rcp.reports";
	private final String KEY_INFO_REPORTS = "key.info.reports";
	private static final String REPORT_PATH = "reports";
	
	public static final String LOAD_JASPER_INFO = "load_jasper_info";

	@Inject
	@Preference(nodePath = THIS_PLUGIN_ID)
	IEclipsePreferences prefs;

	@Inject @Optional Shell sheel;
	
	
	@Inject
	MReport report;
	
//	@Inject EventBroker eventBroker;

	private static R get() {
		Bundle bundler = FrameworkUtil.getBundle(Activator.class);
		BundleContext btx = bundler.getBundleContext();

		IEclipseContext context = EclipseContextFactory.getServiceContext(btx);
		return ContextInjectionFactory.make(R.class, context);
	}

	public static JasperReport getReport(String name) {
		return get().getReport_(name);
	}

	public static JRRewindableDataSource getJRBeanDataSource(Collection<?> list) {
		try {
			return new JRBeanCollectionDataSource(list);
		} catch (Exception e) {
			return new JREmptyDataSource();
		}
	}
	
	public static void compileReports(String pluginID) {
		compileReports(pluginID, REPORT_PATH);
	}
	
	public static void clear(String pluginID) {
		get().clear_(pluginID);
	}
	
	public void clear_(String pluginID) {
		report.clear(pluginID);
	}

	public static void compileReports(String pluginID, String dirName) {
		get().compileReports_(pluginID, dirName);
	}
	
	
	public static void loadReport(IReportViewer view, String name, Map<String, Object> params, List<?> values) {
		JasperReport report = getReport(name);
		JasperPrint print = getJasperPrint(report, params, values);
		view.setDocument(print);
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
	

	private  void compileReports_(String pluginID, String dirName) {
		report.compileReports(pluginID, dirName);
	}

	
	private JasperReport getReport_(String name) {
		try {

			MessageDialog.openInformation(sheel, "TESTE", name);
			
			
			Ref ref = getRef(name);
			
			File file = new File(ref.getFileJasper());
			
			if(!file.exists()) {
				report.recompileReports(name);
				ref = getRef(name);
				file = new File(ref.getFileJasper());
			}
			
			return (JasperReport) JRLoader.loadObject(file);

		} catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}

//		try {
//			return (JasperReport) JRLoader.loadObject(new File(ref.getFileJasper()));
//		} catch (Exception e) {
//			
//			report.recompileReports(name);
//			
//			File file = new File(ref.getFileJasper());
//			if(file.exists()) {
//				try {
//					return (JasperReport) JRLoader.loadObject(file);
//				} catch (Exception e2) {
//				}
//			}
//			
//		}
	}
	
	
	

	private Ref getRef(String name) {
		ReportInfo info = getReportInfo();
		
		
		
		for (Ref ref : info.getReports()) {
			if (ref.getName().equalsIgnoreCase(name)) {
				return ref;
			}
		}

		String err = String.format("Report '%s' not found.", name);
		throw new UnsupportedOperationException(err);
	}

	private ReportInfo getReportInfo() {
		XStream stream = new XStream(new DomDriver());
		String infoXML = prefs.get(KEY_INFO_REPORTS, "<reports.utils.ReportInfo><reports></reports></reports.utils.ReportInfo>");
		
		System.out.println(infoXML);
		
		return (ReportInfo) stream.fromXML(infoXML);
	}

//	public static JasperReport getReport(String reportName) {
//		R r = get();
//		
//	}
//	

}
