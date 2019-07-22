package com.m4rc310.rcp.statusbar.services;

import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.swt.graphics.Image;

@Creatable
@Singleton
public class StatusImpl  {

//	@Inject EventBroker eventBroker;
	
	public void info(String text) {
//			eventBroker.send("status_text", text);
	}

	public void info(Image icon, String text) {
		// TODO Auto-generated method stub
		
	}

}
