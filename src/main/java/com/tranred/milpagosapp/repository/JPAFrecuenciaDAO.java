/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tranred.milpagosapp.repository;

import com.tranred.milpagosapp.domain.Frecuencia;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase para el acceso a datos de la tabla Frecuencia
 * jperez@sistemasemsys.com
 * @version 0.1
 */



@Repository(value = "FrecuenciaDAO")
public class JPAFrecuenciaDAO   {
    
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
    public List<Frecuencia> getFrecuenciaList() {
         return em.createQuery("select tf from Frecuencia tf order by tf.descripcion asc").getResultList();
    }
       
}
