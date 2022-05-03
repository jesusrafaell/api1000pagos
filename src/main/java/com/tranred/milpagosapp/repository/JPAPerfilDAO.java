package com.tranred.milpagosapp.repository;

import com.tranred.milpagosapp.domain.Perfil;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase que implementa la Interface IPerfilDAO
 * mggy@sistemasemsys.com
 * @version 0.1
 */

@Repository(value = "PerfilDao")
public class JPAPerfilDAO implements IPerfilDAO{

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
    public Perfil getById(int id) {        
        return em.find(Perfil.class, id);                
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")    
    public List<Perfil> getPerfilesList() {
        return em.createNativeQuery("select p.id, p.nombre, p.opciones, estatus = case when p.estatus = 0 then 'Inactivo' else 'Activo' end from Perfiles p order by p.id",Perfil.class).getResultList();
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")    
    public List getPerfilesActivosList() {
        return em.createNativeQuery("select p.id from Perfiles p inner join Usuarios u on p.id=u.perfilId group by p.id").getResultList();
    }
    
    @Transactional(readOnly = false)
    public void savePerfil(Perfil perfil) {
        em.persist(perfil);
    }
    
    @Transactional(readOnly = false)
    public void updatePerfil(Perfil perfil) {
        em.merge(perfil);
    }
    
    @Transactional(readOnly = false)
    public void removePerfil(Perfil perfil) {
        Perfil perfilToBeRemoved = em.getReference(Perfil.class, perfil.getId());
        em.remove(perfilToBeRemoved);
    }
}
