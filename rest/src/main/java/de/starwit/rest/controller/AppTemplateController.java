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
import de.starwit.persistence.entity.AppTemplateEntity;
import de.starwit.persistence.response.EntityListResponse;
import de.starwit.persistence.response.EntityResponse;
import de.starwit.persistence.response.ResponseCode;
import de.starwit.persistence.response.ResponseMetadata;
import de.starwit.persistence.validation.EntityValidator;
import de.starwit.service.impl.AppTemplateService;

/**
 * Domain RestController
 * Have a look at the RequestMapping!!!!!!
 */
@RestController
@RequestMapping("${rest.base-path}/apptemplate")
public class AppTemplateController {

    @Autowired
    private AppTemplateService appTemplateService;
    
    private GenericController<AppTemplateEntity> genericController;
    
	@PostConstruct
	public void init() {
		genericController = new GenericController<AppTemplateEntity>();
		genericController.setService(appTemplateService);
	}

	@GetMapping("/query/all")
	public EntityListResponse<AppTemplateEntity> findAll() {
		return genericController.findAll();
	}

	@GetMapping(value = "/query/{id}")
	public EntityResponse<AppTemplateEntity> findById(@PathVariable("id") Long id) {
		return genericController.findById(id);
	}

	@PutMapping
	public EntityResponse<AppTemplateEntity> save(@RequestBody AppTemplateEntity entity) {
		EntityResponse<AppTemplateEntity> response = validateCodeTemplates(entity);
		return response == null ? genericController.createGeneric(entity) : response;
	}

	@PostMapping
	public EntityResponse<AppTemplateEntity> update(@RequestBody AppTemplateEntity entity) {
		EntityResponse<AppTemplateEntity> response = validateCodeTemplates(entity);
		return response == null ? genericController.updateGeneric(entity) : response;
	}

	@DeleteMapping(value = "/{id}")
	public EntityResponse<AppTemplateEntity> delete(@PathVariable("id") Long id) {
		return genericController.delete(id);
	}


	private EntityResponse<AppTemplateEntity> validateCodeTemplates(AppTemplateEntity entity) {
		if (entity.getCodeTemplates() != null) {
			for (CodeTemplateEntity codeTemplate : entity.getCodeTemplates()) {
				ResponseMetadata responseMetadata = EntityValidator.validate(codeTemplate);	
				if (responseMetadata.getResponseCode() == ResponseCode.NOT_VALID) {
					EntityResponse<AppTemplateEntity> response = new EntityResponse<AppTemplateEntity>();
					response.setMetadata(responseMetadata);
					return response;
				}
			}
		}
		
		return null;
	}

}
