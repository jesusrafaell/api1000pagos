package com.tranred.milpagosapp.service;

import com.tranred.milpagosapp.domain.LoteDetalle;
import com.tranred.milpagosapp.domain.Lotes;
import java.io.Serializable;
import java.util.List;

/**
 * Interface utilizada para el manejo de la opcion Generar Lotes de Pagos
 * mggy@sistemasemsys.com
 * @version 0.1
 */
public interface IGenerarLoteService extends Serializable{
    
    public List<Lotes> getLoteList(String compania);
    
    public Lotes getByIdLote(int id);
    
    public void saveLote(Lotes lote);
    
    public List<LoteDetalle> getLoteDetalleList(String compania);
    
    public LoteDetalle getByIdLoteDetalle(int id);
    
    public void saveLoteDetalle(LoteDetalle detalle);
    
    public Object getNumeroLote(String compania, String fecha);
    
}
