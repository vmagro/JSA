package org.jsa.socal.mobile.api;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsa.socal.mobile.AgendaTopic;
import org.jsa.socal.mobile.Convention;

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
					
					JSONArray agendaArr = new JSONArray();
					ArrayList<AgendaTopic> agenda = c.getAgenda();
					for(AgendaTopic t : agenda){
						JSONObject agendaJson = new JSONObject();
						agendaJson.put("block", t.getBlock());
						agendaJson.put("text", t.getText());
						agendaJson.put("loc", t.getLocation());
						agendaJson.put("time", t.getStartTime()+"-"+t.getEndTime());
						agendaJson.put("id", t.getId());
						agendaArr.put(agendaJson);
					}
					
					json.put("agenda", agendaArr);
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
