package com.tranred.milpagosapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;

/**
 * POJO que representa un registro de la tabla Historico para Generar la Facturacion de Comercios
 * La información es obtenida del Store Procedure SP_generaFacturacion
 * 
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */

@NamedNativeQueries({
    @NamedNativeQuery(
            name = "generaFacturacion",
            query = "EXEC SP_generaFacturacion :tipoConsulta,:fecha,:codigoComercio,:tipoContrato",
            resultClass = HistoricoFacturacion.class
    )
 })
@Entity
public class HistoricoFacturacion implements Serializable {
    
    private static final long serialVersionUID = 1L;
        
    @Id private Integer id;                              
                            
    @Column(name = "comerCod")
    private int codigoComercio;  
    
    @Column(name = "comerDesc")
    private String descripcionComercio;        
       
    @Column(name = "comerRif")
    private String rifComercio;      
    
    @Column(name = "contTelefLoc")
    private String telefonoHabitacion;    
    
    @Column(name = "contTelefMov")
    private String telefonoCelular;
    
    @Column(name = "comerDireccion")
    private String direccionComercio;
    
    @Column(name = "aliApellidos")
    private String apellidoAliado;
    
    @Column(name = "aliNombres")
    private String nombreAliado;
    
    @Column(name = "terminales")
    private String terminales;
                
    private BigDecimal montoTotal;           
      
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

    public String getDescripcionComercio() {
        return descripcionComercio;
    }

    public void setDescripcionComercio(String descripcionComercio) {
        this.descripcionComercio = descripcionComercio;
    }

    public String getRifComercio() {
        return rifComercio;
    }

    public void setRifComercio(String rifComercio) {
        this.rifComercio = rifComercio;
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

    public String getDireccionComercio() {
        return direccionComercio;
    }

    public void setDireccionComercio(String direccionComercio) {
        this.direccionComercio = direccionComercio;
    }

    public String getApellidoAliado() {
        return apellidoAliado;
    }

    public void setApellidoAliado(String apellidoAliado) {
        this.apellidoAliado = apellidoAliado;
    }

    public String getNombreAliado() {
        return nombreAliado;
    }

    public void setNombreAliado(String nombreAliado) {
        this.nombreAliado = nombreAliado;
    }

    public String getTerminales() {
        return terminales;
    }

    public void setTerminales(String terminales) {
        this.terminales = terminales;
    }    

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }    
    
}
