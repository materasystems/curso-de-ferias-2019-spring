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

import com.matera.cursoferias.petstore.dto.ClienteRequestDTO;
import com.matera.cursoferias.petstore.dto.ClienteResponseDTO;
import com.matera.cursoferias.petstore.service.ClienteService;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {

	private ClienteService clienteService;

	public ClienteController(ClienteService clienteService) {
		this.clienteService = clienteService;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> save(@RequestBody ClienteRequestDTO clienteRequestDTO) {
		ClienteResponseDTO clienteResponseDTO = clienteService.save(null, clienteRequestDTO);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
											 .path("/{id}")
											 .buildAndExpand(clienteResponseDTO.getId())
											 .toUri();
		
		return ResponseEntity.created(uri)
							 .build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody ClienteRequestDTO clienteRequestDTO) {
		clienteService.save(id, clienteRequestDTO);
		
		return ResponseEntity.noContent()
							 .build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<ClienteResponseDTO> findById(@PathVariable("id") Long id) {
		ClienteResponseDTO clienteResponseDTO = clienteService.findById(id);
		
		return ResponseEntity.status(HttpStatus.OK)
							 .body(clienteResponseDTO);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ClienteResponseDTO>> findAll() {
		List<ClienteResponseDTO> clientesResponseDTO = clienteService.findAll();
		
		return ResponseEntity.status(HttpStatus.OK)
							 .body(clientesResponseDTO);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
		clienteService.deleteById(id);
		
		return ResponseEntity.noContent()
							 .build();
	}
	
}
