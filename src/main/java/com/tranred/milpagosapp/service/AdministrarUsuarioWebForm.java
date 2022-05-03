package com.tranred.milpagosapp.service;

import java.sql.Date;
import java.sql.Timestamp;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Clase que recibe los datos del formulario administrarUsuarioWeb
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */
public class AdministrarUsuarioWebForm {
    
    @NotNull    
    private int codigoUsuarioWeb;
    
    private int codigoComercio;
    
    private String login;
            
    @NotNull
    @NotEmpty
    private String contrasena;       
    
    @NotNull
    @NotEmpty    
    @Size(min = 1, max = 10)
    private String identificacion;
    
    @NotNull
    @NotEmpty    
    private String tipoIdentificacion;              
    
    @NotNull
    @NotEmpty
    @Email
    private String email;
    
    @NotNull
    @NotEmpty
    private String estatus;
   
    private String resetContrasena;
    private String resetPreguntas;
    private String cambio;        
    private Timestamp fechaCreacion;        
    private Date fechaExpira;        
    private Timestamp ultimoAcceso;                                    
    private int intentosFallidos;                               
    
    public int getCodigoUsuarioWeb() {
        return codigoUsuarioWeb;
    }

    public void setCodigoUsuarioWeb(int codigoUsuarioWeb) {
        this.codigoUsuarioWeb = codigoUsuarioWeb;
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

    public void setLogin(String login) {
        this.login = login;
    }       
    
    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
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

    public String getResetContrasena() {
        return resetContrasena;
    }

    public void setResetContrasena(String resetContrasena) {
        this.resetContrasena = resetContrasena;
    }

    public String getResetPreguntas() {
        return resetPreguntas;
    }

    public void setResetPreguntas(String resetPreguntas) {
        this.resetPreguntas = resetPreguntas;
    }          

    public String getCambio() {
        return cambio;
    }

    public void setCambio(String cambio) {
        this.cambio = cambio;
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
    
}
