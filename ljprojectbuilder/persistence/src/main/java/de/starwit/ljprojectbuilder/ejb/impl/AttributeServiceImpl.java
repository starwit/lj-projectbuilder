package de.starwit.ljprojectbuilder.ejb.impl;


import javax.ejb.Stateless;
import de.starwit.ljprojectbuilder.ejb.AttributeService;
import de.starwit.ljprojectbuilder.entity.AttributeEntity;

@Stateless(name = "AttributeService")
public class AttributeServiceImpl extends AbstractServiceImpl<AttributeEntity> implements AttributeService {
	
	private static final long serialVersionUID = 1L;

}



    
