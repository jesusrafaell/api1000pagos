package com.tranred.milpagosapp.repository;

import com.tranred.milpagosapp.domain.Mensaje;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase que implementa la Interface IMensajeDAO
 * mggy@sistemasemsys.com
 * @version 0.1
 */

@Repository(value = "MensajeDao")
public class JPAMensajeDAO implements IMensajeDAO{
    
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
    public Mensaje getById(int id) {        
        return em.find(Mensaje.class, id);                
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")    
    public List<Mensaje> getMensajesList() {
         return em.createQuery("select m from Mensaje m order by m.codigoMensaje").getResultList();
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")    
    public List<Mensaje> getMensajeByClave(String clave) {
         return em.createQuery("select m from Mensaje m where m.mensajeClave = '"+clave+"'").getResultList();
    }
    
    @Transactional(readOnly = false)
    public void updateMensaje(Mensaje mensaje) {
        em.merge(mensaje);
    }
}
