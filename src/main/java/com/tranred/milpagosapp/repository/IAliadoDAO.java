package com.tranred.milpagosapp.repository;

import com.tranred.milpagosapp.domain.Aliado;
import com.tranred.milpagosapp.domain.Estadistica;
import com.tranred.milpagosapp.domain.TipoPagoAliado;
import java.util.List;
import org.jfree.data.general.PieDataset;

/**
 * Interface de acceso a datos de los Aliados Comerciales
 * mggy@sistemasemsys.com
 * @version 0.1
 */
public interface IAliadoDAO {
    
    public Aliado getById(int id);
    
    public List<Aliado> getAliadoByIdUsuario(int idUsuario);
            
    public List<Aliado> getAliadoList();
    
    public List<Aliado> getAliadoByIdentificacion(String tipo, String identificacion);
    
    public List<Aliado> getAliadosByZonaAtencion(String zonaAtencion);
    
    public List<Estadistica> getTipoEstadistica();
    
    public List<TipoPagoAliado> getTipoPagoAliado();
            
    public void saveAliado(Aliado aliado);
    
    public void updateAliado(Aliado aliado);
    
    public void updateAliadoIdUsuario(String idAliado, String idUsuario);
    
    public PieDataset crearDataset();
}
