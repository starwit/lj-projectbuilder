package de.starwit.ljprojectbuilder.ejb;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.runner.RunWith;

import de.starwit.ljprojectbuilder.entity.DomainEntity;

@RunWith(Arquillian.class)
public class DomainServiceTest extends AbstractServiceTest<DomainService, DomainEntity> {
	
	@Override
	public void setService(DomainService service) {
		this.service = service;
	}

	@Override
	public void testCreate() {
		entity = new DomainEntity();
		entity.setName("Domain");
		entity = getService().create(entity);
		ID = entity.getId();
		Assert.assertNotNull(entity.getId());
		Assert.assertEquals("Domain", entity.getName());
	}

	@Override
	public void testUpdate() {
		entity = getService().findById(ID);
		entity.setName("DomainChanged");
		entity = getService().update(entity);
		Assert.assertEquals("DomainChanged", entity.getName());
	}

}