package com.matera.cursoferias.petstore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

	@Autowired
	private Printer englishPrinter;
	
	@Autowired
	private Printer portuguesePrinter;
	
	@Override
	public void run(String... args) throws Exception {
		englishPrinter.print();
		portuguesePrinter.print();
	}

}
