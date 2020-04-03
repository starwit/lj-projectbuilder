package de.starwit.ljprojectbuilder.api.rest;


import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import de.starwit.ljprojectbuilder.ejb.ProjectTemplateService;
import de.starwit.ljprojectbuilder.entity.CodeTemplateEntity;
import de.starwit.ljprojectbuilder.entity.ProjectTemplateEntity;
import de.starwit.ljprojectbuilder.response.EntityResponse;
import de.starwit.ljprojectbuilder.response.ResponseCode;
import de.starwit.ljprojectbuilder.response.ResponseMetadata;
import de.starwit.ljprojectbuilder.validation.EntityValidator;

@Path("/projecttemplate")
@Consumes("application/json")
@Produces("application/json")
public class ProjectTemplateRest extends AbstractRest<ProjectTemplateEntity> {
	
	@Inject
	protected ProjectTemplateService service;
	

	
	@Override
	protected ProjectTemplateService getService() {
		return service;
	}
	
	//Create
	@Path("/")
	@PUT
	public EntityResponse<ProjectTemplateEntity> create(ProjectTemplateEntity entity) {
		EntityResponse<ProjectTemplateEntity> response = validateCodeTemplates(entity);
		return response == null ? super.updateGeneric(entity) : response;
	}

	//Update
	@Path("/")
	@POST
	public EntityResponse<ProjectTemplateEntity> update(ProjectTemplateEntity entity) {
		EntityResponse<ProjectTemplateEntity> response =validateCodeTemplates(entity);
		return response == null ? super.updateGeneric(entity) : response;
	}

	private EntityResponse<ProjectTemplateEntity> validateCodeTemplates(ProjectTemplateEntity entity) {
		if (entity.getCodeTemplates() != null) {
			for (CodeTemplateEntity codeTemplate : entity.getCodeTemplates()) {
				ResponseMetadata responseMetadata = EntityValidator.validate(codeTemplate);	
				if (responseMetadata.getResponseCode() == ResponseCode.NOT_VALID) {
					EntityResponse<ProjectTemplateEntity> response = new EntityResponse<ProjectTemplateEntity>();
					response.setMetadata(responseMetadata);
					return response;
				}
			}
		}
		
		return null;
	}
	
}