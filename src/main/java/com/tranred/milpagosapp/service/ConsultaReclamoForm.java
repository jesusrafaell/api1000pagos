package com.tranred.milpagosapp.service;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Clase que recibe los datos del formulario consultaReclamos
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */
public class ConsultaReclamoForm {
        
    @NotEmpty
    @DateTimeFormat(style="S-")
    private String fechaDesde;
        
    @NotEmpty
    @DateTimeFormat(style="S-")
    private String fechaHasta;
        
    private String tipoReclamo;
            
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

    public String getTipoReclamo() {
        return tipoReclamo;
    }

    public void setTipoReclamo(String tipoReclamo) {
        this.tipoReclamo = tipoReclamo;
    }
    
    public String getMessageError() {
        return messageError;
    }

    public void setMessageError(String messageError) {
        this.messageError = messageError;
    }   
        
}
