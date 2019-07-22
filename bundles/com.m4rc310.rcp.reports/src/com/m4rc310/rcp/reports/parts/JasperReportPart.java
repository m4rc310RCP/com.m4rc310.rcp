package com.m4rc310.rcp.reports.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.runtime.ICoreRunnable;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.EventTopic;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.jasperassistant.designer.viewer.IReportViewer;
import com.jasperassistant.designer.viewer.ViewerComposite;
import com.m4rc310.rcp.reports.R;

import reports.utils.JR;
import reports.utils.ReportReferences;

@SuppressWarnings("restriction")
public class JasperReportPart {
	private ViewerComposite viewReportComposite;

	@Inject
	UISynchronize sync;

	@Inject
	public JasperReportPart() {

	}

	@PostConstruct
	public void postConstruct(Composite parent_, MPart part) {
		final Composite parent = new Composite(parent_, SWT.NONE);
		final GridLayout layout = new GridLayout();
		parent.setLayout(layout);

		this.viewReportComposite = new ViewerComposite(parent, SWT.NONE);
		viewReportComposite.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
	}

	@Inject
	@Optional
	public void receiveEvent(@EventTopic(JR.LOAD_JASPER_INFO) ReportReferences refs) {

		Job job = Job.create("Carregando relatório...", (ICoreRunnable) monitor -> {
			
			monitor.beginTask("...", 2);
			monitor.worked(1);
			
			sync.asyncExec(() -> {
				IReportViewer view = viewReportComposite.getReportViewer();
				R.loadReport(view, refs.getName(), refs.getParams(), refs.getValues());
				view.setZoomMode(IReportViewer.ZOOM_MODE_FIT_WIDTH);
			});
			
			monitor.worked(2);
			monitor.done();
		});

		job.schedule();
	}

}