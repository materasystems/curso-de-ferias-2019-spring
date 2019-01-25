package com.matera.cursoferias.petstore.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Cliente extends EntidadeBase {

	@Column(length = 100, nullable = false)
	private String nome;
	
	@OneToMany(mappedBy = "cliente")
	private List<Pet> pets;
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Pet> getPets() {
		return pets;
	}

	public void setPets(List<Pet> pets) {
		this.pets = pets;
	}

}
