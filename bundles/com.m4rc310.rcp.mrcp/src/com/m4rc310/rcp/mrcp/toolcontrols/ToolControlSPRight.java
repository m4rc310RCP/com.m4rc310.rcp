package com.m4rc310.rcp.mrcp.toolcontrols;


import javax.annotation.PostConstruct;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
//import org.eclipse.swt.layout.GridData;
//import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;


public class ToolControlSPRight {

	@PostConstruct
	public void createGui(Composite _parent) {
		final Composite parent = new Composite(_parent, SWT.NONE);
		GridLayout gl_parent = new GridLayout();
		gl_parent.verticalSpacing = 0;
		gl_parent.marginWidth = 0;
		gl_parent.marginHeight = 0;
		gl_parent.horizontalSpacing = 0;
		parent.setLayout(gl_parent);
		GridData gd = new GridData();
		gd.heightHint = 5;
		gd.widthHint = 4;
		gd.horizontalIndent = 0;
		gd.horizontalSpan = 0;
		
		new Label(parent, SWT.NONE).setLayoutData(gd);
	}
}