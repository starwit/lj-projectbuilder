package de.starwit.ljprojectbuilder.ejb.impl;


import javax.ejb.Stateless;
import de.starwit.ljprojectbuilder.ejb.DomainService;
import de.starwit.ljprojectbuilder.entity.DomainEntity;

@Stateless(name = "DomainService")
public class DomainServiceImpl extends AbstractServiceImpl<DomainEntity> implements DomainService {
	
	private static final long serialVersionUID = 1L;

}



    
