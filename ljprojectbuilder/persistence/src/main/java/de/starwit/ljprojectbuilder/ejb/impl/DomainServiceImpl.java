package de.starwit.ljprojectbuilder.ejb.impl;


import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import de.starwit.ljprojectbuilder.ejb.DomainService;
import de.starwit.ljprojectbuilder.entity.DomainEntity;

@Stateless(name = "DomainService")
public class DomainServiceImpl extends AbstractServiceImpl<DomainEntity> implements DomainService {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public List<DomainEntity> findAllDomainsByProject(Long projectId) {
		String queryStr = "from DomainEntity e where e.project.id = :projectId";
		
		TypedQuery<DomainEntity> query = getEntityManager().createQuery(queryStr, DomainEntity.class);
		query.setParameter("projectId", projectId);
		
		return query.getResultList();
	}

}



    
