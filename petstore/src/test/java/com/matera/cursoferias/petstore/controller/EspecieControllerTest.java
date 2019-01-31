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
import com.matera.cursoferias.petstore.service.EspecieService;
import com.matera.cursoferias.petstore.service.PetService;

@RunWith(SpringRunner.class)
@WebMvcTest(EspecieController.class)
public class EspecieControllerTest {

	@Autowired
	private MockMvc mvc;

    @MockBean
    private EspecieService especieService;

    @MockBean
    private PetService petService;

    private EspecieResponseDTO especie1;
    private EspecieResponseDTO especie2;
    private List<EspecieResponseDTO> especies;

    @Before
    public void before() {
    	especie1 = new EspecieResponseDTO();
    	especie1.setId(1L);
    	especie1.setDescricao("Cachorro");

    	especie2 = new EspecieResponseDTO();
    	especie2.setId(2L);
    	especie2.setDescricao("Gato");

    	especies = Arrays.asList(especie1, especie2);
    }

    @Test
    public void validaSave() throws Exception {
    	EspecieResponseDTO especieResponse = new EspecieResponseDTO();
    	especieResponse.setId(3L);
    	especieResponse.setDescricao("Coelho");

        Mockito.when(especieService.save(Mockito.eq(null), Mockito.any())).thenReturn(especieResponse);

        mvc.perform(post("/api/v1/especies").contentType(APPLICATION_JSON).content("{ \"descricao\": \"Coelho\" }"))
           .andExpect(status().isCreated())
           .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void validaFindAll() throws Exception {
        Mockito.when(especieService.findAll()).thenReturn(especies);

        mvc.perform(get("/api/v1/especies").contentType(APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$", hasSize(especies.size())))
           .andExpect(jsonPath("$[0].id", equalTo(especie1.getId().intValue())))
           .andExpect(jsonPath("$[0].descricao", equalTo(especie1.getDescricao())))
           .andExpect(jsonPath("$[1].id", equalTo(especie2.getId().intValue())))
           .andExpect(jsonPath("$[1].descricao", equalTo(especie2.getDescricao())));
    }

    @Test
    public void validaFindById() throws Exception {
        Mockito.when(especieService.findById(especie1.getId())).thenReturn(especie1);

        mvc.perform(get("/api/v1/especies/" + especie1.getId()).contentType(APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.id", equalTo(especie1.getId().intValue())))
           .andExpect(jsonPath("$.descricao", equalTo(especie1.getDescricao())));
    }

    @Test
    public void validaFindPets() throws Exception {
    	ClienteResponseDTO cliente = new ClienteResponseDTO();
    	cliente.setId(1L);
    	cliente.setNome("João");

    	PetResponseDTO pet1 = new PetResponseDTO();
    	pet1.setId(1L);
    	pet1.setNome("Rex");
    	pet1.setClienteResponseDTO(cliente);
    	pet1.setDataNascimento(LocalDate.parse("2018-01-01"));
		pet1.setEspecieResponseDTO(especie1);

    	List<PetResponseDTO> petsEspecie1 = Arrays.asList(pet1);

        Mockito.when(petService.findByEspecie_Id(especie1.getId())).thenReturn(petsEspecie1);

        mvc.perform(get("/api/v1/especies/" + especie1.getId() + "/pets").contentType(APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$", hasSize(petsEspecie1.size())))
           .andExpect(jsonPath("$[0].id", equalTo(pet1.getId().intValue())))
           .andExpect(jsonPath("$[0].nome", equalTo(pet1.getNome())))
           .andExpect(jsonPath("$[0].dataNascimento", equalTo(pet1.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))))
           .andExpect(jsonPath("$[0].cliente.id", equalTo(cliente.getId().intValue())))
           .andExpect(jsonPath("$[0].cliente.nome", equalTo(cliente.getNome())))
           .andExpect(jsonPath("$[0].especie.id", equalTo(especie1.getId().intValue())))
           .andExpect(jsonPath("$[0].especie.descricao", equalTo(especie1.getDescricao())));
    }

    @Test
    public void validaSaveById() throws Exception {
        mvc.perform(put("/api/v1/especies/" + especie1.getId()).contentType(APPLICATION_JSON).content("{ \"descricao\": \"Peixe\" }"))
           .andExpect(status().isNoContent())
           .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void validaDeleteById() throws Exception {
        mvc.perform(delete("/api/v1/especies/" + especie1.getId()).contentType(APPLICATION_JSON))
           .andExpect(status().isNoContent())
           .andExpect(jsonPath("$").doesNotExist());
    }

}
