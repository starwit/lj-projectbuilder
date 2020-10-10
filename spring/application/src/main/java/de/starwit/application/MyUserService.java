package de.starwit.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;

import de.starwit.persistence.entity.AllowedUserEntity;
import de.starwit.persistence.entity.UserRoleEnum;
import de.starwit.persistence.repository.AllowedUserRepository;

public class MyUserService extends DefaultOAuth2UserService {

	@Autowired
	AllowedUserRepository allowedUserRepository;    

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        DefaultOAuth2User user = (DefaultOAuth2User) super.loadUser(userRequest);

        UserRoleEnum role = UserRoleEnum.NONE;
        role = checkIfUserHasRole(user);
        
        Map<String, Object> userAttrs = new HashMap<>();
        userAttrs.put("login", user.getAttribute("login"));
        OAuth2UserAuthority myAuth =  new OAuth2UserAuthority(role.getRoleName(), userAttrs);

        List<GrantedAuthority> myAuths = new ArrayList<>();
        myAuths.addAll(user.getAuthorities());
        myAuths.add(myAuth);
        DefaultOAuth2User newUser = new DefaultOAuth2User(myAuths, user.getAttributes(), "id");

        for (Object entry : newUser.getAuthorities().toArray()) {
            System.out.println(entry + " - " + entry.getClass());
        }

        return newUser;
    }

    private UserRoleEnum checkIfUserHasRole(DefaultOAuth2User user) {
        String userId = user.getAttribute("login");
        AllowedUserEntity localUserData = allowedUserRepository.findByUserAlias(userId);
        if(localUserData == null) {
            localUserData = new AllowedUserEntity();
            localUserData.setUserAlias(userId);
            localUserData.setUserRole(UserRoleEnum.NONE);
        }
        return localUserData.getUserRole();
    }

    private void printStuff(DefaultOAuth2User user) {
        Map<String, Object> attrs = user.getAttributes();
        for (String key : attrs.keySet()) {
            System.out.println("key: " + key + " - " + attrs.get(key));
        }
        for (Object entry : user.getAuthorities().toArray()) {
            System.out.println(entry + " - " + entry.getClass());
        }
        String login = (String) user.getAttribute("login");
        System.out.println(login);
    }

}
