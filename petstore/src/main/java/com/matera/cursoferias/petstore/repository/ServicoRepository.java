package com.matera.cursoferias.petstore.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.matera.cursoferias.petstore.entity.Servico;

public interface ServicoRepository extends CrudRepository<Servico, Long> {

	List<Servico> findByPet_Id(Long id);
	
	List<Servico> findByDataHoraBetween(LocalDateTime dataInicial, LocalDateTime dataFinal);
}
