package com.tranred.milpagosapp.domain;

import java.io.Serializable;

/**
 * POJO que representa un registro para Generar Estados de Cuenta
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */

public class EstadoCuenta implements Serializable {
    
    private static final long serialVersionUID = 1L;      
                    
    private String terminal;
                            
    private String descripcionComercio;        
          
    private String rifComercio;      
       
    private String telefonoHabitacion;    
        
    private String telefonoCelular;
       
    private String direccionComercio;
        
    private String nombreBanco;
                
    private String montoTotal;
    
    private String montoTotalPetro;
        
    private String montoFacturado;
        
    private String hisComisionMantenimiento;
            
    private String hisIvaSobreMantenimiento;
            
    private String hisComisionBancaria;
            
    private String hisNetoComisionBancaria;
            
    private String hisDebitoContraCargo;
    
    private String fecha;         
       
    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getDescripcionComercio() {
        return descripcionComercio;
    }

    public void setDescripcionComercio(String descripcionComercio) {
        this.descripcionComercio = descripcionComercio;
    }

    public String getRifComercio() {
        return rifComercio;
    }

    public void setRifComercio(String rifComercio) {
        this.rifComercio = rifComercio;
    }

    public String getTelefonoHabitacion() {
        return telefonoHabitacion;
    }

    public void setTelefonoHabitacion(String telefonoHabitacion) {
        this.telefonoHabitacion = telefonoHabitacion;
    }

    public String getTelefonoCelular() {
        return telefonoCelular;
    }

    public void setTelefonoCelular(String telefonoCelular) {
        this.telefonoCelular = telefonoCelular;
    }

    public String getDireccionComercio() {
        return direccionComercio;
    }

    public void setDireccionComercio(String direccionComercio) {
        this.direccionComercio = direccionComercio;
    }

    public String getNombreBanco() {
        return nombreBanco;
    }

    public void setNombreBanco(String nombreBanco) {
        this.nombreBanco = nombreBanco;
    }

    public String getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(String montoTotal) {
        this.montoTotal = montoTotal;
    }

    public String getMontoTotalPetro() {
        return montoTotalPetro;
    }

    public void setMontoTotalPetro(String montoTotalPetro) {
        this.montoTotalPetro = montoTotalPetro;
    }

    public String getMontoFacturado() {
        return montoFacturado;
    }

    public void setMontoFacturado(String montoFacturado) {
        this.montoFacturado = montoFacturado;
    }

    public String getHisComisionMantenimiento() {
        return hisComisionMantenimiento;
    }

    public void setHisComisionMantenimiento(String hisComisionMantenimiento) {
        this.hisComisionMantenimiento = hisComisionMantenimiento;
    }

    public String getHisIvaSobreMantenimiento() {
        return hisIvaSobreMantenimiento;
    }

    public void setHisIvaSobreMantenimiento(String hisIvaSobreMantenimiento) {
        this.hisIvaSobreMantenimiento = hisIvaSobreMantenimiento;
    }

    public String getHisComisionBancaria() {
        return hisComisionBancaria;
    }

    public void setHisComisionBancaria(String hisComisionBancaria) {
        this.hisComisionBancaria = hisComisionBancaria;
    }

    public String getHisNetoComisionBancaria() {
        return hisNetoComisionBancaria;
    }

    public void setHisNetoComisionBancaria(String hisNetoComisionBancaria) {
        this.hisNetoComisionBancaria = hisNetoComisionBancaria;
    }

    public String getHisDebitoContraCargo() {
        return hisDebitoContraCargo;
    }

    public void setHisDebitoContraCargo(String hisDebitoContraCargo) {
        this.hisDebitoContraCargo = hisDebitoContraCargo;
    }


    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
}
