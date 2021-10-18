package de.starwit.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.starwit.mapper.Mapper;
import de.starwit.persistence.entity.Category;
import de.starwit.persistence.repository.CategoryRepository;

@Service
public class CategoryService implements ServiceInterface<Category, CategoryRepository> {

	final static Logger LOG = LoggerFactory.getLogger(CategoryService.class);

	@Autowired
	private CategoryRepository categoryRepository;

	public Category findByName(String name) {
		Category entity = this.categoryRepository.findByName(name);
		return Mapper.convert(entity, Category.class, "templates");
	}

	@Override
	public CategoryRepository getRepository() {
		return categoryRepository;
	}
}