package com.matera.cursoferias.petstore.business.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.matera.cursoferias.petstore.business.ClienteBusiness;
import com.matera.cursoferias.petstore.entity.Cliente;
import com.matera.cursoferias.petstore.repository.ClienteRepository;

@Component
public class ClienteBusinessImpl implements ClienteBusiness {

	private ClienteRepository clienteRepository;

	public ClienteBusinessImpl(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}

	@Override
	public Cliente save(Cliente entidade) {
		return clienteRepository.save(entidade);
	}

	@Override
	public List<Cliente> findAll() {
		List<Cliente> clientes = new ArrayList<>();
		
		clienteRepository.findAll().forEach(cliente -> clientes.add(cliente));
		
		return clientes;
	}

	@Override
	public Cliente findById(Long id) {
		return clienteRepository.findById(id).orElse(null);
	}

	@Override
	public void deleteById(Long id) {
		clienteRepository.deleteById(id);
	}

}
