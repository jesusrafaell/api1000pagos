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
 * POJO que representa la Categoria de un Comercio 
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */

@Entity
@Table (name="CategoriasComercios")
public class CategoriaComercio implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ccCodCateg", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Integer codigoCategoria;
    
    @NotNull
    @NotEmpty    
    @Column(name = "ccDescCateg")
    private String descripcionCategoria;

    public Integer getCodigoCategoria() {
        return codigoCategoria;
    }
        
    public String getCodigoCategoriaAsString() {
	return new Long(codigoCategoria).toString();
    }
    
    public void setCodigoCategoria(Integer codigoCategoria) {
        this.codigoCategoria = codigoCategoria;
    }
    
    public String getDescripcionCategoria() {
        return descripcionCategoria;
    }

    public void setDescripcionCategoria(String descripcionCategoria) {
        this.descripcionCategoria = descripcionCategoria;
    }
    
}
