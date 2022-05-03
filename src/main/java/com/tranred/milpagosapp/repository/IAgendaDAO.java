/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tranred.milpagosapp.repository;

import com.tranred.milpagosapp.domain.Agenda;
import java.util.List;

/**
 * Interface de acceso a datos de la Agenda de Visitas
 * mggy@sistemasemsys.com
 * @version 0.1
 */
public interface IAgendaDAO {
    
    public Agenda getAgendaById(int id);
    
    public List<Agenda> getAliadoList();        
    
    public List<Agenda> getAgendaByAliadoList(int codigoAliado);
    
    public List<Agenda> getAgendaByZonaList(int codigoZonaAtencion);
    
    public void saveAgenda(Agenda agenda);
    
    public void updateAgenda(Agenda agenda);
    
    public void removeAgenda(Agenda agenda);
}
