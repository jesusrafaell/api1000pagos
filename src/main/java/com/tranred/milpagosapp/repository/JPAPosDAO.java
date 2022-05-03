package com.tranred.milpagosapp.repository;

import com.tranred.milpagosapp.domain.ModalidadPos;
import com.tranred.milpagosapp.domain.TipoPos;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase que implementa la Interface IPosDAO
 * mggy@sistemasemsys.com
 * @version 0.1
 */
@Repository(value = "PosDao")
public class JPAPosDAO implements IPosDAO{
    
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
    public ModalidadPos getModalidadPosById(int id) {        
        return em.find(ModalidadPos.class, id);                
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")    
    public List<ModalidadPos> getModalidadPosList() {
        return em.createQuery("select mp from ModalidadPos mp order by mp.id").getResultList();
    }        
    
    @Transactional(readOnly = false)
    public void saveModalidadPos(ModalidadPos modalidadPos) {
        em.persist(modalidadPos);
    }
    
    @Transactional(readOnly = false)
    public void updateModalidadPos(ModalidadPos modalidadPos) {      
        em.merge(modalidadPos);                
    }       
    
    @Transactional(readOnly = false)
    public void removeModalidadPos(ModalidadPos modalidadPos) {      
        ModalidadPos modalidadPosToBeRemoved = em.getReference(ModalidadPos.class, modalidadPos.getId());
        em.remove(modalidadPosToBeRemoved);               
    }                                   
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public TipoPos getTipoPosById(int id) {        
        return em.find(TipoPos.class, id);                
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")    
    public List<TipoPos> getTipoPosList() {
        return em.createQuery("select tp from TipoPos tp order by tp.id").getResultList();
    }        
    
    @Transactional(readOnly = false)
    public void saveTipoPos(TipoPos tipoPos) {
        em.persist(tipoPos);
    }
    
    @Transactional(readOnly = false)
    public void updateTipoPos(TipoPos tipoPos) {      
        em.merge(tipoPos);                
    }       
    
    @Transactional(readOnly = false)
    public void removeTipoPos(TipoPos tipoPos) {      
        TipoPos tipoPosToBeRemoved = em.getReference(TipoPos.class, tipoPos.getId());
        em.remove(tipoPosToBeRemoved);               
    }
    
}
