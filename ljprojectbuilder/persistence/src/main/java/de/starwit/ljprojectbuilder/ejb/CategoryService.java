package de.starwit.ljprojectbuilder.ejb;

import java.io.Serializable;

import javax.ejb.Local;

import de.starwit.ljprojectbuilder.entity.CategoryEntity;

@Local
public interface CategoryService extends Serializable, AbstractService<CategoryEntity> {
	
	CategoryEntity findByName(String name);
}


    
