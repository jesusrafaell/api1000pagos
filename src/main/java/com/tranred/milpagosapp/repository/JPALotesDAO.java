package com.tranred.milpagosapp.repository;

import com.tranred.milpagosapp.domain.Lotes;
import java.sql.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase de acceso a datos para la tabla Lotesxbanco
 * mggy@sistemasemsys.com
 * @version 0.1
 */

@Repository(value = "LotesDao")
public class JPALotesDAO {
    
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
    public List<Lotes> getLoteList(String compania) {
        return em.createQuery("select l from Lotes l where l.codigoCompania = '"+ compania +"'").getResultList();
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public List<Lotes> getLoteByFecha(Date desde, Date hasta) {
        return em.createQuery("select l from Lotes l where l.fechaValor between '"+ desde +"' and '"+ hasta +"'").getResultList();
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public List<Lotes> getLoteByFechaAfiliado(Date desde, String banco, String tipoArchivo) {
        return em.createQuery("select l from Lotes l where l.fechaValor = '"+ desde +"' and l.codigoBanco = '"+ banco +"' and l.tipoArchivo = '"+ tipoArchivo +"'").getResultList();
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public Lotes getByIdLote(int id) {        
        return em.find(Lotes.class, id);
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public List<Lotes> getByNumeroLote(String lote) {        
        return em.createQuery("select l from Lotes l where l.numeroLote = '"+ lote +"'").getResultList();
    }
    
    @Transactional(readOnly = false)
    public void saveLote(Lotes lote) {
        em.persist(lote);
    }
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public String getNumeroLote(String compania, String fecha) {        
        return (String) em.createNativeQuery("select ISNULL( MAX(SUBSTRING(lotNumLote,7,2)) , 0) as lote from Lotesxbanco where lotCodCompania = '" + compania + "' and SUBSTRING(lotNumLote,1,6) = '" + fecha + "'").getSingleResult();
    }
}
