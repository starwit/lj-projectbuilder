package de.starwit.service.impl;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.starwit.mapper.Mapper;
import de.starwit.persistence.entity.Category;
import de.starwit.persistence.repository.CategoryRepository;

@Service
public class CategoryService implements ServiceInterface<Category> {

	final static Logger LOG = LoggerFactory.getLogger(CategoryService.class);

	@Autowired
	private CategoryRepository categoryRepository;

	public Category findByName(String name) {
		Category entity = this.categoryRepository.findByName(name);
		return Mapper.convert(entity, Category.class, "templates");
	}

	@Override
	public Category findById(Long id) {
		Category entity = this.categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
		return Mapper.convert(entity, Category.class, "templates");

	}

	@Override
	public List<Category> findAll() {
		List<Category> entities = this.categoryRepository.findAll();
		return Mapper.convertList(entities, Category.class, "templates");
	}

	@Override
	public Category saveOrUpdate(Category dto) throws ValidationException  {
		Category entity = this.categoryRepository.findById(dto.getId()).orElseThrow(() -> new EntityNotFoundException(String.valueOf(dto.getId())));
		entity.setName(dto.getName());
		entity = this.categoryRepository.save(entity);
		return Mapper.convert(entity, Category.class, "templates");
	}

	@Override
	public void delete(Long id) {
		this.categoryRepository.deleteById(id);	
	}
}