package com.matera.cursoferias.petstore.persistence.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.matera.cursoferias.petstore.domain.entity.Pet;

public interface PetRepository extends CrudRepository<Pet, Long> {

	List<Pet> findByCliente_Id(Long idCliente);
	
	List<Pet> findByEspecie_Id(Long idEspecie);
}
