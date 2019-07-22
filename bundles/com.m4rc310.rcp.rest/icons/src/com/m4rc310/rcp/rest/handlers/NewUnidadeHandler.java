 
package com.m4rc310.rcp.rest.handlers;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

import com.m4rc310.rcp.rest.i18n.Messages;
import com.m4rc310.rcp.rest.wizards.unidade.WizardUnidade;
import com.m4rc310.rcp.rest.wizards.unidade.WizardUnidadePage1;
import com.m4rc310.rcp.rest.wizards.unidade.WizardUnidadePage2;

public class NewUnidadeHandler {
	
	@Execute
	public void execute(Shell shell,  IEclipseContext ctx,@Translation Messages m) {
		IEclipseContext wizardCtx = ctx.createChild();
		wizardCtx.set(Messages.class, m);
		
		WizardUnidadePage1 page1 = ContextInjectionFactory.make(WizardUnidadePage1.class, wizardCtx);
		wizardCtx.set(WizardUnidadePage1.class, page1);
		
		
		WizardUnidadePage2 page2 = ContextInjectionFactory.make(WizardUnidadePage2.class, wizardCtx);
		wizardCtx.set(WizardUnidadePage2.class, page2);
		
		
		WizardUnidade wizard = ContextInjectionFactory.make(WizardUnidade.class, wizardCtx);
		
		
		WizardDialog dialog = new WizardDialog(shell, wizard);
		if (dialog.open()== WizardDialog.OK) {
			System.out.println("-----");
		}
		
	}
	
	
	@CanExecute
	public boolean canExecute() {
		return true;
	}
		
}