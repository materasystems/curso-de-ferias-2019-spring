package com.matera.cursoferias.petstore.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.matera.cursoferias.petstore.business.EspecieBusiness;
import com.matera.cursoferias.petstore.dto.EspecieResponseDTO;
import com.matera.cursoferias.petstore.entity.Especie;
import com.matera.cursoferias.petstore.service.EspecieService;

@Service
public class EspecieServiceImpl implements EspecieService {

	private EspecieBusiness especieBusiness;
	
	public EspecieServiceImpl(EspecieBusiness especieBusiness) {
		this.especieBusiness = especieBusiness;
	}

	@Override
	public List<EspecieResponseDTO> findAll() {
		List<Especie> especies = especieBusiness.findAll();
		List<EspecieResponseDTO> retorno = new ArrayList<>();
		
		especies.forEach(especie -> retorno.add(converteEntidadeParaResponseDTO(especie)));
		
		return retorno;
	}

	@Override
	public EspecieResponseDTO findById(Long id) {
		Especie especie = especieBusiness.findById(id);
		
		return converteEntidadeParaResponseDTO(especie);
	}

	@Override
	public EspecieResponseDTO converteEntidadeParaResponseDTO(Especie entidade) {
		EspecieResponseDTO especieResponseDTO = new EspecieResponseDTO();
		
		especieResponseDTO.setDescricao(entidade.getDescricao());
		especieResponseDTO.setId(entidade.getId());
		
		return especieResponseDTO;
	}

}
