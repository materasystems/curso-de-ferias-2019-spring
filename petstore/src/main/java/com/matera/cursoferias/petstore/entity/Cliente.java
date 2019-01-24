package com.matera.cursoferias.petstore.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Cliente extends EntidadeBase {

	@Column(length = 100, nullable = false)
	private String nome;
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
