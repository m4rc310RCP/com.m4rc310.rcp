package com.m4rc310.rcp.rest.preferences.pages;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;

/** A sample preference page to show how it works */
public class ADefaultPage extends FieldEditorPreferencePage
{
	
	public ADefaultPage()
	{
		super(GRID);
	}

	@Override
	protected void createFieldEditors()
	{
	
		addField(new StringFieldEditor("aDefault", "A default value : ", getFieldEditorParent()));
	}

}
