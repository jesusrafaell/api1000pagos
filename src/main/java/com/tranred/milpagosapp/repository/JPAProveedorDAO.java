package com.tranred.milpagosapp.repository;

import com.tranred.milpagosapp.domain.Proveedor;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 * Clase que implementa la Interface IProveedorDAO
 * mggy@sistemasemsys.com
 * @version 0.1
 */
@Repository(value = "ProveedorDao")
public class JPAProveedorDAO implements IProveedorDAO{
    
    private EntityManager em = null;

    /*
     * Sets the entity manager.
     */
    @PersistenceContext
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
    
    public Proveedor getById(int id) {
        return em.find(Proveedor.class, id); 
    }

    public List<Proveedor> getProveedorList(String afiliado) {
        return em.createQuery("select p from Proveedor p where p.organizacion = '" + afiliado + "' and p.creacion = '1' order by p.nombre").getResultList();
    }
    
    public List<Proveedor> getProveedorByOrgIdList(String afiliado , String id) {
        return em.createQuery("select p from Proveedor p where p.organizacion = '" + afiliado + "' and p.id = '" + id + "' order by p.nombre").getResultList();
    }
    
    public void saveProveedor(Proveedor proveedor) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void updateProveedor(Proveedor proveedor) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void removeProveedor(Proveedor proveedor) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
