package com.matera.cursoferias.petstore.business;

import java.time.LocalDateTime;
import java.util.List;

import com.matera.cursoferias.petstore.entity.Servico;

public interface ServicoBusiness extends CrudBusiness<Servico, Long> {

	List<Servico> findByPet_Id(Long id);
	
	List<Servico> findByDataHoraBetween(LocalDateTime dataInicial, LocalDateTime dataFinal);
}
