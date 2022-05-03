package com.tranred.milpagosapp.service;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;


/**
 * Clase que recibe los datos del formulario consultaEstadisticas
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */
public class EstadisticaForm {
    
    @NotNull
    @NotEmpty       
    private String tipoEstadistica;                      

    public String getTipoEstadistica() {
        return tipoEstadistica;
    }

    public void setTipoEstadistica(String tipoEstadistica) {
        this.tipoEstadistica = tipoEstadistica;
    }
        
}
