 
package com.m4rc310.rcp.commands.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

import java.util.Collection;

import org.eclipse.e4.core.contexts.Active;
import org.eclipse.e4.core.di.annotations.CanExecute;

public class SaveHandler {
	@CanExecute
	public boolean canExecute(EPartService partService) {
		MPart part = partService.getActivePart();
		if ((part != null) && (partService != null)) {
			Collection<MPart> parts = partService.getDirtyParts();
			return parts.contains(part);
		}
		return false;
	}

	@Execute
	public void execute(EPartService partService, @Active MPart part) {
		partService.savePart(part, false);
	}	
}