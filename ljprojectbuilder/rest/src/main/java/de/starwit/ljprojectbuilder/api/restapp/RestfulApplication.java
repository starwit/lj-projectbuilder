package de.starwit.ljprojectbuilder.api.restapp;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import de.starwit.ljprojectbuilder.api.rest.ProjectRest;
import de.starwit.ljprojectbuilder.api.rest.AbstractRest;


@ApplicationPath("/api")
public class RestfulApplication extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		final Set<Class<?>> classes = new HashSet<Class<?>>();
		// register root resource
		classes.add(ProjectRest.class);
		classes.add(AbstractRest.class);

		return classes;
	}

}