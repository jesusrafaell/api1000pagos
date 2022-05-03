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
 * POJO que representa un mensaje de información al cliente
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */

@Entity
@Table (name="Mensajes")
public class Mensaje implements Serializable{
    
    private static final long serialVersionUID = 1L;        
    
    @Id
    @Column(name = "menCod")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigoMensaje;
    
    @NotNull
    @NotEmpty
    @Column(name = "menClave")
    private String mensajeClave;
    
    @NotNull
    @NotEmpty
    @Column(name = "menTexto") 
    private String mensajeTexto;        

    public Integer getCodigoMensaje() {
        return codigoMensaje;
    }

    public void setCodigoMensaje(Integer codigoMensaje) {
        this.codigoMensaje = codigoMensaje;
    }

    public String getMensajeClave() {
        return mensajeClave;
    }

    public void setMensajeClave(String mensajeClave) {
        this.mensajeClave = mensajeClave;
    }

    public String getMensajeTexto() {
        return mensajeTexto;
    }

    public void setMensajeTexto(String mensajeTexto) {
        this.mensajeTexto = mensajeTexto;
    }
        
}
