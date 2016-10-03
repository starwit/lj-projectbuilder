package de.starwit.ljprojectbuilder.api.rest;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import de.starwit.ljprojectbuilder.ejb.ProjectService;
import de.starwit.ljprojectbuilder.entity.ProjectEntity;
import de.starwit.ljprojectbuilder.response.EntityResponse;

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
	
	@Path("/getnew")
	@GET
	public EntityResponse<ProjectEntity> getNew(@PathParam("entityId") Long id) {
		EntityResponse<ProjectEntity> rw = new EntityResponse<ProjectEntity>(new ProjectEntity());
		return rw;
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
}