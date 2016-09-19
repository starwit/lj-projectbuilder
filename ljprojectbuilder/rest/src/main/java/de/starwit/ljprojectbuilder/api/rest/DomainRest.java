package de.starwit.ljprojectbuilder.api.rest;


import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import de.starwit.ljprojectbuilder.ejb.DomainService;
import de.starwit.ljprojectbuilder.ejb.ProjectService;
import de.starwit.ljprojectbuilder.entity.DataType;
import de.starwit.ljprojectbuilder.entity.DomainEntity;
import de.starwit.ljprojectbuilder.entity.ProjectEntity;
import de.starwit.ljprojectbuilder.response.EntityListResponse;
import de.starwit.ljprojectbuilder.response.EntityResponse;
import de.starwit.ljprojectbuilder.response.ResponseCode;
import de.starwit.ljprojectbuilder.response.ResponseMetadata;
import de.starwit.ljprojectbuilder.validation.EntityValidator;

@Path("/domain")
@Consumes("application/json")
@Produces("application/json")
public class DomainRest extends AbstractRest<DomainEntity> {
	
	@Inject
	protected DomainService service;
	
	@Inject
	protected ProjectService projectService;
	
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
	
	@Path("/query/domainsbyproject/{projectId}")
	@GET
	public EntityListResponse<DomainEntity> findAllDomainsByProject(@PathParam("projectId") Long projectId) {
		ProjectEntity projectEntity = projectService.findById(projectId);
		if (projectEntity == null) {
			EntityListResponse<DomainEntity> response = new EntityListResponse<DomainEntity>(null);
			ResponseMetadata responseMetadata = new ResponseMetadata(ResponseCode.NOT_FOUND, "responsecode.notfound");
			response.setMetadata(responseMetadata);
			return response;
		} else {
			List<DomainEntity> entities = getService().findAllDomainsByProject(projectId);
			EntityListResponse<DomainEntity> response = new EntityListResponse<DomainEntity>(entities);
			ResponseMetadata responseMetadata = EntityValidator.isNotEmpty(response.getResult());
			response.setMetadata(responseMetadata);
			return response;
		}
	}
	
	@Path("/query/types")
	@GET
	public DataType[] getTypes() {
		return DataType.values();
	}
}