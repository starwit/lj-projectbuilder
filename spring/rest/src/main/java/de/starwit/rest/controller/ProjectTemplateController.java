package de.starwit.rest.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.starwit.persistence.entity.CodeTemplateEntity;
import de.starwit.persistence.entity.ProjectTemplateEntity;
import de.starwit.persistence.response.EntityListResponse;
import de.starwit.persistence.response.EntityResponse;
import de.starwit.persistence.response.ResponseCode;
import de.starwit.persistence.response.ResponseMetadata;
import de.starwit.persistence.validation.EntityValidator;
import de.starwit.service.impl.ProjectTemplateService;

/**
 * Domain RestController
 * Have a look at the RequestMapping!!!!!!
 */
@RestController
@RequestMapping("${rest.base-path}/projecttemplate")
public class ProjectTemplateController {

    @Autowired
    private ProjectTemplateService projectTemplateService;
    
    private GenericController<ProjectTemplateEntity> genericController;
    
	@PostConstruct
	public void init() {
		genericController = new GenericController<ProjectTemplateEntity>();
		genericController.setService(projectTemplateService);
	}

	@GetMapping("/query/all")
	public EntityListResponse<ProjectTemplateEntity> findAll() {
		return genericController.findAll();
	}

	@GetMapping(value = "/query/{id}")
	public EntityResponse<ProjectTemplateEntity> findById(@PathVariable("id") Long id) {
		return genericController.findById(id);
	}

	@PutMapping
	public EntityResponse<ProjectTemplateEntity> save(@RequestBody ProjectTemplateEntity entity) {
		EntityResponse<ProjectTemplateEntity> response = validateCodeTemplates(entity);
		return response == null ? genericController.editGeneric(entity) : response;
	}

	@PostMapping
	public EntityResponse<ProjectTemplateEntity> update(@RequestBody ProjectTemplateEntity entity) {
		EntityResponse<ProjectTemplateEntity> response = validateCodeTemplates(entity);
		return response == null ? genericController.editGeneric(entity) : response;
	}

	@DeleteMapping(value = "/{id}")
	public EntityResponse<ProjectTemplateEntity> delete(@PathVariable("id") Long id) {
		return genericController.delete(id);
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
