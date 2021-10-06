package de.starwit.rest.controller;

import java.util.ArrayList;
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

import de.starwit.generator.dto.ApplicationDto;
import de.starwit.generator.dto.EntityDto;
import de.starwit.generator.dto.FieldDto;
import de.starwit.generator.mapper.ApplicationMapper;
import de.starwit.persistence.entity.App;
import de.starwit.persistence.entity.AppTemplate;
import de.starwit.service.impl.AppService;

@RestController
@RequestMapping("${rest.base-path}/application")
public class ApplicationController {

    final static Logger LOG = LoggerFactory.getLogger(ApplicationController.class);

	@Autowired
	private AppService appService;

	@Autowired
	private ApplicationMapper appMapper;

    @GetMapping("/query/all")
	public List<ApplicationDto> findAll() {
		return null;
	}

	@GetMapping(value = "/query/{id}")
	public ApplicationDto findById(@PathVariable("id") Long id) throws Exception {
		return appMapper.convertDb(appService.findById(id));
	}

	@PutMapping
	public ApplicationDto save(@RequestBody ApplicationDto dto) throws Exception {
		return update(dto);
	}

	@PostMapping
	public ApplicationDto update(@RequestBody ApplicationDto dto) throws Exception {
		App app = appMapper.convertFrontend(dto);
		app = appService.saveOrUpdate(app);

		//update entities
			//check if id exists if yes -> update, if no -> create
			//update fields
		//update relations
		//update template id
		return appMapper.convertDb(app);
	}

	@DeleteMapping(value = "/{id}")
	public void delete(@PathVariable("id") Long id) {
		
	}


    
}
