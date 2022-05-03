package com.tranred.milpagosapp.service;

import com.tranred.milpagosapp.domain.Parametro;
import com.tranred.milpagosapp.repository.JPAParametroDAO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Clase para el manejo de Parametros
 * mggy@sistemasemsys.com
 * @version 0.1
 */
@Component
public class ParametroService {
    
    private static final long serialVersionUID = 1L;
    
    @Autowired
    private JPAParametroDAO parametroDao;
    
    public List<Parametro> getParametroList() {
        return parametroDao.getParametroList();
    }
    
    public void updateParametro(Parametro parametro){
        parametroDao.updateParametro(parametro); 
    }
    
    public Parametro getParametroById(int id) {
        return parametroDao.getById(id);
    }
    
}
