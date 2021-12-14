package de.starwit.application.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

@Profile("!dev")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    ClientRegistrationRepository clientRegistrationRepository;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
			.csrf().disable()
			.authorizeRequests()
	        .antMatchers("/api/userinfo", "/api/userinfo/", "/api/user/information", "/api/user/information/").permitAll()
			.antMatchers("/").permitAll()
	        .antMatchers("/static/**/**.{js,html,css,json}").permitAll()
	    	.antMatchers("/**/api/user/**").permitAll()
			.antMatchers("/swagger-ui/**", "/swagger-ui.html", "**/api-docs", "**/api-docs/swagger-config").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_PBUSER')")
			.antMatchers("/**/*.{js,html,css,json}").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_PBUSER')")
	        .antMatchers("/**/app/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_PBUSER')")
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
