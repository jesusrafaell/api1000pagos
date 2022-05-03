package com.tranred.milpagosapp.repository;

import com.tranred.milpagosapp.domain.ZonaAtencion;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase para el acceso a datos de la tabla ZonaAtencion
 * mggy@sistemasemsys.com
 * @version 0.1
 */

@Repository(value = "ZonaAtencionDao")
public class JPAZonaAtencionDAO {
    
    private EntityManager em = null;

    /*
     * Sets the entity manager.
     */
    @PersistenceContext
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public ZonaAtencion getById(String id) {        
        return em.find(ZonaAtencion.class, id);                
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")    
    public List<ZonaAtencion> getZonasList() {
         return em.createQuery("select z from ZonaAtencion z order by z.id").getResultList();
    }
    
    @Transactional(readOnly = false)
    public void saveZona(ZonaAtencion zona) {
        em.persist(zona);
    }
    
    @Transactional(readOnly = false)
    public void updateZona(ZonaAtencion zona) {
        em.merge(zona);
    }
    
    @Transactional(readOnly = false)
    public void removeZona(ZonaAtencion zona) {      
        ZonaAtencion zonaToBeRemoved = em.getReference(ZonaAtencion.class, zona.getId());
        em.remove(zonaToBeRemoved);               
    }
}
