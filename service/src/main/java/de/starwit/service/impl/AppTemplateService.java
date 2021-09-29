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
	private TemplateFileService templateFileService;

	@Autowired
	private AppTemplateRepository appTemplateRepository;
	
	public AppTemplate update(AppTemplate entity) throws ValidationException {
		Set<TemplateFile> templateFiles = entity.getTemplateFiles();
		map(entity);

		// TemplateFileEntity might be not filled completely
		if (templateFiles != null) {
			for (TemplateFile templateFileEntity : templateFiles) {
				templateFileEntity.setAppTemplate(entity);

				// set default category
				if (templateFileEntity.getCategory() == null) {
					Category ce = categoryRepository.findByName(Category.DEFAULT_CATEGORY);
					templateFileEntity.setCategory(ce);
				}
			}
		}
		
		entity = this.appTemplateRepository.saveAndFlush(entity);
		return map(entity);
	}

	public AppTemplate create(AppTemplate entity) throws ValidationException {
		Set<TemplateFile> templateFiles = entity.getTemplateFiles();
		this.appTemplateRepository.save(entity);

		if (templateFiles != null) {
			for (TemplateFile templateFileEntity : templateFiles) {
				templateFileEntity.setAppTemplate(entity);
				templateFileService.saveOrUpdate(templateFileEntity);
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
		AppTemplate dto = Mapper.convert(entity, AppTemplate.class, "templateFiles");
		Set<TemplateFile> ctDtos = Mapper.convertSet(entity.getTemplateFiles(), TemplateFile.class, "appTemplate");
		if (entity != null && entity.getTemplateFiles() != null) {
			for (TemplateFile templateFileDto : ctDtos) {
				Category category = Mapper.convert(templateFileDto.getCategory(), Category.class, "templates");
				templateFileDto.setCategory(category);
			}
		}
		dto.setTemplateFiles(ctDtos);
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
