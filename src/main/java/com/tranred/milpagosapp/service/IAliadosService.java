package com.tranred.milpagosapp.service;

import com.tranred.milpagosapp.domain.Agenda;
import com.tranred.milpagosapp.domain.Aliado;
import com.tranred.milpagosapp.domain.Estadistica;
import com.tranred.milpagosapp.domain.Recaudo;
import com.tranred.milpagosapp.domain.TipoPagoAliado;
import java.io.Serializable;
import java.util.List;
import org.jfree.data.general.PieDataset;

/**
 * Interface utilizada para el manejo de los Aliados Comerciales
 * mggy@sistemasemsys.com
 * @version 0.1
 */
public interface IAliadosService extends Serializable{
    
    public Aliado getById(int id);    
    
    public List<Aliado> getAliadoByIdUsuario(int idUsuario);
            
    public List<Aliado> getAliadoList();
    
    public List<Aliado> getAliadoByIdentificacion(String tipo, String identificacion);
    
    public List<Aliado> getAliadosByZonaAtencion(String zonaAtencion);
    
    public List<Estadistica> getTipoEstadistica();
    
    public List<TipoPagoAliado> getTipoPagoAliado();
    
    public void saveAliado(Aliado aliado);
    
    public void saveAgenda(Agenda agenda);
    
    public void updateAliado(Aliado aliado);
    
    public void updateAliadoIdUsuario(String idAliado, String idUsuario);
    
    public void updateAgenda(Agenda agenda);
    
    public List<Recaudo> getRecaudosList(String tipo);
    
    public List<Agenda> getAgendaByAliadoList(int codigoAliado);
    
    public List<Agenda> getAgendaByZonaList(int codigoZonaAtencion);
    
    public Agenda getAgendaById(int id);
    
    public PieDataset crearDataset();
}
