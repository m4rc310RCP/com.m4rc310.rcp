package com.m4rc310.rcp.commands.services;

import java.util.List;

import javax.inject.Inject;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
//import org.eclipse.core.databinding.beans.typed.BeanProperties;
import org.eclipse.jface.databinding.swt.WidgetProperties;
//import org.eclipse.jface.databinding.swt.typed.Be;
import org.eclipse.jface.databinding.viewers.ViewerSupport;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Widget;
//
//import org.eclipse.core.databinding.Binding;
//import org.eclipse.core.databinding.DataBindingContext;
//import org.eclipse.core.databinding.beans.typed.BeanProperties;
//import org.eclipse.core.databinding.observable.IChangeListener;
//import org.eclipse.core.databinding.observable.list.IObservableList;
//import org.eclipse.core.databinding.observable.list.WritableList;
//import org.eclipse.core.databinding.observable.value.IObservableValue;
//import org.eclipse.jface.databinding.swt.typed.WidgetProperties;
//import org.eclipse.jface.databinding.viewers.ViewerSupport;
//import org.eclipse.jface.viewers.StructuredViewer;
//import org.eclipse.swt.SWT;
//import org.eclipse.swt.widgets.Widget;

@SuppressWarnings({ "unchecked", "deprecation" })
public class ObserverRegisterImpl implements IObserverRegister {

	private WritableList<?> writableList;
	private DataBindingContext ctx = new DataBindingContext();

	@Inject
	public ObserverRegisterImpl() {
	}

	@Override
	public void registerObserver(DataBindingContext context, Object object, String field, Widget component) {
		IObservableValue<?> target = WidgetProperties.text(SWT.Modify).observe(component);
		IObservableValue<?> model = BeanProperties.value(field).observe(object);
		context.bindValue(target, model);
	}

	@Override
	public void registerObserverSelection(DataBindingContext context, Object object, String field, Widget component) {
		IObservableValue<?> target = WidgetProperties.text(SWT.Modify).observe(component);
		IObservableValue<?> model = BeanProperties.value(field).observe(object);
		context.bindValue(target, model);
	}

	@Override
	public void dispose() {
		ctx.dispose();
	}

	@Override
	public void registerObserver(Object object, String field, Widget component) {
		registerObserver(ctx, object, field, component);
	}

	@Override
	public void registerViewerSupport(Class<?> type, StructuredViewer viewer, List<?> list, String... propertieNames) {
		writableList = new WritableList<>(list, type);
		ViewerSupport.bind(viewer, writableList,BeanProperties.values(type,propertieNames));
	}

	@Override
	public void updateViewer(StructuredViewer viewer, @SuppressWarnings("rawtypes") List list) {
		if (viewer != null) {
			writableList.clear();
			writableList.addAll(list);
		}
	}

	@Override
	public void removeListener(IChangeListener listener) {
//		IObservableList<?> providers = ctx.getValidationStatusProviders();
//
//		for (Object o : providers) {
////			Binding b = (Binding) o;
////			b.getTarget().removeChangeListener(listener);
//		}
	}

	@Override
	public void addListener(IChangeListener listener) {
//		IObservableList<?> providers = ctx.getValidationStatusProviders();
//
//		for (Object o : providers) {
////			Binding b = (Binding) o;
////			b.getTarget().addChangeListener(listener);
//		}

	}

}
