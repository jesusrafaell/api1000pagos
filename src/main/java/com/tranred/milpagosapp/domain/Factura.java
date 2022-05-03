package com.tranred.milpagosapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * POJO que representa un Registro de Facturacion a Comercios
 *
 * Cada Registro tiene un id que lo identifica
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */

@Entity
@Table (name="Facturacion")
public class Factura implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
        
    @Column(name = "factCodComercio")
    private int codigoComercio;    
    
    @Column(name = "factNumFactura")
    private String numeroFactura;
    
    @Column(name = "factNumControl")
    private String numeroControl;
    
    @Column(name = "factBaseImponible")
    private BigDecimal baseImponible;
    
    @Column(name = "factPorcIVA")
    private String iva;                    
    
    @Column(name = "factMontoIVA")
    private BigDecimal montoIVA;
        
    @Column(name = "factMontoTotal")
    private BigDecimal montoTotal;    
    
    @Column(name = "factMontoPetro")
    private BigDecimal montoPetro;
    
    @Column(name = "factMes")
    private String mes; 
    
    @Column(name = "factAno")
    private String ano; 
    
    @Column(name = "factFecha")
    private Timestamp fecha;    
    
    @Column(name = "factEstatus")
    private int estatus;
    
    @ManyToOne
    @JoinColumn(name="factCodComercio", referencedColumnName = "comerCod", insertable = false, updatable = false)
    private Comercio comercio;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCodigoComercio() {
        return codigoComercio;
    }

    public void setCodigoComercio(int codigoComercio) {
        this.codigoComercio = codigoComercio;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public String getNumeroControl() {
        return numeroControl;
    }

    public void setNumeroControl(String numeroControl) {
        this.numeroControl = numeroControl;
    }

    public BigDecimal getBaseImponible() {
        return baseImponible;
    }

    public void setBaseImponible(BigDecimal baseImponible) {
        this.baseImponible = baseImponible;
    }

    public String getIva() {
        return iva;
    }

    public void setIva(String iva) {
        this.iva = iva;
    }

    public BigDecimal getMontoIVA() {
        return montoIVA;
    }

    public void setMontoIVA(BigDecimal montoIVA) {
        this.montoIVA = montoIVA;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    public BigDecimal getMontoPetro() {
        return montoPetro;
    }

    public void setMontoPetro(BigDecimal montoPetro) {
        this.montoPetro = montoPetro;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }
    
    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public Comercio getComercio() {
        return comercio;
    }

    public void setComercio(Comercio comercio) {
        this.comercio = comercio;
    }
                   
}
