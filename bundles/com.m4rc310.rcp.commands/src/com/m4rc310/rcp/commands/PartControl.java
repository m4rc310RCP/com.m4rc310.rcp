package com.m4rc310.rcp.commands;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MStackElement;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;

@Creatable
@Singleton
public class PartControl {

	@Inject
	MApplication application;
	@Inject
	EPartService partService;
	@Inject
	EModelService modelService;

//	@Inject MessagesRegistry mr;
	
	@Inject
	public PartControl() {

	}
	
	public void visible(String partUri, boolean visible) {
		MPart part = partService.findPart(partUri);
		if(part == null) {
			part = partService.createPart(partUri);
		}
		
		for (String variable : part.getVariables()) {
			if (variable.contains("partStack:")) {
				variable = variable.replace("partStack:", "");
				
				MPartStack stack = modelService.
						findElements(application, variable, MPartStack.class, null).get(0);
				
				stack.setVisible(true);
				
				
				List<MStackElement> childres = stack.getChildren();
				childres.add(part);
				
				break;
			}
		}
		if(visible) {
			partService.showPart(part, PartState.ACTIVATE);
		}else {
			partService.hidePart(part);
		}
	}

	public void show(String partUri) {
		MPart part = partService.findPart(partUri);
		
		if (part == null) {
			part = partService.createPart(partUri);
		}

		for (String variable : part.getVariables()) {
			if (variable.contains("partStack:")) {
				variable = variable.replace("partStack:", "");
				
				MPartStack stack = modelService.
						findElements(application, variable, MPartStack.class, null).get(0);
				
				stack.setVisible(true);
				
				
				List<MStackElement> childres = stack.getChildren();
				childres.add(part);
				
				break;
			}
		}
		partService.showPart(part, PartState.ACTIVATE);
	}

}
