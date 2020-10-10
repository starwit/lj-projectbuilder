package de.starwit.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * Main SpringApplication to start the whole project
 */
@SpringBootApplication(scanBasePackages = { "de.starwit.rest", "de.starwit.service", "de.starwit.persistence",
        "de.starwit.generator.services" })
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(new Class[] { Application.class }, args);
    }

    @Configuration
    @Profile("dev")
    static class ApplicationSecurityDev extends WebSecurityConfigurerAdapter {

        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/**");
        }
    }

    @Configuration
    @Profile("!dev")
    static class ApplicationSecurityProd extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http.authorizeRequests()
                //.antMatchers("/**/projecttemplate*").access("hasRole('ROLE_ADMIN')") //TODO -> breaks default frontend.
                .antMatchers("/welcome.html").permitAll()
                .antMatchers("/role-error.html").access("hasRole('ROLE_NONE') or hasRole('ROLE_PBUSER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/**").access("hasRole('ROLE_PBUSER') or hasRole('ROLE_ADMIN')")
                .and()
                .oauth2Login()
                    .successHandler(myAuthenticationSuccessHandler())
                    .userInfoEndpoint()
                        .userService(myUserService());                       
        }

        @Bean
        MyUserService myUserService() {
            return new MyUserService();
        }

        @Bean
        public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
            return new LocalAuthenticationSuccessHandler();
        }
    }
    
}
