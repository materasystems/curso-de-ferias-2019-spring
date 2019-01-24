package com.matera.cursoferias.petstore.repository;

import org.springframework.data.repository.CrudRepository;

import com.matera.cursoferias.petstore.entity.Pet;

public interface PetRepository extends CrudRepository<Pet, Long> {

}
