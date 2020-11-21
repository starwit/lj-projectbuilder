package de.starwit.service.impl;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.starwit.mapper.Mapper;
import de.starwit.persistence.entity.CategoryEntity;
import de.starwit.persistence.repository.CategoryRepository;

@Service
public class CategoryService implements ServiceInterface<CategoryEntity> {

	final static Logger LOG = LoggerFactory.getLogger(CategoryService.class);

	@Autowired
	private CategoryRepository categoryRepository;

	public CategoryEntity findByName(String name) {
		CategoryEntity entity = this.categoryRepository.findByName(name);
		return Mapper.convert(entity, CategoryEntity.class, "templates");
	}

	@Override
	public CategoryEntity findById(Long id) {
		CategoryEntity entity = this.categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
		return Mapper.convert(entity, CategoryEntity.class, "templates");

	}

	@Override
	public List<CategoryEntity> findAll() {
		List<CategoryEntity> entities = this.categoryRepository.findAll();
		return Mapper.convertList(entities, CategoryEntity.class, "templates");
	}

	@Override
	public CategoryEntity saveOrUpdate(CategoryEntity dto) throws ValidationException  {
		CategoryEntity entity = this.categoryRepository.findById(dto.getId()).orElseThrow(() -> new EntityNotFoundException(String.valueOf(dto.getId())));
		entity.setName(dto.getName());
		entity = this.categoryRepository.save(entity);
		return Mapper.convert(entity, CategoryEntity.class, "templates");
	}

	@Override
	public void delete(Long id) {
		this.categoryRepository.deleteById(id);	
	}
}