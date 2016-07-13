package de.starwit.ljprojectbuilder.ejb;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.runner.RunWith;

import de.starwit.ljprojectbuilder.entity.AttributeEntity;
import de.starwit.ljprojectbuilder.entity.DataType;

@RunWith(Arquillian.class)
public class AttributeServiceTest extends AbstractServiceTest<AttributeService, AttributeEntity> {
	
	@Override
	public void setService(AttributeService service) {
		this.service = service;
	}

	@Override
	public void testCreate() {
		entity = new AttributeEntity();
		entity.setName("Test");
		entity.setType(DataType.String);
		entity = getService().create(entity);
		ID = entity.getId();
		Assert.assertNotNull(entity.getId());
		Assert.assertEquals("Test", entity.getName());
		Assert.assertEquals(DataType.String, entity.getType());
		Assert.assertNull(entity.getPattern());
		Assert.assertNull(entity.getMax());
		Assert.assertNull(entity.getMin());
	}

	@Override
	public void testUpdate() {
		entity = getService().findById(ID);
		entity.setMax(20);
		entity.setMin(3);
		entity.setName("TestChanged");
		entity = getService().update(entity);
		Assert.assertNotNull(entity.getId());
		Assert.assertEquals("TestChanged", entity.getName());
		Assert.assertNull(entity.getPattern());
		Assert.assertEquals(new Integer(20), entity.getMax());
		Assert.assertEquals(new Integer(3), entity.getMin());
	}

}