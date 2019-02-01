package com.matera.cursoferias.petstore.client;

import java.net.URI;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.matera.cursoferias.petstore.service.dto.ClienteRequestDTO;
import com.matera.cursoferias.petstore.service.dto.ClienteResponseDTO;
import com.matera.cursoferias.petstore.service.dto.EspecieRequestDTO;
import com.matera.cursoferias.petstore.service.dto.EspecieResponseDTO;
import com.matera.cursoferias.petstore.service.dto.PetRequestDTO;
import com.matera.cursoferias.petstore.service.dto.PetResponseDTO;
import com.matera.cursoferias.petstore.service.dto.ServicoRequestDTO;

public class ApiClient {

	private RestTemplate restTemplate;
	
	public ApiClient() {
		this.restTemplate = new RestTemplate();
	}
	
	public String criaCliente(ClienteRequestDTO cliente) {
		RequestEntity<ClienteRequestDTO> request = RequestEntity
													.post(URI.create("http://localhost:8080/petstore/api/v1/clientes"))
													.body(cliente);

		ResponseEntity<Void> response = restTemplate.exchange(request, Void.class);
		
		return response.getHeaders().getLocation().toString();
	}
	
	public ClienteResponseDTO buscaCliente(String location) {
		RequestEntity<Void> request = RequestEntity
										.get(URI.create(location))
										.build();
	
		ResponseEntity<ClienteResponseDTO> response = restTemplate.exchange(request, ClienteResponseDTO.class);
		
		return response.getBody();
	}
	
	public String criaEspecie(EspecieRequestDTO especie) {
		RequestEntity<EspecieRequestDTO> request = RequestEntity
													.post(URI.create("http://localhost:8080/petstore/api/v1/especies"))
													.body(especie);

		ResponseEntity<Void> response = restTemplate.exchange(request, Void.class);
		
		return response.getHeaders().getLocation().toString();
	}
	
	public EspecieResponseDTO buscaEspecie(String location) {
		RequestEntity<Void> request = RequestEntity
										.get(URI.create(location))
										.build();
	
		ResponseEntity<EspecieResponseDTO> response = restTemplate.exchange(request, EspecieResponseDTO.class);
		
		return response.getBody();
	}
	
	public String criaPet(PetRequestDTO pet) {
		RequestEntity<PetRequestDTO> request = RequestEntity
												.post(URI.create("http://localhost:8080/petstore/api/v1/pets"))
												.body(pet);

		ResponseEntity<Void> response = restTemplate.exchange(request, Void.class);
		
		return response.getHeaders().getLocation().toString();
	}
	
	public PetResponseDTO buscaPet(String location) {
		RequestEntity<Void> request = RequestEntity
										.get(URI.create(location))
										.build();
	
		ResponseEntity<PetResponseDTO> response = restTemplate.exchange(request, PetResponseDTO.class);
		
		return response.getBody();
	}
	
	public String criaServico(ServicoRequestDTO servico) {
		RequestEntity<ServicoRequestDTO> request = RequestEntity
													.post(URI.create("http://localhost:8080/petstore/api/v1/servicos"))
													.body(servico);

		ResponseEntity<Void> response = restTemplate.exchange(request, Void.class);
		
		return response.getHeaders().getLocation().toString();
	}
}
