package com.tranred.milpagosapp.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * POJO que representa un Prefijo Telefonico
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */

@Entity
@Table (name="CodigosArea")
public class CodigoArea implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "codigo")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String codigo;
        
    private int tipoCodigo;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getTipoCodigo() {
        return tipoCodigo;
    }

    public void setTipoCodigo(int tipoCodigo) {
        this.tipoCodigo = tipoCodigo;
    }
    
}
