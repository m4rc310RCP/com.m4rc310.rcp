 
package com.m4rc310.rcp.commands.handlers;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import com.m4rc310.rcp.commands.i18n.Messages;


public class QuitHandler {
	@Inject @Translation
	Messages m;
	
	@Execute
	public void execute(IWorkbench workbench, Shell shell){
		if (MessageDialog.openConfirm(shell, m.title_dialog_confirm,
				m.questio_exit_this)) {
			workbench.close();
		}
	}
	
	
	@CanExecute
	public boolean canExecute() {
		return true;
	}
		
}