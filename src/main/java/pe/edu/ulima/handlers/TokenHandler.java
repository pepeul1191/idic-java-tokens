package pe.edu.ulima.handlers;

import spark.Request;
import spark.Response;
import spark.Route;
import java.util.List;
import org.bson.Document;
import com.google.gson.JsonObject;

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
	        String list = tokens.buscarUsuario(usuario);
	        
	        if (!list.equalsIgnoreCase("nulo")){
	        	//rpta = { :tipo_mensaje => 'success', :mensaje => [doc['token']] }.to_json
	        	JsonObject rpta = new JsonObject();
	        	rpta.addProperty("tipo_mensaje ", "success");
	        	rpta.addProperty("mensaje", list);
	        	
	        	return rpta.toString();
	        }else{
	        	SecureRandom random = new SecureRandom();
	        	String token = new BigInteger(130, random).toString(32);
	        	Tokens tokensInsertar = new Tokens();
	        	tokensInsertar.insertar(usuario, token);

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
	        String list = tokens.buscarUsuario(usuario);
	        
	        if (!list.equalsIgnoreCase("nulo")){
	        	//rpta = { :tipo_mensaje => 'success', :mensaje => [doc['token']] }.to_json
	        	JsonObject rpta = new JsonObject();
	        	rpta.addProperty("tipo_mensaje ", "warning");
	        	rpta.addProperty("mensaje", "Usuario no cuenta con token");
	        	
	        	return rpta.toString();
	        }else{
	        	JsonObject rpta = new JsonObject();
	        	rpta.addProperty("tipo_mensaje ", "success");
	        	rpta.addProperty("mensaje", list);

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
			Tokens tokens = new Tokens();
	        tokens.eliminar(usuario);
	        
	        JsonObject rpta = new JsonObject();
        	rpta.addProperty("tipo_mensaje ", "success");
        	rpta.addProperty("mensaje", "Token eliminado");
        	
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
