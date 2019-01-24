package com.matera.cursoferias.petstore.repository;

import org.springframework.data.repository.CrudRepository;

import com.matera.cursoferias.petstore.entity.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente, Long> {

}
