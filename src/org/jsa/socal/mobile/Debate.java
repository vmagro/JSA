package org.jsa.socal.mobile;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;

public class Debate {
	
	public static final String KIND = "Debate";
	
	public static final String PROP_TITLE = "title";
	public static final String PROP_RESOLUTION = "resolution";
	public static final String PROP_CONVENTION_ID = "convention";
	
	private Entity data;
	private long id;
	private long conventionId;
	
	public Debate(Entity data){
		this.data = data;
		this.id = data.getKey().getId();
		this.conventionId = (Long) data.getProperty(PROP_CONVENTION_ID);
	}
	
	public Debate(int id){
		try {
			this.data = DatastoreServiceFactory.getDatastoreService().get(KeyFactory.createKey(KIND, id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.id = id;
		this.conventionId = (Long) data.getProperty(PROP_CONVENTION_ID);
	}
	
	public Debate(Convention c){
		this.data = new Entity(KIND);
		this.id = data.getKey().getId();
		this.data.setProperty(PROP_CONVENTION_ID, c.getId());
	}
	
	public Debate(){
		this.data = new Entity(KIND);
		this.id = data.getKey().getId();
	}
	
	public static class Comment{
		
	}
	
	public String getTitle(){
		return (String) data.getProperty(PROP_TITLE);
	}
	
	public String getResolution(){
		return (String) data.getProperty(PROP_RESOLUTION);
	}
	
	public void setTitle(String value){
		data.setProperty(PROP_TITLE, value);
	}
	
	public void setResolution(String value){
		data.setProperty(PROP_RESOLUTION, value);
	}
	
	public void setConventionId(long value){
		data.setProperty(PROP_CONVENTION_ID, value);
	}
	
	public long getId(){
		return id;
	}
	
	public long getConventionId(){
		return conventionId;
	}
	
	public void save(){
		DatastoreServiceFactory.getDatastoreService().put(data);
	}
	
	@Override
	public String toString(){
		return data.toString();
	}

}
