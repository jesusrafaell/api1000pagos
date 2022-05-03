package com.tranred.milpagosapp.service;

/**
 * Clase que recibe los datos del formulario consultaAliado
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */
public class ConsultaAliadoForm {
          
    private String tipoIdentificacion;
               
    private String identificacion;
        
    private String codZonaAtencion;
    
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

    public String getCodZonaAtencion() {
        return codZonaAtencion;
    }

    public void setCodZonaAtencion(String codZonaAtencion) {
        this.codZonaAtencion = codZonaAtencion;
    }    

    public String getMessageError() {
        return messageError;
    }

    public void setMessageError(String messageError) {
        this.messageError = messageError;
    }
        
}
