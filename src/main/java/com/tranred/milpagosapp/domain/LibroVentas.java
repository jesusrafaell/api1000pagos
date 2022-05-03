package com.tranred.milpagosapp.domain;

import java.io.Serializable;

/**
 * POJO que representa un Registro del Libro de Ventas
 *
 * Cada Registro tiene un id que lo identifica
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */

public class LibroVentas implements Serializable {
    
    private static final long serialVersionUID = 1L;    
    
    private String fecha;
    
    private String rif;
    
    private String razonSocial;
    
    private String planilla;
    
    private String numeroFactura;
        
    private String numeroControl;
    
    private String notaCredito;
    
    private String facturaAfectada;
    
    private String montoTotal;
    
    private String ventasNoGravadas;
    
    private String baseImponible;
    
    private String porcentaje;
        
    private String iva;              
        
    private String ivaRetenido;              
        
    private String ivaRecibido;    

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getRif() {
        return rif;
    }

    public void setRif(String rif) {
        this.rif = rif;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getPlanilla() {
        return planilla;
    }

    public void setPlanilla(String planilla) {
        this.planilla = planilla;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public String getNumeroControl() {
        return numeroControl;
    }

    public void setNumeroControl(String numeroControl) {
        this.numeroControl = numeroControl;
    }

    public String getNotaCredito() {
        return notaCredito;
    }

    public void setNotaCredito(String notaCredito) {
        this.notaCredito = notaCredito;
    }

    public String getFacturaAfectada() {
        return facturaAfectada;
    }

    public void setFacturaAfectada(String facturaAfectada) {
        this.facturaAfectada = facturaAfectada;
    }

    public String getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(String montoTotal) {
        this.montoTotal = montoTotal;
    }

    public String getVentasNoGravadas() {
        return ventasNoGravadas;
    }

    public void setVentasNoGravadas(String ventasNoGravadas) {
        this.ventasNoGravadas = ventasNoGravadas;
    }

    public String getBaseImponible() {
        return baseImponible;
    }

    public void setBaseImponible(String baseImponible) {
        this.baseImponible = baseImponible;
    }

    public String getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(String porcentaje) {
        this.porcentaje = porcentaje;
    }

    public String getIva() {
        return iva;
    }

    public void setIva(String iva) {
        this.iva = iva;
    }

    public String getIvaRetenido() {
        return ivaRetenido;
    }

    public void setIvaRetenido(String ivaRetenido) {
        this.ivaRetenido = ivaRetenido;
    }

    public String getIvaRecibido() {
        return ivaRecibido;
    }

    public void setIvaRecibido(String ivaRecibido) {
        this.ivaRecibido = ivaRecibido;
    }
    
    
}
