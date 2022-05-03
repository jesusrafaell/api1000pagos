package com.tranred.milpagosapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * POJO que representa un tipo de pos
 *
 * Cada tipo tiene un id que lo identifica
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 * 
 *  @author jperez@emsys-solutions.net
 * Se agrega los campos Costo y Moneda
 */

@Entity
@Inheritance(strategy =InheritanceType.JOINED)
@Table(name="TipoPos")
public class TipoPos implements Serializable{
	
    private static final long serialVersionUID = 1L;
  
    @Id
    @Column(name = "id", unique = true)
    @NotNull
    private Integer id;
    
    
    @NotNull
    @NotEmpty 
    @Column(name = "descTipoPos")
    private String descripcion;	
    
    
    @Column(name = "Costo")
    private BigDecimal costo;    
    
    
    
    @Column(name = "MonedaId")
    private int moneda;

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
    
    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo= costo;
    }

    public int getMoneda() {
        return moneda;
    }

    public void setMoneda(int moneda) {
        this.moneda = moneda;
    }
             
}
