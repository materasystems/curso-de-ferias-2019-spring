package com.matera.cursoferias.petstore.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PetRequestDTO {

	private String nome;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataNascimento;
	
	private Long idCliente;
	private Long idEspecie;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public Long getIdEspecie() {
		return idEspecie;
	}

	public void setIdEspecie(Long idEspecie) {
		this.idEspecie = idEspecie;
	}
	
}
