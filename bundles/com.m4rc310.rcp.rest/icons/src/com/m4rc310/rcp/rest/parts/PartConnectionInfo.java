
package com.m4rc310.rcp.rest.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.EventTopic;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import com.m4rc310.rcp.commands.services.MImages;
import com.m4rc310.rcp.rest.i18n.MessagesRegistry;
import org.eclipse.wb.swt.ResourceManager;

@SuppressWarnings("restriction")
public class PartConnectionInfo {

	@Inject
	MessagesRegistry mr;

	@Inject
	MImages img;

	private Label labelServer;
	private Label labelTimeServer;

	private Label labelIcon;

	@Inject
	public PartConnectionInfo() {

	}

	@PostConstruct
	public void postConstruct(Composite parent_) {
		Composite parent = new Composite(parent_, SWT.NONE);
		parent.setLayout(new GridLayout(1, false));

		Group group = new Group(parent, SWT.NONE);
		group.setLayout(new GridLayout(1, false));
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		mr.register(group::setText, m -> m.part_label_server_status);

		GridData gd = new GridData();
		gd.horizontalSpan = 2;
		gd.widthHint = 200;
		gd.heightHint = 1;

		Label label = new Label(group, SWT.NONE);
		label.setImage(ResourceManager.getPluginImage("com.m4rc310.rcp.commands", "icons/16x16/apps/accessories-calculator.png"));
		mr.register(label::setText, m -> m.label_server_url);

		Composite c = new Composite(group, SWT.NONE);
		c.setLayout(new GridLayout(2, false));

		this.labelIcon = new Label(c, SWT.NONE);
		Image icon = img.getImage("com.m4rc310.rcp.rest", "icons/network_offline-2.png");
		labelIcon.setImage(icon);

		Composite c1 = new Composite(c, SWT.NONE);
		c1.setLayout(new GridLayout(3, false));

		label = getLabel(c1);
		mr.register(label::setText, m -> m.label_server_status);
		new Label(c1, SWT.NONE);

		this.labelServer = new Label(c1, SWT.NONE | SWT.BOLD);
		mr.register(labelServer::setText, m -> m.label_default);

		label = getLabel(c1);
		label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		label.setAlignment(SWT.RIGHT);
		mr.register(label::setText, m -> m.label_server_time);
		new Label(c1, SWT.NONE);
		
		new Label(c1, SWT.NONE);

		this.labelTimeServer = new Label(c1, SWT.NONE | SWT.BOLD);
		new Label(c1, SWT.NONE);
		new Label(c1, SWT.NONE);
		mr.register(labelTimeServer::setText, m -> m.label_default);
	}

	@Inject
	@Optional
	private void updateStatus(@EventTopic("update_status_server") String value, UISynchronize sync) {
		sync.asyncExec(() -> {
			Image icon = img.getImage("com.m4rc310.rcp.rest", "icons/network_connected-2.png");
			labelIcon.setImage(icon);
		});
	}

	private Label getLabel(Composite parent) {
		Label label = new Label(parent, SWT.NONE);
		label.setAlignment(SWT.RIGHT);
		return label;
	}

}