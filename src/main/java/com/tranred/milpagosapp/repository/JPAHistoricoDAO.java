package com.tranred.milpagosapp.repository;

import com.tranred.milpagosapp.domain.Historico;
import com.tranred.milpagosapp.domain.HistoricoAliado;
import com.tranred.milpagosapp.domain.HistoricoEdoCuenta;
import com.tranred.milpagosapp.domain.HistoricoFacturacion;
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
@Repository(value = "HistoricoDao")
public class JPAHistoricoDAO implements IHistoricoDAO{
    
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
    public List<Historico> getHistoricoPagoComercio(Date fecha, String codigoAfiliado) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        Query query = em.createNamedQuery("consultaHistorico");
        query.setParameter("fecha", df.format(fecha));
        query.setParameter("afiliado", codigoAfiliado);
        query.setParameter("tipoConsulta", 1);        
        return query.getResultList();        
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked") 
    public List<HistoricoAliado> getHistoricoPagoAliado(Date fecha, String codigoAfiliado) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        Query query = em.createNamedQuery("consultaHistoricoAliado");
        query.setParameter("fecha", df.format(fecha));
        query.setParameter("afiliado", codigoAfiliado);
        query.setParameter("tipoConsulta", 2);        
        return query.getResultList();        
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked") 
    public List<HistoricoEdoCuenta> getHistoricoEdoCuentaComercios(Date fecha, int codigoComercio, String terminal) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        Query query = em.createNamedQuery("consultaEdoCuenta");
        query.setParameter("fecha", df.format(fecha));
        query.setParameter("codigoComercio", codigoComercio);
        query.setParameter("terminal", terminal);        
        return query.getResultList();        
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked") 
    public List<HistoricoFacturacion> getHistoricoFacturacionComercios(int tipoConsulta, int tipoContrato, Date fecha, int codigoComercio) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        Query query = em.createNamedQuery("generaFacturacion");
        query.setParameter("tipoConsulta", tipoConsulta);        
        query.setParameter("fecha", df.format(fecha));
        query.setParameter("codigoComercio", codigoComercio);
        query.setParameter("tipoContrato", tipoContrato);
        return query.getResultList();        
    }
    
    @Transactional(readOnly = false)
    public void saveHistorico(Historico registro) {
        em.persist(registro);
    }
}
