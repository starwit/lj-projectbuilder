package de.starwit.rest.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${rest.base-path}/usermanagement")
public class UserManagementController {
    
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping(value = "/logout")
    public void fetchSignoutSite(HttpServletRequest request, HttpServletResponse response) {        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
    }

    @GetMapping("/userinfo")
    public AccessToken userInfoController(Model model, Principal principal) {
    
            KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
            AccessToken accessToken = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
            log.info("user info {},{}", accessToken.getGivenName(), accessToken.getFamilyName());
            return accessToken;
    }     
}
