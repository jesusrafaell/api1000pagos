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
 * POJO que representa un Tipo de Contrato
 *
 * Cada Tipo de Contrato tiene un id que la identifica
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */

@Entity
@Table (name="TipoContrato")
public class TipoContrato implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CodTipoContrato", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private String codigoContrato;
    
    @NotNull
    @NotEmpty    
    @Column(name = "DescTipoContrato")
    private String descripcion;

    public String getCodigoContrato() {
        return codigoContrato;
    }

    public void setCodigoContrato(String codigoContrato) {
        this.codigoContrato = codigoContrato;
    }
    

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
}
