package de.${project.packagePrefix?lower_case}.${project.title?lower_case}.api.restapp;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
<#list (project.selectedDomains) as domain>
import de.${project.packagePrefix?lower_case}.${project.title?lower_case}.api.rest.${domain.name}Rest;
</#list>


@ApplicationPath("/api")
public class RestfulApplication extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		final Set<Class<?>> classes = new HashSet<Class<?>>();
		// register root resource
		<#list (project.selectedDomains) as domain>
		classes.add(${domain.name}Rest.class);
		</#list>

		return classes;
	}

}