package de.starwit.ljprojectbuilder.ejb;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.runner.RunWith;

import de.starwit.ljprojectbuilder.entity.ProjectEntity;
import de.starwit.ljprojectbuilder.entity.ProjectTemplateEntity;

@RunWith(Arquillian.class)
public class ProjectServiceTest extends AbstractServiceTest<ProjectService, ProjectEntity> {
	
	@Inject
	private ProjectTemplateService templateService;
	
	@Override
	public void setService(ProjectService service) {
		this.service = service;
	}

	@Override
	public void testCreate() {
		ProjectEntity entity = new ProjectEntity();
		entity.setPackagePrefix("test");
		entity.setTitle("Test");
		entity.setTargetPath("test");

		ProjectTemplateEntity template = new ProjectTemplateEntity();
		template.setLocation("http://test");
		template.setTitle("testproject");
		template.setPackagePrefix("test");
		templateService.create(template);
		entity.setTemplate(template);
		entity = getService().create(entity);
		ID = entity.getId();
		Assert.assertNotNull(entity.getId());
		Assert.assertEquals("test", entity.getPackagePrefix());
	}

	@Override
	public void testUpdate() {
		ProjectEntity entity = getService().findById(ID);
		entity.setTitle("TestChanged");
		entity = getService().update(entity);
		Assert.assertEquals("TestChanged", entity.getTitle());
		Assert.assertEquals("test", entity.getPackagePrefix());
	}
}