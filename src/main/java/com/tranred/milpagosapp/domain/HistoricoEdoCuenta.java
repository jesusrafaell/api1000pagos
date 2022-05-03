package com.tranred.milpagosapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;

/**
 * POJO que representa un registro de la tabla Historico para Generar Estados de Cuenta
 * La información es obtenida del Store Procedure SP_consultaEstadosCuenta
 * 
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */

@NamedNativeQueries({
    @NamedNativeQuery(
            name = "consultaEdoCuenta",
            query = "EXEC SP_consultaEstadosCuenta :fecha,:codigoComercio,:terminal",
            resultClass = HistoricoEdoCuenta.class
    )
 })
@Entity
public class HistoricoEdoCuenta implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Column(name = "hisId")
    @Id private Integer id;          
                
    @Column(name = "aboTerminal")
    private String terminal;
                            
    @Column(name = "comerDesc")
    private String descripcionComercio;        
       
    @Column(name = "comerRif")
    private String rifComercio;      
    
    @Column(name = "contTelefLoc")
    private String telefonoHabitacion;    
    
    @Column(name = "contTelefMov")
    private String telefonoCelular;
    
    @Column(name = "comerDireccion")
    private String direccionComercio;
    
    @Column(name = "banDescBan")
    private String nombreBanco;
            
    @Column(name = "hisAmountTotal")
    private BigDecimal montoTotal;
            
    @Column(name = "hisComisionMantenimiento")
    private BigDecimal hisComisionMantenimiento;
            
    @Column(name = "hisIvaSobreMantenimiento")
    private BigDecimal hisIvaSobreMantenimiento;
            
    @Column(name = "hisComisionBancaria")
    private BigDecimal hisComisionBancaria;
            
    @Column(name = "hisNetoComisionBancaria")
    private BigDecimal hisNetoComisionBancaria;
            
    @Column(name = "hisDebitoContraCargo")
    private BigDecimal hisDebitoContraCargo;
    
    @Column(name = "montoFacturado")    
    private BigDecimal montoFacturado;
    
    @Column(name = "hisFechaEjecucion")
    private String fecha;         
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    public BigDecimal getMontoFacturado() {
        return montoFacturado;
    }

    public void setMontoFacturado(BigDecimal montoFacturado) {
        this.montoFacturado = montoFacturado;
    }

    public BigDecimal getHisComisionMantenimiento() {
        return hisComisionMantenimiento;
    }

    public void setHisComisionMantenimiento(BigDecimal hisComisionMantenimiento) {
        this.hisComisionMantenimiento = hisComisionMantenimiento;
    }

    public BigDecimal getHisIvaSobreMantenimiento() {
        return hisIvaSobreMantenimiento;
    }

    public void setHisIvaSobreMantenimiento(BigDecimal hisIvaSobreMantenimiento) {
        this.hisIvaSobreMantenimiento = hisIvaSobreMantenimiento;
    }

    public BigDecimal getHisComisionBancaria() {
        return hisComisionBancaria;
    }

    public void setHisComisionBancaria(BigDecimal hisComisionBancaria) {
        this.hisComisionBancaria = hisComisionBancaria;
    }

    public BigDecimal getHisNetoComisionBancaria() {
        return hisNetoComisionBancaria;
    }

    public void setHisNetoComisionBancaria(BigDecimal hisNetoComisionBancaria) {
        this.hisNetoComisionBancaria = hisNetoComisionBancaria;
    }

    public BigDecimal getHisDebitoContraCargo() {
        return hisDebitoContraCargo;
    }

    public void setHisDebitoContraCargo(BigDecimal hisDebitoContraCargo) {
        this.hisDebitoContraCargo = hisDebitoContraCargo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
}
