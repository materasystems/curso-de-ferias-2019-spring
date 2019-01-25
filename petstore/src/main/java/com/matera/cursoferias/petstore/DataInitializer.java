package com.matera.cursoferias.petstore;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.matera.cursoferias.petstore.business.ClienteBusiness;
import com.matera.cursoferias.petstore.business.EspecieBusiness;
import com.matera.cursoferias.petstore.business.PetBusiness;
import com.matera.cursoferias.petstore.business.ServicoBusiness;
import com.matera.cursoferias.petstore.entity.Cliente;
import com.matera.cursoferias.petstore.entity.Especie;
import com.matera.cursoferias.petstore.entity.Pet;
import com.matera.cursoferias.petstore.entity.Servico;
import com.matera.cursoferias.petstore.entity.TipoServico;

@Component
public class DataInitializer implements CommandLineRunner {

	private final EspecieBusiness especieBusiness;
	private final ClienteBusiness clienteBusiness;
	private final PetBusiness petBusiness;
	private final ServicoBusiness servicoBusiness;
	
	public DataInitializer(EspecieBusiness especieBusiness, ClienteBusiness clienteBusiness, PetBusiness petBusiness, ServicoBusiness servicoBusiness) {
		this.especieBusiness = especieBusiness;
		this.clienteBusiness = clienteBusiness;
		this.petBusiness = petBusiness;
		this.servicoBusiness = servicoBusiness;
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		System.out.println("Criação de dados iniciada");
		
		Especie especie1 = new Especie();
		Especie especie2 = new Especie();
		
		especie1.setDescricao("Cachorro");
		especie2.setDescricao("Gato");
		
		especieBusiness.save(especie1);
		especieBusiness.save(especie2);
		
		Cliente cliente1 = new Cliente();
		Cliente cliente2 = new Cliente();
		
		cliente1.setNome("João");
		cliente2.setNome("Maria");
		
		clienteBusiness.save(cliente1);
		clienteBusiness.save(cliente2);
		
		Pet pet1 = new Pet();
		Pet pet2 = new Pet();
		Pet pet3 = new Pet();
		Pet pet4 = new Pet();
		
		pet1.setCliente(cliente1);
		pet1.setDataNascimento(LocalDate.parse("2018-01-01"));
		pet1.setEspecie(especie1);
		pet1.setNome("Rex");
		
		pet2.setCliente(cliente1);
		pet2.setDataNascimento(LocalDate.parse("2018-02-01"));
		pet2.setEspecie(especie2);
		pet2.setNome("Bob");
		
		pet3.setCliente(cliente2);
		pet3.setDataNascimento(LocalDate.parse("2018-03-01"));
		pet3.setEspecie(especie1);
		pet3.setNome("Marley");
		
		pet4.setCliente(cliente2);
		pet4.setDataNascimento(LocalDate.parse("2018-04-01"));
		pet4.setEspecie(especie2);
		pet4.setNome("Nina");
		
		petBusiness.save(pet1);
		petBusiness.save(pet2);
		petBusiness.save(pet3);
		petBusiness.save(pet4);
		
		Servico servico1 = new Servico();
		Servico servico2 = new Servico();
		Servico servico3 = new Servico();
		Servico servico4 = new Servico();
		
		servico1.setDataHora(LocalDateTime.parse("2018-09-01T08:00:00"));
		servico1.setObservacao("Banho e tosa do Rex");
		servico1.setPet(pet1);
		servico1.setTipoServico(TipoServico.BANHO_TOSA);
		servico1.setValor(new BigDecimal("60.00"));
		
		servico2.setDataHora(LocalDateTime.parse("2018-10-01T09:00:00"));
		servico2.setObservacao("Cirurgia do Bob");
		servico2.setPet(pet2);
		servico2.setTipoServico(TipoServico.CIRURGIA);
		servico2.setValor(new BigDecimal("500.00"));
		
		servico3.setDataHora(LocalDateTime.parse("2018-11-01T10:00:00"));
		servico3.setObservacao("Consulta do Marley");
		servico3.setPet(pet3);
		servico3.setTipoServico(TipoServico.CONSULTA);
		servico3.setValor(new BigDecimal("120.00"));
		
		servico4.setDataHora(LocalDateTime.parse("2018-12-01T11:00:00"));
		servico4.setObservacao("Vacinação da Nina");
		servico4.setPet(pet4);
		servico4.setTipoServico(TipoServico.VACINACAO);
		servico4.setValor(new BigDecimal("250.00"));
		
		servicoBusiness.save(servico1);
		servicoBusiness.save(servico2);
		servicoBusiness.save(servico3);
		servicoBusiness.save(servico4);
		
		System.out.println("Criação de dados finalizada");
	}

}
