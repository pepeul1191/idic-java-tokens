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
import pe.edu.ulima.utils.Models;

public class Tokens extends Models{
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
	
	public void eliminar(String usuario){		
		MongoCollection<Document> tokensCollection = this.db.getCollection("tokens");    
		Bson bson = Filters.eq("usuario", usuario);
        tokensCollection.deleteOne(bson);
        
       this.c.getMongoClient().close();
	}
}
