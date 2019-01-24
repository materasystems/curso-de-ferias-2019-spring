package com.matera.cursoferias.petstore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.matera.cursoferias.petstore.entity.Especie;
import com.matera.cursoferias.petstore.repository.EspecieRepository;

@Component
public class Bootstrap implements CommandLineRunner {

	@Autowired
	private Printer printer;
	
	@Autowired
	private EspecieRepository especieRepository;
	
	@Override
	public void run(String... args) throws Exception {
		Especie especie1 = new Especie();
		especie1.setDescricao("Cachorro");
		
		especieRepository.save(especie1);
		
		printer.print();
	}

}
