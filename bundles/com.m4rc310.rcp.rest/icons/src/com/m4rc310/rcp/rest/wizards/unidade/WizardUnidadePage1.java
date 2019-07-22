package com.m4rc310.rcp.rest.wizards.unidade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.brazilutils.br.cpfcnpj.CpfCnpj;
import org.eclipse.e4.ui.services.internal.events.EventBroker;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.m4rc310.rcp.commands.services.IObserverRegister;
import com.m4rc310.rcp.rest.Const;
import com.m4rc310.rcp.rest.i18n.Messages;
import com.m4rc310.rcp.rest.models.AtividadePrincipal;
import com.m4rc310.rcp.rest.models.Unidade;

@SuppressWarnings("restriction")
public class WizardUnidadePage1 extends WizardPage {

	Messages m;

	private Text textCNPJ;

	@Inject
	EventBroker eventBroker;

	@Inject
	IObserverRegister observerRegister;

	private Text textName;

	private Text textFantasy;

	private Text textAddress;

	private Text textNumber;

	private Text textCity;

	private Text textCEP;

	private Text textUF;

	private Text textNumberUnit;

	private Text textAtividadePrincipal;

	private TableViewer viewer;

	private final List<AtividadePrincipal> atividadesSecundarias;

	private Text textNaturezaJuridica;

	private Text textSituacao;

	private Label labelDataAbertura;

	@Inject
	public WizardUnidadePage1(Messages m) {
		super(m.wizard_unidade_title);

		this.m = m;
		
		this.atividadesSecundarias = new ArrayList<AtividadePrincipal>();

		setTitle(m.wizard_unidade_pageA);
		setDescription(m.wizard_unidade_pageA_description);
//		getWizard().getContainer().updateButtons();
	}

