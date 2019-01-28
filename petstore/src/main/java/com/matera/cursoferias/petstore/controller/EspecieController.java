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

import com.matera.cursoferias.petstore.dto.EspecieRequestDTO;
import com.matera.cursoferias.petstore.dto.EspecieResponseDTO;
import com.matera.cursoferias.petstore.service.EspecieService;

@RestController
@RequestMapping("/api/v1/especies")
public class EspecieController {

	private EspecieService especieService;

	public EspecieController(EspecieService especieService) {
		this.especieService = especieService;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> save(@RequestBody EspecieRequestDTO especieRequestDTO) {
		EspecieResponseDTO especieResponseDTO = especieService.save(null, especieRequestDTO);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
											 .path("/{id}")
											 .buildAndExpand(especieResponseDTO.getId())
											 .toUri();
		
		return ResponseEntity.created(uri)
							 .build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody EspecieRequestDTO especieRequestDTO) {
		especieService.save(id, especieRequestDTO);
		
		return ResponseEntity.noContent()
							 .build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<EspecieResponseDTO> findById(@PathVariable("id") Long id) {
		System.out.println("Iniciando API REST (GET) /api/v1/especies/{id}");
		
		EspecieResponseDTO especieResponseDTO = especieService.findById(id);
		
		return ResponseEntity.status(HttpStatus.OK)
							 .body(especieResponseDTO);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<EspecieResponseDTO>> findAll() {
		System.out.println("Iniciando API REST (GET) /api/v1/especies");
		
		List<EspecieResponseDTO> especiesResponseDTO = especieService.findAll();
		
		return ResponseEntity.status(HttpStatus.OK)
							 .body(especiesResponseDTO);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
		especieService.deleteById(id);
		
		return ResponseEntity.noContent()
							 .build();
	}
	
}
