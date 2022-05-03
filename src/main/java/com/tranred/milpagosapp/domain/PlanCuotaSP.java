package com.tranred.milpagosapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;

/**
 * POJO que representa un registro de la tabla Historico para Generar Estados de Cuenta
 * La información es obtenida del Store Procedure SP_consultaEstadosCuenta
 * 
 * @author mcaraballo@emsys-solutions.net
 * @version 0.1
 */

@NamedNativeQueries({
    @NamedNativeQuery(
            name = "consultaCobranzas",
            query = "EXEC SP_consultaCobranza :mes,:ano,:estatus,:tipoPlan,:tipoConsulta,:tipoConsultaIndividual,:tipoIdentificacionComercio,:identificacionComercio,:terminal,:tipoIdentificacionACI,:identificacionACI, :tipoReporte",
            resultClass = PlanCuotaSP.class
    )
 })
@Entity
public class PlanCuotaSP implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "id")
    private Long id;      
                
    @Column(name = "aboCodAfi")
    private String aboCodAfi;         
                
    @Column(name = "aboCodComercio")
    private String aboCodComercio;         
                
    @Column(name = "aboTerminal")
    private String aboTerminal;
                            
    @Column(name = "estatusDesc")
    private String estatusDesc;    

    @Column(name = "idPlanCuota")
    private Long idPlanCuota; 
    
    @Column(name = "codTipoPlan")
    private Long codTipoPlan;     
    
    @Column(name = "descTipoPlan")
    private String descTipoPlan;  
       
    @Column(name = "cantCuotas")
    private String cantCuotas;      
    
    @Column(name = "montoTotal")
    private BigDecimal montoTotal;    
    
    @Column(name = "montoComision")
    private BigDecimal montoComision;
    
    @Column(name = "montoIVA")
    private BigDecimal montoIVA;
    
    @Column(name = "tasaValor")
    private BigDecimal tasaValor;
    
    @Column(name = "fechaProcesoIni")
    private Date fechaProcesoIni;
            
    @Column(name = "fechaProcesoFin")
    private Date fechaProcesoFin;
        
    @Column(name = "fechaPagoUlt")
    private Date fechaPagoUlt;   
        
    @Column(name = "fechaProcesoLoteCerradoUlt")
    private Date fechaProcesoLoteCerradoUlt;         
    
    @Column(name = "planId")
    private String planId;    
    
    @Column(name = "planNombre")
    private String planNombre;    
    
    @Column(name = "comerDesc")
    private String comerDesc;        
       
    @Column(name = "comerRif")
    private String comerRif;        
       
    @Column(name = "contTelefMov")
    private String contTelefMov;        
       
    @Column(name = "aciDesc")
    private String aciDesc;        
       
    @Column(name = "aciRif")
    private String aciRif;        
       
    @Column(name = "aciTLF")
    private String aciTLF;        

    public String getAboCodAfi() {
        return aboCodAfi;
    }

    public void setAboCodAfi(String aboCodAfi) {
        this.aboCodAfi = aboCodAfi;
    }

    public String getAboCodComercio() {
        return aboCodComercio;
    }

    public void setAboCodComercio(String aboCodComercio) {
        this.aboCodComercio = aboCodComercio;
    }

    public String getAboTerminal() {
        return aboTerminal;
    }

    public void setAboTerminal(String aboTerminal) {
        this.aboTerminal = aboTerminal;
    }

    public Long getIdPlanCuota() {
        return idPlanCuota;
    }

    public void setIdPlanCuota(Long idPlanCuota) {
        this.idPlanCuota = idPlanCuota;
    }

    public Long getCodTipoPlan() {
        return codTipoPlan;
    }

    public void setCodTipoPlan(Long codTipoPlan) {
        this.codTipoPlan = codTipoPlan;
    }

    public String getDescTipoPlan() {
        return descTipoPlan;
    }

    public void setDescTipoPlan(String descTipoPlan) {
        this.descTipoPlan = descTipoPlan;
    }

    public String getEstatusDesc() {
        return estatusDesc;
    }

    public void setEstatusDesc(String estatusDesc) {
        this.estatusDesc = estatusDesc;
    }

    public String getCantCuotas() {
        return cantCuotas;
    }

    public void setCantCuotas(String cantCuotas) {
        this.cantCuotas = cantCuotas;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    public BigDecimal getMontoComision() {
        return montoComision;
    }

    public void setMontoComision(BigDecimal montoComision) {
        this.montoComision = montoComision;
    }

    public BigDecimal getMontoIVA() {
        return montoIVA;
    }

    public void setMontoIVA(BigDecimal montoIVA) {
        this.montoIVA = montoIVA;
    }

    public BigDecimal getTasaValor() {
        return tasaValor;
    }

    public void setTasaValor(BigDecimal tasaValor) {
        this.tasaValor = tasaValor;
    }

    public Date getFechaProcesoIni() {
        return fechaProcesoIni;
    }

    public void setFechaProcesoIni(Date fechaProcesoIni) {
        this.fechaProcesoIni = fechaProcesoIni;
    }

    public Date getFechaProcesoFin() {
        return fechaProcesoFin;
    }

    public void setFechaProcesoFin(Date fechaProcesoFin) {
        this.fechaProcesoFin = fechaProcesoFin;
    }

    public Date getFechaPagoUlt() {
        return fechaPagoUlt;
    }

    public void setFechaPagoUlt(Date fechaPagoUlt) {
        this.fechaPagoUlt = fechaPagoUlt;
    }

    public Date getFechaProcesoLoteCerradoUlt() {
        return fechaProcesoLoteCerradoUlt;
    }

    public void setFechaProcesoLoteCerradoUlt(Date fechaProcesoLoteCerradoUlt) {
        this.fechaProcesoLoteCerradoUlt = fechaProcesoLoteCerradoUlt;
    }


    public String getComerDesc() {
        return comerDesc;
    }

    public void setComerDesc(String comerDesc) {
        this.comerDesc = comerDesc;
    }

    public String getComerRif() {
        return comerRif;
    }

    public void setComerRif(String comerRif) {
        this.comerRif = comerRif;
    }

    public String getContTelefMov() {
        return contTelefMov;
    }

    public void setContTelefMov(String contTelefMov) {
        this.contTelefMov = contTelefMov;
    }

    public String getAciDesc() {
        return aciDesc;
    }

    public void setAciDesc(String aciDesc) {
        this.aciDesc = aciDesc;
    }

    public String getAciRif() {
        return aciRif;
    }

    public void setAciRif(String aciRif) {
        this.aciRif = aciRif;
    }

    public String getAciTLF() {
        return aciTLF;
    }

    public void setAciTLF(String aciTLF) {
        this.aciTLF = aciTLF;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getPlanNombre() {
        return planNombre;
    }

    public void setPlanNombre(String planNombre) {
        this.planNombre = planNombre;
    }
       
    
}
