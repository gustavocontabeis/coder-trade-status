package br.com.codersistemas.codertradestatus.model;

public class ItemCarteira {
	
	private String nomeCliente, nomeAtivo;
	private Integer quantidade;
	private Float valorAquisicao, cotacaoAtual, resultado;
	
	public ItemCarteira() {
		super();
	}
	
	public ItemCarteira(String nomeCliente, String nomeAtivo, Integer quantidade, Float valorAquisicao, Float cotacaoAtual,
			Float resultado) {
		super();
		this.nomeCliente = nomeCliente;
		this.nomeAtivo = nomeAtivo;
		this.quantidade = quantidade;
		this.valorAquisicao = valorAquisicao;
		this.cotacaoAtual = cotacaoAtual;
		this.resultado = resultado;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}
	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}
	public String getNomeAtivo() {
		return nomeAtivo;
	}
	public void setNomeAtivo(String nomeAtivo) {
		this.nomeAtivo = nomeAtivo;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	public Float getValorAquisicao() {
		return valorAquisicao;
	}
	public void setValorAquisicao(Float valorAquisicao) {
		this.valorAquisicao = valorAquisicao;
	}
	public Float getCotacaoAtual() {
		return cotacaoAtual;
	}
	public void setCotacaoAtual(Float cotacaoAtual) {
		this.cotacaoAtual = cotacaoAtual;
	}
	public Float getResultado() {
		return resultado;
	}
	public void setResultado(Float resultado) {
		this.resultado = resultado;
	}

}
