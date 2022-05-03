package com.tranred.milpagosapp.repository;

import com.tranred.milpagosapp.domain.Estatus;
import java.util.List;

/**
 * Interface de acceso a datos para los Estatus
 * mggy@sistemasemsys.com
 * @version 0.1
 */
public interface IEstatusDAO {
    
    public Estatus getById(int id);
    
    public List<Estatus> getEstatusList();
    
    public List<Estatus> getEstatusByModulo(String modulo);
    
    public void saveEstatus(Estatus estatus);
    
    public void updateEstatus(Estatus estatus);
    
}
