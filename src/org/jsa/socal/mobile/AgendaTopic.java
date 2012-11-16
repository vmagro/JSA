package org.jsa.socal.mobile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;

public class AgendaTopic {

	public static final String KIND = "Agenda";

	public static final String PROP_TITLE 			= "title";
	public static final String PROP_TEXT 			= "text";
	public static final String PROP_CONVENTION_ID 	= "convention";
	public static final String PROP_START_TIME		= "start";
	public static final String PROP_END_TIME		= "end";
	public static final String PROP_ORDER 			= "order";
	public static final String PROP_LOCATION		= "loc";
	public static final String PROP_NO_BEST_SPEAKER = "nobstspkr";

	private Entity data;
	private long id;
	private long conventionId;

	private static DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();

	public AgendaTopic(Entity data) {
		this.data = data;
		this.id = data.getKey().getId();
		this.conventionId = (Long) data.getProperty(PROP_CONVENTION_ID);
	}

	public static AgendaTopic getAgendaTopic(int id) {
		try {
			Entity data = datastore.get(KeyFactory.createKey(KIND, id));
			return new AgendaTopic(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public AgendaTopic(Convention c) {
		this.data = new Entity(KIND);
		this.id = data.getKey().getId();
		this.data.setProperty(PROP_CONVENTION_ID, c.getId());
	}

	public AgendaTopic() {
		this.data = new Entity(KIND);
		this.id = data.getKey().getId();
	}

	public static class Comment {

		public static final String KIND = "Comment";

		private static final String PROP_AUTHOR = "a";

		private static final String PROP_DATE = "d";

		private static final String PROP_TEXT = "t";

		public static final String PROP_AGENDA_ID = "i";

		private Entity data = null;

		private Comment(Entity entity) {
			this.data = entity;
		}

		public Comment() {
			this.data = new Entity(KIND);
		}

		public String getAuthor() {
			return data.getProperty(PROP_AUTHOR).toString();
		}

		public Calendar getDate() {
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis((Long) data.getProperty(PROP_DATE));
			return cal;
		}

		public String getDateString() {
			return new SimpleDateFormat("MM/dd/yyyy hh:mm a").format(getDate()
					.getTime());
		}

		public String getText() {
			return data.getProperty(PROP_TEXT).toString();
		}

		public int getDebateId() {
			return (Integer) data.getProperty(PROP_AGENDA_ID);
		}

		public int getId() {
			return (int) data.getKey().getId();
		}

		public void setText(String value) {
			data.setProperty(PROP_TEXT, value);
		}

		public void setDate(Calendar value) {
			data.setProperty(PROP_DATE, value.getTimeInMillis());
		}

		public void setAuthor(String value) {
			data.setProperty(PROP_AUTHOR, value);
		}

		public void setTopicId(int value) {
			data.setProperty(PROP_AGENDA_ID, value);
		}

		public void save() {
			DatastoreServiceFactory.getDatastoreService().put(data);
		}
	}

	public ArrayList<Comment> getComments() {
		ArrayList<Comment> comments = new ArrayList<Comment>();
		Query q = new Query(Comment.KIND);
		q.setFilter(new FilterPredicate(Comment.PROP_AGENDA_ID,
				FilterOperator.EQUAL, this.getId()));
		q.addSort(Comment.PROP_DATE, SortDirection.DESCENDING);
		Iterable<Entity> entities = datastore.prepare(q).asIterable();
		for (Entity e : entities) {
			comments.add(new Comment(e));
		}
		return comments;
	}

	public void addComment(String author, String text) {
		Comment c = new Comment();
		c.setAuthor(author);
		c.setText(text);
		c.setTopicId((int) this.getId());
		c.setDate(Calendar.getInstance(TimeZone
				.getTimeZone("America/Los_Angeles")));
		c.save();
	}

	public void removeComment(int id) {
		datastore.delete(KeyFactory.createKey(Comment.KIND, id));
	}

	public String getBlock() {
		return (String) data.getProperty(PROP_TITLE);
	}

	public String getText() {
		return (String) data.getProperty(PROP_TEXT);
	}

	public void setTitle(String value) {
		data.setProperty(PROP_TITLE, value);
	}

	public void setText(String value) {
		data.setProperty(PROP_TEXT, value);
	}

	public void setConventionId(long value) {
		data.setProperty(PROP_CONVENTION_ID, value);
	}
	
	public void setTimes(String start, String end){
		data.setProperty(PROP_START_TIME, start);
		data.setProperty(PROP_END_TIME, end);
	}
	
	public void setOrder(int order){
		data.setProperty(PROP_ORDER, order);
	}
	
	public void setLocation(String l){
		data.setProperty(PROP_LOCATION, l);
	}
	
	public void setNoBestSpeaker(){
		data.setProperty(PROP_NO_BEST_SPEAKER, true);
	}

	public long getId() {
		return id;
	}

	public long getConventionId() {
		return conventionId;
	}
	
	public String getStartTime(){
		return (String) data.getProperty(PROP_START_TIME);
	}
	
	public String getEndTime(){
		return (String) data.getProperty(PROP_END_TIME);
	}
	
	public long getOrder(){
		return (Long) data.getProperty(PROP_ORDER);
	}
	
	public String getLocation(){
		return (String) data.getProperty(PROP_LOCATION);
	}
	
	public boolean hasBestSpeakerAward() {
		if((Boolean) data.getProperty(PROP_NO_BEST_SPEAKER))
			return false;
		return true;
	}
	
	public void save() {
		datastore.put(data);
	}

	public void delete() {
		datastore.delete(data.getKey());
	}

	@Override
	public String toString() {
		return data.toString();
	}

}
