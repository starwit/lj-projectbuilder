package de.starwit.application.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import de.starwit.persistence.entity.Role;
import de.starwit.persistence.entity.User;
import de.starwit.service.impl.UserService;

public class LocalAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    final static Logger LOG = LoggerFactory.getLogger(LocalAuthenticationSuccessHandler.class);
    
    @Autowired
    private UserService userService;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException {
        LOG.info("authentication successfull redirecting...");

        DefaultOAuth2User authUser = (DefaultOAuth2User) authentication.getPrincipal();     
        String userId = authUser.getAttribute("login");
        User localUserData = userService.findByUsername(userId);
        if(localUserData.getRole() == Role.NONE) {
            redirectStrategy.sendRedirect(request, response, "/#/viewcomponents/welcome/");
        } else {
            redirectStrategy.sendRedirect(request, response, "/#/viewcomponents/app-all/");
        }
      }
}