package de.ttt.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;

/**
 * Base RestApplication
 *
 * Disable default HATEOAS with exclude <code>org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration</code>
 *
 */
@SpringBootApplication(scanBasePackages = {"de.ttt.rest", "de.ttt.service", "de.ttt.persistence"}, exclude = RepositoryRestMvcAutoConfiguration.class)
public class RestApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class, args);
    }

}
