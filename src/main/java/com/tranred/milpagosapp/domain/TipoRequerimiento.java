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
 * POJO que representa un Tipo de Requerimiento
 *
 * Cada Tipo de Requerimiento tiene un id que la identifica
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */

@Entity
@Table (name="TipoRequerimiento")
public class TipoRequerimiento implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Integer id;
    
    @NotNull
    @NotEmpty
    @Column(name = "descripcion")
    private String descripcionRequerimiento;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcionRequerimiento() {
        return descripcionRequerimiento;
    }

    public void setDescripcionRequerimiento(String descripcionRequerimiento) {
        this.descripcionRequerimiento = descripcionRequerimiento;
    }
          
}
