package de.starwit.${appName?lower_case}.api.restapp;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
<#list (classnames) as name>
import de.starwit.${appName?lower_case}.api.rest.${name};
</#list>


@ApplicationPath("/api")
public class RestfulApplication extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		final Set<Class<?>> classes = new HashSet<Class<?>>();
		// register root resource
		<#list (classnames) as name>
		classes.add(${name}.class);
		</#list>

		return classes;
	}

}