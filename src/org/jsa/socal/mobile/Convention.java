package org.jsa.socal.mobile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;

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
		Query q = new Query(KIND);
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		today.set(Calendar.MILLISECOND, 0);
		q.addSort(PROP_DATE, SortDirection.ASCENDING);
		Iterable<Entity> entities = datastore.prepare(q).asIterable();
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

	public ArrayList<AgendaTopic> getAgenda() {
		ArrayList<AgendaTopic> agenda = new ArrayList<AgendaTopic>();
		Query q = new Query(AgendaTopic.KIND);
		q.setFilter(new FilterPredicate(AgendaTopic.PROP_CONVENTION_ID, FilterOperator.EQUAL, getId()));
		q.addSort(AgendaTopic.PROP_ORDER, SortDirection.ASCENDING);
		Iterable<Entity> entities = datastore.prepare(q).asIterable();
		for (Entity e : entities) {
			agenda.add(new AgendaTopic(e));
		}
		return agenda;
	}
	
	public ArrayList<AgendaTopic> getTopicsByBlock(String block){
		ArrayList<AgendaTopic> agenda = new ArrayList<AgendaTopic>();
		Query q = new Query(AgendaTopic.KIND);
		q.setFilter(CompositeFilterOperator.and(new FilterPredicate(AgendaTopic.PROP_CONVENTION_ID, FilterOperator.EQUAL, getId()),
				new FilterPredicate(AgendaTopic.PROP_TITLE, FilterOperator.EQUAL, block)));
		
		q.addSort(AgendaTopic.PROP_ORDER, SortDirection.ASCENDING);
		Iterable<Entity> entities = datastore.prepare(q).asIterable();
		for (Entity e : entities) {
			agenda.add(new AgendaTopic(e));
		}
		return agenda;
	}

	public void saveAsync() {
		DatastoreServiceFactory.getAsyncDatastoreService().put(data);
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
