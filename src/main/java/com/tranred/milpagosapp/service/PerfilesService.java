package com.tranred.milpagosapp.service;

import com.tranred.milpagosapp.domain.Opcion;
import com.tranred.milpagosapp.domain.Perfil;
import com.tranred.milpagosapp.repository.IPerfilDAO;
import com.tranred.milpagosapp.repository.JPAOpcionDAO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Clase que implementa la Interface IPerfilesService
 * mggy@sistemasemsys.com
 * @version 0.1
 */
@Component
public class PerfilesService implements IPerfilesService{
    
    private static final long serialVersionUID = 1L;
    
    @Autowired
    private IPerfilDAO perfilDao;
    
    @Autowired
    private JPAOpcionDAO opcionDao;
    
    public Perfil getById(int id) {
        return perfilDao.getById(id);
    }
    
    public List<Perfil> getPerfilesList() {
        return perfilDao.getPerfilesList();
    }
    
    public List<Opcion> getOpcionByModuloList(String modulo){
        return opcionDao.getOpcionByModuloList(modulo);
    }
    
    public void savePerfil(Perfil perfil){
        perfilDao.savePerfil(perfil);
    }   
    
    public void updatePerfil(Perfil perfil) {
        perfilDao.updatePerfil(perfil);
    }
     
    public void removePerfil(Perfil perfil){
        perfilDao.removePerfil(perfil);
    }

    public List getPerfilesActivosList() {
        return perfilDao.getPerfilesActivosList();
    }
   
}
