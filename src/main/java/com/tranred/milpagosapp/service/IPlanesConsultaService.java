package com.tranred.milpagosapp.service;

import com.tranred.milpagosapp.domain.PlanesConsulta;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Interface utilizada para el manejo de reporte de planes por clientes 
 * mcaraballo@emsys-solutions.net
 * @version 0.1
 */
public interface IPlanesConsultaService extends Serializable{
    
    public List<PlanesConsulta> getPlanesConsultaList(String tipoReporte,
                                            String estatus, 
                                            String tipoPlan, 
                                            String tipoConsulta,
                                            String tipoConsultaIndividual,
                                            String tipoIdentificacionComercio,
                                            String identificacionComercio,
                                            String terminal);
    
        
}
