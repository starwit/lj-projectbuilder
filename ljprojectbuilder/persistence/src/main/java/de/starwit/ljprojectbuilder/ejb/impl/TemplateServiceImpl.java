package de.starwit.ljprojectbuilder.ejb.impl;


import javax.ejb.Stateless;

import de.starwit.ljprojectbuilder.ejb.TemplateService;
import de.starwit.ljprojectbuilder.entity.ProjectTemplateEntity;

@Stateless(name = "TemplateService")
public class TemplateServiceImpl extends AbstractServiceImpl<ProjectTemplateEntity> implements TemplateService {
	
	private static final long serialVersionUID = 1L;

}



    
