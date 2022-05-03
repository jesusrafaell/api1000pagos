package com.tranred.milpagosapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * POJO que representa un lote de pagos a comercios (detalle)
 *
 * Cada lote tiene un id que lo identifica
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */

@Entity
@Table(name="LotesDetalle")
public class LoteDetalle implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
   
    @Column(name = "lotCodCompania")
    private String codigoCompania;
    
    @Column(name = "lotNumLote")
    private String numeroLote;
    
    @Column(name = "lotTipoRegistro")
    private int tipoRegistro;
    
    @Column(name = "lotCodMonedaDeb")
    private String codigoMonedaDebito;
    
    @Column(name = "lotCodMonedaCred")
    private String codigoMonedaCredito;
    
    @Column(name = "lotActividadEcom")
    private int actividadEconomica;
    
    @Column(name = "lotMotivoOpe")
    private int motivoOperacion;
    
    @Column(name = "lotCuentaDebito")
    private String cuentaDebito;
    
    @Column(name = "lotFechaValor")
    private Date fechaValor;
    
    @Column(name = "lotMontoTotal")
    private BigDecimal montoTotal;
    
    @Column(name = "lotCantidadPagos")
    private int cantidadPagos;
    
    @Column(name = "lotNumPagoProveedor")
    private String numeroPagoProveedor;
    
    @Column(name = "lotNumFactura")
    private String numeroFactura;
    
    @Column(name = "lotEmailBeneficiario")
    private String emailBeneficiario;

    @Column(name = "lotRifBeneficiario")
    private String rifBeneficiario;

    @Column(name = "lotNombreBeneficiario")
    private String nombreBeneficiario;

    @Column(name = "lotMonto")
    private float monto;
    
    @Column(name = "lotTipoPago")
    private int tipoPago;

    @Column(name = "lotCodOficBanco")
    private int codigoOficinaBanco;

    @Column(name = "lotCuentaBeneficiario")
    private String cuentaBeneficiario;

    @Column(name = "lotConceptoPago")
    private String conceptoPago;

    @Column(name = "lotCodBancoBenef")
    private String codigoBancoBeneficiario;

    @Column(name = "lotTipoCodBanco")
    private String tipoCodigoBanco;

    @Column(name = "lotNombreBancoBenef")
    private String nombreBancoBeneficiario;

    @Column(name = "lotDireccionBancoBenef")
    private String direccionBancoBeneficiario;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigoCompania() {
        return codigoCompania;
    }

    public void setCodigoCompania(String codigoCompania) {
        this.codigoCompania = codigoCompania;
    }

    public String getNumeroLote() {
        return numeroLote;
    }

    public void setNumeroLote(String numeroLote) {
        this.numeroLote = numeroLote;
    }

    public int getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(int tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }

    public String getCodigoMonedaDebito() {
        return codigoMonedaDebito;
    }

    public void setCodigoMonedaDebito(String codigoMonedaDebito) {
        this.codigoMonedaDebito = codigoMonedaDebito;
    }

    public String getCodigoMonedaCredito() {
        return codigoMonedaCredito;
    }

    public void setCodigoMonedaCredito(String codigoMonedaCredito) {
        this.codigoMonedaCredito = codigoMonedaCredito;
    }

    public int getActividadEconomica() {
        return actividadEconomica;
    }

    public void setActividadEconomica(int actividadEconomica) {
        this.actividadEconomica = actividadEconomica;
    }

    public int getMotivoOperacion() {
        return motivoOperacion;
    }

    public void setMotivoOperacion(int motivoOperacion) {
        this.motivoOperacion = motivoOperacion;
    }

    public String getCuentaDebito() {
        return cuentaDebito;
    }

    public void setCuentaDebito(String cuentaDebito) {
        this.cuentaDebito = cuentaDebito;
    }

    public Date getFechaValor() {
        return fechaValor;
    }

    public void setFechaValor(Date fechaValor) {
        this.fechaValor = fechaValor;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    public int getCantidadPagos() {
        return cantidadPagos;
    }

    public void setCantidadPagos(int cantidadPagos) {
        this.cantidadPagos = cantidadPagos;
    }

    public String getNumeroPagoProveedor() {
        return numeroPagoProveedor;
    }

    public void setNumeroPagoProveedor(String numeroPagoProveedor) {
        this.numeroPagoProveedor = numeroPagoProveedor;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public String getEmailBeneficiario() {
        return emailBeneficiario;
    }

    public void setEmailBeneficiario(String emailBeneficiario) {
        this.emailBeneficiario = emailBeneficiario;
    }

    public String getRifBeneficiario() {
        return rifBeneficiario;
    }

    public void setRifBeneficiario(String rifBeneficiario) {
        this.rifBeneficiario = rifBeneficiario;
    }

    public String getNombreBeneficiario() {
        return nombreBeneficiario;
    }

    public void setNombreBeneficiario(String nombreBeneficiario) {
        this.nombreBeneficiario = nombreBeneficiario;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public int getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(int tipoPago) {
        this.tipoPago = tipoPago;
    }

    public int getCodigoOficinaBanco() {
        return codigoOficinaBanco;
    }

    public void setCodigoOficinaBanco(int codigoOficinaBanco) {
        this.codigoOficinaBanco = codigoOficinaBanco;
    }

    public String getCuentaBeneficiario() {
        return cuentaBeneficiario;
    }

    public void setCuentaBeneficiario(String cuentaBeneficiario) {
        this.cuentaBeneficiario = cuentaBeneficiario;
    }

    public String getConceptoPago() {
        return conceptoPago;
    }

    public void setConceptoPago(String conceptoPago) {
        this.conceptoPago = conceptoPago;
    }

    public String getCodigoBancoBeneficiario() {
        return codigoBancoBeneficiario;
    }

    public void setCodigoBancoBeneficiario(String codigoBancoBeneficiario) {
        this.codigoBancoBeneficiario = codigoBancoBeneficiario;
    }

    public String getTipoCodigoBanco() {
        return tipoCodigoBanco;
    }

    public void setTipoCodigoBanco(String tipoCodigoBanco) {
        this.tipoCodigoBanco = tipoCodigoBanco;
    }

    public String getNombreBancoBeneficiario() {
        return nombreBancoBeneficiario;
    }

    public void setNombreBancoBeneficiario(String nombreBancoBeneficiario) {
        this.nombreBancoBeneficiario = nombreBancoBeneficiario;
    }

    public String getDireccionBancoBeneficiario() {
        return direccionBancoBeneficiario;
    }

    public void setDireccionBancoBeneficiario(String direccionBancoBeneficiario) {
        this.direccionBancoBeneficiario = direccionBancoBeneficiario;
    }
    
}
