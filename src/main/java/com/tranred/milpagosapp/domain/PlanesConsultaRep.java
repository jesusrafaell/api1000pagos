package com.tranred.milpagosapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * POJO que representa un registro para Generar Archivos de Cobranza
 * 
 * 
 * @author mcaraballo@emsys-solutions.net
 * @version 0.1
 */

public class PlanesConsultaRep implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String aboCodAfi;         

    private String aboCodComercio;         
                
    private String aboTerminal;
    
    private Integer planId;
    
    private String descTipoPlan;
    
    private String planNombre;
                            
    private String estatusDesc;    
    
    private String montoTarifa;
    
    private String montoInicial;
    
    private Integer planPlazo;
    
    private String fechaInicio;
            
    private String fechaFin;
        
    private String frecuenciaDesc;  
    
    private String monedaDesc;
    
    private String comerDesc;        
       
    private String comerRif;        
       
    private String contTelefMov;        
       
    private String aciDesc;        
       
    private String aciRif;        
       
    private String aciTLF;        

    public String getAboCodAfi() {
        return aboCodAfi;
    }

    public void setAboCodAfi(String aboCodAfi) {
        this.aboCodAfi = aboCodAfi;
    }

    public String getAboCodComercio() {
        return aboCodComercio;
    }

    public void setAboCodComercio(String aboCodComercio) {
        this.aboCodComercio = aboCodComercio;
    }

    public String getAboTerminal() {
        return aboTerminal;
    }

    public void setAboTerminal(String aboTerminal) {
        this.aboTerminal = aboTerminal;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public String getDescTipoPlan() {
        return descTipoPlan;
    }

    public void setDescTipoPlan(String descTipoPlan) {
        this.descTipoPlan = descTipoPlan;
    }

    public String getPlanNombre() {
        return planNombre;
    }

    public void setPlanNombre(String planNombre) {
        this.planNombre = planNombre;
    }

    public String getEstatusDesc() {
        return estatusDesc;
    }

    public void setEstatusDesc(String estatusDesc) {
        this.estatusDesc = estatusDesc;
    }

    public String getMontoTarifa() {
        return montoTarifa;
    }

    public void setMontoTarifa(String montoTarifa) {
        this.montoTarifa = montoTarifa;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getFrecuenciaDesc() {
        return frecuenciaDesc;
    }

    public void setFrecuenciaDesc(String frecuenciaDesc) {
        this.frecuenciaDesc = frecuenciaDesc;
    }

    public String getMonedaDesc() {
        return monedaDesc;
    }

    public void setMonedaDesc(String monedaDesc) {
        this.monedaDesc = monedaDesc;
    }

    public String getComerDesc() {
        return comerDesc;
    }

    public void setComerDesc(String comerDesc) {
        this.comerDesc = comerDesc;
    }

    public String getComerRif() {
        return comerRif;
    }

    public void setComerRif(String comerRif) {
        this.comerRif = comerRif;
    }

    public String getContTelefMov() {
        return contTelefMov;
    }

    public void setContTelefMov(String contTelefMov) {
        this.contTelefMov = contTelefMov;
    }

    public String getAciDesc() {
        return aciDesc;
    }

    public void setAciDesc(String aciDesc) {
        this.aciDesc = aciDesc;
    }

    public String getAciRif() {
        return aciRif;
    }

    public void setAciRif(String aciRif) {
        this.aciRif = aciRif;
    }

    public String getAciTLF() {
        return aciTLF;
    }

    public void setAciTLF(String aciTLF) {
        this.aciTLF = aciTLF;
    }

    public String getMontoInicial() {
        return montoInicial;
    }

    public void setMontoInicial(String montoInicial) {
        this.montoInicial = montoInicial;
    }

    public Integer getPlanPlazo() {
        return planPlazo;
    }

    public void setPlanPlazo(Integer planPlazo) {
        this.planPlazo = planPlazo;
    }

}