	@Override
	public void createControl(Composite parent_) {
		// create layout
		Composite parent = new Composite(parent_, SWT.NONE);
		parent.setLayout(new GridLayout(1, false));

		Group g = new Group(parent, SWT.None);
		g.setLayout(new GridLayout(4, false));

		StackLayout stackLayout = new StackLayout();
		StackLayout stackLayout2 = new StackLayout();

		// setting widget's

		new Label(g, SWT.NONE).setText(m.label_cnpj);

		Composite c = new Composite(g, SWT.NONE);
		GridLayout gl_c = new GridLayout(6, false);
		removeMargins(gl_c);
		c.setLayout(gl_c);

		textCNPJ = new Text(c, SWT.BORDER | SWT.CENTER);

		Composite gb = new Composite(c, SWT.NONE);
		gb.setLayout(stackLayout);

		Button buttonRequestData = new Button(gb, SWT.PUSH);
		Button buttonCancelRequestData = new Button(gb, SWT.PUSH);

		new Label(c, SWT.NONE).setText(m.label_num_unidade);
		this.textNumberUnit = new Text(c, SWT.BORDER|SWT.CENTER);
		
		Composite gb2 = new Composite(c,SWT.NONE);
		gb2.setLayout(stackLayout2);
		
		Button buttonIncNumberUnit = new Button(gb2, SWT.PUSH);
		Button buttonCancelIncNumberUnit = new Button(gb2, SWT.PUSH);
		
		this.labelDataAbertura = new Label(c, SWT.NONE);
		
		new Label(g, SWT.NONE).setText(m.label_natureza_juridica);
		
		Composite c4 = new Composite(g, SWT.NONE);
		GridLayout layout = new GridLayout(3, false);
		removeMargins(layout);
		c4.setLayout(layout);
		
		this.textNaturezaJuridica = new Text(c4, SWT.BORDER|SWT.READ_ONLY);
		
		new Label(c4, SWT.NONE).setText(m.label_situacao);
		this.textSituacao = new Text(c4, SWT.BORDER|SWT.READ_ONLY|SWT.CENTER);
		
//		new Label(g, SWT.NONE);

		new Label(g, SWT.NONE).setText(m.label_name);
		this.textName = new Text(g, SWT.BORDER| SWT.READ_ONLY);

		new Label(g, SWT.NONE).setText(m.label_fantasy);
		this.textFantasy = new Text(g, SWT.BORDER | SWT.READ_ONLY);

		new Label(g, SWT.NONE).setText(m.label_address);
		this.textAddress = new Text(g, SWT.BORDER | SWT.READ_ONLY);

		new Label(g, SWT.NONE).setText(m.label_address_number);
		this.textNumber = new Text(g, SWT.BORDER | SWT.CENTER | SWT.READ_ONLY);

		new Label(g, SWT.NONE).setText(m.label_address_city);
		this.textCity = new Text(g, SWT.BORDER | SWT.READ_ONLY);

		new Label(g, SWT.NONE).setText(m.label_address_cep);

		Composite c2 = new Composite(g, SWT.NONE);
		
		layout = new GridLayout(3, false);
		removeMargins(layout);
		
		c2.setLayout(layout);
		
		
		
		this.textCEP = new Text(c2, SWT.BORDER | SWT.CENTER | SWT.READ_ONLY);

		new Label(c2, SWT.NONE).setText(m.label_address_uf);
		this.textUF = new Text(c2, SWT.BORDER | SWT.CENTER | SWT.READ_ONLY);
		
		Label labelLine = new Label(g, SWT.SEPARATOR|SWT.HORIZONTAL);
		
		
		Composite c3 = new Composite(g, SWT.NONE);
		layout = new GridLayout(1,false);
		removeMargins(layout);

		c3.setLayout(layout);
		
		Label labelAtividadePrincipal = new Label(c3, SWT.NONE);
		labelAtividadePrincipal.setText(m.label_atividade_principal);
		
		this.textAtividadePrincipal = new Text(c3, SWT.READ_ONLY|SWT.BORDER);
		
		new Label(c3, SWT.NONE);
		new Label(c3, SWT.NONE).setText(m.label_atividades_secundarias);
		
		this.viewer = new TableViewer(c3, SWT.MULTI | SWT.H_SCROLL
	            | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		
		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		viewer.setContentProvider(ArrayContentProvider.getInstance());
		
		createColumns(viewer);
		
		
		// Layout

		GridData gd = new GridData();
		gd.widthHint = 140;
		textCNPJ.setLayoutData(gd);
		
		gd = new GridData();
		gd.widthHint = 90;
		labelDataAbertura .setLayoutData(gd);

		gd = new GridData(GridData.FILL_HORIZONTAL);	
		gd.horizontalSpan=3;
		c4.setLayoutData(gd);
		
		gd = new GridData(GridData.FILL_HORIZONTAL);
		textNaturezaJuridica.setLayoutData(gd);
		
		gd = new GridData();
		gd.widthHint = 150;
		textSituacao.setLayoutData(gd);
		
		gd = new GridData();
		gd.widthHint = 300;
		textName.setLayoutData(gd);

		gd = new GridData();	
		gd.horizontalSpan=3;
		c.setLayoutData(gd);
		
		gd = new GridData();
		gd.widthHint = 40;
		textNumberUnit.setLayoutData(gd);
		
		gd = new GridData(GridData.FILL_HORIZONTAL);
		textFantasy.setLayoutData(gd);

		gd = new GridData(GridData.FILL_HORIZONTAL);
		textCity.setLayoutData(gd);

		gd = new GridData(GridData.FILL_HORIZONTAL);
		textAddress.setLayoutData(gd);

		gd = new GridData();
		gd.widthHint = 100;
		textCEP.setLayoutData(gd);
		
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 4;
		c3.setLayoutData(gd);
		
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 4;
		labelLine.setLayoutData(gd);
		
		gd = new GridData(GridData.FILL_HORIZONTAL);
		textAtividadePrincipal.setLayoutData(gd);
		
		textNumberUnit.setEnabled(false);
		
		buttonRequestData.setEnabled(false);
		buttonIncNumberUnit.setEnabled(false);

		buttonRequestData.setText(m.text_verify);
		
		buttonCancelRequestData.setText(m.text_cancel);
		buttonCancelIncNumberUnit.setText(m.label_alterar);
		buttonIncNumberUnit.setText(m.label_save);
		
		GridData gridData = new GridData();
        gridData.verticalAlignment = GridData.FILL;
        gridData.horizontalSpan = 2;
        gridData.heightHint=60;
        gridData.grabExcessHorizontalSpace = true;
        gridData.grabExcessVerticalSpace = true;
        gridData.horizontalAlignment = GridData.FILL;
        viewer.getControl().setLayoutData(gridData);
		

		// setting listener's

		textCNPJ.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String scnpj = ((Text) e.widget).getText();
				CpfCnpj cc = new CpfCnpj(scnpj);

				boolean isValidate = cc.isValid() && cc.isCnpj();
				buttonRequestData.setEnabled(isValidate);
				if (isValidate) {
					c.getShell().setDefaultButton(buttonRequestData);
				}
			}
		});
		
		textNumberUnit.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String snumber = ((Text) e.widget).getText();
				boolean isValidate = snumber.isEmpty();
				buttonIncNumberUnit.setEnabled(!isValidate);
				gb2.getShell().setDefaultButton(buttonIncNumberUnit);
			}
		});
		
		buttonIncNumberUnit.addListener(SWT.Selection, e->{
			textNumberUnit.setEnabled(false);
			buttonCancelRequestData.setEnabled(false);
			stackLayout2.topControl = buttonCancelIncNumberUnit;
			gb2.layout();
			
		});
		
		buttonCancelIncNumberUnit.addListener(SWT.Selection, e->{
			textNumberUnit.setEnabled(true);
			buttonCancelRequestData.setEnabled(true);
			buttonIncNumberUnit.setEnabled(false);
			textNumberUnit.setText("");
			stackLayout2.topControl = buttonIncNumberUnit;
			gb2.layout();
			textNumberUnit.setFocus();
		});

		stackLayout2.topControl= buttonIncNumberUnit;
		
		stackLayout.topControl = buttonRequestData;

		buttonRequestData.addListener(SWT.Selection, e -> {
			String res = textCNPJ.getText();
			textCNPJ.setEnabled(false);
			requestValue(res);
			textNumberUnit.setEnabled(true);
			textNumberUnit.setFocus();
			stackLayout.topControl = buttonCancelRequestData;
			gb.layout();
		});

		buttonCancelRequestData.addListener(SWT.Selection, e -> {
			textCNPJ.setEnabled(true);
			buttonRequestData.setEnabled(false);

			clean(textCNPJ, textAddress, textCEP, textCity, textFantasy, 
					textAtividadePrincipal,textName, textNumber, textUF, textNumberUnit);
			
			labelDataAbertura.setText("---");
			
			observerRegister.updateViewer(viewer, new ArrayList<>());

			textCNPJ.setFocus();
			textNumberUnit.setEnabled(false);
			stackLayout.topControl = buttonRequestData;
			gb.layout();
		});

		setControl(parent);
		setPageComplete(false);
	}
	
	
	private void removeMargins(GridLayout layout) {
		layout.marginWidth = 0;
		layout.verticalSpacing = 0;
		
		layout.horizontalSpacing = 0;
		layout.marginBottom = 0;
		layout.marginHeight = 0;
		layout.marginLeft = 0;
		layout.marginRight = 0;
		layout.marginTop = 0;
	}

	private void clean(Text... texts) {
		for (Text text : texts) {
			text.setText("");
		}
	}
	
	
	 private void createColumns(final TableViewer viewer) {
		 	String[] titles = { "code", "text"};
	        int[] bounds = { 100, 400};
	        
	        TableViewerColumn colCode = new TableViewerColumn(viewer, SWT.NONE);
	        colCode.getColumn().setText(titles[0]);
	        colCode.getColumn().setWidth(bounds[0]);
	        
	        TableViewerColumn colText = new TableViewerColumn(viewer, SWT.NONE);
	        colText.getColumn().setText(titles[1]);
	        colText.getColumn().setWidth(bounds[1]);
	        
	        
//	        this.writableList = new WritableList<>();
	        
	        observerRegister.registerViewerSupport(AtividadePrincipal.class, viewer,
	        		atividadesSecundarias, "code","text");
	        
	        
//	        ViewerSupport.bind(viewer, writableList,
//					BeanProperties.values(new String[] { Todo.FIELD_SUMMARY, Todo.FIELD_DESCRIPTION }));
	 }

	private void requestValue(String cnpj) {
		CpfCnpj cc = new CpfCnpj(cnpj);

		RestTemplate restTemplate = new RestTemplate();
		String url = String.format("https://m4rc310.herokuapp.com/api/info/%s", cc.getNumber());

		ResponseEntity<Unidade> und = restTemplate.getForEntity(url, Unidade.class);
		loadUnidade(und.getBody());
	}

