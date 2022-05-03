package com.tranred.milpagosapp.service;

import com.tranred.milpagosapp.domain.Abono;
import com.tranred.milpagosapp.domain.Afiliado;
import com.tranred.milpagosapp.domain.CategoriaComercio;
import com.tranred.milpagosapp.domain.Comercio;
import com.tranred.milpagosapp.domain.ComercioConsulta;
import com.tranred.milpagosapp.domain.ComerciosXAfiliado;
import com.tranred.milpagosapp.domain.ComisionMilPagos;
import com.tranred.milpagosapp.domain.Contacto;
import com.tranred.milpagosapp.domain.HistoricoEdoCuenta;
import com.tranred.milpagosapp.domain.HistoricoFacturacion;
import com.tranred.milpagosapp.domain.Recaudo;
import com.tranred.milpagosapp.domain.TerminalXComercio;
import com.tranred.milpagosapp.domain.TipoContrato;
import com.tranred.milpagosapp.domain.TipoGarantiaComercio;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

/**
 * Interface utilizada para el manejo de Comercios 
 * mggy@sistemasemsys.com
 * @version 0.1
 */
public interface IComercioService extends Serializable{
    
    public Comercio getById(int id);
            
    public List<ComercioConsulta> getComercioById(int idComercio);
            
    public List<ComercioConsulta> getComercioList();
    
    public List<ComercioConsulta> getComercioByRIF(String rif);
    
    public List<TerminalXComercio> getTerminalXComercioList();
    
    public List<TerminalXComercio> getTerminalesByComercioList(int comercio);
    
    public List<TerminalXComercio> getTerminalInfoList(String terminal);
    
    public List<HistoricoEdoCuenta> getHistoricoEdoCuentaComercios(Date fecha, int codigoComercio, String terminal);
    
    public List<HistoricoFacturacion> getHistoricoFacturacionComercios(int tipoConsulta, int tipoContrato, Date fecha, int codigoComercio);
    
    public BigDecimal getMontoPagadasTipoPlan(String terminal, Date fecha, Integer codTipoPlan);
    
    public List<Comercio> getComerciosByCategoria(int categoria);
    
    public List<Comercio> getComerciosByContrato(int contrato);
    
    public List<Comercio> getComerciosByDias(String dias);
    
    public List<Comercio> getComerciosByContribuyente(String contribuyente);
            
    public void saveComercio(Comercio comercio);
    
    public void updateComercio(Comercio comercio);
    
    public void saveContacto(Contacto contacto);      
        
    public void updateContacto(Contacto contacto);
    
    public List<Contacto> getContactoByCodComercio(int id);
    
    public List<Recaudo> getRecaudosList(String tipo);
    
    public List<CategoriaComercio> getCategoriaComercioList();
    
    public List<Comercio> getComerciosExcluidosList();
    
    public List<TipoContrato> getTipoContratoList();
    
    public List<TipoGarantiaComercio> getTipoGarantiaList();
    
    public void saveComerciosXafiliado(String codigoAfiliado, int codigoComercio);
    
    public List<Abono> getAbonoByTerminal(String codigoTerminal);
            
    public List<Abono> getAbonosByComercioList(String codigoComercio);
    
    public void saveAbono(Abono abono);
    
    public void updateAbono(Abono abono);
    
    public List<Afiliado> getAfiliadoList();
        
    public void saveAfiliado(Afiliado afiliado);
    
    public void updateAfiliado(Afiliado afiliado);
    
    public ComisionMilPagos getComisionById(int id);
    
    public List<ComisionMilPagos> getComisionList();
    
    public void saveComision(ComisionMilPagos comision);
    
    public void updateComision(ComisionMilPagos comision);
    
    public void removeBanco(ComisionMilPagos comision);
    
    public List<ComerciosXAfiliado> getComerciosXAfiliadoByCodComercio(int id);
    
    public void deleteComerciosXafiliado(int codigoComercio);
        
}
