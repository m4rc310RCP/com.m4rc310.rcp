 
package com.m4rc310.rcp.commands.handlers;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.dialogs.MessageDialog;

import org.eclipse.swt.widgets.Shell;

public class AboutHandler {
	@Execute
	public void execute(Shell shell) {
		MessageDialog.openInformation(shell, "About", "CIPA ");
	}
	
	
	@CanExecute
	public boolean canExecute() {
		
		return true;
	}
		
}