package de.starwit.service.impl;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;

import de.starwit.persistence.entity.AbstractEntity;

/**
 * AbstractService used as template for all service implementations and implements the basic 
 * functionality (create, read, update, delete, and other basic stuff).
 * 
 * @author Anett
 *
 * @param <E>
 */
public interface ServiceInterface<E extends AbstractEntity<Long>, R extends JpaRepository<E, Long>> {

	static Logger LOG = LoggerFactory.getLogger(ServiceInterface.class);

	public R getRepository();

	default public List<E> findAll() {
        return this.getRepository().findAll();
    }

    default public E findById(Long id) {
        return this.getRepository().findById(id).orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
    }

    default public E saveOrUpdate(E entity) {
        this.getRepository().save(entity);
        return entity;
    }

    default public void delete(Long id) {
        this.getRepository().deleteById(id);
    }

}

