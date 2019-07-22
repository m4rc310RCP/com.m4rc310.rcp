package com.m4rc310.rcp.popup;

//import org.eclipse.mylyn.internal.provisional.commons.ui.AbstractNotificationPopup;
import org.eclipse.mylyn.commons.ui.dialogs.AbstractNotificationPopup;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Link;

public class PopUpUI extends AbstractNotificationPopup{
	private String title;
	private String text;
	
	public PopUpUI(Display display, String title, String text) {
		this(display, getDefaultImage(),title, text);
	}
	
	public PopUpUI(Display display, Image popupImage, String title, String text) {
		super(display);
		setDefaultImage(popupImage);
		this.title  = title;
		this.text = text;
	}
	
	@Override
	protected void createContentArea(Composite composite) {
		composite.setLayout(new GridLayout(1, true));
		Link linkGoogleNews = new Link(composite, 0);
//		String googlenewsLink = "This is a link to <a href=\"https://news.google.com\">Google News</a>";
		String googlenewsLink = text;
		linkGoogleNews.setText(googlenewsLink);
		linkGoogleNews.setSize(500, 150);

		linkGoogleNews.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
			}
		});
	}

	@Override
	protected Image getPopupShellImage(int maximumHeight) {
		return getDefaultImage();
	}

	@Override
	protected String getPopupShellTitle() {
		return title;
	}

	
}
