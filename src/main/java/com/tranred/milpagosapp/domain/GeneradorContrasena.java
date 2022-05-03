package com.tranred.milpagosapp.domain;

/**
 * POJO para generar un password aleatorio
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */
public class GeneradorContrasena {
    
        public static String NUMEROS = "123456789";
 
	public static String MAYUSCULAS = "ABDFGHJKLMPRSTXZ";
 
	public static String MINUSCULAS = "abdfghjklmprstxz";
 
	public static String ESPECIALES = "!#$&()?¡";
 
	//
	public static String getPinNumber() {
		return getPassword(NUMEROS, 4);
	}
 
	public static String getPassword() {
		return getPassword(8);
	}
 
	public static String getPassword(int length) {
		return getPassword(NUMEROS + MAYUSCULAS + MINUSCULAS, length);
	}
 
	public static String getPassword(String key, int length) {
		String pswd = "";
 
		for (int i = 0; i < length; i++) {
			pswd+=(key.charAt((int)(Math.random() * key.length())));
		}
 
		return pswd;
	}
}
