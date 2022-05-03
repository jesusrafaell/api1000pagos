package com.tranred.milpagosapp.service;

import com.tranred.milpagosapp.domain.PlanesConsulta;
import com.tranred.milpagosapp.repository.IPlanesConsultaDAO;
import java.sql.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Clase que implementa la Interface IComercioService
 * mcaraballo@emsys-solutions.net
 * @version 0.1
 */
@Component
public class PlanesConsultaService implements IPlanesConsultaService{
    
    private static final long serialVersionUID = 1L;
    
    @Autowired
    private IPlanesConsultaDAO planesConsultaDao;
    
    @Override
    public List<PlanesConsulta> getPlanesConsultaList(String tipoReporte, 
                                            String estatus, 
                                            String tipoPlan, 
                                            String tipoConsulta,
                                            String tipoConsultaIndividual,
                                            String tipoIdentificacionComercio,
                                            String identificacionComercio,
                                            String terminal) {
        return planesConsultaDao.getPlanesConsultaComercios( tipoReporte,
                                             estatus, 
                                             tipoPlan, 
                                             tipoConsulta,
                                             tipoConsultaIndividual,
                                             tipoIdentificacionComercio,
                                             identificacionComercio,
                                             terminal);
    }

}
