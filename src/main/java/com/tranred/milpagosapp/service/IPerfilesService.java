package com.tranred.milpagosapp.service;

import com.tranred.milpagosapp.domain.Opcion;
import com.tranred.milpagosapp.domain.Perfil;
import java.io.Serializable;
import java.util.List;

/**
 * Interface utilizada para el manejo de la opcion Perfiles en el módulo
 * de administración de la app
 * mggy@sistemasemsys.com
 * @version 0.1
 */
public interface IPerfilesService extends Serializable{
    
    public Perfil getById(int id);
            
    public List<Perfil> getPerfilesList();
    
    public List getPerfilesActivosList();
            
    public List<Opcion> getOpcionByModuloList(String modulo);
    
    public void savePerfil(Perfil perfil);
    
    public void updatePerfil(Perfil perfil);
    
    public void removePerfil(Perfil perfil);
    
}
