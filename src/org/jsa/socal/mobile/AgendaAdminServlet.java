package org.jsa.socal.mobile;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class AgendaAdminServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		doPost(req,resp);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		
		String action = req.getParameter("action");
		if(action == null)
			action = "";
		if(action.equalsIgnoreCase("update-agenda")){
			int agendaId = Integer.parseInt(req.getParameter("id"));
			AgendaTopic topic = null;
			if(agendaId == -1)
				topic = new AgendaTopic(); //does not already exist, make new topic
			else
				topic = AgendaTopic.getAgendaTopic(agendaId); //get existing topic
			topic.setText(req.getParameter("text"));
			topic.setLongText(req.getParameter("longtext"));
			topic.setTitle(req.getParameter("title"));
			topic.setConventionId(Long.parseLong(req.getParameter("convention")));
			topic.setTimes(req.getParameter("start"), req.getParameter("end"));
			topic.setLocation(req.getParameter("location"));
			topic.setOrder(Integer.parseInt(req.getParameter("order")));
			topic.save();
		}
		if(action.equalsIgnoreCase("delete")){
			int agendaId = Integer.parseInt(req.getParameter("id"));
			AgendaTopic t = AgendaTopic.getAgendaTopic(agendaId); //get existing topic
			t.delete();
		}
		
		String convid = req.getParameter("convention");
		if(convid != null){
			Convention c = Convention.getConvention(Integer.parseInt(convid));
			if(c != null){
				req.setAttribute("agenda", c.getAgenda());
				req.setAttribute("convention", c);
			}
		}
		
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/agenda_admin.jsp");
		rd.forward(req, resp);
	}
}
