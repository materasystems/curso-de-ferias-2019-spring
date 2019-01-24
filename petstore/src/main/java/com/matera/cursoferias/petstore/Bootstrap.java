package com.matera.cursoferias.petstore;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.matera.cursoferias.petstore.entity.Cliente;
import com.matera.cursoferias.petstore.entity.Especie;
import com.matera.cursoferias.petstore.entity.Pet;
import com.matera.cursoferias.petstore.entity.Servico;
import com.matera.cursoferias.petstore.entity.TipoServico;
import com.matera.cursoferias.petstore.repository.ClienteRepository;
import com.matera.cursoferias.petstore.repository.EspecieRepository;
import com.matera.cursoferias.petstore.repository.PetRepository;
import com.matera.cursoferias.petstore.repository.ServicoRepository;

@Component
public class Bootstrap implements CommandLineRunner {

	@Autowired
	private EspecieRepository especieRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private PetRepository petRepository;
	
	@Autowired
	private ServicoRepository servicoRepository;
	
	@Override
	public void run(String... args) throws Exception {
		System.out.println("Iniciando criação de dados");
		
		Cliente cliente1 = new Cliente();
		Cliente cliente2 = new Cliente();

		cliente1.setNome("João");
		cliente2.setNome("Maria");

		clienteRepository.save(cliente1);
		clienteRepository.save(cliente2);

		Especie especie1 = new Especie();
		Especie especie2 = new Especie();

		especie1.setDescricao("Cachorro");
		especie2.setDescricao("Gato");

		especieRepository.save(especie1);
		especieRepository.save(especie2);

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

		petRepository.save(pet1);
		petRepository.save(pet2);
		petRepository.save(pet3);
		petRepository.save(pet4);

		Servico servico1 = new Servico();
		Servico servico2 = new Servico();
		Servico servico3 = new Servico();
		Servico servico4 = new Servico();

		servico1.setDataHora(LocalDateTime.parse("2018-09-10T08:00:00"));
		servico1.setObservacao("Banho e tosa do Rex");
		servico1.setPet(pet1);
		servico1.setTipoServico(TipoServico.BANHO_TOSA);
		servico1.setValor(new BigDecimal("60.00"));

		servico2.setDataHora(LocalDateTime.parse("2018-10-10T09:00:00"));
		servico2.setObservacao("Cirurgia do Bob");
		servico2.setPet(pet2);
		servico2.setTipoServico(TipoServico.CIRURGIA);
		servico2.setValor(new BigDecimal("500.00"));

		servico3.setDataHora(LocalDateTime.parse("2018-11-10T10:00:00"));
		servico3.setObservacao("Consulta do Marley");
		servico3.setPet(pet3);
		servico3.setTipoServico(TipoServico.CONSULTA);
		servico3.setValor(new BigDecimal("120.00"));

		servico4.setDataHora(LocalDateTime.parse("2018-12-10T11:00:00"));
		servico4.setObservacao("Vacinação da Nina");
		servico4.setPet(pet4);
		servico4.setTipoServico(TipoServico.VACINACAO);
		servico4.setValor(new BigDecimal("250.00"));

		servicoRepository.save(servico1);
		servicoRepository.save(servico2);
		servicoRepository.save(servico3);
		servicoRepository.save(servico4);
		
		System.out.println("Criação de dados finalizada");
	}

}
