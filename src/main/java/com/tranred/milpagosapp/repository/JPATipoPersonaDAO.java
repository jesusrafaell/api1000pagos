package com.tranred.milpagosapp.repository;

import com.tranred.milpagosapp.domain.TipoPersona;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase para el acceso a datos de la tabla TipoPersona
 * mggy@sistemasemsys.com
 * @version 0.1
 */

@Repository(value = "TipoPersonaDao")
public class JPATipoPersonaDAO {
    
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
    public List<TipoPersona> getTipoPersonaList() {
         return em.createQuery("select tp from TipoPersona tp order by tp.descripcion asc").getResultList();
    }
       
}
