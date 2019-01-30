package com.matera.cursoferias.petstore.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PetRequestDTO {

	@NotNull(message = "Nome é de preenchimento obrigatório.")
	@Size(max = 100, message = "Nome não pode ultrapassar 100 caracteres.")
	private String nome;
	
	@NotNull(message = "Data de nascimento é de preenchimento obrigatório.")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataNascimento;
	
	@NotNull(message = "Cliente é de preenchimento obrigatório.")
	private Long idCliente;
	
	@NotNull(message = "Espécie é de preenchimento obrigatório.")
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
