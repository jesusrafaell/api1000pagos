package com.tranred.milpagosapp.repository;

import com.tranred.milpagosapp.domain.Opcion;
import com.tranred.milpagosapp.domain.OpcionWeb;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase para el acceso a datos de la tabla opciones
 * mggy@sistemasemsys.com
 * @version 0.1
 */
@Repository(value = "OpcionDao")
public class JPAOpcionDAO {
    
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
    public List<Opcion> getOpcionList() {
         return em.createQuery("select o from Opcion o order by o.id").getResultList();
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")    
    public List<OpcionWeb> getOpcionWebList() {
         return em.createQuery("select ow from OpcionWeb ow order by ow.codigoOpcionWeb").getResultList();
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")    
    public List<Opcion> getOpcionByModuloList(String modulo) {
         return em.createQuery("select o from Opcion o where o.moduloId = "+ modulo +" order by o.id").getResultList();
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public Opcion getById(int id) {        
        return em.find(Opcion.class, id);                
    }
    
    @Transactional(readOnly = false)
    public void saveOpcion(Opcion opcion) {
        em.merge(opcion);
    }
    
}
