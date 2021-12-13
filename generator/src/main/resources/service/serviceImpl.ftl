package de.${app.packagePrefix?lower_case}.${app.title?lower_case}.ejb.impl;


import javax.ejb.Stateless;
import de.${app.packagePrefix?lower_case}.${app.title?lower_case}.ejb.${domain.name}Service;
import de.${app.packagePrefix?lower_case}.${app.title?lower_case}.entity.${domain.name}Entity;

@Stateless(name = "${domain.name}Service")
public class ${domain.name}ServiceImpl extends AbstractServiceImpl<${domain.name}Entity> implements ${domain.name}Service {
	
	private static final long serialVersionUID = 1L;

}



    
