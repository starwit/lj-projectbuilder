package de.ttt.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main SpringApplication to start the whole project
 */
@SpringBootApplication(scanBasePackages = {"de.ttt.rest", "de.ttt.service", "de.ttt.persistence"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(new Class[]{Application.class}, args);
    }

}
