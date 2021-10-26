package de.starwit.rest.controller;

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

import de.starwit.dto.ApplicationDto;
import de.starwit.mapper.ApplicationMapper;
import de.starwit.persistence.entity.App;
import de.starwit.service.impl.AppService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("${rest.base-path}/apps")
public class ApplicationController implements ControllerInterface<ApplicationDto> {

    final static Logger LOG = LoggerFactory.getLogger(ApplicationController.class);

	@Autowired
	private AppService appService;

	@Autowired
	private ApplicationMapper appMapper;

    @GetMapping
	public List<ApplicationDto> findAll() {
		return appMapper.convertToDtoList(appService.findAll());
	}

	@GetMapping(value = "/{id}")
	public ApplicationDto findById(@PathVariable("id") Long id) {
		App entity = appService.findById(id);
		ApplicationDto dto = appMapper.convertToDto(entity);
		return dto;
	}

	@Operation(summary = "Create Application")
	@PutMapping
	public ApplicationDto save(@RequestBody ApplicationDto dto) {
		return update(dto);
	}

	@PostMapping
	public ApplicationDto update(@RequestBody ApplicationDto dto) {
		App app = appMapper.convertToEntity(dto);
		app = appService.saveOrUpdate(app);
		return appMapper.convertToDto(app);
	}

	@Operation(summary = "Updates only app properties. List of entities will not be saved, changed or removed.")
	@PostMapping(value="/appproperties")
	public ApplicationDto updateProperties(@Valid @RequestBody ApplicationDto dto) {
		App app = appMapper.convertToEntity(dto);
		App appOld = appService.findById(app.getId());
		app.setDomains(appOld.getDomains());
		app = appService.saveOrUpdate(app);
		return appMapper.convertToDto(app);
	}

	@DeleteMapping(value = "/{id}")
	public void delete(@PathVariable("id") Long id) {
		appService.delete(id);
	}

	@ExceptionHandler(value = { EntityNotFoundException.class })
    public ResponseEntity<Object> handleException(EntityNotFoundException ex) {
        LOG.info("App not found. ", ex.getMessage());
        return new ResponseEntity<Object>("App not found.", HttpStatus.NOT_FOUND);
    }  
}
