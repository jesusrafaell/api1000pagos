package com.tranred.milpagosapp.service;

import com.tranred.milpagosapp.domain.Proveedor;
import java.io.Serializable;
import java.util.List;

/**
 * Interface utilizada para el manejo de Proveedores
 * mggy@sistemasemsys.com
 * @version 0.1
 */
public interface IProveedorService extends Serializable{
    
    public List<Proveedor> getProveedorList(String afiliado);
    
    public List<Proveedor> getProveedorByOrgIdList(String afiliado , String id);
        
}
