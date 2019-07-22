package com.m4rc310.rcp.rest.wizards;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;

public abstract class CustomWizardPage extends WizardPage {

	protected CustomWizardPage(String pageName) {
		super(pageName);
	}

	protected CustomWizardPage(String pageName, String title, ImageDescriptor titleImage) {
		super(pageName, title, titleImage);
	}

	@Override
	public IWizardPage getNextPage() {

		boolean isNextPressed = "nextPressed".equalsIgnoreCase(Thread.currentThread().getStackTrace()[2].getMethodName());
		if (isNextPressed) {
            boolean validatedNextPress = this.nextPressed();
            if (!validatedNextPress) {
                return this;
            }
        }
		return super.getNextPage();
	}

    /**
     * @see WizardDialog#nextPressed()
     * @see WizardPage#getNextPage()
     */
    protected boolean nextPressed() {
        boolean validatedNextPressed = true;
        try {
        	System.out.println("-----");
        	
            //your checking here: remote connection, local directory/file creation, showing dialogs, etc... 
        } catch (Exception ex) {
            System.out.println("Error validation when pressing Next: " + ex);
        }
        return validatedNextPressed;
    }
		
}
