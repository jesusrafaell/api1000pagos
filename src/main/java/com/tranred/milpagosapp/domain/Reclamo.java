package com.tranred.milpagosapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * POJO que representa un Reclamo del Comercio
 *
 * Cada Reclamo tiene un id que la identifica
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */

@Entity
@Table (name="Reclamos")
public class Reclamo implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)     
    private Integer id;
    
    @NotNull    
    @NotEmpty      
    @Column(name = "recTipoReclamo")
    private String tipoReclamo;
           
    @Column(name = "recFechaRecepcion")      
    private Timestamp fechaRecepcion;
    
    @Column(name = "recIdUsuario")   
    private int idUsuario;
    
    @Column(name = "recNumeroTarjeta")
    @Size(max = 20)
    private String numeroTarjeta;
    
    @NotEmpty
    @DateTimeFormat(style="S-")
    @Column(name = "recFechaTransaccion")   
    private String fechaTransaccion;
    
    @Column(name = "recMonto")    
    private BigDecimal montoReclamo;
            
    @Column(name = "recLote")    
    private String numeroLote;
    
    @Column(name = "recTerminal")        
    private String terminal;
    
    @Column(name = "recCodEstatus")       
    private int codigoEstatus;
    
    @Column(name = "recObservaciones")
    @Size(max = 250)
    private String observacionesReclamo;
    
    @ManyToOne
    @JoinColumn(name="recTipoReclamo", referencedColumnName = "id", insertable = false, updatable = false)
    private TipoReclamo codTipoReclamo;
    
    @ManyToOne
    @JoinColumn(name="recCodEstatus", referencedColumnName = "id", insertable = false, updatable = false)
    private Estatus codEstatus;
    
    @Transient
    private int diasRespuesta;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipoReclamo() {
        return tipoReclamo;
    }

    public void setTipoReclamo(String tipoReclamo) {
        this.tipoReclamo = tipoReclamo;
    }

    public Timestamp getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(Timestamp fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getFechaTransaccion() {
        return fechaTransaccion;
    }

    public void setFechaTransaccion(String fechaTransaccion) {
        this.fechaTransaccion = fechaTransaccion;
    }

    public BigDecimal getMontoReclamo() {
        return montoReclamo;
    }

    public void setMontoReclamo(BigDecimal montoReclamo) {
        this.montoReclamo = montoReclamo;
    }

    public String getNumeroLote() {
        return numeroLote;
    }

    public void setNumeroLote(String numeroLote) {
        this.numeroLote = numeroLote;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public int getCodigoEstatus() {
        return codigoEstatus;
    }

    public void setCodigoEstatus(int codigoEstatus) {
        this.codigoEstatus = codigoEstatus;
    }

    public String getObservacionesReclamo() {
        return observacionesReclamo;
    }

    public void setObservacionesReclamo(String observacionesReclamo) {
        this.observacionesReclamo = observacionesReclamo;
    }

    public TipoReclamo getCodTipoReclamo() {
        return codTipoReclamo;
    }

    public void setCodTipoReclamo(TipoReclamo codTipoReclamo) {
        this.codTipoReclamo = codTipoReclamo;
    }

    public Estatus getCodEstatus() {
        return codEstatus;
    }

    public void setCodEstatus(Estatus codEstatus) {
        this.codEstatus = codEstatus;
    }

    public int getDiasRespuesta() {
        return diasRespuesta;
    }

    public void setDiasRespuesta(int diasRespuesta) {
        this.diasRespuesta = diasRespuesta;
    }
        
}
