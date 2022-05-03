/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tranred.milpagosapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * POJO que representa un plan pago detalle, nos traera la lista de planes por cada terminal necesaria para mostrar en el listado
 *
 * Cada registro tiene un id que lo identifica, procediente de PlanPago
 * 
 * @author mcaraballo@emsys-solutions.net
 */
@Entity
@Inheritance(strategy =InheritanceType.JOINED)
@Table(name="infoPlanesxTerminalView")
public class PlanPagoDetail implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "planPagoId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotNull
    @Column(name = "planId")
    private Integer idplan;
     
    @Column(name = "aboTerminal")    
    @NotNull
    private String codigoTerminal;
    
    @NotNull
    @Column(name = "aboCodAfi")
    private String codigoAfiliado;
    
    @NotNull
    @Column(name = "aboCodComercio")
    private Integer codigoComercio;    
    
    @Column(name = "montoTarifa")
    private BigDecimal montoTarifa;    
    
    @Column(name = "montoFijo")
    private Integer montoFijo; 
    
    @Column(name = "porcComisionBancaria")
    private BigDecimal porcComisionBancaria;   
    
    @Column(name = "montoInicial")
    private BigDecimal montoInicial;    
      
    @Column(name = "tipoPagoMantenimiento")
    private String tipoPagoMantenimiento;
    
    @Column(name = "monedaId")
    private Integer moneda; 
    
    @Column(name = "cantCuotas")
    private Integer cantCuotas; 
  
    @Column(name = "frecuenciaId")
    private Integer frecuenciaId; 

    @NotNull
    @Column(name = "estatusId")
    private Integer estatusId;
    
    @Column(name = "fechaInicio")
    private String fechaInicio;
    
    @Column(name = "fechaFin")
    private String fechaFin;
    
    @Column(name = "planNombre")
    private String planNombre;	 
    
    @Column(name = "planDesc")
    private String planDescripcion;
    
    @Column(name = "frecuenciaDescripcion")
    private String frecuenciaDescripcion;	
    
    @Column(name = "estatusDescripcion")
    private String estatusDescripcion;

    @NotNull
    @Column(name = "codTipoPlan")
    private Integer codTipoPlan;
    
    @NotNull
    @Column(name = "descTipoPlan")
    private String descTipoPlan;
    
    @NotNull
    @Column(name = "editable")
    private String editable;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdplan() {
        return idplan;
    }

    public void setIdplan(Integer idplan) {
        this.idplan = idplan;
    }

    public String getCodigoTerminal() {
        return codigoTerminal;
    }

    public void setCodigoTerminal(String codigoTerminal) {
        this.codigoTerminal = codigoTerminal;
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

    public BigDecimal getMontoTarifa() {
        return montoTarifa;
    }

    public void setMontoTarifa(BigDecimal montoTarifa) {
        this.montoTarifa = montoTarifa;
    }

    public Integer getMontoFijo() {
        return montoFijo;
    }

    public void setMontoFijo(Integer montoFijo) {
        this.montoFijo = montoFijo;
    }

    public BigDecimal getPorcComisionBancaria() {
        return porcComisionBancaria;
    }

    public void setPorcComisionBancaria(BigDecimal porcComisionBancaria) {
        this.porcComisionBancaria = porcComisionBancaria;
    }

    public Integer getFrecuenciaId() {
        return frecuenciaId;
    }

    public void setFrecuenciaId(Integer frecuenciaId) {
        this.frecuenciaId = frecuenciaId;
    }

    public Integer getEstatusId() {
        return estatusId;
    }

    public void setEstatusId(Integer estatusId) {
        this.estatusId = estatusId;
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

    public String getPlanNombre() {
        return planNombre;
    }

    public void setPlanNombre(String planNombre) {
        this.planNombre = planNombre;
    }

    public String getPlanDescripcion() {
        return planDescripcion;
    }

    public void setPlanDescripcion(String planDescripcion) {
        this.planDescripcion = planDescripcion;
    }

    public String getFrecuenciaDescripcion() {
        return frecuenciaDescripcion;
    }

    public void setFrecuenciaDescripcion(String frecuenciaDescripcion) {
        this.frecuenciaDescripcion = frecuenciaDescripcion;
    }

    public String getEstatusDescripcion() {
        return estatusDescripcion;
    }

    public void setEstatusDescripcion(String estatusDescripcion) {
        this.estatusDescripcion = estatusDescripcion;
    }

    public Integer getCodTipoPlan() {
        return codTipoPlan;
    }

    public void setCodTipoPlan(Integer codTipoPlan) {
        this.codTipoPlan = codTipoPlan;
    }

    public String getDescTipoPlan() {
        return descTipoPlan;
    }

    public void setDescTipoPlan(String descTipoPlan) {
        this.descTipoPlan = descTipoPlan;
    }

    public BigDecimal getMontoInicial() {
        return montoInicial;
    }

    public void setMontoInicial(BigDecimal montoInicial) {
        this.montoInicial = montoInicial;
    }

    public Integer getCantCuotas() {
        return cantCuotas;
    }

    public void setCantCuotas(Integer cantCuotas) {
        this.cantCuotas = cantCuotas;
    }

    public String getTipoPagoMantenimiento() {
        return tipoPagoMantenimiento;
    }

    public void setTipoPagoMantenimiento(String tipoPagoMantenimiento) {
        this.tipoPagoMantenimiento = tipoPagoMantenimiento;
    }

    public Integer getMoneda() {
        return moneda;
    }

    public void setMoneda(Integer moneda) {
        this.moneda = moneda;
    }

    public String getEditable() {
        return editable;
    }

    public void setEditable(String editable) {
        this.editable = editable;
    }

    
    
}
