package de.starwit.dto;

import de.starwit.persistence.entity.AbstractEntity;

/**
 * Contains all configuration for app setup and generator.
 * @author Anett Huebner
 *
 */
public class GeneratorDto extends AbstractEntity<Long> {
	
	private ApplicationDto app;

	
	private String username;

	private String password;

	private String user;
	private String pass;

	/********************* GETTER, SETTER **************************/
	
	public ApplicationDto getApp() {
		return app;
	}

	public void setApp(ApplicationDto app) {
		this.app = app;
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
