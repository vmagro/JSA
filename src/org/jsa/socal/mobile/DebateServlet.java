package org.jsa.socal.mobile;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
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
		System.out.println(req.getParameter("id"));
		int debateId = Integer.parseInt(req.getParameter("id"));
		Debate debate = Debate.getDebate(debateId);
		if (debate != null) {
			req.setAttribute("debate", debate);
			req.setAttribute("title", debate.getTitle());
		}

		UserService users = UserServiceFactory.getUserService();

		String jsp = "";

		String action = req.getParameter("action");
		if (action == null)
			action = "";
		if (action.equalsIgnoreCase("new-comment")) {
			User u = users.getCurrentUser();
			jsp = "addcomment";
			req.setAttribute("user", u);
		} else if (action.equalsIgnoreCase("add-comment")) {
			debate.addComment(users.getCurrentUser().getNickname(), req.getParameter("text"));
			jsp = "debate";
			req.removeAttribute("debate");
			req.setAttribute("debate", debate);
		} else if (action.equalsIgnoreCase("delete-comment")) {
			if (users.isUserAdmin()) {
				int commentId = Integer.parseInt(req.getParameter("comment"));
				debate.removeComment(commentId);
			}
			jsp = "debate";
		} else {
			req.setAttribute("admin", users.isUserAdmin());
			jsp = "debate";
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/" + jsp + ".jsp");
		rd.forward(req, resp);
	}
}