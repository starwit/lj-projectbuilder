package de.starwit.ljprojectbuilder.auth;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns="/HasUserToken")
public class UserTokenServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String token = (String)session.getAttribute(LoginHandler.AUTH_SESSION_TOKEN);
		
		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();
		if(token != null) {
			out.print("{\"hasToken\":\"true\"}");
		} else {
			out.print("{\"hasToken\":\"false\"}");
		}
	}
}
