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
 * POJO que representa un tipo de estadistica
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */

@Entity
@Table (name="TipoEstadistica")
public class Estadistica implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Integer id;
    
    @NotNull
    @NotEmpty    
    @Column(name = "descripcion")
    private String tipoEstadistica;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipoEstadistica() {
        return tipoEstadistica;
    }

    public void setTipoEstadistica(String tipoEstadistica) {
        this.tipoEstadistica = tipoEstadistica;
    }
        
}
