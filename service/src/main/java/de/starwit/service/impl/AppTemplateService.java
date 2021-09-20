package de.starwit.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.starwit.mapper.Mapper;
import de.starwit.persistence.entity.CategoryEntity;
import de.starwit.persistence.entity.CodeTemplateEntity;
import de.starwit.persistence.entity.AppTemplateEntity;
import de.starwit.persistence.exception.EntityNotFoundException;
import de.starwit.persistence.repository.CategoryRepository;
import de.starwit.persistence.repository.AppTemplateRepository;

@Service
public class AppTemplateService implements ServiceInterface<AppTemplateEntity> {

	final static Logger LOG = LoggerFactory.getLogger(AppTemplateService.class);

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private CodeTemplateService codeTemplateService;

	@Autowired
	private AppTemplateRepository appTemplateRepository;
	
	public AppTemplateEntity update(AppTemplateEntity entity) throws ValidationException {
		Set<CodeTemplateEntity> codeTemplates = entity.getCodeTemplates();
		map(entity);

		// CodeTemplateEntity might be not filled completely
		if (codeTemplates != null) {
			for (CodeTemplateEntity codeTemplateEntity : codeTemplates) {
				codeTemplateEntity.setAppTemplate(entity);

				// set default category
				if (codeTemplateEntity.getCategory() == null) {
					CategoryEntity ce = categoryRepository.findByName(CategoryEntity.DEFAULT_CATEGORY);
					codeTemplateEntity.setCategory(ce);
				}
			}
		}
		
		entity = this.appTemplateRepository.saveAndFlush(entity);
		return map(entity);
	}

	public AppTemplateEntity create(AppTemplateEntity entity) throws ValidationException {
		Set<CodeTemplateEntity> codeTemplates = entity.getCodeTemplates();
		this.appTemplateRepository.save(entity);

		if (codeTemplates != null) {
			for (CodeTemplateEntity codeTemplateEntity : codeTemplates) {
				codeTemplateEntity.setAppTemplate(entity);
				codeTemplateService.saveOrUpdate(codeTemplateEntity);
			}
		}
		return map(entity);
	}

	@Override
	public List<AppTemplateEntity> findAll() {
		List<AppTemplateEntity> entities = this.appTemplateRepository.findAll();
		List<AppTemplateEntity> dtos = new ArrayList<AppTemplateEntity>();
		for (AppTemplateEntity entity : entities) {
			AppTemplateEntity dto = map(entity);
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public AppTemplateEntity findById(Long id) {
		AppTemplateEntity entity = this.appTemplateRepository.findById(id).orElse(null);		
		return map(entity);
	}

	public AppTemplateEntity map(AppTemplateEntity entity) {
		AppTemplateEntity dto = Mapper.convert(entity, AppTemplateEntity.class, "codeTemplates");
		Set<CodeTemplateEntity> ctDtos = Mapper.convertSet(entity.getCodeTemplates(), CodeTemplateEntity.class, "appTemplate");
		if (entity != null && entity.getCodeTemplates() != null) {
			for (CodeTemplateEntity codeTemplateDto : ctDtos) {
				CategoryEntity category = Mapper.convert(codeTemplateDto.getCategory(), CategoryEntity.class, "templates");
				codeTemplateDto.setCategory(category);
			}
		}
		dto.setCodeTemplates(ctDtos);
		return dto;
	}

	@Override
	public AppTemplateEntity saveOrUpdate(AppTemplateEntity entity) {
		map(entity);
		return this.appTemplateRepository.save(entity);
	}

	@Override
	public void delete(Long id) throws EntityNotFoundException {
		this.appTemplateRepository.deleteById(id);
	}

}
