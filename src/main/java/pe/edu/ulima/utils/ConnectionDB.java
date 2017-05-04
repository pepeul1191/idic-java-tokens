package pe.edu.ulima.utils;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class ConnectionDB {
	private MongoDatabase c;
	private MongoClient mongoClient;
    
    public ConnectionDB(){
    	MongoDatabase c;
        
        try {
        	mongoClient = new MongoClient( "localhost" , 27017 );
        	this.c = mongoClient.getDatabase("db_tokens");
        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
    }

    public MongoDatabase getConnection() {
        return (MongoDatabase) c;
    }

	public MongoClient getMongoClient() {
		return mongoClient;
	}
}
