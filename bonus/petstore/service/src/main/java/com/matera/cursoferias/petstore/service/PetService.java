package com.matera.cursoferias.petstore.service;

import java.util.List;

import com.matera.cursoferias.petstore.domain.entity.Pet;
import com.matera.cursoferias.petstore.service.dto.PetRequestDTO;
import com.matera.cursoferias.petstore.service.dto.PetResponseDTO;

public interface PetService extends CrudService<PetRequestDTO, PetResponseDTO, Pet, Long> {

	List<PetResponseDTO> findByCliente_Id(Long idCliente);
	
	List<PetResponseDTO> findByEspecie_Id(Long idEspecie);
}
