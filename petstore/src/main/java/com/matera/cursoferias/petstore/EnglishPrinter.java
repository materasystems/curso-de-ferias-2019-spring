package com.matera.cursoferias.petstore;

import org.springframework.stereotype.Component;

@Component
public class EnglishPrinter implements Printer {

	@Override
	public void print() {
		System.out.println("Hello world");
	}

}
