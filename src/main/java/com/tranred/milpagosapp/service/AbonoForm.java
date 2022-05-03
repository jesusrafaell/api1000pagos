package com.tranred.milpagosapp.service;

import com.tranred.milpagosapp.domain.*;
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

public class AbonoForm {
    
    private Integer id;
    
    
    @NotNull
    @NotEmpty
    @Size(max = 8)
    private String codigoTerminal;
    
    @NotNull
    @NotEmpty
    @Size(max = 15)
    private String codigoAfiliado;
    
    @NotNull
    @NotEmpty
    private String codigoComercio;
    
    @NotNull
    @NotEmpty
    @Size(max = 4)
    private String codigoBancoCuentaAbono;
    
    @NotNull
    @NotEmpty   
    @Size(max = 20, min = 20)
    private String numeroCuentaAbono;
    
    @NotNull
    @NotEmpty   
    @Size(max = 2)
    private String tipoCuentaAbono;
        
    private String freg;
    
    @NotNull
    private Integer estatus;
    
    private Integer pagoContado;
    
    private String fechaPago;
    
    private String montoEquipoUSD;
    
    private String montoEquipoBs;
    
    private String ivaEquipoBs;
    
    private String montoTotalEquipoBs;
    
    /*este campo sera para guardar el valor inicial del pago de contado, asi si da error algun campo del formulario al enviarse sabemos cual es el que se encuentra en la BD*/
    private Integer pagoContadoBD;
    
    private Boolean poseePlanEquipo;

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

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getMontoEquipoUSD() {
        return montoEquipoUSD;
    }

    public void setMontoEquipoUSD(String montoEquipoUSD) {
        this.montoEquipoUSD = montoEquipoUSD;
    }

    public String getMontoEquipoBs() {
        return montoEquipoBs;
    }

    public void setMontoEquipoBs(String montoEquipoBs) {
        this.montoEquipoBs = montoEquipoBs;
    }

    public String getIvaEquipoBs() {
        return ivaEquipoBs;
    }

    public void setIvaEquipoBs(String ivaEquipoBs) {
        this.ivaEquipoBs = ivaEquipoBs;
    }

    public String getMontoTotalEquipoBs() {
        return montoTotalEquipoBs;
    }

    public void setMontoTotalEquipoBs(String montoTotalEquipoBs) {
        this.montoTotalEquipoBs = montoTotalEquipoBs;
    }

    public Integer getPagoContadoBD() {
        return pagoContadoBD;
    }

    public void setPagoContadoBD(Integer pagoContadoBD) {
        this.pagoContadoBD = pagoContadoBD;
    }

    public Boolean getPoseePlanEquipo() {
        return poseePlanEquipo;
    }

    public void setPoseePlanEquipo(Boolean poseePlanEquipo) {
        this.poseePlanEquipo = poseePlanEquipo;
    }
    
}
