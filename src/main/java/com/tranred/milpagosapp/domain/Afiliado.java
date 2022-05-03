package com.tranred.milpagosapp.domain;

import java.io.Serializable;
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
 * POJO que representa un afiliado
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */

@Entity
@Table (name="Afiliados")
public class Afiliado implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @Size(max = 15)
    @NotNull
    @NotEmpty
    @Column(name = "afiCod")    
    private String codigoAfiliado;
    
    @NotNull
    @NotEmpty
    @Size(max = 200)
    @Column(name = "afiDesc")
    private String descripcionAfiliado;
    
    @NotNull
    @NotEmpty
    @Column(name = "afiCodTipoPer")
    private String tipoPersonaAfiliado;
       
    @Column(name = "afiFreg")
    private String afiliadoFreg;
    
    @NotNull
    @NotEmpty
    @Column(name = "afiCodBan")
    private String codigoBanco;
    
    @NotNull
    @NotEmpty
    @Column(name = "afiNroCuenta")
    private String numeroCuentaAbono;

    public String getCodigoAfiliado() {
        return codigoAfiliado;
    }

    public void setCodigoAfiliado(String codigoAfiliado) {
        this.codigoAfiliado = codigoAfiliado;
    }

    public String getDescripcionAfiliado() {
        return descripcionAfiliado;
    }

    public void setDescripcionAfiliado(String descripcionAfiliado) {
        this.descripcionAfiliado = descripcionAfiliado;
    }

    public String getTipoPersonaAfiliado() {
        return tipoPersonaAfiliado;
    }

    public void setTipoPersonaAfiliado(String tipoPersonaAfiliado) {
        this.tipoPersonaAfiliado = tipoPersonaAfiliado;
    }

    public String getAfiliadoFreg() {
        return afiliadoFreg;
    }

    public void setAfiliadoFreg(String afiliadoFreg) {
        this.afiliadoFreg = afiliadoFreg;
    }     

    public String getCodigoBanco() {
        return codigoBanco;
    }

    public void setCodigoBanco(String codigoBanco) {
        this.codigoBanco = codigoBanco;
    }

    public String getNumeroCuentaAbono() {
        return numeroCuentaAbono;
    }

    public void setNumeroCuentaAbono(String numeroCuentaAbono) {
        this.numeroCuentaAbono = numeroCuentaAbono;
    }
        
}
