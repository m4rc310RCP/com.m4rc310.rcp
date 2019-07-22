package com.m4rc310.rcp.rest.addons;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.EventTopic;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.osgi.service.event.Event;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.m4rc310.rcp.rest.Const;

@SuppressWarnings("restriction")
public class AddonInitConnection {
	
	@Inject
	@Preference(nodePath = "com.m4rc310.rcp.rest")
	IEclipsePreferences prefs;
	
//	@Inject
//	MReport report;

	@Inject
	@Optional
	public void applicationStarted(
			@EventTopic(UIEvents.UILifeCycle.APP_STARTUP_COMPLETE) Event event, IEventBroker eventBroker) {
		
		eventBroker.send(Const.TRY_CONNECTION, "");
		
		applicationStarted(eventBroker);
		
		eventBroker.send(Const.COMPILE_REPORTS, "com.m4rc310.rcp.rest");
		
	}
	
	public void applicationStarted(IEventBroker eventBroker) {

		try {
			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					try {
						
						String url = prefs.get("server_url", Const.HOST_HIROKU);
						if (!url.isEmpty()) {
							RestTemplate restTemplate = new RestTemplate();
							url = String.format("%s/%s", url, "api/up");
							ResponseEntity<String> res = restTemplate.getForEntity(url, String.class);
							
							eventBroker.send(Const.CONNECTION_SUCCESS, res.getBody());
							
							Thread.sleep(5000);

							eventBroker.send(Const.CONNECTION_NORMAL, "");
							
							
						}
					} catch (Exception e) {
						eventBroker.send(Const.CONNECTION_NO_SUCCESS, "");
//						MessageDialog.openError(shell, m.titleError, m.messageErrorConnection);
					}

					System.out.println(MessageFormat.format("{0}", new Date()));
				}
			};

			Timer timer = new Timer();
			timer.schedule(task, 0, 900000);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	

}
