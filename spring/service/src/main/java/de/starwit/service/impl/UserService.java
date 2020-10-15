package de.starwit.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;

import de.starwit.persistence.entity.UserEntity;
import de.starwit.exception.EntityNotFoundException;
import de.starwit.persistence.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public Page<UserEntity> findAll(Pageable pageable) {
        return this.userRepository.findAll(pageable);
    }

    public UserEntity findById(Long id) {
        return this.userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public UserEntity create(UserEntity userEntity) {
        return this.userRepository.save(userEntity);
    }

    public UserEntity findByUsername(String username){
        return this.userRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
    }

    public UserEntity getCurrentUser(){
        DefaultOidcUser userDetails = (DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currentUsername = userDetails.getUserInfo().getPreferredUsername();
        return this.findByUsername(currentUsername);
    }

}
