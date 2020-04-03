package de.spring.service.impl;

import java.util.Set;

import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.spring.persistence.entity.CategoryEntity;
import de.spring.persistence.entity.CodeTemplateEntity;
import de.spring.persistence.entity.ProjectTemplateEntity;
import de.spring.persistence.repository.ProjectTemplateRepository;

@Service
public class ProjectTemplateService {

	final static Logger LOG = LoggerFactory.getLogger(CodeTemplateService.class);

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private CodeTemplateService codeTemplateService;

	@Autowired
	private ProjectTemplateRepository projectTemplateRepository;

	public ProjectTemplateEntity update(ProjectTemplateEntity entity) throws ValidationException {
		Set<CodeTemplateEntity> codeTemplates = entity.getCodeTemplates();

		// CodeTemplateEntity might be not filled completely
		if (codeTemplates != null) {
			for (CodeTemplateEntity codeTemplateEntity : codeTemplates) {
				if (codeTemplateEntity.getId() == null) {
					codeTemplateEntity.setProjectTemplate(entity);
				} else {
					// Add properties not got from outside
					CodeTemplateEntity oldEntity = this.codeTemplateService.findById(codeTemplateEntity.getId());
					codeTemplateEntity.setProjects(oldEntity.getProjects());
					codeTemplateEntity.setProjectTemplate(entity);
					// existingCodeTemplateIds.remove(codeTemplateEntity.getId());
				}

				// set default category
				if (codeTemplateEntity.getCategory() == null) {
					CategoryEntity ce = categoryService.findByName(CategoryEntity.DEFAULT_CATEGORY);
					codeTemplateEntity.setCategory(ce);
				}
				// set category by provided name
				else if (codeTemplateEntity.getCategory().getId() == null) {
					String categoryName = codeTemplateEntity.getCategory().getName();
					categoryName = categoryName == null ? CategoryEntity.DEFAULT_CATEGORY : categoryName;
					CategoryEntity ce = categoryService.findByName(categoryName);
					codeTemplateEntity.setCategory(ce);
				}
			}
		}

		return this.projectTemplateRepository.saveAndFlush(entity);
	}

	public ProjectTemplateEntity create(ProjectTemplateEntity entity) throws ValidationException {
		Set<CodeTemplateEntity> codeTemplates = entity.getCodeTemplates();
		entity.setCodeTemplates(null);
		this.projectTemplateRepository.save(entity);

		if (codeTemplates != null) {
			for (CodeTemplateEntity codeTemplateEntity : codeTemplates) {
				codeTemplateEntity.setProjectTemplate(entity);
			}
			entity.setCodeTemplates(codeTemplates);
			this.projectTemplateRepository.save(entity);
		}

		this.projectTemplateRepository.flush();
		return entity;
	}
}
