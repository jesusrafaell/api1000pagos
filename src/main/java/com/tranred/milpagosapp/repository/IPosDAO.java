package com.tranred.milpagosapp.repository;

import com.tranred.milpagosapp.domain.ModalidadPos;
import com.tranred.milpagosapp.domain.TipoPos;
import java.util.List;

/**
 * Interface de acceso a datos de un ModalidadPos
 mggy@sistemasemsys.com
 * @version 0.1
 */
public interface IPosDAO {
    
    public ModalidadPos getModalidadPosById(int id);
    
    public List<ModalidadPos> getModalidadPosList();        

    public void saveModalidadPos(ModalidadPos pos);
    
    public void updateModalidadPos(ModalidadPos pos);
    
    public void removeModalidadPos(ModalidadPos pos);
        
    public TipoPos getTipoPosById(int id);
    
    public List<TipoPos> getTipoPosList();        

    public void saveTipoPos(TipoPos pos);
    
    public void updateTipoPos(TipoPos pos);
    
    public void removeTipoPos(TipoPos pos);

}
