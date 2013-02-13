package org.jsa.socal.mobile;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class VoteServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		out.println("<html>");

		int cid = Integer.parseInt(req.getParameter("cid"));
		ArrayList<AgendaTopic> topics = Convention.getConvention(cid)
				.getAgenda();

		out.println("<h3>Intrepreted Data</h3>");
		out.println("<p>Spelling may be incorrect!");
		for (AgendaTopic t : topics) {
			out.println("<li>" + t.getBlock() + ": " + t.getText());
			ArrayList<Vote> votes = Vote.getVotes((int) t.getId());
			HashMap<String, String> speakerMap = new HashMap<String, String>();
			HashMap<String, Integer> guessed = new HashMap<String, Integer>();
			out.println("<ul>");
			for (Vote v : votes) {
				String speaker = v.getSpeaker();
				// get the first 2 letters of the speakers first name and of the
				// speaker's last name to try to account for spelling errors
				String speakerFailsafe = speaker.substring(0, 2);
				if (speaker.contains(" "))// take into account that user might
											// only enter a first name and
											// having no spaces will crash the
											// code if we don't take that into
											// consideration
					speakerFailsafe += " "
							+ speaker.substring(speaker.indexOf(" "),
									speaker.indexOf(" ") + 2);
				
				if (guessed.containsKey(speakerFailsafe))
					guessed.put(speakerFailsafe,
							guessed.get(speakerFailsafe) + 1); // increment
																// count by 1 if
																// we already
																// have a vote
																// for this
																// person
				else{
					guessed.put(speakerFailsafe, 1);
					
					//store the speaker's real name with the failsafe as a key so we can display it nicer to the user tallying the votes
					speakerMap.put(speakerFailsafe, speaker);
				}
			}
			for (String speaker : guessed.keySet())
				out.println("<li>" + speakerMap.get(speaker) + " : " + guessed.get(speaker)
						+ "</li>");
			out.println("</ul>");
			out.println("</li>");
		}

		out.println("<h3>Raw Data</h3>");
		out.println("<ul>");
		for (AgendaTopic t : topics) {
			out.println("<li>" + t.getBlock() + ": " + t.getText());
			out.println("<ol>");
			for (Vote v : Vote.getVotes((int) t.getId())) {
				out.println("<li>" + v.getSpeaker() + " - " + v.getUser()
						+ "</li>");
			}
			out.println("</ol>");
			out.println("</li>");
		}
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) {
		String user = req.getParameter("user");
		String speaker = req.getParameter("speaker");
		int topicId = Integer.parseInt(req.getParameter("id"));
		String block = AgendaTopic.getAgendaTopic(topicId).getBlock();

		Vote v = new Vote();
		v.setUser(user);
		v.setSpeaker(speaker);
		v.setBlock(block);
		v.setTopicId(topicId);

		if (!Vote.doesVoteExist(user, block)) {
			v.saveAsync();
		}
	}

}
