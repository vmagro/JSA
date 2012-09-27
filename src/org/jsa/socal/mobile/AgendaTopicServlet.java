package org.jsa.socal.mobile;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class AgendaTopicServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		doPost(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		AgendaTopic topic = null;
		int topicId = 0;
		if (req.getParameter("id") == null) {
			topicId = (Integer) req.getSession().getAttribute("topicId");
		}
		else
			topicId = Integer.parseInt(req.getParameter("id"));
		req.getSession().setAttribute("topicId", topicId);
		topic = AgendaTopic.getAgendaTopic(topicId);
		if (topic != null) {
			req.setAttribute("topic", topic);
			req.setAttribute("title", topic.getBlock());
		}

		UserService users = UserServiceFactory.getUserService();

		String jsp = "";

		req.setAttribute("loginUrl", users.createLoginURL(req.getRequestURI()));
		req.setAttribute("logoutUrl",
				users.createLogoutURL(req.getRequestURI()));
		req.setAttribute("reqUri", req.getRequestURI());

		String action = req.getParameter("action");
		if (action == null)
			action = "";
		if (action.equalsIgnoreCase("add-comment")) {
			topic.addComment(req.getParameter("author"),
					req.getParameter("text"));
			resp.setStatus(200);
			return;
		} else if (action.equalsIgnoreCase("delete-comment")) {
			if (users.isUserAdmin()) {
				int commentId = Integer.parseInt(req.getParameter("comment"));
				topic.removeComment(commentId);
			}
			return;
		} else {
			if (users.isUserLoggedIn()) {
				req.setAttribute("admin", users.isUserAdmin());
			}
			jsp = "agenda_topic";
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/" + jsp + ".jsp");
		rd.forward(req, resp);
	}
}