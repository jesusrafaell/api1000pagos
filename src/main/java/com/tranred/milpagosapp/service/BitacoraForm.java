package com.tranred.milpagosapp.service;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Clase que recibe los datos del formulario consultaBitacora
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */
public class BitacoraForm {
        
    @NotEmpty
    @DateTimeFormat(style="S-")
    private String fechaDesde;
        
    @NotEmpty
    @DateTimeFormat(style="S-")
    private String fechaHasta;
    
    @NotNull
    @NotEmpty
    private String modulo;
        
    private String opcion;
    
    private String usuario;
    
    private String messageError;

    public String getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(String fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public String getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(String fechaHasta) {
        this.fechaHasta = fechaHasta;
    }        

    public String getOpcion() {
        return opcion;
    }

    public void setOpcion(String opcion) {
        this.opcion = opcion;
    }

    public String getMessageError() {
        return messageError;
    }

    public void setMessageError(String messageError) {
        this.messageError = messageError;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
        
}
