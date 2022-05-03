package com.tranred.milpagosapp.repository;

import com.tranred.milpagosapp.domain.Recaudo;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase para el acceso a datos de la tabla Recaudos
 * mggy@sistemasemsys.com
 * @version 0.1
 */

@Repository(value = "RecaudoDao")
public class JPARecaudoDAO {
    
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
    public Recaudo getById(int id) {        
        return em.find(Recaudo.class, id);                
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")    
    public List<Recaudo> getRecaudosList(String tipo) {
         return em.createQuery("select r from Recaudo r where r.tipoRecaudo = '"+ tipo +"' and r.estatus = 0 order by r.id").getResultList();
    }
}
