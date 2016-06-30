package de.starwit.ljprojectbuilder.api.rest;


import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import de.starwit.ljprojectbuilder.ejb.DomainService;
import de.starwit.ljprojectbuilder.entity.DomainEntity;
import de.starwit.ljprojectbuilder.response.EntityResponse;

@Path("/domain")
@Consumes("application/json")
@Produces("application/json")
public class DomainRest extends AbstractRest<DomainEntity> {
	
	@Inject
	protected DomainService service;
	
	@Override
	protected DomainService getService() {
		return service;
	}
	
	//Create
	@Path("/")
	@PUT
	public EntityResponse<DomainEntity> create(DomainEntity entity) {
		return super.createGeneric(entity);
	}

	//Update
	@Path("/")
	@POST
	public EntityResponse<DomainEntity> update(DomainEntity entity) {
		return super.updateGeneric(entity);
	}
}