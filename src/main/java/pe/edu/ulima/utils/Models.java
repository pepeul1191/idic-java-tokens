package pe.edu.ulima.utils;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import com.udpwork.ssdb.SSDB;

public class Models {
	protected ConnectionDB c;
	protected SSDB db;
	
	public Models(){
		this.c = new ConnectionDB();
		this.db = c.getClient();
	}
}
