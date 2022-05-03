package com.tranred.milpagosapp.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * POJO que representa un Aliado Comercial
 *
 * Cada Aliado tiene un id que lo identifica
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */

@Entity
@Table (name="Aliados")
public class Aliado implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "aliIdUsuario") 
    private Integer idUsuario;
    
    @Column(name = "aliTipoIdentificacion")
    @NotNull
    @NotEmpty
    private String tipoIdentificacion;
    @Column(name = "aliIdentificacion")
    @NotNull
    @NotEmpty   
    @Size(min = 1, max = 9)
    private String identificacion;
    @Column(name = "aliApellidos")    
    @NotNull
    @NotEmpty   
    @Size(max = 80)
    private String apellidos;
    @Column(name = "aliNombres")
    @NotNull
    @NotEmpty   
    @Size(max = 80)
    private String nombres;
    @Column(name = "aliSexo")
    @NotNull
    @NotEmpty    
    private String sexo;
    @Column(name = "aliFechaNacimiento")
    @NotEmpty
    @DateTimeFormat(style="S-")   
    private String fechaNacimiento;
    @Column(name = "aliCodigoTelHabitacion")
    private String codigoTelHabitacion;
    @Column(name = "aliTelefonoHabitacion")
    @NotNull
    @NotEmpty   
    @Min(7)
    private String telefonoHabitacion;
    @Column(name = "aliCodigoCelular")
    private String codigoCelular;
    @Column(name = "aliCelular")
    @NotNull
    @NotEmpty   
    @Min(7)
    private String celular;
    @Column(name = "aliEmail")
    @NotNull
    @NotEmpty
    @Email
    @Size(max = 80)
    private String email;
    @Column(name = "aliProfesion")
    @NotNull
    @NotEmpty   
    @Size(max = 80)
    private String profesion;
    @Column(name = "aliDireccion")    
    @Size(max = 250)
    private String direccion;
    @Column(name = "aliCodZonaAtencion")
    @NotNull
    @NotEmpty    
    private String codZonaAtencion;
    @Column(name = "aliCodModalidadPago")
    private String codModalidadPago;    
    
    @ManyToOne
    @JoinColumn(name="aliCodModalidadPago", referencedColumnName = "codTipoPago", insertable = false, updatable = false)
    private TipoPagoAliado codTipoPagoAliado;
    
    @Column(name = "aliCuentaAbono")
    @Size(max = 20)
    private String cuentaAbono;    
    @Column(name = "aliObservaciones")
    @Size(max = 250)
    private String observaciones;
    @Column(name = "aliCodEstatus")
    private int estatus;
    @Column(name = "aliRecaudos")
    private String recaudos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }
    
    public String getContactoIdentificacion() {
        return tipoIdentificacion + "-" + identificacion;
    }
    
    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
    
    public String getContactoNombreCompleto() {
        return apellidos + " " + nombres;
    }
    
    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTelefonoHabitacion() {
        return telefonoHabitacion;
    }

    public void setTelefonoHabitacion(String telefonoHabitacion) {
        this.telefonoHabitacion = telefonoHabitacion;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCodZonaAtencion() {
        return codZonaAtencion;
    }

    public void setCodZonaAtencion(String codZonaAtencion) {
        this.codZonaAtencion = codZonaAtencion;
    }

    public String getCodModalidadPago() {
        return codModalidadPago;
    }

    public void setCodModalidadPago(String codModalidadPago) {
        this.codModalidadPago = codModalidadPago;
    }

    public TipoPagoAliado getCodTipoPagoAliado() {
        return codTipoPagoAliado;
    }

    public void setCodTipoPagoAliado(TipoPagoAliado codTipoPagoAliado) {
        this.codTipoPagoAliado = codTipoPagoAliado;
    }   

    public String getCuentaAbono() {
        return cuentaAbono;
    }

    public void setCuentaAbono(String cuentaAbono) {
        this.cuentaAbono = cuentaAbono;
    }
    
    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public String getRecaudos() {
        return recaudos;
    }

    public void setRecaudos(String recaudos) {
        this.recaudos = recaudos;
    }

    public String getCodigoTelHabitacion() {
        return codigoTelHabitacion;
    }

    public void setCodigoTelHabitacion(String codigoTelHabitacion) {
        this.codigoTelHabitacion = codigoTelHabitacion;
    }

    public String getCodigoCelular() {
        return codigoCelular;
    }

    public void setCodigoCelular(String codigoCelular) {
        this.codigoCelular = codigoCelular;
    }
    
    public String getContactoTelefono() {
        return codigoCelular + "-" + celular;
    }
}
