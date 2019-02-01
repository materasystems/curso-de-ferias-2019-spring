package com.matera.cursoferias.petstore.business.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.matera.cursoferias.petstore.business.ServicoBusiness;
import com.matera.cursoferias.petstore.domain.entity.Servico;
import com.matera.cursoferias.petstore.persistence.repository.ServicoRepository;
import com.matera.cursoferias.petstore.util.exception.RegistroNaoEncontradoException;

@Component
public class ServicoBusinessImpl implements ServicoBusiness {

	private final ServicoRepository servicoRepository;

	public ServicoBusinessImpl(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }

    @Override
	public Servico save(Servico entity) {
        return servicoRepository.save(entity);
    }

	@Override
	public List<Servico> findAll() {
        List<Servico> servicos = new ArrayList<>();

        servicoRepository.findAll().forEach(servico -> servicos.add(servico));

        return servicos;
    }

	@Override
	public Servico findById(Long id) {
		Servico servico = servicoRepository.findById(id).orElse(null);

		if (servico == null) {
			throw new RegistroNaoEncontradoException(String.format("Serviço %d não encontrado!", id));
		}

		return servico;
    }

	@Override
	public void deleteById(Long id) {
		findById(id);
		servicoRepository.deleteById(id);
    }

	@Override
	public List<Servico> findByPet_Id(Long idPet) {
		return servicoRepository.findByPet_Id(idPet);
	}

	@Override
	public List<Servico> findByDataHoraBetween(LocalDateTime dataInicial, LocalDateTime dataFinal) {
		return servicoRepository.findByDataHoraBetween(dataInicial, dataFinal);
	}

}
