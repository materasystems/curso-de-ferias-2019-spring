package com.matera.cursoferias.petstore;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("en")
@Primary
public class EnglishPrinter implements Printer {

	@Value("${printer.msg}")
	private String msg;

	@Override
	public void print() {
		System.out.println(msg);
	}

}