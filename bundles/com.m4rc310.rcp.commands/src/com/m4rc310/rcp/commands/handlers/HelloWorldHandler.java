package com.m4rc310.rcp.commands.handlers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;

import com.m4rc310.rcp.commands.PartControl;
import com.m4rc310.rcp.popup.services.IPopupService;
import com.m4rc310.rcp.reports.MReport;
import com.m4rc310.rcp.reports.R;

import reports.utils.ReportReferences;


//import com.m4rc310.rcp.notifications.services.IPopupNotification;
//import org.eclipse.e4.core.di.annotations.Execute;

/**
 * <b>Warning</b> : As explained in <a href=
 * "http://wiki.eclipse.org/Eclipse4/RCP/FAQ#Why_aren.27t_my_handler_fields_being_re-injected.3F">this
 * wiki page</a>, it is not recommended to define @Inject fields in a handler.
 * <br/>
 * <br/>
 * <b>Inject the values in the @Execute methods</b>
 */
public class HelloWorldHandler {
	

//	@Execute
//	public void execute(@Named(IServiceConstants.ACTIVE_SHELL) Shell s) {
	@Execute
	public void execute(MReport report, PartControl pc,IEventBroker eventBroker, IPopupService popup) {
		
		try {
			
//			URL bundle = Platform.getBundle("com.m4rc310.rcp.commands").getResource("reports");
//			
//			String path = FileLocator.toFileURL(bundle).getFile();
//			
//			System.out.println(path);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		
//		report.clearReportRegister();
		
//		report.compileReports("com.m4rc310.rcp.commands");
		
//		R.clear("com.m4rc310.rcp.commands");
		
//		System.out.println(R.getReport("PG2"));
		
		
		
		
		
		
////		Job sjob = Job.create("---->", runnable->{
////			Display.getDefault().asyncExec(new Runnable() {
////				@Override
////				public void run() {
////					runnable.beginTask("starting...", 20);
////					for (int i = 0; i < 20; i++) {
////						try {
////							Thread.sleep(2000);
////						} catch (Exception e) {
////						}
////						runnable.worked(i);
////					}
////				}});
////		});
//		
//		
////		sjob.schedule();
//		
//		ReportUtils.compileReports("com.m4rc310.rcp.commands", "reports");
//		Image imageDefault = ResourceManager.getPluginImage("com.m4rc310.rcp.commands","icons/16x16/others/comment_yellow.gif");
//		
//		Job job = Job.create("Loading...", runnable -> {
//			Display.getDefault().syncExec(new Runnable() {
//				@Override
//				public void run() {
//					
//					runnable.worked(2);
//					
					pc.show("com.m4rc310.rcp.reports.partdescriptor.default");
//
					List<String> values = new ArrayList<>();

					values.add("-----");
//					
//					report.compileReports("com.m4rc310.rcp.commands", "reports");
//					
//					runnable.done();
//					
////					notification.show("Teste de Notificação");
					ReportReferences rr = new ReportReferences("PG", null, values);
					eventBroker.send(R.LOAD_JASPER_INFO, rr);
//				}
//			});
//
//		});
//
//		job.schedule();
//		
//		popup.show(imageDefault,"Teste de Popup Notifications", "Esse é um teste rápido de funcionalidade prática",100);
		
	}

}
