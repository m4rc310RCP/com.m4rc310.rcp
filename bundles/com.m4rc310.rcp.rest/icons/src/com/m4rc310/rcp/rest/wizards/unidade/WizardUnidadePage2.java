package com.m4rc310.rcp.rest.wizards.unidade;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.brazilutils.br.cpfcnpj.Cnpj;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.EventTopic;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.m4rc310.rcp.rest.Const;
import com.m4rc310.rcp.rest.i18n.Messages;
import com.m4rc310.rcp.rest.models.Unidade;

@SuppressWarnings("restriction")
public class WizardUnidadePage2 extends WizardPage {

	Messages m;

	private Text textCNPJ;

	private Text textNumeroUnidade;
	
	private Unidade unidades ;
	

	@Inject
	Shell shell;

	@Inject
	public WizardUnidadePage2(Messages m) {
		super(m.wizard_unidade_title);

		this.m = m;

		setTitle(m.wizard_unidade_pageA);
		setDescription(m.wizard_unidade_pageB_description);
		setPageComplete(false);
	}

	@Inject
	@Optional
	private void updateStatusNormal(@EventTopic(Const.LOAD_CNPJ) Cnpj cnpj) {

		if (!textCNPJ.isDisposed())
			textCNPJ.setText(cnpj.getCpfCnpj());
	}

	@Override
	public void createControl(Composite parent_) {
		
		unidades = new Unidade();
		
		Composite parent = new Composite(parent_, SWT.NONE);

		setControl(parent);
		parent.setLayout(new GridLayout(1, false));

		Group group = new Group(parent, SWT.BORDER);
		group.setLayout(new GridLayout(6, false));

		Label label = new Label(group, SWT.NONE);
		label.setText(m.label_numero_unidade);

		GridData gd = new GridData();
		this.textNumeroUnidade = new Text(group, SWT.BORDER | SWT.CENTER);

		textNumeroUnidade.addVerifyListener(e -> {
			if (e.character == SWT.BS || e.character == SWT.DEL || e.text.isEmpty()) {
				return;
			}
			e.doit = e.text.matches("[0-9]");
		});

		gd.widthHint = 50;
		textNumeroUnidade.setLayoutData(gd);

		Composite buttonGroup = new Composite(group, SWT.NONE);

		StackLayout stackLayout = new StackLayout();
		buttonGroup.setLayout(stackLayout);

		Button buttonConfirmar = new Button(buttonGroup, SWT.PUSH);
		buttonConfirmar.setText(m.text_confirmar);
		buttonConfirmar.setEnabled(false);

		Button buttonModificar = new Button(buttonGroup, SWT.PUSH);
		buttonModificar.setText(m.text_modify);
		buttonModificar.setEnabled(false);

		stackLayout.topControl = buttonConfirmar;

		textNumeroUnidade.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String sn = ((Text) e.widget).getText();
				buttonConfirmar.setEnabled(!sn.isEmpty());
				if (!sn.isEmpty()) {
					parent.getShell().setDefaultButton(buttonConfirmar);
				}
			}
		});

		Listener listener = new Listener() {
			boolean con = true; // ponto chave

			@Override
			public void handleEvent(Event arg0) {
				textNumeroUnidade.setEnabled(!con);
				label.setEnabled(!con);

				buttonModificar.setEnabled(con);
				buttonConfirmar.setEnabled(!con);

				stackLayout.topControl = con ? buttonModificar : buttonConfirmar;
				buttonGroup.layout();

				if (!con) {
					textNumeroUnidade.setText("");
					textNumeroUnidade.setFocus();
					buttonConfirmar.setEnabled(con);
				}

				con = !con;
			}
		};

		buttonConfirmar.addListener(SWT.Selection, listener);
		buttonModificar.addListener(SWT.Selection, listener);

		
		Label labelCnpj = new Label(group, SWT.NONE);
		labelCnpj.setText(m.label_cnpj);
		
		gd = new GridData();

		gd.widthHint = 170;
		textCNPJ = new Text(group, SWT.BORDER | SWT.READ_ONLY | SWT.CENTER);
		textCNPJ.setLayoutData(gd);

		setControl(parent);
	}

}
