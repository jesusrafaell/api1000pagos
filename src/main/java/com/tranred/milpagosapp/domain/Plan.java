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
 * POJO que representa un plan de cobranza
 *
 * Cada plan tiene un id que lo identifica
 *
 * @author jcperez@emsys-solutions.net
 * @version 0.1
 */


@Entity
@Inheritance(strategy =InheritanceType.JOINED)
@Table(name="Planes")
public class Plan implements Serializable{
	
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "planId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotNull
    @NotEmpty 
    @Column(name = "codTipoPlan")
    private String codtipoplan;
    
    @NotNull
    @NotEmpty 
    @Column(name = "planNombre")
    private String nombre;	 
    
    @NotNull
    @NotEmpty 
    @Column(name = "planDesc")
    private String descripcion;	 
    
    @Column(name = "montoTarifa")
    private BigDecimal montoTarifa;

    @Column(name = "porcComisionBancaria")
    private BigDecimal porcComisionBancaria;   
    
    @Column(name = "montoFijo")
    private Integer montoFijo;    
   
    @Column(name = "montoInicial")
    private BigDecimal montoInicial;    
      
    @Column(name = "cantCuotas")
    private Integer cantCuotas; 
    
    @Column(name = "planPlazo")
    private int planplazo;

    @Column(name = "fechaInicio")
    private String fechainicio;
    
    @Column(name = "fechaFin")
    private String fechafin;
    

    @Column(name = "indefinido")
    private String indefinido; 
    
    @NotNull
    @Column(name = "frecuenciaId")
    private int frecuencia; 

    @NotNull
    @Column(name = "monedaId")
    private int moneda; 
    
    @NotNull
    @Column(name = "estatusId")
    private int estatus;
    
    @NotNull
    @Column(name = "montoPromedio")
    private BigDecimal montopromedio; 
    
 
    @Column(name = "cantidadTransacciones")
    private int transaccion; 
    
    @Column(name = "cantidadDiasImpago")
    private int diasimpago; 
    
    @Column(name =  "porcentajeImpago")
    private int porcentaje; 
  
    
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
    
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
             
    public BigDecimal getMontoTarifa() {
        return montoTarifa;
    }

    public void setMontoTarifa(BigDecimal montoTarifa) {
        this.montoTarifa= montoTarifa;
    }

    public BigDecimal getPorcComisionBancaria() {
        return porcComisionBancaria;
    }

    public void setPorcComisionBancaria(BigDecimal porcComisionBancaria) {
        this.porcComisionBancaria = porcComisionBancaria;
    }

    public Integer getMontoFijo() {
        return montoFijo;
    }

    public void setMontoFijo(Integer montoFijo) {
        this.montoFijo = montoFijo;
    }

    public int getPlanplazo() {
        return planplazo;
    }

    public void setPlanplazo(int planplazo) {
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

    public void setFechafin (String fechafin) {
        this.fechafin = fechafin;
    }
    
   public String getIndefinido() {
        return indefinido;
    }

    public void setIndefinido(String indefinido) {
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
    
     public BigDecimal getMontopromedio() {
        return montopromedio;
    }

    public void setMontopromedio(BigDecimal montopromedio) {
        this.montopromedio= montopromedio;
    }
    
     public int getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(int transaccion) {
        this.transaccion = transaccion;
    }
    
    public int getDiasimpago() {
        return diasimpago;
    }

    public void setDiasimpago(int diasimpago) {
        this.diasimpago = diasimpago;
    }
    
    public int getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(int porcentaje) {
        this.porcentaje = porcentaje;
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
    
}
