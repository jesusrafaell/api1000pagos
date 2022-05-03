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
 * Clase que recibe los datos del formulario crearPlan y editarPlan
 *
 * @author mcaraballo@emsys-solutions.net
 * @version 0.1
 */

public class PlanForm{
	
    private Integer id;
    
    @NotNull
    @NotEmpty 
    private String codtipoplan;
    
    private String descTipoPlan;
    
    @NotNull(message = "")
    @NotEmpty(message = "Debe Escribir un Nombre de Plan")
    private String nombre;	 
    
    @NotNull(message = "")
    @NotEmpty(message = "Debe Escribir una Descripción del Plan") 
    private String descripcion;	 
    
    private String montoTarifa;    
    
    private String montoInicial;   
    
    private String porcComisionBancaria;
    
    private String tipoPagoMantenimiento;
    
    private Boolean montoFijo;
    
    private String planplazo;                    
    
    private String fechainicio;
    
    private String fechafin;
    
    @NotNull
    private Boolean indefinido; 
    
    @NotNull
    private int frecuencia; 

    @NotNull
    private int moneda; 
    
    @NotNull
    private int estatus;
    
    @NotNull
    private String montopromedio; 
    
    private String transaccion; 
    
    private String diasimpago; 
    
    private String porcentaje; 
    
    private String jsonTipoPlanes;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodtipoplan() {
        return codtipoplan;
    }

    public void setCodtipoplan(String codtipoplan) {
        this.codtipoplan = codtipoplan;
    }

    public String getDescTipoPlan() {
        return descTipoPlan;
    }

    public void setDescTipoPlan(String descTipoPlan) {
        this.descTipoPlan = descTipoPlan;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMontoTarifa() {
        return montoTarifa;
    }

    public void setMontoTarifa(String montoTarifa) {
        this.montoTarifa = montoTarifa;
    }

    public String getPorcComisionBancaria() {
        return porcComisionBancaria;
    }

    public void setPorcComisionBancaria(String porcComisionBancaria) {
        this.porcComisionBancaria = porcComisionBancaria;
    }

    public Boolean getMontoFijo() {
        return montoFijo;
    }

    public void setMontoFijo(Boolean montoFijo) {
        this.montoFijo = montoFijo;
    }

    public String getTipoPagoMantenimiento() {
        return tipoPagoMantenimiento;
    }

    public void setTipoPagoMantenimiento(String tipoPagoMantenimiento) {
        this.tipoPagoMantenimiento = tipoPagoMantenimiento;
    }

    public String getPlanplazo() {
        return planplazo;
    }

    public void setPlanplazo(String planplazo) {
        this.planplazo = planplazo;
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

    public Boolean getIndefinido() {
        return indefinido;
    }

    public void setIndefinido(Boolean indefinido) {
        this.indefinido = indefinido;
    }

    public int getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(int frecuencia) {
        this.frecuencia = frecuencia;
    }

    public int getMoneda() {
        return moneda;
    }

    public void setMoneda(int moneda) {
        this.moneda = moneda;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public String getMontopromedio() {
        return montopromedio;
    }

    public void setMontopromedio(String montopromedio) {
        this.montopromedio = montopromedio;
    }

    public String getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(String transaccion) {
        this.transaccion = transaccion;
    }

    public String getDiasimpago() {
        return diasimpago;
    }

    public void setDiasimpago(String diasimpago) {
        this.diasimpago = diasimpago;
    }

    public String getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(String porcentaje) {
        this.porcentaje = porcentaje;
    }

    public String getMontoInicial() {
        return montoInicial;
    }

    public void setMontoInicial(String montoInicial) {
        this.montoInicial = montoInicial;
    }

    public String getJsonTipoPlanes() {
        return jsonTipoPlanes;
    }

    public void setJsonTipoPlanes(String jsonTipoPlanes) {
        this.jsonTipoPlanes = jsonTipoPlanes;
    }
    
    
    
}