package com.tranred.milpagosapp.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.tranred.milpagosapp.domain.Usuario;
import com.tranred.milpagosapp.domain.UsuarioWeb;

/**
 * Clase que implementa la Interface IUsuarioDAO
 * mggy@sistemasemsys.com
 * @version 0.1
 */
@Repository(value = "usuarioDao")
public class JPAUsuarioDAO implements IUsuarioDAO {
    
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
    public Usuario getById(int id) {
        return em.find(Usuario.class, id);
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public List<Usuario> getByLogin(String login) {
        return em.createQuery("select u from Usuario u where u.login = '"+ login +"'").getResultList();
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public List<Usuario> getByperfil(String perfil) {
        return em.createQuery("select u from Usuario u where u.perfil = '"+ perfil +"'").getResultList();
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public List<Usuario> getByIdentificacion(String tipo, String identificacion) {
        return em.createQuery("select u from Usuario u where u.tipoIdentificacion = '"+ tipo +"' and u.identificacion = '"+ identificacion +"'").getResultList();
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public List<UsuarioWeb> getUsuarioWebByIdentificacion(String tipo, String identificacion) {
        return em.createQuery("select uw from UsuarioWeb uw where uw.tipoIdentificacion = '"+ tipo +"' and uw.identificacion = '"+ identificacion +"'").getResultList();
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")    
    public List<Usuario> getUsuariosList() {
        return em.createQuery("select u from Usuario u order by u.id").getResultList();
    }
    
    @Transactional(readOnly = false)
    public void saveUsuario(Usuario usuario) {
        em.persist(usuario);
    }
    
    @Transactional(readOnly = false)
    public void updateUsuario(Usuario usuario) {
        em.merge(usuario);
    }
    
    @Transactional(readOnly = false)
    public void updateUsuarioWeb(UsuarioWeb usuarioWeb) {
        em.merge(usuarioWeb);
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public void updateUsuarioSesion(int id, int sesion) {
        em.createNativeQuery("update Usuarios set ultimoAcceso = GETDATE(), inicioSesion = '"+ sesion +"' where Id = '"+ id +"'").executeUpdate();
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public void updateUsuarioIntentoFallido(int id, int intento) {
        em.createNativeQuery("update Usuarios set intentosFallidos = '"+ intento +"' where Id = '"+ id +"'").executeUpdate();
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public void updateUsuarioBloqueado(int id) {
        em.createNativeQuery("update Usuarios set estatus = 8 where Id = '"+ id +"'").executeUpdate();
    }
    
    @Transactional(readOnly = false)
    public void removeUsuario(Usuario usuario) {      
        Usuario usuarioToBeRemoved = em.getReference(Usuario.class, usuario.getId());
        em.remove(usuarioToBeRemoved);               
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked") 
    public List<Usuario> validaUsuario(String login, String contrasena) {
        
        return em.createQuery("select u from Usuario u where login='" + login + "' AND  contrasena='" + contrasena + "'").getResultList();
    }        
    
}
