package com.tranred.milpagosapp.service;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Clase que recibe los datos del formulario login
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */
public class LoginUsuarioForm {
   
    @NotNull
    @NotEmpty
    @Size(min = 6, max = 12)
    @Pattern.List({
        @Pattern(regexp = "(?=^[a-zA-Z]).+", message = "El primer caracter debe ser Alfabetico"),        
        @Pattern(regexp = "(?=\\S+$).+", message = "No debe contener espacios en Blanco")
    })
    private String login;
    
    @NotNull
    @NotEmpty
    @Pattern.List({        
        @Pattern(regexp = "(?=\\S+$).+", message = "No debe contener espacios en Blanco")        
    })
    @Size(min = 8, max = 12)
    private String contrasena;
    
    private String messageError;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getMessageError() {
        return messageError;
    }   

    public void setMessageError(String messageError) {
        this.messageError = messageError;
    }
        
}
