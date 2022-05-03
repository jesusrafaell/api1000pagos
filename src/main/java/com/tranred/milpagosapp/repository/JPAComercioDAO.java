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
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase que implementa la Interface IComercioDAO
 * mggy@sistemasemsys.com
 * @version 0.1
 */
@Repository(value = "ComercioDao")
public class JPAComercioDAO implements IComercioDAO{
    
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
    public Comercio getById(int id) {
        System.out.println(id);
        return em.find(Comercio.class, id);                
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked") 
    public List<ComercioConsulta> getComercioList() {        
        Query query = em.createNamedQuery("consultaComercio");
        query.setParameter("tipoConsulta", 2);        
        return query.getResultList();        
    }        
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked") 
    public List<ComercioConsulta> getComercioByRIF(String rif) {        
        Query query = em.createNamedQuery("consultaComercio");
        query.setParameter("tipoConsulta", 1);
        query.setParameter("rif", rif);
        query.setParameter("idComercio", "");
        query.setParameter("terminal", "");
        return query.getResultList();        
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked") 
    public List<TerminalXComercio> getTerminalXComercioList() {        
        Query query = em.createNamedQuery("consultaTerminalXComercio");
        query.setParameter("tipoConsulta", 4);
        query.setParameter("rif", "");
        query.setParameter("idComercio", "");
        query.setParameter("terminal", "");
        return query.getResultList();        
    } 
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked") 
    public List<TerminalXComercio> getTerminalesByComercioList(int comercio) {        
        Query query = em.createNamedQuery("consultaTerminalXComercio");
        query.setParameter("tipoConsulta", 5);
        query.setParameter("rif", "");
        query.setParameter("idComercio", comercio);
        query.setParameter("terminal", "");
        return query.getResultList();        
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked") 
    public List<TerminalXComercio> getTerminalInfoList(String terminal) {        
        Query query = em.createNamedQuery("consultaTerminalXComercio");
        query.setParameter("tipoConsulta", 6);
        query.setParameter("rif", "");
        query.setParameter("idComercio", "");
        query.setParameter("terminal", terminal);
        return query.getResultList();        
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked") 
    public List<Comercio> getComerciosByCategoria(int categoria) {        
        return em.createQuery("select c from Comercio c where c.codigoCategoria = " + categoria + " order by c.codigoComercio").getResultList();
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked") 
    public List<Comercio> getComerciosByContrato(int contrato) {        
        return em.createQuery("select c from Comercio c where c.codigoTipoContrato = " + contrato + " order by c.codigoComercio").getResultList();
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked") 
    public List<Comercio> getComerciosByDias(String dias) {        
        return em.createQuery("select c from Comercio c where c.diasOperacion like '" + dias + "' order by c.codigoComercio").getResultList();
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked") 
    public List<Comercio> getComerciosByContribuyente(String contribuyente) {        
        return em.createQuery("select c from Comercio c where c.pagaIVA = '" + contribuyente + "' order by c.codigoComercio").getResultList();
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked") 
    public List<Comercio> getComerciosExcluidosList() {        
        return em.createQuery("select c from Comercio c where c.excluirArchivoPago = 1 order by c.codigoComercio").getResultList();
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked") 
    public List<ComercioConsulta> getComercioById(int idComercio) {        
        Query query = em.createNamedQuery("consultaComercio");
        query.setParameter("tipoConsulta", 3);
        query.setParameter("rif", "");
        query.setParameter("idComercio", idComercio);
        query.setParameter("terminal", "");
        return query.getResultList();        
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked") 
    public List<CategoriaComercio> getCategoriaComercioList() {        
        return em.createQuery("select cc from CategoriaComercio cc order by cc.codigoCategoria").getResultList();
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked") 
    public List<TipoContrato> getTipoContratoList() {        
        return em.createQuery("select tc from TipoContrato tc order by tc.codigoContrato").getResultList();       
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked") 
    public List<TipoGarantiaComercio> getTipoGarantiaList() {        
        return em.createQuery("select tg from TipoGarantiaComercio tg order by tg.codigoTipoGarantia").getResultList();
    }        
    
    @Transactional(readOnly = false)
    public void saveComercio(Comercio comercio) {
        em.persist(comercio);
    }
    
    @Transactional(readOnly = false)
    public void updateComercio(Comercio comercio) {
        em.merge(comercio);
    }
    
    @Transactional(readOnly = false)
    public void saveContacto(Contacto contacto) {
        em.persist(contacto);
    }
    
    @Transactional(readOnly = false)
    public void updateContacto(Contacto contacto) {
        em.merge(contacto);
    }
    
    @Transactional(readOnly = true)
    public List<Contacto> getContactoByCodComercio(int id) {
        return em.createQuery("select c from Contacto c where c.codigoComercio = " + id +"").getResultList();
    }
    
    @Transactional(readOnly = false)
    public void saveComerciosXafiliado(String codigoAfiliado, int codigoComercio) {
        em.createNativeQuery("INSERT INTO ComerciosXafiliado ([cxaCodAfi],[cxaCodComer]) VALUES ('"+ codigoAfiliado +"',"+ codigoComercio +")").executeUpdate();
    }
     
    @Transactional(readOnly = true)
    public List<ComerciosXAfiliado> getComerciosXAfiliadoByCodComercio(int id) {
        return em.createQuery("select cxa from ComerciosXAfiliado cxa where cxa.codigoComercio = " + id +"").getResultList();
    }
    
    @Transactional(readOnly = false)
    public void deleteComerciosXafiliado(int codigoComercio) {
        em.createNativeQuery("DELETE FROM ComerciosXafiliado WHERE cxaCodComer = "+ codigoComercio +"").executeUpdate();
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked") 
    public List<Comercio> getComerciosContribuyentesList() {        
        return em.createQuery("select c from Comercio c where c.pagaIVA = 'SI' order by c.codigoComercio").getResultList();
    }
}
