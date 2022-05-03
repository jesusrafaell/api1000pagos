package com.tranred.milpagosapp.repository;

import com.tranred.milpagosapp.domain.ComisionMilPagos;
import java.util.List;

/**
 * Interface de acceso a datos para las Comisiones
 * mggy@sistemasemsys.com
 * @version 0.1
 */
public interface IComisionDAO {
    
    public ComisionMilPagos getById(int id);
    
    public List<ComisionMilPagos> getComisionList();
    
    public void saveComision(ComisionMilPagos comision);
    
    public void updateComision(ComisionMilPagos comision);
    
    public void removeBanco(ComisionMilPagos comision);
    
}
