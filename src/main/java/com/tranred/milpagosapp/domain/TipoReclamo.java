package com.tranred.milpagosapp.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * POJO que representa un Tipo de Reclamo
 *
 * Cada Tipo de Reclamo tiene un id que la identifica
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */

@Entity
@Table (name="TipoReclamo")
public class TipoReclamo implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Integer id;
    
    @NotNull
    @NotEmpty
    @Column(name = "descTipoRec")
    private String descripcionReclamo;
    
    @NotNull
    @NotEmpty
    @Column(name = "tiempoTipoRec")
    private int tiempoRespuesta;
    
    @NotNull
    @NotEmpty
    @Column(name = "buzonTipoRec")
    private String buzonTipoReclamo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcionReclamo() {
        return descripcionReclamo;
    }

    public void setDescripcionReclamo(String descripcionReclamo) {
        this.descripcionReclamo = descripcionReclamo;
    }

    public int getTiempoRespuesta() {
        return tiempoRespuesta;
    }

    public void setTiempoRespuesta(int tiempoRespuesta) {
        this.tiempoRespuesta = tiempoRespuesta;
    }

    public String getBuzonTipoReclamo() {
        return buzonTipoReclamo;
    }

    public void setBuzonTipoReclamo(String buzonTipoReclamo) {
        this.buzonTipoReclamo = buzonTipoReclamo;
    }
    
}
