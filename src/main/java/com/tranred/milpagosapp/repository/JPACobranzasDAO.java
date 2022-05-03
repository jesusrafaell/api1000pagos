package com.tranred.milpagosapp.repository;

import com.tranred.milpagosapp.domain.PlanCuotaSP;
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
@Repository(value = "CobranzasDao")
public class JPACobranzasDAO implements ICobranzasDAO{
    
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
                                                 String tipoReporte) {
        Query query = em.createNamedQuery("consultaCobranzas");
        query.setParameter("mes", mes);
        query.setParameter("ano", ano);
        query.setParameter("estatus", estatus);
        query.setParameter("tipoPlan", tipoPlan);
        query.setParameter("tipoConsulta", tipoConsulta);
        query.setParameter("tipoConsultaIndividual", tipoConsultaIndividual);
        query.setParameter("tipoIdentificacionComercio", tipoIdentificacionComercio);
        query.setParameter("identificacionComercio", identificacionComercio);
        query.setParameter("terminal", terminal);
        query.setParameter("tipoIdentificacionACI", tipoIdentificacionACI);
        query.setParameter("identificacionACI", identificacionACI); 
        query.setParameter("tipoReporte", tipoReporte);        
        return query.getResultList();        
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")    
    @Override
    public BigDecimal getMontoCuotasPagadas(String id, Integer estatus, Integer idPlanPago) {
        
        String query = "SELECT SUM(c.montoTotal) as montoPagado "
                     + "FROM dbo.PlanCuota c "
                     + "JOIN dbo.PlanPago pp ON pp.PlanPagoId = c.PlanPagoId "
                     + "JOIN dbo.Planes p ON  p.PlanId = pp.PlanId "
                     + "WHERE c.Estatusid = " + Integer.toString(estatus) + " AND p.CodTipoPlan = 2 "
                     + "  AND c.aboTerminal = '" + id + "'"
                     + "  AND c.PlanPagoId  = " + Integer.toString(idPlanPago);
        
        return (BigDecimal) em.createNativeQuery(query).getSingleResult();
       // return em.createQuery("select b.id as id, b.codigoTerminal as terminal, c.nombre as nombreplan from PlanPago b  , Plan c where b.idplan =  c.id and b.codigoTerminal = " + id + " order by 1").getResultList();
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")    
    @Override
    public BigDecimal getMontoPagadasTipoPlan(String terminal, Date fecha, Integer codTipoPlan) {
        
        String query = "SELECT SUM(c.montoComision)+SUM(montoIVA) as montoPagado "
                     + "FROM dbo.PlanCuota c "
                     + "JOIN dbo.PlanPago pp ON pp.planPagoId = c.planPagoId "
                     + "JOIN dbo.Planes p ON  p.planId = pp.planId "
                     + "WHERE c.estatusId = 27 "
                     + "  AND p.codTipoPlan = " + Integer.toString(codTipoPlan)
                     + "  AND c.aboTerminal = '" + terminal + "'"
                     + "  AND DATEDIFF(MONTH,'" + fecha.toString()+ "',fechaPago) = 0 ";
        
        return (BigDecimal) em.createNativeQuery(query).getSingleResult();
    }
    
    @Transactional(readOnly = false)
    public void updateStatusCuotas(String planPagoId, String estatus, String fecha) {
        em.createNativeQuery("UPDATE PlanCuota SET estatusId = "+ estatus +" WHERE estatusId in(25) AND planPagoId="+ planPagoId +" AND DATEDIFF(DAY,'"+fecha+"',fechaProceso)>0").executeUpdate();
    }
}
