package com.m4rc310.rcp.commands.services;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.wb.swt.ResourceManager;

public class MImagesImpl implements MImages {

	
	
	@Override
	public Image getImage(String pluginId, String pathImage) {
		return ResourceManager.getPluginImage(pluginId, pathImage);
	}

	@Override
	public Image getImage(String pluginId, String pathImage, int w, int h) {
		Image img = getImage(pluginId, pathImage);
		Image scaledImage = new Image(Display.getDefault(), w, h);
		GC gc = new GC(scaledImage);
		gc.setAntialias(SWT.ON);
		gc.setInterpolation(SWT.HIGH);
		gc.drawImage(img, 0, 0, img.getBounds().width, img.getBounds().height, 0, 0, w, h);
		return scaledImage;
	}

	@Override
	public Image getImage(String reference) {
		return ResourceManager.getImage(reference);
	}

}
