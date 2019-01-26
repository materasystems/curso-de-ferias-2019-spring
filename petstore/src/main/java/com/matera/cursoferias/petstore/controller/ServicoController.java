package com.matera.cursoferias.petstore.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.matera.cursoferias.petstore.dto.ServicoResponseDTO;
import com.matera.cursoferias.petstore.service.ServicoService;

@RestController
@RequestMapping("/api/v1/servicos")
public class ServicoController {

	private ServicoService servicoService;

	public ServicoController(ServicoService servicoService) {
		this.servicoService = servicoService;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<ServicoResponseDTO> findById(@PathVariable("id") Long id) {
		System.out.println("Iniciando API REST (GET) /api/v1/servicos/{id}");
		
		ServicoResponseDTO servicoResponseDTO = servicoService.findById(id);
		
		return ResponseEntity.status(HttpStatus.OK)
							 .body(servicoResponseDTO);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ServicoResponseDTO>> findAll() {
		System.out.println("Iniciando API REST (GET) /api/v1/servicos");
		
		List<ServicoResponseDTO> servicosResponseDTO = servicoService.findAll();
		
		return ResponseEntity.status(HttpStatus.OK)
							 .body(servicosResponseDTO);
	}
	
}
