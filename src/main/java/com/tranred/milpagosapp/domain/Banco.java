package com.tranred.milpagosapp.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * POJO que representa un Banco del Sistema
 *
 * Cada Banco tiene un id que la identifica
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */

@Entity
@Table (name="Bancos")
public class Banco implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "banCodBan", unique = true)    
    @NotNull
    @NotEmpty   
    @Size(min = 1, max = 4)
    private String codigo;
    
    @NotNull
    @NotEmpty    
    @Size(min = 1, max = 100)
    @Column(name = "banDescBan")
    private String descripcion;
    
    @NotNull
    @NotEmpty    
    @Size(min = 1, max = 12)
    @Column(name = "banSwift")
    private String codigoSwift;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigoSwift() {
        return codigoSwift;
    }

    public void setCodigoSwift(String codigoSwift) {
        this.codigoSwift = codigoSwift;
    }
    
}
