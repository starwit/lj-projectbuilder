package de.starwit.ljprojectbuilder.api.rest;


import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import de.starwit.ljprojectbuilder.ejb.AttributeService;
import de.starwit.ljprojectbuilder.entity.AttributeEntity;
import de.starwit.ljprojectbuilder.response.EntityResponse;

@Path("/attribute")
@Consumes("application/json")
@Produces("application/json")
public class AttributeRest extends AbstractRest<AttributeEntity> {
	
	@Inject
	protected AttributeService service;
	
	@Override
	protected AttributeService getService() {
		return service;
	}
	
	//Create
	@Path("/")
	@PUT
	public EntityResponse<AttributeEntity> create(AttributeEntity entity) {
		return super.createGeneric(entity);
	}

	//Update
	@Path("/")
	@POST
	public EntityResponse<AttributeEntity> update(AttributeEntity entity) {
		return super.updateGeneric(entity);
	}
}