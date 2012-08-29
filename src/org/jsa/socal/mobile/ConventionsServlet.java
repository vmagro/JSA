package org.jsa.socal.mobile;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.googleapis.services.GoogleKeyInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.json.JsonHttpRequestInitializer;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.calendar.Calendar;

@SuppressWarnings("serial")
public class ConventionsServlet extends HttpServlet {
	
	private static final JsonFactory JSON_FACTORY = new JacksonFactory();
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		doPost(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		JsonHttpRequestInitializer initializer = new GoogleKeyInitializer(ClientCredentials.KEY);
		Calendar cal = Calendar.builder(HTTP_TRANSPORT, JSON_FACTORY)
			.setApplicationName("JSA")
			.setJsonHttpRequestInitializer(initializer)
			.build();
		
		
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/conventions.jsp");
		rd.forward(req, resp);
	}
}
