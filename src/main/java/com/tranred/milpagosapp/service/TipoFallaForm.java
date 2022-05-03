package com.tranred.milpagosapp.service;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Clase que recibe los datos de los formularios Editar y Eliminar TipoFallaForm
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */
public class TipoFallaForm {
    
    private int id;
    
    @NotNull
    @NotEmpty
    @Size(min=1, max=35)
    private String falla;
    
    @NotNull
    @NotEmpty    
    @Size(min=1, max=10)
    private String tipo;
    
    @NotNull    
    @NotEmpty    
    @Size(min=1, max=10)
    private String subTipo;        
    
    private String messageError;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
        
    public String getFalla() {
        return falla;
    }

    public void setFalla(String falla) {
        this.falla = falla;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getSubTipo() {
        return subTipo;
    }

    public void setSubTipo(String subTipo) {
        this.subTipo = subTipo;
    }

    public String getMessageError() {
        return messageError;
    }

    public void setMessageError(String messageError) {
        this.messageError = messageError;
    }
    
}
