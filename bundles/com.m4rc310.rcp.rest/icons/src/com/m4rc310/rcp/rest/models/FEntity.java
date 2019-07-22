package com.m4rc310.rcp.rest.models;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Date;

public class FEntity implements Serializable  {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	
	
	private final Date dateAdd;
	
	private boolean temp;
	
	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
            this);
	
	
	
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getPhonetic() {
		return phonetic;
	}


	public void setPhonetic(String phonetic) {
		this.phonetic = phonetic;
	}


	public Date getDateAdd() {
		return dateAdd;
	}


	public boolean isTemp() {
		return temp;
	}


	public void setTemp(boolean temp) {
		this.temp = temp;
	}


	private String phonetic;
	
	
	public FEntity() {
		this.dateAdd = new Date();
	}
	
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void addPropertyChangeListener(String propertyName,
            PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(String propertyName,
            PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(propertyName,
                listener);
    }

    protected void firePropertyChange(String propertyName, Object oldValue,
            Object newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, oldValue,
                newValue);
    }

	 

}
