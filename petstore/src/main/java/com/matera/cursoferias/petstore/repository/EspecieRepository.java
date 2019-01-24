package com.matera.cursoferias.petstore.repository;

import org.springframework.data.repository.CrudRepository;

import com.matera.cursoferias.petstore.entity.Especie;

public interface EspecieRepository extends CrudRepository<Especie, Long> {

}
