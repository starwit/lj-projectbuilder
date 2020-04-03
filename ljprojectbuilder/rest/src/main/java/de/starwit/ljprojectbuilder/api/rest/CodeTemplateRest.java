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

import de.starwit.ljprojectbuilder.ejb.CodeTemplateService;
import de.starwit.ljprojectbuilder.ejb.ProjectTemplateService;
import de.starwit.ljprojectbuilder.entity.CodeTemplateEntity;
import de.starwit.ljprojectbuilder.entity.ProjectTemplateEntity;
import de.starwit.ljprojectbuilder.response.EntityListResponse;
import de.starwit.ljprojectbuilder.response.EntityResponse;
import de.starwit.ljprojectbuilder.response.ResponseCode;
import de.starwit.ljprojectbuilder.response.ResponseMetadata;
import de.starwit.ljprojectbuilder.validation.EntityValidator;

@Path("/codetemplate")
@Consumes("application/json")
@Produces("application/json")
public class CodeTemplateRest extends AbstractRest<CodeTemplateEntity> {
	
	@Inject
	protected CodeTemplateService service;
	
	@Inject
	protected ProjectTemplateService projectTemplateService;
	
	@Override
	protected CodeTemplateService getService() {
		return service;
	}
	
	//Create
	@Path("/")
	@PUT
	public EntityResponse<CodeTemplateEntity> create(CodeTemplateEntity entity) {
		return super.createGeneric(entity);
	}

	//Update
	@Path("/")
	@POST
	public EntityResponse<CodeTemplateEntity> update(CodeTemplateEntity entity) {
		return super.updateGeneric(entity);
	}
	
	@Path("/query/byprojecttemplate/{projecttemplateId}")
	@GET
	public EntityListResponse<CodeTemplateEntity> findAllCodeTemplatesByProjectTemplate(@PathParam("projecttemplateId") Long projecttemplateId) {
		ProjectTemplateEntity projectTemplateEntity = projectTemplateService.findById(projecttemplateId);
		if (projectTemplateEntity == null) {
			EntityListResponse<CodeTemplateEntity> response = new EntityListResponse<CodeTemplateEntity>(null);
			ResponseMetadata responseMetadata = new ResponseMetadata(ResponseCode.NOT_FOUND, "responsecode.notfound");
			response.setMetadata(responseMetadata);
			return response;
		} else {
			List<CodeTemplateEntity> entities = getService().findAllCodeTemplatesByProjectTemplate(projecttemplateId);
			EntityListResponse<CodeTemplateEntity> response = new EntityListResponse<CodeTemplateEntity>(entities);
			ResponseMetadata responseMetadata = EntityValidator.isNotEmpty(response.getResult());
			response.setMetadata(responseMetadata);
			return response;
		}
	}
	
	
}