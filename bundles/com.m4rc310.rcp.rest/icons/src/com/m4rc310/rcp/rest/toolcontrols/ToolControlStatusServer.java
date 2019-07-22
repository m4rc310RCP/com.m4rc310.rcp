package com.m4rc310.rcp.rest.toolcontrols;

import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.EventTopic;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.ResourceManager;

import com.m4rc310.rcp.commands.services.MImages;
import com.m4rc310.rcp.rest.Const;
import com.m4rc310.rcp.rest.i18n.Messages;
import com.m4rc310.rcp.rest.i18n.MessagesRegistry;

@SuppressWarnings("restriction")
public class ToolControlStatusServer {

	private final String IMAGES_PLUGIN = "com.m4rc310.rcp.commands";
	private final String ICON_CONNECTED = "icons/16x16/status/network-idle.png";
	private final String ICON_DISCONNECTED = "icons/16x16/status/network-error.png";

	private Image a;
	private Image b;
	private Image iconConnected;
	private Image iconDisconnected;

	private boolean connected = false;

	private Label icon;
	private Label labelStatus;

	@Inject
	MessagesRegistry mr;

	@Inject
	@Translation
	Messages mm;

	@Inject
	UISynchronize sync;

	@Inject
	MImages img;

	private Timer timer;

	@PostConstruct
	public void createGui(Composite parent_) {
		Composite parent = new Composite(parent_, SWT.NONE);
		GridLayout layout = new GridLayout(3, false);
		parent.setLayout(layout);

		Label separator = new Label(parent, SWT.SEPARATOR | SWT.VERTICAL);

		GridData gd = new GridData();
		gd.heightHint = 14;

		separator.setLayoutData(gd);

		this.icon = new Label(parent, SWT.NONE);
		icon.setImage(img.getImage(IMAGES_PLUGIN, ICON_DISCONNECTED));
		mr.register(icon::setToolTipText, m -> m.status_server_tooltip);

		gd = new GridData();
		gd.widthHint = 200;
		this.labelStatus = new Label(parent, SWT.NONE);
		mr.register(labelStatus::setText, m -> m.label_default);
		labelStatus.setLayoutData(gd);

		a = img.getImage(IMAGES_PLUGIN, "icons/16x16/status/network-receive.png");
		b = img.getImage(IMAGES_PLUGIN, "icons/16x16/status/network-transmit.png");
		iconConnected = img.getImage(IMAGES_PLUGIN, ICON_CONNECTED);
		iconDisconnected = img.getImage(IMAGES_PLUGIN, ICON_DISCONNECTED);
	}

	@Inject
	@Optional
	private void tryConnection(@EventTopic(Const.TRY_CONNECTION) String status) {

		if (timer != null) {
			return;
		}

		this.timer = new Timer();
		timer.schedule(new TimerTask() {
			boolean ab = true;
			final String[] stringLabel = new String[] { mm.server_connecting_0, mm.server_connecting_1,
					mm.server_connecting_2, mm.server_connecting_3 };
			long i = 0;

			@Override
			public void run() {
				sync.asyncExec(() -> {
					if (labelStatus.isDisposed()) {
						return;
					}

					Color color = ResourceManager.getColor(255, 0, 0);
					labelStatus.setForeground(color);

					if (!icon.isDisposed()) {
						ab = !ab;
						icon.setImage(ab ? a : b);
						int id = (int) i++ % 4;
						labelStatus.setText(stringLabel[id]);
					}

					if (connected) {
						icon.setImage(iconConnected);
						timer.cancel();
					}
				});
			}
		}, 0, 300);
	}

	@Inject
	@Optional
	private void errorStatus(@EventTopic(Const.CONNECTION_NO_SUCCESS) String status) {
		sync.asyncExec(() -> {
			try {

				timer.cancel();
				Color color = ResourceManager.getColor(255, 0, 0);
				labelStatus.setForeground(color);

				mr.register(labelStatus::setText, m -> m.server_not_connected);
				icon.setImage(iconDisconnected);

			} catch (Exception e) {

			}

		});
	}

	@Inject
	@Optional
	private void updateStatusNormal(@EventTopic(Const.CONNECTION_NORMAL) String status) {
		
		sync.asyncExec(() -> {
			try {
				labelStatus.setText("");;
			} catch (Exception e) {
			}
			
		});
	}
	
	@Inject
	@Optional
	private void updateStatus(@EventTopic(Const.CONNECTION_SUCCESS) String status) {
		sync.asyncExec(() -> {
			try {
				timer.cancel();
				
				Color color = ResourceManager.getColor(0, 0, 255);
				labelStatus.setForeground(color);
				mr.register(labelStatus::setText, m -> m.server_connected_label);
				icon.setImage(iconConnected);

			} catch (Exception e) {
			}
			
		});
	}

}