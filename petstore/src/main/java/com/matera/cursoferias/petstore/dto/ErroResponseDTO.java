package com.matera.cursoferias.petstore.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.FieldError;

public class ErroResponseDTO {

	private List<String> erros;

	public static ErroResponseDTO buildError(List<String> listaErros) {
		ErroResponseDTO erroResponseDTO = new ErroResponseDTO();
		erroResponseDTO.setErros(listaErros);
		
		return erroResponseDTO;
	}
	
	public static ErroResponseDTO withError(String erro) {
		List<String> listaErros = new ArrayList<>();
		listaErros.add(erro);
		
		return buildError(listaErros);
	}
	
	public static ErroResponseDTO withError(List<FieldError> erros) {
		List<String> listaErros = new ArrayList<>();
		erros.forEach(erro -> listaErros.add(String.format("%s: %s", erro.getField(), erro.getDefaultMessage())));
		
		return buildError(listaErros);
	}
	
	public List<String> getErros() {
		return erros;
	}

	public void setErros(List<String> erros) {
		this.erros = erros;
	}
	
}
