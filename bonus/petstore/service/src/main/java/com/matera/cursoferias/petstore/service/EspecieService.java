package com.matera.cursoferias.petstore.service;

import com.matera.cursoferias.petstore.domain.entity.Especie;
import com.matera.cursoferias.petstore.service.dto.EspecieRequestDTO;
import com.matera.cursoferias.petstore.service.dto.EspecieResponseDTO;

public interface EspecieService extends CrudService<EspecieRequestDTO, EspecieResponseDTO, Especie, Long> {

}
