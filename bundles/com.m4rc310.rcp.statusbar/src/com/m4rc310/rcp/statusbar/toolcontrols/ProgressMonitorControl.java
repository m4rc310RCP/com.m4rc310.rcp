package com.m4rc310.rcp.statusbar.toolcontrols;

import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.core.runtime.jobs.ProgressProvider;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;


public class ProgressMonitorControl {

	private final UISynchronize sync;

	private ProgressBar progressBar;
	private GobalProgressMonitor monitor;

	private Label labelInfo;

	@Inject
	public ProgressMonitorControl(UISynchronize sync) {
		this.sync = Objects.requireNonNull(sync);
	}
	

	@PostConstruct
	public void createControls(Composite parent_) {
		Composite parent = new Composite(parent_, SWT.NONE);

		GridLayout layout = new GridLayout(2, false);
		parent.setLayout(layout);
		
		
		this.labelInfo = new Label(parent, SWT.NONE);
		labelInfo.setVisible(false);
		GridData gd = new GridData();
		gd.widthHint = 10;

		labelInfo.setLayoutData(gd);
		
		
		progressBar = new ProgressBar(parent, SWT.SMOOTH);
		progressBar.setBounds(100, 10, 50, 20);
		progressBar.setVisible(false);
		
		gd = new GridData();
		gd.widthHint = 50;
		
		progressBar.setLayoutData(gd);
		
		
		monitor = new GobalProgressMonitor();

		Job.getJobManager().setProgressProvider(new ProgressProvider() {
			@Override
			public IProgressMonitor createMonitor(Job job) {
				return monitor.addJob(job);
			}
		});
	}

	private void setText(String text) {
		sync.asyncExec(()->{
			labelInfo.setText(text);
		});
	}
	
	
	
	private final class GobalProgressMonitor extends NullProgressMonitor {

		// thread-Safe via thread confinement of the UI-Thread
		// (means access only via UI-Thread)
		private long runningTasks = 0L;

		@Override
		public void beginTask(final String name, final int totalWork) {
			sync.syncExec(new Runnable() {

				@Override
				public void run() {
					
					progressBar.setVisible(true);
//					labelInfo.setVisible(true);
					
					if (runningTasks <= 0) {
						// --- no task is running at the moment ---
						progressBar.setSelection(0);
						progressBar.setMaximum(totalWork);
					} else {
						// --- other tasks are running ---
						progressBar.setMaximum(progressBar.getMaximum() + totalWork);
					}

					runningTasks++;
					progressBar.setToolTipText("Currently running: " + runningTasks + "\nLast task: " + name);
					
				}
			});
		}

		@Override
		public void worked(final int work) {
			sync.syncExec(new Runnable() {
				@Override
				public void run() {
					progressBar.setSelection(progressBar.getSelection() + work);
				}
			});
		}
		
		@Override
		public void done() {
			super.done();
			
			try {
				Thread.sleep(1000);
				sync.syncExec(()->{
					progressBar.setVisible(false);
					labelInfo.setVisible(false);
				});
			} catch (Exception e) {
				// TODO: handle exception
			}

		}
		

		public IProgressMonitor addJob(Job job) {
			if (job != null) {
				job.addJobChangeListener(new JobChangeAdapter() {
					@Override
					public void done(IJobChangeEvent event) {
						sync.syncExec(new Runnable() {

							@Override
							public void run() {
								runningTasks--;
								if (runningTasks > 0) {
									// --- some tasks are still running ---
									progressBar.setToolTipText("Currently running: " + runningTasks);
									setText(progressBar.getToolTipText());
								} else {
									// --- all tasks are done (a reset of selection could also be done) ---
									progressBar.setToolTipText("Currently no background progress running.");
									setText(progressBar.getToolTipText());
								}
							}
						});

						// clean-up
						event.getJob().removeJobChangeListener(this);
					}
				});
			}
			return this;
		}
	}
}