package com.tranred.milpagosapp.service;

/**
 * Interface utilizada para el envio de correos electronicos
 * mggy@sistemasemsys.com
 * @version 0.1
 */
import java.io.File;
 
public interface ICorreoService {
 
	public void send(String to, String subject, String text);
	
	public void send(String to, String subject, String text, File... attachments);
 
}
