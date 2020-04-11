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

import de.starwit.dto.CategoryDto;
import de.starwit.persistence.response.EntityListResponse;
import de.starwit.persistence.response.EntityResponse;
import de.starwit.service.impl.CategoryService;

/**
 * Domain RestController Have a look at the RequestMapping!!!!!!
 */
@RestController
@RequestMapping("${rest.base-path}/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	private GenericController<CategoryDto> genericController;

	@PostConstruct
	public void init() {
		genericController = new GenericController<CategoryDto>();
		genericController.setService(categoryService);
	}

	@GetMapping("/query/all")
	public EntityListResponse<CategoryDto> findAll() {
		return genericController.findAll();
	}

	@GetMapping(value = "/query/{id}")
	public EntityResponse<CategoryDto> findById(@PathVariable("id") Long id) {
		return genericController.findById(id);
	}

	@PutMapping
	public EntityResponse<CategoryDto> save(@RequestBody CategoryDto category) {
		return genericController.editGeneric(category);
	}

	@PostMapping
	public EntityResponse<CategoryDto> update(@RequestBody CategoryDto category) {
		return genericController.editGeneric(category);
	}

	@DeleteMapping(value = "/{id}")
	public EntityResponse<CategoryDto> delete(@PathVariable("id") Long id) {
		return genericController.delete(id);
	}

}
