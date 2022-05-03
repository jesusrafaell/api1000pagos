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
            name = "generarCuotas",
            query = "EXEC SP_generarCuotas :tipo,:terminal,:inicioMes,:mes,:anio",
            resultClass = GenerarCuotas.class
    )
 })
@Entity
public class GenerarCuotas implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id            
    @Column(name = "result")
    private Long result;         
                
    @Column(name = "message")
    private String message;         

    public Long getResult() {
        return result;
    }

    public void setResult(Long result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

                
}
