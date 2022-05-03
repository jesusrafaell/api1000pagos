package com.tranred.milpagosapp.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * POJO que representa un Estatus dependiendo del modulo
 *
 * Cada Estatus tiene un id que lo identifica
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */

@Entity
@Table (name="Estatus")
public class Estatus implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotNull
    @NotEmpty
    @Size(min = 1, max = 50)
    private String descripcion;
    @NotNull
    @NotEmpty
    @Size(min = 1, max = 3)
    private String modulo;    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }    
    
}
