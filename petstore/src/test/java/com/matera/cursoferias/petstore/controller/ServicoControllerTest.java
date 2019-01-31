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
import com.matera.cursoferias.petstore.service.ServicoService;

@RunWith(SpringRunner.class)
@WebMvcTest(ServicoController.class)
public class ServicoControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private ServicoService servicoService;

    private ServicoResponseDTO servico1;
    private ServicoResponseDTO servico2;
    private List<ServicoResponseDTO> servicos;

    @Before
    public void before() {
    	ClienteResponseDTO cliente = new ClienteResponseDTO();
    	cliente.setId(1L);
    	cliente.setNome("João");

    	EspecieResponseDTO especie = new EspecieResponseDTO();
    	especie.setId(1L);
    	especie.setDescricao("Cachorro");

    	PetResponseDTO pet = new PetResponseDTO();
    	pet.setId(1L);
    	pet.setNome("Rex");
    	pet.setDataNascimento(LocalDate.parse("2018-01-01"));
    	pet.setClienteResponseDTO(cliente);
    	pet.setEspecieResponseDTO(especie);

    	servico1 = new ServicoResponseDTO();
    	servico1.setId(1L);
    	servico1.setObservacao("Teste");
    	servico1.setDataHora(LocalDateTime.parse("2018-01-01T16:11:26.485"));
    	servico1.setTipoServico(TipoServico.CONSULTA.getDescricao());
    	servico1.setValor(new BigDecimal(80));
    	servico1.setPetResponseDTO(pet);

    	servico2 = new ServicoResponseDTO();
    	servico2.setId(1L);
    	servico2.setObservacao("Teste 2");
    	servico2.setDataHora(LocalDateTime.parse("2018-01-01T16:11:26.485"));
    	servico2.setTipoServico(TipoServico.VACINACAO.getDescricao());
    	servico2.setValor(new BigDecimal(100));
    	servico2.setPetResponseDTO(pet);

    	servicos = Arrays.asList(servico1, servico2);
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

    	ServicoResponseDTO servicoResponse = new ServicoResponseDTO();
    	servicoResponse.setId(1L);
    	servicoResponse.setObservacao("Teste 2");
    	servicoResponse.setDataHora(LocalDateTime.parse("2018-01-01T16:11:26.485"));
    	servicoResponse.setTipoServico(TipoServico.VACINACAO.getDescricao());
    	servicoResponse.setValor(new BigDecimal(100));
    	servicoResponse.setPetResponseDTO(petResponse);

        Mockito.when(servicoService.save(Mockito.eq(null), Mockito.any())).thenReturn(servicoResponse);

        mvc.perform(post("/api/v1/servicos").contentType(APPLICATION_JSON).content("{ \"observacao\": \"Teste 2\" ,"
																			  	 + "  \"idTipoServico\": \"4\" 	  ,"
																			  	 + "  \"valor\": \"100\"          ,"
																			  	 + "  \"idPet\": \"1\" }"))
           .andExpect(status().isCreated())
           .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void validaFindAll() throws Exception {
        Mockito.when(servicoService.findAll()).thenReturn(servicos);

        mvc.perform(get("/api/v1/servicos").contentType(APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$", hasSize(servicos.size())))
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
           .andExpect(jsonPath("$[0].pet.especie.descricao", equalTo(servico1.getPetResponseDTO().getEspecieResponseDTO().getDescricao())))
           .andExpect(jsonPath("$[1].id", equalTo(servico2.getId().intValue())))
           .andExpect(jsonPath("$[1].observacao", equalTo(servico2.getObservacao())))
           .andExpect(jsonPath("$[1].dataHora", equalTo(servico2.getDataHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))))
           .andExpect(jsonPath("$[1].tipoServico", equalTo(servico2.getTipoServico())))
           .andExpect(jsonPath("$[1].valor", equalTo(servico2.getValor().intValue())))
           .andExpect(jsonPath("$[1].pet.id", equalTo(servico2.getPetResponseDTO().getId().intValue())))
           .andExpect(jsonPath("$[1].pet.nome", equalTo(servico2.getPetResponseDTO().getNome())))
           .andExpect(jsonPath("$[1].pet.dataNascimento", equalTo(servico2.getPetResponseDTO().getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))))
           .andExpect(jsonPath("$[1].pet.cliente.id", equalTo(servico2.getPetResponseDTO().getClienteResponseDTO().getId().intValue())))
           .andExpect(jsonPath("$[1].pet.cliente.nome", equalTo(servico2.getPetResponseDTO().getClienteResponseDTO().getNome())))
           .andExpect(jsonPath("$[1].pet.especie.id", equalTo(servico2.getPetResponseDTO().getEspecieResponseDTO().getId().intValue())))
           .andExpect(jsonPath("$[1].pet.especie.descricao", equalTo(servico2.getPetResponseDTO().getEspecieResponseDTO().getDescricao())));
    }

    @Test
    public void validaFindById() throws Exception {
        Mockito.when(servicoService.findById(servico1.getId())).thenReturn(servico1);

        mvc.perform(get("/api/v1/servicos/" + servico1.getId()).contentType(APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.id", equalTo(servico1.getId().intValue())))
           .andExpect(jsonPath("$.observacao", equalTo(servico1.getObservacao())))
           .andExpect(jsonPath("$.dataHora", equalTo(servico1.getDataHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))))
           .andExpect(jsonPath("$.tipoServico", equalTo(servico1.getTipoServico())))
           .andExpect(jsonPath("$.valor", equalTo(servico1.getValor().intValue())))
           .andExpect(jsonPath("$.pet.id", equalTo(servico1.getPetResponseDTO().getId().intValue())))
           .andExpect(jsonPath("$.pet.nome", equalTo(servico1.getPetResponseDTO().getNome())))
           .andExpect(jsonPath("$.pet.dataNascimento", equalTo(servico1.getPetResponseDTO().getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))))
           .andExpect(jsonPath("$.pet.cliente.id", equalTo(servico1.getPetResponseDTO().getClienteResponseDTO().getId().intValue())))
           .andExpect(jsonPath("$.pet.cliente.nome", equalTo(servico1.getPetResponseDTO().getClienteResponseDTO().getNome())))
           .andExpect(jsonPath("$.pet.especie.id", equalTo(servico1.getPetResponseDTO().getEspecieResponseDTO().getId().intValue())))
           .andExpect(jsonPath("$.pet.especie.descricao", equalTo(servico1.getPetResponseDTO().getEspecieResponseDTO().getDescricao())));
    }

    @Test
    public void validaSaveById() throws Exception {
    	mvc.perform(put("/api/v1/servicos/" + servico1.getId()).contentType(APPLICATION_JSON).content("{ \"observacao\": \"Teste 2\" ,"
																								    + "  \"idTipoServico\": \"4\" 	 ,"
																								  	+ "  \"valor\": \"100\"          ,"
																								  	+ "  \"idPet\": \"1\" }"))
	       .andExpect(status().isNoContent())
	       .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void validaDeleteById() throws Exception {
        mvc.perform(delete("/api/v1/servicos/" + servico1.getId()).contentType(APPLICATION_JSON))
           .andExpect(status().isNoContent())
           .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void validaFindByData() throws Exception {
        Mockito.when(servicoService.findByDataHoraBetween(Mockito.eq(LocalDate.parse("2019-01-01")), Mockito.eq(LocalDate.parse("2019-02-01")))).thenReturn(servicos);

        mvc.perform(get("/api/v1/servicos/buscaPorData?dataInicial=01/01/2019&dataFinal=01/02/2019").contentType(APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$", hasSize(servicos.size())))
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
           .andExpect(jsonPath("$[0].pet.especie.descricao", equalTo(servico1.getPetResponseDTO().getEspecieResponseDTO().getDescricao())))
           .andExpect(jsonPath("$[1].id", equalTo(servico2.getId().intValue())))
           .andExpect(jsonPath("$[1].observacao", equalTo(servico2.getObservacao())))
           .andExpect(jsonPath("$[1].dataHora", equalTo(servico2.getDataHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))))
           .andExpect(jsonPath("$[1].tipoServico", equalTo(servico2.getTipoServico())))
           .andExpect(jsonPath("$[1].valor", equalTo(servico2.getValor().intValue())))
           .andExpect(jsonPath("$[1].pet.id", equalTo(servico2.getPetResponseDTO().getId().intValue())))
           .andExpect(jsonPath("$[1].pet.nome", equalTo(servico2.getPetResponseDTO().getNome())))
           .andExpect(jsonPath("$[1].pet.dataNascimento", equalTo(servico2.getPetResponseDTO().getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))))
           .andExpect(jsonPath("$[1].pet.cliente.id", equalTo(servico2.getPetResponseDTO().getClienteResponseDTO().getId().intValue())))
           .andExpect(jsonPath("$[1].pet.cliente.nome", equalTo(servico2.getPetResponseDTO().getClienteResponseDTO().getNome())))
           .andExpect(jsonPath("$[1].pet.especie.id", equalTo(servico2.getPetResponseDTO().getEspecieResponseDTO().getId().intValue())))
           .andExpect(jsonPath("$[1].pet.especie.descricao", equalTo(servico2.getPetResponseDTO().getEspecieResponseDTO().getDescricao())));
    }

}
