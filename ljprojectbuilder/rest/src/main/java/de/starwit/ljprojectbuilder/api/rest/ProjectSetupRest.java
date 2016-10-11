package de.starwit.ljprojectbuilder.api.rest;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import de.starwit.ljprojectbuilder.ejb.ProjectSetupService;
import de.starwit.ljprojectbuilder.entity.ProjectEntity;
import de.starwit.ljprojectbuilder.response.EntityResponse;
import de.starwit.ljprojectbuilder.response.ResponseCode;
import de.starwit.ljprojectbuilder.response.ResponseMetadata;

@Path("/projectsetup")
@Consumes("application/json")
@Produces("application/json")
public class ProjectSetupRest {
	
	@Inject
	protected ProjectSetupService service;
	
	@Path("/setup")
	@POST
	public EntityResponse<ProjectEntity> setup(ProjectEntity entity) {
		ResponseMetadata responseMetadata = service.copyProjectTemplate(entity);
		EntityResponse<ProjectEntity> response = new EntityResponse<>(entity);
		response.setMetadata(responseMetadata);
		return response;
	}
	
	@Path("/setupall")
	@POST
	public EntityResponse<ProjectEntity> setupAll(ProjectEntity entity) {
		ResponseMetadata responseMetadata = service.copyProjectTemplate(entity);
		if (ResponseCode.OK.equals(responseMetadata.getResponseCode())) {
			responseMetadata = service.renameProject(entity);
		}
		if (ResponseCode.OK.equals(responseMetadata.getResponseCode())) {
			responseMetadata = service.renamePackage(entity);
		}
		
		EntityResponse<ProjectEntity> response = new EntityResponse<>(entity);
		response.setMetadata(responseMetadata);
		return response;
	}
	
	@Path("/rename")
	@POST
	public EntityResponse<ProjectEntity> renameProject(ProjectEntity entity) {
		EntityResponse<ProjectEntity> response = new EntityResponse<>(entity);
		ResponseMetadata responseMetadata = service.renameProject(entity);
		response.setMetadata(responseMetadata);
		return response;
	}
	
	@Path("/renamepackage")
	@POST
	public EntityResponse<ProjectEntity> renamePackage(ProjectEntity entity) {
		EntityResponse<ProjectEntity> response = new EntityResponse<>(entity);
		ResponseMetadata responseMetadata = service.renamePackage(entity);
		response.setMetadata(responseMetadata);
		return response;
	}
}