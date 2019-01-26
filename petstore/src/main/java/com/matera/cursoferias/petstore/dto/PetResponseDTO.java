package com.matera.cursoferias.petstore.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PetResponseDTO {

	private Long id;
	private String nome;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataNascimento;
	
	@JsonProperty("cliente")
	private ClienteResponseDTO clienteResponseDTO;
	
	@JsonProperty("especie")
	private EspecieResponseDTO especieResponseDTO;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public ClienteResponseDTO getClienteResponseDTO() {
		return clienteResponseDTO;
	}

	public void setClienteResponseDTO(ClienteResponseDTO clienteResponseDTO) {
		this.clienteResponseDTO = clienteResponseDTO;
	}

	public EspecieResponseDTO getEspecieResponseDTO() {
		return especieResponseDTO;
	}

	public void setEspecieResponseDTO(EspecieResponseDTO especieResponseDTO) {
		this.especieResponseDTO = especieResponseDTO;
	}
	
}
