package de.${app.packagePrefix?lower_case}.${app.title?lower_case}.ejb;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.runner.RunWith;

import de.${app.packagePrefix?lower_case}.${app.title?lower_case}.entity.${domain.name}Entity;

@RunWith(Arquillian.class)
public class ${domain.name}ServiceTest extends AbstractServiceTest<${domain.name}Service, ${domain.name}Entity> {
	
	@Override
	public void setService(${domain.name}Service service) {
		this.service = service;
	}

	@Override
	public void testCreate() {
		entity = new ${domain.name}Entity();
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