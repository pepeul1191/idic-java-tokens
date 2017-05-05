package pe.edu.ulima.utils;

import com.udpwork.ssdb.SSDB;

public class ConnectionDB {
	private SSDB ssdbClient;
    
    public ConnectionDB(){        
        try {
        	this.ssdbClient = new SSDB("127.0.0.1", 8888);
        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
    }

	public SSDB getClient() {
		return ssdbClient;
	}
}
