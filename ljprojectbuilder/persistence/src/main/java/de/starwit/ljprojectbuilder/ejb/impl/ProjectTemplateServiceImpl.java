package de.starwit.ljprojectbuilder.ejb.impl;


import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.ValidationException;

import org.apache.log4j.Logger;

import de.starwit.ljprojectbuilder.ejb.CategoryService;
import de.starwit.ljprojectbuilder.ejb.ProjectTemplateService;
import de.starwit.ljprojectbuilder.entity.CategoryEntity;
import de.starwit.ljprojectbuilder.entity.CodeTemplateEntity;
import de.starwit.ljprojectbuilder.entity.ProjectTemplateEntity;

@Stateless(name = "ProjectTemplateService")
public class ProjectTemplateServiceImpl extends AbstractServiceImpl<ProjectTemplateEntity> implements ProjectTemplateService {
	
	private static final long serialVersionUID = 1L;
	public final static Logger LOG = Logger.getLogger(ProjectTemplateServiceImpl.class);
	
	@Inject
	private CategoryService categoryService;
	
	public ProjectTemplateEntity update(ProjectTemplateEntity entity) throws ValidationException {
		
		Set<CodeTemplateEntity> codeTemplates = entity.getCodeTemplates();
		
		//CodeTemplateEntity might be not filled completely
		for (CodeTemplateEntity codeTemplateEntity : codeTemplates) {
			if (codeTemplateEntity.getId() == null) {
				codeTemplateEntity.setProjectTemplate(entity);
			} else {
				//Add properties not got from outside
				CodeTemplateEntity oldEntity = getEntityManager().find(CodeTemplateEntity.class, codeTemplateEntity.getId());
				codeTemplateEntity.setProjects(oldEntity.getProjects());
				codeTemplateEntity.setProjectTemplate(entity);
//				existingCodeTemplateIds.remove(codeTemplateEntity.getId());
			}
			
			//set default category
			if (codeTemplateEntity.getCategory() == null) {
				CategoryEntity ce = categoryService.findByName(CategoryEntity.DEFAULT_CATEGORY);
				codeTemplateEntity.setCategory(ce);
			}
			//set category by provided name
			else if (codeTemplateEntity.getCategory().getId() == null) {
				String categoryName = codeTemplateEntity.getCategory().getName();
				categoryName = categoryName == null ? CategoryEntity.DEFAULT_CATEGORY : categoryName;
				CategoryEntity ce = categoryService.findByName(categoryName);
				codeTemplateEntity.setCategory(ce);
			}
		}
		entity = getEntityManager().merge(entity);
		getEntityManager().flush();
		return entity;
	}
	
	public ProjectTemplateEntity create(ProjectTemplateEntity entity) throws ValidationException {
		Set<CodeTemplateEntity> codeTemplates = entity.getCodeTemplates();
		entity.setCodeTemplates(null);
		getEntityManager().persist(entity);
		if (codeTemplates != null) {
			for (CodeTemplateEntity codeTemplateEntity : codeTemplates) {
				codeTemplateEntity.setProjectTemplate(entity);
			}
			entity.setCodeTemplates(codeTemplates);
			getEntityManager().persist(entity);
		}
		getEntityManager().flush();
		return entity;
	}
}



    
