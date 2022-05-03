package com.tranred.milpagosapp.repository;

import com.tranred.milpagosapp.domain.Abono;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase que implementa la Interface IAbonoDAO
 * mggy@sistemasemsys.com
 * @version 0.1
 */

@Repository(value = "AbonoDao")
public class JPAAbonoDAO implements IAbonoDAO{
    
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
    public List<Abono> getAbonosList() {
         return em.createQuery("select a from Abono a order by a.codigoAfiliado, a.codigoComercio, a.codigoTerminal").getResultList();
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")    
    public List<Abono> getAbonosByComercioList(String codigoComercio) {
         return em.createQuery("select a from Abono a where a.codigoComercio = '"+ codigoComercio +"' order by a.codigoAfiliado, a.codigoComercio, a.codigoTerminal").getResultList();
    }               
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")    
    public List<Abono> getAbonoByTerminal(String codigoTerminal) {
         return em.createQuery("select a from Abono a where a.codigoTerminal = '"+ codigoTerminal +"'").getResultList();
    }
    
    @Transactional(readOnly = false)
    public void saveAbono(Abono abono) {
        em.persist(abono);
    }
    
    @Transactional(readOnly = false)
    public void updateAbono(Abono abono) {
        em.merge(abono);
    }

    @Transactional(readOnly = false)
    public void deleteAbono(Abono abono) {
        em.remove(em.contains(abono) ? abono : em.merge(abono));
    }

}
