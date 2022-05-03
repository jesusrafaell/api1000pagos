package com.tranred.milpagosapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;

/**
 * POJO que representa un Registro Historico de Pago a Comercios
 *
 * Cada Registro tiene un id que lo identifica
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */

@NamedNativeQueries({
    @NamedNativeQuery(
            name = "consultaHistorico",
            query = "EXEC SP_consultaHistoricoPago :fecha, :tipoConsulta, :afiliado",
            resultClass = Historico.class
    )
 })
@Entity
public class Historico implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "hisId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "aboCodAfi")
    private String codigoAfiliado;
        
    @Column(name = "aboCodComercio")
    private int codigoComercio;    
    
    @Column(name = "aboTerminal")
    private String terminal;
    
    @Column(name = "aboCodBanco")
    private String codigoBanco;
    
    @Column(name = "aboNroCuenta")
    private String cuentaAbono;
    
    @Column(name = "aboTipoCuenta")
    private String tipoCuentaAbono;                    
    
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
    
    @Column(name = "comerExcluirPago")
    private String excluirArchivoPago;
      
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
    private int tipoPersonaAfiliado;
    
    @Column(name = "afiNroCuenta")
    private String nroCuentaAbono;
         
    @Column(name = "hisLote")
    private String lote;
    
    @Column(name = "hisRecordTDD")
    private int recordTDD;
       
    @Column(name = "hisAmountTDD")
    private float montoTDD;
    
    @Column(name = "hisRecordTDC")
    private int recordTDC;    
    
    @Column(name = "hisAmountTDC")
    private float montoTDC;
    
    @Column(name = "hisAmountTDCImpuesto")
    private float montoTdcImpuesto;    
    
    @Column(name = "hisAmountIVA")
    private float montoIVA;    
    
    @Column(name = "hisAmountComisionBanco")
    private float montoComisionBanco;
            
    @Column(name = "hisAmountTotal")
    private BigDecimal montoTotal;
      
    @Column(name = "hisFechaEjecucion")
    private Date fecha;    
 
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
    private String estatusAliado;        
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigoBanco() {
        return codigoBanco;
    }

    public void setCodigoBanco(String codigoBanco) {
        this.codigoBanco = codigoBanco;
    }

    public String getCuentaAbono() {
        return cuentaAbono;
    }

    public void setCuentaAbono(String cuentaAbono) {
        this.cuentaAbono = cuentaAbono;
    }

    public String getTipoCuentaAbono() {
        return tipoCuentaAbono;
    }

    public void setTipoCuentaAbono(String tipoCuentaAbono) {
        this.tipoCuentaAbono = tipoCuentaAbono;
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

    public String getExcluirArchivoPago() {
        return excluirArchivoPago;
    }

    public void setExcluirArchivoPago(String excluirArchivoPago) {
        this.excluirArchivoPago = excluirArchivoPago;
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

    public int getTipoPersonaAfiliado() {
        return tipoPersonaAfiliado;
    }

    public void setTipoPersonaAfiliado(int tipoPersonaAfiliado) {
        this.tipoPersonaAfiliado = tipoPersonaAfiliado;
    }

    public String getNroCuentaAbono() {
        return nroCuentaAbono;
    }

    public void setNroCuentaAbono(String nroCuentaAbono) {
        this.nroCuentaAbono = nroCuentaAbono;
    }
         
    public String getCodigoAfiliado() {
        return codigoAfiliado;
    }

    public void setCodigoAfiliado(String codigoAfiliado) {
        this.codigoAfiliado = codigoAfiliado;
    }

    public int getCodigoComercio() {
        return codigoComercio;
    }

    public void setCodigoComercio(int codigoComercio) {
        this.codigoComercio = codigoComercio;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public int getRecordTDD() {
        return recordTDD;
    }

    public void setRecordTDD(int recordTDD) {
        this.recordTDD = recordTDD;
    }

    public float getMontoTDD() {
        return montoTDD;
    }

    public void setMontoTDD(float montoTDD) {
        this.montoTDD = montoTDD;
    }

    public int getRecordTDC() {
        return recordTDC;
    }

    public void setRecordTDC(int recordTDC) {
        this.recordTDC = recordTDC;
    }

    public float getMontoTDC() {
        return montoTDC;
    }

    public void setMontoTDC(float montoTDC) {
        this.montoTDC = montoTDC;
    }

    public float getMontoTdcImpuesto() {
        return montoTdcImpuesto;
    }

    public void setMontoTdcImpuesto(float montoTdcImpuesto) {
        this.montoTdcImpuesto = montoTdcImpuesto;
    }

    public float getMontoIVA() {
        return montoIVA;
    }

    public void setMontoIVA(float montoIVA) {
        this.montoIVA = montoIVA;
    }

    public float getMontoComisionBanco() {
        return montoComisionBanco;
    }

    public void setMontoComisionBanco(float montoComisionBanco) {
        this.montoComisionBanco = montoComisionBanco;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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

    public String getEstatusAliado() {
        return estatusAliado;
    }

    public void setEstatusAliado(String estatusAliado) {
        this.estatusAliado = estatusAliado;
    }    

}
