package com.tranred.milpagosapp.service;

import com.tranred.milpagosapp.domain.LoteDetalle;
import com.tranred.milpagosapp.domain.Lotes;
import com.tranred.milpagosapp.repository.JPALoteDetalleDAO;
import com.tranred.milpagosapp.repository.JPALotesDAO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Clase que implementa la Interface IGenerarLoteService
 * mggy@sistemasemsys.com
 * @version 0.1
 */
@Component
public class GenerarLoteService implements IGenerarLoteService{
    
    private static final long serialVersionUID = 1L;
    
    @Autowired
    private JPALotesDAO lotesDao;
    
    @Autowired
    private JPALoteDetalleDAO loteDetalleDao;
    
    public List<Lotes> getLoteList(String compania) {
        return lotesDao.getLoteList(compania);
    }   

    public void saveLote(Lotes lote) {
        lotesDao.saveLote(lote);
    }

    public Lotes getByIdLote(int id) {
        return lotesDao.getByIdLote(id);
    }
    
    public Object getNumeroLote(String compania, String fecha){
        return lotesDao.getNumeroLote(compania,fecha);
    }
    
    public List<LoteDetalle> getLoteDetalleList(String compania) {
        return loteDetalleDao.getLoteDetalleList(compania);
    }

    public LoteDetalle getByIdLoteDetalle(int id) {
        return loteDetalleDao.getByIdLoteDetalle(id);
    }

    public void saveLoteDetalle(LoteDetalle detalle) {
        loteDetalleDao.saveLoteDetalle(detalle);
    }
    
}
