package com.matera.cursoferias.petstore.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.matera.cursoferias.petstore.business.ClienteBusiness;
import com.matera.cursoferias.petstore.dto.ClienteRequestDTO;
import com.matera.cursoferias.petstore.dto.ClienteResponseDTO;
import com.matera.cursoferias.petstore.entity.Cliente;
import com.matera.cursoferias.petstore.service.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService {

	private ClienteBusiness clienteBusiness;
	
	public ClienteServiceImpl(ClienteBusiness clienteBusiness) {
		this.clienteBusiness = clienteBusiness;
	}
	
	@Override
	public ClienteResponseDTO save(Long id, ClienteRequestDTO requestDTO) {
		Cliente cliente = converteRequestDTOParaEntidade(id, requestDTO);
		
		cliente = clienteBusiness.save(cliente);
		
		return converteEntidadeParaResponseDTO(cliente);
	}

	@Override
	public List<ClienteResponseDTO> findAll() {
		List<Cliente> clientes = clienteBusiness.findAll();
		List<ClienteResponseDTO> retorno = new ArrayList<>();
		
		clientes.forEach(cliente -> retorno.add(converteEntidadeParaResponseDTO(cliente)));
		
		return retorno;
	}

	@Override
	public ClienteResponseDTO findById(Long id) {
		Cliente cliente = clienteBusiness.findById(id);
		
		return converteEntidadeParaResponseDTO(cliente);
	}
	
	@Override
	public Cliente findEntidadeById(Long id) {
		return clienteBusiness.findById(id);
	}
	
	@Override
	public void deleteById(Long id) {
		clienteBusiness.deleteById(id);
	}

	@Override
	public Cliente converteRequestDTOParaEntidade(Long id, ClienteRequestDTO requestDTO) {
		Cliente cliente = id == null ? new Cliente() : clienteBusiness.findById(id);
		
		cliente.setNome(requestDTO.getNome());
		
		return cliente;
	}

	@Override
	public ClienteResponseDTO converteEntidadeParaResponseDTO(Cliente entidade) {
		ClienteResponseDTO clienteResponseDTO = new ClienteResponseDTO();
		
		clienteResponseDTO.setId(entidade.getId());
		clienteResponseDTO.setNome(entidade.getNome());
		
		return clienteResponseDTO;
	}

}
