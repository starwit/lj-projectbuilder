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
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import de.starwit.persistence.entity.AllowedUserEntity;
import de.starwit.persistence.repository.AllowedUserRepository;

public class LocalAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    final static Logger LOG = LoggerFactory.getLogger(LocalAuthenticationSuccessHandler.class);

    @Autowired
	AllowedUserRepository allowedUserRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException {
        LOG.info("authentication successfull ");
        DefaultOAuth2User authUser = (DefaultOAuth2User) authentication.getPrincipal();

		Map<String, Object> userAttr = authUser.getAttributes();
		for (String key : userAttr.keySet()) {
			LOG.debug(key + ":" + authUser.getAttributes().get(key));
		}

		String authenticatedUserName = (String) authUser.getAttributes().get("login");
		AllowedUserEntity user = allowedUserRepository.findByUserAlias(authenticatedUserName);
		if(null == user) {
            LOG.warn("User " + authenticatedUserName + " is not listed in allowed users.");
            response.sendRedirect(request.getContextPath() + "/error.html");
		} else {
            LOG.debug("User " + authenticatedUserName + " may enter");
            response.sendRedirect(request.getContextPath());
		}        
      }
}