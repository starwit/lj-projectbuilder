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

import de.starwit.persistence.entity.AppTemplate;
import de.starwit.service.impl.AppTemplateService;

/**
 * Domain RestController
 * Have a look at the RequestMapping!!!!!!
 */
@RestController
@RequestMapping("${rest.base-path}/apptemplate")
public class AppTemplateController {

	final static Logger LOG = LoggerFactory.getLogger(AppTemplateController.class);

    @Autowired
    private AppTemplateService appTemplateService;

    
	@GetMapping(value = "/{templateId}")
	public AppTemplate findById(@PathVariable("templateId") Long id) {
		AppTemplate template = appTemplateService.findById(id);
		return template;
	}

	@PutMapping
	public AppTemplate save(@RequestBody AppTemplate entity) {
		return update(entity);
	}

	@PostMapping
	public AppTemplate update(@RequestBody AppTemplate entity) {
		return appTemplateService.saveOrUpdate(entity);
	}

	@GetMapping
	public List<AppTemplate> findAll() {
		return appTemplateService.findAll();
	}

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
