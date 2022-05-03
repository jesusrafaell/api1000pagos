package com.tranred.milpagosapp.service;

import com.tranred.milpagosapp.domain.PlanCuotaSP;
import com.tranred.milpagosapp.repository.ICobranzasDAO;
import java.sql.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Clase que implementa la Interface IComercioService
 * mggy@sistemasemsys.com
 * @version 0.1
 */
@Component
public class CobranzasService implements ICobranzaService{
    
    private static final long serialVersionUID = 1L;
    
    @Autowired
    private ICobranzasDAO cobranzasDao;
    
    @Override
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
                                            String tipoReporte) {
        return cobranzasDao.getCobranzasComercios( mes, 
                                             ano, 
                                             estatus, 
                                             tipoPlan, 
                                             tipoConsulta,
                                             tipoConsultaIndividual,
                                             tipoIdentificacionComercio,
                                             identificacionComercio,
                                             terminal,
                                             tipoIdentificacionACI,
                                             identificacionACI,
                                             tipoReporte);
    }
    
    public void updateStatusCuotas(String planPagoId, String estatus, String fecha){
        cobranzasDao.updateStatusCuotas(planPagoId, estatus, fecha);
    }

}
