package de.starwit.ljprojectbuilder.ejb;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.runner.RunWith;

import de.starwit.ljprojectbuilder.entity.ProjectTemplateEntity;

@RunWith(Arquillian.class)
public class TemplateServiceTest extends AbstractServiceTest<TemplateService, ProjectTemplateEntity> {
	
	@Override
	public void setService(TemplateService service) {
		this.service = service;
	}

	@Override
	public void testCreate() {
		entity = new ProjectTemplateEntity();
		entity.setLocation("http://www.mastertheboss.com");
		entity.setPackagePrefix("test");
		entity.setTitle("Test");
		entity = getService().create(entity);
		ID = entity.getId();
		Assert.assertNotNull(entity.getId());
		Assert.assertEquals("Test", entity.getTitle());
	}

	@Override
	public void testUpdate() {
		entity = getService().findById(ID);
		entity.setTitle("TestChanged");
		entity = getService().update(entity);
		Assert.assertNotNull(entity.getId());
		Assert.assertEquals("TestChanged", entity.getTitle());
	}
}