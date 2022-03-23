package de.starwit.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Base RestApplication
 * <p>
 * Disable default HATEOAS with exclude <code>org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration</code>
 */
@SpringBootApplication(scanBasePackages = {"de.starwit.rest", "de.starwit.service", "de.starwit.persistence",
        "de.starwit.generator.services", "de.starwit.generator.mapper", "de.starwit.application.config", "de.starwit.mapper"})
public class RestApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class, args);
    }

}
