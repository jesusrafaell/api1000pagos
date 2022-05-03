package com.tranred.milpagosapp.repository;

import com.tranred.milpagosapp.domain.LoteDetalle;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase de acceso a datos para la tabla Lotesxbanco
 * mggy@sistemasemsys.com
 * @version 0.1
 */

@Repository(value = "LoteDetalleDao")
public class JPALoteDetalleDAO {
    
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
    public List<LoteDetalle> getLoteDetalleList(String compania) {
        return em.createQuery("select l from LoteDetalle l where l.codigoCompania = '"+ compania +"'").getResultList();
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public LoteDetalle getByIdLoteDetalle(int id) {        
        return em.find(LoteDetalle.class, id);                
    }
    
    @Transactional(readOnly = false)
    public void saveLoteDetalle(LoteDetalle detalle) {
        em.persist(detalle);
    }
}
