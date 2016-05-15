package de.starwit.ljprojectbuilderp.ejb;

import java.util.List;

import javax.persistence.EntityManager;
import javax.validation.ValidationException;

import de.starwit.ljprojectbuilderp.entity.AbstractEntity;
import de.starwit.ljprojectbuilderp.exception.EntityNotFoundException;

/**
 * Service Interface for all methods defined in the class AbstractDao. 
 * All concrete service interfaces should inherit from this class. 
 * The service implementations should extend the class AbstractDao and implement 
 * the concrete service interface.
 * 
 * @author Anett
 *
 * @param <E>
 */
public interface AbstractService<E extends AbstractEntity> {
	
	E create(E entity);
	
	void delete(Long id) throws EntityNotFoundException;
	
	E update(E entity) throws ValidationException;
	
	E findById(Long id);
	
	List<E> findAll();
	
	/**
	 * Use this service to get children of oneToMany relations without lazy loading problems.
	 * @param id
	 * @param attributeName the name of the attribute for the oneToMany relation
	 * @return
	 */
	E findByIdWithRelations(Long id, String... attributeName);
	
	E findByIdWithRelation(Long id, String attributeName);
	
	
	EntityManager getEntityManager();

	void setEntityManager(EntityManager em);

}
