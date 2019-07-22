package com.m4rc310.rcp.popup.services;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.wb.swt.ResourceManager;

import com.m4rc310.rcp.popup.PopUpUI;


public class PopupServiceImpl implements IPopupService{

	private final Image imageDefault = ResourceManager.getPluginImage("com.m4rc310.rcp.popup", "icons/png_000.bmp");

	@Override
	public void show(String text) {
		show("-", text);
	}

	@Override
	public void show(String title, String text) {
		show(imageDefault,title, text, 0);
	}

	@Override
	public void show(Image icon, String text) {
		show(icon, text, 0);
	}

	@Override
	public void show(Image icon, String title, String text) {
		show(icon,title,text, 0);
	}

	@Override
	public void show(String text, int autoCloseMiliseconds) {
		show(imageDefault,"-",text, 0);
	}

	@Override
	public void show(String title, String text, int autoCloseMiliseconds) {
		show(imageDefault, title, text, autoCloseMiliseconds);
	}

	@Override
	public void show(Image icon, String text, int autoCloseMiliseconds) {
		show(icon, "-", text, autoCloseMiliseconds);
	}

	@Override
	public void show(Image icon, String title, String text, int autoCloseMiliseconds) {
		Runnable runnable = new Runnable() {
			public void run() {
				PopUpUI popup = new PopUpUI(Display.getCurrent(),icon,title,text) {
				};
				popup.open();
			}
		};
		runnable.run();
	}
}
