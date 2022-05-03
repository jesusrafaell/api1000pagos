package com.tranred.milpagosapp.domain;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * POJO que representa un Contacto del Comercio
 *
 * Cada Contacto tiene un id que la identifica
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */

@Entity
@Table (name="Contactos")
public class Contacto implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "contCode", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private String codigoContacto;
        
    @Column(name = "contCodComer")
    private int codigoComercio;
   
    @Column(name = "contCodUsuario")
    private String codigoUsuario;
        
    @Column(name = "contNombres")
    private String contactoNombres;
        
    @Column(name = "contApellidos")
    private String contactoApellidos;
        
    @Column(name = "contTelefLoc")
    private String telefonoLocal;
        
    @Column(name = "contTelefMov")
    private String telefonoMovil;
       
    @Column(name = "contMail")
    private String email;
        
    @Column(name = "contFreg")
    private Date freg;

    public String getCodigoContacto() {
        return codigoContacto;
    }

    public void setCodigoContacto(String codigoContacto) {
        this.codigoContacto = codigoContacto;
    }

    public int getCodigoComercio() {
        return codigoComercio;
    }

    public void setCodigoComercio(int codigoComercio) {
        this.codigoComercio = codigoComercio;
    }

    public String getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(String codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public String getContactoNombres() {
        return contactoNombres;
    }

    public void setContactoNombres(String contactoNombres) {
        this.contactoNombres = contactoNombres;
    }

    public String getContactoApellidos() {
        return contactoApellidos;
    }

    public void setContactoApellidos(String contactoApellidos) {
        this.contactoApellidos = contactoApellidos;
    }   

    public String getTelefonoLocal() {
        return telefonoLocal;
    }

    public void setTelefonoLocal(String telefonoLocal) {
        this.telefonoLocal = telefonoLocal;
    }

    public String getTelefonoMovil() {
        return telefonoMovil;
    }

    public void setTelefonoMovil(String telefonoMovil) {
        this.telefonoMovil = telefonoMovil;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getFreg() {
        return freg;
    }

    public void setFreg(Date freg) {
        this.freg = freg;
    }
    
}
