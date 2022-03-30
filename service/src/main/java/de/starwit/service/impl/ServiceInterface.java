package de.starwit.service.impl;

import de.starwit.persistence.entity.AbstractEntity;
import de.starwit.persistence.exception.NotificationException;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;

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

    public default List<E> findAll() {
        return this.getRepository().findAll();
    }

    public default E findById(Long id) {
        return this.getRepository().findById(id).orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
    }

    public default E saveOrUpdate(E entity) {
        entity = this.getRepository().save(entity);
        return entity;
    }

    public default void delete(Long id) throws NotificationException {
        this.getRepository().deleteById(id);
    }
}
