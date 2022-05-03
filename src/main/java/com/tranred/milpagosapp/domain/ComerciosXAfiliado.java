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
 * POJO que representa los Comercios x Afiliado
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */

@Entity
@Table (name="ComerciosXafiliado")
public class ComerciosXAfiliado implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "cxaId", unique = true)    
    private int id;
    
    @NotNull
    @NotEmpty
    @Column(name = "cxaCodAfi")    
    private String codigoAfiliado;
    
    @NotNull
    @NotEmpty    
    @Column(name = "cxaCodComer")
    private String codigoComercio;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigoAfiliado() {
        return codigoAfiliado;
    }

    public void setCodigoAfiliado(String codigoAfiliado) {
        this.codigoAfiliado = codigoAfiliado;
    }

    public String getCodigoComercio() {
        return codigoComercio;
    }

    public void setCodigoComercio(String codigoComercio) {
        this.codigoComercio = codigoComercio;
    }
    
}
