package de.${package}.${appName?lower_case}.ejb;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.runner.RunWith;

import de.${package}.${appName?lower_case}.entity.${domain}Entity;

@RunWith(Arquillian.class)
public class ${domain}ServiceTest extends AbstractServiceTest<${domain}Service, ${domain}Entity> {
	
	@Override
	public void setService(${domain}Service service) {
		this.service = service;
	}

	@Override
	public void testCreate() {
		entity = new ${domain}Entity();
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