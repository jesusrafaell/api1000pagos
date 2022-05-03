package com.tranred.milpagosapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;

/**
 * POJO que representa un registro de la tabla Historico para Generar Estados de Cuenta
 * La información es obtenida del Store Procedure SP_consultaEstadosCuenta
 * 
 * @author mcaraballo@emsys-solutions.net
 * @version 0.1
 */

@NamedNativeQueries({
    @NamedNativeQuery(
            name = "consultaPlanes",
            query = "EXEC SP_consultaPlanes :tipoReporte,:estatus,:tipoPlan,:tipoConsulta,:tipoConsultaIndividual,:tipoIdentificacionComercio,:identificacionComercio,:terminal",
            resultClass = PlanesConsulta.class
    )
 })
@Entity
public class PlanesConsulta implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "id")
    private Long id;      
                
    @Column(name = "aboCodAfi")
    private String aboCodAfi;         
                
    @Column(name = "aboCodComercio")
    private String aboCodComercio;         
                
    @Column(name = "aboTerminal")
    private String aboTerminal;    
      
    @Column(name = "planId")
    private Integer planId; 
                            
    @Column(name = "descTipoPlan")
    private String descTipoPlan;        
       
    @Column(name = "planNombre")
    private String planNombre;      
    
    @Column(name = "estatusDesc")
    private String estatusDesc;    
    
    @Column(name = "montoTarifa")
    private String montoTarifa;
    
    @Column(name = "montoInicial")
    private String montoInicial;    
      
    @Column(name = "planPlazo")
    private Integer planPlazo; 
    
    @Column(name = "fechaInicio")
    private Date fechaInicio;
    
    @Column(name = "fechaFin")
    private Date fechaFin;
            
    @Column(name = "frecuenciaDesc")
    private String frecuenciaDesc;
        
    @Column(name = "monedaDesc")
    private String monedaDesc;         
    
    @Column(name = "comerDesc")
    private String comerDesc;        
       
    @Column(name = "comerRif")
    private String comerRif;        
       
    @Column(name = "contTelefMov")
    private String contTelefMov;        
       
    @Column(name = "aciDesc")
    private String aciDesc;        
       
    @Column(name = "aciRif")
    private String aciRif;        
       
    @Column(name = "aciTLF")
    private String aciTLF;        

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public String getDescTipoPlan() {
        return descTipoPlan;
    }

    public void setDescTipoPlan(String descTipoPlan) {
        this.descTipoPlan = descTipoPlan;
    }

    public String getPlanNombre() {
        return planNombre;
    }

    public void setPlanNombre(String planNombre) {
        this.planNombre = planNombre;
    }

    public String getEstatusDesc() {
        return estatusDesc;
    }

    public void setEstatusDesc(String estatusDesc) {
        this.estatusDesc = estatusDesc;
    }

    public String getMontoTarifa() {
        return montoTarifa;
    }

    public void setMontoTarifa(String montoTarifa) {
        this.montoTarifa = montoTarifa;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getFrecuenciaDesc() {
        return frecuenciaDesc;
    }

    public void setFrecuenciaDesc(String frecuenciaDesc) {
        this.frecuenciaDesc = frecuenciaDesc;
    }

    public String getMonedaDesc() {
        return monedaDesc;
    }

    public void setMonedaDesc(String monedaDesc) {
        this.monedaDesc = monedaDesc;
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

    public String getMontoInicial() {
        return montoInicial;
    }

    public void setMontoInicial(String montoInicial) {
        this.montoInicial = montoInicial;
    }

    public Integer getPlanPlazo() {
        return planPlazo;
    }

    public void setPlanPlazo(Integer planPlazo) {
        this.planPlazo = planPlazo;
    }

    
}
