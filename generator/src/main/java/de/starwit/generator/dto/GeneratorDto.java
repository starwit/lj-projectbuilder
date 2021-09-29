package de.starwit.generator.dto;

import java.util.Set;

import de.starwit.persistence.entity.Domain;
import de.starwit.persistence.entity.App;

/**
 * Contains all configuration for app setup and generator.
 * @author Anett Huebner
 *
 */
public class GeneratorDto {
	
	private App app;

	
	private Set<Domain> selectedDomains;
	
	private String username;

	private String password;

	private String user;
	private String pass;

	/********************* GETTER, SETTER **************************/
	
	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}

	public Set<Domain> getSelectedDomains() {
		return selectedDomains;
	}

	public void setSelectedDomains(Set<Domain> selectedDomains) {
		this.selectedDomains = selectedDomains;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
}
