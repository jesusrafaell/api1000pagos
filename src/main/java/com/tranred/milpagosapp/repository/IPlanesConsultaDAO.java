package com.tranred.milpagosapp.repository;

import com.tranred.milpagosapp.domain.PlanesConsulta;
import java.math.BigDecimal;
import java.util.List;

/**
 * Interface de acceso a datos a la tabla Historico
 * mcaraballo@emsys-solutions.net
 * @version 0.1
 */
public interface IPlanesConsultaDAO {
    
    public List<PlanesConsulta> getPlanesConsultaComercios(String tipoReporte, 
                                                 String estatus, 
                                                 String tipoPlan, 
                                                 String tipoConsulta,
                                                 String tipoConsultaIndividual,
                                                 String tipoIdentificacionComercio,
                                                 String identificacionComercio,
                                                 String terminal);
    
    public BigDecimal getMontoCuotasPagadas(Integer id, Integer estatus, Integer idPlanPago) ;
    
}
