package com.tranred.milpagosapp.service;

import com.tranred.milpagosapp.domain.PlanCuotaSP;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Interface utilizada para el manejo de Comercios 
 * mggy@sistemasemsys.com
 * @version 0.1
 */
public interface ICobranzaService extends Serializable{
    
    public List<PlanCuotaSP> getCobranzasList(String mes, 
                                            String ano, 
                                            String estatus, 
                                            String tipoPlan, 
                                            String tipoConsulta,
                                            String tipoConsultaIndividual,
                                            String tipoIdentificacionComercio,
                                            String identificacionComercio,
                                            String terminal,
                                            String tipoIdentificacionACI,
                                            String identificacionACI,
                                            String tipoReporte);
    
    public void updateStatusCuotas(String planPagoId, String estatus, String fecha);    
}
