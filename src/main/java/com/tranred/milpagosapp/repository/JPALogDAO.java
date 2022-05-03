package com.tranred.milpagosapp.repository;

import com.tranred.milpagosapp.domain.Logs;
import com.tranred.milpagosapp.domain.LogsWeb;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase que implementa la Interface ILogDAO
 * mggy@sistemasemsys.com
 * @version 0.1
 */
@Repository(value = "LogDao")
public class JPALogDAO implements ILogDAO{

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
    public List<Logs> getLogList(Timestamp fechaDesde,Timestamp fechaHasta,int modulo, String opcion) {        
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");        
        return em.createQuery("select l from Logs l inner join l.usuario u inner join l.modulo m where l.moduloId = " + modulo + " and  l.opcionId like '" + opcion + "' and l.fecha BETWEEN '" + df.format(fechaDesde) + " 00:00:00.0" + "' and '" + df.format(fechaHasta) + " 23:59:59.999" + "' order by l.fecha desc").getResultList();
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked") 
    public List<LogsWeb> getLogWebList(Timestamp fechaDesde,Timestamp fechaHasta, String opcion) {        
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");        
        return em.createQuery("select lw from LogsWeb lw where lw.opcionWebId like '" + opcion + "' and lw.fechaLogWeb BETWEEN '" + df.format(fechaDesde) + " 00:00:00.0" + "' and '" + df.format(fechaHasta) + " 23:59:59.999" + "' order by lw.fechaLogWeb desc").getResultList();
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked") 
    public List<Logs> getLogsListByUsuario(Timestamp fechaDesde,Timestamp fechaHasta,int modulo, String opcion, String usuario) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        return em.createQuery("select l from Logs l inner join l.usuario u inner join l.modulo m where l.usuario.login = '" + usuario + "' and l.moduloId = " + modulo + " and  l.opcionId like '" + opcion + "' and l.fecha BETWEEN '" + df.format(fechaDesde) + " 00:00:00.0" + "' and '" + df.format(fechaHasta) + " 23:59:59.999" + "' order by l.fecha desc").getResultList();
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked") 
    public List<LogsWeb> getLogsWebListByUsuario(Timestamp fechaDesde,Timestamp fechaHasta, String opcion, String usuario) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        return em.createQuery("select lw from LogsWeb lw where lw.usuarioWeb.login = '" + usuario + "' and  lw.opcionWebId like '" + opcion + "' and lw.fechaLogWeb BETWEEN '" + df.format(fechaDesde) + " 00:00:00.0" + "' and '" + df.format(fechaHasta) + " 23:59:59.999" + "' order by lw.fechaLogWeb desc").getResultList();
    }
    
    @Transactional(readOnly = false)
    public void saveLog(Logs log) {
        em.persist(log);
    }    
    
}
