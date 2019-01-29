package com.matera.cursoferias.petstore.service;

import java.time.LocalDate;
import java.util.List;

import com.matera.cursoferias.petstore.dto.ServicoRequestDTO;
import com.matera.cursoferias.petstore.dto.ServicoResponseDTO;
import com.matera.cursoferias.petstore.entity.Servico;

public interface ServicoService extends CrudService<ServicoRequestDTO, ServicoResponseDTO, Servico, Long> {

	List<ServicoResponseDTO> findByPet_Id(Long idPet);
	
	List<ServicoResponseDTO> findByDataHoraBetween(LocalDate dataInicial, LocalDate dataFinal);
}
