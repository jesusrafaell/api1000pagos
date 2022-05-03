package com.tranred.milpagosapp.service;

import com.tranred.milpagosapp.domain.Proveedor;
import com.tranred.milpagosapp.repository.JPAProveedorDAO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Clase para el manejo de Proveedores
 * mggy@sistemasemsys.com
 * @version 0.1
 */
@Component
public class ProveedorService implements IProveedorService{
    
    private static final long serialVersionUID = 1L;
    
    @Autowired
    private JPAProveedorDAO proveedorDao;
    
    public List<Proveedor> getProveedorList(String afiliado) {
        return proveedorDao.getProveedorList(afiliado);
    }
    
    public List<Proveedor> getProveedorByOrgIdList(String afiliado , String id){
        return proveedorDao.getProveedorByOrgIdList(afiliado,id);
    }
}
