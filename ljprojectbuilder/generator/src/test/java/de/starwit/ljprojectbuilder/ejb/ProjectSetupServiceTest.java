package de.starwit.ljprojectbuilder.ejb;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.starwit.generator.services.ProjectSetupService;

//@RunWith(Arquillian.class)
public class ProjectSetupServiceTest {
	
	protected ProjectSetupService service;
	
	@PersistenceContext
	EntityManager entityManager;
	
//	@Test
	public void setupAndGenerateProjectTest()  {
		
	}
}