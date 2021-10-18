package de.starwit.rest.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import de.starwit.dto.ApplicationDto;
import de.starwit.mapper.ApplicationMapper;
import de.starwit.persistence.entity.App;
import de.starwit.service.impl.AppService;

@RestController
@RequestMapping("${rest.base-path}/application")
public class ApplicationController {

    final static Logger LOG = LoggerFactory.getLogger(ApplicationController.class);

	@Autowired
	private AppService appService;

	@Autowired
	private ApplicationMapper appMapper;

    @GetMapping
	public List<ApplicationDto> findAll() {
		return null;
	}

	@GetMapping(value = "/{id}")
	public ApplicationDto findById(@PathVariable("id") Long id) {
		App entity = appService.findById(id);
		ApplicationDto dto = appMapper.convertToDto(entity);
		return dto;
	}

	@PutMapping
	public ApplicationDto save(@RequestBody ApplicationDto dto) {
		return update(dto);
	}

	@PostMapping
	public ApplicationDto update(@RequestBody ApplicationDto dto) {
		App app = appMapper.convertToEntity(dto);
		app = appService.saveOrUpdate(app);
		return appMapper.convertToDto(app);
	}

	@DeleteMapping(value = "/{id}")
	public void delete(@PathVariable("id") Long id) {
		
	}


    
}
