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
public class DebateServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		doPost(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		Debate debate = null;
		int debateId = 0;
		if (req.getParameter("id") == null) {
			debateId = (Integer) req.getSession().getAttribute("debateId");
		}
		else
			debateId = Integer.parseInt(req.getParameter("id"));
		req.getSession().setAttribute("debateId", debateId);
		debate = Debate.getDebate(debateId);
		if (debate != null) {
			req.setAttribute("debate", debate);
			req.setAttribute("title", debate.getTitle());
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
			debate.addComment(req.getParameter("author"),
					req.getParameter("text"));
			System.out.println("add comment");
			resp.setStatus(200);
			return;
		} else if (action.equalsIgnoreCase("delete-comment")) {
			if (users.isUserAdmin()) {
				int commentId = Integer.parseInt(req.getParameter("comment"));
				debate.removeComment(commentId);
				System.out.println("deleting: " + commentId);
			}
			return;
		} else {
			if (users.isUserLoggedIn()) {
				req.setAttribute("admin", users.isUserAdmin());
			}
			jsp = "debate";
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/" + jsp + ".jsp");
		rd.forward(req, resp);
	}
}