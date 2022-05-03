package com.tranred.milpagosapp.domain;

import java.io.Serializable;
import java.sql.Date;
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
 * POJO que representa un Usuario del sistema
 *
 * Cada Usuario tiene un id que lo identifica
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */

@Entity
@Inheritance(strategy =InheritanceType.JOINED)
@Table(name="Usuarios")
public class Usuario implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
           
    private String nombre;
        
    private String tipoIdentificacion;
    
    private String identificacion;
    
    private String email;
    
    private String perfilId;
    
    @ManyToOne
    @JoinColumn(name="perfilId", referencedColumnName = "id", insertable = false, updatable = false)
    private Perfil perfil;        
    
    private String login;
        
    private String contrasena;
    
    private Timestamp fechaCreacion;
    
    private Date fechaExpira;
    
    private Date ultimoAcceso;
        
    private String estatus;
    
    @ManyToOne
    @JoinColumn(name="estatus", referencedColumnName = "id", insertable = false, updatable = false)
    private Estatus estatusId;
            
    private String cambio;
    
    private int intentosFallidos;
    
    private int inicioSesion;        

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }
    
    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }
    
    public String getUsuarioIdentificacion() {
        return tipoIdentificacion + "-" + identificacion;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
        
    public String getPerfilId() {
        return perfilId;
    }

    public void setPerfilId(String perfilId) {
        this.perfilId = perfilId;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Date getFechaExpira() {
        return fechaExpira;
    }

    public void setFechaExpira(Date fechaExpira) {
        this.fechaExpira = fechaExpira;
    }
    
    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    public Date getUltimoAcceso() {
        return ultimoAcceso;
    }    

    public void setUltimoAcceso(Date ultimoAcceso) {
        this.ultimoAcceso = ultimoAcceso;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public Estatus getEstatusId() {
        return estatusId;
    }

    public void setEstatusId(Estatus estatusId) {
        this.estatusId = estatusId;
    }
    
    public String getCambio() {
        return cambio;
    }

    public void setCambio(String cambio) {
        this.cambio = cambio;
    }

    public int getIntentosFallidos() {
        return intentosFallidos;
    }

    public void setIntentosFallidos(int intentosFallidos) {
        this.intentosFallidos = intentosFallidos;
    }

    public int getInicioSesion() {
        return inicioSesion;
    }

    public void setInicioSesion(int inicioSesion) {
        this.inicioSesion = inicioSesion;
    }
    
    
}