package de.starwit.ljprojectbuilder.ejb;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.runner.RunWith;

import de.starwit.ljprojectbuilder.entity.TemplateEntity;

@RunWith(Arquillian.class)
public class TemplateServiceTest extends AbstractServiceTest<TemplateService, TemplateEntity> {
	
	@Override
	public void setService(TemplateService service) {
		this.service = service;
	}

	@Override
	public void testCreate() {
		entity = new TemplateEntity();
		entity.setTemplateLocation("http://www.mastertheboss.com");
		entity.setTemplatePackagePrefix("test");
		entity.setTemplateTitle("Test");
		entity = getService().create(entity);
		ID = entity.getId();
		Assert.assertNotNull(entity.getId());
		Assert.assertEquals("Test", entity.getTemplateTitle());
	}

	@Override
	public void testUpdate() {
		entity = getService().findById(ID);
		entity.setTemplateTitle("TestChanged");
		entity = getService().update(entity);
		Assert.assertNotNull(entity.getId());
		Assert.assertEquals("TestChanged", entity.getTemplateTitle());
	}
}