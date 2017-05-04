package pe.edu.ulima.handlers;

import spark.Request;
import spark.Response;
import spark.Route;
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

import pe.edu.ulima.models.Tokens;

import java.security.SecureRandom;
import java.math.BigInteger;

public class TokenHandler {
	public static Route generar = (Request request, Response response) -> {
		String usuario = request.queryParams("usuario");
		
		if (usuario == null){
			JsonObject rpta = new JsonObject();
        	rpta.addProperty("tipo_mensaje ", "error");
        	rpta.addProperty("mensaje", "El usuario no puede ser nulo" );

        	return rpta.toString();
		}
		
		try{
	        Tokens tokens = new Tokens();
	        List<Document> list = tokens.buscarUsuario(usuario);
	        
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
	        	Tokens tokensInsertar = new Tokens();
	        	tokensInsertar.insertar(doc);
	        	
	        	//{ :tipo_mensaje => 'success', :mensaje => ['token', token] }.to_json
	        	JsonObject rpta = new JsonObject();
	        	rpta.addProperty("tipo_mensaje ", "success");
	        	rpta.addProperty("mensaje ", token);
	        	
	        	return rpta;
	        }
	     }catch(Exception e){
	    	 //{ :tipo_mensaje => 'error', :mensaje => ['Se ha producido un error al generar el token al usuario', e] }.to_json
	        JsonObject rpta = new JsonObject();
        	rpta.addProperty("tipo_mensaje ", "error");
        	rpta.addProperty("mensaje", (String) e.getMessage() );

        	return rpta.toString();
	     }
	 };
	 
	public static Route obtener = (Request request, Response response) -> {
		String usuario = request.queryParams("usuario");
		
		if (usuario == null){
			JsonObject rpta = new JsonObject();
        	rpta.addProperty("tipo_mensaje ", "error");
        	rpta.addProperty("mensaje", "El usuario no puede ser nulo" );

        	return rpta.toString();
		}
		
		try{
			Tokens tokens = new Tokens();
	        List<Document> list = tokens.buscarUsuario(usuario);
	        
	        if (list.size() == 0){
	        	//rpta = { :tipo_mensaje => 'success', :mensaje => [doc['token']] }.to_json
	        	Document doc = list.get(0);
	        	JsonObject rpta = new JsonObject();
	        	rpta.addProperty("tipo_mensaje ", "warning");
	        	rpta.addProperty("mensaje", "Usuario no cuenta con token");
	        	
	        	return rpta.toString();
	        }else{
	        	Document doc = list.get(0);
	        	JsonObject rpta = new JsonObject();
	        	rpta.addProperty("tipo_mensaje ", "success");
	        	rpta.addProperty("mensaje", doc.getString("token"));
	        	
	        	return rpta.toString();
	        }
	     }catch(Exception e){
	    	 //{ :tipo_mensaje => 'error', :mensaje => ['Se ha producido un error al generar el token al usuario', e] }.to_json
	        JsonObject rpta = new JsonObject();
        	rpta.addProperty("tipo_mensaje ", "error");
        	rpta.addProperty("mensaje", (String) e.getMessage() );
        	
        	return rpta.toString();
	     }
	};
		 
    public static Route borrar = (Request request, Response response) -> {
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
	        tokensCollection.deleteOne(bson);
	        JsonObject rpta = new JsonObject();
        	rpta.addProperty("tipo_mensaje ", "success");
        	rpta.addProperty("mensaje", "Token eliminado");
        	mongoClient.close();
        	
        	return rpta.toString();
	     }catch(Exception e){
	    	 //{ :tipo_mensaje => 'error', :mensaje => ['Se ha producido un error al generar el token al usuario', e] }.to_json
	        JsonObject rpta = new JsonObject();
        	rpta.addProperty("tipo_mensaje ", "error");
        	rpta.addProperty("mensaje", (String) e.getMessage() );
        	
        	return rpta.toString();
	     }
	};
}
