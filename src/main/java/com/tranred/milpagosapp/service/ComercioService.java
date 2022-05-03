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
import com.tranred.milpagosapp.repository.IAbonoDAO;
import com.tranred.milpagosapp.repository.IAfiliadoDAO;
import com.tranred.milpagosapp.repository.IComercioDAO;
import com.tranred.milpagosapp.repository.IComisionDAO;
import com.tranred.milpagosapp.repository.IHistoricoDAO;
import com.tranred.milpagosapp.repository.ICobranzasDAO;
import com.tranred.milpagosapp.repository.JPARecaudoDAO;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Clase que implementa la Interface IComercioService
 * mggy@sistemasemsys.com
 * @version 0.1
 */
@Component
public class ComercioService implements IComercioService{
    
    private static final long serialVersionUID = 1L;
    
    @Autowired
    private IComercioDAO comercioDao;
    
    @Autowired
    private JPARecaudoDAO recaudoDao;
    
    @Autowired
    private IAbonoDAO abonoDao;
    
    @Autowired
    private IAfiliadoDAO afiliadoDao;
    
    @Autowired
    private IComisionDAO comisionDao;
    
    @Autowired
    private IHistoricoDAO historicoDao;
        
    @Autowired
    private ICobranzasDAO cobranzaDao;
        
    public Comercio getById(int id){
        return comercioDao.getById(id);
    }
    public List<ComercioConsulta> getComercioList() {
        return comercioDao.getComercioList();
    }

    public List<ComercioConsulta> getComercioByRIF(String rif) {
        return comercioDao.getComercioByRIF(rif);
    }
    
    public List<TerminalXComercio> getTerminalXComercioList() {
        return comercioDao.getTerminalXComercioList();
    }
    
    public List<TerminalXComercio> getTerminalesByComercioList(int comercio) {
        return comercioDao.getTerminalesByComercioList(comercio);
    }
        
    public List<TerminalXComercio> getTerminalInfoList(String terminal) {
        return comercioDao.getTerminalInfoList(terminal);
    }
    
    public List<HistoricoEdoCuenta> getHistoricoEdoCuentaComercios(Date fecha, int codigoComercio, String terminal) {
        return historicoDao.getHistoricoEdoCuentaComercios(fecha, codigoComercio, terminal);
    }
    
    public List<HistoricoFacturacion> getHistoricoFacturacionComercios(int tipoConsulta, int tipoContrato, Date fecha, int codigoComercio) {
        return historicoDao.getHistoricoFacturacionComercios(tipoConsulta, tipoContrato, fecha, codigoComercio);
    }
    
    public BigDecimal getMontoPagadasTipoPlan(String terminal, Date fecha, Integer codTipoPlan) {
        return cobranzaDao.getMontoPagadasTipoPlan( terminal, fecha, codTipoPlan);
    }
    
    public List<Comercio> getComerciosByCategoria(int categoria) {
        return comercioDao.getComerciosByCategoria(categoria);
    }
    
    public void saveComercio(Comercio comercio) {
        comercioDao.saveComercio(comercio);
    }

    public void updateComercio(Comercio comercio) {
        comercioDao.updateComercio(comercio);
    }

    public void saveContacto(Contacto contacto) {
        comercioDao.saveContacto(contacto);
    }

    public void updateContacto(Contacto contacto) {
        comercioDao.updateContacto(contacto);
    }
    
    public List<Contacto> getContactoByCodComercio(int id){
        return comercioDao.getContactoByCodComercio(id);
    }
    
    public List<Recaudo> getRecaudosList(String tipo) {
        return recaudoDao.getRecaudosList(tipo);
    }

    public List<CategoriaComercio> getCategoriaComercioList() {
        return comercioDao.getCategoriaComercioList();
    }
    
    public List<Comercio> getComerciosExcluidosList(){
        return comercioDao.getComerciosExcluidosList();
    }
    
    public List<TipoContrato> getTipoContratoList() {
        return comercioDao.getTipoContratoList();
    }
    
    public void saveComerciosXafiliado(String codigoAfiliado, int codigoComercio){
        comercioDao.saveComerciosXafiliado(codigoAfiliado, codigoComercio);
    }
    
    public List<ComercioConsulta> getComercioById(int idComercio) {
        return comercioDao.getComercioById(idComercio);
    }
    
    public List<TipoGarantiaComercio> getTipoGarantiaList() {
        return comercioDao.getTipoGarantiaList();
    }
    
    public List<Abono> getAbonoByTerminal(String codigoTerminal) {
        return abonoDao.getAbonoByTerminal(codigoTerminal);
    }
    
    public List<Abono> getAbonosByComercioList(String codigoComercio) {
        return abonoDao.getAbonosByComercioList(codigoComercio);
    }

    public void saveAbono(Abono abono) {
        abonoDao.saveAbono(abono);
    }

    public void updateAbono(Abono abono) {
        abonoDao.updateAbono(abono);
    }

    public List<Afiliado> getAfiliadoList() {
        return afiliadoDao.getAfiliadoList();
    }

    public void saveAfiliado(Afiliado afiliado) {
        afiliadoDao.saveAfiliado(afiliado);
    }

    public void updateAfiliado(Afiliado afiliado) {
        afiliadoDao.updateAfiliado(afiliado);
    }    

    public ComisionMilPagos getComisionById(int id) {
        return comisionDao.getById(id);
    }

    public List<ComisionMilPagos> getComisionList() {
        return comisionDao.getComisionList();
    }

    public void saveComision(ComisionMilPagos comision) {
        comisionDao.saveComision(comision);
    }

    public void updateComision(ComisionMilPagos comision) {
        comisionDao.updateComision(comision);
    }

    public void removeBanco(ComisionMilPagos comision) {
        comisionDao.removeBanco(comision);
    }                  

    @Override
    public List<ComerciosXAfiliado> getComerciosXAfiliadoByCodComercio(int id) {
        return comercioDao.getComerciosXAfiliadoByCodComercio(id);
    }

    @Override
    public void deleteComerciosXafiliado(int codigoComercio) {
        comercioDao.deleteComerciosXafiliado(codigoComercio);
    }        

    @Override
    public List<Comercio> getComerciosByContrato(int contrato) {
        return comercioDao.getComerciosByContrato(contrato);
    }

    @Override
    public List<Comercio> getComerciosByDias(String dias) {
        return comercioDao.getComerciosByDias(dias);
    }

    @Override
    public List<Comercio> getComerciosByContribuyente(String contribuyente) {
        return comercioDao.getComerciosByContribuyente(contribuyente);
    }
}
