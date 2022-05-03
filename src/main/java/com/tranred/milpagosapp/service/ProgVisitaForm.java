package com.tranred.milpagosapp.service;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Email;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * Clase que recibe los datos del formulario programarVisita
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */
public class ProgVisitaForm {
    
    private int idAgenda;
            
    @NotNull
    @NotEmpty   
    @Size(max = 150)  
    private String descripcionComercio;
            
    private int tipoPersonaComercio; 
                       
    @NotNull
    @NotEmpty
    private String tipoIdentificacion;
    
    @NotNull
    @NotEmpty
    @Size(max = 10)
    private String rifComercio;
    
    @NotNull
    @NotEmpty
    private String codigoCategoria;        
    
    @NotNull
    @NotEmpty     
    private String direccionComercio;
        
    private String observacionesComercio;
        
    private String estatusComercio;
    
    private int codigoComercio;
       
    private int codigoUsuario;
    
    private String existeAliado;
    
    private int codigoContacto;
    
    private String messageError;
    
    @NotNull
    @NotEmpty
    private String contactoNombres;
    
    @NotNull
    @NotEmpty
    private String contactoApellidos;
    
    @Size(max = 11)
    private String telefonoLocal;
    
    @NotNull
    @NotEmpty
    @Size(max = 11)
    private String telefonoMovil;
    
    @NotNull
    @NotEmpty
    @Email
    private String email;
        
    private String aliadoComercial;
    
    @NotNull
    @NotEmpty
    private String fechaVisitaInicio;
    
    @NotNull
    @NotEmpty
    private String fechaVisitaFin;
    
    @NotNull
    @NotEmpty
    private String tituloVisita; 
    
    private CommonsMultipartFile imagenComercio;
    
    public int getIdAgenda() {
        return idAgenda;
    }

    public void setIdAgenda(int idAgenda) {
        this.idAgenda = idAgenda;
    }
        
    public String getDescripcionComercio() {
        return descripcionComercio;
    }

    public void setDescripcionComercio(String descripcionComercio) {
        this.descripcionComercio = descripcionComercio;
    }

    public int getTipoPersonaComercio() {
        return tipoPersonaComercio;
    }

    public void setTipoPersonaComercio(int tipoPersonaComercio) {
        this.tipoPersonaComercio = tipoPersonaComercio;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getRifComercio() {
        return rifComercio;
    }

    public void setRifComercio(String rifComercio) {
        this.rifComercio = rifComercio;
    }

    public String getCodigoCategoria() {
        return codigoCategoria;
    }

    public void setCodigoCategoria(String codigoCategoria) {
        this.codigoCategoria = codigoCategoria;
    }

    public String getDireccionComercio() {
        return direccionComercio;
    }

    public void setDireccionComercio(String direccionComercio) {
        this.direccionComercio = direccionComercio;
    }

    public String getObservacionesComercio() {
        return observacionesComercio;
    }

    public void setObservacionesComercio(String observacionesComercio) {
        this.observacionesComercio = observacionesComercio;
    }

    public String getEstatusComercio() {
        return estatusComercio;
    }

    public void setEstatusComercio(String estatusComercio) {
        this.estatusComercio = estatusComercio;
    }

    public int getCodigoComercio() {
        return codigoComercio;
    }

    public void setCodigoComercio(int codigoComercio) {
        this.codigoComercio = codigoComercio;
    }

    public int getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(int codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public int getCodigoContacto() {
        return codigoContacto;
    }

    public void setCodigoContacto(int codigoContacto) {
        this.codigoContacto = codigoContacto;
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

    public String getAliadoComercial() {
        return aliadoComercial;
    }

    public void setAliadoComercial(String aliadoComercial) {
        this.aliadoComercial = aliadoComercial;
    }

    public String getFechaVisitaInicio() {
        return fechaVisitaInicio;
    }

    public void setFechaVisitaInicio(String fechaVisitaInicio) {
        this.fechaVisitaInicio = fechaVisitaInicio;
    }

    public String getFechaVisitaFin() {
        return fechaVisitaFin;
    }

    public void setFechaVisitaFin(String fechaVisitaFin) {
        this.fechaVisitaFin = fechaVisitaFin;
    }   

    public String getExisteAliado() {
        return existeAliado;
    }

    public void setExisteAliado(String existeAliado) {
        this.existeAliado = existeAliado;
    }

    public String getTituloVisita() {
        return tituloVisita;
    }

    public void setTituloVisita(String tituloVisita) {
        this.tituloVisita = tituloVisita;
    }

    public CommonsMultipartFile getImagenComercio() {
        return imagenComercio;
    }

    public void setImagenComercio(CommonsMultipartFile imagenComercio) {
        this.imagenComercio = imagenComercio;
    }

    public String getMessageError() {
        return messageError;
    }

    public void setMessageError(String messageError) {
        this.messageError = messageError;
    }
        
}
