package com.matera.cursoferias.petstore.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.matera.cursoferias.petstore.business.ServicoBusiness;
import com.matera.cursoferias.petstore.dto.ServicoRequestDTO;
import com.matera.cursoferias.petstore.dto.ServicoResponseDTO;
import com.matera.cursoferias.petstore.entity.Servico;
import com.matera.cursoferias.petstore.entity.TipoServico;
import com.matera.cursoferias.petstore.service.PetService;
import com.matera.cursoferias.petstore.service.ServicoService;

@Service
public class ServicoServiceImpl implements ServicoService {

	private ServicoBusiness servicoBusiness;
	private PetService petService;

	public ServicoServiceImpl(ServicoBusiness servicoBusiness, PetService petService) {
		this.servicoBusiness = servicoBusiness;
		this.petService = petService;
	}
	
	@Override
	public ServicoResponseDTO save(Long id, ServicoRequestDTO requestDTO) {
		Servico servico = converteRequestDTOParaEntidade(id, requestDTO);
		
		servico = servicoBusiness.save(servico);
		
		return converteEntidadeParaResponseDTO(servico);
	}

	@Override
	public List<ServicoResponseDTO> findAll() {
		List<Servico> servicos = servicoBusiness.findAll();
		List<ServicoResponseDTO> retorno = new ArrayList<>();
		
		servicos.forEach(servico -> retorno.add(converteEntidadeParaResponseDTO(servico)));
		
		return retorno;
	}

	@Override
	public ServicoResponseDTO findById(Long id) {
		Servico servico = servicoBusiness.findById(id);
		
		return converteEntidadeParaResponseDTO(servico);
	}
	
	@Override
	public Servico findEntidadeById(Long id) {
		return servicoBusiness.findById(id);
	}
	
	@Override
	public void deleteById(Long id) {
		servicoBusiness.deleteById(id);
	}

	@Override
	public Servico converteRequestDTOParaEntidade(Long id, ServicoRequestDTO requestDTO) {
		Servico servico = id == null ? new Servico() : servicoBusiness.findById(id);
		
		servico.setDataHora(LocalDateTime.now());
		servico.setObservacao(requestDTO.getObservacao());
		servico.setPet(petService.findEntidadeById(requestDTO.getIdPet()));
		servico.setTipoServico(TipoServico.valueOf(requestDTO.getIdTipoServico()));
		servico.setValor(requestDTO.getValor());
		
		return servico;
	}

	@Override
	public ServicoResponseDTO converteEntidadeParaResponseDTO(Servico entidade) {
		ServicoResponseDTO servicoResponseDTO = new ServicoResponseDTO();
		
		servicoResponseDTO.setDataHora(entidade.getDataHora());
		servicoResponseDTO.setId(entidade.getId());
		servicoResponseDTO.setObservacao(entidade.getObservacao());
		servicoResponseDTO.setPetResponseDTO(petService.converteEntidadeParaResponseDTO(entidade.getPet()));
		servicoResponseDTO.setTipoServico(entidade.getTipoServico().getDescricao());
		servicoResponseDTO.setValor(entidade.getValor());
		
		return servicoResponseDTO;
	}

}
