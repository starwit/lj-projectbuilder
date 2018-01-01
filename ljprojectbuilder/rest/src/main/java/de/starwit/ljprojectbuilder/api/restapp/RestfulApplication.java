package de.starwit.ljprojectbuilder.api.restapp;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import de.starwit.ljprojectbuilder.api.rest.AttributeRest;
import de.starwit.ljprojectbuilder.api.rest.CategoryRest;
import de.starwit.ljprojectbuilder.api.rest.CodeTemplateRest;
import de.starwit.ljprojectbuilder.api.rest.DomainRest;
import de.starwit.ljprojectbuilder.api.rest.ProjectRest;
import de.starwit.ljprojectbuilder.api.rest.ProjectSetupRest;
import de.starwit.ljprojectbuilder.api.rest.ProjectTemplateRest;


@ApplicationPath("/api")
public class RestfulApplication extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		
		final Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(AttributeRest.class);
		classes.add(ProjectSetupRest.class);
		classes.add(DomainRest.class);
		classes.add(ProjectRest.class);
		classes.add(ProjectTemplateRest.class);
		classes.add(CategoryRest.class);
		classes.add(CodeTemplateRest.class);
		return classes;
	}
}