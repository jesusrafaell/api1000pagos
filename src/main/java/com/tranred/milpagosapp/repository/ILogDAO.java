package com.tranred.milpagosapp.repository;

import com.tranred.milpagosapp.domain.Logs;
import com.tranred.milpagosapp.domain.LogsWeb;
import java.sql.Timestamp;
import java.util.List;

/**
 * Interface de acceso a datos del log de auditoria
 * mggy@sistemasemsys.com
 * @version 0.1
 */
public interface ILogDAO {
    
    public List<Logs> getLogList(Timestamp fechaDesde,Timestamp fechaHasta,int modulo,String opcion);
    
    public List<LogsWeb> getLogWebList(Timestamp fechaDesde,Timestamp fechaHasta,String opcion);
    
    public List<Logs> getLogsListByUsuario(Timestamp fechaDesde,Timestamp fechaHasta,int modulo, String opcion, String usuario);
    
    public List<LogsWeb> getLogsWebListByUsuario(Timestamp fechaDesde,Timestamp fechaHasta, String opcion, String usuario);
            
    public void saveLog(Logs log);
    
}
