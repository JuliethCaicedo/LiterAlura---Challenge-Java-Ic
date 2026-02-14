package com.ic.LiterAluraChallengeIc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraChallengeIcApplication implements CommandLineRunner {

	private final Principal principal;

    public LiterAluraChallengeIcApplication(Principal principal) {
        this.principal = principal;
    }

    public static void main(String[] args) {
		SpringApplication.run(LiterAluraChallengeIcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		principal.bienvenidoLiterAlura();
	}
}
