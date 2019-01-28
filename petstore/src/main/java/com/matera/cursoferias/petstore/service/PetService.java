package com.matera.cursoferias.petstore.service;

import com.matera.cursoferias.petstore.dto.PetRequestDTO;
import com.matera.cursoferias.petstore.dto.PetResponseDTO;
import com.matera.cursoferias.petstore.entity.Pet;

public interface PetService extends CrudService<PetRequestDTO, PetResponseDTO, Pet, Long> {

}
