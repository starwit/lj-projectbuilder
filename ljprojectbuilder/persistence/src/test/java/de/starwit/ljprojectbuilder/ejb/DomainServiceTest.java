package de.starwit.ljprojectbuilder.ejb;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.runner.RunWith;

import de.starwit.ljprojectbuilder.entity.DomainEntity;
import de.starwit.ljprojectbuilder.entity.ProjectEntity;
import de.starwit.ljprojectbuilder.entity.ProjectTemplateEntity;

@RunWith(Arquillian.class)
public class DomainServiceTest extends AbstractServiceTest<DomainService, DomainEntity> {
	
	@Inject
	private TemplateService templateService;
	
	@Inject
	private ProjectService projectService;
	
	@Override
	public void setService(DomainService service) {
		this.service = service;
	}

	@Override
	public void testCreate() {
		ProjectEntity pentity = new ProjectEntity();
		pentity.setTargetPath("D:/tmp/test/");
		ProjectTemplateEntity template = new ProjectTemplateEntity();
		template.setLocation("http://test");
		template.setTitle("testproject");
		template.setPackagePrefix("test");
		templateService.create(template);
		pentity.setTitle("lirejarp");
		pentity.setPackagePrefix("lirejarp");
		pentity.setTemplate(template);
		pentity = projectService.create(pentity);
		entity = new DomainEntity();
		entity.setProject(pentity);
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