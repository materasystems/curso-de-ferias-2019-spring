package com.matera.cursoferias.petstore.business;

import java.util.List;

import com.matera.cursoferias.petstore.domain.entity.Pet;

public interface PetBusiness extends CrudBusiness<Pet, Long>{

	List<Pet> findByCliente_Id(Long idCliente);
	
	List<Pet> findByEspecie_Id(Long idEspecie);
}
