package com.tranred.milpagosapp.repository;

import com.tranred.milpagosapp.domain.Reclamo;
import com.tranred.milpagosapp.domain.TipoReclamo;
import java.sql.Timestamp;
import java.util.List;

/**
 * Interface de acceso a datos de un Reclamo
 * mggy@sistemasemsys.com
 * @version 0.1
 */
public interface IReclamoDAO {
    
    public Reclamo getById(int id);
    
    public List<Reclamo> getReclamosList(Timestamp fechaDesde,Timestamp fechaHasta);
    
    public List<Reclamo> getReclamosListByTipo(Timestamp fechaDesde,Timestamp fechaHasta,String tipo);
       
    public void saveReclamo(Reclamo reclamo);
    
    public void updateReclamo(Reclamo reclamo);
    
    public List<TipoReclamo> getTipoReclamosList();
    
    public void saveTipoReclamo(TipoReclamo tipoReclamo);
    
    public void updateTipoReclamo(TipoReclamo tipoReclamo);
    
    public int getNumeroReclamo();
    
}
