package com.m4rc310.rcp.rest.models;

public class Colaborador extends FEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String nome;
	
	private String sobrenome;
	
	
	private String nomeCompleto;
	

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	// @Column(unique = true)
	private String cpf;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

}
