package pe.edu.ulima.models;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import pe.edu.ulima.utils.ConnectionDB;

public class Tokens {
	private ConnectionDB c;
	private MongoDatabase db;
	
	public Tokens(){
		this.c = new ConnectionDB();
		this.db = c.getConnection();
	}
	
	public List<Document> buscarUsuario(String usuario){		
		MongoCollection<Document> tokensCollection = this.db.getCollection("tokens");    
        Bson bson = Filters.eq("usuario", usuario);
        List<Document> rpta =  tokensCollection.find(bson).into(new ArrayList<>());
        
       this.c.getMongoClient().close();
        
        return rpta;
	}
	
	public void insertar(Document doc){
		MongoCollection<Document> tokensCollection = this.db.getCollection("tokens");
		tokensCollection.insertOne(doc);
		this.c.getMongoClient().close();
	}
}
