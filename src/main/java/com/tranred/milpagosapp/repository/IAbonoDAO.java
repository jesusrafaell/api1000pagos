package com.tranred.milpagosapp.repository;

import com.tranred.milpagosapp.domain.Abono;
import java.util.List;

/**
 * Interface de acceso a datos para un Abono
 mggy@sistemasemsys.com
 * @version 0.1
 */
public interface IAbonoDAO {
    
    public List<Abono> getAbonoByTerminal(String codigoTerminal);
            
    public List<Abono> getAbonosList();
    
    public List<Abono> getAbonosByComercioList(String codigoComercio);
    
    public void saveAbono(Abono abono);
    
    public void updateAbono(Abono abono);

    public void deleteAbono(Abono abono);
}
