package com.m4rc310.rcp.rest.dialogs;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.springframework.web.client.RestTemplate;

public class Experiments {

	public static void main(String[] args) {
		Display display = Display.getDefault();
	    final Shell shell = new Shell(display);
	    shell.setSize(233, 58);
	    shell.setLayout(new FillLayout());

	    
	    RestTemplate restTemplate = new RestTemplate();
		String url = String.format("https://www.receitaws.com.br/v1/cnpj/75904383003570");
		Object data = restTemplate.getForObject(url, Object.class);
		
		System.out.println(String.valueOf(data));
	    
	    
	    
	
	    shell.open();
	    while (!shell.isDisposed()) {
	        if (!display.readAndDispatch())
	            display.sleep();
	    }
	}

}
