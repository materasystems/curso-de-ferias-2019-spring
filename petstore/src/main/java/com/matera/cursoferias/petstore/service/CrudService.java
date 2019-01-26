package com.matera.cursoferias.petstore.service;

import java.util.List;

import com.matera.cursoferias.petstore.entity.EntidadeBase;

public interface CrudService<ResponseDTO, Entidade extends EntidadeBase, Id> {

	List<ResponseDTO> findAll();
	
	ResponseDTO findById(Id id);
	
	ResponseDTO converteEntidadeParaResponseDTO(Entidade entidade);
	
}
