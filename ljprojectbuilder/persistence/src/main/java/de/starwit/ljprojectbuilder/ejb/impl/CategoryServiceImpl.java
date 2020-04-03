package de.starwit.ljprojectbuilder.ejb.impl;


import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import de.starwit.ljprojectbuilder.ejb.CategoryService;
import de.starwit.ljprojectbuilder.entity.CategoryEntity;

@Stateless(name = "CategoryService")
public class CategoryServiceImpl extends AbstractServiceImpl<CategoryEntity> implements CategoryService {
	
	private static final long serialVersionUID = 1L;
	final static Logger LOG = Logger.getLogger(CategoryServiceImpl.class);
	
	@Override
	public CategoryEntity findByName(String name) {
		String queryStr = "select category from CategoryEntity category where category.name = :name";
		
		//test
		TypedQuery<CategoryEntity> query = getEntityManager().createQuery(queryStr, CategoryEntity.class);
		query.setParameter("name", name);
		List<CategoryEntity> result = query.getResultList();
		
		if (result != null && result.size() > 0) {
			return result.get(0);
		}
		return null;
	}
}