 
package com.m4rc310.rcp.preferences;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.EventTopic;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.osgi.service.event.Event;

//import org.eclipse.e4.core.services.events.IEventBroker;

@SuppressWarnings("restriction")
public class E4PreferencesAddon {
	
	@Inject
	@Optional
	public void applicationStarted(
			@EventTopic(UIEvents.UILifeCycle.APP_STARTUP_COMPLETE) Event event, IEclipseContext ctx) {
		
	}

}
