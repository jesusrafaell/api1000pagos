package com.tranred.milpagosapp.repository;

import com.tranred.milpagosapp.domain.PlanesConsulta;
import com.tranred.milpagosapp.domain.PlanPago;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase que implementa la Interface IHistoricoDAO
 * mggy@sistemasemsys.com
 * @version 0.1
 */
@Repository(value = "PlanesConsultaDao")
public class JPAPlanesConsultaDAO implements IPlanesConsultaDAO{
    
    private EntityManager em = null;

    /*
     * Sets the entity manager.
     */
    @PersistenceContext
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked") 
    @Override
    public List<PlanesConsulta> getPlanesConsultaComercios(String tipoReporte, 
                                                 String estatus, 
                                                 String tipoPlan, 
                                                 String tipoConsulta,
                                                 String tipoConsultaIndividual,
                                                 String tipoIdentificacionComercio,
                                                 String identificacionComercio,
                                                 String terminal) {
        Query query = em.createNamedQuery("consultaPlanes");
        query.setParameter("tipoReporte", tipoReporte);
        query.setParameter("estatus", estatus);
        query.setParameter("tipoPlan", tipoPlan);
        query.setParameter("tipoConsulta", tipoConsulta);
        query.setParameter("tipoConsultaIndividual", tipoConsultaIndividual);
        query.setParameter("tipoIdentificacionComercio", tipoIdentificacionComercio);
        query.setParameter("identificacionComercio", identificacionComercio);
        query.setParameter("terminal", terminal);
        return query.getResultList();        
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")    
    @Override
    public BigDecimal getMontoCuotasPagadas(Integer id, Integer estatus, Integer idPlanPago) {
        
        String query = "SELECT SUM(c.montoTotal) as montoPagado "
                     + "FROM dbo.PlanCuota c "
                     + "JOIN dbo.PlanPago pp ON pp.planPagoId = c.planPagoId "
                     + "JOIN dbo.Planes p ON  p.planId = pp.planId "
                     + "WHERE c.estatusId = " + Integer.toString(estatus) + " AND p.codTipoPlan = 2 "
                     + "  AND c.aboTerminal = " + Integer.toString(id) 
                     + "  AND c.planPagoId  = " + Integer.toString(idPlanPago);
        
        return (BigDecimal) em.createNativeQuery(query).getSingleResult();
    }
}
