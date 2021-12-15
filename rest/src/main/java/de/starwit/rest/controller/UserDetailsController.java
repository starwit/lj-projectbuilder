package de.starwit.rest.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.starwit.persistence.entity.User;
import de.starwit.persistence.repository.UserRepository;

@RestController
@RequestMapping("${rest.base-path}/user")
public class UserDetailsController {
	final static Logger LOG = LoggerFactory.getLogger(UserDetailsController.class);

	@Autowired
	UserRepository allowedUserRepository;

	@GetMapping("/userDetails")
	public String getUserDetails(){
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		DefaultOAuth2User authUser = (DefaultOAuth2User) authentication.getPrincipal();

		Map<String, Object> userAttr = authUser.getAttributes();
		for (String key : userAttr.keySet()) {
			LOG.info(key + ":" + authUser.getAttributes().get(key));
		}

		String authenticatedUserName = (String) authUser.getAttributes().get("login");
		User user = allowedUserRepository.findByUsername(authenticatedUserName).get();
		if(null == user) {
			LOG.warn("User " + authenticatedUserName + " may not enter");
		} else {
			LOG.info("User " + authenticatedUserName + " may enter");
		}

		
		return authentication.getName();
	}

	@GetMapping("/image")
	public String getUserImageLink() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String imageURL="welcome.jpg";
		if (authentication != null) {
			DefaultOAuth2User authUser = (DefaultOAuth2User) authentication.getPrincipal();		
			imageURL = (String) authUser.getAttributes().get("avatar_url");
		}
		return imageURL;
	}

	@GetMapping("/oidc-principal")
	public OidcUser getOidcUserPrincipal(@AuthenticationPrincipal OidcUser principal) {
		LOG.debug(principal.toString());
	    return principal;
	}
}