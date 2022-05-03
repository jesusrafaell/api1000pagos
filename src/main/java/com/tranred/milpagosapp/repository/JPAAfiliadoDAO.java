package com.tranred.milpagosapp.repository;

import com.tranred.milpagosapp.domain.Afiliado;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase que implementa la Interface IAfiliadoDAO
 * mggy@sistemasemsys.com
 * @version 0.1
 */

@Repository(value = "AfiliadoDao")
public class JPAAfiliadoDAO implements IAfiliadoDAO{
    
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
    public Afiliado getAfiliadoById(String id) {        
        return em.find(Afiliado.class, id);                
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")    
    public List<Afiliado> getAfiliadoList() {
         return em.createQuery("select a from Afiliado a order by a.codigoAfiliado").getResultList();
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")    
    public List<Afiliado> getAfiliadoByBancoList(String codigo) {
         return em.createQuery("select a from Afiliado a where a.codigoBanco = " + codigo + " order by a.codigoAfiliado").getResultList();
    }
    
    @Transactional(readOnly = false)
    public void saveAfiliado(Afiliado afiliado) {
        em.persist(afiliado);
    }
    
    @Transactional(readOnly = false)
    public void updateAfiliado(Afiliado afiliado) {
        em.merge(afiliado);
    }        
        
}
