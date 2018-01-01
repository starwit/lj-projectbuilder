package de.starwit.ljprojectbuilder.ejb.impl;


import java.util.Set;

import javax.ejb.Stateless;
import javax.validation.ValidationException;

import de.starwit.ljprojectbuilder.ejb.ProjectTemplateService;
import de.starwit.ljprojectbuilder.entity.CodeTemplateEntity;
import de.starwit.ljprojectbuilder.entity.ProjectTemplateEntity;

@Stateless(name = "ProjectTemplateService")
public class ProjectTemplateServiceImpl extends AbstractServiceImpl<ProjectTemplateEntity> implements ProjectTemplateService {
	
	private static final long serialVersionUID = 1L;
	
	public ProjectTemplateEntity update(ProjectTemplateEntity entity) throws ValidationException {
		Set<CodeTemplateEntity> codeTemplates = entity.getCodeTemplates();
		for (CodeTemplateEntity codeTemplateEntity : codeTemplates) {
			CodeTemplateEntity oldEntity = getEntityManager().find(CodeTemplateEntity.class, codeTemplateEntity.getId());
			codeTemplateEntity.setProjects(oldEntity.getProjects());
			codeTemplateEntity.setProjectTemplate(entity);
		}
		entity = getEntityManager().merge(entity);
		getEntityManager().flush();
		return entity;
	}

}



    
