package com.tranred.milpagosapp.service;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Clase que recibe los datos del formulario generarConsultaPlanes
 * @author mcaraballo@emsys-solutions.com
 * @version 0.1
 */
public class GenerarConsultaPlanesForm {
          
    private String tipoIdentificacion;
               
    private String identificacion;
    
    private String terminal;
    
    private String tipoIdentificacionComercio;
    
    private String identificacionComercio;
    
    @NotNull
    @NotEmpty
    private String tipoConsulta;
    
    private String tipoConsultaIndividual;
    
    @NotNull(message = "")
    @NotEmpty(message = "Debe Seleccionar al menos 1 Tipo de Plan.")
    private String tipoPlan;
    
    //@NotNull(message = "")
    //@NotEmpty(message = "Debe Seleccionar al menos 1 estatus.")
    private String estatus;
   
    private String tipoReporte;
    
    private String messageError;

    public GenerarConsultaPlanesForm() {
        this.tipoIdentificacion = new String();
        this.identificacion = new String();
        this.terminal = new String();
        this.identificacionComercio = new String();
        this.tipoConsulta = new String();
        this.tipoConsultaIndividual = new String();
        this.tipoReporte = new String();
        this.tipoPlan = new String();
        this.estatus = new String();
        this.messageError = new String();
    }



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

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getTipoConsulta() {
        return tipoConsulta;
    }

    public void setTipoConsulta(String tipoConsulta) {
        this.tipoConsulta = tipoConsulta;
    }

    public String getTipoReporte() {
        return tipoReporte;
    }

    public void setTipoReporte(String tipoReporte) {
        this.tipoReporte = tipoReporte;
    }
    
    public String getTipoPlan() {
        return tipoPlan;
    }

    public void setTipoPlan(String tipoPlan) {
        this.tipoPlan = tipoPlan;
    }
    
    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getTipoIdentificacionComercio() {
        return tipoIdentificacionComercio;
    }

    public void setTipoIdentificacionComercio(String tipoIdentificacionComercio) {
        this.tipoIdentificacionComercio = tipoIdentificacionComercio;
    }

    public String getIdentificacionComercio() {
        return identificacionComercio;
    }

    public void setIdentificacionComercio(String identificacionComercio) {
        this.identificacionComercio = identificacionComercio;
    }


    public String getTipoConsultaIndividual() {
        return tipoConsultaIndividual;
    }

    public void setTipoConsultaIndividual(String tipoConsultaIndividual) {
        this.tipoConsultaIndividual = tipoConsultaIndividual;
    }
    
    public String getMessageError() {
        return messageError;
    }

    public void setMessageError(String messageError) {
        this.messageError = messageError;
    }
        
}
