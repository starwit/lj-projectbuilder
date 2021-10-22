package de.starwit.rest.controller;

import java.util.List;

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
import de.starwit.service.impl.TemplateFileService;
import de.starwit.service.impl.AppTemplateService;

/**
 * Domain RestController
 * Have a look at the RequestMapping!!!!!!
 */
@Deprecated
@RestController
@RequestMapping("${rest.base-path}/templatefile")
public class TemplateFileController {

    @Autowired
    private TemplateFileService templateFileService;

    @Autowired
    private AppTemplateService appTemplateService;
    
	private GenericController<TemplateFile> genericController;

	@PostConstruct
	public void init() {
		genericController = new GenericController<TemplateFile>();
		genericController.setService(templateFileService);
	}
	
	@GetMapping("/query/all")
	public EntityListResponse<TemplateFile> findAll() {
		return genericController.findAll();
	}

	@GetMapping(value = "/query/{id}")
	public EntityResponse<TemplateFile> findById(@PathVariable("id") Long id) {
		return genericController.findById(id);
	}

	@PutMapping
	public EntityResponse<TemplateFile> save(@RequestBody TemplateFile category) {
		return genericController.editGeneric(category);
	}

	@PostMapping
	public EntityResponse<TemplateFile> update(@RequestBody TemplateFile category) {
		return genericController.editGeneric(category);
	}

	@DeleteMapping(value = "/{id}")
	public EntityResponse<TemplateFile> delete(@PathVariable("id") Long id) {
		return genericController.delete(id);
	}
	
    @GetMapping(value = "/query/byapptemplate/{apptemplateId}")
    public EntityListResponse<TemplateFile> findAllTemplateFilesByAppTemplate(
      @PathVariable("apptemplateId") Long apptemplateId) {
      AppTemplate appTemplate = appTemplateService.findById(apptemplateId);
      if (appTemplate == null) {
        EntityListResponse<TemplateFile> response = new EntityListResponse<TemplateFile>(null);
        ResponseMetadata responseMetadata = new ResponseMetadata(ResponseCode.NOT_FOUND, "responsecode.notfound");
        response.setMetadata(responseMetadata);
        return response;
      } else {
        List<TemplateFile> entities = templateFileService.findAllTemplateFilesByAppTemplate(apptemplateId);
        EntityListResponse<TemplateFile> response = new EntityListResponse<TemplateFile>(entities);
        ResponseMetadata responseMetadata = EntityValidator.isNotEmpty(response.getResult());
        response.setMetadata(responseMetadata);
        return response;
      }
    }

}
