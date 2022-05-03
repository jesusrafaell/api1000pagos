/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tranred.milpagosapp.repository;

import com.tranred.milpagosapp.domain.Moneda;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase para el acceso a datos de la tabla Moneda
 * jperez@sistemasemsys.com
 * @version 0.1
 */



@Repository(value = "MonedaDAO")
public class JPAMonedaDAO   {
    
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
    public List<Moneda> getMonedaList() {
         return em.createQuery("select tm from Moneda tm order by tm.descripcion asc").getResultList();
    }
       
}
