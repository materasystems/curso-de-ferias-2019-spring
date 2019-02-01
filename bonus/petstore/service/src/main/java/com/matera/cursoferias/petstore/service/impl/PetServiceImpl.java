package com.matera.cursoferias.petstore.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.matera.cursoferias.petstore.business.PetBusiness;
import com.matera.cursoferias.petstore.domain.entity.Pet;
import com.matera.cursoferias.petstore.service.ClienteService;
import com.matera.cursoferias.petstore.service.EspecieService;
import com.matera.cursoferias.petstore.service.PetService;
import com.matera.cursoferias.petstore.service.dto.PetRequestDTO;
import com.matera.cursoferias.petstore.service.dto.PetResponseDTO;

@Service
public class PetServiceImpl implements PetService {

    private final PetBusiness petBusiness;
    private final EspecieService especieService;
    private final ClienteService clienteService;

    public PetServiceImpl(PetBusiness petBusiness, EspecieService especieService, ClienteService clienteService) {
        this.petBusiness = petBusiness;
        this.especieService = especieService;
        this.clienteService = clienteService;
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
        Pet pet = findEntidadeById(id);

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
        Pet pet = id != null ? findEntidadeById(id) : new Pet();

        pet.setDataNascimento(requestDTO.getDataNascimento());
        pet.setEspecie(especieService.findEntidadeById(requestDTO.getIdEspecie()));
        pet.setNome(requestDTO.getNome());
        pet.setCliente(clienteService.findEntidadeById(requestDTO.getIdCliente()));

        return pet;
    }

    @Override
    public PetResponseDTO converteEntidadeParaResponseDTO(Pet entidade) {
        PetResponseDTO petResponseDTO = new PetResponseDTO();

        petResponseDTO.setDataNascimento(entidade.getDataNascimento());
        petResponseDTO.setEspecie(especieService.converteEntidadeParaResponseDTO(entidade.getEspecie()));
        petResponseDTO.setId(entidade.getId());
        petResponseDTO.setNome(entidade.getNome());
        petResponseDTO.setCliente(clienteService.converteEntidadeParaResponseDTO(entidade.getCliente()));

        return petResponseDTO;
    }

	@Override
	public List<PetResponseDTO> findByCliente_Id(Long idCliente) {
		List<Pet> pets = petBusiness.findByCliente_Id(idCliente);
        return converteListaEntidadeParaListaResponseDTO(pets);
	}

	@Override
	public List<PetResponseDTO> findByEspecie_Id(Long idEspecie) {
		List<Pet> pets = petBusiness.findByEspecie_Id(idEspecie);
        return converteListaEntidadeParaListaResponseDTO(pets);
	}

	private List<PetResponseDTO> converteListaEntidadeParaListaResponseDTO(List<Pet> pets) {
		List<PetResponseDTO> retorno = new ArrayList<>();

        pets.forEach(pet -> retorno.add(converteEntidadeParaResponseDTO(pet)));

        return retorno;
	}

}
