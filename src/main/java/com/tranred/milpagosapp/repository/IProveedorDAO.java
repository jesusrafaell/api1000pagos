package com.tranred.milpagosapp.repository;

import com.tranred.milpagosapp.domain.Proveedor;
import java.util.List;

/**
 * Interface de acceso a datos de un Proveedor
 * mggy@sistemasemsys.com
 * @version 0.1
 */
public interface IProveedorDAO {
    
    public Proveedor getById(int id);
    
    public List<Proveedor> getProveedorList(String afiliado);
    
    public List<Proveedor> getProveedorByOrgIdList(String afiliado , String id);

    public void saveProveedor(Proveedor proveedor);
    
    public void updateProveedor(Proveedor proveedor);
    
    public void removeProveedor(Proveedor proveedor);
    
}
