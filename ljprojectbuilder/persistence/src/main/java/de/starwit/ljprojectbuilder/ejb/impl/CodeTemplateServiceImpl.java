package de.starwit.ljprojectbuilder.ejb.impl;


import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import de.starwit.ljprojectbuilder.ejb.CodeTemplateService;
import de.starwit.ljprojectbuilder.entity.CodeTemplateEntity;

@Stateless(name = "CodeTemplateService")
public class CodeTemplateServiceImpl extends AbstractServiceImpl<CodeTemplateEntity> implements CodeTemplateService {
	
	private static final long serialVersionUID = 1L;
	final static Logger LOG = Logger.getLogger(CodeTemplateServiceImpl.class);
	
	@Override
	public List<CodeTemplateEntity> findAllCodeTemplatesByProject(Long projectId) {
		String queryStr = "select ct from CodeTemplateEntity ct inner join ct.projects projects where projects.id = :projectId";
		
		//test
		TypedQuery<CodeTemplateEntity> query = getEntityManager().createQuery(queryStr, CodeTemplateEntity.class);
		query.setParameter("projectId", projectId);
		
		return query.getResultList();
	}
	
	@Override
	public List<CodeTemplateEntity> findAllCodeTemplatesByProjectTemplate(Long projectTemplateId) {
		String queryStr = "select ct from CodeTemplateEntity ct where ct.projectTemplate.id = :projectTemplateId";
		
		//test
		TypedQuery<CodeTemplateEntity> query = getEntityManager().createQuery(queryStr, CodeTemplateEntity.class);
		query.setParameter("projectTemplateId", projectTemplateId);
		
		return query.getResultList();
	}
	
	@Override
	public List<Long> findAllCodeTemplateIdsByProjectTemplate(Long projectTemplateId) {
		String queryStr = "select ct.id from CodeTemplateEntity ct where ct.projectTemplate.id = :projectTemplateId";
		
		//test
		TypedQuery<Long> query = getEntityManager().createQuery(queryStr, Long.class);
		query.setParameter("projectTemplateId", projectTemplateId);
		
		return query.getResultList();
	}
}