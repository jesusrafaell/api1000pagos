package com.tranred.milpagosapp.service;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Clase que recibe los datos del formulario generarFacturacionComercios
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */
public class GenerarFacturacionForm {
          
    private String tipoIdentificacion;
               
    private String identificacion;            
    
    @NotNull
    @NotEmpty
    private String tipoConsulta;
    
    @NotNull    
    private int tipoContrato;
    
    @NotNull
    @NotEmpty    
    private String mes;
    
    @NotNull
    @NotEmpty    
    private String ano;
    
    @NotNull
    @NotEmpty    
    private String numeroControl;
    
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
    
    public String getTipoConsulta() {
        return tipoConsulta;
    }

    public void setTipoConsulta(String tipoConsulta) {
        this.tipoConsulta = tipoConsulta;
    }

    public int getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(int tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }   

    public String getNumeroControl() {
        return numeroControl;
    }

    public void setNumeroControl(String numeroControl) {
        this.numeroControl = numeroControl;
    }
    
    public String getMessageError() {
        return messageError;
    }

    public void setMessageError(String messageError) {
        this.messageError = messageError;
    }
        
}
