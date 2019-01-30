package com.matera.cursoferias.petstore.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EspecieRequestDTO {

	@NotNull(message = "Descrição é de preenchimento obrigatório.")
	@Size(max = 100, message = "Descrição não pode ultrapassar 100 caracteres.")
	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
