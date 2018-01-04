package de.starwit.ljprojectbuilder.auth;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * Implements github's OAuth2 method. Details see
 * https://developer.github.com/apps/building-oauth-apps/authorization-options-for-oauth-apps/
 * @author markus
 *
 */
public class GithubLoginHandler implements LoginHandler {
   
    private static final Logger LOG = Logger.getLogger(GithubLoginHandler.class);
    
    private Properties config;
	
	public GithubLoginHandler(Properties config) {
		this.config = config;
	}

	@Override
	public void handleInitialAuthRequest(HttpServletResponse resp) {
		String requestURL = "";
		requestURL += config.getProperty("provider_url");
		requestURL += "?client_id=" + config.getProperty("client_id");
		requestURL += "&redirect_uri=" + config.getProperty("landing_url") + "?request_token=true";
		LOG.info("sending initial request to github: " + requestURL);
		
		try {
			resp.sendRedirect(requestURL);
		} catch (IOException e) {
			gotoAuthErrorState(resp);
		}
	}

	@Override
	public void handleTokenRequest(HttpServletRequest req, HttpServletResponse resp) {
		LOG.info("make access token request to github");
		Map<String, String[]> params = req.getParameterMap();
		String authCode = (params.get("code"))[0]; 
		if(authCode == null) {
			gotoAuthErrorState(resp);
		}
		
		//request new access token
		String providerResponse = requestAccessToken(authCode);
		if("NO-TOKEN".equals(providerResponse)) {
			gotoAuthErrorState(resp);
		}
		//extract token from provider response
		Map<String,String> responseParams = extractAccessToken(providerResponse);
		
		if(responseParams.get("access_token") != null) {	
			//put new token to session
			HttpServletRequest request = (HttpServletRequest) req;
			HttpSession session = request.getSession();
			session.setAttribute(AUTH_SESSION_TOKEN, responseParams.get("access_token"));
		} else {
			gotoAuthErrorState(resp);
		}
		
		try {
			resp.sendRedirect("/ljprojectbuilder/");
		} catch (IOException e) {
			LOG.error("redirect to start page didn't work");
			e.printStackTrace();
		}

	}
	
	private String requestAccessToken(String authCode) {
		
		HttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(config.getProperty("access_token_url"));
		
		List<NameValuePair> accessTokenParams = new ArrayList<NameValuePair>(3);
		accessTokenParams.add(new BasicNameValuePair("code", authCode));
		accessTokenParams.add(new BasicNameValuePair("client_id", config.getProperty("client_id")));
		accessTokenParams.add(new BasicNameValuePair("client_secret", config.getProperty("client_secret")));
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(accessTokenParams, "UTF-8"));
			HttpResponse response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			return EntityUtils.toString(entity); 
		} catch (UnsupportedEncodingException e) {
			LOG.debug(e.getMessage());
		} catch (ParseException e) {
			LOG.debug(e.getMessage());
		} catch (IOException e) {
			LOG.debug(e.getMessage());
		}
		
		return "NO-TOKEN";
	}
	
	private Map<String, String> extractAccessToken(String content) {		
    	Map<String, String> parameterMap = new HashMap<String, String>();
        StringTokenizer st =  new StringTokenizer(content, "&");
        while(st.hasMoreElements()) {
        	String token = st.nextToken();      	

        	StringTokenizer tmpSt = new StringTokenizer(token, "=");
        	if (tmpSt.countTokens() == 2) {
        		parameterMap.put(tmpSt.nextToken(), tmpSt.nextToken());
        	}
        }
		return parameterMap;
	}
	
	private void gotoAuthErrorState(HttpServletResponse resp) {
		// means github auth is not available thus app is not working, error site
		LOG.error("login not working");
		try {
			resp.sendRedirect("/ljprojectbuilder?auth_error=true");
		} catch (IOException e1) {
			//means we can't even reach our own homepage... panic mode on
			e1.printStackTrace();
		}
	}
}
