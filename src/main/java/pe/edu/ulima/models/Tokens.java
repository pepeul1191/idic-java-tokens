package pe.edu.ulima.models;

import pe.edu.ulima.utils.Models;

public class Tokens extends Models{
	public String buscarUsuario(String usuario){		 
        String rpta = null;
		try {
			byte[] temp = this.db.get(usuario);
			if(temp != null){
	             rpta = new String(temp);
	         }else{
	             rpta = "nulo";
	         }
		} catch (Exception e) {
			e.printStackTrace();
			rpta = (String) e.getMessage();
		}
		
		return rpta;
	}
	
	public void insertar(String usuario, String token){
		try {
			this.db.set(usuario, token);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void eliminar(String usuario){		
		try {
			this.db.del(usuario);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
