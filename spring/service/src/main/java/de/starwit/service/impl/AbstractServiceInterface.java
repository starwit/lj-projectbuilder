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
public interface AbstractServiceInterface<E extends AbstractEntity<Long>> {

	static Logger LOG = LoggerFactory.getLogger(AbstractServiceInterface.class);

	public void delete(Long id) throws EntityNotFoundException;

	public E saveOrUpdate(E entity) throws ValidationException;
	
	public List<E> findAll();

	public E findById(Long id);
}
