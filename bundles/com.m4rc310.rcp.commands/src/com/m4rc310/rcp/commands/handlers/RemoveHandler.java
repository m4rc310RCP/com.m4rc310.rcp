 
package com.m4rc310.rcp.commands.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.CanExecute;

public class RemoveHandler {
	@Execute
	public void execute() {
		
	}
	
	
	@CanExecute
	public boolean canExecute() {
		return false;
	}
		
}