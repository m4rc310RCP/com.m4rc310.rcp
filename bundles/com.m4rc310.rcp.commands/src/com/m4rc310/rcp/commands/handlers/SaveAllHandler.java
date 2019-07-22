 
package com.m4rc310.rcp.commands.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.core.di.annotations.CanExecute;

public class SaveAllHandler {
	@Execute
	public void execute(EPartService service, IEventBroker broker) {
		service.saveAll(false);
//		broker.post(MyEventConstants.TOPIC_TODO_UPDATE, "saved");
	}

	
	@CanExecute
	boolean canExecute(@Optional EPartService partService) {
		if (partService != null) {
			return !partService.getDirtyParts().isEmpty();
		}
		return false;
	}
		
}