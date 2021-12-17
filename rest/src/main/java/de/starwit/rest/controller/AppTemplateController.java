package de.starwit.rest.controller;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.starwit.dto.SaveAppTemplateDto;
import de.starwit.mapper.AppTemplateMapper;
import de.starwit.persistence.entity.AppTemplate;
import de.starwit.service.impl.AppTemplateService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("${rest.base-path}/apptemplates")
public class AppTemplateController {

	final static Logger LOG = LoggerFactory.getLogger(AppTemplateController.class);

    @Autowired
    private AppTemplateService appTemplateService;

	@Autowired
	private AppTemplateMapper appTemplateMapper;

	@Operation(summary = "Get appTemplate with id")
	@GetMapping(value = "/{templateId}")
	public AppTemplate findById(@PathVariable("templateId") Long id) {
		AppTemplate template = appTemplateService.findById(id);
		return template;
	}

	@Operation(summary = "Create appTemplate (location, branch, description, credentialsRequired)")
	@PutMapping
	public AppTemplate save(@RequestBody SaveAppTemplateDto appTemplateDto) {
		return update(appTemplateDto);
	}

	@Operation(summary = "Update appTemplate (location, branch, description, credentialsRequired)")
	@PostMapping
	public AppTemplate update(@RequestBody SaveAppTemplateDto appTemplateDto) {
		AppTemplate appTemplate = new AppTemplate();
		if (appTemplateDto.getId() != null) {
			appTemplate = appTemplateService.findById(appTemplateDto.getId());
		}
		appTemplate.setLocation(appTemplateDto.getLocation());
		appTemplate.setBranch(appTemplateDto.getBranch());
		appTemplate.setDescription(appTemplate.getDescription());
		appTemplate.setCredentialsRequired(appTemplateDto.isCredentialsRequired());
		return appTemplateService.saveOrUpdate(appTemplate);
	}

	@Operation(summary = "Get all appTemplates")
	@GetMapping
	public List<SaveAppTemplateDto> findAll() {
		return appTemplateMapper.convertToDtoList(appTemplateService.findAll());
	}

	@Operation(summary = "Delete appTemplate")
	@DeleteMapping(value = "/{id}")
	public void delete(@PathVariable("id") Long id) {
		appTemplateService.delete(id);
	}

	@ExceptionHandler(value = { EntityNotFoundException.class })
    public ResponseEntity<Object> handleException(EntityNotFoundException ex) {
        LOG.info("AppTemplate not found. ", ex.getMessage());
        return new ResponseEntity<Object>("AppTemplate not found.", HttpStatus.NOT_FOUND);
    }
}
