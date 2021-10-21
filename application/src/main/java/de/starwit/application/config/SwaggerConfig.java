package de.starwit.application.config;

import org.apache.catalina.security.SecurityUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.SecurityConfig;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .components(
                new Components()
                .addSecuritySchemes("oauth", oauth())
            )
            .info(
                new Info()
                .title("Message service")
                .description("This documents message-service API")
                .version("1.0")
            );
    }

    public SecurityScheme oauth() {
        return new SecurityScheme()
        .name("oauth")
        .type(Type.OAUTH2)
        .in(In.HEADER)
        .bearerFormat("jwt")        
        .flows(
            new OAuthFlows()
                .implicit(new OAuthFlow().authorizationUrl("https://github.com/login/oauth/authorize")
                .scopes(null)
            )
        );
    }

}
