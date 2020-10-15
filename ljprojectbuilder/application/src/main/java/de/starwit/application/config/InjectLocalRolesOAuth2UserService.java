package de.starwit.application.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;

import de.starwit.exception.EntityNotFoundException;
import de.starwit.persistence.entity.Role;
import de.starwit.persistence.entity.UserEntity;
import de.starwit.service.impl.UserService;

public class InjectLocalRolesOAuth2UserService extends DefaultOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private static Logger LOGGER = LoggerFactory.getLogger(InjectLocalRolesOAuth2UserService.class);

    @Autowired
    private UserService userService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    	DefaultOAuth2User user = (DefaultOAuth2User) super.loadUser(userRequest);
        String username = user.getName();

        UserEntity userEntity;
        try {
            userEntity = this.userService.findByUsername(username);
            LOGGER.debug("Found authenticated user \"{}\" in local DB.", username);
        } catch (EntityNotFoundException ex) {
        	LOGGER.debug("New user with username \"{}\" encountered! Adding user to local DB.", username);
            userEntity = createUserWithNoneRole(username);
            LOGGER.debug("New authenticated user \"{}\" created!", userEntity.getUsername());
        }

        OAuth2User injectedUser = injectRole(user);
        LOGGER.debug("Successfully injected role \"{}\" for user \"{}\"", userEntity.getRole(), username);

        return injectedUser;
    }
    
    private Role checkIfUserHasRole(OAuth2User user) {
        String userId = user.getAttribute("login");
        UserEntity localUserData = null;
        try {
        	localUserData = userService.findByUsername(userId);
        } catch (EntityNotFoundException ex) {
        	localUserData = new UserEntity();
            localUserData.setUsername(userId);
            localUserData.setRole(Role.NONE);
        }

        return localUserData.getRole();
    }

    private OAuth2User injectRole(OAuth2User user) {
        Role role = Role.NONE;
        role = checkIfUserHasRole(user);
        
        Map<String, Object> userAttrs = new HashMap<>();
        userAttrs.put("login", user.getAttribute("login"));
        OAuth2UserAuthority myAuth =  new OAuth2UserAuthority(role.authorityString, userAttrs);

        List<GrantedAuthority> myAuths = new ArrayList<>();
        myAuths.addAll(user.getAuthorities());
        myAuths.add(myAuth);
        DefaultOAuth2User newUser = new DefaultOAuth2User(myAuths, user.getAttributes(), "id");

        for (Object entry : newUser.getAuthorities().toArray()) {
        	LOGGER.debug("New authenticated user \"{}\" with class \"{}\" encountered! Adding user to local DB.", entry, entry.getClass());
        }

        return newUser;
    }


    private UserEntity createUserWithNoneRole(String username) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setRole(Role.NONE);
        return userService.create(userEntity);
    }
    
}
