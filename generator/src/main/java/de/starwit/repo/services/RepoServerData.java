// package de.starwit.repo.services;

// import javax.xml.bind.annotation.XmlRootElement;

// @XmlRootElement
// public class RepoServerData {
// 	private String baseURL;
// 	private String appName = null;

// 	private String username;
// 	private String password;

// 	private String repoName;

// 	/**
// 	 * Bitbucket specific implementation for request url.
// 	 * It's either a user or a app repo.
// 	 * @return
// 	 */
// 	public String getRepoRequestURL() {
// 		String requestUrl = baseURL;
// 		if (appName == null || "".equals(appName)) {
// 			requestUrl += "users/" + username + "/repos";
// 		} else {
// 			requestUrl += "apps/" + appName + "/repos";
// 		}
// 		return requestUrl;
// 	}

// 	public void setUsername(String username) {
// 		this.username = username;
// 	}

// 	public String getUsername() {
// 		return this.username;
// 	}

// 	public void setPassword(String password) {
// 		this.password = password;
// 	}

// 	public String getPassword() {
// 		return this.password;
// 	}

// 	public String getBaseURL() {
// 		return baseURL;
// 	}

// 	public void setBaseURL(String baseURL) {
// 		this.baseURL = baseURL;
// 	}

// 	public String getAppName() {
// 		return appName;
// 	}

// 	public void setAppName(String appName) {
// 		this.appName = appName;
// 	}

// 	public String getRepoName() {
// 		return repoName;
// 	}

// 	public void setRepoName(String repoName) {
// 		this.repoName = repoName;
// 	}
// }
