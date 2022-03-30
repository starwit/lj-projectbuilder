package de.starwit.generator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
    scanBasePackages = {
        "de.starwit.rest",
        "de.starwit.service",
        "de.starwit.mapper",
        "de.starwit.persistence",
        "de.starwit.generator.services",
        "de.starwit.generator.mapper",
        "de.starwit.application.config",
    }
)
public class GeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeneratorApplication.class, args);
    }
}
