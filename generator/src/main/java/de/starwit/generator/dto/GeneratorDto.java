package de.starwit.generator.dto;

import java.util.Set;

import de.starwit.persistence.entity.DomainEntity;
import de.starwit.persistence.entity.ProjectEntity;

/**
 * Contains all configuration for project setup and generator.
 * @author Anett Huebner
 *
 */
public class GeneratorDto {
	
	private ProjectEntity project;

	
	private Set<DomainEntity> selectedDomains;
	
	private String username;

	private String password;

	private String user;
	private String pass;

	/********************* GETTER, SETTER **************************/
	
	public ProjectEntity getProject() {
		return project;
	}

	public void setProject(ProjectEntity project) {
		this.project = project;
	}

	public Set<DomainEntity> getSelectedDomains() {
		return selectedDomains;
	}

	public void setSelectedDomains(Set<DomainEntity> selectedDomains) {
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
