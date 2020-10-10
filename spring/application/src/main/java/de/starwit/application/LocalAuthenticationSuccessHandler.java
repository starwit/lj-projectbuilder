package de.starwit.application;

import java.io.IOException;
import java.util.Map;

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

import de.starwit.persistence.entity.AllowedUserEntity;
import de.starwit.persistence.entity.UserRoleEnum;
import de.starwit.persistence.repository.AllowedUserRepository;

public class LocalAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    final static Logger LOG = LoggerFactory.getLogger(LocalAuthenticationSuccessHandler.class);
    
    @Autowired
    private AllowedUserRepository allowedUserRepository;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException {
        LOG.info("authentication successfull redirecting...");

        DefaultOAuth2User authUser = (DefaultOAuth2User) authentication.getPrincipal();     
        String userId = authUser.getAttribute("login");
        AllowedUserEntity localUserData = allowedUserRepository.findByUserAlias(userId);
        if(localUserData.getUserRole() == UserRoleEnum.NONE) {
            redirectStrategy.sendRedirect(request, response, "/role-error.html");
        } else {
            redirectStrategy.sendRedirect(request, response, "/#/viewcomponents/project-all/");
        }
      }
}