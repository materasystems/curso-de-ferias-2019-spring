package com.matera.cursoferias.petstore.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.matera.cursoferias.petstore.business.PetBusiness;
import com.matera.cursoferias.petstore.dto.PetRequestDTO;
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
	public PetResponseDTO save(Long id, PetRequestDTO requestDTO) {
		Pet pet = converteRequestDTOParaEntidade(id, requestDTO);
		
		pet = petBusiness.save(pet);
		
		return converteEntidadeParaResponseDTO(pet);
	}

	@Override
	public List<PetResponseDTO> findAll() {
		List<Pet> pets = petBusiness.findAll();
		
		return converteListaEntidadeParaListaResponseDTO(pets);
	}

	@Override
	public PetResponseDTO findById(Long id) {
		Pet pet = petBusiness.findById(id);
		
		return converteEntidadeParaResponseDTO(pet);
	}
	
	@Override
	public Pet findEntidadeById(Long id) {
		return petBusiness.findById(id);
	}
	
	@Override
	public void deleteById(Long id) {
		petBusiness.deleteById(id);
	}

	@Override
	public Pet converteRequestDTOParaEntidade(Long id, PetRequestDTO requestDTO) {
		Pet pet = id == null ? new Pet() : petBusiness.findById(id);
		
		pet.setCliente(clienteService.findEntidadeById(requestDTO.getIdCliente()));
		pet.setDataNascimento(requestDTO.getDataNascimento());
		pet.setEspecie(especieService.findEntidadeById(requestDTO.getIdEspecie()));
		pet.setNome(requestDTO.getNome());
		
		return pet;
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

	@Override
	public List<PetResponseDTO> findByEspecie_Id(Long idEspecie) {
		List<Pet> especies = petBusiness.findByEspecie_Id(idEspecie);
		
		return converteListaEntidadeParaListaResponseDTO(especies);
	}
	
	private List<PetResponseDTO> converteListaEntidadeParaListaResponseDTO(List<Pet> pets) {
		List<PetResponseDTO> retorno = new ArrayList<>();
		
		pets.forEach(pet -> retorno.add(converteEntidadeParaResponseDTO(pet)));
		
		return retorno;
	}

}
