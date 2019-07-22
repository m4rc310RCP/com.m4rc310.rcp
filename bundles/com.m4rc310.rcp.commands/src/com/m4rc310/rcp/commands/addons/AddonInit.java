package com.m4rc310.rcp.commands.addons;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.EventTopic;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.osgi.service.event.Event;

import com.m4rc310.rcp.reports.R;

import org.eclipse.e4.core.services.events.IEventBroker;

@SuppressWarnings({ "unused", "restriction" })
public class AddonInit {

	@Inject
	@Optional
	public void applicationStarted(
			@EventTopic(UIEvents.UILifeCycle.APP_STARTUP_COMPLETE) Event event) {
		
		R.compileReports("com.m4rc310.rcp.commands");
		
//		MessageDialog.openInformation(shell, "About", "compile");
		
	}

}
