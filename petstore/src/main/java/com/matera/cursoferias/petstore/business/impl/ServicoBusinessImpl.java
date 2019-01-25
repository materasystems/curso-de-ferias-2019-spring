package com.matera.cursoferias.petstore.business.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.matera.cursoferias.petstore.business.ServicoBusiness;
import com.matera.cursoferias.petstore.entity.Servico;
import com.matera.cursoferias.petstore.repository.ServicoRepository;

@Component
public class ServicoBusinessImpl implements ServicoBusiness {

	private ServicoRepository servicoRepository;
	
	public ServicoBusinessImpl(ServicoRepository servicoRepository) {
		this.servicoRepository = servicoRepository;
	}

	@Override
	public Servico save(Servico entidade) {
		return servicoRepository.save(entidade);
	}

	@Override
	public List<Servico> findAll() {
		List<Servico> servicos = new ArrayList<>();
		
		servicoRepository.findAll().forEach(servico -> servicos.add(servico));
		
		return servicos;
	}

	@Override
	public Servico findById(Long id) {
		return servicoRepository.findById(id).orElse(null);
	}

	@Override
	public void deleteById(Long id) {
		servicoRepository.deleteById(id);
	}

}
