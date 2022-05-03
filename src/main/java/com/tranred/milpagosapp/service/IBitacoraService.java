package com.tranred.milpagosapp.service;

import com.tranred.milpagosapp.domain.Logs;
import com.tranred.milpagosapp.domain.LogsWeb;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * Interface utilizada para el manejo de la opcion bitacora en el módulo
 * de administración de la app
 * mggy@sistemasemsys.com
 * @version 0.1
 */
public interface IBitacoraService extends Serializable{
        
    public List<Logs> getLogsList(Timestamp fechaDesde,Timestamp fechaHasta,int modulo, String opcion);
    
    public List<LogsWeb> getLogWebList(Timestamp fechaDesde,Timestamp fechaHasta, String opcion);
    
    public List<Logs> getLogsListByUsuario(Timestamp fechaDesde,Timestamp fechaHasta,int modulo, String opcion, String usuario);
    
    public List<LogsWeb> getLogsWebListByUsuario(Timestamp fechaDesde,Timestamp fechaHasta, String opcion, String usuario);
    
    public void saveLogs(int usuarioId,int moduloId,int opcionId,String descripcion,Timestamp fecha,int error,String ip,String valueOld,String valueNew);
}
