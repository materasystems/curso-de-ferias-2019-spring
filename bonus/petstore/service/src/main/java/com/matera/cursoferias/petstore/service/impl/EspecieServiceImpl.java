package com.matera.cursoferias.petstore.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.matera.cursoferias.petstore.business.EspecieBusiness;
import com.matera.cursoferias.petstore.domain.entity.Especie;
import com.matera.cursoferias.petstore.service.EspecieService;
import com.matera.cursoferias.petstore.service.dto.EspecieRequestDTO;
import com.matera.cursoferias.petstore.service.dto.EspecieResponseDTO;

@Service
public class EspecieServiceImpl implements EspecieService {

    private final EspecieBusiness especieBusiness;

    public EspecieServiceImpl(EspecieBusiness especieBusiness) {
        this.especieBusiness = especieBusiness;
    }

    @Override
    public EspecieResponseDTO save(Long id, EspecieRequestDTO requestDTO) {
        Especie especie = converteRequestDTOParaEntidade(id, requestDTO);

        especie = especieBusiness.save(especie);

        return converteEntidadeParaResponseDTO(especie);
    }

    @Override
    public List<EspecieResponseDTO> findAll() {
        List<Especie> especies = especieBusiness.findAll();
        List<EspecieResponseDTO> retorno = new ArrayList<>();

        especies.forEach(especie -> retorno.add(converteEntidadeParaResponseDTO(especie)));

        return retorno;
    }

    @Override
    public EspecieResponseDTO findById(Long id) {
        Especie especie = findEntidadeById(id);

        return converteEntidadeParaResponseDTO(especie);
    }

    @Override
    public Especie findEntidadeById(Long id) {
        return especieBusiness.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        especieBusiness.deleteById(id);
    }

    @Override
    public Especie converteRequestDTOParaEntidade(Long id, EspecieRequestDTO requestDTO) {
        Especie especie = id != null ? findEntidadeById(id) : new Especie();

        especie.setDescricao(requestDTO.getDescricao());

        return especie;
    }

    @Override
    public EspecieResponseDTO converteEntidadeParaResponseDTO(Especie entidade) {
        EspecieResponseDTO especieResponseDTO = new EspecieResponseDTO();

        especieResponseDTO.setId(entidade.getId());
        especieResponseDTO.setDescricao(entidade.getDescricao());

        return especieResponseDTO;
    }

}
