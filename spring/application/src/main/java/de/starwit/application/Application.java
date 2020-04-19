package de.starwit.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Main SpringApplication to start the whole project
 */
@SpringBootApplication(scanBasePackages = {"de.starwit.rest", "de.starwit.service", "de.starwit.persistence", "de.starwit.generator.services"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(new Class[]{Application.class}, args);
    }
    
    @Configuration
    @Profile("dev")
    static class ApplicationSecurityDev extends WebSecurityConfigurerAdapter {
 
        @Override
        public void configure(WebSecurity web) throws Exception {
            web
                    .ignoring()
                    .antMatchers("/**");
        }
    }
    
    @Configuration
    @Profile("!dev")
    static class ApplicationSecurityProd extends WebSecurityConfigurerAdapter {
 
        @Override
        public void configure(WebSecurity web) throws Exception {
        	web
            .ignoring()
            .antMatchers("/welcome.html");
        	
        }
        
    	@Override
        protected void configure(HttpSecurity http) throws Exception {
            http
            	.authorizeRequests()
                    .antMatchers("/welcome.html").permitAll()
                    .antMatchers("/**").access("hasRole('ROLE_USER')")
                    .anyRequest().authenticated()
                    .and()
                .oauth2Login()
                    .permitAll();
        }
    }
    
}
