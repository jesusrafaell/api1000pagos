package com.tranred.milpagosapp.service;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Clase que recibe los datos del formulario editarParametro
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */

public class ParametroForm {
    
    private String id;    
    
    @NotNull
    @NotEmpty
    private String codigo;
    
    @NotNull
    @NotEmpty
    private String nombre;
    
    @NotNull
    @NotEmpty
    @Size(min=1, max=140)
    private String descripcion;
    
    @NotNull
    @NotEmpty
    @Size(min=1, max=140)
    private String valor;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
    
    
}
