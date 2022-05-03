package com.tranred.milpagosapp.domain;

import com.tranred.milpagosapp.util.Utilidades;
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
 * POJO que representa un Usuario del Sitio Web MilPagos
 *
 * Cada Usuario tiene un id que lo identifica
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */

@Entity
@Inheritance(strategy =InheritanceType.JOINED)
@Table(name="UsuariosWeb")
public class UsuarioWeb implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id    
    @Column(name = "userWebCod")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigoUsuarioWeb;               
        
    @Column(name = "userWebTipoIdentificacion")
    private String tipoIdentificacion;
        
    @Column(name = "userWebIdentificacion")
    private String identificacion;
    
    @Column(name = "userWebCodigoComercio")
    private int codigoComercio;
            
    @Column(name = "userWebLogin")
    private String login;
          
    @Column(name = "userWebContrasena")
    private String contrasena;
       
    @Column(name = "userWebEmail")
    private String email;                                  
       
    @Column(name = "userWebEstatus")
    private String estatus;
    
    @ManyToOne
    @JoinColumn(name="userWebEstatus", referencedColumnName = "id", insertable = false, updatable = false)
    private Estatus estatusId;    
    
    @Column(name = "userWebFechaCreacion")
    private Timestamp fechaCreacion;
    
    @Column(name = "userWebFechaExpira")
    private Date fechaExpira;
    
    @Column(name = "userWebUltimoAcceso")
    private Timestamp ultimoAcceso;                            
    
    @Column(name = "userWebIntentosFallidos")
    private int intentosFallidos;       
    
    @Column(name = "userWebCambio")
    private String cambio;

    public Integer getCodigoUsuarioWeb() {
        return codigoUsuarioWeb;
    }

    public void setCodigoUsuarioWeb(Integer codigoUsuarioWeb) {
        this.codigoUsuarioWeb = codigoUsuarioWeb;
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
    
    public String getUsuarioWebIdentificacion() {
        return tipoIdentificacion + "-" + identificacion;
    }

    public int getCodigoComercio() {
        return codigoComercio;
    }

    public void setCodigoComercio(int codigoComercio) {
        this.codigoComercio = codigoComercio;
    }
    
    public String getLogin() {
        return login;
    }
    
    public String getLoginDesencriptado() throws Exception {
        return Utilidades.Desencriptar(login);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaExpira() {
        return fechaExpira;
    }

    public void setFechaExpira(Date fechaExpira) {
        this.fechaExpira = fechaExpira;
    }

    public Timestamp getUltimoAcceso() {
        return ultimoAcceso;
    }

    public void setUltimoAcceso(Timestamp ultimoAcceso) {
        this.ultimoAcceso = ultimoAcceso;
    }

    public int getIntentosFallidos() {
        return intentosFallidos;
    }

    public void setIntentosFallidos(int intentosFallidos) {
        this.intentosFallidos = intentosFallidos;
    }
   
    public String getCambio() {
        return cambio;
    }

    public void setCambio(String cambio) {
        this.cambio = cambio;
    }
   
    
}