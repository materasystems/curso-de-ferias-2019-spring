package com.matera.cursoferias.petstore.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.matera.cursoferias.petstore.dto.ClienteResponseDTO;
import com.matera.cursoferias.petstore.dto.EspecieResponseDTO;
import com.matera.cursoferias.petstore.dto.PetResponseDTO;
import com.matera.cursoferias.petstore.service.ClienteService;
import com.matera.cursoferias.petstore.service.PetService;

@RunWith(SpringRunner.class)
@WebMvcTest(ClienteController.class)
public class ClienteControllerTest {

	@Autowired
	private MockMvc mvc;

    @MockBean
    private ClienteService clienteService;

    @MockBean
    private PetService petService;

    private ClienteResponseDTO cliente1;
    private ClienteResponseDTO cliente2;
    private List<ClienteResponseDTO> clientes;

    @Before
    public void before() {
    	cliente1 = new ClienteResponseDTO();
    	cliente1.setId(1L);
    	cliente1.setNome("João");

    	cliente2 = new ClienteResponseDTO();
    	cliente2.setId(2L);
    	cliente2.setNome("Maria");

    	clientes = Arrays.asList(cliente1, cliente2);
    }

    @Test
    public void validaSave() throws Exception {
    	ClienteResponseDTO clienteResponse = new ClienteResponseDTO();
    	clienteResponse.setId(3L);
    	clienteResponse.setNome("Pedro");

        Mockito.when(clienteService.save(Mockito.eq(null), Mockito.any())).thenReturn(clienteResponse);

        mvc.perform(post("/api/v1/clientes").contentType(APPLICATION_JSON).content("{ \"nome\": \"Pedro\" }"))
           .andExpect(status().isCreated())
           .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void validaFindAll() throws Exception {
        Mockito.when(clienteService.findAll()).thenReturn(clientes);

        mvc.perform(get("/api/v1/clientes").contentType(APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$", hasSize(clientes.size())))
           .andExpect(jsonPath("$[0].id", equalTo(cliente1.getId().intValue())))
           .andExpect(jsonPath("$[0].nome", equalTo(cliente1.getNome())))
           .andExpect(jsonPath("$[1].id", equalTo(cliente2.getId().intValue())))
           .andExpect(jsonPath("$[1].nome", equalTo(cliente2.getNome())));
    }

    @Test
    public void validaFindById() throws Exception {
        Mockito.when(clienteService.findById(cliente1.getId())).thenReturn(cliente1);

        mvc.perform(get("/api/v1/clientes/" + cliente1.getId()).contentType(APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.id", equalTo(cliente1.getId().intValue())))
           .andExpect(jsonPath("$.nome", equalTo(cliente1.getNome())));
    }

    @Test
    public void validaFindPets() throws Exception {
    	EspecieResponseDTO especie = new EspecieResponseDTO();
    	especie.setId(1L);
    	especie.setDescricao("Cachorro");

    	PetResponseDTO pet1 = new PetResponseDTO();
    	pet1.setId(1L);
    	pet1.setNome("Rex");
    	pet1.setClienteResponseDTO(cliente1);
    	pet1.setDataNascimento(LocalDate.parse("2018-01-01"));
		pet1.setEspecieResponseDTO(especie);

    	List<PetResponseDTO> petsCliente1 = Arrays.asList(pet1);

        Mockito.when(petService.findByCliente_Id(cliente1.getId())).thenReturn(petsCliente1);

        mvc.perform(get("/api/v1/clientes/" + cliente1.getId() + "/pets").contentType(APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$", hasSize(petsCliente1.size())))
           .andExpect(jsonPath("$[0].id", equalTo(pet1.getId().intValue())))
           .andExpect(jsonPath("$[0].nome", equalTo(pet1.getNome())))
           .andExpect(jsonPath("$[0].dataNascimento", equalTo(pet1.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))))
           .andExpect(jsonPath("$[0].cliente.id", equalTo(cliente1.getId().intValue())))
           .andExpect(jsonPath("$[0].cliente.nome", equalTo(cliente1.getNome())))
           .andExpect(jsonPath("$[0].especie.id", equalTo(especie.getId().intValue())))
           .andExpect(jsonPath("$[0].especie.descricao", equalTo(especie.getDescricao())));
    }

    @Test
    public void validaSaveById() throws Exception {
        mvc.perform(put("/api/v1/clientes/" + cliente1.getId()).contentType(APPLICATION_JSON).content("{ \"nome\": \"Joãozinho\" }"))
           .andExpect(status().isNoContent())
           .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void validaDeleteById() throws Exception {
        mvc.perform(delete("/api/v1/clientes/" + cliente1.getId()).contentType(APPLICATION_JSON))
           .andExpect(status().isNoContent())
           .andExpect(jsonPath("$").doesNotExist());
    }

}
