package com.tranred.milpagosapp.service;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Clase que recibe los datos del formulario consultaUsuarioWeb
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */
public class ConsultaUsuarioWebForm {
    
    @NotNull
    @NotEmpty     
    private String tipoIdentificacion;
    
    @NotNull
    @NotEmpty     
    private String identificacion;                 
    
    private String messageError;

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }    
    
    public String getMessageError() {
        return messageError;
    }

    public void setMessageError(String messageError) {
        this.messageError = messageError;
    }
        
}
