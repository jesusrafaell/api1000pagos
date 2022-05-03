package com.tranred.milpagosapp.repository;

import com.tranred.milpagosapp.domain.Parametro;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase para el acceso a datos de la tabla parametros
 * mggy@sistemasemsys.com
 * @version 0.1
 */

@Repository(value = "ParametroDao")
public class JPAParametroDAO {
    
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
    public Parametro getById(int id) {        
        return em.find(Parametro.class, id);                
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public List<Parametro> getByCodigo(String codigo) {        
        return em.createQuery("select p from Parametro p where p.codigo = '"+ codigo +"' order by p.id").getResultList();
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")    
    public List<Parametro> getParametroList() {
        return em.createQuery("select p from Parametro p order by p.id").getResultList();
    }
    
    @Transactional(readOnly = false)
    public void saveParametro(Parametro parametro) {
        em.persist(parametro);
    }
    
    @Transactional(readOnly = false)
    public void updateParametro(Parametro parametro) {
        em.merge(parametro);
    }
}
