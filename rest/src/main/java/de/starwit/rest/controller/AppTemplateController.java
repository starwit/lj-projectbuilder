package de.starwit.rest.controller;

import java.security.Principal;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

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

import de.starwit.allowedroles.IsAdmin;
import de.starwit.dto.SaveAppTemplateDto;
import de.starwit.mapper.AppTemplateMapper;
import de.starwit.persistence.entity.AppTemplate;
import de.starwit.service.impl.AppTemplateService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("${rest.base-path}/apptemplates")
public class AppTemplateController implements GroupsInterface {

	static final Logger LOG = LoggerFactory.getLogger(AppTemplateController.class);

	@Autowired
	private AppTemplateService appTemplateService;

	@Autowired
	private AppTemplateMapper appTemplateMapper;

	@Operation(summary = "Get appTemplate with id")
	@GetMapping(value = "/{templateId}")
	public AppTemplate findById(@PathVariable("templateId") Long id) {
		return appTemplateService.findById(id);
	}

	@IsAdmin
	@Operation(summary = "Create appTemplate (location, branch, description, credentialsRequired)")
	@PostMapping
	public AppTemplate save(@Valid @RequestBody SaveAppTemplateDto appTemplateDto) {
		return update(appTemplateDto);
	}

	@IsAdmin
	@Operation(summary = "Update appTemplate (location, branch, description, credentialsRequired)")
	@PutMapping
	public AppTemplate update(@Valid @RequestBody SaveAppTemplateDto appTemplateDto) {
		AppTemplate appTemplate = new AppTemplate();
		if (appTemplateDto.getId() != null) {
			appTemplate = appTemplateService.findById(appTemplateDto.getId());
		}
		appTemplate.setLocation(appTemplateDto.getLocation());
		appTemplate.setBranch(appTemplateDto.getBranch());
		appTemplate.setDescription(appTemplateDto.getDescription());
		appTemplate.setCredentialsRequired(appTemplateDto.isCredentialsRequired());
		List<String> assignedGroups = appTemplate.getGroups();
		assignedGroups = identifyAssignedGroups(appTemplateDto.getGroupsToAssign(), assignedGroups, appTemplateDto.getUserGroups());
		appTemplate.setGroups(assignedGroups);
		return appTemplateService.saveOrUpdate(appTemplate);
	}

	@Operation(summary = "Get all appTemplates")
	@GetMapping
	public List<SaveAppTemplateDto> findAll(Principal principal) {
		List<String> groups = getGroups(principal);
		return appTemplateMapper.convertToDtoList(appTemplateService.findByGroups(groups));
	}

	@IsAdmin
	@Operation(summary = "Delete appTemplate")
	@DeleteMapping(value = "/{id}")
	public void delete(@PathVariable("id") Long id) {
		appTemplateService.delete(id);
	}

	@ExceptionHandler(value = { EntityNotFoundException.class })
	public ResponseEntity<Object> handleException(EntityNotFoundException ex) {
		LOG.info("AppTemplate not found. {} ", ex.getMessage());
		return new ResponseEntity<>("AppTemplate not found.", HttpStatus.NOT_FOUND);
	}
}
