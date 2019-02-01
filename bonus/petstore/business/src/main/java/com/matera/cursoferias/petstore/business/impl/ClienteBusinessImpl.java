package com.matera.cursoferias.petstore.business.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.matera.cursoferias.petstore.business.ClienteBusiness;
import com.matera.cursoferias.petstore.business.PetBusiness;
import com.matera.cursoferias.petstore.domain.entity.Cliente;
import com.matera.cursoferias.petstore.domain.entity.Pet;
import com.matera.cursoferias.petstore.persistence.repository.ClienteRepository;
import com.matera.cursoferias.petstore.util.exception.BusinessException;
import com.matera.cursoferias.petstore.util.exception.RegistroNaoEncontradoException;

@Component
public class ClienteBusinessImpl implements ClienteBusiness {

	private final ClienteRepository clienteRepository;
	private final PetBusiness petBusiness;

	public ClienteBusinessImpl(ClienteRepository clienteRepository, PetBusiness petBusiness) {
        this.clienteRepository = clienteRepository;
        this.petBusiness = petBusiness;
    }

    @Override
	public Cliente save(Cliente entity) {
        return clienteRepository.save(entity);
	}

	@Override
	public List<Cliente> findAll() {
		List<Cliente> clientes = new ArrayList<>();

		clienteRepository.findAll().forEach(cliente -> clientes.add(cliente));

        return clientes;
	}

	@Override
	public Cliente findById(Long id) {
		Cliente cliente = clienteRepository.findById(id).orElse(null);

		if (cliente == null) {
            throw new RegistroNaoEncontradoException(String.format("Cliente %d não encontrado!", id));
        }

		return cliente;
	}

	@Override
	public void deleteById(Long id) {
		findById(id);

		List<Pet> pets = petBusiness.findByCliente_Id(id);

		if (!pets.isEmpty()) {
            throw new BusinessException(String.format("Cliente %d não pode ser excluído pois possui Pets!", id));
        }

		clienteRepository.deleteById(id);
	}
}
