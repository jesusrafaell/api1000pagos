package com.tranred.milpagosapp.domain;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * POJO que representa un Comercio

 Cada comercio tiene un id que lo identifica
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */


@Entity
@Table (name="Comercios")
public class Comercio implements Serializable{
    
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
    private int codigoPadre;
        
    @Column(name = "comerRif")
    private String rifComercio;
    
    @Column(name = "comerFreg")
    private Date comercioFreg;   
        
    @Column(name = "comerCodTipoCont")
    private Integer codigoTipoContrato;
    
    @Column(name = "comerInicioContrato")
    private Date fechaInicioContrato;
    
    @Column(name = "comerFinContrato")
    private Date fechaFinContrato;
    
    @Column(name = "comerExcluirPago")
    private int excluirArchivoPago;
        
    @Column(name = "comerCodCategoria")
    private int codigoCategoria;
    
    @Column(name = "comerCodigoBanco")
    private String codigoBancoCuentaAbono;
    
    @Column(name = "comerCuentaBanco")
    private String numeroCuentaAbono;
    
    @Column(name = "comerPuntoAdicional")
    private int puntoAdicional;
    
    @Column(name = "comerCodigoBanco2")
    private String codigoBancoCuentaAbono2;
    
    @Column(name = "comerCuentaBanco2")
    private String numeroCuentaAbono2;
    
    @Column(name = "comerCodigoBanco3")
    private String codigoBancoCuentaAbono3;
    
    @Column(name = "comerCuentaBanco3")
    private String numeroCuentaAbono3;
        
    @Column(name = "comerGarantiaFianza")
    private int entregoGarantiaFianza;
        
    @Column(name = "comerMontoGarFian")
    private Double montoGarantiaFianza;
    
    @Column(name = "comerFechaGarFian")
    private Date fechaGarantiaFianza;
    
    @Column(name = "comerModalidadPos")
    private String modalidadPos;
    
    @Column(name = "comerTipoPos")
    private String tipoPos;
    
    @Column(name = "comerModalidadGarantia")
    private String modalidadGarantia;
    
    @ManyToOne
    @JoinColumn(name="comerModalidadGarantia", referencedColumnName = "codTipoGarantia", insertable = false, updatable = false)
    private TipoGarantiaComercio tipoGarantia;
    
    @Column(name = "comerRecaudos")
    private String recaudosComercio;
        
    @Column(name = "comerDireccion")
    private String direccionComercio;
    
    @Column(name = "comerDireccionHabitacion")
    private String direccionHabitacion;
    
    @Column(name = "comerDireccionPos")
    private String direccionPos;
    
    @Column(name = "comerObservaciones")
    private String observacionesComercio;
    
    @Column(name = "comerCodAliado")
    private String codigoAliado;
    
    @ManyToOne
    @JoinColumn(name="comerCodAliado", referencedColumnName = "id", insertable = false, updatable = false)
    private Aliado aliadoComercial;
    
    @Column(name = "comerEstatus")
    private int estatusComercio;        
    
    @Column(name = "comerImagen")
    private byte[] imagenComercio;
    
    @ManyToOne
    @JoinColumn(name="comerCod", referencedColumnName = "contCodComer", insertable = false, updatable = false)
    private Contacto contacto;
    
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

    public int getCodigoPadre() {
        return codigoPadre;
    }

    public void setCodigoPadre(int codigoPadre) {
        this.codigoPadre = codigoPadre;
    }

    public String getRifComercio() {
        return rifComercio;
    }

    public void setRifComercio(String rifComercio) {
        this.rifComercio = rifComercio;
    }

    public Date getComercioFreg() {
        return comercioFreg;
    }

    public void setComercioFreg(Date comercioFreg) {
        this.comercioFreg = comercioFreg;
    }    

    public Integer getCodigoTipoContrato() {
        return codigoTipoContrato;
    }
    
    public void setCodigoTipoContrato(Integer codigoTipoContrato) {
        this.codigoTipoContrato = codigoTipoContrato;
    }    

    public Date getFechaInicioContrato() {
        return fechaInicioContrato;
    }

    public void setFechaInicioContrato(Date fechaInicioContrato) {
        this.fechaInicioContrato = fechaInicioContrato;
    }

    public Date getFechaFinContrato() {
        return fechaFinContrato;
    }

    public void setFechaFinContrato(Date fechaFinContrato) {
        this.fechaFinContrato = fechaFinContrato;
    }

    public int getExcluirArchivoPago() {
        return excluirArchivoPago;
    }

    public void setExcluirArchivoPago(int excluirArchivoPago) {
        this.excluirArchivoPago = excluirArchivoPago;
    }
    
    public int getCodigoCategoria() {
        return codigoCategoria;
    }

    public void setCodigoCategoria(int codigoCategoria) {
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

    public int getPuntoAdicional() {
        return puntoAdicional;
    }

    public void setPuntoAdicional(int puntoAdicional) {
        this.puntoAdicional = puntoAdicional;
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

    public int getEntregoGarantiaFianza() {
        return entregoGarantiaFianza;
    }

    public void setEntregoGarantiaFianza(int entregoGarantiaFianza) {
        this.entregoGarantiaFianza = entregoGarantiaFianza;
    }

    public Double getMontoGarantiaFianza() {
        return montoGarantiaFianza;
    }
    
    public void setMontoGarantiaFianza(Double montoGarantiaFianza) {
        this.montoGarantiaFianza = montoGarantiaFianza;
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

    public TipoGarantiaComercio getTipoGarantia() {
        return tipoGarantia;
    }

    public void setTipoGarantia(TipoGarantiaComercio tipoGarantia) {
        this.tipoGarantia = tipoGarantia;
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

    public Aliado getAliadoComercial() {
        return aliadoComercial;
    }

    public void setAliadoComercial(Aliado aliadoComercial) {
        this.aliadoComercial = aliadoComercial;
    }
    
    public Contacto getContacto() {
        return contacto;
    }

    public void setContacto(Contacto contacto) {
        this.contacto = contacto;
    }

    public int getEstatusComercio() {
        return estatusComercio;
    }

    public void setEstatusComercio(int estatusComercio) {
        this.estatusComercio = estatusComercio;
    }

    public byte[] getImagenComercio() {
        return imagenComercio;
    }

    public void setImagenComercio(byte[] imagenComercio) {
        this.imagenComercio = imagenComercio;
    }

    public Date getFechaGarantiaFianza() {
        return fechaGarantiaFianza;
    }

    public void setFechaGarantiaFianza(Date fechaGarantiaFianza) {
        this.fechaGarantiaFianza = fechaGarantiaFianza;
    }

    public String getDiasOperacion() {
        return diasOperacion;
    }

    public void setDiasOperacion(String diasOperacion) {
        this.diasOperacion = diasOperacion;
    }
    
}
