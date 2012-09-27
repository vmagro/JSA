package org.jsa.socal.mobile;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ConventionServlet extends HttpServlet {
	
	private static final String PARAM_CONVENTION_ID = "id";
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		doPost(req,resp);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		int convId = Integer.parseInt(req.getParameter(PARAM_CONVENTION_ID));
		Convention conv = Convention.getConvention(convId);
		req.setAttribute("title", conv.getTitle());
		req.setAttribute("date", conv.getDateString());
		req.setAttribute("location", conv.getLocation());
		req.setAttribute("agenda", conv.getAgenda());
		
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/convention.jsp");
		rd.forward(req, resp);
	}
}
