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
 * POJO que representa un Tipo de Persona
 *
 * Cada Tipo de Persona tiene un id que la identifica
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */

@Entity
@Table (name="TipoPersona")
public class TipoPersona implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CodTipoPer", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int codigoTipoPersona;
    
    @NotNull
    @NotEmpty    
    @Column(name = "DescTipoPer")
    private String descripcion;

    public int getCodigoTipoPersona() {
        return codigoTipoPersona;
    }

    public void setCodigoTipoPersona(int codigoTipoPersona) {
        this.codigoTipoPersona = codigoTipoPersona;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }    
    
}
