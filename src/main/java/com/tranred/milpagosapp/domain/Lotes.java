package com.tranred.milpagosapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.DecimalFormat;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * POJO que representa un lote de pagos a comercios
 *
 * Cada lote tiene un id que lo identifica
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */

@Entity
@Table(name="LotesXbanco")
public class Lotes implements Serializable{
    
    private static final long serialVersionUID = 1L;    
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
   
    @Column(name = "lotAfiliado")
    private String codigoAfiliado;
    
    @Column(name = "lotTipoArchivo")
    private String tipoArchivo;
    
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
    
    @Column(name = "lotBanco")
    private String codigoBanco;
    
    @ManyToOne
    @JoinColumn(name="lotBanco", referencedColumnName = "banCodBan", insertable = false, updatable = false)
    private Banco banco;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigoAfiliado() {
        return codigoAfiliado;
    }

    public void setCodigoAfiliado(String codigoAfiliado) {
        this.codigoAfiliado = codigoAfiliado;
    }

    public String getTipoArchivo() {
        return tipoArchivo;
    }

    public void setTipoArchivo(String tipoArchivo) {
        this.tipoArchivo = tipoArchivo;
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
    
    public String getMontoTotalFormato() {
        DecimalFormat formateador = new DecimalFormat("#,#00.00");        
        return formateador.format(montoTotal);
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

    public String getCodigoBanco() {
        return codigoBanco;
    }

    public void setCodigoBanco(String codigoBanco) {
        this.codigoBanco = codigoBanco;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }
        
}
