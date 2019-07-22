 
package com.m4rc310.rcp.rest.handlers;

import java.io.File;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.internal.events.EventBroker;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import com.m4rc310.rcp.rest.Const;

@SuppressWarnings("restriction")
public class TestReportCompilationHandler {
	@Execute
	public void execute(EventBroker eventBroker, Shell shell) {
		
		try {
			URL bundle = Platform.getBundle("com.m4rc310.rcp.rest").getResource("mls");
			String path = FileLocator.toFileURL(bundle).getFile();
			
			File file = new File(path);

			
			MessageDialog.openInformation(shell, "Teste", file.exists()+"");
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		
		
		eventBroker.send(Const.CLEAR_REPORTS, "com.m4rc310.rcp.rest");
		eventBroker.send(Const.COMPILE_REPORTS, "com.m4rc310.rcp.rest");
	}
		
}