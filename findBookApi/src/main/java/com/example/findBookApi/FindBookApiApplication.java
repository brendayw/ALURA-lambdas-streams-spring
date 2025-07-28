package com.example.findBookApi;

import com.example.findBookApi.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FindBookApiApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(FindBookApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.mostrarMenu();

	}
}
