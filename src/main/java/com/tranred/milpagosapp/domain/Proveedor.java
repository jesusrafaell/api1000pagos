package com.tranred.milpagosapp.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * POJO que representa un proveedor
 *
 * Cada proveedor tiene un id que la identifica
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */

@Entity
@Table(name="Lista_Proveedores")
public class Proveedor implements Serializable{
	
    private static final long serialVersionUID = 1L;        

    @Id
    @Column(name = "id_tabla")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_tabla;

    private String codigo;
    private String id;
    private String nombre;    
    @Column(name = "Org")
    private String organizacion;    
    private boolean creacion;
    @Column(name = "Opc_Creacion")
    private boolean opcionCreacion;
    private boolean falla;
    private boolean retiro;
    private boolean lote;

    public Integer getId_tabla() {
        return id_tabla;
    }

    public void setId_tabla(Integer id_tabla) {
        this.id_tabla = id_tabla;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(String organizacion) {
        this.organizacion = organizacion;
    }

    public boolean isCreacion() {
        return creacion;
    }

    public void setCreacion(boolean creacion) {
        this.creacion = creacion;
    }

    public boolean isOpcionCreacion() {
        return opcionCreacion;
    }

    public void setOpcionCreacion(boolean opcionCreacion) {
        this.opcionCreacion = opcionCreacion;
    }

    public boolean isFalla() {
        return falla;
    }

    public void setFalla(boolean falla) {
        this.falla = falla;
    }

    public boolean isRetiro() {
        return retiro;
    }

    public void setRetiro(boolean retiro) {
        this.retiro = retiro;
    }

    public boolean isLote() {
        return lote;
    }

    public void setLote(boolean lote) {
        this.lote = lote;
    }
        
}
