package de.${app.packagePrefix?lower_case}.${app.title?lower_case}.api.restapp;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
<#list (app.selectedDomains) as domain>
import de.${app.packagePrefix?lower_case}.${app.title?lower_case}.api.rest.${domain.name}Rest;
</#list>


@ApplicationPath("/api")
public class RestfulApplication extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		final Set<Class<?>> classes = new HashSet<Class<?>>();
		// register root resource
		<#list (app.selectedDomains) as domain>
		classes.add(${domain.name}Rest.class);
		</#list>

		return classes;
	}

}