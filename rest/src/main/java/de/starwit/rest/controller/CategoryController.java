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

import de.starwit.persistence.entity.Category;
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

	private GenericController<Category> genericController;

	@PostConstruct
	public void init() {
		genericController = new GenericController<Category>();
		genericController.setService(categoryService);
	}

	@GetMapping("/query/all")
	public EntityListResponse<Category> findAll() {
		return genericController.findAll();
	}

	@GetMapping(value = "/query/{id}")
	public EntityResponse<Category> findById(@PathVariable("id") Long id) {
		return genericController.findById(id);
	}

	@PutMapping
	public EntityResponse<Category> save(@RequestBody Category category) {
		return genericController.editGeneric(category);
	}

	@PostMapping
	public EntityResponse<Category> update(@RequestBody Category category) {
		return genericController.editGeneric(category);
	}

	@DeleteMapping(value = "/{id}")
	public EntityResponse<Category> delete(@PathVariable("id") Long id) {
		return genericController.delete(id);
	}

}
