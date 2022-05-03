/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * POJO que representa un Tipo de Plan
 *
 * Cada Tipo de Plan tiene un id que la identifica
 *
 * @author jperez@semsys-solutions.net
 * @version 0.1
 */

@Entity
@Table (name="TipoPlan")
public class TipoPlan implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "codTipoPlan", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int codigoTipoPlan;
    
    @NotNull
    @NotEmpty    
    @Column(name = "descTipoPlan")
    private String descripcion;
    
    @NotNull
    @Column(name = "estatusId")
    private int estatus;

    public int getCodigoTipoPlan() {
        return codigoTipoPlan;
    }

    public void setCodigoTipoPlan(int codigoTipoPlan) {
        this.codigoTipoPlan = codigoTipoPlan;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }    
    
   public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }
    
    
}
