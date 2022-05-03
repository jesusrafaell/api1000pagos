package com.tranred.milpagosapp.repository;

import com.tranred.milpagosapp.domain.Afiliado;
import java.util.List;

/**
 * Interface de acceso a datos de un Afiliado
 mggy@sistemasemsys.com
 * @version 0.1
 */
public interface IAfiliadoDAO {
    
    public Afiliado getAfiliadoById(String id);
    
    public List<Afiliado> getAfiliadoList();
        
    public List<Afiliado> getAfiliadoByBancoList(String codigo);
 
    public void saveAfiliado(Afiliado afiliado);
    
    public void updateAfiliado(Afiliado afiliado);

}
