package de.starwit.${appName?lower_case}.ejb.impl;


import javax.ejb.Stateless;
import de.starwit.${appName?lower_case}.ejb.${domain}Service;
import de.starwit.${appName?lower_case}.entity.${domain}Entity;

@Stateless(name = "${domain}Service")
public class ${domain}ServiceImpl extends AbstractServiceImpl<${domain}Entity> implements ${domain}Service {
	
	private static final long serialVersionUID = 1L;

}



    
