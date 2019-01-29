package com.matera.cursoferias.petstore.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.matera.cursoferias.petstore.dto.ServicoRequestDTO;
import com.matera.cursoferias.petstore.dto.ServicoResponseDTO;
import com.matera.cursoferias.petstore.service.ServicoService;

@RestController
@RequestMapping("/api/v1/servicos")
public class ServicoController {

	private ServicoService servicoService;

	public ServicoController(ServicoService servicoService) {
		this.servicoService = servicoService;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> save(@RequestBody ServicoRequestDTO servicoRequestDTO) {
		ServicoResponseDTO servicoResponseDTO = servicoService.save(null, servicoRequestDTO);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
											 .path("/{id}")
											 .buildAndExpand(servicoResponseDTO.getId())
											 .toUri();
		
		return ResponseEntity.created(uri)
							 .build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody ServicoRequestDTO servicoRequestDTO) {
		servicoService.save(id, servicoRequestDTO);
		
		return ResponseEntity.noContent()
							 .build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<ServicoResponseDTO> findById(@PathVariable("id") Long id) {
		ServicoResponseDTO servicoResponseDTO = servicoService.findById(id);
		
		return ResponseEntity.status(HttpStatus.OK)
							 .body(servicoResponseDTO);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ServicoResponseDTO>> findAll() {
		List<ServicoResponseDTO> servicosResponseDTO = servicoService.findAll();
		
		return ResponseEntity.status(HttpStatus.OK)
							 .body(servicosResponseDTO);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
		servicoService.deleteById(id);
		
		return ResponseEntity.noContent()
							 .build();
	}
	
}
