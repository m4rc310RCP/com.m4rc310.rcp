package com.m4rc310.rcp.rest.models;

import java.io.Serializable;
import java.math.BigDecimal;

public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	private String phonetic;
	
	
	public String getPhonetic() {
		return phonetic;
	}

	private BigDecimal valor;

	private int fator;
	
	public void setPhonetic(String phonetic) {
		this.phonetic = phonetic;
	}


	public BigDecimal getValor() {
		return valor;
	}
	
	

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Long getId() {
		return id;
	}

	public int getFator() {
		return fator;
	}


	public void setFator(int fator) {
		this.fator = fator;
	}


	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
