package com.tranred.milpagosapp.repository;

import com.tranred.milpagosapp.domain.Plan;
import com.tranred.milpagosapp.domain.Estatus;
import com.tranred.milpagosapp.domain.Planes;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase para el acceso a datos de la tabla Planes
 * jperez@emsys-solutions.net
 * @version 0.1
 */

@Repository(value = "PlanDao")
public class JPAPlanDAO {
    
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
    public Plan getById(int id) {        
        return em.find(Plan.class, id);                
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")    
    public List<Plan> getPlanList() {
          return em.createQuery("select b from Plan b order by b.id").getResultList();
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")    
    public List<Planes> getInformationPlanList(int estatus) {
        String sql;
        switch (estatus) {
            case 0: //Activos
                sql = "select b from Planes b where b.estatus in (select s.id from Estatus s where s.modulo = 'pla' and s.descripcion = 'Activo') and b.id > 0 order by b.id";
                break;
            case 1: // Inactivos
                sql = "select b from Planes b where b.estatus in (select s.id from Estatus s where s.modulo = 'pla' and s.descripcion = 'Inactivo') and b.id > 0  order by b.id";
                break;
            default://Todos
                sql = "select b from Planes b where b.id > 0  order by b.id";
                break;
        }
        return em.createQuery(sql).getResultList();
    }
    
    @Transactional(readOnly = false)
    public void savePlan(Plan plan) {
        em.persist(plan);
    }
    
    @Transactional(readOnly = false)
    public void updatePlan(Plan plan) {
        em.merge(plan);
    }
    
    @Transactional(readOnly = false)
    public void removePlan(Plan plan) {
        Plan planToBeRemoved = em.getReference(Plan.class, plan.getId());
        em.remove(planToBeRemoved);
    }
}
