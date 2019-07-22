package com.m4rc310.rcp.commands.services;

import java.util.List;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.widgets.Widget;


public interface IObserverRegister {
	void registerObserver(Object object, String field, Widget component);

	void registerObserver(DataBindingContext context, Object object, String field, Widget component);

	void dispose();
	
	void registerViewerSupport(Class<?> type, StructuredViewer viewer, List<?> list, String... propertieNames);

	void updateViewer(StructuredViewer viewer, List<?> list);

	void registerObserverSelection(DataBindingContext context, Object object, String field, Widget component);

	void removeListener(IChangeListener listener);
	
	void addListener(IChangeListener listener);
}
