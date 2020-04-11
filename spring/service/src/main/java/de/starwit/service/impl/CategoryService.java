package de.starwit.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.starwit.dto.CategoryDto;
import de.starwit.persistence.entity.CategoryEntity;
import de.starwit.persistence.repository.CategoryRepository;

@Service
public class CategoryService implements AbstractServiceInterface<CategoryDto> {

	final static Logger LOG = LoggerFactory.getLogger(CategoryService.class);

	@Autowired
	private CategoryRepository categoryRepository;

	private CategoryDto entityToDto(CategoryEntity entity) {
		if (entity != null) {
			CategoryDto cat = new CategoryDto();
			cat.setName(entity.getName());
			cat.setId(entity.getId());
			return cat;
		}
		return null;
	}

	public CategoryDto findByName(String name) {
		CategoryEntity entity = this.categoryRepository.findByName(name);
		return entityToDto(entity);
	}

	public CategoryDto findById(Long id) {
		CategoryEntity entity = this.categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
		return entityToDto(entity);

	}

	public List<CategoryDto> findAll() {
		List<CategoryEntity> entities = this.categoryRepository.findAll();
		List<CategoryDto> dtos = new ArrayList<CategoryDto>();
		if (entities != null) {
			for (CategoryEntity categoryEntity : entities) {
				dtos.add(entityToDto(categoryEntity));
			}
			return dtos;
		}
		return null;
	}

	public CategoryDto saveOrUpdate(CategoryDto dto) {
		CategoryEntity entity = this.categoryRepository.findById(dto.getId()).orElseThrow(() -> new EntityNotFoundException(String.valueOf(dto.getId())));
		entity.setName(dto.getName());
		entity = this.categoryRepository.save(entity);
		return entityToDto(entity);
	}

	@Override
	public void delete(Long id) {
		this.categoryRepository.deleteById(id);	
	}
}