package org.jsa.socal.mobile;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class DebateAdminServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		doPost(req,resp);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		
		String action = req.getParameter("action");
		if(action == null)
			action = "";
		if(action.equalsIgnoreCase("update-debate")){
			int debateId = Integer.parseInt(req.getParameter("id"));
			Debate d = null;
			if(debateId == -1)
				d = new Debate(); //does not already exist, make new debate
			else
				d = new Debate(debateId); //get existing debate
			d.setResolution(req.getParameter("resolution"));
			System.out.println(req.getParameter("title"));
			d.setTitle(req.getParameter("title"));
			d.setConventionId(Long.parseLong(req.getParameter("convention")));
			System.out.println(d);
			d.save();
		}
		
		String convid = req.getParameter("convention");
		if(convid != null){
			Convention c = Convention.getConvention(Integer.parseInt(convid));
			if(c != null){
				req.setAttribute("debates", c.getDebates());
				System.out.println("sending "+c.getDebates().size()+" debates");
				req.setAttribute("convention", c);
			}
		}
		
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/debate_admin.jsp");
		rd.forward(req, resp);
	}
}
