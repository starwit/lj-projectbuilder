package de.starwit.ljprojectbuilder.ejb.impl;


import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import de.starwit.ljprojectbuilder.ejb.DomainService;
import de.starwit.ljprojectbuilder.entity.DomainEntity;
import de.starwit.ljprojectbuilder.entity.ProjectEntity;

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
	
	@Override
	public void setDomainSelected(Long domainId, boolean selected) {
		String queryStr = "update DomainEntity domain set domain.selected = :selected where domain.id = :domainId";
		
		Query query = getEntityManager().createQuery(queryStr);
		query.setParameter("domainId", domainId);
		query.setParameter("selected", selected);
		
		query.executeUpdate();
	}
	
	@Override
	public DomainEntity update(DomainEntity entity) {
		if (entity != null) {
			entity.setProject(getEntityManager().find(ProjectEntity.class, entity.getProject().getId()));
		}
		return super.update(entity);
	}
	
	@Override
	public DomainEntity create(DomainEntity entity) {
		if (entity != null) {
			entity.setProject(getEntityManager().find(ProjectEntity.class, entity.getProject().getId()));
		}
		return super.create(entity);
	}
}