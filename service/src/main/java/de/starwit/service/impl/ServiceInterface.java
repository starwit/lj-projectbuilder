package de.starwit.service.impl;

import java.util.List;

import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.starwit.persistence.entity.AbstractEntity;
import de.starwit.persistence.exception.EntityNotFoundException;

/**
 * AbstractService used as template for all service implementations and implements the basic 
 * functionality (create, read, update, delete, and other basic stuff).
 * 
 * @author Anett
 *
 * @param <E>
 */
public interface ServiceInterface<E extends AbstractEntity<Long>> {

	static Logger LOG = LoggerFactory.getLogger(ServiceInterface.class);

	public void delete(Long id) throws EntityNotFoundException;

	public E saveOrUpdate(E entity) throws ValidationException;
	
	public List<E> findAll();

	public E findById(Long id);
	
	public default E create(E entity) throws ValidationException {
		return saveOrUpdate(entity);
	}
	
	public default E update(E entity) throws ValidationException {
		return saveOrUpdate(entity);
	}
}
