package com.m4rc310.rcp.rest.wizards.unidade;

import javax.inject.Inject;

import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.services.internal.events.EventBroker;
import org.eclipse.jface.wizard.Wizard;

import com.m4rc310.rcp.rest.i18n.Messages;

//@Creatable
@SuppressWarnings("restriction")
public class WizardUnidade extends Wizard {

	@Inject @Translation
	Messages m;

	@Inject WizardUnidadePage1 page1;
	@Inject WizardUnidadePage2 page2;
	
	@Inject EventBroker eventBroker;
	
	@Inject
	public WizardUnidade() {
		setNeedsProgressMonitor(true);
	}
	
	@Override
	public void addPages() {
		super.addPages();
		addPage(page1);
		addPage(page2);
	}
	
	
	@Override
	public boolean performFinish() {
		return page1.isPageComplete() && page2.isPageComplete();
	}
}
