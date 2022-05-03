package com.tranred.milpagosapp.service;

import com.tranred.milpagosapp.domain.GenerarCuotas;
import com.tranred.milpagosapp.domain.PlanCuotaSP;
import java.io.Serializable;
import java.util.List;

/**
 * Interface utilizada para el manejo de Comercios 
 * mcaraballo@.com
 * @version 0.1
 */
public interface IGenerarCuotasService extends Serializable{
    
    public List<GenerarCuotas> setCuotasComerciosList(String tipo, 
                                            String terminal, 
                                            String inicioMes, 
                                            String mes, 
                                            String anio);

}
