 
package com.m4rc310.rcp.rest.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuItem;

import com.m4rc310.rcp.commands.PartControl;

public class ShowStatusServerHandler {
	@Execute
	public void execute(PartControl control,MMenuItem item) {
		control.visible("com.m4rc310.rcp.rest.part.server.status", item.isSelected());
	}
		
}