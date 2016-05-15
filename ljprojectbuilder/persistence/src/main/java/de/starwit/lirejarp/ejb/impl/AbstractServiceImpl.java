package de.starwit.ljprojectbuilderp.ejb.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import javax.validation.ValidationException;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import de.starwit.ljprojectbuilderp.ejb.AbstractService;
import de.starwit.ljprojectbuilderp.entity.AbstractEntity;
import de.starwit.ljprojectbuilderp.exception.EntityNotFoundException;
import de.starwit.ljprojectbuilderp.exception.IllegalIdException;

/**
 * AbstractService used as template for all service implementations and implements the basic 
 * functionality (create, read, update, delete, and other basic stuff).
 * 
 * @author Anett
 *
 * @param <E>
 */
public class AbstractServiceImpl<E extends AbstractEntity> implements AbstractService<E> {

	private static Logger LOG = Logger.getLogger(AbstractServiceImpl.class);

	@PersistenceContext
	private EntityManager entityManager;
	
	private E parentClass;

	@SuppressWarnings("unchecked")
	public Class<E> getParentClass() {
		if (parentClass == null) {
			try {
				parentClass = (E) ((Class<?>) ((ParameterizedType) this
						.getClass().getGenericSuperclass())
						.getActualTypeArguments()[0]).newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				LOG.error("Inherit class of AbstractEntity could not be resolved.");
			}
		}
		return (Class<E>) parentClass.getClass();
	}

	public E create(E entity) {
		getEntityManager().persist(entity);
		getEntityManager().flush();
		return entity;
	}

	public void delete(Long id) throws EntityNotFoundException {
		E entity = getEntityManager().find(getParentClass(), id);
		if (entity == null) {
			throw new EntityNotFoundException(id, getParentClass().getName());
		}
		getEntityManager().remove(entity);
	}

	public E update(E entity) throws ValidationException {
		entity = getEntityManager().merge(entity);
		getEntityManager().flush();
		return entity;
	}
	
	public List<E> findAll() {
		Class<E> clazz = getParentClass();
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<E> criteria = cb.createQuery(clazz);
		Root<E> r = criteria.from(getParentClass());
		criteria.select(r);
		TypedQuery<E> query = getEntityManager().createQuery(criteria);
		return query.getResultList();
	}
	
	public List<E> findAll(int firstResult, int maxResult) {
		Class<E> clazz = getParentClass();
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<E> criteria = cb.createQuery(clazz);
		Root<E> r = criteria.from(getParentClass());
		criteria.select(r);
		TypedQuery<E> query = getEntityManager().createQuery(criteria);
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResult);
		return query.getResultList();
	}
	
	public Long countAll() {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Long> criteria = cb.createQuery(Long.class);
		Root<E> r = criteria.from(getParentClass());
		criteria.select(cb.count(r));
		TypedQuery<Long> query = getEntityManager().createQuery(criteria);
		return query.getSingleResult();
	}

	public E findById(Long id) {
		
		return getEntityManager().find(getParentClass(), id);
	}

	public E findByIdWithRelations(Long id, String... relations) {
		Class<E> clazz = getParentClass();
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<E> criteria = cb.createQuery(clazz);
		Root<E> root = criteria.from(clazz);

		criteria.where(cb.equal(root.get("id"), id));
		criteria.select(root);
		TypedQuery<E> query = getEntityManager().createQuery(criteria);
		E result = query.getSingleResult();
		
		for (String attributeName : relations) {
			try {
				Collection<?> value = (Collection<?>) PropertyUtils.getProperty(result, attributeName);
				value.size();
			} catch (IllegalAccessException | InvocationTargetException
					| NoSuchMethodException e) {
				LOG.error("Method for class " + clazz + " of property" + attributeName + " could not be resolved.", e);
			}
		}
		return result;
	}
	
	public E findByIdWithRelation(Long id, String relation) {
		Class<E> clazz = getParentClass();
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<E> criteria = cb.createQuery(clazz);
		Root<E> root = criteria.from(clazz);
		root.fetch(relation, JoinType.LEFT);
		criteria.where(cb.equal(root.get("id"), id));
		criteria.select(root);
		TypedQuery<E> query = getEntityManager().createQuery(criteria);
		E result = query.getSingleResult();
		
		return result;
	}

	public <T> T findByIdOrThrowException(Long id, Class<T> entityClass)
			throws IllegalIdException, EntityNotFoundException {
		if (id == null) {
			throw new IllegalIdException(entityClass.getName());
		}
		T entity = getEntityManager().find(entityClass, id);
		if (entity == null) {
			throw new EntityNotFoundException(id, entityClass.getName());
		}
		return entity;
	}

	
	public E findByIdOrThrowException(Long id)
			throws IllegalIdException, EntityNotFoundException {
		if (id == null) {
			throw new IllegalIdException(getParentClass().getName());
		}
		E entity = getEntityManager().find(getParentClass(), id);
		if (entity == null) {
			throw new EntityNotFoundException(id, getParentClass().getName());
		}
		return entity;
	}
	

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

    public EntityManager getEntityManager() {    
        return this.entityManager;    
    }   
	
}
