package com.m4rc310.rcp.commands.services;

import org.eclipse.swt.graphics.Image;

public interface MImages {
	Image getImage(String reference);
	Image getImage(String pluginId, String pathImage);
	Image getImage(String pluginId, String pathImage, int w, int h);
}
