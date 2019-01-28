package com.matera.cursoferias.petstore.service;

import com.matera.cursoferias.petstore.dto.EspecieRequestDTO;
import com.matera.cursoferias.petstore.dto.EspecieResponseDTO;
import com.matera.cursoferias.petstore.entity.Especie;

public interface EspecieService extends CrudService<EspecieRequestDTO, EspecieResponseDTO, Especie, Long> {

}
