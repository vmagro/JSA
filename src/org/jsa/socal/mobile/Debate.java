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

public class Debate {

	public static final String KIND = "Debate";

	public static final String PROP_TITLE = "title";
	public static final String PROP_RESOLUTION = "resolution";
	public static final String PROP_CONVENTION_ID = "convention";

	private Entity data;
	private long id;
	private long conventionId;

	private static DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();

	public Debate(Entity data) {
		this.data = data;
		this.id = data.getKey().getId();
		this.conventionId = (Long) data.getProperty(PROP_CONVENTION_ID);
	}

	public static Debate getDebate(int id) {
		try {
			Entity data = datastore.get(KeyFactory.createKey(KIND, id));
			return new Debate(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Debate(Convention c) {
		this.data = new Entity(KIND);
		this.id = data.getKey().getId();
		this.data.setProperty(PROP_CONVENTION_ID, c.getId());
	}

	public Debate() {
		this.data = new Entity(KIND);
		this.id = data.getKey().getId();
	}

	public static class Comment {

		public static final String KIND = "Comment";

		private static final String PROP_AUTHOR = "a";

		private static final String PROP_DATE = "d";

		private static final String PROP_TEXT = "t";

		public static final String PROP_DEBATE_ID = "i";

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
			return (Integer) data.getProperty(PROP_DEBATE_ID);
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

		public void setDebateId(int value) {
			data.setProperty(PROP_DEBATE_ID, value);
		}

		public void save() {
			DatastoreServiceFactory.getDatastoreService().put(data);
		}
	}

	public ArrayList<Comment> getComments() {
		ArrayList<Comment> comments = new ArrayList<Comment>();
		Query q = new Query(Comment.KIND);
		q.setFilter(new FilterPredicate(Comment.PROP_DEBATE_ID,
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
		c.setDebateId((int) this.getId());
		c.setDate(Calendar.getInstance(TimeZone
				.getTimeZone("America/Los_Angeles")));
		c.save();
	}

	public void removeComment(int id) {
		datastore.delete(KeyFactory.createKey(Comment.KIND, id));
	}

	public String getTitle() {
		return (String) data.getProperty(PROP_TITLE);
	}

	public String getResolution() {
		return (String) data.getProperty(PROP_RESOLUTION);
	}

	public void setTitle(String value) {
		data.setProperty(PROP_TITLE, value);
	}

	public void setResolution(String value) {
		data.setProperty(PROP_RESOLUTION, value);
	}

	public void setConventionId(long value) {
		data.setProperty(PROP_CONVENTION_ID, value);
	}

	public long getId() {
		return id;
	}

	public long getConventionId() {
		return conventionId;
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
