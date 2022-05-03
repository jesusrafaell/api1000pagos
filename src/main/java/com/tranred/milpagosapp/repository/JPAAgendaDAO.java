package com.tranred.milpagosapp.repository;

import com.tranred.milpagosapp.domain.Agenda;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase que implementa la Interface IAgendaDAO
 * mggy@sistemasemsys.com
 * @version 0.1
 */

@Repository(value = "AgendaDao")
public class JPAAgendaDAO implements IAgendaDAO{
    
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
    public Agenda getAgendaById(int id) {        
        return em.find(Agenda.class, id);                
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")    
    public List<Agenda> getAliadoList() {
         return em.createQuery("select a from Agenda a order by a.id").getResultList();
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")    
    public List<Agenda> getAgendaByAliadoList(int codigoAliado) {
         return em.createQuery("select ag from Agenda ag where ag.idAliado = "+ codigoAliado +" order by ag.id").getResultList();
    }       
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")    
    public List<Agenda> getAgendaByZonaList(int codigoZonaAtencion) {
         return em.createQuery("select ag from Agenda ag where ag.informacionAliado.codZonaAtencion = "+ codigoZonaAtencion +" order by ag.id").getResultList();
    }
    
    @Transactional(readOnly = false)
    public void saveAgenda(Agenda agenda) {
        em.persist(agenda);
    }
    
    @Transactional(readOnly = false)
    public void updateAgenda(Agenda agenda) {
        em.merge(agenda);
    }
    
    @Transactional(readOnly = false)
    public void removeAgenda(Agenda agenda) {
        Agenda agendaToBeRemoved = em.getReference(Agenda.class, agenda.getId());
        em.remove(agendaToBeRemoved);
    }
        
}
