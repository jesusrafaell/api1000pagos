package com.tranred.milpagosapp.service;

/**
 * Clase que recibe los datos del formulario consultaVisitasAliado
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */
public class ConsultaVisitasForm {
          
    private String aliadoComercial;
        
    private String codZonaAtencion;
    
    private String messageError;

    public String getAliadoComercial() {
        return aliadoComercial;
    }

    public void setAliadoComercial(String aliadoComercial) {
        this.aliadoComercial = aliadoComercial;
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
