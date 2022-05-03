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

public class Cobranzas implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String aboCodAfi;         

    private String aboCodComercio;         
                
    private String aboTerminal;
                            
    private String estatusDesc;        
       
    private String cantCuotas;      
    
    private BigDecimal montoTotal;    
    
    private BigDecimal montoComision;
    
    private BigDecimal montoIVA;
    
    private BigDecimal tasaValor;
    
    private String fechaProcesoIni;
            
    private String fechaProcesoFin;
        
    private String fechaPagoUlt;      
             
    private String planId;    
    
    private String planNombre;
    
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

    public String getEstatusDesc() {
        return estatusDesc;
    }

    public void setEstatusDesc(String estatusDesc) {
        this.estatusDesc = estatusDesc;
    }

    public String getCantCuotas() {
        return cantCuotas;
    }

    public void setCantCuotas(String cantCuotas) {
        this.cantCuotas = cantCuotas;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    public BigDecimal getMontoComision() {
        return montoComision;
    }

    public void setMontoComision(BigDecimal montoComision) {
        this.montoComision = montoComision;
    }

    public BigDecimal getMontoIVA() {
        return montoIVA;
    }

    public void setMontoIVA(BigDecimal montoIVA) {
        this.montoIVA = montoIVA;
    }

    public String getFechaProcesoIni() {
        return fechaProcesoIni;
    }

    public void setFechaProcesoIni(String fechaProcesoIni) {
        this.fechaProcesoIni = fechaProcesoIni;
    }

    public String getFechaProcesoFin() {
        return fechaProcesoFin;
    }

    public void setFechaProcesoFin(String fechaProcesoFin) {
        this.fechaProcesoFin = fechaProcesoFin;
    }

    public String getFechaPagoUlt() {
        return fechaPagoUlt;
    }

    public void setFechaPagoUlt(String fechaPagoUlt) {
        this.fechaPagoUlt = fechaPagoUlt;
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

    public BigDecimal getTasaValor() {
        return tasaValor;
    }

    public void setTasaValor(BigDecimal tasaValor) {
        this.tasaValor = tasaValor;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getPlanNombre() {
        return planNombre;
    }

    public void setPlanNombre(String planNombre) {
        this.planNombre = planNombre;
    }

}