package com.tranred.milpagosapp.service;

import com.tranred.milpagosapp.domain.HistoricoFacturacion;
import com.tranred.milpagosapp.domain.PlanCuotaSP;
import com.tranred.milpagosapp.domain.PlanesConsulta;
import java.math.BigDecimal;
import java.util.List;


/**
 * Interface utilizada para generar archivos PDF y Excel
 * mggy@sistemasemsys.com
 * @version 0.1
 */
 
public interface IGenerarArchivosService {
 
	public void generarEdoCuentaPDFComercios(String mes, String ano, int comercio, String rifComercio, String terminal);
        
        public void generarEdoCuentaExcelComercios(String mes, String ano, int comercio, String rifComercio, String terminal);
        
        public void generarFacturacionByComercio(String mes, String ano, String contrato, int numeroControl,  List<HistoricoFacturacion> resultado, List<HistoricoFacturacion> terminales);
        
        public void generarFacturacionComercios(String mes, String ano, String fechaFin, String contrato, int tipoContrato, int numeroControl,  List<HistoricoFacturacion> resultado);
        
        public void generarFacturacionPDFComercios(String tipoContrato,  int codigoComercio, String rifComercio, String razonSocial, String direccion, String telefono, String terminales, BigDecimal monto, String mes, String ano, int numeroControl, String nombreApellidoAliado);
        
        public void generarFacturacionExcelComercios(String tipoContrato,  int codigoComercio, String rifComercio, String razonSocial, String direccion, String telefono, String terminales, BigDecimal monto, String mes, String ano, int numeroControl, String nombreApellidoAliado);
        
        public void generarLibroVentasExcel(String mes, String ano);
        
        public void generarCobranzasTXTComercios(List<PlanCuotaSP> cobranzasXComercio, String tipoReporte, String titulo);

        public void generarCobranzasExcelComercios(List<PlanCuotaSP> cobranzasXComercio, String tipoReporte, String titulo);
        
        public void generarDataentryCobranzaExcel(List<PlanCuotaSP> cobranzasXComercio);
        
        public void generarConsultaPlanesTXTComercios(List<PlanesConsulta> planesXComercio);

        public void generarConsultaPlanesExcelComercios(List<PlanesConsulta> planesXComercio);
        
}
