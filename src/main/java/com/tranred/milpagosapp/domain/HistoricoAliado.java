package com.tranred.milpagosapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;

/**
 * POJO que representa un Registro Historico de Pago Aliados Comerciales
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */

@NamedNativeQueries({
    @NamedNativeQuery(
            name = "consultaHistoricoAliado",
            query = "EXEC SP_consultaHistoricoPago :fecha, :tipoConsulta, :afiliado",
            resultClass = HistoricoAliado.class
    )
 })
@Entity
public class HistoricoAliado implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
       
    @Column(name = "montoAbonoAliado")
    private BigDecimal montoTotal;           
 
    @Column(name = "aliTipoIdentificacion")   
    private String tipoIdentificacionAliado;
    
    @Column(name = "aliIdentificacion")    
    private String identificacionAliado;
    
    @Column(name = "aliApellidos")    
    private String apellidosAliado;
    
    @Column(name = "aliNombres")    
    private String nombresAliado;
                    
    @Column(name = "aliEmail")   
    private String emailAliado;
        
    @Column(name = "aliDireccion")        
    private String direccionAliado;
    
    @Column(name = "aliCodZonaAtencion")   
    private String codZonaAtencionAliado;
    
    @Column(name = "aliCodModalidadPago")
    private String codModalidadPagoAliado;           
    
    @Column(name = "aliCuentaAbono")    
    private String cuentaAbonoAliado;
        
    @Column(name = "aliCodEstatus")
    private int estatusAliado;        

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }        

    public String getTipoIdentificacionAliado() {
        return tipoIdentificacionAliado;
    }

    public void setTipoIdentificacionAliado(String tipoIdentificacionAliado) {
        this.tipoIdentificacionAliado = tipoIdentificacionAliado;
    }

    public String getIdentificacionAliado() {
        return identificacionAliado;
    }

    public void setIdentificacionAliado(String identificacionAliado) {
        this.identificacionAliado = identificacionAliado;
    }
    
    public String getIdentificacionCompletaAliado() {
        return tipoIdentificacionAliado + "-" +identificacionAliado;
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
    
    public String getContactoAliado() {
        return apellidosAliado + " " + nombresAliado;
    }
    
    public String getEmailAliado() {
        return emailAliado;
    }

    public void setEmailAliado(String emailAliado) {
        this.emailAliado = emailAliado;
    }

    public String getDireccionAliado() {
        return direccionAliado;
    }

    public void setDireccionAliado(String direccionAliado) {
        this.direccionAliado = direccionAliado;
    }

    public String getCodZonaAtencionAliado() {
        return codZonaAtencionAliado;
    }

    public void setCodZonaAtencionAliado(String codZonaAtencionAliado) {
        this.codZonaAtencionAliado = codZonaAtencionAliado;
    }

    public String getCodModalidadPagoAliado() {
        return codModalidadPagoAliado;
    }

    public void setCodModalidadPagoAliado(String codModalidadPagoAliado) {
        this.codModalidadPagoAliado = codModalidadPagoAliado;
    }

    public String getCuentaAbonoAliado() {
        return cuentaAbonoAliado;
    }

    public void setCuentaAbonoAliado(String cuentaAbonoAliado) {
        this.cuentaAbonoAliado = cuentaAbonoAliado;
    }

    public int getEstatusAliado() {
        return estatusAliado;
    }

    public void setEstatusAliado(int estatusAliado) {
        this.estatusAliado = estatusAliado;
    }    

}
