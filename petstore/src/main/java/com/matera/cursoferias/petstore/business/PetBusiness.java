package com.matera.cursoferias.petstore.business;

import java.util.List;

import com.matera.cursoferias.petstore.entity.Pet;

public interface PetBusiness extends CrudBusiness<Pet, Long> {

	List<Pet> findByEspecie_Id(Long id);

	List<Pet> findByCliente_Id(Long id);
}
