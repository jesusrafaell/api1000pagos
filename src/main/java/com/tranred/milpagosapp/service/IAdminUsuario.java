package com.tranred.milpagosapp.service;

import java.io.Serializable;
import java.util.List;
import com.tranred.milpagosapp.domain.Usuario;
import com.tranred.milpagosapp.domain.UsuarioWeb;

/**
 * Interface utilizada para el manejo de usuarios
 * mggy@sistemasemsys.com
 * @version 0.1
 */
public interface IAdminUsuario extends Serializable {        
    
    public Usuario getById(int id);
    
    public void saveUsuario(Usuario usuario);
    
    public void updateUsuario(Usuario usuario);
    
    public void updateUsuarioWeb(UsuarioWeb usuarioWeb);
    
    public void removeUsuario(Usuario usuario);
            
    public List<Usuario> getUsuarios();
    
    public List<Usuario> getByLogin(String login);
    
    public List<Usuario> getByperfil(String perfil);
    
    public List<Usuario> getByIdentificacion(String tipo, String identificacion);
    
    public List<UsuarioWeb> getUsuarioWebByIdentificacion(String tipo, String identificacion);
    
    public List<Usuario> validaUsuario(String login, String contrasena);             
    
    public void updateUsuarioSession(int id, int sesion);
    
    public void updateUsuarioIntentoFallido(int id, int intento);
    
    public void updateUsuarioBloqueado(int id);
}
