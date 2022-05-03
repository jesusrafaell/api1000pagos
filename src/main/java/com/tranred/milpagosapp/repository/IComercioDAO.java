package com.tranred.milpagosapp.repository;

import com.tranred.milpagosapp.domain.CategoriaComercio;
import com.tranred.milpagosapp.domain.Comercio;
import com.tranred.milpagosapp.domain.ComercioConsulta;
import com.tranred.milpagosapp.domain.ComerciosXAfiliado;
import com.tranred.milpagosapp.domain.Contacto;
import com.tranred.milpagosapp.domain.TerminalXComercio;
import com.tranred.milpagosapp.domain.TipoContrato;
import com.tranred.milpagosapp.domain.TipoGarantiaComercio;
import java.util.List;

/**
 * Interface de acceso a datos de un Comercio
 mggy@sistemasemsys.com
 * @version 0.1
 */
public interface IComercioDAO {
    
    public Comercio getById(int id);
    
    public List<ComercioConsulta> getComercioById(int idComercio);
            
    public List<ComercioConsulta> getComercioList();
    
    public List<ComercioConsulta> getComercioByRIF(String rif);
    
    public List<TerminalXComercio> getTerminalXComercioList();
    
    public List<TerminalXComercio> getTerminalesByComercioList(int comercio);
    
    public List<TerminalXComercio> getTerminalInfoList(String terminal);
    
    public List<Comercio> getComerciosByCategoria(int categoria);
    
    public List<Comercio> getComerciosByContrato(int contrato);
    
    public List<Comercio> getComerciosByDias(String dias);
    
    public List<Comercio> getComerciosByContribuyente(String contribuyente);
    
    public void saveComercio(Comercio comercio);
    
    public void updateComercio(Comercio comercio);
    
    public void saveContacto(Contacto contacto);
       
    public void updateContacto(Contacto contacto);
    
    public List<CategoriaComercio> getCategoriaComercioList();
    
    public List<Comercio> getComerciosExcluidosList();
    
    public List<TipoContrato> getTipoContratoList();
    
    public List<TipoGarantiaComercio> getTipoGarantiaList();
    
    public void saveComerciosXafiliado(String codigoAfiliado, int codigoComercio);
    
    public List<Contacto> getContactoByCodComercio(int id);
    
    public List<ComerciosXAfiliado> getComerciosXAfiliadoByCodComercio(int id);
    
    public void deleteComerciosXafiliado(int codigoComercio);
    
    public List<Comercio> getComerciosContribuyentesList();
}
