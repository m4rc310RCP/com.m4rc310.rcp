package com.m4rc310.rcp.mrcp.toolcontrols;


import javax.annotation.PostConstruct;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
//import org.eclipse.swt.layout.GridData;
//import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;


public class ToolControlSP {

	@PostConstruct
	public void createGui(Composite _parent) {
		final Composite parent = new Composite(_parent, SWT.NONE);
		parent.setLayout(new GridLayout());
		GridData gd = new GridData();
		gd.heightHint = 22;
//		parent.setLayoutData(gd);
		
		new Label(parent, SWT.NONE).setLayoutData(gd);
		
	}
	
//	@Inject
//	@Optional
//	public void receiveEvent(@EventTopic("LOAD_USER_NAME") String username) {
//		text.setText(username);
//		((GridData)text.getLayoutData()).widthHint = username.isEmpty()?1:80;
//	}
	
}