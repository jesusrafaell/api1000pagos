package com.tranred.milpagosapp.repository;

import com.tranred.milpagosapp.domain.Historico;
import com.tranred.milpagosapp.domain.HistoricoAliado;
import com.tranred.milpagosapp.domain.HistoricoEdoCuenta;
import com.tranred.milpagosapp.domain.HistoricoFacturacion;
import java.sql.Date;
import java.util.List;

/**
 * Interface de acceso a datos a la tabla Historico
 * mggy@sistemasemsys.com
 * @version 0.1
 */
public interface IHistoricoDAO {
    
    public List<Historico> getHistoricoPagoComercio(Date fecha, String codigoAfiliado);
    
    public List<HistoricoAliado> getHistoricoPagoAliado(Date fecha, String codigoAfiliado);
    
    public List<HistoricoEdoCuenta> getHistoricoEdoCuentaComercios(Date fecha, int codigoComercio, String terminal);
    
    public List<HistoricoFacturacion> getHistoricoFacturacionComercios(int tipoConsulta, int tipoContrato, Date fecha, int codigoComercio);
            
    public void saveHistorico(Historico registro);
    
}
