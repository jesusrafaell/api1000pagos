package com.tranred.milpagosapp.repository;

import com.tranred.milpagosapp.domain.CodigoArea;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase para el acceso a datos de la tabla CodigosArea
 * mggy@sistemasemsys.com
 * @version 0.1
 */
@Repository(value = "CodigosAreaDao")
public class JPACodigoAreaDAO {
    
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
    public List<CodigoArea> getCodigosAreaList(int tipo) {
         return em.createQuery("select c from CodigoArea c where c.tipoCodigo = "+ tipo +" order by c.codigo").getResultList();
    }
}
