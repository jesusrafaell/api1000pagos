package com.tranred.milpagosapp.repository;

import com.tranred.milpagosapp.domain.Mensaje;
import java.util.List;

/**
 * Interface de acceso a datos para los Mensajes de Informacion al Cliente
 * mggy@sistemasemsys.com
 * @version 0.1
 */
public interface IMensajeDAO {
    
    public Mensaje getById(int id);
    
    public List<Mensaje> getMensajesList();
    
    public List<Mensaje> getMensajeByClave(String clave);        
    
    public void updateMensaje(Mensaje mensaje);
}
