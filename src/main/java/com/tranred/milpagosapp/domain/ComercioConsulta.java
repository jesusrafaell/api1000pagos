package com.tranred.milpagosapp.domain;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;

/**
 * POJO que representa los Datos de un Comercio incluyendo Afiliados y Contactos
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */

@NamedNativeQueries({
    @NamedNativeQuery(
            name = "consultaComercio",
            query = "EXEC SP_consultaComercio :tipoConsulta,:rif,:idComercio,:terminal",
            resultClass = ComercioConsulta.class
    )
 })
@Entity
public class ComercioConsulta implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "comerCod")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigoComercio;
    
    @Column(name = "comerDesc")
    private String descripcionComercio;
        
    @Column(name = "comerTipoPer")
    private int tipoPersonaComercio;    
    
    @Column(name = "comerPagaIva")
    private String pagaIVA;
    
    @Column(name = "comerCodUsuario")
    private String usuario;
    
    @Column(name = "comerCodPadre")
    private String codigoPadre;
       
    @Column(name = "comerRif")
    private String rifComercio;
    
    @Column(name = "cxaCodAfi")
    private String codigoAfiliado;    
    
    @Column(name = "contCode")
    private String codigoContacto;
    
    @Column(name = "contNombres")
    private String nombreContacto;
    
    @Column(name = "contApellidos")
    private String apellidoContacto;    
    
    @Column(name = "contTelefLoc")
    private String telefonoHabitacion;    
    
    @Column(name = "contTelefMov")
    private String telefonoCelular;
            
    @Column(name = "contMail")
    private String email;
      
    @Column(name = "afiDesc")
    private String descripcionAfiliado; 
    
    @Column(name = "afiCodTipoPer")
    private String tipoPersonaAfiliado;
    
    @Column(name = "comerCodCategoria")
    private String categoriaComercio;
    
    @Column(name = "comerCodTipoCont")
    private String tipoContrato;
    
    @Column(name = "comerInicioContrato")
    private String fechaInicioContrato;
    
    @Column(name = "comerFinContrato")
    private String fechaFinContrato;
    
    @Column(name = "comerExcluirPago")
    private String excluirArchivoPago;
    
    @Column(name = "comerGarantiaFianza")
    private String entregoGarantia;
    
    @Column(name = "comerCodigoBanco")
    private String codigoBancoCuentaAbono;

    @Column(name = "comerCuentaBanco")
    private String cuentaAbono;
    
    @Column(name = "comerPuntoAdicional")
    private String puntoAdicional;
    
    @Column(name = "comerCodigoBanco2")
    private String codigoBancoCuentaAbono2;
    
    @Column(name = "comerCuentaBanco2")
    private String cuentaAbono2;
    
    @Column(name = "comerCodigoBanco3")
    private String codigoBancoCuentaAbono3;
    
    @Column(name = "comerCuentaBanco3")
    private String cuentaAbono3;
    
    @Column(name = "comerModalidadPos")
    private String modalidadPos;
    
    @Column(name = "comerTipoPos")
    private String tipoPos;
    
    @Column(name = "comerModalidadGarantia")
    private String modalidadGarantia;
    
    @Column(name = "comerMontoGarFian")
    private String montoGarantia;
    
    @Column(name = "comerFechaGarFian")
    private String fechaGarantiaFianza;
    
    @Column(name = "comerRecaudos")
    private String recaudos; 
    
    @Column(name = "comerDireccion")
    private String direccionComercio;
    
    @Column(name = "comerDireccionHabitacion")
    private String direccionHabitacion;
    
    @Column(name = "comerDireccionPos")
    private String direccionPos;
    
    @Column(name = "comerObservaciones")
    private String observacionesComercio;
    
    @Column(name = "comerEstatus")
    private String estatus;
    
    @Column(name = "comerImagen")
    private byte[] imagenComercio;
    
    @Column(name = "comerCodAliado")
    private String codigoAliado;
    
    @Column(name = "aliApellidos")
    private String apellidosAliado;
    
    @Column(name = "aliNombres")
    private String nombresAliado;
    
    @Column(name = "cmPorcentaje")
    private String comisionComercio;
    
    @Column(name = "comerDiasOperacion")
    private String diasOperacion;
    
    public Integer getCodigoComercio() {
        return codigoComercio;
    }

    public void setCodigoComercio(Integer codigoComercio) {
        this.codigoComercio = codigoComercio;
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

    public String getCodigoPadre() {
        return codigoPadre;
    }

    public void setCodigoPadre(String codigoPadre) {
        this.codigoPadre = codigoPadre;
    }

    public String getRifComercio() {
        return rifComercio;
    }

    public void setRifComercio(String rifComercio) {
        this.rifComercio = rifComercio;
    }

    public String getCodigoAfiliado() {
        return codigoAfiliado;
    }

    public void setCodigoAfiliado(String codigoAfiliado) {
        this.codigoAfiliado = codigoAfiliado;
    }

    public String getCodigoContacto() {
        return codigoContacto;
    }

    public void setCodigoContacto(String codigoContacto) {
        this.codigoContacto = codigoContacto;
    }
        
    public String getNombreContacto() {
        return nombreContacto;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public String getApellidoContacto() {
        return apellidoContacto;
    }

    public void setApellidoContacto(String apellidoContacto) {
        this.apellidoContacto = apellidoContacto;
    }

    public String getTelefonoHabitacion() {
        return telefonoHabitacion;
    }

    public void setTelefonoHabitacion(String telefonoHabitacion) {
        this.telefonoHabitacion = telefonoHabitacion;
    }

    public String getTelefonoCelular() {
        return telefonoCelular;
    }

    public void setTelefonoCelular(String telefonoCelular) {
        this.telefonoCelular = telefonoCelular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescripcionAfiliado() {
        return descripcionAfiliado;
    }

    public void setDescripcionAfiliado(String descripcionAfiliado) {
        this.descripcionAfiliado = descripcionAfiliado;
    }

    public String getTipoPersonaAfiliado() {
        return tipoPersonaAfiliado;
    }

    public void setTipoPersonaAfiliado(String tipoPersonaAfiliado) {
        this.tipoPersonaAfiliado = tipoPersonaAfiliado;
    }

    public String getCategoriaComercio() {
        return categoriaComercio;
    }

    public void setCategoriaComercio(String categoriaComercio) {
        this.categoriaComercio = categoriaComercio;
    }

    public String getTipoContrato() {
        return tipoContrato;
    }
    
    public void setTipoContrato(String tipoContrato) {
        this.tipoContrato = tipoContrato;
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

    public String getCodigoBancoCuentaAbono() {
        return codigoBancoCuentaAbono;
    }

    public void setCodigoBancoCuentaAbono(String codigoBancoCuentaAbono) {
        this.codigoBancoCuentaAbono = codigoBancoCuentaAbono;
    }
    
    public String getCuentaAbono() {
        return cuentaAbono;
    }

    public void setCuentaAbono(String cuentaAbono) {
        this.cuentaAbono = cuentaAbono;
    }

    public String getMontoGarantia() {
        return montoGarantia;
    }

    public void setMontoGarantia(String montoGarantia) {
        this.montoGarantia = montoGarantia;
    }

    public String getRecaudos() {
        return recaudos;
    }

    public void setRecaudos(String recaudos) {
        this.recaudos = recaudos;
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

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getEntregoGarantia() {
        return entregoGarantia;
    }

    public void setEntregoGarantia(String entregoGarantia) {
        this.entregoGarantia = entregoGarantia;
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
    
    public byte[] getImagenComercio() {
        return imagenComercio;
    }

    public void setImagenComercio(byte[] imagenComercio) {
        this.imagenComercio = imagenComercio;
    }

    public String getCodigoAliado() {
        return codigoAliado;
    }

    public void setCodigoAliado(String codigoAliado) {
        this.codigoAliado = codigoAliado;
    }

    public String getApellidosAliado() {
        return apellidosAliado;
    }

    public void setApellidosAliado(String apellidosAliado) {
        this.apellidosAliado = apellidosAliado;
    }

    public String getNombresAliado() {
        return nombresAliado;
    }

    public void setNombresAliado(String nombresAliado) {
        this.nombresAliado = nombresAliado;
    }
    
    public String getAliadoNombreCompleto() {
        return apellidosAliado + " " + nombresAliado;
    }

    public String getComisionComercio() {
        return comisionComercio;
    }

    public void setComisionComercio(String comisionComercio) {
        this.comisionComercio = comisionComercio;
    }

    public String getPuntoAdicional() {
        return puntoAdicional;
    }

    public void setPuntoAdicional(String puntoAdicional) {
        this.puntoAdicional = puntoAdicional;
    }

    public String getCodigoBancoCuentaAbono2() {
        return codigoBancoCuentaAbono2;
    }

    public void setCodigoBancoCuentaAbono2(String codigoBancoCuentaAbono2) {
        this.codigoBancoCuentaAbono2 = codigoBancoCuentaAbono2;
    }

    public String getCuentaAbono2() {
        return cuentaAbono2;
    }

    public void setCuentaAbono2(String cuentaAbono2) {
        this.cuentaAbono2 = cuentaAbono2;
    }

    public String getCodigoBancoCuentaAbono3() {
        return codigoBancoCuentaAbono3;
    }

    public void setCodigoBancoCuentaAbono3(String codigoBancoCuentaAbono3) {
        this.codigoBancoCuentaAbono3 = codigoBancoCuentaAbono3;
    }

    public String getCuentaAbono3() {
        return cuentaAbono3;
    }

    public void setCuentaAbono3(String cuentaAbono3) {
        this.cuentaAbono3 = cuentaAbono3;
    }

    public String getFechaGarantiaFianza() {
        return fechaGarantiaFianza;
    }

    public void setFechaGarantiaFianza(String fechaGarantiaFianza) {
        this.fechaGarantiaFianza = fechaGarantiaFianza;
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

    public String getDiasOperacion() {
        return diasOperacion;
    }

    public void setDiasOperacion(String diasOperacion) {
        this.diasOperacion = diasOperacion;
    }
    
}
