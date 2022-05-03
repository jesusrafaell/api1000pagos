package com.tranred.milpagosapp.service;

import com.tranred.milpagosapp.domain.Afiliado;
import java.sql.Date;
import java.util.Set;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.Min;


/**
 * Clase que recibe los datos del formulario crearComercio y resultadoConsultaComercio
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */
public class ComercioForm {
    
    @NotNull
    @NotEmpty   
    @Size(max = 150)  
    private String descripcionComercio;
            
    private int tipoPersonaComercio;    
        
    @NotEmpty
    private String pagaIVA;
        
    private String usuario;
       
    private int codigoPadre;
    
    @NotNull
    @NotEmpty    
    private String tipoIdentificacion;
    
    @NotNull
    @NotEmpty
    @Size(max = 10)
    private String rifComercio;
       
    private Date comercioFreq;   
    
    @NotNull
    @NotEmpty     
    private String codigoTipoContrato;
    @NotNull
    @NotEmpty
    private String fechaInicioContrato;
    @NotNull
    @NotEmpty
    private String fechaFinContrato;
        
    @NotEmpty    
    private String diasOperacion;
    
    private String excluirArchivoPago;
    
    @NotNull
    @NotEmpty    
    private String codigoCategoria;
    
    @NotNull
    @NotEmpty
    private String codigoBancoCuentaAbono;
    
    @NotNull
    @NotEmpty
    @Min(20)
    private String numeroCuentaAbono;
    
    private String codigoBancoCuentaAbono2;
        
    private String numeroCuentaAbono2;
    
    private String codigoBancoCuentaAbono3;

    private String numeroCuentaAbono3;
           
    private String entregoGarantiaFianza;
    
    private String modalidadPos;
    
    private String tipoPos;
    
    private String modalidadGarantia;
    
    private String montoGarantiaFianza;

    private String fechaGarantiaFianza;
           
    private String recaudosComercio;
    
    @NotNull
    @NotEmpty     
    private String direccionComercio;
        
    @NotNull
    @NotEmpty     
    private String direccionHabitacion;
    
    @NotNull
    @NotEmpty     
    private String direccionPos;
    
    private String observacionesComercio;
        
    private String codigoAliado;
    
    private String aliadoComercial;
    
    private String estatusComercio;
    
    private int codigoComercio;
    
    private int codigoContacto;
       
    private String codigoUsuario;
    
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
        
    private Date freg;
         
    private String comisionComercio;
    
    @NotNull
    @NotEmpty
    private String[] afiliados;

    private String puntoAdicional;
    
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

    public String getPagaIVA() {
        return pagaIVA;
    }

