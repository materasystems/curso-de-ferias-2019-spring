package com.matera.cursoferias.petstore.business.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.matera.cursoferias.petstore.business.PetBusiness;
import com.matera.cursoferias.petstore.business.ServicoBusiness;
import com.matera.cursoferias.petstore.domain.entity.Pet;
import com.matera.cursoferias.petstore.domain.entity.Servico;
import com.matera.cursoferias.petstore.persistence.repository.PetRepository;
import com.matera.cursoferias.petstore.util.exception.BusinessException;
import com.matera.cursoferias.petstore.util.exception.RegistroNaoEncontradoException;

@Component
public class PetBusinessImpl implements PetBusiness {

	private final PetRepository petRepository;
	private final ServicoBusiness servicoBusiness;

	public PetBusinessImpl(PetRepository petRepository, ServicoBusiness servicoBusiness) {
        this.petRepository = petRepository;
        this.servicoBusiness = servicoBusiness;
    }

    @Override
	public Pet save(Pet entity) {
        return petRepository.save(entity);
	}

	@Override
	public List<Pet> findAll() {
		List<Pet> pets = new ArrayList<>();

		petRepository.findAll().forEach(pet -> pets.add(pet));

        return pets;
	}

	@Override
	public Pet findById(Long id) {
		Pet pet = petRepository.findById(id).orElse(null);

		if (pet == null) {
            throw new RegistroNaoEncontradoException(String.format("Pet %d não encontrado!", id));
        }

		return pet;
	}

	@Override
	public void deleteById(Long id) {
		findById(id);

		List<Servico> servicos = servicoBusiness.findByPet_Id(id);

		if (!servicos.isEmpty()) {
            throw new BusinessException(String.format("Pet %d não pode ser excluído pois possui Serviços!", id));
        }

		petRepository.deleteById(id);
	}

	@Override
	public List<Pet> findByCliente_Id(Long idCliente) {
		return petRepository.findByCliente_Id(idCliente);
	}

	@Override
	public List<Pet> findByEspecie_Id(Long idEspecie) {
		return petRepository.findByEspecie_Id(idEspecie);
	}
}
