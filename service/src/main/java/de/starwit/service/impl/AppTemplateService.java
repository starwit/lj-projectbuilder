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
import de.starwit.persistence.entity.Category;
import de.starwit.persistence.entity.TemplateFile;
import de.starwit.persistence.entity.AppTemplate;
import de.starwit.persistence.exception.EntityNotFoundException;
import de.starwit.persistence.repository.CategoryRepository;
import de.starwit.persistence.repository.AppTemplateRepository;

@Service
public class AppTemplateService implements ServiceInterface<AppTemplate> {

	final static Logger LOG = LoggerFactory.getLogger(AppTemplateService.class);

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private CodeTemplateService codeTemplateService;

	@Autowired
	private AppTemplateRepository appTemplateRepository;
	
	public AppTemplate update(AppTemplate entity) throws ValidationException {
		Set<TemplateFile> codeTemplates = entity.getCodeTemplates();
		map(entity);

		// CodeTemplateEntity might be not filled completely
		if (codeTemplates != null) {
			for (TemplateFile codeTemplateEntity : codeTemplates) {
				codeTemplateEntity.setAppTemplate(entity);

				// set default category
				if (codeTemplateEntity.getCategory() == null) {
					Category ce = categoryRepository.findByName(Category.DEFAULT_CATEGORY);
					codeTemplateEntity.setCategory(ce);
				}
			}
		}
		
		entity = this.appTemplateRepository.saveAndFlush(entity);
		return map(entity);
	}

	public AppTemplate create(AppTemplate entity) throws ValidationException {
		Set<TemplateFile> codeTemplates = entity.getCodeTemplates();
		this.appTemplateRepository.save(entity);

		if (codeTemplates != null) {
			for (TemplateFile codeTemplateEntity : codeTemplates) {
				codeTemplateEntity.setAppTemplate(entity);
				codeTemplateService.saveOrUpdate(codeTemplateEntity);
			}
		}
		return map(entity);
	}

	@Override
	public List<AppTemplate> findAll() {
		List<AppTemplate> entities = this.appTemplateRepository.findAll();
		List<AppTemplate> dtos = new ArrayList<AppTemplate>();
		for (AppTemplate entity : entities) {
			AppTemplate dto = map(entity);
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public AppTemplate findById(Long id) {
		AppTemplate entity = this.appTemplateRepository.findById(id).orElse(null);		
		return map(entity);
	}

	public AppTemplate map(AppTemplate entity) {
		AppTemplate dto = Mapper.convert(entity, AppTemplate.class, "codeTemplates");
		Set<TemplateFile> ctDtos = Mapper.convertSet(entity.getCodeTemplates(), TemplateFile.class, "appTemplate");
		if (entity != null && entity.getCodeTemplates() != null) {
			for (TemplateFile codeTemplateDto : ctDtos) {
				Category category = Mapper.convert(codeTemplateDto.getCategory(), Category.class, "templates");
				codeTemplateDto.setCategory(category);
			}
		}
		dto.setCodeTemplates(ctDtos);
		return dto;
	}

	@Override
	public AppTemplate saveOrUpdate(AppTemplate entity) {
		map(entity);
		return this.appTemplateRepository.save(entity);
	}

	@Override
	public void delete(Long id) throws EntityNotFoundException {
		this.appTemplateRepository.deleteById(id);
	}

}
