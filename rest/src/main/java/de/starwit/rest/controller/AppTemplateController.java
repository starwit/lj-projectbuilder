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

import de.starwit.persistence.entity.TemplateFile;
import de.starwit.persistence.entity.AppTemplate;
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
    
    private GenericController<AppTemplate> genericController;
    
	@PostConstruct
	public void init() {
		genericController = new GenericController<AppTemplate>();
		genericController.setService(appTemplateService);
	}

	@GetMapping("/query/all")
	public EntityListResponse<AppTemplate> findAll() {
		return genericController.findAll();
	}

	@GetMapping(value = "/query/{id}")
	public EntityResponse<AppTemplate> findById(@PathVariable("id") Long id) {
		return genericController.findById(id);
	}

	@PutMapping
	public EntityResponse<AppTemplate> save(@RequestBody AppTemplate entity) {
		EntityResponse<AppTemplate> response = validateTemplateFiles(entity);
		return response == null ? genericController.createGeneric(entity) : response;
	}

	@PostMapping
	public EntityResponse<AppTemplate> update(@RequestBody AppTemplate entity) {
		EntityResponse<AppTemplate> response = validateTemplateFiles(entity);
		return response == null ? genericController.updateGeneric(entity) : response;
	}

	@DeleteMapping(value = "/{id}")
	public EntityResponse<AppTemplate> delete(@PathVariable("id") Long id) {
		return genericController.delete(id);
	}


	private EntityResponse<AppTemplate> validateTemplateFiles(AppTemplate entity) {
		if (entity.getTemplateFiles() != null) {
			for (TemplateFile templateFile : entity.getTemplateFiles()) {
				ResponseMetadata responseMetadata = EntityValidator.validate(templateFile);	
				if (responseMetadata.getResponseCode() == ResponseCode.NOT_VALID) {
					EntityResponse<AppTemplate> response = new EntityResponse<AppTemplate>();
					response.setMetadata(responseMetadata);
					return response;
				}
			}
		}
		
		return null;
	}

}
