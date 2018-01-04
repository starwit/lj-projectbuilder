package de.starwit.ljprojectbuilder.auth;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * Generic filter to be used for OAuth2 based authentication. This filter 
 * checks if user has a certain object containing access token on his HTTPSession.
 * If not external authentication is triggered. 
 * 
 * @author markus
 *
 */
public class CheckTokenFilter implements Filter {
	
    private static final Logger LOG = Logger.getLogger(CheckTokenFilter.class);
    
    private LoginHandler loginHandler;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		Properties config = loadPropertiesFromFile();

		if (config != null) {
			loginHandler = new GithubLoginHandler(config); //TODO make injectable
			LOG.info("check if loginhandler lives " + loginHandler);
		} else {
			LOG.error("couldn't load login property file. App will not work!");
		}
	}

	private Properties loadPropertiesFromFile() {
		File configDir = new File(System.getProperty("catalina.base"), "conf");
		File configFile = new File(configDir, "oauth-provider-github.properties");
		InputStream stream;
		try {
			stream = new FileInputStream(configFile);
			Properties props = new Properties();
			props.load(stream);
			return props;
		} catch (IOException e) {
			LOG.error("login props file not loaded: " + e.getMessage());
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpSession session = request.getSession();
		Object data = session.getAttribute(LoginHandler.AUTH_SESSION_TOKEN);
		
		if(req.getParameter("auth_error") != null) {
			//TODO general error handling, if auth failed
		}
		
		if(data == null) {
			//means no user data available -> authenticate against identity provider
			if(hasDebugParameter()) {
				//login with static test user
				session.setAttribute(LoginHandler.AUTH_SESSION_TOKEN, "debug-token");
				session.setAttribute(LoginHandler.AUTH_USER_DATA_ID, "1");
			} else {
				LOG.debug("no user data available handle remote login " + req.getParameter("request_token"));
				if(req.getParameter("request_token") == null) {
					// user auth not started yet -> refer to external auth provider
					loginHandler.handleInitialAuthRequest((HttpServletResponse)resp);
					return;					
				} else {
					// user successfully authenticated against external provider -> request access token
					loginHandler.handleTokenRequest((HttpServletRequest) req, (HttpServletResponse)resp);
					return;
				}
			}
		}
		chain.doFilter(req, resp);
	}

	private boolean hasDebugParameter() {
		if(System.getProperty("useDebugUser") != null) {
			return true;
		}
		return false;
	}

	@Override
	public void destroy() {
	}
}