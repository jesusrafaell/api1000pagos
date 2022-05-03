package com.tranred.milpagosapp.service;

import com.tranred.milpagosapp.domain.Logs;
import com.tranred.milpagosapp.domain.LogsWeb;
import com.tranred.milpagosapp.repository.ILogDAO;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Clase que implementa la Interface IBitacoraService
 * mggy@sistemasemsys.com
 * @version 0.1
 */
@Component
public class BitacoraService implements IBitacoraService{
    
    private static final long serialVersionUID = 1L;
    
    @Autowired
    private ILogDAO logDao;
    
    public List<Logs> getLogsList(Timestamp fechaDesde,Timestamp fechaHasta,int modulo, String opcion) {
        return logDao.getLogList(fechaDesde,fechaHasta,modulo, opcion);
    }
    
    public List<LogsWeb> getLogWebList(Timestamp fechaDesde,Timestamp fechaHasta, String opcion){
        return logDao.getLogWebList(fechaDesde, fechaHasta, opcion);
    }
    
    public List<Logs> getLogsListByUsuario(Timestamp fechaDesde,Timestamp fechaHasta,int modulo, String opcion, String usuario){
        return logDao.getLogsListByUsuario(fechaDesde,fechaHasta,modulo, opcion, usuario);
    }
    
    public List<LogsWeb> getLogsWebListByUsuario(Timestamp fechaDesde,Timestamp fechaHasta, String opcion, String usuario){
        return logDao.getLogsWebListByUsuario(fechaDesde, fechaHasta, opcion, usuario);
    }
    
    public void saveLogs(int usuarioId,int moduloId,int opcionId,String descripcion,Timestamp fecha,int error,String ip,String valueOld,String valueNew) {
        
        Logs log = new Logs();
        log.setUsuarioId(usuarioId);
        log.setModuloId(moduloId);
        log.setOpcionId(opcionId);
        log.setDescripcion(descripcion);
        log.setFecha(fecha);
        log.setError(error);
        log.setIp(ip);
        log.setValoresOld(valueOld);
        log.setValoresNew(valueNew);
        
        logDao.saveLog(log);
        
    }  
     
}
