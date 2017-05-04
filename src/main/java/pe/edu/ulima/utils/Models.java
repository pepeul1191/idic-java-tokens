package pe.edu.ulima.utils;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class Models {
	protected ConnectionDB c;
	protected MongoDatabase db;
	
	public Models(){
		this.c = new ConnectionDB();
		this.db = c.getConnection();
	}
}
