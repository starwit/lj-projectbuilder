package de.starwit.application.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    ClientRegistrationRepository clientRegistrationRepository;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
	    	.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
	        .authorizeRequests()
	        .antMatchers("/api/userinfo", "/api/userinfo/", "/api/user/information", "/api/user/information/").permitAll()
	        .antMatchers("/**/*.{js,html,css}").permitAll()
	    	.antMatchers("/**/viewcomponents/default/*.html").permitAll()
	    	.antMatchers("/**/api/user/**").permitAll()
	        .antMatchers("/**/viewcomponents/projecttemplate/*.html").access("hasRole('ROLE_ADMIN')")
	        .antMatchers("/**/viewcomponents/domain/*.html").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_PBUSER')")
	        .antMatchers("/**/viewcomponents/project/*.html").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_PBUSER')")
	        .antMatchers("/**/viewcomponents/generator/*.html").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_PBUSER')")
	        .antMatchers("/**/api/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_PBUSER')")
	        .anyRequest().authenticated()
	        .and()
	        .oauth2Login()
	        	.successHandler(myAuthenticationSuccessHandler())
	        		.userInfoEndpoint()
	        	.userService(injectLocalRolesOAuth2UserService())
	        .and().and()
	        .logout()
	        .deleteCookies("JSESSIONID")
	        .logoutSuccessHandler(oidcLogoutSuccessHandler());
    }

    @Bean
    InjectLocalRolesOAuth2UserService injectLocalRolesOAuth2UserService() {
        return new InjectLocalRolesOAuth2UserService();
    }

	@Bean
	public LocalAuthenticationSuccessHandler myAuthenticationSuccessHandler() {
		return new LocalAuthenticationSuccessHandler();
	}   

    OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessHandler() {
        OidcClientInitiatedLogoutSuccessHandler successHandler = new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository);
        successHandler.setPostLogoutRedirectUri("{baseUrl}");
        return successHandler;
    }

}
