package com.matera.cursoferias.petstore.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Especie extends EntidadeBase {

	@Column(length=50, nullable = false)
	private String descricao;
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
