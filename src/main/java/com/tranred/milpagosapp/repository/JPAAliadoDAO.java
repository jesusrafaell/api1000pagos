package com.tranred.milpagosapp.repository;

import com.tranred.milpagosapp.domain.Aliado;
import com.tranred.milpagosapp.domain.Estadistica;
import com.tranred.milpagosapp.domain.TipoPagoAliado;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase que implementa la Interface IAliadoDAO
 * mggy@sistemasemsys.com
 * @version 0.1
 */

@Repository(value = "AliadoDao")
public class JPAAliadoDAO implements IAliadoDAO{
    
    private EntityManager em = null;

    /*
     * Sets the entity manager.
     */
    @PersistenceContext
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public Aliado getById(int id) {        
        return em.find(Aliado.class, id);                
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")    
    public List<Aliado> getAliadoList() {
         return em.createQuery("select a from Aliado a order by a.id").getResultList();
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")    
    public List<Aliado> getAliadoByIdentificacion(String tipo, String identificacion) {
         return em.createQuery("select a from Aliado a where a.tipoIdentificacion = '"+ tipo +"' and a.identificacion = '"+ identificacion +"' order by a.id").getResultList();
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")    
    public List<Aliado> getAliadosByZonaAtencion(String zonaAtencion) {
         return em.createQuery("select a from Aliado a where a.codZonaAtencion = '"+ zonaAtencion +"' order by a.id").getResultList();
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")    
    public List<Aliado> getAliadoByIdUsuario(int idUsuario) {
         return em.createQuery("select a from Aliado a where a.idUsuario = "+ idUsuario +"").getResultList();
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")    
    public List<Estadistica> getTipoEstadistica() {
         return em.createQuery("select e from Estadistica e order by e.tipoEstadistica").getResultList();
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")    
    public List<TipoPagoAliado> getTipoPagoAliado() {
         return em.createQuery("select t from TipoPagoAliado t order by t.codigoTipoPago").getResultList();
    }
    
    @Transactional(readOnly = false)
    public void saveAliado(Aliado aliado) {
        em.persist(aliado);
    }
    
    @Transactional(readOnly = false)
    public void updateAliado(Aliado aliado) {
        em.merge(aliado);
    }
    
    @Transactional(readOnly = false)
    public void updateAliadoIdUsuario(String idAliado, String idUsuario) {
        em.createNativeQuery("UPDATE Aliados SET aliIdUsuario = '"+ idUsuario +"' WHERE id='"+ idAliado +"'").executeUpdate();
    }
    
    public PieDataset crearDataset() {
    
        DefaultPieDataset dpd = new DefaultPieDataset();
        dpd.setValue("Mac", 21);
        dpd.setValue("Windows", 100);
        
        return dpd;
    }
}
