package com.matera.cursoferias.petstore.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Servico extends EntidadeBase {

	@Column(length = 500, nullable = false)
	private String observacao;
	
	@Column(name = "data_hora", nullable = false)
	private LocalDateTime dataHora;
	
	@Column(name = "tipo_servico", nullable = false)
	private int tipoServico;
	
	@Column(nullable = false)
	private BigDecimal valor;
	
	@ManyToOne
	@JoinColumn(name = "id_pet", nullable = false)
	private Pet pet;

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}

	public TipoServico getTipoServico() {
		return TipoServico.valueOf(tipoServico);
	}

	public void setTipoServico(TipoServico tipoServico) {
		this.tipoServico = tipoServico.getId();
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Pet getPet() {
		return pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
	}
	
}
