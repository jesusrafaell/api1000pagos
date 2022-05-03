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
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * POJO que representa el Logs de la aplicacion

 Cada registro tiene un id que lo identifica
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */

@Entity
@Inheritance(strategy =InheritanceType.JOINED)
@Table (name="logs")
public class Logs implements Serializable{
	
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
        
    private int usuarioId;
    
    private int moduloId;
    
    private int opcionId;
    
    private String descripcion;
    
    private String valoresOld;
    
    private String valoresNew;
    
    @Column(name = "fechaHora")
    private Timestamp fecha;
    
    @Column(name = "errorOperacion")
    private int error;
    private String ip;
    @ManyToOne
    @JoinColumn(name="usuarioId", referencedColumnName = "id", insertable = false, updatable = false)
    private Usuario usuario;
    @OneToOne
    @JoinColumn(name="moduloId", referencedColumnName = "id", insertable = false, updatable = false)
    private Modulo modulo;

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getModuloId() {
        return moduloId;
    }

    public void setModuloId(int moduloId) {
        this.moduloId = moduloId;
    }

    public int getOpcionId() {
        return opcionId;
    }

    public void setOpcionId(int opcionId) {
        this.opcionId = opcionId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }   

    public String getValoresOld() {
        return valoresOld;
    }

    public void setValoresOld(String valoresOld) {
        this.valoresOld = valoresOld;
    }

    public String getValoresNew() {
        return valoresNew;
    }

    public void setValoresNew(String valoresNew) {
        this.valoresNew = valoresNew;
    }
    
    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }
    
    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
    
}
