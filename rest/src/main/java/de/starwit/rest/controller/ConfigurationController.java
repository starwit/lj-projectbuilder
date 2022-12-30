package de.starwit.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${rest.base-path}/configuration")
public class ConfigurationController {

    @Value("${sentry.dsn.frontend}")
    private String sentryDsn;

    @Operation(summary = "Returns the sentry dsn for frontend")
    @GetMapping(value = "/sentry/dsn")
    public String getSentryDsn(){
        return sentryDsn;
    }


}
