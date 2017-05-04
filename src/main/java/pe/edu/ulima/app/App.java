package pe.edu.ulima.app;
import static spark.Spark.*;
import pe.edu.ulima.handlers.TokenHandler;

public class App 
{
    public static void main( String[] args )
    {
    	port(5002);
    
    	options("/*", (request, response) -> {

            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });
    	
    	before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Request-Method",  "*");
            response.header("Access-Control-Allow-Headers",  "*");
            // Note: this may or may not be necessary in your particular application
            response.type("application/json");
        });
    	
    	get("/generar", TokenHandler.generar);
    	get("/get_token", TokenHandler.obtener);
    	get("/borrar", TokenHandler.borrar);
    }
}
