package com.m4rc310.rcp.statusbar.toolcontrols;

import javax.annotation.PostConstruct;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class Status1ToolControl {
	
	private Label label;

	@PostConstruct
	public void createGui(Composite composite) {
		Composite parent = new Composite(composite, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		parent.setLayout(layout);
		
		this.label = new Label(parent, SWT.NONE);
		label.setText("Status Bar");
		
		GridData gd = new GridData();
		gd.widthHint = 150;
		label.setLayoutData(gd);
	}
	
//	@Inject
//	@Optional
//	public void receiveEvent(@EventTopic("status_text") String refs, final UISynchronize sync) {
////		sync.asyncExec(()->{
////			try {
////				label.setText(refs);
////			} catch (Exception e) {
////				// TODO: handle exception
////			}
////			
////		});
//
//		Job job = Job.create("Carregando relatório...", (ICoreRunnable) monitor -> {
//			sync.asyncExec(() -> {
//				label.setText(refs);
////				IReportViewer view = viewReportComposite.getReportViewer();
////				JR.loadReport(view, refs.getName(), refs.getParams(), refs.getValues());
////				view.setZoomMode(IReportViewer.ZOOM_MODE_FIT_WIDTH);
//			});
//		});
////
//		job.schedule();
//	}
	
}