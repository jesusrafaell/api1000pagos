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
 * POJO que representa un Tipo de Pago para un Aliado Comercial
 *
 * Cada Tipo de Pago tiene un id que la identifica
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */

@Entity
@Table (name="TipoPagoAliado")
public class TipoPagoAliado implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CodTipoPago", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Integer codigoTipoPago;
    
    @NotNull
    @NotEmpty    
    @Column(name = "DescTipoPago")
    private String descripcionPago;

    public Integer getCodigoTipoPago() {
        return codigoTipoPago;
    }

    public void setCodigoTipoPago(Integer codigoTipoPago) {
        this.codigoTipoPago = codigoTipoPago;
    }

    public String getDescripcionPago() {
        return descripcionPago;
    }

    public void setDescripcionPago(String descripcionPago) {
        this.descripcionPago = descripcionPago;
    }
        
}
