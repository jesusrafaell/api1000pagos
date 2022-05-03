package com.tranred.milpagosapp.service;

import com.tranred.milpagosapp.domain.Afiliado;
import com.tranred.milpagosapp.domain.Banco;
import com.tranred.milpagosapp.domain.Mensaje;
import com.tranred.milpagosapp.domain.ModalidadPos;
import com.tranred.milpagosapp.domain.TipoPos;
import com.tranred.milpagosapp.domain.ZonaAtencion;
import com.tranred.milpagosapp.repository.IAfiliadoDAO;
import com.tranred.milpagosapp.repository.IMensajeDAO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.tranred.milpagosapp.repository.IPosDAO;
import com.tranred.milpagosapp.repository.JPABancoDAO;
import com.tranred.milpagosapp.repository.JPAZonaAtencionDAO;
import org.springframework.stereotype.Component;

/**
 * Clase que implementa la Interface IAdminMantenimiento
 * mggy@sistemasemsys.com
 * @version 0.1
 */
@Component
public class AdminMantenimiento implements IAdminMantenimiento{
    
    private static final long serialVersionUID = 1L;
    
    @Autowired
    private IPosDAO posDao;   
    
    @Autowired
    private IMensajeDAO mensajeDao;   
    
    @Autowired
    private JPABancoDAO bancoDao;
    
    @Autowired
    private JPAZonaAtencionDAO zonasDAO;
    
    @Autowired
    private IAfiliadoDAO afiliadoDao;
    
    public ModalidadPos getModalidadPosById(int id) {
        return posDao.getModalidadPosById(id);
    }
    
    public List<ModalidadPos> getModalidadPosList() {
        return posDao.getModalidadPosList();
    }

    public void saveModalidadPos(ModalidadPos modalidadPos) {
        posDao.saveModalidadPos(modalidadPos);
    }
    
    public void updateModalidadPos(ModalidadPos modalidadPos){
        posDao.updateModalidadPos(modalidadPos); 
    }
    
    public void removeModalidadPos(ModalidadPos modalidadPos) {
        posDao.removeModalidadPos(modalidadPos); 
    }

    public TipoPos getTipoPosById(int id) {
        return posDao.getTipoPosById(id);
    }
    
    public List<TipoPos> getTipoPosList() {
        return posDao.getTipoPosList();
    }

    public void saveTipoPos(TipoPos tipoPos) {
        posDao.saveTipoPos(tipoPos);
    }
    
    public void updateTipoPos(TipoPos tipoPos){
        posDao.updateTipoPos(tipoPos); 
    }
    
    public void removeTipoPos(TipoPos tipoPos) {
        posDao.removeTipoPos(tipoPos); 
    }

    public Banco getBancoById(String id) {
        return bancoDao.getById(id);
    }
    
    public List<Banco> getBancoList() {
        return bancoDao.getBancoList();
    }

    public void saveBanco(Banco banco) {
        bancoDao.saveBanco(banco);
    }
    
    public void updateBanco(Banco banco){
        bancoDao.updateBanco(banco); 
    }
    
    public void removeBanco(Banco banco) {
        bancoDao.removeBanco(banco); 
    }

    public ZonaAtencion getZonaById(String id) {
        return zonasDAO.getById(id);
    }
    
    public List<ZonaAtencion> getZonasAtencionList() {
        return zonasDAO.getZonasList();
    }

    public void saveZonaAtencion(ZonaAtencion zona) {
        zonasDAO.saveZona(zona);
    }
    
    public void updateZonaAtencion(ZonaAtencion zona){
        zonasDAO.updateZona(zona); 
    }
    
    public void removeZonaAtencion(ZonaAtencion zona) {
        zonasDAO.removeZona(zona); 
    }
    
    public Mensaje getMensajeById(int id) {
        return mensajeDao.getById(id);
    }
    
    public List<Mensaje> getMensajesList() {
        return mensajeDao.getMensajesList();
    }
    
    public void updateMensaje(Mensaje mensaje) {
        mensajeDao.updateMensaje(mensaje);
    }
    
    public Afiliado getAfiliadoById(String id) {
        return afiliadoDao.getAfiliadoById(id);
    }
    
    public List<Afiliado> getAfiliadoList() {
        return afiliadoDao.getAfiliadoList();
    }

    public void saveAfiliado(Afiliado afiliado) {
        afiliadoDao.saveAfiliado(afiliado);
    }
    
    public void updateAfiliado(Afiliado afiliado){
        afiliadoDao.updateAfiliado(afiliado);
    }
}
