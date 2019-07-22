package com.m4rc310.rcp.reports.addons;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.EventTopic;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.osgi.service.event.Event;

@SuppressWarnings("restriction")
public class CompileReportsAddon {

//	@Inject
//	M shell;

	@Inject
	@Optional
	public void applicationStarted(@EventTopic(UIEvents.UILifeCycle.APP_STARTUP_COMPLETE) Event event) {
//		try {
//			URL bundle = Platform.getBundle("com.m4rc310.reports").getResource("mreports");
//			String url = String.format("%s", FileLocator.toFileURL(bundle).getFile(), "bd");
//		
//			ReportUtils.compileReports(url);
//		
//		} catch (Exception e) {
//			Display.getDefault().asyncExec(new Runnable() {
//				@Override
//				public void run() {
//					Shell shell = new Shell();
//					MessageDialog.openInformation(shell, "Error", e.getMessage());
//				}
//			});
//		}
	}

}
