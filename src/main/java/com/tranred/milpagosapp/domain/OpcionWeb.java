package com.tranred.milpagosapp.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * POJO que representa una Opcion del Sistema Web 1000Pagos
 *
 * Cada Opcion tiene un id que la identifica
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */

@Entity
@Table (name="OpcionesWeb")
public class OpcionWeb implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "opWebCod")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigoOpcionWeb;
    
    @Column(name = "opWebNombre")
    private String nombreOpcionWeb;
    
    @Column(name = "opWebDescripcion")
    private String descripcionOpcionWeb;    

    public Integer getCodigoOpcionWeb() {
        return codigoOpcionWeb;
    }

    public void setCodigoOpcionWeb(Integer codigoOpcionWeb) {
        this.codigoOpcionWeb = codigoOpcionWeb;
    }
    
    public String getCodigoOpcionWebAsString() {
	return new Long(codigoOpcionWeb).toString();
    }
    
    public String getNombreOpcionWeb() {
        return nombreOpcionWeb;
    }

    public void setNombreOpcionWeb(String nombreOpcionWeb) {
        this.nombreOpcionWeb = nombreOpcionWeb;
    }

    public String getDescripcionOpcionWeb() {
        return descripcionOpcionWeb;
    }

    public void setDescripcionOpcionWeb(String descripcionOpcionWeb) {
        this.descripcionOpcionWeb = descripcionOpcionWeb;
    }    
     
}
