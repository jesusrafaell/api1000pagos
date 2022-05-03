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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * POJO que representa un abono asociado a un comercio
 *
 * @author mggy@sistemasemsys.com
 * @author jcperez@emsys-solutions.net
 * se agrega el estatus del terminal
 * @version 0.1
 */

@Entity
@Table (name="Abonos")
public class Abono implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "aboCod")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
          
    @Column(name = "aboTerminal")    
    @NotNull
    @NotEmpty
    @Size(max = 8)
    private String codigoTerminal;
    
    @NotNull
    @NotEmpty
    @Size(max = 15)
    @Column(name = "aboCodAfi")
    private String codigoAfiliado;
    
    @NotNull
    @NotEmpty
    @Column(name = "aboCodComercio")
    private String codigoComercio;
    
    @NotNull
    @NotEmpty
    @Size(max = 4)
    @Column(name = "aboCodBanco")
    private String codigoBancoCuentaAbono;
    
    @NotNull
    @NotEmpty   
    @Size(max = 20, min = 20)
    @Column(name = "aboNroCuenta")
    private String numeroCuentaAbono;
    
    @NotNull
    @NotEmpty   
    @Size(max = 2)
    @Column(name = "aboTipoCuenta")
    private String tipoCuentaAbono;
        
    @Column(name = "aboFreg")
    private String freg;
    
    @NotNull
    @Column(name = "estatusId")
    private Integer estatus;
    
    
    @Column(name = "pagoContado")
    private Integer pagoContado;
    
    @Column(name = "fechaPago")
    private Date fechaPago;
    
    @Column(name = "montoEquipoUSD")
    private BigDecimal montoEquipoUSD;
    
    @Column(name = "montoEquipoBs")
    private BigDecimal montoEquipoBs;
    
    @Column(name = "ivaEquipoBs")
    private BigDecimal ivaEquipoBs;
    
    @Column(name = "montoTotalEquipoBs")
    private BigDecimal montoTotalEquipoBs;
    
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
        
    public String getCodigoTerminal() {
        return codigoTerminal;
    }

    public void setCodigoTerminal(String codigoTerminal) {
        this.codigoTerminal = codigoTerminal;
    }   

    public String getCodigoAfiliado() {
        return codigoAfiliado;
    }

    public void setCodigoAfiliado(String codigoAfiliado) {
        this.codigoAfiliado = codigoAfiliado;
    }

    public String getCodigoComercio() {
        return codigoComercio;
    }

    public void setCodigoComercio(String codigoComercio) {
        this.codigoComercio = codigoComercio;
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

    public String getTipoCuentaAbono() {
        return tipoCuentaAbono;
    }

    public void setTipoCuentaAbono(String tipoCuentaAbono) {
        this.tipoCuentaAbono = tipoCuentaAbono;
    }

    public String getFreg() {
        return freg;
    }

    public void setFreg(String freg) {
        this.freg = freg;
    }
    
    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public Integer getPagoContado() {
        return pagoContado;
    }

    public void setPagoContado(Integer pagoContado) {
        this.pagoContado = pagoContado;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public BigDecimal getMontoEquipoUSD() {
        return montoEquipoUSD;
    }

    public void setMontoEquipoUSD(BigDecimal montoEquipoUSD) {
        this.montoEquipoUSD = montoEquipoUSD;
    }

    public BigDecimal getMontoEquipoBs() {
        return montoEquipoBs;
    }

    public void setMontoEquipoBs(BigDecimal montoEquipoBs) {
        this.montoEquipoBs = montoEquipoBs;
    }

    public BigDecimal getIvaEquipoBs() {
        return ivaEquipoBs;
    }

    public void setIvaEquipoBs(BigDecimal ivaEquipoBs) {
        this.ivaEquipoBs = ivaEquipoBs;
    }

    public BigDecimal getMontoTotalEquipoBs() {
        return montoTotalEquipoBs;
    }

    public void setMontoTotalEquipoBs(BigDecimal montoTotalEquipoBs) {
        this.montoTotalEquipoBs = montoTotalEquipoBs;
    }
    
}
