package com.matera.cursoferias.petstore.business.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.matera.cursoferias.petstore.business.EspecieBusiness;
import com.matera.cursoferias.petstore.business.PetBusiness;
import com.matera.cursoferias.petstore.entity.Especie;
import com.matera.cursoferias.petstore.entity.Pet;
import com.matera.cursoferias.petstore.exception.BusinessException;
import com.matera.cursoferias.petstore.exception.RegistroNaoEncontradoException;
import com.matera.cursoferias.petstore.repository.EspecieRepository;

@Component
public class EspecieBusinessImpl implements EspecieBusiness {

	private EspecieRepository especieRepository;
	private PetBusiness petBusiness;

	public EspecieBusinessImpl(EspecieRepository especieRepository, PetBusiness petBusiness) {
		this.especieRepository = especieRepository;
		this.petBusiness = petBusiness;
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
		Especie especie = especieRepository.findById(id).orElse(null);
		
		if (especie == null) {
			throw new RegistroNaoEncontradoException(String.format("Espécie %d não encontrada", id));
		}
		
		return especie; 
	}

	@Override
	public void deleteById(Long id) {
		findById(id);
		
		List<Pet> pets = petBusiness.findByEspecie_Id(id);
		
		if (!pets.isEmpty()) {
			throw new BusinessException(String.format("Espécie %d não pode ser excluída pois possui Pets!", id));
		}
		
		especieRepository.deleteById(id);
	}

}
