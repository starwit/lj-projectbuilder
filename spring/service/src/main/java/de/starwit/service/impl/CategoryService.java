package de.starwit.service.impl;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.starwit.persistence.entity.CategoryEntity;
import de.starwit.persistence.repository.CategoryRepository;

@Service
public class CategoryService {

	final static Logger LOG = LoggerFactory.getLogger(CategoryService.class);

	@Autowired
	private CategoryRepository categoryRepository;

	public CategoryEntity findByName(String name) {
		return this.categoryRepository.findByName(name);
	}

	public CategoryEntity findById(Long id) {
		return this.categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
	}

	public List<CategoryEntity> findAll() {
		return this.categoryRepository.findAll();
	}

	public CategoryEntity saveOrUpdate(CategoryEntity entity) {
		return this.categoryRepository.save(entity);
	}

	public CategoryEntity delete(CategoryEntity entity) {
    this.categoryRepository.delete(entity);
    return entity;
	}
}