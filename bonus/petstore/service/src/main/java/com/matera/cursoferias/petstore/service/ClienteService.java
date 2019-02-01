package com.matera.cursoferias.petstore.service;

import com.matera.cursoferias.petstore.domain.entity.Cliente;
import com.matera.cursoferias.petstore.service.dto.ClienteRequestDTO;
import com.matera.cursoferias.petstore.service.dto.ClienteResponseDTO;

public interface ClienteService extends CrudService<ClienteRequestDTO, ClienteResponseDTO, Cliente, Long> {

}
