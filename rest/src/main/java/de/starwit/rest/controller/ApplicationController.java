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
import de.starwit.allowedroles.IsUser;
import de.starwit.dto.ApplicationDto;
import de.starwit.mapper.ApplicationMapper;
import de.starwit.persistence.entity.App;
import de.starwit.service.impl.AppService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("${rest.base-path}/apps")
public class ApplicationController implements GroupsInterface {

	static final Logger LOG = LoggerFactory.getLogger(ApplicationController.class);

	@Autowired
	private AppService appService;

	@Autowired
	private ApplicationMapper appMapper;

	@Operation(summary = "Get all apps")
	@GetMapping
	public List<ApplicationDto> findAll(Principal principal) {
		List<String> groups = getGroups(principal);
		return appMapper.convertToDtoList(appService.findByGroups(groups));
	}

	@Operation(summary = "Get app with id")
	@GetMapping(value = "/{id}")
	public ApplicationDto findById(@PathVariable("id") Long id) {
		App entity = appService.findById(id);
		return appMapper.convertToDto(entity);
	}

	@IsAdmin
	@IsUser
	@Operation(summary = "Create app")
	@PostMapping
	public ApplicationDto save(@Valid @RequestBody ApplicationDto dto) {
		return update(dto);
	}

	@IsAdmin
	@IsUser
	@Operation(summary = "Update app")
	@PutMapping
	public ApplicationDto update(@Valid @RequestBody ApplicationDto dto) {
		App app = new App();
		if (dto.getId() != null) {
			app = appService.findById(dto.getId());
		}
		List<String> assignedGroups = app.getGroups();
		assignedGroups = identifyAssignedGroups(dto.getGroupsToAssign(), assignedGroups, dto.getUserGroups());
		app.setGroups(assignedGroups);
		app = appService.saveOrUpdate(app);
		return appMapper.convertToDto(app);
	}

	@IsAdmin
	@IsUser
	@Operation(summary = "Updates only app properties. List of entities will not be saved, changed or removed.")
	@PostMapping(value = "/app-properties")
	public ApplicationDto updateProperties(@Valid @RequestBody ApplicationDto dto) {
		App app = appMapper.convertToEntity(dto);
		App appOld = appService.findById(app.getId());
		app.setDomains(appOld.getDomains());
		app = appService.saveOrUpdate(app);
		return appMapper.convertToDto(app);
	}

	@IsAdmin
	@IsUser
	@Operation(summary = "Delete app with id")
	@DeleteMapping(value = "/{id}")
	public void delete(@PathVariable("id") Long id) {
		appService.delete(id);
	}

	@ExceptionHandler(value = { EntityNotFoundException.class })
	public ResponseEntity<Object> handleException(EntityNotFoundException ex) {
		LOG.info("App not found. {}", ex.getMessage());
		return new ResponseEntity<>("App not found.", HttpStatus.NOT_FOUND);
	}
}
