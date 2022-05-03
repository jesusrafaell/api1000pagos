package com.tranred.milpagosapp.service;

import com.tranred.milpagosapp.domain.GenerarCuotas;
import com.tranred.milpagosapp.repository.IGenerarCuotasDAO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Clase que implementa la Interface IComercioService
 * mggy@sistemasemsys.com
 * @version 0.1
 */
@Component
public class GenerarCuotasService implements IGenerarCuotasService{
    
    private static final long serialVersionUID = 1L;
    
    @Autowired
    private IGenerarCuotasDAO generarCuotasDao;
    
    public List<GenerarCuotas> setCuotasComerciosList(String tipo, 
                                            String terminal, 
                                            String inicioMes, 
                                            String mes, 
                                            String anio) {
        return generarCuotasDao.setCuotasComercios(tipo, 
                                            terminal, 
                                            inicioMes, 
                                            mes, 
                                            anio);
    }

}
