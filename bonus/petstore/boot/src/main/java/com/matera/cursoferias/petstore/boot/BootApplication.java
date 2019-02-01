package com.matera.cursoferias.petstore.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.matera.cursoferias.petstore.persistence")
@EntityScan("com.matera.cursoferias.petstore.domain")
@ComponentScan("com.matera.cursoferias.petstore.controller, com.matera.cursoferias.petstore.service, com.matera.cursoferias.petstore.business")
@SpringBootApplication
public class BootApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootApplication.class, args);
	}

}

