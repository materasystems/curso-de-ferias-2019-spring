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
public class ClienteIntegrationTest {

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
	public void criaClienteComSucesso() {
		ClienteRequestDTO cliente = new ClienteRequestDTO();
		cliente.setNome("João");
		
		Response response = given()
								.port(porta)
								.body(cliente)
								.header("Content-Type", "application/json")
								.post("/petstore/api/v1/clientes")
								.andReturn();
		
		String location = response.getHeader("location");
		
		assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
		assertTrue(location.matches(".*/petstore/api/v1/clientes/[0-9]+"));
	}
	
	@Test
	public void criaClienteSemNomeErro() {
		ClienteRequestDTO cliente = new ClienteRequestDTO(); 
		
		given()
			.port(porta)
			.body(cliente)
			.header("Content-Type", "application/json")
			.post("/petstore/api/v1/clientes")
			.then()
				.statusCode(HttpStatus.BAD_REQUEST.value())
				.body("erros.size()", Matchers.is(1))
				.body("erros[0]", Matchers.equalTo("nome: Nome é de preenchimento obrigatório."));
	}
	
	@Test
	public void criaClienteComNomeExcedendoLimiteErro() {
		ClienteRequestDTO cliente = new ClienteRequestDTO(); 
		cliente.setNome("Testando criação de cliente com nome excedendo limite máximo de 100 caracteres definido no ClienteRequestDTO");
		
		given()
			.port(porta)
			.body(cliente)
			.header("Content-Type", "application/json")
			.post("/petstore/api/v1/clientes")
			.then()
				.statusCode(HttpStatus.BAD_REQUEST.value())
				.body("erros.size()", Matchers.is(1))
				.body("erros[0]", Matchers.equalTo("nome: Nome não pode ultrapassar 100 caracteres."));
	}
	
	@Test
	public void buscaClientes() {
		ClienteRequestDTO cliente1 = new ClienteRequestDTO();
		cliente1.setNome("João");
		
		ClienteRequestDTO cliente2 = new ClienteRequestDTO();
		cliente2.setNome("Maria");
		
		List<ClienteRequestDTO> clientes = Arrays.asList(cliente1, cliente2);
		
		clientes.forEach(cliente -> given()
										.port(porta)
										.body(cliente)
										.header("Content-Type", "application/json")
										.post("/petstore/api/v1/clientes"));
		
		given()
		  .port(porta)
		  .get("/petstore/api/v1/clientes")
		  .then()
		  	.statusCode(HttpStatus.OK.value())
		  .body("size()", Matchers.is(2))
		  .body("nome[0]", Matchers.equalTo(cliente1.getNome()))
		  .body("nome[1]", Matchers.equalTo(cliente2.getNome()));
	}
	
	@Test
	public void buscaClientePorIdSucesso() {
		ClienteRequestDTO cliente = new ClienteRequestDTO();
		cliente.setNome("João");
		
		Response response;
		
		response = given()
					.port(porta)
					.body(cliente)
					.header("Content-Type", "application/json")
					.post("/petstore/api/v1/clientes")
					.andReturn();
		
		assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
		
		ClienteResponseDTO clienteResponse = given()
											  .port(porta)
											  .get(response.getHeader("location"))
											  .then()
											  	.statusCode(HttpStatus.OK.value())
											  .extract()
											  .response()
											  .body()
											  .as(ClienteResponseDTO.class);

		assertEquals(cliente.getNome(), clienteResponse.getNome());
	}
	
	@Test
	public void buscaClientePorIdErro() {
		given()
		  .port(porta)
		  .get("/petstore/api/v1/clientes/1")
		  .then()
		  	.statusCode(HttpStatus.NOT_FOUND.value())
		  	.body("erros.size()", Matchers.is(1))
			.body("erros[0]", Matchers.equalTo("Cliente 1 não encontrado!"));
	}

	@Test
	public void atualizaCliente() {
		ClienteRequestDTO cliente = new ClienteRequestDTO();
		cliente.setNome("João");
		
		Response response;
		
		response = given()
					.port(porta)
					.body(cliente)
					.header("Content-Type", "application/json")
					.post("/petstore/api/v1/clientes")
					.andReturn();
		
		assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
		String location = response.getHeader("location");
		
		cliente.setNome("Pedro");
		
		response = given()
					  .port(porta)
				      .body(cliente)
				      .header("Content-Type", "application/json")
				      .put(location)
					  .andReturn();
		
		assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatusCode());
		
		ClienteResponseDTO clienteResponse = given()
											  .port(porta)
											  .get(location)
											  .then()
											  	.statusCode(HttpStatus.OK.value())
											  .extract()
											  .response()
											  .body()
											  .as(ClienteResponseDTO.class);

		assertEquals(cliente.getNome(), clienteResponse.getNome());
	}
	
	@Test
	public void apagaClienteSucesso() {
		ClienteRequestDTO cliente = new ClienteRequestDTO();
		cliente.setNome("João");
		
		Response response;
		
		response = given()
					.port(porta)
					.body(cliente)
					.header("Content-Type", "application/json")
					.post("/petstore/api/v1/clientes")
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
	public void apagaClienteNaoExistenteErro() {		
		given()
		  .port(porta)
	      .header("Content-Type", "application/json")
	      .delete("/petstore/api/v1/clientes/1")
	      .then()
		  	.statusCode(HttpStatus.NOT_FOUND.value())
		  	.body("erros.size()", Matchers.is(1))
			.body("erros[0]", Matchers.equalTo("Cliente 1 não encontrado!"));
	}
	
	@Test
	public void apagaClienteComPetErro() {
		ClienteRequestDTO cliente = new ClienteRequestDTO();
		cliente.setNome("João");
		
		Response response;
		
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
		
		EspecieRequestDTO especie = new EspecieRequestDTO();
		especie.setDescricao("Cachorro");
		
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
	      .delete(locationCliente)
	      .then()
		  	.statusCode(HttpStatus.BAD_REQUEST.value())
		  	.body("erros.size()", Matchers.is(1))
			.body("erros[0]", Matchers.equalTo(String.format("Cliente %d não pode ser excluído pois possui Pets!", clienteResponse.getId())));
	}
	
	@Test
	public void buscaPetsDoCliente() {
		ClienteRequestDTO cliente = new ClienteRequestDTO();
		cliente.setNome("João");
		
		Response response;
		
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
		
		EspecieRequestDTO especie = new EspecieRequestDTO();
		especie.setDescricao("Cachorro");
		
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
			.get(locationCliente + "/pets")
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