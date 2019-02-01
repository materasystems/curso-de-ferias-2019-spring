package com.matera.cursoferias.petstore.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import com.matera.cursoferias.petstore.domain.entity.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente, Long> {

}
