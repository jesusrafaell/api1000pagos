package com.tranred.milpagosapp.repository;

import com.tranred.milpagosapp.domain.Perfil;
import java.util.List;

/**
 * Interface de acceso a datos de un Perfil
 * mggy@sistemasemsys.com
 * @version 0.1
 */
public interface IPerfilDAO{
    
     public Perfil getById(int id);
     
     public List<Perfil> getPerfilesList();
     
     public List getPerfilesActivosList();
     
     public void savePerfil(Perfil perfil);          
     
     public void updatePerfil(Perfil perfil);
     
     public void removePerfil(Perfil perfil);
}
