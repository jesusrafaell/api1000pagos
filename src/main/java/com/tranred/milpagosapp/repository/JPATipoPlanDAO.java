/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tranred.milpagosapp.repository;

import com.tranred.milpagosapp.domain.TipoPlan;
import com.tranred.milpagosapp.domain.Estatus;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase para el acceso a datos de la tabla TipoPlan
 * jperez@sistemasemsys.com
 * @version 0.1
 */



@Repository(value = "TipoPlanDao")
public class JPATipoPlanDAO  {
    
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
    public List<TipoPlan> getTipoPlanList() {
         return em.createQuery("select tp from TipoPlan tp where tp.estatus in (select s.id from Estatus s where s.modulo = 'pla' and s.descripcion = 'Activo') order by tp.descripcion asc").getResultList();
    }
       
}
