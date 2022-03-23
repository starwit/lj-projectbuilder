package de.starwit.service.impl;

import de.starwit.persistence.entity.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * AbstractService used as template for all service implementations and implements the basic
 * functionality (create, read, update, delete, and other basic stuff).
 *
 * @param <E>
 * @author Anett
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
        entity = this.getRepository().save(entity);
        return entity;
    }

    default public void delete(Long id) {
        this.getRepository().deleteById(id);
    }

}

