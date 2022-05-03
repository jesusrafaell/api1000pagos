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
 * POJO que representa un Tipo de Moneda
 *
 * Cada Tipo de Moneda  tiene un id que la identifica
 *
 * @author jperez@semsys-solutions.net
 * @version 0.1
 */

@Entity
@Table (name="Moneda")
public class Moneda implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int idmoneda;
    
    @NotNull
    @NotEmpty    
    @Column(name = "descripcion")
    private String descripcion;

    public int getId() {
        return idmoneda;
    }

    public void setId(int id) {
        this.idmoneda = idmoneda;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }    
    
}
