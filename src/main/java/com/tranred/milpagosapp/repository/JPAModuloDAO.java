package com.tranred.milpagosapp.repository;

import com.tranred.milpagosapp.domain.Modulo;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase para el acceso a datos de la tabla modulo
 * mggy@sistemasemsys.com
 * @version 0.1
 */

@Repository(value = "ModuloDao")
public class JPAModuloDAO {
    
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
    public List<Modulo> getModuloList() {
         return em.createQuery("select m from Modulo m order by m.id").getResultList();
    }
    
    @Transactional(readOnly = false)
    public void saveModulo(Modulo modulo) {
        em.persist(modulo);
    }
    
}
