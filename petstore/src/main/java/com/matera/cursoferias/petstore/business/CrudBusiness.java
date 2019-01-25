package com.matera.cursoferias.petstore.business;

import java.util.List;

public interface CrudBusiness<Entidade, Id> {

	Entidade save(Entidade entidade);
	
	List<Entidade> findAll();
	
	Entidade findById(Id id);
	
	void deleteById(Id id);

}
