package com.tranred.milpagosapp.service;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Clase que recibe los datos de los formularios crearUsuario, editarUsuario
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */
public class CrearUsuarioForm {
    
    @NotNull
    @NotEmpty
    @Size(min = 6, max = 12)
    @Pattern.List({
        @Pattern(regexp = "(?=^[a-zA-Z]).+", message = "El primer caracter debe ser Alfabetico"),        
        @Pattern(regexp = "(?=\\S+$).+", message = "No debe contener espacios en Blanco")
    })    
    private String login;
        
    @Pattern.List({        
        @Pattern(regexp = "(?=\\S+$).+", message = "No debe contener espacios en Blanco"),        
        @Pattern(regexp = "(?=.*[!”#$&()?¡]).+", message ="Debe contener por lo menos un caracter especial (!” #$&()?¡)")
    })
    @Size(min = 8, max = 12)
    @NotNull
    @NotEmpty
    private String contrasena;
    
    @NotNull
    @NotEmpty
    private String perfilId;        
    
    @NotNull
    @NotEmpty    
    @Size(min = 1, max = 10)
    private String identificacion;
    
    @NotNull
    @NotEmpty    
    private String tipoIdentificacion;        
    
    @NotNull
    @NotEmpty    
    @Size(min = 1, max = 100)
    private String nombre;
    
    @NotNull
    @NotEmpty
    @Email
    private String email;
    
    @NotNull
    @NotEmpty
    private String estatus;
    private String envioCorreo;
    private String reset;
    private String cambio;
    private String id;

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

    public String getPerfilId() {
        return perfilId;
    }

    public void setPerfilId(String perfilId) {
        this.perfilId = perfilId;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getEnvioCorreo() {
        return envioCorreo;
    }

    public void setEnvioCorreo(String envioCorreo) {
        this.envioCorreo = envioCorreo;
    }
    
    public String getReset() {
        return reset;
    }

    public void setReset(String reset) {
        this.reset = reset;
    }

    public String getCambio() {
        return cambio;
    }

    public void setCambio(String cambio) {
        this.cambio = cambio;
    }    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
}
