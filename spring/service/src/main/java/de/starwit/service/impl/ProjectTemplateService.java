package de.starwit.service.impl;

import java.util.List;
import java.util.Set;

import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.starwit.persistence.entity.CategoryEntity;
import de.starwit.persistence.entity.CodeTemplateEntity;
import de.starwit.persistence.entity.ProjectTemplateEntity;
import de.starwit.persistence.exception.EntityNotFoundException;
import de.starwit.persistence.repository.CategoryRepository;
import de.starwit.persistence.repository.ProjectTemplateRepository;

@Service
public class ProjectTemplateService implements AbstractServiceInterface<ProjectTemplateEntity> {

	final static Logger LOG = LoggerFactory.getLogger(CodeTemplateService.class);

	@Autowired
	private CategoryRepository categoryRepository;

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
					CategoryEntity ce = categoryRepository.findByName(CategoryEntity.DEFAULT_CATEGORY);
					;
					codeTemplateEntity.setCategory(ce);
				}
				// set category by provided name
				else if (codeTemplateEntity.getCategory().getId() == null) {
					String categoryName = codeTemplateEntity.getCategory().getName();
					categoryName = categoryName == null ? CategoryEntity.DEFAULT_CATEGORY : categoryName;
					CategoryEntity ce = categoryRepository.findByName(categoryName);
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

	@Override
	public List<ProjectTemplateEntity> findAll() {
		return this.projectTemplateRepository.findAll();
	}

	@Override
	public ProjectTemplateEntity findById(Long id) {
		return this.projectTemplateRepository.findById(id).orElse(null);
	}

	@Override
	public ProjectTemplateEntity saveOrUpdate(ProjectTemplateEntity entity) {
		return this.projectTemplateRepository.save(entity);
	}

	@Override
	public void delete(Long id) throws EntityNotFoundException {
		this.projectTemplateRepository.deleteById(id);
	}

}
