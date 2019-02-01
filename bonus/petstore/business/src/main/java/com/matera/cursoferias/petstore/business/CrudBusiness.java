package com.matera.cursoferias.petstore.business;

import java.util.List;

import com.matera.cursoferias.petstore.domain.entity.EntidadeBase;

public interface CrudBusiness<Entidade extends EntidadeBase, Id> {

	Entidade save(Entidade entidade);

	List<Entidade> findAll();

	Entidade findById(Id id);

	void deleteById(Id id);

}
