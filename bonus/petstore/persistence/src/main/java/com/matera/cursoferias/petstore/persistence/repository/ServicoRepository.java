package com.matera.cursoferias.petstore.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.matera.cursoferias.petstore.domain.entity.Servico;

public interface ServicoRepository extends CrudRepository<Servico, Long> {

	List<Servico> findByPet_Id(Long idPet);

	List<Servico> findByDataHoraBetween(LocalDateTime dataInicial, LocalDateTime dataFinal);

}
