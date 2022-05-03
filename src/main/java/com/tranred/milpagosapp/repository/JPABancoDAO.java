package com.tranred.milpagosapp.repository;

import com.tranred.milpagosapp.domain.Banco;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase para el acceso a datos de la tabla Bancos
 * mggy@sistemasemsys.com
 * @version 0.1
 */

@Repository(value = "BancoDao")
public class JPABancoDAO {
    
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
    public Banco getById(String id) {        
        return em.find(Banco.class, id);                
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")    
    public List<Banco> getBancoList() {
         return em.createQuery("select b from Banco b order by b.codigo").getResultList();
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")    
    public List<Banco> getCodigoSwiftList(String codigo) {
         return em.createQuery("select b from Banco b where codigo = '" + codigo + "' order by b.codigo").getResultList();
    }
    
    @Transactional(readOnly = false)
    public void saveBanco(Banco banco) {
        em.persist(banco);
    }
    
    @Transactional(readOnly = false)
    public void updateBanco(Banco banco) {
        em.merge(banco);
    }
    
    @Transactional(readOnly = false)
    public void removeBanco(Banco banco) {
        Banco bancoToBeRemoved = em.getReference(Banco.class, banco.getCodigo());
        em.remove(bancoToBeRemoved);
    }
}
