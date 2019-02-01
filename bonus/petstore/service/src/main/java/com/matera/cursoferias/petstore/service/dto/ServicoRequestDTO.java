package com.matera.cursoferias.petstore.service.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

public class ServicoRequestDTO {

	@NotNull(message = "Observação é de preenchimento obrigatório.")
    private String observacao;

	@NotNull(message = "Tipo serviço é de preenchimento obrigatório.")
    private int idTipoServico;

	@NotNull(message = "Valor é de preenchimento obrigatório.")
    private BigDecimal valor;

	@NotNull(message = "Pet é de preenchimento obrigatório.")
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
