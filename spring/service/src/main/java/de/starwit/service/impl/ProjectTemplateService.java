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
import de.starwit.persistence.entity.ProjectTemplateEntity;
import de.starwit.persistence.exception.EntityNotFoundException;
import de.starwit.persistence.repository.CategoryRepository;
import de.starwit.persistence.repository.ProjectTemplateRepository;

@Service
public class ProjectTemplateService implements ServiceInterface<ProjectTemplateEntity> {

	final static Logger LOG = LoggerFactory.getLogger(ProjectTemplateService.class);

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private CodeTemplateService codeTemplateService;

	@Autowired
	private ProjectTemplateRepository projectTemplateRepository;
	
	public ProjectTemplateEntity update(ProjectTemplateEntity entity) throws ValidationException {
		Set<CodeTemplateEntity> codeTemplates = entity.getCodeTemplates();
		map(entity);

		// CodeTemplateEntity might be not filled completely
		if (codeTemplates != null) {
			for (CodeTemplateEntity codeTemplateEntity : codeTemplates) {
				codeTemplateEntity.setProjectTemplate(entity);

				// set default category
				if (codeTemplateEntity.getCategory() == null) {
					CategoryEntity ce = categoryRepository.findByName(CategoryEntity.DEFAULT_CATEGORY);
					codeTemplateEntity.setCategory(ce);
				}
			}
		}
		
		entity = this.projectTemplateRepository.saveAndFlush(entity);
		return map(entity);
	}

	public ProjectTemplateEntity create(ProjectTemplateEntity entity) throws ValidationException {
		Set<CodeTemplateEntity> codeTemplates = entity.getCodeTemplates();
		this.projectTemplateRepository.save(entity);

		if (codeTemplates != null) {
			for (CodeTemplateEntity codeTemplateEntity : codeTemplates) {
				codeTemplateEntity.setProjectTemplate(entity);
				codeTemplateService.saveOrUpdate(codeTemplateEntity);
			}
		}
		return map(entity);
	}

	@Override
	public List<ProjectTemplateEntity> findAll() {
		List<ProjectTemplateEntity> entities = this.projectTemplateRepository.findAll();
		List<ProjectTemplateEntity> dtos = new ArrayList<ProjectTemplateEntity>();
		for (ProjectTemplateEntity entity : entities) {
			ProjectTemplateEntity dto = map(entity);
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public ProjectTemplateEntity findById(Long id) {
		ProjectTemplateEntity entity = this.projectTemplateRepository.findById(id).orElse(null);		
		return map(entity);
	}

	private ProjectTemplateEntity map(ProjectTemplateEntity entity) {
		ProjectTemplateEntity dto = Mapper.convert(entity, ProjectTemplateEntity.class, "codeTemplates");
		Set<CodeTemplateEntity> ctDtos = Mapper.convertSet(entity.getCodeTemplates(), CodeTemplateEntity.class, "projectTemplate");
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
	public ProjectTemplateEntity saveOrUpdate(ProjectTemplateEntity entity) {
		map(entity);
		return this.projectTemplateRepository.save(entity);
	}

	@Override
	public void delete(Long id) throws EntityNotFoundException {
		this.projectTemplateRepository.deleteById(id);
	}

}
