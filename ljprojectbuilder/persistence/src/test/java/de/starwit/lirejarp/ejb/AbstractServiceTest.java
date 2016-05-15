package de.starwit.lirejarp.ejb;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.starwit.lirejarp.entity.AbstractEntity;
import de.starwit.lirejarp.exception.EntityNotFoundException;


public abstract class AbstractServiceTest<E extends AbstractService<T>, T extends AbstractEntity> {
	
	private static Logger LOG = Logger.getLogger(AbstractServiceTest.class);
	
	private static boolean INITIALIZED = false;
	protected static Long ID;
	
	protected E service;
	protected T entity;
	
	@PersistenceContext
	EntityManager entityManager;

	@Inject
    public abstract void setService(E service);
	
	
	
	@Inject
	public DataImportExportService dataImportExtortService;

	@Before
	public void loadTestData() {
		if (!INITIALIZED) {
			LOG.info("***************Data was imported.");
			dataImportExtortService.importAll();

			INITIALIZED = true;
		}
	}
	
    public E getService() {
		return service;
	}
    
    @Test
    @InSequence(1)
    public abstract void testCreate();
    
	@Test
	@InSequence(2)
	public void testFindById() {
		entity = getService().findById(ID);
		Assert.assertNotNull(entity);
	}
    
    @Test
    @InSequence(3)
    public abstract void testUpdate();
    
//	@Test
	@InSequence(4)
	public void testDelete() throws EntityNotFoundException {
		entity = getService().findById(ID);
		Assert.assertNotNull(entity);
		getService().delete(ID);
		entity = getService().findById(ID);
		Assert.assertNull(entity);
	}
	
   
}
