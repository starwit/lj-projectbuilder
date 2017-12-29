package de.starwit.ljprojectbuilder.api.rest;


import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import de.starwit.ljprojectbuilder.ejb.TemplateService;
import de.starwit.ljprojectbuilder.entity.ProjectTemplateEntity;
import de.starwit.ljprojectbuilder.response.EntityResponse;

@Path("/template")
@Consumes("application/json")
@Produces("application/json")
public class TemplateRest extends AbstractRest<ProjectTemplateEntity> {
	
	@Inject
	protected TemplateService service;
	
	@Override
	protected TemplateService getService() {
		return service;
	}
	
	//Create
	@Path("/")
	@PUT
	public EntityResponse<ProjectTemplateEntity> create(ProjectTemplateEntity entity) {
		return super.createGeneric(entity);
	}

	//Update
	@Path("/")
	@POST
	public EntityResponse<ProjectTemplateEntity> update(ProjectTemplateEntity entity) {
		return super.updateGeneric(entity);
	}
}