package com.tranred.milpagosapp.repository;

import com.tranred.milpagosapp.domain.Reclamo;
import com.tranred.milpagosapp.domain.TipoReclamo;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase que implementa la Interface IReclamoDAO
 * mggy@sistemasemsys.com
 * @version 0.1
 */

@Repository(value = "ReclamoDao")
public class JPAReclamoDAO implements IReclamoDAO{
    
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
    public Reclamo getById(int id) {        
        return em.find(Reclamo.class, id);               
    }
        
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked") 
    public List<Reclamo> getReclamosList(Timestamp fechaDesde,Timestamp fechaHasta) {        
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");        
        return em.createQuery("select r from Reclamo r where r.fechaRecepcion BETWEEN '" + df.format(fechaDesde) + " 00:00:00.0" + "' and '" + df.format(fechaHasta) + " 23:59:59.999" + "' order by r.fechaRecepcion desc").getResultList();
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked") 
    public List<Reclamo> getReclamosListByTipo(Timestamp fechaDesde,Timestamp fechaHasta,String tipo) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        return em.createQuery("select r from Reclamo r where r.tipoReclamo = " + tipo + " and r.fechaRecepcion BETWEEN '" + df.format(fechaDesde) + " 00:00:00.0" + "' and '" + df.format(fechaHasta) + " 23:59:59.999" + "' order by r.fechaRecepcion desc").getResultList();
    }
    
    @Transactional(readOnly = false)
    public void saveReclamo(Reclamo reclamo) {
        em.persist(reclamo);
    }

    @Transactional(readOnly = false)
    public void updateReclamo(Reclamo reclamo) {
        em.merge(reclamo);
    }

    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")    
    public List<TipoReclamo> getTipoReclamosList() {
         return em.createQuery("select tr from TipoReclamo tr order by tr.id").getResultList();
    }   

    @Transactional(readOnly = false)
    public void saveTipoReclamo(TipoReclamo tipoReclamo) {
        em.persist(tipoReclamo);
    }

    @Transactional(readOnly = false)
    public void updateTipoReclamo(TipoReclamo tipoReclamo) {
        em.merge(tipoReclamo);
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public int getNumeroReclamo() {
        return (Integer) em.createNativeQuery("select MAX(id) as numeroReclamo from Reclamos").getSingleResult();
    }
}
