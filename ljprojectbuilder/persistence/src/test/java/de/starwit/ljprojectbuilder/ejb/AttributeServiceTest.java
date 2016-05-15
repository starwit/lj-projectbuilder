package de.starwit.ljprojectbuilder.ejb;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.runner.RunWith;

import de.starwit.ljprojectbuilder.entity.AttributeEntity;

@RunWith(Arquillian.class)
public class AttributeServiceTest extends AbstractServiceTest<AttributeService, AttributeEntity> {
	
	@Override
	public void setService(AttributeService service) {
		this.service = service;
	}

	@Override
	public void testCreate() {
		entity = new AttributeEntity();
		entity = getService().create(entity);
		ID = entity.getId();
		Assert.assertNotNull(entity.getId());
		Assert.fail("Not yet implemented");
		
	}

	@Override
	public void testUpdate() {
		entity = getService().findById(ID);
		entity = getService().update(entity);
		Assert.fail("Not yet implemented");
	}

}