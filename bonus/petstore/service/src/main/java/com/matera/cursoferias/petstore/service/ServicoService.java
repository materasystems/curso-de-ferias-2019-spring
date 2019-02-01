package com.matera.cursoferias.petstore.service;

import java.time.LocalDate;
import java.util.List;

import com.matera.cursoferias.petstore.domain.entity.Servico;
import com.matera.cursoferias.petstore.service.dto.ServicoRequestDTO;
import com.matera.cursoferias.petstore.service.dto.ServicoResponseDTO;

public interface ServicoService extends CrudService<ServicoRequestDTO, ServicoResponseDTO, Servico, Long> {

	List<ServicoResponseDTO> findByPet_Id(Long idPet);

	List<ServicoResponseDTO> findByDataHoraBetween(LocalDate dataInicial, LocalDate dataFinal);

}
