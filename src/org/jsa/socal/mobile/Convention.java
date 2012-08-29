package org.jsa.socal.mobile;

import java.util.ArrayList;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;

public class Convention {

	public static final String KIND = "Convention";

	private static DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();
	private Entity data;

	public Convention(int id) {
		try {
			data = datastore.get(KeyFactory.createKey(KIND, id));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public Convention(Entity entity) {
		this.data = entity;
	}

	public Convention() {
		data = new Entity(KIND);
	}

	public static ArrayList<Convention> getConventions() {
		ArrayList<Convention> conventions = new ArrayList<Convention>();
		Iterable<Entity> entities = datastore.prepare(new Query(KIND))
				.asIterable();
		return conventions;
	}

}
