package com.tranred.milpagosapp.repository;

import com.tranred.milpagosapp.domain.HistoricoValores;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase de acceso a datos para el historico de un valor en especifico
 * mggy@sistemasemsys.com
 * @version 0.1
 */
@Repository(value = "HistoricoValorDao")
public class JPAHistoricoValorDAO {
    
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
    public List<HistoricoValores> getHistoricoByUserIdValorTipo(int usuario, String valor, String tipo) {
        return em.createQuery("select h from HistoricoValores h where h.usuarioId = "+ usuario +" and h.valor = '"+ valor +"' and h.tipo = '"+ tipo +"' order by h.id asc").getResultList();
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")    
    public List<HistoricoValores> getHistoricoByUserIdTipo(int usuario, String tipo) {
        return em.createQuery("select h from HistoricoValores h where h.usuarioId = "+ usuario +" and h.tipo = '"+ tipo +"' order by h.id asc").getResultList();
    }
    
    @Transactional(readOnly = false)
    public void saveHistorico(HistoricoValores registro) {
        em.persist(registro);
    }
    
    @Transactional(readOnly = false)
    public void updateHistorico(HistoricoValores registro) {
        em.merge(registro);
    }
}
