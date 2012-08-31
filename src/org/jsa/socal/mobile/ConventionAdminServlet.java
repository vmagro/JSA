package org.jsa.socal.mobile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ConventionAdminServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		doPost(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
	
		String action = req.getParameter("action");
		if(action != null && !action.equals("")){
			if(action.equals("update-convention")){
				int id = Integer.parseInt(req.getParameter("id"));
				Convention c = null;
				if(id == -1) //new convention
					c = new Convention();
				else
					c = new Convention(id);
				c.setTitle(req.getParameter("title"));
				c.setLocation(req.getParameter("location"));
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
				Date date = new Date();
				try {
					date = formatter.parse(req.getParameter("date"));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				cal.setTimeInMillis(date.getTime());
				c.setDate(cal);
				System.out.println(c);
				c.save();
			}
		}
		
		req.setAttribute("conventions", Convention.getConventions());
		
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/convention_admin.jsp");
		rd.forward(req, resp);
	}
}
