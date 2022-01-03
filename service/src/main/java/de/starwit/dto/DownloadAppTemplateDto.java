package de.starwit.dto;

import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Contains all configuration for app setup and generator.
 * @author Anett Huebner
 *
 */
@Schema
public class DownloadAppTemplateDto {
	
	private Long appTemplateId;

	private String username;
	private String password;

	private String user;
	private String pass;

	/********************* GETTER, SETTER **************************/
	@Schema(defaultValue = "1")
	@NotNull
	public Long getAppTemplateId() {
		return appTemplateId;
	}

	public void setAppTemplateId(Long appTemplateId) {
		this.appTemplateId = appTemplateId;
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
