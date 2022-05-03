/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tranred.milpagosapp.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


/**
 *
 * @author Nazguls
 */
public class PlanPagoForm {
    private Integer id;
    
    @NotNull
    @Min(value = 1, message="Debe Seleccionar un Plan")
    private String idplan;
    
    private String codtipoplan;
        
    @NotNull
    private String codigoTerminal;
    
    private String codigoAfiliado;
    
    private Integer codigoComercio;
    
    private String montoTarifa;    
    
    private String montoInicial;   
    
    private String porcComisionBancaria;
    
    private String tipoPagoMantenimiento;
    
    private Boolean montoFijo;  
    
    private int moneda;
    
    @Min(value = 1, message="Debe Seleccionar una frecuencia")

    private Integer frecuencia; 

    private Integer estatus;
    
    private String fechainicio;
    
    private String fechafin;
    
    private String jsonPlanes;
    
    private String jsonPlanesActivos;
    
    private String jsonMontoCuotasCobradas;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdplan() {
        return idplan;
    }

    public void setIdplan(String idplan) {
        this.idplan = idplan;
    }

    public String getCodtipoplan() {
        return codtipoplan;
    }

    public void setCodtipoplan(String codtipoplan) {
        this.codtipoplan = codtipoplan;
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

    public String getMontoTarifa() {
        return montoTarifa;
    }

    public void setMontoTarifa(String montoTarifa) {
        this.montoTarifa = montoTarifa;
    }

    public String getMontoInicial() {
        return montoInicial;
    }

    public void setMontoInicial(String montoInicial) {
        this.montoInicial = montoInicial;
    }

    public String getPorcComisionBancaria() {
        return porcComisionBancaria;
    }

    public void setPorcComisionBancaria(String porcComisionBancaria) {
        this.porcComisionBancaria = porcComisionBancaria;
    }

    public String getTipoPagoMantenimiento() {
        return tipoPagoMantenimiento;
    }

    public void setTipoPagoMantenimiento(String tipoPagoMantenimiento) {
        this.tipoPagoMantenimiento = tipoPagoMantenimiento;
    }

    public Boolean getMontoFijo() {
        return montoFijo;
    }

    public void setMontoFijo(Boolean montoFijo) {
        this.montoFijo = montoFijo;
    }

    public int getMoneda() {
        return moneda;
    }

    public void setMoneda(int moneda) {
        this.moneda = moneda;
    }

    public Integer getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(Integer frecuencia) {
        this.frecuencia = frecuencia;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public String getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(String fechainicio) {
        this.fechainicio = fechainicio;
    }

    public String getFechafin() {
        return fechafin;
    }

    public void setFechafin(String fechafin) {
        this.fechafin = fechafin;
    }

    public String getJsonPlanes() {
        return jsonPlanes;
    }

    public void setJsonPlanes(String jsonPlanes) {
        this.jsonPlanes = jsonPlanes;
    }

    public String getJsonPlanesActivos() {
        return jsonPlanesActivos;
    }

    public void setJsonPlanesActivos(String jsonPlanesActivos) {
        this.jsonPlanesActivos = jsonPlanesActivos;
    }

    public String getJsonMontoCuotasCobradas() {
        return jsonMontoCuotasCobradas;
    }

    public void setJsonMontoCuotasCobradas(String jsonMontoCuotasCobradas) {
        this.jsonMontoCuotasCobradas = jsonMontoCuotasCobradas;
    }
    
}
