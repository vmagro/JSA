package org.jsa.socal.mobile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

public class Convention {

	public static final String KIND = "Convention";

	private static final String PROP_TITLE = "title";
	private static final String PROP_DATE = "date";
	private static final String PROP_LOC = "loc";

	private static DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();

	private Entity data;
	private long id;

	public static Convention getConvention(int id) {
		try {
			Entity data = datastore.get(KeyFactory.createKey(KIND, id));
			return new Convention(data);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

	}

	public Convention(Entity entity) {
		this.data = entity;
		this.id = data.getKey().getId();
	}

	public Convention() {
		this.data = new Entity(KIND);
		this.id = data.getKey().getId();
	}

	public static ArrayList<Convention> getConventions() {
		ArrayList<Convention> conventions = new ArrayList<Convention>();
		Iterable<Entity> entities = datastore.prepare(new Query(KIND))
				.asIterable();
		for (Entity e : entities)
			conventions.add(new Convention(e));
		return conventions;
	}

	public String getTitle() {
		return (String) data.getProperty(PROP_TITLE);
	}

	public Calendar getDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis((Long) data.getProperty(PROP_DATE));
		return cal;
	}

	public String getDateString() {
		return new SimpleDateFormat("MM/dd/yyyy").format(getDate().getTime());
	}

	public String getLocation() {
		return (String) data.getProperty(PROP_LOC);
	}

	public void setTitle(String value) {
		data.setProperty(PROP_TITLE, value);
	}

	public void setDate(Calendar value) {
		data.setProperty(PROP_DATE, value.getTimeInMillis());
	}

	public void setLocation(String value) {
		data.setProperty(PROP_LOC, value);
	}

	public long getId() {
		return id;
	}

	public ArrayList<Debate> getDebates() {
		ArrayList<Debate> debates = new ArrayList<Debate>();
		Query q = new Query(Debate.KIND);
		q.setFilter(new FilterPredicate(Debate.PROP_CONVENTION_ID,
				FilterOperator.EQUAL, getId()));
		Iterable<Entity> entities = datastore.prepare(q).asIterable();
		for (Entity e : entities) {
			debates.add(new Debate(e));
		}
		return debates;
	}

	public void saveAsync() {
		DatastoreServiceFactory.getAsyncDatastoreService().put(data);
	}

	public void save() {
		datastore.put(data);
	}

	@Override
	public String toString() {
		return data.toString();
	}

}
