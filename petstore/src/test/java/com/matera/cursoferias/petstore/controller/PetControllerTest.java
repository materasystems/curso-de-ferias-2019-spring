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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import com.matera.cursoferias.petstore.dto.ServicoResponseDTO;
import com.matera.cursoferias.petstore.entity.TipoServico;
import com.matera.cursoferias.petstore.service.PetService;
import com.matera.cursoferias.petstore.service.ServicoService;

@RunWith(SpringRunner.class)
@WebMvcTest(PetController.class)
public class PetControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private PetService petService;

	@MockBean
	private ServicoService servicoService;

    private PetResponseDTO pet1;
    private PetResponseDTO pet2;
    private List<PetResponseDTO> pets;

    @Before
    public void before() {
    	ClienteResponseDTO cliente = new ClienteResponseDTO();
    	cliente.setId(1L);
    	cliente.setNome("João");

    	EspecieResponseDTO especie = new EspecieResponseDTO();
    	especie.setId(1L);
    	especie.setDescricao("Cachorro");

    	pet1 = new PetResponseDTO();
    	pet1.setId(1L);
    	pet1.setNome("Rex");
    	pet1.setDataNascimento(LocalDate.parse("2018-01-01"));
    	pet1.setClienteResponseDTO(cliente);
    	pet1.setEspecieResponseDTO(especie);

    	pet2 = new PetResponseDTO();
    	pet2.setId(1L);
    	pet2.setNome("Totó");
    	pet2.setDataNascimento(LocalDate.parse("2018-01-01"));
    	pet2.setClienteResponseDTO(cliente);
    	pet2.setEspecieResponseDTO(especie);

    	pets = Arrays.asList(pet1, pet2);
    }

    @Test
    public void validaSave() throws Exception {
    	ClienteResponseDTO cliente = new ClienteResponseDTO();
    	cliente.setId(1L);
    	cliente.setNome("João");

    	EspecieResponseDTO especie = new EspecieResponseDTO();
    	especie.setId(1L);
    	especie.setDescricao("Cachorro");

    	PetResponseDTO petResponse = new PetResponseDTO();
    	petResponse = new PetResponseDTO();
    	petResponse.setId(1L);
    	petResponse.setNome("Nina");
    	petResponse.setDataNascimento(LocalDate.parse("2018-01-01"));
    	petResponse.setClienteResponseDTO(cliente);
    	petResponse.setEspecieResponseDTO(especie);

        Mockito.when(petService.save(Mockito.eq(null), Mockito.any())).thenReturn(petResponse);

        mvc.perform(post("/api/v1/pets").contentType(APPLICATION_JSON).content("{ \"nome\": \"Nina\"                 ,"
																		  	 + "  \"dataNascimento\": \"01/01/2018\" ,"
																		  	 + "  \"idCliente\": \"1\"               ,"
																		  	 + "  \"idEspecie\": \"1\" }"))
           .andExpect(status().isCreated())
           .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void validaFindAll() throws Exception {
        Mockito.when(petService.findAll()).thenReturn(pets);

        mvc.perform(get("/api/v1/pets").contentType(APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$", hasSize(pets.size())))
           .andExpect(jsonPath("$[0].id", equalTo(pet1.getId().intValue())))
           .andExpect(jsonPath("$[0].nome", equalTo(pet1.getNome())))
           .andExpect(jsonPath("$[0].dataNascimento", equalTo(pet1.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))))
           .andExpect(jsonPath("$[0].cliente.id", equalTo(pet1.getClienteResponseDTO().getId().intValue())))
           .andExpect(jsonPath("$[0].cliente.nome", equalTo(pet1.getClienteResponseDTO().getNome())))
           .andExpect(jsonPath("$[0].especie.id", equalTo(pet1.getEspecieResponseDTO().getId().intValue())))
           .andExpect(jsonPath("$[0].especie.descricao", equalTo(pet1.getEspecieResponseDTO().getDescricao())))
           .andExpect(jsonPath("$[1].id", equalTo(pet2.getId().intValue())))
           .andExpect(jsonPath("$[1].nome", equalTo(pet2.getNome())))
           .andExpect(jsonPath("$[1].dataNascimento", equalTo(pet2.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))))
           .andExpect(jsonPath("$[1].cliente.id", equalTo(pet2.getClienteResponseDTO().getId().intValue())))
           .andExpect(jsonPath("$[1].cliente.nome", equalTo(pet2.getClienteResponseDTO().getNome())))
           .andExpect(jsonPath("$[1].especie.id", equalTo(pet2.getEspecieResponseDTO().getId().intValue())))
           .andExpect(jsonPath("$[1].especie.descricao", equalTo(pet2.getEspecieResponseDTO().getDescricao())));
    }

    @Test
    public void validaFindById() throws Exception {
        Mockito.when(petService.findById(pet1.getId())).thenReturn(pet1);

        mvc.perform(get("/api/v1/pets/" + pet1.getId()).contentType(APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.id", equalTo(pet1.getId().intValue())))
           .andExpect(jsonPath("$.nome", equalTo(pet1.getNome())))
           .andExpect(jsonPath("$.dataNascimento", equalTo(pet1.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))))
           .andExpect(jsonPath("$.cliente.id", equalTo(pet1.getClienteResponseDTO().getId().intValue())))
           .andExpect(jsonPath("$.cliente.nome", equalTo(pet1.getClienteResponseDTO().getNome())))
           .andExpect(jsonPath("$.especie.id", equalTo(pet1.getEspecieResponseDTO().getId().intValue())))
           .andExpect(jsonPath("$.especie.descricao", equalTo(pet1.getEspecieResponseDTO().getDescricao())));
    }

    @Test
    public void validaFindServicos() throws Exception {
    	ServicoResponseDTO servico1 = new ServicoResponseDTO();
    	servico1.setId(1L);
    	servico1.setObservacao("Teste");
    	servico1.setDataHora(LocalDateTime.parse("2018-01-01T16:11:26.485"));
    	servico1.setTipoServico(TipoServico.CONSULTA.getDescricao());
    	servico1.setValor(new BigDecimal(80));
    	servico1.setPetResponseDTO(pet1);

    	List<ServicoResponseDTO> servicosPet1 = Arrays.asList(servico1);

        Mockito.when(servicoService.findByPet_Id(servico1.getId())).thenReturn(servicosPet1);

        mvc.perform(get("/api/v1/pets/" + pet1.getId() + "/servicos").contentType(APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$", hasSize(servicosPet1.size())))
           .andExpect(jsonPath("$[0].id", equalTo(servico1.getId().intValue())))
           .andExpect(jsonPath("$[0].observacao", equalTo(servico1.getObservacao())))
           .andExpect(jsonPath("$[0].dataHora", equalTo(servico1.getDataHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))))
           .andExpect(jsonPath("$[0].tipoServico", equalTo(servico1.getTipoServico())))
           .andExpect(jsonPath("$[0].valor", equalTo(servico1.getValor().intValue())))
           .andExpect(jsonPath("$[0].pet.id", equalTo(servico1.getPetResponseDTO().getId().intValue())))
           .andExpect(jsonPath("$[0].pet.nome", equalTo(servico1.getPetResponseDTO().getNome())))
           .andExpect(jsonPath("$[0].pet.dataNascimento", equalTo(servico1.getPetResponseDTO().getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))))
           .andExpect(jsonPath("$[0].pet.cliente.id", equalTo(servico1.getPetResponseDTO().getClienteResponseDTO().getId().intValue())))
           .andExpect(jsonPath("$[0].pet.cliente.nome", equalTo(servico1.getPetResponseDTO().getClienteResponseDTO().getNome())))
           .andExpect(jsonPath("$[0].pet.especie.id", equalTo(servico1.getPetResponseDTO().getEspecieResponseDTO().getId().intValue())))
           .andExpect(jsonPath("$[0].pet.especie.descricao", equalTo(servico1.getPetResponseDTO().getEspecieResponseDTO().getDescricao())));
    }

    @Test
    public void validaSaveById() throws Exception {
    	mvc.perform(put("/api/v1/pets/" + pet1.getId()).contentType(APPLICATION_JSON).content("{ \"nome\": \"Bidu\"                 ,"
																					  	    + "  \"dataNascimento\": \"01/01/2018\" ,"
																					  	    + "  \"idCliente\": \"1\"               ,"
																					  	    + "  \"idEspecie\": \"1\" }"))
	       .andExpect(status().isNoContent())
	       .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void validaDeleteById() throws Exception {
        mvc.perform(delete("/api/v1/pets/" + pet1.getId()).contentType(APPLICATION_JSON))
           .andExpect(status().isNoContent())
           .andExpect(jsonPath("$").doesNotExist());
    }

}
