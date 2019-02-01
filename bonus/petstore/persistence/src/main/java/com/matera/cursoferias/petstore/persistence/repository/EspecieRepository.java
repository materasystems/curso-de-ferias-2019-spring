package com.matera.cursoferias.petstore.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import com.matera.cursoferias.petstore.domain.entity.Especie;

public interface EspecieRepository extends CrudRepository<Especie, Long>{

}
