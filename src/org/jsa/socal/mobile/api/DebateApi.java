package org.jsa.socal.mobile.api;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsa.socal.mobile.Debate;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

@SuppressWarnings("serial")
public class DebateApi extends HttpServlet {
	
	// GET is used to retrieve info about the debate
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		JSONObject json = new JSONObject();
		try{
			int id = Integer.parseInt(req.getParameter("id"));
			json.put("id", id);
			Debate d = Debate.getDebate(id);
			json.put("block", d.getTitle());
			json.put("res", d.getResolution());
			json.put("cid", d.getConventionId());
		}catch(JSONException ex){
			ex.printStackTrace();
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
