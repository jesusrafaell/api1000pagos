package com.tranred.milpagosapp.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * POJO que representa el Logs de la aplicacion Web 1000Pagos

 Cada registro tiene un id que lo identifica
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */

@Entity
@Inheritance(strategy =InheritanceType.JOINED)
@Table (name="LogsWeb")
public class LogsWeb implements Serializable{
	
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "logWebCod")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigoLogWeb;
        
    @Column(name = "logWebUserCod")
    private int usuarioWebId;
    
    @Column(name = "logWebOpcion")
    private int opcionWebId;
    
    @Column(name = "logWebDescripcion")
    private String descripcionLogWeb;
    
    @Column(name = "logWebFechaHora")
    private Timestamp fechaLogWeb;
    
    @Column(name = "logWebIp")
    private String ipLogWeb;
            
    @ManyToOne
    @JoinColumn(name="logWebUserCod", referencedColumnName = "userWebCod", insertable = false, updatable = false)
    private UsuarioWeb usuarioWeb;        

    public Integer getCodigoLogWeb() {
        return codigoLogWeb;
    }

    public void setCodigoLogWeb(Integer codigoLogWeb) {
        this.codigoLogWeb = codigoLogWeb;
    }

    public int getUsuarioWebId() {
        return usuarioWebId;
    }

    public void setUsuarioWebId(int usuarioWebId) {
        this.usuarioWebId = usuarioWebId;
    }

    public int getOpcionWebId() {
        return opcionWebId;
    }

    public void setOpcionWebId(int opcionWebId) {
        this.opcionWebId = opcionWebId;
    }

    public String getDescripcionLogWeb() {
        return descripcionLogWeb;
    }

    public void setDescripcionLogWeb(String descripcionLogWeb) {
        this.descripcionLogWeb = descripcionLogWeb;
    }

    public Timestamp getFechaLogWeb() {
        return fechaLogWeb;
    }

    public void setFechaLogWeb(Timestamp fechaLogWeb) {
        this.fechaLogWeb = fechaLogWeb;
    }

    public String getIpLogWeb() {
        return ipLogWeb;
    }

    public void setIpLogWeb(String ipLogWeb) {
        this.ipLogWeb = ipLogWeb;
    }

    public UsuarioWeb getUsuarioWeb() {
        return usuarioWeb;
    }

    public void setUsuarioWeb(UsuarioWeb usuarioWeb) {
        this.usuarioWeb = usuarioWeb;
    }
      
}
