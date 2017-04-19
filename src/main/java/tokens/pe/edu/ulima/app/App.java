package tokens.pe.edu.ulima.app;
import static spark.Spark.*;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import java.security.SecureRandom;
import java.math.BigInteger;

public class App 
{
    public static void main( String[] args )
    {
    	get("/generar", (request, response) -> {
    		String usuario = request.queryParams("usuario");
    		
    		if (usuario == null){
    			JsonObject rpta = new JsonObject();
	        	rpta.addProperty("tipo_mensaje ", "error");
	        	rpta.addProperty("mensaje", "El usuario no puede ser nulo" );

	        	return rpta.toString();
    		}
    		
    		try{
    	        MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
    	        MongoDatabase db_tokens = mongoClient.getDatabase("db_tokens");
    	        MongoCollection<Document> tokensCollection = db_tokens.getCollection("tokens");    
    	        Bson bson = Filters.eq("usuario", usuario);
    	        List<Document> list = tokensCollection.find(bson).into(new ArrayList<>());
    	        
    	        if (list.size() == 1){
    	        	//rpta = { :tipo_mensaje => 'success', :mensaje => [doc['token']] }.to_json
    	        	Document doc = list.get(0);
    	        	JsonObject rpta = new JsonObject();
    	        	rpta.addProperty("tipo_mensaje ", "success");
    	        	rpta.addProperty("mensaje", (String) doc.get("token"));
    	        	
    	        	return rpta.toString();
    	        }else{
    	        	SecureRandom random = new SecureRandom();
    	        	String token = new BigInteger(130, random).toString(32);
    	        	Document doc = new Document("usuario", usuario).append("token", token);
    	        	tokensCollection.insertOne(doc);
    	        	//{ :tipo_mensaje => 'success', :mensaje => ['token', token] }.to_json
    	        	JsonObject rpta = new JsonObject();
    	        	rpta.addProperty("tipo_mensaje ", "success");
    	        	JsonArray mensaje = new JsonArray();
    	        	mensaje.add("token");
    	        	mensaje.add(token);
    	        	rpta.addProperty("mensaje", mensaje.toString());
    	        	
    	        	return rpta;
    	        }
    	     }catch(Exception e){
    	    	 //{ :tipo_mensaje => 'error', :mensaje => ['Se ha producido un error al generar el token al usuario', e] }.to_json
    	        JsonObject rpta = new JsonObject();
	        	rpta.addProperty("tipo_mensaje ", "error");
	        	rpta.addProperty("mensaje", (String) e.getMessage() );

	        	return rpta.toString();
    	     }
    	});
    }
}
