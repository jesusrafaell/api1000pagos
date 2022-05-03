package com.tranred.milpagosapp.service;

import java.util.List;
import com.tranred.milpagosapp.domain.Usuario;
import com.tranred.milpagosapp.domain.UsuarioWeb;
import org.springframework.beans.factory.annotation.Autowired;
import com.tranred.milpagosapp.repository.IUsuarioDAO;
import org.springframework.stereotype.Component;

/**
 * Clase que implementa la Interface IAdminUsuario
 * mggy@sistemasemsys.com
 * @version 0.1
 */
@Component
public class AdminUsuario implements IAdminUsuario {
    
    private static final long serialVersionUID = 1L;
    
    @Autowired
    private IUsuarioDAO usuarioDao;    
    
    public Usuario getById(int id) {
        return usuarioDao.getById(id);
    }
    
    public List<Usuario> getUsuarios() {
        return usuarioDao.getUsuariosList();
    }
    
    public List<Usuario> getByLogin(String login) {
        return usuarioDao.getByLogin(login);
    }
    
    public List<Usuario> getByIdentificacion(String tipo, String identificacion){
        return usuarioDao.getByIdentificacion(tipo, identificacion);
    }
    
    public List<UsuarioWeb> getUsuarioWebByIdentificacion(String tipo, String identificacion){
        return usuarioDao.getUsuarioWebByIdentificacion(tipo, identificacion);
    }
    
    public void saveUsuario(Usuario usuario) {
        usuarioDao.saveUsuario(usuario);
    } 
    
    public void updateUsuario(Usuario usuario) {
        usuarioDao.updateUsuario(usuario);
    }
    
    public void updateUsuarioWeb(UsuarioWeb usuarioWeb) {
        usuarioDao.updateUsuarioWeb(usuarioWeb);
    }
    
    public void removeUsuario(Usuario usuario){
        usuarioDao.removeUsuario(usuario);
    }              
    
    public List<Usuario> validaUsuario(String login, String contrasena) {
        return usuarioDao.validaUsuario(login, contrasena);
    }      
    
    public List<Usuario> getByperfil(String perfil) {
        return usuarioDao.getByperfil(perfil);
    }
    
    public void updateUsuarioSession(int id, int sesion){
        usuarioDao.updateUsuarioSesion(id, sesion);
    }
    
    public void updateUsuarioIntentoFallido(int id, int intento){
        usuarioDao.updateUsuarioIntentoFallido(id, intento);
    }
    
    public void updateUsuarioBloqueado(int id){
        usuarioDao.updateUsuarioBloqueado(id);
    }
    
}
