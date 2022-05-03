package com.tranred.milpagosapp.service;

import com.tranred.milpagosapp.domain.Afiliado;
import com.tranred.milpagosapp.domain.Banco;
import com.tranred.milpagosapp.domain.Mensaje;
import com.tranred.milpagosapp.domain.ModalidadPos;
import com.tranred.milpagosapp.domain.TipoPos;
import com.tranred.milpagosapp.domain.ZonaAtencion;
import java.io.Serializable;
import java.util.List;

/**
 * Interface utilizada para el manejo de la opcion mantenimiento en el modulo
 * de administracion de la app
 * mggy@sistemasemsys.com
 * @version 0.1
 */
public interface IAdminMantenimiento extends Serializable{
    
    public ModalidadPos getModalidadPosById(int id);
    
    public List<ModalidadPos> getModalidadPosList();
    
    public void updateModalidadPos(ModalidadPos modalidadPos);
            
    public void saveModalidadPos(ModalidadPos modalidadPos);
    
    public void removeModalidadPos(ModalidadPos modalidadPos);
    
    public TipoPos getTipoPosById(int id);
    
    public List<TipoPos> getTipoPosList();        

    public void saveTipoPos(TipoPos pos);
    
    public void updateTipoPos(TipoPos pos);
    
    public void removeTipoPos(TipoPos pos);
    
    public Banco getBancoById(String id);
    
    public List<Banco> getBancoList();
    
    public void updateBanco(Banco banco);
            
    public void saveBanco(Banco banco);
    
    public void removeBanco(Banco banco);
    
    public ZonaAtencion getZonaById(String id);
    
    public List<ZonaAtencion> getZonasAtencionList();

    public void saveZonaAtencion(ZonaAtencion zona);
    
    public void updateZonaAtencion(ZonaAtencion zona);
    
    public void removeZonaAtencion(ZonaAtencion zona);
    
    public Mensaje getMensajeById(int id);
            
    public List<Mensaje> getMensajesList();   
    
    public void updateMensaje(Mensaje mensaje);
    
    public Afiliado getAfiliadoById(String id);        
    
    public List<Afiliado> getAfiliadoList();

    public void saveAfiliado(Afiliado afiliado);
    
    public void updateAfiliado(Afiliado afiliado);
    
}
