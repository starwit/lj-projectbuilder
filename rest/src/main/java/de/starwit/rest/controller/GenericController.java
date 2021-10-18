package de.starwit.rest.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;

import de.starwit.persistence.entity.AbstractEntity;
import de.starwit.persistence.response.EntityListResponse;
import de.starwit.persistence.response.EntityResponse;
import de.starwit.persistence.response.ResponseCode;
import de.starwit.persistence.response.ResponseMetadata;
import de.starwit.persistence.validation.EntityValidator;
import de.starwit.service.impl.ServiceInterface;

public class GenericController<E extends AbstractEntity<Long>> {
	
	private static final Logger LOG = LoggerFactory.getLogger("fileAppender");

	private ServiceInterface<E, ? extends JpaRepository<E, Long>> service;
	
	private enum EditMode {
		CREATE,
		UPDATE,
		SAVEORUPDATE;
	}
	
	
	public ServiceInterface<E, ? extends JpaRepository<E, Long>> getService() {
		return service;
	}
	
	public void setService(ServiceInterface<E, ? extends JpaRepository<E, Long>> service) {
		this.service = service;
	}
	
	public EntityResponse<E> createGeneric(E entity) {
		return editGeneric(entity, EditMode.CREATE);
	}
	
	public EntityResponse<E> updateGeneric(E entity) {
		return editGeneric(entity, EditMode.UPDATE);
	}
	
	public EntityResponse<E> editGeneric(E entity) {
		return editGeneric(entity, EditMode.SAVEORUPDATE);
	}

	public EntityResponse<E> editGeneric(E entity, EditMode editMode) {
	
		EntityResponse<E> response = new EntityResponse<E>();
		ResponseMetadata responseMetadata = EntityValidator.validate(entity);
		response.setMetadata(responseMetadata);
		
		if (!ResponseCode.NOT_VALID.equals(responseMetadata.getResponseCode())) {
			E interalEntity;
			LOG.debug("************ FrontendService for " + getService().getClass().getSimpleName());
			interalEntity = getService().saveOrUpdate(entity);
			response.setResult(interalEntity);
			response.setMetadata(EntityValidator.savedResultExists(interalEntity));
		}
		return response;
	}


	/**
	 * returns a flat entity with NO associated entities
	 * 
	 * @param id
	 * @return
	 */
	public EntityResponse<E> findById(Long id) {
		E entity = getService().findById(id);
		EntityResponse<E> rw = new EntityResponse<E>(entity);
		if (entity == null) {
			rw.setMetadata(new ResponseMetadata(ResponseCode.NOT_FOUND, "response.notfound"));
		}
		return rw;
	}
	
	//Delete
	public EntityResponse<E> delete(Long id) {
		EntityResponse<E> response = new EntityResponse<E>();
		ResponseMetadata responseMetadata = new ResponseMetadata();

		getService().delete(id);
		responseMetadata.setResponseCode(ResponseCode.OK);
		responseMetadata.setMessage("Der Eintrag wurde gelöscht.");

		response.setMetadata(responseMetadata);
		return response;
	}
	
	
	public EntityListResponse<E> findAll() {
		List<E> entities = getService().findAll();
		EntityListResponse<E> response = new EntityListResponse<E>(entities);
		ResponseMetadata responseMetadata = EntityValidator.isNotEmpty(response.getResult());
		response.setMetadata(responseMetadata);
		return response;
	}
}
