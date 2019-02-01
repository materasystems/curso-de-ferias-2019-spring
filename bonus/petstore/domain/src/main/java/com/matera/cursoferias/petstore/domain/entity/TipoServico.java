package com.matera.cursoferias.petstore.domain.entity;

import com.matera.cursoferias.petstore.util.exception.RegistroNaoEncontradoException;

public enum TipoServico {

    CONSULTA(1, "Consulta"),
    BANHO_TOSA(2, "Banho e Tosa"),
    CIRURGIA(3, "Cirurgia"),
    VACINACAO(4, "Vacinação");

	private int id;
	private String descricao;

	private TipoServico(int id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	public int getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}

	public static TipoServico valueOf(int id) {
		for (TipoServico tipoServico : values()) {
			if (tipoServico.id == id) {
				return tipoServico;
			}
		}

		throw new RegistroNaoEncontradoException(String.format("Tipo de serviço %d não encontrado!", id));
	}
}
