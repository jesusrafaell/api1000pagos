package com.tranred.milpagosapp.service;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Clase que recibe los datos del formulario lotePagos
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */
public class GenerarLoteForm {        
    
    @NotEmpty
    @DateTimeFormat(style="S-")
    private String fechaDesde;
            
    private String fechaHasta;
    
    @NotNull 
    @NotEmpty    
    private String tipoPago;
    
    @NotNull 
    @NotEmpty    
    private String banco;
    
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

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }   
    
}   
            

