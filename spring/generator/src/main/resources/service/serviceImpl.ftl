package de.${project.packagePrefix?lower_case}.${project.title?lower_case}.ejb.impl;


import javax.ejb.Stateless;
import de.${project.packagePrefix?lower_case}.${project.title?lower_case}.ejb.${domain.name}Service;
import de.${project.packagePrefix?lower_case}.${project.title?lower_case}.entity.${domain.name}Entity;

@Stateless(name = "${domain.name}Service")
public class ${domain.name}ServiceImpl extends AbstractServiceImpl<${domain.name}Entity> implements ${domain.name}Service {
	
	private static final long serialVersionUID = 1L;

}



    
