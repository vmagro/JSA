package org.jsa.socal.mobile;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;

public class Debate {
	
	public static final String KIND = "Debate";
	
	private static final String PROP_TITLE = "title";
	private static final String PROP_RESOLUTION = "resolution";
	
	private Entity data;
	
	public Debate(Entity data){
		this.data = data;
	}
	
	public Debate(int id){
		Query q = new Query(KIND);
		data = DatastoreServiceFactory.getDatastoreService().prepare(q).asSingleEntity();
	}
	
	public Debate(){
		data = new Entity(KIND);
	}
	
	public static class Comment{
	}

}
