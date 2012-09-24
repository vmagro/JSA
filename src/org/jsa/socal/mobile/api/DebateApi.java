package org.jsa.socal.mobile.api;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsa.socal.mobile.Debate;
import org.jsa.socal.mobile.Debate.Comment;

import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

@SuppressWarnings("serial")
public class DebateApi extends HttpServlet {
	
	// GET is used to retrieve info about the debate
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		int id = Integer.parseInt(req.getParameter("id"));
		
		JSONArray json = new JSONArray();
		ArrayList<Comment> comments = Debate.getDebate(id).getComments();
		for(Comment c : comments){
			try {
				JSONObject j = new JSONObject();
				j.put("name", c.getAuthor());
				j.put("text", c.getText());
				json.put(j);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		resp.getWriter().println(json);
	}
	
	// POST is used to submit a comment
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		String name = req.getParameter("name");
		String text = req.getParameter("text");
		int id = Integer.parseInt(req.getParameter("id"));
		Debate.getDebate(id).addComment(name, text);
	}

}
