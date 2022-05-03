package com.tranred.milpagosapp.repository;

import com.tranred.milpagosapp.domain.PlanCuotaSP;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

/**
 * Interface de acceso a datos a la tabla Historico
 * mggy@sistemasemsys.com
 * @version 0.1
 */
public interface ICobranzasDAO {
    
    public List<PlanCuotaSP> getCobranzasComercios(String mes, 
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
    
    public BigDecimal getMontoCuotasPagadas(String id, Integer estatus, Integer idPlanPago) ;
    
    public BigDecimal getMontoPagadasTipoPlan(String terminal, Date fecha, Integer codTipoPlan);
    
    public void updateStatusCuotas(String planPagoId, String estatus, String fecha);
}
