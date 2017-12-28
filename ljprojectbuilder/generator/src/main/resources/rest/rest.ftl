package de.${project.packagePrefix?lower_case}.${project.title?lower_case}.api.rest;


import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import de.${project.packagePrefix?lower_case}.${project.title?lower_case}.api.rest.response.EntityResponse;
import de.${project.packagePrefix?lower_case}.${project.title?lower_case}.ejb.${domain.name}Service;
import de.${project.packagePrefix?lower_case}.${project.title?lower_case}.entity.${domain.name}Entity;

@Path("/${domain.name?lower_case}")
@Consumes("application/json")
@Produces("application/json")
public class ${domain.name}Rest extends AbstractRest<${domain.name}Entity> {
	
	@Inject
	protected ${domain.name}Service service;
	
	@Override
	protected ${domain.name}Service getService() {
		return service;
	}
	
	//Create
	@Path("/")
	@PUT
	public EntityResponse<${domain.name}Entity> create(${domain.name}Entity entity) {
		return super.createGeneric(entity);
	}

	//Update
	@Path("/")
	@POST
	public EntityResponse<${domain.name}Entity> update(${domain.name}Entity entity) {
		return super.updateGeneric(entity);
	}
}