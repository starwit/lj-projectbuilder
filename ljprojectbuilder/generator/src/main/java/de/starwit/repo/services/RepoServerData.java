package de.starwit.repo.services;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RepoServerData {
	private String baseURL;
	private String projectName = null;

	private String username;
	private String password;

	public String getRepoRequestURL() {
		String requestUrl = baseURL;
		if (projectName == null) {
			requestUrl += "users/" + username + "/repos";
		} else {
			requestUrl += "projects/" + projectName + "/repos";
		}
		return requestUrl;
	}
	
	
/*
	public RepoServerData(String username, String password) {
		this.username = username;
		this.password = password;
	}*/

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return this.username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return this.password;
	}

	public String getBaseURL() {
		return baseURL;
	}

	public void setBaseURL(String baseURL) {
		this.baseURL = baseURL;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
}
