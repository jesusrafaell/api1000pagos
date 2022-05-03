package com.tranred.milpagosapp.repository;

import com.tranred.milpagosapp.domain.Estatus;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase que implementa la Interface IEstatusDAO
 * mggy@sistemasemsys.com
 * @version 0.1
 */
@Repository(value = "EstatusDao")
public class JPAEstatusDAO implements IEstatusDAO{
    
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
    public Estatus getById(int id) {        
        return em.find(Estatus.class, id);                
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")    
    public List<Estatus> getEstatusList() {
         return em.createQuery("select e from Estatus e order by e.id").getResultList();
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")    
    public List<Estatus> getEstatusByModulo(String modulo) {
         return em.createQuery("select e from Estatus e where e.modulo = '"+ modulo +"' order by e.id").getResultList();
    }
    
    @Transactional(readOnly = false)
    public void saveEstatus(Estatus estatus) {
        em.persist(estatus);
    }
    
    @Transactional(readOnly = false)
    public void updateEstatus(Estatus estatus) {
        em.merge(estatus);
    }
}
