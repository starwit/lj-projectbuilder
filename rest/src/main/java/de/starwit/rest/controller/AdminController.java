package de.starwit.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.starwit.persistence.entity.UserEntity;
import de.starwit.persistence.repository.UserRepository;

@RestController
@RequestMapping("${rest.base-path}/admin")
public class AdminController {

	@Autowired
	UserRepository allowedUserRepository;    
    
    @GetMapping("/knownUsers")
	public List<UserEntity> getKnownUsers() {
		return allowedUserRepository.findAll();
	}	
}
