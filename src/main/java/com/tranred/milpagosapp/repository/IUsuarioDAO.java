package com.tranred.milpagosapp.repository;

import com.tranred.milpagosapp.domain.Usuario;
import com.tranred.milpagosapp.domain.UsuarioWeb;
import java.util.List;

/**
 * Interface de acceso a datos de un usuario
 * mggy@sistemasemsys.com
 * @version 0.1
 */
public interface IUsuarioDAO {
    
    public Usuario getById(int id);
    
    public List<Usuario> getUsuariosList();
    
    public List<Usuario> getByLogin(String login);
    
    public List<Usuario> getByperfil(String perfil);
    
    public List<Usuario> getByIdentificacion(String tipo, String identificacion);
    
    public List<UsuarioWeb> getUsuarioWebByIdentificacion(String tipo, String identificacion);

    public void saveUsuario(Usuario usuario);
    
    public void updateUsuario(Usuario usuario);
    
    public void updateUsuarioWeb(UsuarioWeb usuarioWeb);
    
    public void removeUsuario(Usuario usuario);                
    
    public List<Usuario> validaUsuario(String login, String contrasena);
    
    public void updateUsuarioSesion(int id, int sesion);
    
    public void updateUsuarioIntentoFallido(int id, int intento);
    
    public void updateUsuarioBloqueado(int id);
}
