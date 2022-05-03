package com.tranred.milpagosapp.repository;

import com.tranred.milpagosapp.domain.Factura;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase de acceso a datos para el historico de un valor en especifico
 * mggy@sistemasemsys.com
 * @version 0.1
 */
@Repository(value = "FacturacionDao")
public class JPAFacturacionDAO {
    
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
    public List<Factura> getFacturacionComercio(String mes, String ano, int codigoComercio) {
        return em.createQuery("select f from Factura h where f.codigoComercio = "+ codigoComercio +" and month(f.fecha) = '"+ mes +"' and year(f.fecha) = '"+ ano +"' order by f.numeroFactura asc").getResultList();
    }        
    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked") 
    public List<Factura> getFacturacion(String mes, String ano) {
        return em.createQuery("select f from Factura f where f.mes = '"+ mes +"' and f.ano = '"+ ano +"' and f.estatus = 1 order by f.numeroFactura asc").getResultList();
    }     
    
    @Transactional(readOnly = false)
    public void saveFacturacion(Factura registro) {
        em.persist(registro);
    }
    
    @Transactional(readOnly = false)
    public void updateFactura(int codigoComercio, String mes, String ano) {
        em.createNativeQuery("UPDATE Facturacion SET factEstatus = 0 WHERE factCodComercio = "+ codigoComercio +" and factMes = '"+ mes +"' and factAno = '"+ ano +"'").executeUpdate();
    }
}
