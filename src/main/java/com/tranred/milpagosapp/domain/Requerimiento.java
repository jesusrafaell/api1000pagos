package com.tranred.milpagosapp.domain;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * POJO que representa un Requerimiento del Comercio
 *
 * Cada Requerimiento tiene un id que la identifica
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */

@Entity
@Table (name="Bancos")
public class Requerimiento implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)     
    private Integer id;
    
    @NotNull
    @NotEmpty        
    private int codigoComercio;
    
    @NotNull
    @NotEmpty
    private int tipoRequerimiento;
    
    @NotNull
    @NotEmpty        
    private Date fechaRecepcion;
    
    @NotNull
    @NotEmpty
    private int idUsuario;
    
    @NotNull
    @NotEmpty        
    private String personaContacto;
    
    @NotNull
    @NotEmpty
    @Column(name = "estatus")
    private int estatusRequerimiento;

    @NotNull
    @NotEmpty
    @Column(name = "observaciones")
    private String observacionesReqto;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCodigoComercio() {
        return codigoComercio;
    }

    public void setCodigoComercio(int codigoComercio) {
        this.codigoComercio = codigoComercio;
    }

    public int getTipoRequerimiento() {
        return tipoRequerimiento;
    }

    public void setTipoRequerimiento(int tipoRequerimiento) {
        this.tipoRequerimiento = tipoRequerimiento;
    }

    public Date getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(Date fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getPersonaContacto() {
        return personaContacto;
    }

    public void setPersonaContacto(String personaContacto) {
        this.personaContacto = personaContacto;
    }

    public int getEstatusRequerimiento() {
        return estatusRequerimiento;
    }

    public void setEstatusRequerimiento(int estatusRequerimiento) {
        this.estatusRequerimiento = estatusRequerimiento;
    }

    public String getObservacionesReqto() {
        return observacionesReqto;
    }

    public void setObservacionesReqto(String observacionesReqto) {
        this.observacionesReqto = observacionesReqto;
    }
    
    
}