    public void setPagaIVA(String pagaIVA) {
        this.pagaIVA = pagaIVA;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getCodigoPadre() {
        return codigoPadre;
    }

    public void setCodigoPadre(int codigoPadre) {
        this.codigoPadre = codigoPadre;
    }

    public String getRifComercio() {
        return rifComercio;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }
    
    public void setRifComercio(String rifComercio) {
        this.rifComercio = rifComercio;
    }

    public Date getComercioFreq() {
        return comercioFreq;
    }

    public void setComercioFreq(Date comercioFreq) {
        this.comercioFreq = comercioFreq;
    }

    public String getCodigoTipoContrato() {
        return codigoTipoContrato;
    }

    public void setCodigoTipoContrato(String codigoTipoContrato) {
        this.codigoTipoContrato = codigoTipoContrato;
    }

    public String getFechaInicioContrato() {
        return fechaInicioContrato;
    }

    public void setFechaInicioContrato(String fechaInicioContrato) {
        this.fechaInicioContrato = fechaInicioContrato;
    }

    public String getFechaFinContrato() {
        return fechaFinContrato;
    }

    public void setFechaFinContrato(String fechaFinContrato) {
        this.fechaFinContrato = fechaFinContrato;
    }

    public String getExcluirArchivoPago() {
        return excluirArchivoPago;
    }

    public void setExcluirArchivoPago(String excluirArchivoPago) {
        this.excluirArchivoPago = excluirArchivoPago;
    }
    
    public String getCodigoCategoria() {
        return codigoCategoria;
    }

    public void setCodigoCategoria(String codigoCategoria) {
        this.codigoCategoria = codigoCategoria;
    }

    public String getCodigoBancoCuentaAbono() {
        return codigoBancoCuentaAbono;
    }

    public void setCodigoBancoCuentaAbono(String codigoBancoCuentaAbono) {
        this.codigoBancoCuentaAbono = codigoBancoCuentaAbono;
    }
    
    public String getNumeroCuentaAbono() {
        return numeroCuentaAbono;
    }

    public void setNumeroCuentaAbono(String numeroCuentaAbono) {
        this.numeroCuentaAbono = numeroCuentaAbono;
    }

    public String getCodigoBancoCuentaAbono2() {
        return codigoBancoCuentaAbono2;
    }

    public void setCodigoBancoCuentaAbono2(String codigoBancoCuentaAbono2) {
        this.codigoBancoCuentaAbono2 = codigoBancoCuentaAbono2;
    }

    public String getNumeroCuentaAbono2() {
        return numeroCuentaAbono2;
    }

    public void setNumeroCuentaAbono2(String numeroCuentaAbono2) {
        this.numeroCuentaAbono2 = numeroCuentaAbono2;
    }

    public String getCodigoBancoCuentaAbono3() {
        return codigoBancoCuentaAbono3;
    }

    public void setCodigoBancoCuentaAbono3(String codigoBancoCuentaAbono3) {
        this.codigoBancoCuentaAbono3 = codigoBancoCuentaAbono3;
    }

    public String getNumeroCuentaAbono3() {
        return numeroCuentaAbono3;
    }

    public void setNumeroCuentaAbono3(String numeroCuentaAbono3) {
        this.numeroCuentaAbono3 = numeroCuentaAbono3;
    }

    public String getEntregoGarantiaFianza() {
        return entregoGarantiaFianza;
    }

    public void setEntregoGarantiaFianza(String entregoGarantiaFianza) {
        this.entregoGarantiaFianza = entregoGarantiaFianza;
    }

    public String getModalidadPos() {
        return modalidadPos;
    }

    public void setModalidadPos(String modalidadPos) {
        this.modalidadPos = modalidadPos;
    }

    public String getTipoPos() {
        return tipoPos;
    }
    
    public void setTipoPos(String tipoPos) {
        this.tipoPos = tipoPos;
    }
    
    public String getModalidadGarantia() {
        return modalidadGarantia;
    }

    public void setModalidadGarantia(String modalidadGarantia) {
        this.modalidadGarantia = modalidadGarantia;
    }
    
    public String getMontoGarantiaFianza() {
        return montoGarantiaFianza;
    }

    public void setMontoGarantiaFianza(String montoGarantiaFianza) {
        this.montoGarantiaFianza = montoGarantiaFianza;
    }

    public String getRecaudosComercio() {
        return recaudosComercio;
    }

    public void setRecaudosComercio(String recaudosComercio) {
        this.recaudosComercio = recaudosComercio;
    }

    public String getDireccionComercio() {
        return direccionComercio;
    }

    public void setDireccionComercio(String direccionComercio) {
        this.direccionComercio = direccionComercio;
    }

    public String getDireccionHabitacion() {
        return direccionHabitacion;
    }

    public void setDireccionHabitacion(String direccionHabitacion) {
        this.direccionHabitacion = direccionHabitacion;
    }

    public String getDireccionPos() {
        return direccionPos;
    }

    public void setDireccionPos(String direccionPos) {
        this.direccionPos = direccionPos;
    }

    public String getObservacionesComercio() {
        return observacionesComercio;
    }

    public void setObservacionesComercio(String observacionesComercio) {
        this.observacionesComercio = observacionesComercio;
    }

    public String getCodigoAliado() {
        return codigoAliado;
    }

    public void setCodigoAliado(String codigoAliado) {
        this.codigoAliado = codigoAliado;
    }

    public String getAliadoComercial() {
        return aliadoComercial;
    }

    public void setAliadoComercial(String aliadoComercial) {
        this.aliadoComercial = aliadoComercial;
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

    public int getCodigoContacto() {
        return codigoContacto;
    }

    public void setCodigoContacto(int codigoContacto) {
        this.codigoContacto = codigoContacto;
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

    public String getComisionComercio() {
        return comisionComercio;
    }

    public void setComisionComercio(String comisionComercio) {
        this.comisionComercio = comisionComercio;
    }

    public String[] getAfiliados() {
        return afiliados;
    }

    public void setAfiliados(String[] afiliados) {
        this.afiliados = afiliados;
    }    

    public String getDiasOperacion() {
        return diasOperacion;
    }

    public void setDiasOperacion(String diasOperacion) {
        this.diasOperacion = diasOperacion;
    }

    public String getPuntoAdicional() {
        return puntoAdicional;
    }

    public void setPuntoAdicional(String puntoAdicional) {
        this.puntoAdicional = puntoAdicional;
    }

    public String getFechaGarantiaFianza() {
        return fechaGarantiaFianza;
    }

    public void setFechaGarantiaFianza(String fechaGarantiaFianza) {
        this.fechaGarantiaFianza = fechaGarantiaFianza;
    }
        
}
