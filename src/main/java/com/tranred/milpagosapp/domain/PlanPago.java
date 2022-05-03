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
 * @author jcperez@emsys-solutions.net
 * @version 0.1
 */


@Entity
@Inheritance(strategy =InheritanceType.JOINED)
@Table(name="PlanPago")
public class PlanPago implements Serializable{
	
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
    
    @Column(name = "montoInicial")
    private BigDecimal montoInicial;    
      
    @Column(name = "porcComisionBancaria")
    private BigDecimal porcComisionBancaria;   
    
    @Column(name = "montoFijo")
    private Integer montoFijo;  
    
    @Column(name = "cantCuotas")
    private Integer cantCuotas; 
    
    @Column(name = "frecuenciaId")
    private Integer frecuencia; 

    @NotNull
    @Column(name = "estatusId")
    private Integer estatus;
    
    @Column(name = "fechaInicio")
    private Date fechainicio;
    
    @Column(name = "fechaFin")
    private Date fechafin;
    
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
        this.montoTarifa= montoTarifa;
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
    
   public Date getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(Date fechainicio) {
        this.fechainicio = fechainicio;
    }
    
    public Date getFechafin() {
        return fechafin;
    }

    public void setFechafin (Date fechafin) {
        this.fechafin = fechafin;
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
    
}
