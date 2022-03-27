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
import de.starwit.dto.AppDto;
import de.starwit.mapper.AppMapper;
import de.starwit.persistence.entity.App;
import de.starwit.persistence.exception.NotificationException;
import de.starwit.rest.exception.NotificationDto;
import de.starwit.service.impl.AppService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("${rest.base-path}/apps")
public class AppController {

	static final Logger LOG = LoggerFactory.getLogger(AppController.class);

	@Autowired
	private AppService appService;

	@Autowired
	private AppMapper appMapper;

	@Operation(summary = "Get all apps")
	@GetMapping
	public List<AppDto> findAll(Principal principal) {
		List<String> groups = GroupsHelper.getGroups(principal);
		return appMapper.convertToDtoList(appService.findByGroups(groups));
	}

	@Operation(summary = "Get app with id")
	@GetMapping(value = "/{id}")
	public AppDto findById(@PathVariable("id") Long id) {
		App entity = appService.findById(id);
		return appMapper.convertToDto(entity);
	}

	@IsAdmin
	@IsUser
	@Operation(summary = "Create app")
	@PostMapping
	public AppDto save(@Valid @RequestBody AppDto dto) {
		return update(dto);
	}

	@IsAdmin
	@IsUser
	@Operation(summary = "Update app")
	@PutMapping
	public AppDto update(@Valid @RequestBody AppDto dto) {
		App app = new App();
		if (dto.getId() != null) {
			app = appService.findById(dto.getId());
		}
		List<String> assignedGroups = app.getGroups();
		assignedGroups = GroupsHelper.identifyAssignedGroups(dto.getGroupsToAssign(), assignedGroups, dto.getUserGroups());
		dto.setGroupsToAssign(assignedGroups);
		app = appService.saveOrUpdate(appMapper.convertToEntity(dto));
		return appMapper.convertToDto(app);
	}

	@IsAdmin
	@IsUser
	@Operation(summary = "Updates only app properties. List of entities will not be saved, changed or removed.")
	@PostMapping(value = "/app-properties")
	public AppDto updateProperties(@Valid @RequestBody AppDto dto) {
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
	public void delete(@PathVariable("id") Long id) throws NotificationException {
		appService.delete(id);
	}

	@ExceptionHandler(value = { EntityNotFoundException.class })
	public ResponseEntity<Object> handleException(EntityNotFoundException ex) {
		LOG.info("App not found. {}", ex.getMessage());
		NotificationDto output = new NotificationDto("error.app.notfound", "App not found.");
		return new ResponseEntity<>(output, HttpStatus.NOT_FOUND);
	}
}
