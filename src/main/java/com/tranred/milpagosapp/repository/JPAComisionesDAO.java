package com.tranred.milpagosapp.repository;

import com.tranred.milpagosapp.domain.ComisionMilPagos;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase que implementa la Interface IComisionDAO
 * mggy@sistemasemsys.com
 * @version 0.1
 */
@Repository(value = "ComisionesDao")
public class JPAComisionesDAO implements IComisionDAO{
    
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
    public ComisionMilPagos getById(int id) {        
        return em.find(ComisionMilPagos.class, id);                
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")    
    public List<ComisionMilPagos> getComisionList() {
         return em.createQuery("select c from Comision c order by c.codigoComercio").getResultList();
    }        
    
    @Transactional(readOnly = false)
    public void saveComision(ComisionMilPagos comision) {
        em.persist(comision);
    }
    
    @Transactional(readOnly = false)
    public void updateComision(ComisionMilPagos comision) {
        em.merge(comision);
    }
    
    @Transactional(readOnly = false)
    public void removeBanco(ComisionMilPagos comision) {
        ComisionMilPagos comisionToBeRemoved = em.getReference(ComisionMilPagos.class, comision.getCodigoComercio());
        em.remove(comisionToBeRemoved);
    }
}
