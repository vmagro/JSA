package org.jsa.socal.mobile;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ConventionServlet extends HttpServlet {
	
	private static final String PARAM_CONVENTION_ID = "conv";
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		doPost(req,resp);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		int convId = Integer.parseInt(req.getParameter(PARAM_CONVENTION_ID));
		Convention conv = new Convention(convId);
		req.setAttribute("title", conv.getTitle());
		Calendar date = conv.getDate();
		req.setAttribute("date", date.get(Calendar.MONTH)+"/"+date.get(Calendar.DAY_OF_MONTH)+"/"+date.get(Calendar.YEAR));
		req.setAttribute("location", conv.getLocation());
		req.setAttribute("debates", conv.getDebates());
		
		
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/home.jsp");
		rd.forward(req, resp);
	}
}
