/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tranred.milpagosapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import javax.validation.constraints.Size;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * POJO que representa un plan pago de cobranza para un terminal
 *
 * Cada plan tiene un id que lo identifica
 *
 * @author mcaraballo@emsys-solutions.net
 * @version 0.1
 */


@Entity
@Inheritance(strategy =InheritanceType.JOINED)
@Table(name="PlanCuota")
public class PlanCuota implements Serializable{
	
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "idPlanCuota")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotNull
    @Column(name = "aboCodAfi")
    private String codigoAfiliado;
    
    @NotNull
    @Column(name = "aboCodComercio")
    private Integer codigoComercio;
    
    @Column(name = "aboTerminal")    
    @NotNull
    private String codigoTerminal;
    
    @NotNull
    @Column(name = "planPagoId")
    private Integer planPagoId;
            
    @Column(name = "montoTotal")
    private BigDecimal montoTotal;    
    
    @Column(name = "montoComision")
    private BigDecimal montoComision;    
      
    @Column(name = "montoIVA")
    private BigDecimal montoIVA;   
    
    @Column(name = "monedaId")
    private Integer monedaId;  
    
    @Column(name = "tasaId")
    private Integer tasaId; 
    
    @Column(name = "tasaValor")
    private BigDecimal tasaValor;  
        
    @Column(name = "fechaProceso")
    private Date fechaProceso;
    
    @Column(name = "fechaPago")
    private Date fechaPago;
    
    @NotNull
    @Column(name = "estatusId")
    private Integer estatus;
    
    @Column(name = "fechaProcesoLoteCerrado")
    private Date fechaProcesoLoteCerrado;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigoAfiliado() {
        return codigoAfiliado;
    }

    public void setCodigoAfiliado(String codigoAfiliado) {
        this.codigoAfiliado = codigoAfiliado;
    }

    public Integer getCodigoComercio() {
        return codigoComercio;
    }

    public void setCodigoComercio(Integer codigoComercio) {
        this.codigoComercio = codigoComercio;
    }

    public String getCodigoTerminal() {
        return codigoTerminal;
    }

    public void setCodigoTerminal(String codigoTerminal) {
        this.codigoTerminal = codigoTerminal;
    }

    public Integer getPlanPagoId() {
        return planPagoId;
    }

    public void setPlanPagoId(Integer planPagoId) {
        this.planPagoId = planPagoId;
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

    public Integer getMonedaId() {
        return monedaId;
    }

    public void setMonedaId(Integer monedaId) {
        this.monedaId = monedaId;
    }

    public Integer getTasaId() {
        return tasaId;
    }

    public void setTasaId(Integer tasaId) {
        this.tasaId = tasaId;
    }

    public BigDecimal getTasaValor() {
        return tasaValor;
    }

    public void setTasaValor(BigDecimal tasaValor) {
        this.tasaValor = tasaValor;
    }

    public Date getFechaProceso() {
        return fechaProceso;
    }

    public void setFechaProceso(Date fechaProceso) {
        this.fechaProceso = fechaProceso;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public Date getFechaProcesoLoteCerrado() {
        return fechaProcesoLoteCerrado;
    }

    public void setFechaProcesoLoteCerrado(Date fechaProcesoLoteCerrado) {
        this.fechaProcesoLoteCerrado = fechaProcesoLoteCerrado;
    }
    
    
    
}
