package de.starwit.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main SpringApplication to start the whole project
 */
@SpringBootApplication(scanBasePackages = { "de.starwit.rest", "de.starwit.service", "de.starwit.persistence",
        "de.starwit.generator.services", "de.starwit.application.config"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(new Class[] { Application.class }, args);
    }
}
