package de.starwit.rest.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.starwit.persistence.entity.AttributeEntity;
import de.starwit.persistence.response.EntityListResponse;
import de.starwit.persistence.response.EntityResponse;
import de.starwit.service.impl.AttributeService;

/**
 * Domain RestController
 * Have a look at the RequestMapping!!!!!!
 */
@RestController
@RequestMapping("${rest.base-path}/attribute")
public class AttributeController {

    @Autowired
    private AttributeService attributeService;
    
	private GenericController<AttributeEntity> genericController;

	@PostConstruct
	public void init() {
		genericController = new GenericController<AttributeEntity>();
		genericController.setService(attributeService);
	}

	@GetMapping("/query/all")
	public EntityListResponse<AttributeEntity> findAll() {
		return genericController.findAll();
	}

	@GetMapping(value = "/query/{id}")
	public EntityResponse<AttributeEntity> findById(@PathVariable("id") Long id) {
		return genericController.findById(id);
	}

	@PutMapping
	public EntityResponse<AttributeEntity> save(@RequestBody AttributeEntity category) {
		return genericController.editGeneric(category);
	}

	@PostMapping
	public EntityResponse<AttributeEntity> update(@RequestBody AttributeEntity category) {
		return genericController.editGeneric(category);
	}

	@DeleteMapping(value = "/{id}")
	public EntityResponse<AttributeEntity> delete(@PathVariable("id") Long id) {
		return genericController.delete(id);
	}
}
