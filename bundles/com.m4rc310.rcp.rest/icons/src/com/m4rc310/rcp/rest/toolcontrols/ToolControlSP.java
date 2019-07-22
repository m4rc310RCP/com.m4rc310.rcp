package com.m4rc310.rcp.rest.toolcontrols;


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
		new Label(parent, SWT.NONE).setLayoutData(gd);
	}
	
}