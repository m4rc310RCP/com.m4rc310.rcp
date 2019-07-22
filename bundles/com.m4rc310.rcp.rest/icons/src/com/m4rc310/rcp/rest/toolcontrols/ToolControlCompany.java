package com.m4rc310.rcp.rest.toolcontrols;


import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.EventTopic;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
//import org.eclipse.swt.layout.GridData;
//import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

import com.m4rc310.rcp.rest.Const;


@SuppressWarnings("restriction")
public class ToolControlCompany {

	private Label labelCompanyName;
	private Label labelAditionalInformation;

	@Inject
	UISynchronize sync;
	private Composite parent;
	
	
	@PostConstruct
	public void createGui(Composite _parent) {
		this.parent = new Composite(_parent, SWT.NONE);
		GridLayout gl_parent = new GridLayout(1,false);
		gl_parent.verticalSpacing = 0;
		gl_parent.marginWidth = 0;
		gl_parent.marginHeight = 0;
		
		parent.setLayout(gl_parent);
		
		this.labelCompanyName = new Label(parent, SWT.None);
		labelCompanyName.setAlignment(SWT.RIGHT);
		labelCompanyName.setForeground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_FOREGROUND));
		labelCompanyName.setFont(SWTResourceManager.getFont(".AppleSystemUIFont", 11, SWT.BOLD));
		labelCompanyName.setText("");

		this.labelAditionalInformation = new Label(parent, SWT.RIGHT);
		labelAditionalInformation.setAlignment(SWT.RIGHT);
		labelAditionalInformation.setFont(SWTResourceManager.getFont(".AppleSystemUIFont", 8, SWT.NORMAL));
		labelAditionalInformation.setText("User: mlsilva");
		
		GridData gd = new GridData();
		gd.widthHint = 270;
		labelCompanyName.setLayoutData(gd);
	}
	
	@Inject
	@Optional
	private void updateStatusNormal(@EventTopic(Const.LOAD_COMPANY_NAME) String status) {
		
		sync.asyncExec(() -> {
			try {
				labelCompanyName.setText(status);
				parent.layout();
			} catch (Exception e) {
			}
			
		});
	}
	
	
}