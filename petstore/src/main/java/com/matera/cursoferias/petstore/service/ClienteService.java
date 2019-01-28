package com.matera.cursoferias.petstore.service;

import com.matera.cursoferias.petstore.dto.ClienteRequestDTO;
import com.matera.cursoferias.petstore.dto.ClienteResponseDTO;
import com.matera.cursoferias.petstore.entity.Cliente;

public interface ClienteService extends CrudService<ClienteRequestDTO, ClienteResponseDTO, Cliente, Long> {

}
