package com.tranred.milpagosapp.service;

import com.tranred.milpagosapp.domain.Agenda;
import com.tranred.milpagosapp.domain.Aliado;
import com.tranred.milpagosapp.domain.Estadistica;
import com.tranred.milpagosapp.domain.Recaudo;
import com.tranred.milpagosapp.domain.TipoPagoAliado;
import com.tranred.milpagosapp.repository.IAgendaDAO;
import com.tranred.milpagosapp.repository.IAliadoDAO;
import com.tranred.milpagosapp.repository.JPARecaudoDAO;
import java.util.List;
import org.jfree.data.general.PieDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Clase que implementa la Interface IAliadosService
 * mggy@sistemasemsys.com
 * @version 0.1
 */

@Component
public class AliadosService implements IAliadosService {
    
    private static final long serialVersionUID = 1L;
    
    @Autowired
    private IAliadoDAO aliadoDao;
    
    @Autowired
    private IAgendaDAO agendaDao;
    
    @Autowired
    private JPARecaudoDAO recaudoDao;
    
    public Aliado getById(int id) {
        return aliadoDao.getById(id);
    }
    
    public List<Aliado> getAliadoList() {
        return aliadoDao.getAliadoList();
    }        
    
    public List<Aliado> getAliadoByIdentificacion(String tipo, String identificacion){
        return aliadoDao.getAliadoByIdentificacion(tipo,identificacion);
    }
    
    public List<Aliado> getAliadosByZonaAtencion(String zonaAtencion){
        return aliadoDao.getAliadosByZonaAtencion(zonaAtencion);
    }
    public void saveAliado(Aliado aliado){
        aliadoDao.saveAliado(aliado);
    }   
    
    public void saveAgenda(Agenda agenda) {
        agendaDao.saveAgenda(agenda);
    }
    
    public void updateAliado(Aliado aliado) {
        aliadoDao.updateAliado(aliado);
    }
    
    public void updateAgenda(Agenda agenda) {
        agendaDao.updateAgenda(agenda);
    }
    
    public void updateAliadoIdUsuario(String idAliado, String idUsuario){
        aliadoDao.updateAliadoIdUsuario(idAliado, idUsuario);
    }
    
    public List<Agenda> getAgendaByAliadoList(int codigoAliado){
        return agendaDao.getAgendaByAliadoList(codigoAliado);
    }
        
    public List<Agenda> getAgendaByZonaList(int codigoZonaAtencion) {
        return agendaDao.getAgendaByZonaList(codigoZonaAtencion);
    }
    
    public List<Recaudo> getRecaudosList(String tipo) {
        return recaudoDao.getRecaudosList(tipo);
    }
    
    public List<Aliado> getAliadoByIdUsuario(int idUsuario) {
        return aliadoDao.getAliadoByIdUsuario(idUsuario);
    }   

    public Agenda getAgendaById(int id) {
        return agendaDao.getAgendaById(id);
    }
   
    public List<Estadistica> getTipoEstadistica() {
        return aliadoDao.getTipoEstadistica();
    }
    
    public PieDataset crearDataset() {
        return aliadoDao.crearDataset();
    }
    
    public List<TipoPagoAliado> getTipoPagoAliado() {
        return aliadoDao.getTipoPagoAliado();
    }   
        
}
