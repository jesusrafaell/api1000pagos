package com.tranred.milpagosapp.service;

import com.tranred.milpagosapp.domain.Reclamo;
import com.tranred.milpagosapp.domain.TipoReclamo;
import com.tranred.milpagosapp.repository.IReclamoDAO;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Clase que implementa la Interface IReclamoService
 * mggy@sistemasemsys.com
 * @version 0.1
 */

@Component
public class ReclamosService implements IReclamoService {
    
    private static final long serialVersionUID = 1L;
    
    @Autowired
    private IReclamoDAO reclamoDao;        
    
    public Reclamo getById(int id) {
        return reclamoDao.getById(id);
    }        
    
    public void saveReclamo(Reclamo reclamo) {
        reclamoDao.saveReclamo(reclamo);
    }
    
    public void updateReclamo(Reclamo reclamo) {
        reclamoDao.updateReclamo(reclamo);
    }
    
    public List<TipoReclamo> getTipoReclamosList() {
        return reclamoDao.getTipoReclamosList();
    }

    public void saveTipoReclamo(TipoReclamo tipoReclamo) {
        reclamoDao.saveTipoReclamo(tipoReclamo);
    }
    
    public void updateTipoReclamo(TipoReclamo tipoReclamo) {
        reclamoDao.updateTipoReclamo(tipoReclamo);
    }
    
    public int getNumeroReclamo() {
        return reclamoDao.getNumeroReclamo();
    }
    
    public List<Reclamo> getReclamosList(Timestamp fechaDesde, Timestamp fechaHasta) {
        return reclamoDao.getReclamosList(fechaDesde, fechaHasta);
    }

    public List<Reclamo> getReclamosListByTipo(Timestamp fechaDesde, Timestamp fechaHasta, String tipo) {
        return reclamoDao.getReclamosListByTipo(fechaDesde, fechaHasta, tipo);
    }
        
}
