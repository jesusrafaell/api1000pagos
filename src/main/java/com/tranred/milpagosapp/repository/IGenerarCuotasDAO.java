package com.tranred.milpagosapp.repository;

import com.tranred.milpagosapp.domain.GenerarCuotas;
import java.sql.Date;
import java.util.List;

/**
 * Interface de acceso a datos a la tabla Historico
 * mggy@sistemasemsys.com
 * @version 0.1
 */
public interface IGenerarCuotasDAO {
    
    public List<GenerarCuotas> setCuotasComercios(String tipo, 
                                            String terminal, 
                                            String inicioMes, 
                                            String mes, 
                                            String anio);
    
}
