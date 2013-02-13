package org.jsa.socal.mobile;

import java.util.ArrayList;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;

public class Vote {

	public static final String KIND = "Vote";

	private static final String PROP_USER = "user";
	private static final String PROP_SPEAKER = "speaker";
	private static final String PROP_BLOCK = "block";
	private static final String PROP_TOPIC_ID = "topicId";

	private static DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();

	private Entity data;
	private long id;

	public Vote(Entity entity) {
		this.data = entity;
		this.id = data.getKey().getId();
	}

	public Vote() {
		this.data = new Entity(KIND);
		this.id = data.getKey().getId();
	}
	
	public static boolean doesVoteExist(String user, String block){
		Query q = new Query(KIND);
		q.setFilter(CompositeFilterOperator.and(new FilterPredicate(PROP_BLOCK, FilterOperator.EQUAL, block), new FilterPredicate(PROP_USER, FilterOperator.EQUAL, user)));
		return datastore.prepare(q).asList(FetchOptions.Builder.withDefaults()).size() != 0;
	}
	
	public static void deleteVote(String user, String block){
		Query q = new Query(KIND);
		q.setFilter(CompositeFilterOperator.and(new FilterPredicate(PROP_BLOCK, FilterOperator.EQUAL, block), new FilterPredicate(PROP_USER, FilterOperator.EQUAL, user)));
		datastore.delete(datastore.prepare(q).asSingleEntity().getKey());
	}

	public static ArrayList<Vote> getVotes(String block) {
		ArrayList<Vote> votes = new ArrayList<Vote>();
		Query q = new Query(KIND);
		q.setFilter(new FilterPredicate(PROP_BLOCK, FilterOperator.EQUAL, block));
		Iterable<Entity> entities = datastore.prepare(q).asIterable();
		for (Entity e : entities)
			votes.add(new Vote(e));
		return votes;
	}
	
	public static ArrayList<Vote> getVotes(int topicId) {
		ArrayList<Vote> votes = new ArrayList<Vote>();
		Query q = new Query(KIND);
		q.setFilter(new FilterPredicate(PROP_TOPIC_ID, FilterOperator.EQUAL, topicId));
		q.addSort(PROP_SPEAKER, SortDirection.DESCENDING);
		Iterable<Entity> entities = datastore.prepare(q).asIterable();
		for (Entity e : entities)
			votes.add(new Vote(e));
		return votes;
	}

	public String getUser() {
		return (String) data.getProperty(PROP_USER);
	}
	
	public String getBlock() {
		return (String) data.getProperty(PROP_BLOCK);
	}
	
	public String getSpeaker() {
		return (String) data.getProperty(PROP_SPEAKER);
	}

	public int getTopicId() {
		return (Integer) data.getProperty(PROP_TOPIC_ID);
	}

	public void setUser(String value) {
		data.setProperty(PROP_USER, value);
	}
	
	public void setBlock(String value) {
		data.setProperty(PROP_BLOCK, value);
	}
	
	public void setSpeaker(String value) {
		data.setProperty(PROP_SPEAKER, value);
	}

	public void setTopicId(int value) {
		data.setProperty(PROP_TOPIC_ID, value);
	}

	public long getId() {
		return id;
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
