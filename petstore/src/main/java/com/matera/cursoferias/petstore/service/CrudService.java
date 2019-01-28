package com.matera.cursoferias.petstore.service;

import java.util.List;

import com.matera.cursoferias.petstore.entity.EntidadeBase;

public interface CrudService<RequestDTO, ResponseDTO, Entidade extends EntidadeBase, Id> {

	ResponseDTO save(Id id, RequestDTO requestDTO);
	
	List<ResponseDTO> findAll();
	
	ResponseDTO findById(Id id);
	
	void deleteById(Id id);
	
	Entidade converteRequestDTOParaEntidade(Id id, RequestDTO requestDTO);
	
	ResponseDTO converteEntidadeParaResponseDTO(Entidade entidade);
	
}