//	@Override
	public void createControl_(Composite parent_) {
		Composite parent = new Composite(parent_, SWT.NONE);

		setControl(parent);
		parent.setLayout(new GridLayout(1, false));

		Group g = new Group(parent, SWT.None);
		g.setLayout(new GridLayout(4, false));

		GridData gd = new GridData();
		Label label = new Label(g, SWT.NONE);
		label.setText(m.label_cnpj);
		label.setLayoutData(gd);

		gd = new GridData();
		gd.widthHint = 200;

		this.textCNPJ = new Text(g, SWT.BORDER | SWT.CENTER);
		textCNPJ.setMessage("");
		textCNPJ.setLayoutData(gd);

		Button buttonRequestData = new Button(g, SWT.PUSH);
		buttonRequestData.setEnabled(false);

		textCNPJ.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				Text text = (Text) e.widget;
				try {
//					cc = new Cnpj(text.getText());
//					eventBroker.send(Const.LOAD_CNPJ, cc);

					buttonRequestData.setEnabled(true);
					if (!isPageComplete()) {
						parent.getShell().setDefaultButton(buttonRequestData);
					}
					setErrorMessage(null);
				} catch (Exception e2) {
					buttonRequestData.setEnabled(false);
					setErrorMessage(m.message_error_cnpj_invalid);
					setPageComplete(false);
				}
			}
		});

		textCNPJ.addVerifyListener(e -> {

			if (e.character == SWT.DEL || e.character == SWT.BS) {
				return;
			}
			e.doit = e.text.matches("[0-9,.-]+");
		});

		buttonRequestData.setText(m.text_verify);
		buttonRequestData.addListener(SWT.Selection, e -> {
			setPageComplete(true);
		});

		setPageComplete(false);
		setControl(parent);
	}

	private void loadUnidade(Unidade unidade) {
		observerRegister.dispose();

		observerRegister.registerObserver(unidade, "cnpj", textCNPJ);
		observerRegister.registerObserver(unidade, "situacao", textSituacao);
		observerRegister.registerObserver(unidade, "natureza_juridica", textNaturezaJuridica);
		observerRegister.registerObserver(unidade, "nome", textName);
		observerRegister.registerObserver(unidade, "fantasia", textFantasy);
		observerRegister.registerObserver(unidade, "municipio", textCity);
//		observerRegister.registerObserver(unidade, "abertura", labelDataAbertura);
		observerRegister.registerObserver(unidade, "logradouro", textAddress);
		observerRegister.registerObserver(unidade, "numero", textNumber);
		observerRegister.registerObserver(unidade, "cep", textCEP);
		observerRegister.registerObserver(unidade.getAtividade_principal()[0], "text", textAtividadePrincipal);
		observerRegister.registerObserver(unidade, "uf", textUF);
		
		labelDataAbertura.setText(unidade.getAbertura());
		
		
		observerRegister.updateViewer(viewer, Arrays.asList(unidade.getAtividades_secundarias()));
		

		eventBroker.send(Const.LOAD_UNIDADE, unidade);

//		observerRegister.removeListener(listener);
//		observerRegister.addListener(listener);
	}

}
