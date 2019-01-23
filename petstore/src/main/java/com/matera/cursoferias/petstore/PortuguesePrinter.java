package com.matera.cursoferias.petstore;

import org.springframework.stereotype.Component;

@Component
public class PortuguesePrinter implements Printer {

	@Override
	public void print() {
		System.out.println("Olá mundo");
	}

}
