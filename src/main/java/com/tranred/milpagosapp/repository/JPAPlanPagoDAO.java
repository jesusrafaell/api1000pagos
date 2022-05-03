package com.tranred.milpagosapp.repository;

import com.tranred.milpagosapp.domain.PlanPago;
import com.tranred.milpagosapp.domain.Plan;
import com.tranred.milpagosapp.domain.Estatus;
import com.tranred.milpagosapp.domain.PlanPagoDetail;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase para el acceso a datos de la tabla PlanPago
 * jperez@emsys-solutions.net
 * @version 0.1
 */

@Repository(value = "PlanPagoDao")
public class JPAPlanPagoDAO{
    
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
    public PlanPago getById(int id) {        
        return em.find(PlanPago.class, id);                
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")    
    public List<PlanPago> getPlanPagoList() {
          return em.createQuery("select b from PlanPago b order by b.id").getResultList();
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")    
    public List<PlanPago> getPlanPagoByTerminalList(String id, Integer estatus) {
        
        String query = "";
        if(estatus.equals(0)){
            query = "select b from PlanPago b  where b.codigoTerminal = '" + id +"' order by b.id";
        }else{
            query = "select b from PlanPago b  where b.codigoTerminal = '" + id +"' and b.estatus = " + Integer.toString(estatus) +" order by b.id";
        }
         return em.createQuery(query).getResultList();
       // return em.createQuery("select b.id as id, b.codigoTerminal as terminal, c.nombre as nombreplan from PlanPago b  , Plan c where b.idplan =  c.id and b.codigoTerminal = " + id + " order by 1").getResultList();
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")    
    public List<PlanPagoDetail> getPlanPagoByTerminalDetailList(String id) {
         return em.createQuery("select b from PlanPagoDetail b where b.codigoTerminal = '" + id + "' order by editable DESC, estatusId asc, id desc").getResultList();
    }
    
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")    
    public List<PlanPago> getPlanPagoByPlanList(int id) {
         return em.createQuery("select b from PlanPago b  where b.idplan = " + Integer.toString(id) +" order by b.id").getResultList();
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public Integer getPlanPagoDummy(String codigoTerminal) {
        try{
            String query = "SELECT planPagoId as planPagoId "
                     + "FROM dbo.PlanPago pp "
                     + "WHERE pp.Estatusid = 23 AND pp.planId = 0 AND pp.aboTerminal = '" + codigoTerminal + "'";
            return (Integer) em.createNativeQuery(query).getSingleResult();
        } catch(NoResultException e) {
            return null;
        }
    }
    
    @Transactional(readOnly = false)
    public void savePlanPago(PlanPago planpago) {
        em.persist(planpago);
    }
    
    @Transactional(readOnly = false)
    public void updatePlanPago(PlanPago planpago) {
        em.merge(planpago);
    }
    
    @Transactional(readOnly = false)
    public void removePlanPago(PlanPago planpago) {
        PlanPago planPagoToBeRemoved = em.getReference(PlanPago.class, planpago.getId());
        em.remove(planPagoToBeRemoved);
    }
}
