package de.starwit.ljprojectbuilder.ejb.impl;


import javax.ejb.Stateless;

import de.starwit.ljprojectbuilder.ejb.ProjectTemplateService;
import de.starwit.ljprojectbuilder.entity.ProjectTemplateEntity;

@Stateless(name = "ProjectTemplateService")
public class ProjectTemplateServiceImpl extends AbstractServiceImpl<ProjectTemplateEntity> implements ProjectTemplateService {
	
	private static final long serialVersionUID = 1L;

}



    
