/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tranred.milpagosapp.repository;

import com.tranred.milpagosapp.domain.PlanCuota;
import com.tranred.milpagosapp.domain.PlanPago;
import com.tranred.milpagosapp.domain.Plan;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 * Clase para el acceso a datos de la tabla PlanCuota
 * jperez@emsys-solutions.net
 * @version 0.1
 */

@Repository(value = "PlanCuotaDao")
public class JPAPlanCuotaDAO{
    
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
    public PlanCuota getById(int id) {        
        return em.find(PlanCuota.class, id);                
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")    
    public List<PlanCuota> getPlanCuotaList() {
          return em.createQuery("select b from PlanCuota b order by b.id").getResultList();
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")    
    public List<PlanCuota> getPlanCuotaByTerminalList(String aboTerminal, String estatus) {
        
        String query = "";
        if(estatus.equals("")){
            query = "select b from PlanCuota b  where b.codigoTerminal = '" + aboTerminal +"' order by b.id";
        }else{
            query = "select b from PlanCuota b  where b.codigoTerminal = '" + aboTerminal +"' and b.estatus in (" + estatus +") order by b.id";
        }
         return em.createQuery(query).getResultList();
       // return em.createQuery("select b.id as id, b.codigoTerminal as terminal, c.nombre as nombreplan from PlanCuota b  , Plan c where b.idplan =  c.id and b.codigoTerminal = " + id + " order by 1").getResultList();
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")    
    public List<PlanCuota> getPlanCuotaByEstatusList(String estatus, String tipoPlan) {
        
        String query = "";
        if(!estatus.equals("") && !tipoPlan.equals("")){
            //cuotas con estatus definidos separados por coma
            query = "select b from PlanCuota b join PlanPago c on b.planPagoId = c.id join Plan d on d.id = c.idplan where b.estatus in (" + estatus +") and d.codtipoplan in (" + tipoPlan +") order by b.id asc";
            return em.createQuery(query).getResultList();
        }else{
            return null;
        }
        
       // return em.createQuery("select b.id as id, b.codigoTerminal as terminal, c.nombre as nombreplan from PlanCuota b  , Plan c where b.idplan =  c.id and b.codigoTerminal = " + id + " order by 1").getResultList();
    }
    
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")    
    public List<PlanCuota> getPlanCuotaByPlanPagoList(int id) {
         return em.createQuery("select b from PlanCuota b where b.planPagoId = " + Integer.toString(id) +" order by b.id").getResultList();
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public Integer getPlanCuotaDummy(String codigoTerminal) {
        try{
            String query = "SELECT planCuotaId as planCuotaId "
                     + "FROM dbo.PlanCuota pp "
                     + "WHERE pp.Estatusid = 23 AND pp.planId = 0 AND pp.aboTerminal = '" + codigoTerminal + "'";
            return (Integer) em.createNativeQuery(query).getSingleResult();
        } catch(NoResultException e) {
            return null;
          }
    }
    
    @Transactional(readOnly = false)
    public void savePlanCuota(PlanCuota planCuota) {
        em.persist(planCuota);
    }
    
    @Transactional(readOnly = false)
    public void updatePlanCuota(PlanCuota planCuota) {
        em.merge(planCuota);
    }
    
    @Transactional(readOnly = false)
    public void removePlanCuota(PlanCuota planCuota) {
        PlanCuota planCuotaToBeRemoved = em.getReference(PlanCuota.class, planCuota.getId());
        em.remove(planCuotaToBeRemoved);
    }
}
