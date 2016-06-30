package de.starwit.ljprojectbuilder.api.rest;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import de.starwit.ljprojectbuilder.ejb.ProjectService;
import de.starwit.ljprojectbuilder.entity.ProjectEntity;
import de.starwit.ljprojectbuilder.response.EntityResponse;
import de.starwit.ljprojectbuilder.response.ResponseMetadata;

@Path("/project")
@Consumes("application/json")
@Produces("application/json")
public class ProjectRest extends AbstractRest<ProjectEntity> {
	
	@Inject
	protected ProjectService service;
	
	@Override
	protected ProjectService getService() {
		return service;
	}
	
	//Create
	@Path("/")
	@PUT
	public EntityResponse<ProjectEntity> create(ProjectEntity entity) {
		return super.createGeneric(entity);
	}

	//Update
	@Path("/")
	@POST
	public EntityResponse<ProjectEntity> update(ProjectEntity entity) {
		return super.updateGeneric(entity);
	}
	
	@Path("/generate")
	@POST
	public EntityResponse<ProjectEntity> generate(ProjectEntity entity) {
		ResponseMetadata responseMetadata = service.copyProjectTemplate(entity);
		EntityResponse<ProjectEntity> response = new EntityResponse<>(entity);
		response.setMetadata(responseMetadata);
		return response;
	}
	
	@Path("/rename")
	@POST
	public EntityResponse<ProjectEntity> renameProject(ProjectEntity entity) {
		service.renameAll(entity);
		EntityResponse<ProjectEntity> response = new EntityResponse<>(entity);
		ResponseMetadata responseMetadata = service.renameAll(entity);
		response.setMetadata(responseMetadata);
		return response;
	}
}