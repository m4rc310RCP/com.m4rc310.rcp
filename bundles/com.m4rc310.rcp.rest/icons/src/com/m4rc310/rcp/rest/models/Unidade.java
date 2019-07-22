package com.m4rc310.rcp.rest.models;

public class Unidade extends FEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String cnpj;

	private String nome;
	private String fantasia;
	private String situacao;
	private String municipio;
	private String logradouro;
	private String numero;
	private String abertura;
	private String cep;
	private String natureza_juridica;
	private String uf;
	private AtividadePrincipal[] atividade_principal;
	private AtividadePrincipal[] atividades_secundarias;
	
	public AtividadePrincipal[] getAtividade_principal() {
		return atividade_principal;
	}

	public void setAtividade_principal(AtividadePrincipal[] atividade_principal) {
		this.atividade_principal = atividade_principal;
	}

	public String getCnpj() {
		return cnpj;
	}
	
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getFantasia() {
		return fantasia;
	}

	public void setFantasia(String fantasia) {
		this.fantasia = fantasia;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getNatureza_juridica() {
		return natureza_juridica;
	}

	public void setNatureza_juridica(String natureza_juridica) {
		this.natureza_juridica = natureza_juridica;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public AtividadePrincipal[] getAtividades_secundarias() {
		return atividades_secundarias;
	}

	public void setAtividades_secundarias(AtividadePrincipal[] atividades_secundarias) {
		this.atividades_secundarias = atividades_secundarias;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getAbertura() {
		return abertura;
	}

	public void setAbertura(String abertura) {
		this.abertura = abertura;
	}

	
	
}
