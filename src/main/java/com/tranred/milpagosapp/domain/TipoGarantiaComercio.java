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
 * POJO que representa un Tipo de Garantia para un Comercio
 *
 * Cada Tipo de Garantia tiene un id que la identifica
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */

@Entity
@Table (name="TipoGarantiaComercio")
public class TipoGarantiaComercio implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CodTipoGarantia", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Integer codigoTipoGarantia;
    
    @NotNull
    @NotEmpty    
    @Column(name = "DescTipoGarantia")
    private String descripcionTipoGarantia;

    public Integer getCodigoTipoGarantia() {
        return codigoTipoGarantia;
    }

    public void setCodigoTipoGarantia(Integer codigoTipoGarantia) {
        this.codigoTipoGarantia = codigoTipoGarantia;
    }

    public String getDescripcionTipoGarantia() {
        return descripcionTipoGarantia;
    }

    public void setDescripcionTipoGarantia(String descripcionTipoGarantia) {
        this.descripcionTipoGarantia = descripcionTipoGarantia;
    }
    
}
