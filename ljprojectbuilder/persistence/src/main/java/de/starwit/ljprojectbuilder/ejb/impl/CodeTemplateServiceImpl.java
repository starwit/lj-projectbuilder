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
}