package com.matera.cursoferias.petstore.aplicacao;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;

import com.matera.cursoferias.petstore.client.ApiClient;
import com.matera.cursoferias.petstore.domain.entity.TipoServico;
import com.matera.cursoferias.petstore.service.dto.ClienteRequestDTO;
import com.matera.cursoferias.petstore.service.dto.ClienteResponseDTO;
import com.matera.cursoferias.petstore.service.dto.EspecieRequestDTO;
import com.matera.cursoferias.petstore.service.dto.EspecieResponseDTO;
import com.matera.cursoferias.petstore.service.dto.PetRequestDTO;
import com.matera.cursoferias.petstore.service.dto.PetResponseDTO;
import com.matera.cursoferias.petstore.service.dto.ServicoRequestDTO;

public class Aplicacao {
	
	public static void main(String[] args) throws ParseException {
		ApiClient apiClient = new ApiClient();
		
		ClienteRequestDTO clienteRequest = new ClienteRequestDTO();
		clienteRequest.setNome("Pedro");
		
		String locationCliente = apiClient.criaCliente(clienteRequest);
		ClienteResponseDTO clienteResponse = apiClient.buscaCliente(locationCliente);
		
		EspecieRequestDTO especieRequest = new EspecieRequestDTO();
		especieRequest.setDescricao("Cachorro");
		
		String locationEspecie = apiClient.criaEspecie(especieRequest);
		EspecieResponseDTO especieResponse = apiClient.buscaEspecie(locationEspecie);
		
		PetRequestDTO petRequest = new PetRequestDTO();
		petRequest.setNome("Rex");
		petRequest.setDataNascimento(LocalDate.parse("2018-01-01"));
		petRequest.setIdCliente(clienteResponse.getId());
		petRequest.setIdEspecie(especieResponse.getId());
		
		String locationPet = apiClient.criaPet(petRequest);
		PetResponseDTO petResponse = apiClient.buscaPet(locationPet);
		
		ServicoRequestDTO servicoRequest = new ServicoRequestDTO();
		servicoRequest.setObservacao("Consulta do Rex");
		servicoRequest.setIdTipoServico(TipoServico.CONSULTA.getId());
		servicoRequest.setIdPet(petResponse.getId());
		servicoRequest.setValor(new BigDecimal(80.00));
		
		apiClient.criaServico(servicoRequest);
		
		System.out.println("Dados criados");
	}
}
