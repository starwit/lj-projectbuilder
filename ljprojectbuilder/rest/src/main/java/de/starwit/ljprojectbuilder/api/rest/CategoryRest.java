package de.starwit.ljprojectbuilder.api.rest;


import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import de.starwit.ljprojectbuilder.ejb.CategoryService;
import de.starwit.ljprojectbuilder.entity.CategoryEntity;
import de.starwit.ljprojectbuilder.response.EntityResponse;

@Path("/category")
@Consumes("application/json")
@Produces("application/json")
public class CategoryRest extends AbstractRest<CategoryEntity> {
	
	@Inject
	protected CategoryService service;
	

	
	@Override
	protected CategoryService getService() {
		return service;
	}
	
	//Create
	@Path("/")
	@PUT
	public EntityResponse<CategoryEntity> create(CategoryEntity entity) {
		return super.createGeneric(entity);
	}

	//Update
	@Path("/")
	@POST
	public EntityResponse<CategoryEntity> update(CategoryEntity entity) {
		return super.updateGeneric(entity);
	}
}