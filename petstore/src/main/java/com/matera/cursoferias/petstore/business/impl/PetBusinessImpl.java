package com.matera.cursoferias.petstore.business.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.matera.cursoferias.petstore.business.PetBusiness;
import com.matera.cursoferias.petstore.entity.Pet;
import com.matera.cursoferias.petstore.repository.PetRepository;

@Component
public class PetBusinessImpl implements PetBusiness {

	private PetRepository petRepository;
	
	public PetBusinessImpl(PetRepository petRepository) {
		this.petRepository = petRepository;
	}

	@Override
	public Pet save(Pet entidade) {
		return petRepository.save(entidade);
	}

	@Override
	public List<Pet> findAll() {
		List<Pet> pets = new ArrayList<>();
		
		petRepository.findAll().forEach(pet -> pets.add(pet));
		
		return pets;
	}

	@Override
	public Pet findById(Long id) {
		return petRepository.findById(id).orElse(null);
	}

	@Override
	public void deleteById(Long id) {
		petRepository.deleteById(id);
	}

	@Override
	public List<Pet> findByEspecie_Id(Long id) {
		return petRepository.findByEspecie_Id(id);
	}

}
