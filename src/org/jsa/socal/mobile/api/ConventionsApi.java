package org.jsa.socal.mobile.api;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsa.socal.mobile.Convention;
import org.jsa.socal.mobile.Debate;

import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

@SuppressWarnings("serial")
public class ConventionsApi extends HttpServlet {
	
	// GET is to retrieve information
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		String idString = req.getParameter("id");
		if(idString == null){ //output all the conventions
			ArrayList<Convention> conventions = Convention.getConventions();
			JSONArray arr = new JSONArray();
			for(Convention c : conventions){
				JSONObject json = new JSONObject();
				try {
					json.put("id", c.getId());
					json.put("title", c.getTitle());
					json.put("loc", c.getLocation());
					json.put("date", c.getDate().getTimeInMillis());
					
					JSONArray debatesArr = new JSONArray();
					ArrayList<Debate> debates = c.getDebates();
					for(Debate d : debates){
						JSONObject debateJson = new JSONObject();
						debateJson.put("block", d.getBlock());
						debateJson.put("res", d.getResolution());
						debatesArr.put(debateJson);
					}
					
					json.put("debates", debatesArr);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				arr.put(json);
			}
			resp.getWriter().println(arr);
		}
		else{
			int id = Integer.parseInt(idString);
			Convention c = Convention.getConvention(id);
			JSONObject json = new JSONObject();
			try {
				json.put("id", c.getId());
				json.put("title", c.getTitle());
				json.put("loc", c.getLocation());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			resp.getWriter().println(json);
		}
	}
	
}
