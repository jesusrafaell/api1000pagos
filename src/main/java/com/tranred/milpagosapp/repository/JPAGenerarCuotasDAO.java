/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tranred.milpagosapp.repository;

import com.tranred.milpagosapp.domain.GenerarCuotas;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase que implementa la Interface IGenerarCuotasDAO
 * mggy@sistemasemsys.com
 * @version 0.1
 */
@Repository(value = "GenerarCuotasDao")
public class JPAGenerarCuotasDAO implements IGenerarCuotasDAO{
    
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
    @Override
    public List<GenerarCuotas> setCuotasComercios(String tipo, 
                                            String terminal, 
                                            String inicioMes, 
                                            String mes, 
                                            String anio) {
        Query query = em.createNamedQuery("generarCuotas");
        query.setParameter("tipo", tipo);
        query.setParameter("terminal", terminal);
        query.setParameter("inicioMes", inicioMes);
        query.setParameter("mes", mes);
        query.setParameter("anio", anio);
        return query.getResultList();        
    }
    
}
