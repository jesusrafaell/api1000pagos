package com.tranred.milpagosapp.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;

/**
 * POJO que representa los Terminales asociados a un Comercio
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */

@NamedNativeQueries({
    @NamedNativeQuery(
            name = "consultaTerminalXComercio",
            query = "EXEC SP_consultaComercio :tipoConsulta,:rif,:idComercio,:terminal",
            resultClass = TerminalXComercio.class
    )
 })
@Entity
public class TerminalXComercio implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id @Column(name="ID") String id; 
    
    @Column(name = "comerCod")
    private int codigoComercio;
    
    @Column(name = "comerRif")
    private String rifComercio;
    
    @Column(name = "aboTerminal")
    private String terminal;      

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public int getCodigoComercio() {
        return codigoComercio;
    }

    public void setCodigoComercio(int codigoComercio) {
        this.codigoComercio = codigoComercio;
    }

    public String getRifComercio() {
        return rifComercio;
    }

    public void setRifComercio(String rifComercio) {
        this.rifComercio = rifComercio;
    }
    
    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }
        
}
