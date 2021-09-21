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

import de.starwit.persistence.entity.AttributeEntity;
import de.starwit.persistence.entity.DataType;
import de.starwit.persistence.entity.DomainEntity;
import de.starwit.persistence.entity.App;
import de.starwit.persistence.exception.NotificationException;
import de.starwit.persistence.response.EntityListResponse;
import de.starwit.persistence.response.EntityResponse;
import de.starwit.persistence.response.ResponseCode;
import de.starwit.persistence.response.ResponseMetadata;
import de.starwit.persistence.validation.EntityValidator;
import de.starwit.service.impl.DomainService;
import de.starwit.service.impl.AppService;

/**
 * DomainEntity RestController Have a look at the RequestMapping!!!!!!
 */
@RestController
@RequestMapping("${rest.base-path}/domain")
public class DomainController {

	@Autowired
	private DomainService domainService;

	@Autowired
	private AppService appService;

	private GenericController<DomainEntity> genericController;

	@PostConstruct
	public void init() {
		genericController = new GenericController<DomainEntity>();
		genericController.setService(domainService);
	}

	@GetMapping("/query/all")
	public EntityListResponse<DomainEntity> findAll() {
		return genericController.findAll();
	}

	@GetMapping(value = "/query/{id}")
	public EntityResponse<DomainEntity> findById(@PathVariable("id") Long id) {
		return genericController.findById(id);
	}

	@PutMapping
	public EntityResponse<DomainEntity> save(@RequestBody DomainEntity domain) {
		return saveOrUpdate(domain);
	}

	private EntityResponse<DomainEntity> saveOrUpdate(DomainEntity domain) {
		EntityResponse<DomainEntity> response;
		try {
			response = validateAttibutes(domain);
		} catch (NotificationException nex) {
			response = new EntityResponse<DomainEntity>(null);
	        response.setMetadata(nex.getResponseMetadata());
	        return response;
		}
		return response == null ? genericController.editGeneric(domain) : response;
	}

	@PostMapping
	public EntityResponse<DomainEntity> update(@RequestBody DomainEntity domain) {
		return saveOrUpdate(domain);
	}

	@DeleteMapping(value = "/{id}")
	public EntityResponse<DomainEntity> delete(@PathVariable("id") Long id) {
		return this.genericController.delete(id);
	}

	// Custom Endpoints
	@GetMapping(value = "/query/domainsbyapp/{appId}")
	public EntityListResponse<DomainEntity> findAllDomainsByApp(@PathVariable("appId") Long appId) {
		App app;
		try {
			app = appService.findAppByIdOrThrowExeption(appId);
		} catch (NotificationException nex) {
           EntityListResponse<DomainEntity> response = new EntityListResponse<DomainEntity>(null);
           response.setMetadata(nex.getResponseMetadata());
           return response;
		}
		if (app == null) {
			EntityListResponse<DomainEntity> response = new EntityListResponse<DomainEntity>(null);
			ResponseMetadata responseMetadata = new ResponseMetadata(ResponseCode.NOT_FOUND, "responsecode.notfound");
			response.setMetadata(responseMetadata);
			return response;
		} else {
			List<DomainEntity> entities = this.domainService.findAllDomainsByApp(appId);
			EntityListResponse<DomainEntity> response = new EntityListResponse<DomainEntity>(entities);
			ResponseMetadata responseMetadata = EntityValidator.isNotEmpty(response.getResult());
			response.setMetadata(responseMetadata);
			return response;
		}
	}

	@GetMapping(value = "/query/types")
	public DataType[] getTypes() {
		return DataType.values();
	}
	
	private EntityResponse<DomainEntity> validateAttibutes(DomainEntity entity) throws NotificationException {
		App app = appService.findAppByIdOrThrowExeption(entity.getAppId());
		entity.setApp(app);
		EntityResponse<DomainEntity> response = new EntityResponse<DomainEntity>();
		
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
