
package com.m4rc310.rcp.rest.handlers;

import javax.inject.Inject;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.ui.services.internal.events.EventBroker;

import com.m4rc310.rcp.rest.Const;

@SuppressWarnings("restriction")
public class UpdateInfoHandler {
	
	@Inject
	@Preference(nodePath = "com.m4rc310.rcp.rest")
	IEclipsePreferences prefs;
	
	
	
	@Execute
	public void execute(EventBroker eventBroker) {
		eventBroker.send(Const.LOAD_COMPANY_NAME, "COAMO AGROINDUSTRIAL COOPERATIVA");
	}

}