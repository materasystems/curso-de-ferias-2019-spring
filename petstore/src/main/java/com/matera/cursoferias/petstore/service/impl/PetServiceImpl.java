package com.matera.cursoferias.petstore.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.matera.cursoferias.petstore.business.PetBusiness;
import com.matera.cursoferias.petstore.dto.PetResponseDTO;
import com.matera.cursoferias.petstore.entity.Pet;
import com.matera.cursoferias.petstore.service.ClienteService;
import com.matera.cursoferias.petstore.service.EspecieService;
import com.matera.cursoferias.petstore.service.PetService;

@Service
public class PetServiceImpl implements PetService {

	private PetBusiness petBusiness;
	private ClienteService clienteService;
	private EspecieService especieService;
	
	public PetServiceImpl(PetBusiness petBusiness, ClienteService clienteService, EspecieService especieService) {
		this.petBusiness = petBusiness;
		this.clienteService = clienteService;
		this.especieService = especieService;
	}

	@Override
	public List<PetResponseDTO> findAll() {
		List<Pet> pets = petBusiness.findAll();
		List<PetResponseDTO> retorno = new ArrayList<>();
		
		pets.forEach(pet -> retorno.add(converteEntidadeParaResponseDTO(pet)));
		
		return retorno;
	}

	@Override
	public PetResponseDTO findById(Long id) {
		Pet pet = petBusiness.findById(id);
		
		return converteEntidadeParaResponseDTO(pet);
	}

	@Override
	public PetResponseDTO converteEntidadeParaResponseDTO(Pet entidade) {
		PetResponseDTO petResponseDTO = new PetResponseDTO();
		
		petResponseDTO.setClienteResponseDTO(clienteService.converteEntidadeParaResponseDTO(entidade.getCliente()));
		petResponseDTO.setDataNascimento(entidade.getDataNascimento());
		petResponseDTO.setEspecieResponseDTO(especieService.converteEntidadeParaResponseDTO(entidade.getEspecie()));
		petResponseDTO.setId(entidade.getId());
		petResponseDTO.setNome(entidade.getNome());
		
		return petResponseDTO;
	}

}
