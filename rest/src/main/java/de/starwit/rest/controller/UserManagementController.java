package de.starwit.rest.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.starwit.persistence.converter.ListToStringConverter;
import de.starwit.persistence.entity.App;
import de.starwit.service.impl.AppService;
import de.starwit.service.impl.AppTemplateService;

@RestController
@RequestMapping("${rest.base-path}/usermanagement")
public class UserManagementController {
    
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AppTemplateService appTemplateService;

    @Autowired
    private AppService appService;

    @GetMapping(value = "/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String contextPath = request.getContextPath();
        request.logout();  
        response.sendRedirect(contextPath + "/");     
    }

    @GetMapping("/userinfo")
    public AccessToken userInfoController(Model model, Principal principal) {
    
            KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
            AccessToken accessToken = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
            String groups = accessToken.getOtherClaims().get("groups").toString();
            log.info("user groups {}", groups);
            log.info("user info {},{}", accessToken.getGivenName(), accessToken.getFamilyName());
            return accessToken;
    }

    @PutMapping("/app-groups/{appId}")
    public List<String> updateAppGroupsFromUser(Model model, Principal principal, @PathVariable("appId") Long appId, @Valid @RequestBody List<String> selectedGroups) {
        KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
        AccessToken accessToken = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
        String groups = accessToken.getOtherClaims().get("groups").toString();

        ListToStringConverter conv = new ListToStringConverter();
        return conv.convertToEntityAttribute(groups);
    }

    @PutMapping("/apptemplate-groups/{appTemplateId}")
    public void updateAppTemplateGroupsFromUser(Model model, Principal principal, @PathVariable("appTemplateId") Long appId, @Valid @RequestBody List<String> selectedGroups) {
        KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
        AccessToken accessToken = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
        String groups = accessToken.getOtherClaims().get("groups").toString();

        ListToStringConverter conv = new ListToStringConverter();
        List<String> userGroups = conv.convertToEntityAttribute(groups);
    }
}
