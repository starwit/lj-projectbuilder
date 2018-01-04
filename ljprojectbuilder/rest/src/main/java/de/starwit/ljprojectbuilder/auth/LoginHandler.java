package de.starwit.ljprojectbuilder.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LoginHandler {
	
    final static String AUTH_SESSION_TOKEN = "de.starwit.ljpb.auth.token";
    final static String AUTH_USER_DATA_ID = "de.starwit.ljpb.auth.id";
	
	public void handleInitialAuthRequest(HttpServletResponse resp);
	public void handleTokenRequest(HttpServletRequest req, HttpServletResponse resp);
}
