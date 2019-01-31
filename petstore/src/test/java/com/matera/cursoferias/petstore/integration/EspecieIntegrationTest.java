package com.matera.cursoferias.petstore.integration;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import com.matera.cursoferias.petstore.dto.ClienteRequestDTO;
import com.matera.cursoferias.petstore.dto.ClienteResponseDTO;
import com.matera.cursoferias.petstore.dto.EspecieRequestDTO;
import com.matera.cursoferias.petstore.dto.EspecieResponseDTO;
import com.matera.cursoferias.petstore.dto.PetRequestDTO;
import com.matera.cursoferias.petstore.dto.PetResponseDTO;
import com.matera.cursoferias.petstore.repository.ClienteRepository;
import com.matera.cursoferias.petstore.repository.EspecieRepository;
import com.matera.cursoferias.petstore.repository.PetRepository;
import com.matera.cursoferias.petstore.repository.ServicoRepository;

import io.restassured.response.Response;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EspecieIntegrationTest {

	@LocalServerPort
    private int porta;
	
	@Autowired
	private ServicoRepository servicoRepository;
	
	@Autowired
	private PetRepository petRepository;
	
	@Autowired
	private EspecieRepository especieRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Before
	public void limpaBase() {
		servicoRepository.deleteAll();
		petRepository.deleteAll();
		especieRepository.deleteAll();
		clienteRepository.deleteAll();
	}
	
	@Test
	public void criaEspecieComSucesso() {
		EspecieRequestDTO especie = new EspecieRequestDTO();
		especie.setDescricao("Cachorro");
		
		Response response = given()
								.port(porta)
								.body(especie)
								.header("Content-Type", "application/json")
								.post("/petstore/api/v1/especies")
								.andReturn();
		
		String location = response.getHeader("location");
		
		assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
		assertTrue(location.matches(".*/petstore/api/v1/especies/[0-9]+"));
	}
	
	@Test
	public void criaEspecieSemDescricaoErro() {
		EspecieRequestDTO especie = new EspecieRequestDTO(); 
		
		given()
			.port(porta)
			.body(especie)
			.header("Content-Type", "application/json")
			.post("/petstore/api/v1/especies")
			.then()
				.statusCode(HttpStatus.BAD_REQUEST.value())
				.body("erros.size()", Matchers.is(1))
				.body("erros[0]", Matchers.equalTo("descricao: Descrição é de preenchimento obrigatório."));
	}
	
	@Test
	public void criaEspecieComDescricaoExcedendoLimiteErro() {
		EspecieRequestDTO cliente = new EspecieRequestDTO(); 
		cliente.setDescricao("Testando criação de espécie com descrição excedendo limite máximo de 100 caracteres definido no EspecieRequestDTO");
		
		given()
			.port(porta)
			.body(cliente)
			.header("Content-Type", "application/json")
			.post("/petstore/api/v1/especies")
			.then()
				.statusCode(HttpStatus.BAD_REQUEST.value())
				.body("erros.size()", Matchers.is(1))
				.body("erros[0]", Matchers.equalTo("descricao: Descrição não pode ultrapassar 100 caracteres."));
	}
	
	@Test
	public void buscaEspecies() {
		EspecieRequestDTO especie1 = new EspecieRequestDTO();
		especie1.setDescricao("Cachorro");
		
		EspecieRequestDTO especie2 = new EspecieRequestDTO();
		especie2.setDescricao("Gato");
		
		List<EspecieRequestDTO> especies = Arrays.asList(especie1, especie2);
		
		especies.forEach(especie -> given()
										.port(porta)
										.body(especie)
										.header("Content-Type", "application/json")
										.post("/petstore/api/v1/especies"));
		
		given()
		  .port(porta)
		  .get("/petstore/api/v1/especies")
		  .then()
		  	.statusCode(HttpStatus.OK.value())
		  .body("size()", Matchers.is(2))
		  .body("descricao[0]", Matchers.equalTo(especie1.getDescricao()))
		  .body("descricao[1]", Matchers.equalTo(especie2.getDescricao()));
	}
	
	@Test
	public void buscaEspeciePorIdSucesso() {
		EspecieRequestDTO especie = new EspecieRequestDTO();
		especie.setDescricao("Cachorro");
		
		Response response;
		
		response = given()
					.port(porta)
					.body(especie)
					.header("Content-Type", "application/json")
					.post("/petstore/api/v1/especies")
					.andReturn();
		
		assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
		
		EspecieResponseDTO especieResponse = given()
											  .port(porta)
											  .get(response.getHeader("location"))
											  .then()
											  	.statusCode(HttpStatus.OK.value())
											  .extract()
											  .response()
											  .body()
											  .as(EspecieResponseDTO.class);

		assertEquals(especie.getDescricao(), especieResponse.getDescricao());
	}
	
	@Test
	public void buscaEspeciePorIdErro() {
		given()
		  .port(porta)
		  .get("/petstore/api/v1/especies/1")
		  .then()
		  	.statusCode(HttpStatus.NOT_FOUND.value())
		  	.body("erros.size()", Matchers.is(1))
			.body("erros[0]", Matchers.equalTo("Espécie 1 não encontrada!"));
	}

	@Test
	public void atualizaEspecie() {
		EspecieRequestDTO especie = new EspecieRequestDTO();
		especie.setDescricao("Cachorro");
		
		Response response;
		
		response = given()
					.port(porta)
					.body(especie)
					.header("Content-Type", "application/json")
					.post("/petstore/api/v1/especies")
					.andReturn();
		
		assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
		String location = response.getHeader("location");
		
		especie.setDescricao("Gato");
		
		response = given()
					  .port(porta)
				      .body(especie)
				      .header("Content-Type", "application/json")
				      .put(location)
					  .andReturn();
		
		assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatusCode());
		
		EspecieResponseDTO especieResponse = given()
											  .port(porta)
											  .get(location)
											  .then()
											  	.statusCode(HttpStatus.OK.value())
											  .extract()
											  .response()
											  .body()
											  .as(EspecieResponseDTO.class);

		assertEquals(especie.getDescricao(), especieResponse.getDescricao());
	}
	
	@Test
	public void apagaEspecieSucesso() {
		EspecieRequestDTO especie = new EspecieRequestDTO();
		especie.setDescricao("Cachorro");
		
		Response response;
		
		response = given()
					.port(porta)
					.body(especie)
					.header("Content-Type", "application/json")
					.post("/petstore/api/v1/especies")
					.andReturn();
		
		assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
		String location = response.getHeader("location");
		
		response = given()
					  .port(porta)
				      .header("Content-Type", "application/json")
				      .delete(location)
					  .andReturn();
		
		assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatusCode());
		
		given()
		  .port(porta)
		  .get(location)
		  .then()
		  	.statusCode(HttpStatus.NOT_FOUND.value())
		  	.body("erros.size()", Matchers.is(1));
	}
	
	@Test
	public void apagaEspecieNaoExistenteErro() {		
		given()
		  .port(porta)
	      .header("Content-Type", "application/json")
	      .delete("/petstore/api/v1/especies/1")
	      .then()
		  	.statusCode(HttpStatus.NOT_FOUND.value())
		  	.body("erros.size()", Matchers.is(1))
			.body("erros[0]", Matchers.equalTo("Espécie 1 não encontrada!"));
	}
	
	@Test
	public void apagaEspecieComPetErro() {
		EspecieRequestDTO especie = new EspecieRequestDTO();
		especie.setDescricao("Cachorro");
		
		Response response;
		
		response = given()
					.port(porta)
					.body(especie)
					.header("Content-Type", "application/json")
					.post("/petstore/api/v1/especies")
					.andReturn();
		
		assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
		String locationEspecie = response.getHeader("location");
		
		EspecieResponseDTO especieResponse = given()
											  .port(porta)
											  .get(locationEspecie)
											  .then()
											  	.statusCode(HttpStatus.OK.value())
											  .extract()
											  .response()
											  .body()
											  .as(EspecieResponseDTO.class);
		
		ClienteRequestDTO cliente = new ClienteRequestDTO();
		cliente.setNome("João");
		
		response = given()
					.port(porta)
					.body(cliente)
					.header("Content-Type", "application/json")
					.post("/petstore/api/v1/clientes")
					.andReturn();
	
		assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
		String locationCliente = response.getHeader("location");
		
		ClienteResponseDTO clienteResponse = given()
											  .port(porta)
											  .get(locationCliente)
											  .then()
											  	.statusCode(HttpStatus.OK.value())
											  .extract()
											  .response()
											  .body()
											  .as(ClienteResponseDTO.class);
		
		PetRequestDTO pet = new PetRequestDTO();
		pet.setNome("Rex");
		pet.setDataNascimento(LocalDate.parse("2018-01-01"));
		pet.setIdCliente(clienteResponse.getId());
		pet.setIdEspecie(especieResponse.getId());
		
		response = given()
					.port(porta)
					.body(pet)
					.header("Content-Type", "application/json")
					.post("/petstore/api/v1/pets")
					.andReturn();
		
		assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
		
		given()
		  .port(porta)
	      .header("Content-Type", "application/json")
	      .delete(locationEspecie)
	      .then()
		  	.statusCode(HttpStatus.BAD_REQUEST.value())
		  	.body("erros.size()", Matchers.is(1))
			.body("erros[0]", Matchers.equalTo(String.format("Espécie %d não pode ser excluída pois possui Pets!", especieResponse.getId())));
	}
	
	@Test
	public void buscaPetsDaEspecie() {
		EspecieRequestDTO especie = new EspecieRequestDTO();
		especie.setDescricao("Cachorro");
		
		Response response;
		
		response = given()
					.port(porta)
					.body(especie)
					.header("Content-Type", "application/json")
					.post("/petstore/api/v1/especies")
					.andReturn();
		
		assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
		String locationEspecie = response.getHeader("location");
		
		EspecieResponseDTO especieResponse = given()
											  .port(porta)
											  .get(locationEspecie)
											  .then()
											  	.statusCode(HttpStatus.OK.value())
											  .extract()
											  .response()
											  .body()
											  .as(EspecieResponseDTO.class);
		
		ClienteRequestDTO cliente = new ClienteRequestDTO();
		cliente.setNome("João");
		
		response = given()
					.port(porta)
					.body(cliente)
					.header("Content-Type", "application/json")
					.post("/petstore/api/v1/clientes")
					.andReturn();
	
		assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
		String locationCliente = response.getHeader("location");
		
		ClienteResponseDTO clienteResponse = given()
											  .port(porta)
											  .get(locationCliente)
											  .then()
											  	.statusCode(HttpStatus.OK.value())
											  .extract()
											  .response()
											  .body()
											  .as(ClienteResponseDTO.class);
		
		PetRequestDTO pet = new PetRequestDTO();
		pet.setNome("Rex");
		pet.setDataNascimento(LocalDate.parse("2018-01-01"));
		pet.setIdCliente(clienteResponse.getId());
		pet.setIdEspecie(especieResponse.getId());
		
		response = given()
					.port(porta)
					.body(pet)
					.header("Content-Type", "application/json")
					.post("/petstore/api/v1/pets")
					.andReturn();
		
		assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
		
		PetResponseDTO petResponse = given()
									  .port(porta)
									  .get(response.getHeader("location"))
									  .then()
									  	.statusCode(HttpStatus.OK.value())
									  .extract()
									  .response()
									  .body()
									  .as(PetResponseDTO.class);
		
		given()
			.port(porta)
			.get(locationEspecie + "/pets")
			.then()
				.statusCode(HttpStatus.OK.value())
			.body("size()", Matchers.is(1))
			.body("id[0]", Matchers.equalTo(petResponse.getId().intValue()))
			.body("nome[0]", Matchers.equalTo(pet.getNome()))
			.body("dataNascimento[0]", Matchers.equalTo(pet.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))))
			.body("cliente[0].id", Matchers.equalTo(clienteResponse.getId().intValue()))
			.body("cliente[0].nome", Matchers.equalTo(cliente.getNome()))
			.body("especie[0].id", Matchers.equalTo(especieResponse.getId().intValue()))
			.body("especie[0].descricao", Matchers.equalTo(especie.getDescricao()));
	}
}