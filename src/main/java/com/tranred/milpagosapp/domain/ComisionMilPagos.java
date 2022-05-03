package com.tranred.milpagosapp.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * POJO que representa una Comision para MilPagos
 *
 * Cada Banco tiene un id que la identifica
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */

@Entity
@Table (name="ComisionesMilPagos")
public class ComisionMilPagos implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "cmCodComercio")
    private int codigoComercio;
        
    @Column(name = "cmPorcentaje")
    private String porcentajeComision;

    public int getCodigoComercio() {
        return codigoComercio;
    }

    public void setCodigoComercio(int codigoComercio) {
        this.codigoComercio = codigoComercio;
    }

    public String getPorcentajeComision() {
        return porcentajeComision;
    }

    public void setPorcentajeComision(String porcentajeComision) {
        this.porcentajeComision = porcentajeComision;
    }
    
}
