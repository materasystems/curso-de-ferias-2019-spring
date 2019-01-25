package com.matera.cursoferias.petstore.business.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.matera.cursoferias.petstore.business.EspecieBusiness;
import com.matera.cursoferias.petstore.entity.Especie;
import com.matera.cursoferias.petstore.repository.EspecieRepository;

@Component
public class EspecieBusinessImpl implements EspecieBusiness {

	private EspecieRepository especieRepository;

	public EspecieBusinessImpl(EspecieRepository especieRepository) {
		this.especieRepository = especieRepository;
	}

	@Override
	public Especie save(Especie entidade) {
		return especieRepository.save(entidade);
	}

	@Override
	public List<Especie> findAll() {
		List<Especie> especies = new ArrayList<>();
		
		especieRepository.findAll().forEach(especie -> especies.add(especie));
		
		return especies;
	}

	@Override
	public Especie findById(Long id) {
		return especieRepository.findById(id).orElse(null);
	}

	@Override
	public void deleteById(Long id) {
		especieRepository.deleteById(id);
	}

}
