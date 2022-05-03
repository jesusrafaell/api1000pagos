package com.tranred.milpagosapp.service;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Clase que recibe los datos del formulario consultaUsuarios
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */
public class ConsultaUsuarioForm {
    
    @NotNull
    @NotEmpty
    private String perfil;                  
    
    private String messageError;

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }
    
    public String getMessageError() {
        return messageError;
    }

    public void setMessageError(String messageError) {
        this.messageError = messageError;
    }
        
}
