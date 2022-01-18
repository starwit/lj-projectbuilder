package de.starwit.rest.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.starwit.allowedroles.IsAdmin;

@RestController
@RequestMapping("${rest.base-path}/user")
public class UserController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping(value = "/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String contextPath = request.getContextPath();
        request.logout();
        response.sendRedirect(contextPath + "/");
    }

    @IsAdmin
    @GetMapping("/info")
    public AccessToken getUserInfo(Model model, Principal principal) {
        KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
        AccessToken accessToken = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
        String groups = accessToken.getOtherClaims().get("groups").toString();
        log.info("user groups {}", groups);
        log.info("user info {},{}", accessToken.getGivenName(), accessToken.getFamilyName());
        return accessToken;
    }

    @GetMapping("/roles")
    public Set<String> getRoles(Model model, Principal principal) {
        KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
        return keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken().getRealmAccess()
                .getRoles();
    }
}