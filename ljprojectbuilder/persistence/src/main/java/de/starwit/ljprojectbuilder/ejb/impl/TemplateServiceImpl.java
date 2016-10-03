package de.starwit.ljprojectbuilder.ejb.impl;


import javax.ejb.Stateless;

import de.starwit.ljprojectbuilder.ejb.TemplateService;
import de.starwit.ljprojectbuilder.entity.TemplateEntity;

@Stateless(name = "TemplateService")
public class TemplateServiceImpl extends AbstractServiceImpl<TemplateEntity> implements TemplateService {
	
	private static final long serialVersionUID = 1L;

}



    
