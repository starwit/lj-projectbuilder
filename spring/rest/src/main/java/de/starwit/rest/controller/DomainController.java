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

import de.starwit.dto.DomainDto;
import de.starwit.persistence.entity.AttributeEntity;
import de.starwit.persistence.entity.DataType;
import de.starwit.persistence.entity.ProjectEntity;
import de.starwit.persistence.exception.NotificationException;
import de.starwit.persistence.repository.ProjectRepository;
import de.starwit.persistence.response.EntityListResponse;
import de.starwit.persistence.response.EntityResponse;
import de.starwit.persistence.response.ResponseCode;
import de.starwit.persistence.response.ResponseMetadata;
import de.starwit.persistence.validation.EntityValidator;
import de.starwit.service.impl.DomainService;

/**
 * DomainEntity RestController Have a look at the RequestMapping!!!!!!
 */
@RestController
@RequestMapping("${rest.base-path}/domain")
public class DomainController {

	@Autowired
	private DomainService domainService;

	@Autowired
	private ProjectRepository projectRepository;

	private GenericController<DomainDto> genericController;

	@PostConstruct
	public void init() {
		genericController = new GenericController<DomainDto>();
		genericController.setService(domainService);
	}

	@GetMapping("/query/all")
	public EntityListResponse<DomainDto> findAll() {
		return genericController.findAll();
	}

	@GetMapping(value = "/query/{id}")
	public EntityResponse<DomainDto> findById(@PathVariable("id") Long id) {
		return genericController.findById(id);
	}

	@PutMapping
	public EntityResponse<DomainDto> save(@RequestBody DomainDto domain) {
		EntityResponse<DomainDto> response = validateAttibutes(domain);
		return response == null ? genericController.editGeneric(domain) : response;
	}

	@PostMapping
	public EntityResponse<DomainDto> update(@RequestBody DomainDto domain) {
		EntityResponse<DomainDto> response = validateAttibutes(domain);
		return response == null ? genericController.editGeneric(domain) : response;
	}

	@DeleteMapping(value = "/{id}")
	public EntityResponse<DomainDto> delete(@PathVariable("id") Long id) {
		return this.genericController.delete(id);
	}

	// Custom Endpoints
	@GetMapping(value = "/query/domainsbyproject/{projectId}")
	public EntityListResponse<DomainDto> findAllDomainsByProject(@PathVariable("projectId") Long projectId)
			throws NotificationException {
		ProjectEntity project = projectRepository.findProjectByIdOrThrowExeption(projectId);
		if (project == null) {
			EntityListResponse<DomainDto> response = new EntityListResponse<DomainDto>(null);
			ResponseMetadata responseMetadata = new ResponseMetadata(ResponseCode.NOT_FOUND, "responsecode.notfound");
			response.setMetadata(responseMetadata);
			return response;
		} else {
			List<DomainDto> entities = this.domainService.findAllDomainsByProject(projectId);
			EntityListResponse<DomainDto> response = new EntityListResponse<DomainDto>(entities);
			ResponseMetadata responseMetadata = EntityValidator.isNotEmpty(response.getResult());
			response.setMetadata(responseMetadata);
			return response;
		}
	}

	@GetMapping(value = "/query/types")
	public DataType[] getTypes() {
		return DataType.values();
	}
	
	private EntityResponse<DomainDto> validateAttibutes(DomainDto entity) {
		EntityResponse<DomainDto> response = new EntityResponse<DomainDto>();
		
		if (entity.getAttributes() != null) {
			for (AttributeEntity attribute : entity.getAttributes()) {
				ResponseMetadata responseMetadata = EntityValidator.validate(attribute);	
				if (responseMetadata.getResponseCode() == ResponseCode.NOT_VALID) {
					response.setMetadata(responseMetadata);
					return response;
				}
			}
		}
		
		return null;
	}
}
