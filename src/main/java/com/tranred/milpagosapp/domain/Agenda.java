package com.tranred.milpagosapp.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * POJO que representa un evento de la Agenda asociado a un aliado
 *
 * Cada Evento tiene un id que lo identifica
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */

@Entity
@Table (name="Agenda")
public class Agenda implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotNull
    @NotEmpty   
    @Size(max = 50)
    @Column(name = "titulo")
    private String titulo;
    
    @NotNull    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaInicio;
    
    @NotNull    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaFin;
    
    @NotNull  
    private int idComercio;    

    @NotNull  
    private int idAliado;
    
    @ManyToOne
    @JoinColumn(name="idAliado", referencedColumnName = "id", insertable = false, updatable = false)
    private Aliado informacionAliado;
    
    @ManyToOne
    @JoinColumn(name="idComercio", referencedColumnName = "comerCod", insertable = false, updatable = false)
    private Comercio informacionComercio;
        
    @Size(max = 150)
    @Column(name = "observaciones")
    private String observacionesAgenda;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
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
        
    public int getIdComercio() {
        return idComercio;
    }

    public void setIdComercio(int idComercio) {
        this.idComercio = idComercio;
    }

    public int getIdAliado() {
        return idAliado;
    }

    public void setIdAliado(int idAliado) {
        this.idAliado = idAliado;
    }

    public Aliado getInformacionAliado() {
        return informacionAliado;
    }

    public void setInformacionAliado(Aliado informacionAliado) {
        this.informacionAliado = informacionAliado;
    }

    public Comercio getInformacionComercio() {
        return informacionComercio;
    }

    public void setInformacionComercio(Comercio informacionComercio) {
        this.informacionComercio = informacionComercio;
    }
    
    public String getObservacionesAgenda() {
        return observacionesAgenda;
    }

    public void setObservacionesAgenda(String observacionesAgenda) {
        this.observacionesAgenda = observacionesAgenda;
    }
    
    
}
