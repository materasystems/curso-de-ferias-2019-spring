package com.matera.cursoferias.petstore.dto;

import java.math.BigDecimal;

public class ServicoRequestDTO {

	private String observacao;
	private int idTipoServico;
	private BigDecimal valor;
	private Long idPet;

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public int getIdTipoServico() {
		return idTipoServico;
	}

	public void setIdTipoServico(int idTipoServico) {
		this.idTipoServico = idTipoServico;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Long getIdPet() {
		return idPet;
	}

	public void setIdPet(Long idPet) {
		this.idPet = idPet;
	}
	
}
