package com.m4rc310.rcp.rest.wizards;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.jface.wizard.Wizard;

@Creatable
public class CreationWizard extends Wizard {

	public static final String NEW_UNIDADE = "new_unidade";
	
	public CreationWizard() {
		super();
		setNeedsProgressMonitor(true);
	}
	
	@Override
	public boolean performFinish() {
		return false;
	}

}
