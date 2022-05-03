package com.tranred.milpagosapp.service;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.core.layout.LayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.util.SortUtils;
import com.tranred.milpagosapp.domain.Cobranzas;
import com.tranred.milpagosapp.domain.EstadoCuenta;
import com.tranred.milpagosapp.domain.Factura;
import com.tranred.milpagosapp.domain.PlanCuotaSP;
import com.tranred.milpagosapp.domain.HistoricoEdoCuenta;
import com.tranred.milpagosapp.domain.HistoricoFacturacion;
import com.tranred.milpagosapp.domain.LibroVentas;
import com.tranred.milpagosapp.domain.PlanesConsulta;
import com.tranred.milpagosapp.domain.PlanesConsultaRep;
import com.tranred.milpagosapp.repository.JPAFacturacionDAO;
import com.tranred.milpagosapp.util.Utilidades;
import com.tranred.utils.Archivo;
import com.tranred.utils.Fecha;
import com.tranred.utils.Numero;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle; 
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Servicio para generar archivos PDF o EXCEL
 */
@Component
public class GenerarArchivosService implements IGenerarArchivosService{

    @Autowired
    private IComercioService comercioService;
    
    @Autowired
    private JPAFacturacionDAO facturasDao;

    @Autowired
    private Utilidades utils;

    protected final Log logger = LogFactory.getLog(getClass());
    private final Locale español = new Locale("es");
    private final SimpleDateFormat formateador = new SimpleDateFormat("MMMM", español);
    private JasperPrint jp, jpe;
    private JasperReport jr, jre;
    private Map params = new HashMap();
    private DynamicReport dr, dre;
    private DynamicReportBuilder drbe = new DynamicReportBuilder();
    private JRDataSource ds = null, dse= null;

    @Value("${ruta.edoCuentaComercios}")
    private String rutaEdoCuenta;

    @Value("${ruta.facturacionComercios}")
    private String rutaFacturacion;
    
    @Value("${ruta.libroVentas}")
    private String rutaLibroVentas;
    
    @Value("${ruta.cobranzaComercios}")
    private String rutaCobranzaComercios;

    public void generarEdoCuentaPDFComercios(String mes, String ano, int comercio, String rifComercio, String terminal) {

        jp = new JasperPrint();
        jr = null;
        params = new HashMap();
        dr = new DynamicReport();
        ds = null;
        String pathLogo = "classpath:/img/EdoCuenta.png", pathPetro = "classpath:/img/petro.png", fechaFin, nombreMes;
        System.setProperty("java.awt.headless", "true");

        //Buscamos el ultimo dia del mes seleccionado
        fechaFin = Utilidades.ultimoDiaMes(mes, ano);
        nombreMes = formateador.format(Utilidades.deStringToDate(fechaFin));

        try {

            EstadoCuenta elementToInclude;
            Collection<EstadoCuenta> dataResult=new ArrayList<>();
            String montoTotalFacturadoFormato
                    , montoTotalDeduccionesFormato
                    , montoDeduccionesMantenimientoFormato
                    , montoDeduccionesIvaMantenimientoFormato
                    , montoDeduccionesComisionBancariaFormato
                    , totalAbonoFormato
                    , montoTotalFacturadoPetroFormato
                    , montoTotalDeduccionesPetroFormato
                    , montoDeduccionesManteminientoPetroFormato
                    , montoDeduccionesIvaMantenimientoPetroFormato
                    , montoDeduccionesComisionBancariaPetroFormato
                    , totalAbonoPetroFormato;
            BigDecimal montoTotalFacturado = new BigDecimal(0)
                    , montoTotalDeducciones = new BigDecimal(0)
                    , montoTotalDeduccionesMantenimiento = new BigDecimal(0)
                    , montoTotalDeduccionesIvaMantenimiento = new BigDecimal(0)
                    , montoTotalDeduccionesComisionBancaria = new BigDecimal(0)
                    , totalAbono = new BigDecimal(0)
                    , montoPetro = new BigDecimal(utils.parametro("monto.petro").get(0).getValor())
                    , montoTotalPetro = new BigDecimal(0)
                    , montoTotalFacturadoPetro = new BigDecimal(0)
                    , montoTotalDeduccionesPetro = new BigDecimal(0)
                    , montoTotalDeduccionesMantenimientoPetro = new BigDecimal(0)
                    , montoTotalDeduccionesIvaMantenimientoPetro = new BigDecimal(0)
                    , montoTotalDeduccionesComisionBancarioPetro = new BigDecimal(0)
                    
                    , totalAbonoPetro = new BigDecimal(0);
            montoTotalDeduccionesMantenimiento = montoTotalDeduccionesMantenimiento.setScale(2, RoundingMode.CEILING);
            montoTotalDeduccionesIvaMantenimiento = montoTotalDeduccionesIvaMantenimiento.setScale(2, RoundingMode.CEILING);
            montoTotalDeduccionesComisionBancaria = montoTotalDeduccionesComisionBancaria.setScale(2, RoundingMode.CEILING);
            montoTotalFacturado = montoTotalFacturado.setScale(2, RoundingMode.CEILING);
            montoPetro = montoPetro.setScale(2, RoundingMode.CEILING);
            montoTotalPetro = montoTotalPetro.setScale(6, RoundingMode.CEILING);
            totalAbono = totalAbono.setScale(2, RoundingMode.CEILING);
            totalAbonoPetro = totalAbonoPetro.setScale(6, RoundingMode.CEILING);

            jr =  JasperCompileManager.compileReport(getClass().getResourceAsStream("/templates/estadosCuentaComercioPDF.jrxml"));
            List<HistoricoEdoCuenta> resultado = comercioService.getHistoricoEdoCuentaComercios(Utilidades.convierteFechaSqlsinHora(fechaFin), comercio, terminal);
            /*BigDecimal montoDeduccionesMantenimiento = comercioService.getMontoPagadasTipoPlan( terminal, Utilidades.convierteFechaSqlsinHora(fechaFin), 1);
            if(montoDeduccionesMantenimiento == null){
                montoDeduccionesMantenimiento = new BigDecimal(0);
            }else{
                montoDeduccionesMantenimiento = montoDeduccionesMantenimiento.setScale(2, RoundingMode.CEILING);
            }
            BigDecimal montoDeduccionesPorUso = comercioService.getMontoPagadasTipoPlan( terminal, Utilidades.convierteFechaSqlsinHora(fechaFin), 3);
            if(montoDeduccionesPorUso == null){
                montoDeduccionesPorUso = new BigDecimal(0);
            }else{
                montoDeduccionesPorUso = montoDeduccionesPorUso.setScale(2, RoundingMode.CEILING);
            }*/
            if(!resultado.isEmpty()){

                //Parametros del Reporte
                params.put("fechaInicioMes", "01" + fechaFin.substring(2));
                params.put("fechaFinMes", fechaFin);
 		params.put("terminal", terminal);
                params.put("nombreComercio", resultado.get(0).getDescripcionComercio());
                params.put("rif", resultado.get(0).getRifComercio().substring(0, 1) + "-" + resultado.get(0).getRifComercio().substring(1));
                params.put("direccion", resultado.get(0).getDireccionComercio());
                if(resultado.get(0).getTelefonoHabitacion()==null || resultado.get(0).getTelefonoHabitacion().isEmpty()){
                    params.put("telefono", resultado.get(0).getTelefonoCelular());
                }else{
                    params.put("telefono", resultado.get(0).getTelefonoHabitacion());
                }
                params.put("banco", resultado.get(0).getNombreBanco());
                params.put("logo", pathLogo);

                for(HistoricoEdoCuenta registros: resultado){
                    elementToInclude = new EstadoCuenta();
                    elementToInclude.setFecha(registros.getFecha());
                    elementToInclude.setMontoFacturado(Utilidades.FormatearNumero(registros.getMontoFacturado().toString()));
                    elementToInclude.setHisComisionMantenimiento(Utilidades.FormatearNumero(registros.getHisComisionMantenimiento().toString()));
                    elementToInclude.setHisIvaSobreMantenimiento(Utilidades.FormatearNumero(registros.getHisIvaSobreMantenimiento().toString()));
                    elementToInclude.setHisComisionBancaria(Utilidades.FormatearNumero(registros.getHisComisionBancaria().toString()));
                    elementToInclude.setMontoTotal(Utilidades.FormatearNumero(registros.getMontoTotal().toString()));
                    
                    montoTotalPetro = registros.getMontoTotal().divide(montoPetro, 6, RoundingMode.HALF_UP);
                    elementToInclude.setMontoTotalPetro(Utilidades.FormatearNumeroPetro(montoTotalPetro.toString()));
                    //acumulacion totales
                    montoTotalFacturado = montoTotalFacturado.add(registros.getMontoFacturado());
                    //montoTotalDeducciones = montoTotalDeducciones.add(registros.getComisionMilPagos());
                    montoTotalDeducciones = montoTotalDeducciones.add(registros.getHisComisionMantenimiento()).add(registros.getHisIvaSobreMantenimiento()).add(registros.getHisComisionBancaria());
                    montoTotalDeduccionesMantenimiento = montoTotalDeduccionesMantenimiento.add(registros.getHisComisionMantenimiento());                    
                    montoTotalDeduccionesIvaMantenimiento = montoTotalDeduccionesIvaMantenimiento.add(registros.getHisIvaSobreMantenimiento());                    
                    montoTotalDeduccionesComisionBancaria = montoTotalDeduccionesComisionBancaria.add(registros.getHisComisionBancaria());                    
                    totalAbono = totalAbono.add(registros.getMontoTotal());
                    dataResult.add(elementToInclude);
                }

                montoTotalFacturadoFormato = Utilidades.FormatearNumero(montoTotalFacturado.toString());
                
                montoTotalDeduccionesFormato = Utilidades.FormatearNumero(montoTotalDeducciones.toString());
                montoDeduccionesMantenimientoFormato = Utilidades.FormatearNumero(montoTotalDeduccionesMantenimiento.toString());
                montoDeduccionesIvaMantenimientoFormato = Utilidades.FormatearNumero(montoTotalDeduccionesIvaMantenimiento.toString());
                montoDeduccionesComisionBancariaFormato = Utilidades.FormatearNumero(montoTotalDeduccionesComisionBancaria.toString());
                totalAbonoFormato = Utilidades.FormatearNumero(totalAbono.toString());

                //Petro
                montoTotalFacturadoPetroFormato = Utilidades.FormatearNumeroPetro(montoTotalFacturado.divide(montoPetro, 6, RoundingMode.HALF_UP).toString());
                montoTotalDeduccionesPetroFormato = Utilidades.FormatearNumeroPetro(montoTotalDeducciones.divide(montoPetro, 6, RoundingMode.HALF_UP).toString());
                montoDeduccionesManteminientoPetroFormato = Utilidades.FormatearNumeroPetro(montoTotalDeduccionesMantenimiento.divide(montoPetro, 6, RoundingMode.HALF_UP).toString());
                montoDeduccionesIvaMantenimientoPetroFormato = Utilidades.FormatearNumeroPetro(montoTotalDeduccionesIvaMantenimiento.divide(montoPetro, 6, RoundingMode.HALF_UP).toString());
                montoDeduccionesComisionBancariaPetroFormato = Utilidades.FormatearNumeroPetro(montoTotalDeduccionesComisionBancaria.divide(montoPetro, 6, RoundingMode.HALF_UP).toString());
                totalAbonoPetroFormato = Utilidades.FormatearNumeroPetro(totalAbono.divide(montoPetro, 6, RoundingMode.HALF_UP).toString());

                params.put("montoTotalFacturado", montoTotalFacturadoFormato);
                params.put("montoTotalDeducciones", montoTotalDeduccionesFormato);
                params.put("montoDeduccionesMantenimiento", montoDeduccionesMantenimientoFormato);
                params.put("montoDeduccionesIvaMantenimiento", montoDeduccionesIvaMantenimientoFormato);
                params.put("montoDeduccionesComisionBancaria", montoDeduccionesComisionBancariaFormato);
                params.put("totalAbono", totalAbonoFormato);

                //Petro
                params.put("montoTotalFacturadoPetro", montoTotalFacturadoPetroFormato);
                params.put("montoTotalDeduccionesPetro", montoTotalDeduccionesPetroFormato);
                params.put("montoDeduccionesMantenimientoPetro", montoDeduccionesManteminientoPetroFormato);
                params.put("montoDeduccionesIvaMantenimientoPetro", montoDeduccionesIvaMantenimientoPetroFormato);
                params.put("montoDeduccionesComisionBancariaPetro", montoDeduccionesComisionBancariaPetroFormato);
                params.put("totalAbonoPetro", totalAbonoPetroFormato);

                Collection<EstadoCuenta> dataOneReport = dataResult;

                dataOneReport = SortUtils.sortCollection(dataOneReport,dr.getColumns());

                //Crea el JRDataSource a partir de la coleccion de datos
                ds = new JRBeanCollectionDataSource(dataOneReport);

                /**
                 * Creamos el objeto que imprimiremos pasando como parametro
                 * el JasperReport object, y el JRDataSource
                 */
                if (ds != null){
                        jp = JasperFillManager.fillReport(jr, params, ds);
                }else{
                        jp = JasperFillManager.fillReport(jr, params);
                }
                exportReportPDF(rutaEdoCuenta+"/"+ano+"/"+nombreMes.toUpperCase()+"/"+terminal.toUpperCase(),rifComercio.toUpperCase()+"_"+terminal+"_"+fechaFin.substring(3,5)+ano);
            }

        } catch (Exception cve) {
            logger.error(cve.getMessage());
        }

    }

    public void generarEdoCuentaExcelComercios(String mes, String ano, int comercio, String rifComercio, String terminal) {

        jpe = new JasperPrint();
        jre = null;
        params = new HashMap();
        dre = new DynamicReport();
        drbe = new DynamicReportBuilder();
        dse = null;
        String pathLogo = "classpath:/img/EdoCuenta.png", pathPetro = "classpath:/img/petro.png", fechaFin, nombreMes;
        System.setProperty("java.awt.headless", "true");

        //Buscamos el ultimo dia del mes seleccionado
        fechaFin = Utilidades.ultimoDiaMes(mes, ano);
        nombreMes = formateador.format(Utilidades.deStringToDate(fechaFin));

        // Definimos un estilo para el detalle de los registros
        Style styleColumns = new Style();
        styleColumns.setHorizontalAlign(HorizontalAlign.CENTER);
        styleColumns.setVerticalAlign(VerticalAlign.MIDDLE);
        styleColumns.setTextColor(Color.BLACK);
        styleColumns.setBorder(Border.PEN_1_POINT());

        // Definimos un estilo para el titulo
        Style styleTitle = new Style();
        styleTitle.setHorizontalAlign(HorizontalAlign.CENTER);

        // Definimos un estilo para el subtitulo
        Style styleSubTitle = new Style();

        // Definimos un estilo para la cabecera de los registros
        Style styleColumnsHeader = new Style();
        styleColumnsHeader.setBorder(Border.PEN_1_POINT());
        styleColumnsHeader.setHorizontalAlign(HorizontalAlign.CENTER);
        styleColumnsHeader.setVerticalAlign(VerticalAlign.MIDDLE);

        try {

            EstadoCuenta elementToInclude;
            Collection<EstadoCuenta> dataResultE=new ArrayList<>();
            String montoTotalFacturadoFormato
                    , montoTotalDeduccionesFormato
                    , montoDeduccionesMantenimientoFormato
                    , montoDeduccionesIvaMantenimientoFormato
                    , montoDeduccionesComisionBancariaFormato
                    , totalAbonoFormato
                    , montoTotalFacturadoPetroFormato
                    , montoTotalDeduccionesPetroFormato
                    , montoDeduccionesManteminientoPetroFormato
                    , montoDeduccionesIvaMantenimientoPetroFormato
                    , montoDeduccionesComisionBancariaPetroFormato
                    , totalAbonoPetroFormato;
            BigDecimal montoTotalFacturado = new BigDecimal(0)
                    , montoTotalDeducciones = new BigDecimal(0)
                    , montoTotalDeduccionesMantenimiento = new BigDecimal(0)
                    , montoTotalDeduccionesIvaMantenimiento = new BigDecimal(0)
                    , montoTotalDeduccionesComisionBancaria = new BigDecimal(0)
                    , totalAbono = new BigDecimal(0)
                    , montoPetro = new BigDecimal(utils.parametro("monto.petro").get(0).getValor())
                    , montoTotalPetro = new BigDecimal(0)
                    , montoTotalFacturadoPetro = new BigDecimal(0)
                    , montoTotalDeduccionesPetro = new BigDecimal(0)
                    , montoTotalDeduccionesMantenimientoPetro = new BigDecimal(0)
                    , montoTotalDeduccionesIvaMantenimientoPetro = new BigDecimal(0)
                    , montoTotalDeduccionesComisionBancarioPetro = new BigDecimal(0)
                    , totalAbonoPetro = new BigDecimal(0);
            montoTotalDeducciones = montoTotalDeducciones.setScale(2, RoundingMode.CEILING);
            montoTotalFacturado = montoTotalFacturado.setScale(2, RoundingMode.CEILING);
            montoPetro = montoPetro.setScale(2, RoundingMode.CEILING);
            montoTotalPetro = montoTotalPetro.setScale(6, RoundingMode.CEILING);
            totalAbono = totalAbono.setScale(2, RoundingMode.CEILING);
            totalAbonoPetro = totalAbonoPetro.setScale(6, RoundingMode.CEILING);
            //JasperCompileManager.compileReport(getClass().getResourceAsStream("/templates/estadosCuentaComercioExcel.jrxml"));
            System.out.println(Utilidades.convierteFechaSqlsinHora(fechaFin));
            System.out.println(comercio);
            System.out.println(terminal);
            List<HistoricoEdoCuenta> resultado = comercioService.getHistoricoEdoCuentaComercios(Utilidades.convierteFechaSqlsinHora(fechaFin), comercio, terminal);
            /*BigDecimal montoDeduccionesMantenimiento = comercioService.getMontoPagadasTipoPlan( terminal, Utilidades.convierteFechaSqlsinHora(fechaFin), 1);
            if(montoDeduccionesMantenimiento == null){
                montoDeduccionesMantenimiento = new BigDecimal(0);
            }else{
                montoDeduccionesMantenimiento = montoDeduccionesMantenimiento.setScale(2, RoundingMode.CEILING);
            }
            BigDecimal montoDeduccionesPorUso = comercioService.getMontoPagadasTipoPlan( terminal, Utilidades.convierteFechaSqlsinHora(fechaFin), 3);
            if(montoDeduccionesPorUso == null){
                montoDeduccionesPorUso = new BigDecimal(0);
            }else{
                montoDeduccionesPorUso = montoDeduccionesPorUso.setScale(2, RoundingMode.CEILING);
            }*/
            if(!resultado.isEmpty()){

                //Parametros del Reporte
                params.put("fechaInicioMes", "01" + fechaFin.substring(2));
                params.put("fechaFinMes", fechaFin);
 		            params.put("terminal", terminal);
                params.put("nombreComercio", resultado.get(0).getDescripcionComercio());
                params.put("rif", resultado.get(0).getRifComercio().substring(0, 1) + "-" + resultado.get(0).getRifComercio().substring(1));
                params.put("direccion", resultado.get(0).getDireccionComercio());
                if(resultado.get(0).getTelefonoHabitacion()==null || resultado.get(0).getTelefonoHabitacion().isEmpty()){
                    params.put("telefono", resultado.get(0).getTelefonoCelular());
                }else{
                    params.put("telefono", resultado.get(0).getTelefonoHabitacion());
                }
                params.put("banco", resultado.get(0).getNombreBanco());
                params.put("logo", pathLogo);

                //Columnas del reporte
                AbstractColumn columnFecha = ColumnBuilder.getNew()
                        .setColumnProperty("fecha", String.class.getName())
                        .setTitle("Fecha")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(125)
                        .build();

                AbstractColumn columnMontoFacturado = ColumnBuilder.getNew()
                        .setColumnProperty("montoFacturado", String.class.getName())
                        .setTitle("Monto Facturado (Bs.)")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(130)
                        .build();

                AbstractColumn columnMantenimiento = ColumnBuilder.getNew()
                        .setColumnProperty("hisComisionMantenimiento", String.class.getName())
                        .setTitle("Tarifa 1000Pagos (Bs.)")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(130)
                        .build();

                AbstractColumn columnIvaMantenimiento = ColumnBuilder.getNew()
                        .setColumnProperty("hisIvaSobreMantenimiento", String.class.getName())
                        .setTitle("Iva (Bs.)")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(130)
                        .build();

                AbstractColumn columnComisionBancaria = ColumnBuilder.getNew()
                        .setColumnProperty("hisComisionBancaria", String.class.getName())
                        .setTitle("Comision Bancaria (Bs.)")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(130)
                        .build();

                AbstractColumn columnaMontoTotal = ColumnBuilder.getNew()
                        .setColumnProperty("montoTotal", String.class.getName())
                        .setTitle("Total Abono (Bs.)")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(130)
                        .build();

                AbstractColumn columnaMontoTotalPetro = ColumnBuilder.getNew()
                        .setColumnProperty("montoTotalPetro", String.class.getName())
                        .setTitle("Total Abono (PTR)")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(130)
                        .build();

                drbe.addColumn(columnFecha)
                    .addColumn(columnMontoFacturado)
                    .addColumn(columnMantenimiento)
                    .addColumn(columnIvaMantenimiento)
                    .addColumn(columnComisionBancaria)
                    .addColumn(columnaMontoTotal)
                    .addColumn(columnaMontoTotalPetro)
                    .setReportName("Estado de Cuenta")
                    .setUseFullPageWidth(true)
                    .setDetailHeight(10)
                    .setLeftMargin(40)
                    .setRightMargin(40)
                    .setWhenNoDataNoPages()
                    .setIgnorePagination(true)
                    .setDefaultStyles(styleTitle, styleSubTitle, styleColumnsHeader, styleColumns);

                drbe.setTemplateFile("/templates/estadosCuentaComercioExcel.jrxml");

                for(HistoricoEdoCuenta registros: resultado){
                    elementToInclude = new EstadoCuenta();
                    elementToInclude.setFecha(registros.getFecha());
                    elementToInclude.setMontoFacturado(Utilidades.FormatearNumero(registros.getMontoFacturado().toString()));
                    elementToInclude.setHisComisionMantenimiento(Utilidades.FormatearNumero(registros.getHisComisionMantenimiento().toString()));
                    elementToInclude.setHisIvaSobreMantenimiento(Utilidades.FormatearNumero(registros.getHisIvaSobreMantenimiento().toString()));
                    elementToInclude.setHisComisionBancaria(Utilidades.FormatearNumero(registros.getHisComisionBancaria().toString()));
                    elementToInclude.setMontoTotal(Utilidades.FormatearNumero(registros.getMontoTotal().toString()));
                    
                    montoTotalPetro = registros.getMontoTotal().divide(montoPetro, 6, RoundingMode.HALF_UP);
                    elementToInclude.setMontoTotalPetro(Utilidades.FormatearNumeroPetro(montoTotalPetro.toString()));
                    //acumulacion totales
                    montoTotalFacturado = montoTotalFacturado.add(registros.getMontoFacturado());
                    //montoTotalDeducciones = montoTotalDeducciones.add(registros.getComisionMilPagos());
                    montoTotalDeducciones = montoTotalDeducciones.add(registros.getHisComisionMantenimiento()).add(registros.getHisIvaSobreMantenimiento()).add(registros.getHisComisionBancaria());
                    montoTotalDeduccionesMantenimiento = montoTotalDeduccionesMantenimiento.add(registros.getHisComisionMantenimiento());                    
                    montoTotalDeduccionesIvaMantenimiento = montoTotalDeduccionesIvaMantenimiento.add(registros.getHisIvaSobreMantenimiento());                    
                    montoTotalDeduccionesComisionBancaria = montoTotalDeduccionesComisionBancaria.add(registros.getHisComisionBancaria());                    
                    totalAbono = totalAbono.add(registros.getMontoTotal());
                    dataResultE.add(elementToInclude);
                }

                montoTotalFacturadoFormato = Utilidades.FormatearNumero(montoTotalFacturado.toString());
                
                montoTotalDeduccionesFormato = Utilidades.FormatearNumero(montoTotalDeducciones.toString());
                montoDeduccionesMantenimientoFormato = Utilidades.FormatearNumero(montoTotalDeduccionesMantenimiento.toString());
                montoDeduccionesIvaMantenimientoFormato = Utilidades.FormatearNumero(montoTotalDeduccionesIvaMantenimiento.toString());
                montoDeduccionesComisionBancariaFormato = Utilidades.FormatearNumero(montoTotalDeduccionesComisionBancaria.toString());
                totalAbonoFormato = Utilidades.FormatearNumero(totalAbono.toString());

                //Petro
                montoTotalFacturadoPetroFormato = Utilidades.FormatearNumeroPetro(montoTotalFacturado.divide(montoPetro, 6, RoundingMode.HALF_UP).toString());
                montoTotalDeduccionesPetroFormato = Utilidades.FormatearNumeroPetro(montoTotalDeducciones.divide(montoPetro, 6, RoundingMode.HALF_UP).toString());
                montoDeduccionesManteminientoPetroFormato = Utilidades.FormatearNumeroPetro(montoTotalDeduccionesMantenimiento.divide(montoPetro, 6, RoundingMode.HALF_UP).toString());
                montoDeduccionesIvaMantenimientoPetroFormato = Utilidades.FormatearNumeroPetro(montoTotalDeduccionesIvaMantenimiento.divide(montoPetro, 6, RoundingMode.HALF_UP).toString());
                montoDeduccionesComisionBancariaPetroFormato = Utilidades.FormatearNumeroPetro(montoTotalDeduccionesComisionBancaria.divide(montoPetro, 6, RoundingMode.HALF_UP).toString());
                totalAbonoPetroFormato = Utilidades.FormatearNumeroPetro(totalAbono.divide(montoPetro, 6, RoundingMode.HALF_UP).toString());

                params.put("montoTotalFacturado", montoTotalFacturadoFormato);
                params.put("montoTotalDeducciones", montoTotalDeduccionesFormato);
                params.put("montoDeduccionesMantenimiento", montoDeduccionesMantenimientoFormato);
                params.put("montoDeduccionesIvaMantenimiento", montoDeduccionesIvaMantenimientoFormato);
                params.put("montoDeduccionesComisionBancaria", montoDeduccionesComisionBancariaFormato);
                params.put("totalAbono", totalAbonoFormato);

                //Petro
                params.put("montoTotalFacturadoPetro", montoTotalFacturadoPetroFormato);
                params.put("montoTotalDeduccionesPetro", montoTotalDeduccionesPetroFormato);
                params.put("montoDeduccionesMantenimientoPetro", montoDeduccionesManteminientoPetroFormato);
                params.put("montoDeduccionesIvaMantenimientoPetro", montoDeduccionesIvaMantenimientoPetroFormato);
                params.put("montoDeduccionesComisionBancariaPetro", montoDeduccionesComisionBancariaPetroFormato);
                params.put("totalAbonoPetro", totalAbonoPetroFormato);

                Collection<EstadoCuenta> dataOneReportE = dataResultE;

                dre = drbe.build();

                dataOneReportE = SortUtils.sortCollection(dataOneReportE,dre.getColumns());

                //Crea el JRDataSource a partir de la coleccion de datos
                dse = new JRBeanCollectionDataSource(dataOneReportE);

                /**
                 * Creamos el objeto JasperReport que pasamos como parametro a
                 * DynamicReport,junto con una nueva instancia de ClassicLayoutManager
                 * y el JRDataSource
                 */
                jre = DynamicJasperHelper.generateJasperReport(dre, getLayoutManager(), params);

                /**
                 * Creamos el objeto que imprimiremos pasando como parametro
                 * el JasperReport object, y el JRDataSource
                 */
                if (dse != null){
                        jpe = JasperFillManager.fillReport(jre, params, dse);
                }else{
                        jpe = JasperFillManager.fillReport(jre, params);
                }

                exportReportXLS(rutaEdoCuenta+"/"+ano+"/"+nombreMes.toUpperCase()+"/"+terminal.toUpperCase(),rifComercio.toUpperCase()+"_"+terminal+"_"+fechaFin.substring(3,5)+ano);
            }

        } catch (Exception cve) {
            logger.error(cve.getMessage());
        }

    }
    
    @Async
    public void generarFacturacionByComercio(String mes, String ano, String contrato, int numeroControl,  List<HistoricoFacturacion> resultado, List<HistoricoFacturacion> terminales) {
        
        String telefono, nombreApellidoAliado;
        
        try {
            for(HistoricoFacturacion registros: resultado){

                if(!registros.getTelefonoHabitacion().isEmpty()){
                    telefono = registros.getTelefonoHabitacion();
                }else{
                    telefono = registros.getTelefonoCelular();
                }

                if(registros.getApellidoAliado()!=null && !registros.getApellidoAliado().isEmpty()){
                    nombreApellidoAliado = registros.getNombreAliado().replace(" ","_").toUpperCase() + "_" + registros.getApellidoAliado().replace(" ","_").toUpperCase();
                }else{
                    nombreApellidoAliado = "DESCONOCIDO";
                }

                generarFacturacionPDFComercios(contrato, registros.getCodigoComercio(), registros.getRifComercio(), registros.getDescripcionComercio(), registros.getDireccionComercio(), telefono, terminales.get(0).getTerminales(), registros.getMontoTotal(), mes, ano, numeroControl, nombreApellidoAliado);
                generarFacturacionExcelComercios(contrato, registros.getCodigoComercio(), registros.getRifComercio(), registros.getDescripcionComercio(), registros.getDireccionComercio(), telefono, terminales.get(0).getTerminales(), registros.getMontoTotal(), mes, ano, numeroControl, nombreApellidoAliado);

                numeroControl++;
            } 
        
            generarLibroVentasExcel(mes, ano);
            
        } catch (Exception cve) {
            logger.error(cve.getMessage());
        }
    }
    
    @Async
    public void generarFacturacionComercios(String mes, String ano, String fechaFin, String contrato, int tipoContrato, int numeroControl,  List<HistoricoFacturacion> resultado) {
        
        String telefono, nombreApellidoAliado;
        List<HistoricoFacturacion> terminales;
        
        try {
            
            for(HistoricoFacturacion registros: resultado){

                if(!registros.getTelefonoHabitacion().isEmpty()){
                    telefono = registros.getTelefonoHabitacion();
                }else{
                    telefono = registros.getTelefonoCelular();
                }

                if(registros.getApellidoAliado()!=null && !registros.getApellidoAliado().isEmpty()){
                    nombreApellidoAliado = registros.getNombreAliado().replace(" ","_").toUpperCase() + "_" + registros.getApellidoAliado().replace(" ","_").toUpperCase();
                }else{
                    nombreApellidoAliado = "DESCONOCIDO";
                }
                
                terminales = comercioService.getHistoricoFacturacionComercios(3, tipoContrato, Utilidades.convierteFechaSqlsinHora(fechaFin), registros.getCodigoComercio());

                generarFacturacionPDFComercios(contrato, registros.getCodigoComercio(), registros.getRifComercio(), registros.getDescripcionComercio(), registros.getDireccionComercio(), telefono, terminales.get(0).getTerminales(), registros.getMontoTotal(), mes, ano, numeroControl, nombreApellidoAliado);
                generarFacturacionExcelComercios(contrato, registros.getCodigoComercio(), registros.getRifComercio(), registros.getDescripcionComercio(), registros.getDireccionComercio(), telefono, terminales.get(0).getTerminales(), registros.getMontoTotal(), mes, ano, numeroControl, nombreApellidoAliado);

                numeroControl++;
            }
            
            generarLibroVentasExcel(mes, ano);
            
        } catch (Exception cve) {
            logger.error(cve.getMessage());
        }
    }
    
    public void generarFacturacionPDFComercios(String tipoContrato,  int codigoComercio, String rifComercio, String razonSocial, String direccion, String telefono, String terminales, BigDecimal monto, String mes, String ano, int numeroControl, String nombreApellidoAliado) {

        jp = new JasperPrint();
        jr = null;
        params = new HashMap();
        dr = new DynamicReport();
        String fechaFin, nombreMes, pathPetro = "classpath:/img/petro.png";
        System.setProperty("java.awt.headless", "true");

        JRDataSource vacio = new JREmptyDataSource(1);

        ds = vacio;

        //Buscamos el ultimo dia del mes seleccionado
        fechaFin = Utilidades.ultimoDiaMes(mes, ano);
        nombreMes = formateador.format(Utilidades.deStringToDate(fechaFin));

        try {

            double iva = Double.parseDouble(utils.parametro("facturacion.IVA").get(0).getValor()) / 100;
            double divisor1 = iva+1;
            BigDecimal montoTotalTransado = new BigDecimal(0),montoTotalFactura = new BigDecimal(0), baseImponible = new BigDecimal(0), montoIVA = new BigDecimal(0), total = new BigDecimal(0), divisor = new BigDecimal("0.9"), divisorBI = new BigDecimal("0.10"), porcentajeIva = new BigDecimal(iva), divisorIVA = new BigDecimal(String.valueOf(divisor1)), montoPetro = new BigDecimal(utils.parametro("monto.petro").get(0).getValor()), totalPetro = new BigDecimal(0);
            montoTotalTransado = montoTotalTransado.setScale(2, RoundingMode.CEILING);
            montoTotalFactura = montoTotalTransado.setScale(2, RoundingMode.CEILING);
            baseImponible = baseImponible.setScale(2, RoundingMode.CEILING);
            montoIVA = montoIVA.setScale(2, RoundingMode.CEILING);
            montoPetro = montoPetro.setScale(2, RoundingMode.CEILING);
            total = total.setScale(2, RoundingMode.CEILING);
            totalPetro = totalPetro.setScale(6, RoundingMode.CEILING);
            divisorIVA = divisorIVA.setScale(2, RoundingMode.CEILING);
            porcentajeIva = porcentajeIva.setScale(2, RoundingMode.DOWN);
            String baseImponibleFormato, montoIVAFormato, totalFormato, totalPetroFormato, porcentajeRebaja, articulo;

            //Valida si esta activo el descuento
            if("0".equals(utils.parametro("facturacion.descuento").get(0).getValor())){
                jr =  JasperCompileManager.compileReport(getClass().getResourceAsStream("/templates/facturacionComercioDescuentoPDF.jrxml"));
            }else{
                jr =  JasperCompileManager.compileReport(getClass().getResourceAsStream("/templates/facturacionComercioPDF.jrxml"));
            }

            //Parametros del Reporte
            params.put("numeroControl", String.valueOf(numeroControl));
            params.put("fechaFinMes", fechaFin);
            params.put("nombreMes", "Mes de " + nombreMes.toUpperCase());
            params.put("terminal", "Terminal: " + terminales + "<br>");
            params.put("nombreComercio", razonSocial);
            params.put("rif", rifComercio.substring(0, 1) + "-" + rifComercio.substring(1));
            params.put("direccion", direccion);
            params.put("telefono", telefono);

            //Calculo de los montos para la factura segun el tope (Gaceta Oficial)
            montoTotalTransado = monto.divide(divisor, 2, RoundingMode.CEILING);

            //Monto factura
            //montoTotalFactura = montoTotalTransado.multiply(divisorBI);
            montoTotalFactura = monto.add(monto.multiply(porcentajeIva));

            //Valida si exta activo el descuento
            if("0".equals(utils.parametro("facturacion.descuento").get(0).getValor())){

                double porcentaje1 = Double.parseDouble(utils.parametro("facturacion.porcentajeIVA.rango1").get(0).getValor()) / 100;
                double porcentaje2 = Double.parseDouble(utils.parametro("facturacion.porcentajeIVA.rango2").get(0).getValor()) / 100;
                BigDecimal divisorIVA_1 = new BigDecimal((porcentaje1 + 1)), divisorIVA_2 = new BigDecimal((porcentaje2 + 1)), porcentajeIva_1 = new BigDecimal(porcentaje1), porcentajeIva_2 = new BigDecimal(porcentaje2), tope = new BigDecimal(utils.parametro("facturacion.monto.tope").get(0).getValor());
                divisorIVA_1 = divisorIVA_1.setScale(2, RoundingMode.CEILING);
                divisorIVA_2 = divisorIVA_2.setScale(2, RoundingMode.CEILING);
                porcentajeIva_1 = porcentajeIva_1.setScale(2, RoundingMode.CEILING);
                porcentajeIva_2 = porcentajeIva_2.setScale(2, RoundingMode.CEILING);

                if(montoTotalFactura.compareTo(tope)>0){
                    baseImponible = montoTotalFactura.divide(divisorIVA_2, 2, RoundingMode.CEILING);
                    montoIVA = baseImponible.multiply(porcentajeIva_2);
                    porcentajeRebaja = utils.parametro("facturacion.porcentajeIVA.rango2").get(0).getValor();
                    articulo = utils.parametro("facturacion.articulo.rango2").get(0).getValor();
                }else{
                    baseImponible = montoTotalFactura.divide(divisorIVA_1, 2, RoundingMode.CEILING);
                    montoIVA = baseImponible.multiply(porcentajeIva_1);
                    porcentajeRebaja = utils.parametro("facturacion.porcentajeIVA.rango1").get(0).getValor();
                    articulo = utils.parametro("facturacion.articulo.rango1").get(0).getValor();
                }

                params.put("articulo", articulo);
                params.put("porcentajeRebaja", porcentajeRebaja);

            }else{
                baseImponible = montoTotalFactura.divide(divisorIVA, 2, RoundingMode.CEILING);
                montoIVA = baseImponible.multiply(porcentajeIva);
            }

            total = baseImponible.add(montoIVA);
            totalPetro = total.divide(montoPetro, 6, RoundingMode.HALF_UP);

            baseImponibleFormato = Utilidades.FormatearNumero(baseImponible.toString());
            montoIVAFormato = Utilidades.FormatearNumero(montoIVA.toString());
            totalFormato = Utilidades.FormatearNumero(total.toString());
            totalPetroFormato = Utilidades.FormatearNumeroPetro(totalPetro.toString());

            params.put("iva", utils.parametro("facturacion.IVA").get(0).getValor());
            params.put("baseImponible", baseImponibleFormato);
            params.put("montoIVA", montoIVAFormato);
            params.put("total", totalFormato);
            params.put("totalPetro", totalPetroFormato);

            /**
             * Creamos el objeto que imprimiremos pasando como parametro
             * el JasperReport object, y el JRDataSource
             */
            if (ds != null){
                jp = JasperFillManager.fillReport(jr, params, ds);
            }else{
                jp = JasperFillManager.fillReport(jr, params);
            }

            exportReportPDF(rutaFacturacion+"/"+tipoContrato.toUpperCase()+"/"+ano+"/"+nombreMes.toUpperCase()+"/"+nombreApellidoAliado+"/"+numeroControl+"_"+rifComercio,rifComercio.toUpperCase()+"_fact");

        } catch (Exception cve) {
            logger.error(cve.getMessage());
        }

    }

    public void generarFacturacionExcelComercios(String tipoContrato, int codigoComercio, String rifComercio, String razonSocial, String direccion, String telefono, String terminales, BigDecimal monto, String mes, String ano, int numeroControl, String nombreApellidoAliado) {

        jpe = new JasperPrint();

        jre = null;
        params = new HashMap();
        dre = new DynamicReport();
        drbe = new DynamicReportBuilder();
        dse = null;        
        String fechaFin, nombreMes, pathPetro = "classpath:/img/petro.png";
        System.setProperty("java.awt.headless", "true");
        JRDataSource vacio = new JREmptyDataSource(1);

        dse = vacio;

        //Buscamos el ultimo dia del mes seleccionado
        fechaFin = Utilidades.ultimoDiaMes(mes, ano);
        nombreMes = formateador.format(Utilidades.deStringToDate(fechaFin));
        
        Factura facturacion = new Factura();
        
        try {

            double iva = Double.parseDouble(utils.parametro("facturacion.IVA").get(0).getValor()) / 100;
            double divisor1 = iva+1;
            BigDecimal montoTotalTransado = new BigDecimal(0),
                    montoTotalFactura = new BigDecimal(0), 
                    baseImponible = new BigDecimal(0), 
                    montoIVA = new BigDecimal(0), 
                    total = new BigDecimal(0), 
                    divisor = new BigDecimal("0.9"), 
                    divisorBI = new BigDecimal("0.10"), 
                    porcentajeIva = new BigDecimal(iva), 
                    divisorIVA = new BigDecimal(String.valueOf(divisor1)), 
                    montoPetro = new BigDecimal(utils.parametro("monto.petro").get(0).getValor()), 
                    totalPetro = new BigDecimal(0);
            montoTotalTransado = montoTotalTransado.setScale(2, RoundingMode.CEILING);
            montoTotalFactura = montoTotalTransado.setScale(2, RoundingMode.CEILING);
            baseImponible = baseImponible.setScale(2, RoundingMode.CEILING);
            montoIVA = montoIVA.setScale(2, RoundingMode.CEILING);
            montoPetro = montoPetro.setScale(2, RoundingMode.CEILING);
            total = total.setScale(2, RoundingMode.CEILING);
            totalPetro = totalPetro.setScale(6, RoundingMode.CEILING);
            divisorIVA = divisorIVA.setScale(2, RoundingMode.CEILING);
            porcentajeIva = porcentajeIva.setScale(2, RoundingMode.DOWN);
            String baseImponibleFormato, montoIVAFormato, totalFormato, totalPetroFormato, porcentajeRebaja, articulo;
            JasperCompileManager.compileReport(getClass().getResourceAsStream("/templates/facturacionComercioExcel.jrxml"));
            JasperCompileManager.compileReport(getClass().getResourceAsStream("/templates/facturacionComercioDescuentoExcel.jrxml"));

            //Parametros del Reporte
            params.put("numeroControl", String.valueOf(numeroControl));
            params.put("fechaFinMes", fechaFin);
            params.put("nombreMes", "Mes de " + nombreMes.toUpperCase());
            params.put("terminal", "Terminal: " + terminales + "<br>");
            params.put("nombreComercio", razonSocial);
            params.put("rif", rifComercio.substring(0, 1) + "-" + rifComercio.substring(1));
            params.put("direccion", direccion);
            params.put("telefono", telefono);

            //Calculo de los montos para la factura segun el tope (Gaceta Oficial)
            montoTotalTransado = monto.divide(divisor, 2, RoundingMode.CEILING);

            //Monto factura
            //montoTotalFactura = montoTotalTransado.multiply(divisorBI);
            montoTotalFactura = monto.add(monto.multiply(porcentajeIva));

            //Valida si exta activo el descuento
            if("0".equals(utils.parametro("facturacion.descuento").get(0).getValor())){

                double porcentaje1 = Double.parseDouble(utils.parametro("facturacion.porcentajeIVA.rango1").get(0).getValor()) / 100;
                double porcentaje2 = Double.parseDouble(utils.parametro("facturacion.porcentajeIVA.rango2").get(0).getValor()) / 100;
                BigDecimal divisorIVA_1 = new BigDecimal((porcentaje1 + 1)), 
                        divisorIVA_2 = new BigDecimal((porcentaje2 + 1)), 
                        porcentajeIva_1 = new BigDecimal(porcentaje1), 
                        porcentajeIva_2 = new BigDecimal(porcentaje2), 
                        tope = new BigDecimal(utils.parametro("facturacion.monto.tope").get(0).getValor());
                divisorIVA_1 = divisorIVA_1.setScale(2, RoundingMode.CEILING);
                divisorIVA_2 = divisorIVA_2.setScale(2, RoundingMode.CEILING);
                porcentajeIva_1 = porcentajeIva_1.setScale(2, RoundingMode.CEILING);
                porcentajeIva_2 = porcentajeIva_2.setScale(2, RoundingMode.CEILING);

                if(montoTotalFactura.compareTo(tope)>0){
                    baseImponible = montoTotalFactura.divide(divisorIVA_2, 2, RoundingMode.CEILING);
                    montoIVA = baseImponible.multiply(porcentajeIva_2);
                    porcentajeRebaja = utils.parametro("facturacion.porcentajeIVA.rango2").get(0).getValor();
                    articulo = utils.parametro("facturacion.articulo.rango2").get(0).getValor();
                }else{
                    baseImponible = montoTotalFactura.divide(divisorIVA_1, 2, RoundingMode.CEILING);
                    montoIVA = baseImponible.multiply(porcentajeIva_1);
                    porcentajeRebaja = utils.parametro("facturacion.porcentajeIVA.rango1").get(0).getValor();
                    articulo = utils.parametro("facturacion.articulo.rango1").get(0).getValor();
                }

                params.put("articulo", articulo);
                params.put("porcentajeRebaja", porcentajeRebaja);

            }else{
                baseImponible = montoTotalFactura.divide(divisorIVA, 2, RoundingMode.CEILING);
                montoIVA = baseImponible.multiply(porcentajeIva);
            }

            total = baseImponible.add(montoIVA);
            totalPetro = total.divide(montoPetro, 6, RoundingMode.HALF_UP);

            baseImponibleFormato = Utilidades.FormatearNumero(baseImponible.toString());
            montoIVAFormato = Utilidades.FormatearNumero(montoIVA.toString());
            totalFormato = Utilidades.FormatearNumero(total.toString());
            totalPetroFormato = Utilidades.FormatearNumeroPetro(totalPetro.toString());

            params.put("iva", utils.parametro("facturacion.IVA").get(0).getValor());
            params.put("baseImponible", baseImponibleFormato);
            params.put("montoIVA", montoIVAFormato);
            params.put("total", totalFormato);
            params.put("totalPetro", totalPetroFormato);

            drbe.setReportName("FACT " + utils.parametro("facturacion.IVA").get(0).getValor() + "%")
                    .setUseFullPageWidth(true)
                    .setIgnorePagination(true);

            //Valida si exta activo el descuento
            if("0".equals(utils.parametro("facturacion.descuento").get(0).getValor())){
                drbe.setTemplateFile("/templates/facturacionComercioDescuentoExcel.jrxml");
            }else{
                drbe.setTemplateFile("/templates/facturacionComercioExcel.jrxml");
            }

            dre = drbe.build();

            /**
             * Creamos el objeto JasperReport que pasamos como parametro a
             * DynamicReport,junto con una nueva instancia de ClassicLayoutManager
             * y el JRDataSource
             */
            jre = DynamicJasperHelper.generateJasperReport(dre, getLayoutManager(), params);

            /**
             * Creamos el objeto que imprimiremos pasando como parametro
             * el JasperReport object, y el JRDataSource
             */
            if (dse != null){
                    jpe = JasperFillManager.fillReport(jre, params, dse);
            }else{
                    jpe = JasperFillManager.fillReport(jre, params);
            }
            
            facturasDao.updateFactura(codigoComercio, mes, ano);
                        
            facturacion.setCodigoComercio(codigoComercio);
            facturacion.setNumeroFactura(String.valueOf(numeroControl));
            facturacion.setNumeroControl(String.valueOf(numeroControl));
            facturacion.setBaseImponible(baseImponible);
            facturacion.setIva(utils.parametro("facturacion.IVA").get(0).getValor());
            facturacion.setMontoIVA(montoIVA);
            facturacion.setMontoTotal(total);
            facturacion.setMontoPetro(totalPetro);
            facturacion.setMes(mes);
            facturacion.setAno(ano);
            facturacion.setFecha(Utilidades.getFechaActualSql());
            facturacion.setEstatus(1);
            
            facturasDao.saveFacturacion(facturacion);
            
            exportReportXLS(rutaFacturacion+"/"+tipoContrato.toUpperCase()+"/"+ano+"/"+nombreMes.toUpperCase()+"/"+nombreApellidoAliado+"/"+numeroControl+"_"+rifComercio,rifComercio.toUpperCase()+"_fact");                        
            
        } catch (Exception cve) {
            logger.error(cve.getMessage());
        }

    }
    
    @Async
    public void generarLibroVentasExcel(String mes, String ano) {

        jpe = new JasperPrint();
        jre = null;
        params = new HashMap();
        dre = new DynamicReport();
        drbe = new DynamicReportBuilder();
        dse = null;
        String pathLogo = "classpath:/img/EdoCuenta.png", fechaFin, nombreMes;
        System.setProperty("java.awt.headless", "true");

        //Buscamos el ultimo dia del mes seleccionado
        fechaFin = Utilidades.ultimoDiaMes(mes, ano);
        nombreMes = formateador.format(Utilidades.deStringToDate(fechaFin));

        // Definimos un estilo para el detalle de los registros
        Style styleColumns = new Style();
        styleColumns.setHorizontalAlign(HorizontalAlign.CENTER);
        styleColumns.setVerticalAlign(VerticalAlign.MIDDLE);
        styleColumns.setTextColor(Color.BLACK);
        styleColumns.setBorder(Border.PEN_1_POINT());

        // Definimos un estilo para el titulo
        Style styleTitle = new Style();
        styleTitle.setHorizontalAlign(HorizontalAlign.CENTER);

        // Definimos un estilo para el subtitulo
        Style styleSubTitle = new Style();

        // Definimos un estilo para la cabecera de los registros
        Style styleColumnsHeader = new Style();
        styleColumnsHeader.setBorder(Border.PEN_1_POINT());
        styleColumnsHeader.setHorizontalAlign(HorizontalAlign.CENTER);
        styleColumnsHeader.setVerticalAlign(VerticalAlign.MIDDLE);

        try {

            LibroVentas elementToInclude;
            Collection<LibroVentas> dataResultE=new ArrayList<>();
            String montoTotalFacturadoFormato,  baseImponibleFormato, ivaFormato;
            BigDecimal montoTotalFacturado = new BigDecimal(0), baseImponible = new BigDecimal(0), iva = new BigDecimal(0);            
            montoTotalFacturado = montoTotalFacturado.setScale(2, RoundingMode.CEILING);
            baseImponible = baseImponible.setScale(2, RoundingMode.CEILING);
            iva = iva.setScale(6, RoundingMode.CEILING);
            JasperCompileManager.compileReport(getClass().getResourceAsStream("/templates/libroVentasExcel.jrxml"));
            List<Factura> resultado = facturasDao.getFacturacion(mes, ano);

            if(!resultado.isEmpty()){

                //Parametros del Reporte
                params.put("ano", ano);
                params.put("nombreMes", nombreMes.toUpperCase());
 		params.put("logo", pathLogo);

                //Columnas del reporte
                AbstractColumn columnFecha = ColumnBuilder.getNew()
                        .setColumnProperty("fecha", String.class.getName())
                        .setTitle("FECHA")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(125)
                        .build();

                AbstractColumn columnRIF = ColumnBuilder.getNew()
                        .setColumnProperty("rif", String.class.getName())
                        .setTitle("Nª R.I.F.")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(130)
                        .build();                                

                AbstractColumn columnaRazonSocial = ColumnBuilder.getNew()
                        .setColumnProperty("razonSocial", String.class.getName())
                        .setTitle("R.SOCIAL DEL CLIENTE")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(250)
                        .build();

                AbstractColumn columnaPlanilla = ColumnBuilder.getNew()
                        .setColumnProperty("planilla", String.class.getName())
                        .setTitle("Nª PLANILLA DE EXPORTACION")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(130)
                        .build();

                AbstractColumn columnaFactura = ColumnBuilder.getNew()
                        .setColumnProperty("numeroFactura", String.class.getName())
                        .setTitle("NRO DE FACTURA")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(130)
                        .build();
                
                AbstractColumn columnaNumeroControl = ColumnBuilder.getNew()
                        .setColumnProperty("numeroControl", String.class.getName())
                        .setTitle("NRO DE CONTROL")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(130)
                        .build();

                AbstractColumn columnaNotaCredito = ColumnBuilder.getNew()
                        .setColumnProperty("notaCredito", String.class.getName())
                        .setTitle("NUMERO DE NOTA DE CREDITO")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(130)
                        .build();

                AbstractColumn columnaFacturaAfectada = ColumnBuilder.getNew()
                        .setColumnProperty("facturaAfectada", String.class.getName())
                        .setTitle("NUMERO DE FACTURA AFECTADA")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(130)
                        .build();

                AbstractColumn columnaTotalFacturado = ColumnBuilder.getNew()
                        .setColumnProperty("montoTotal", String.class.getName())
                        .setTitle("TOTAL VENTAS INCLUYENDO I.V.A")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(200)
                        .build();
                
                AbstractColumn columnaVentasNoGravadas = ColumnBuilder.getNew()
                        .setColumnProperty("ventasNoGravadas", String.class.getName())
                        .setTitle("VENTAS INTERNAS NO GRAVADAS")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(200)
                        .build();

                AbstractColumn columnaBaseImponible = ColumnBuilder.getNew()
                        .setColumnProperty("baseImponible", String.class.getName())
                        .setTitle("BASE IMPONIBLE")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(200)
                        .build();
                
                AbstractColumn columnPorcentaje = ColumnBuilder.getNew()
                        .setColumnProperty("porcentaje", String.class.getName())
                        .setTitle("%")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(130)
                        .build();

                AbstractColumn columnaImpuesto = ColumnBuilder.getNew()
                        .setColumnProperty("iva", String.class.getName())
                        .setTitle("IMPUESTO I.V.A")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(200)
                        .build();

                AbstractColumn columnaIVARetenido = ColumnBuilder.getNew()
                        .setColumnProperty("ivaRetenido", String.class.getName())
                        .setTitle("I.V.A RETENIDO")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(130)
                        .build();

                AbstractColumn columnaIVAPercibido = ColumnBuilder.getNew()
                        .setColumnProperty("ivaRecibido", String.class.getName())
                        .setTitle("I.V.A. PERCIBIDO")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(130)
                        .build();
                
                drbe.addColumn(columnFecha)
                    .addColumn(columnRIF)
                    .addColumn(columnaRazonSocial)
                    .addColumn(columnaPlanilla)
                    .addColumn(columnaFactura)
                    .addColumn(columnaNumeroControl)                        
                    .addColumn(columnaNotaCredito)
                    .addColumn(columnaFacturaAfectada)
                    .addColumn(columnaTotalFacturado)
                    .addColumn(columnaVentasNoGravadas)
                    .addColumn(columnaBaseImponible)                            
                    .addColumn(columnPorcentaje)
                    .addColumn(columnaImpuesto)
                    .addColumn(columnaIVARetenido)
                    .addColumn(columnaIVAPercibido)                          
                    .setReportName("LIBRO VENTAS" + nombreMes.toUpperCase() + " " + ano)
                    .setUseFullPageWidth(true)
                    .setDetailHeight(10)
                    .setLeftMargin(20)
                    .setRightMargin(20)
                    .setWhenNoDataNoPages()
                    .setIgnorePagination(true)
                    .setDefaultStyles(styleTitle, styleSubTitle, styleColumnsHeader, styleColumns);

                drbe.setTemplateFile("/templates/libroVentasExcel.jrxml");

                for(Factura registros: resultado){
                    elementToInclude = new LibroVentas();
                    elementToInclude.setFecha(Utilidades.cambiaFormatoFecha2(registros.getFecha().toString()));
                    elementToInclude.setRif(registros.getComercio().getRifComercio());
                    elementToInclude.setRazonSocial(registros.getComercio().getDescripcionComercio());
                    elementToInclude.setPlanilla("");
                    elementToInclude.setNumeroFactura(registros.getNumeroFactura());
                    elementToInclude.setNumeroControl(registros.getNumeroFactura());
                    elementToInclude.setNotaCredito("");
                    elementToInclude.setFacturaAfectada("");                  
                    elementToInclude.setMontoTotal(Utilidades.FormatearNumero(registros.getMontoTotal().toString()));
                    elementToInclude.setVentasNoGravadas("");
                    elementToInclude.setBaseImponible(Utilidades.FormatearNumero(registros.getBaseImponible().toString()));
                    elementToInclude.setPorcentaje(registros.getIva());
                    elementToInclude.setIva(Utilidades.FormatearNumero(registros.getMontoIVA().toString()));
                    elementToInclude.setIvaRetenido("");
                    elementToInclude.setIvaRecibido("");
                    
                    montoTotalFacturado = montoTotalFacturado.add(registros.getMontoTotal());
                    baseImponible = baseImponible.add(registros.getBaseImponible());
                    iva = iva.add(registros.getMontoIVA());
                    
                    dataResultE.add(elementToInclude);
                }
                
                montoTotalFacturadoFormato = Utilidades.FormatearNumero(montoTotalFacturado.toString());
                baseImponibleFormato = Utilidades.FormatearNumero(baseImponible.toString());
                ivaFormato = Utilidades.FormatearNumero(iva.toString());               

                params.put("montoTotalFacturado", montoTotalFacturadoFormato);
                params.put("baseImponible", baseImponibleFormato);
                params.put("iva", ivaFormato);
                
                Collection<LibroVentas> dataOneReportE = dataResultE;

                dre = drbe.build();

                dataOneReportE = SortUtils.sortCollection(dataOneReportE,dre.getColumns());

                //Crea el JRDataSource a partir de la coleccion de datos
                dse = new JRBeanCollectionDataSource(dataOneReportE);

                /**
                 * Creamos el objeto JasperReport que pasamos como parametro a
                 * DynamicReport,junto con una nueva instancia de ClassicLayoutManager
                 * y el JRDataSource
                 */
                jre = DynamicJasperHelper.generateJasperReport(dre, getLayoutManager(), params);

                /**
                 * Creamos el objeto que imprimiremos pasando como parametro
                 * el JasperReport object, y el JRDataSource
                 */
                if (dse != null){
                        jpe = JasperFillManager.fillReport(jre, params, dse);
                }else{
                        jpe = JasperFillManager.fillReport(jre, params);
                }

                exportReportXLS(rutaLibroVentas+"/LIBRO_VENTAS/"+ano+"/"+nombreMes.toUpperCase(),"LibroVentas"+"_"+nombreMes.toUpperCase()+"_"+ano);
            }

        } catch (Exception cve) {
            logger.error(cve.getMessage());
        }

    }
    
    protected LayoutManager getLayoutManager() {
            return new ClassicLayoutManager();
    }

    public void exportReportPDF(String ruta, String nombreArchivo) throws Exception {

        final String path=ruta;

        JRPdfExporter exporterPDF = new JRPdfExporter();

        File outputFile = new File(path,nombreArchivo + ".pdf");

        if (outputFile.delete())
          System.out.println("El fichero ha sido borrado satisfactoriamente");
        else
          System.out.println("El fichero no puede ser borrado");

        outputFile.getParentFile().mkdirs();
        if(outputFile.exists()){
            outputFile.delete();
        }

        FileOutputStream fos = new FileOutputStream(outputFile);

        exporterPDF.setParameter(JRExporterParameter.JASPER_PRINT, jp);
        exporterPDF.setParameter(JRExporterParameter.OUTPUT_STREAM, fos);

        exporterPDF.exportReport();

        fos.close();

    }

    public void exportReportXLS(String ruta, String nombreArchivo) throws Exception {

        final String path=ruta;

        JRXlsExporter exporterXLS = new JRXlsExporter();
        File outputFileXLS = new File(path,nombreArchivo + ".xls");

        if (outputFileXLS.delete())
          System.out.println("El fichero ha sido borrado satisfactoriamente");
        else
          System.out.println("El fichero no puede ser borrado");
          
        outputFileXLS.getParentFile().mkdirs();
        if(outputFileXLS.exists()){
            outputFileXLS.delete();
        }

        FileOutputStream fosXLS = new FileOutputStream(outputFileXLS);

        //Excel parametros especificos
        exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
        exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,Boolean.FALSE);
        exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE,Boolean.TRUE);
        exporterXLS.setParameter(JRXlsExporterParameter.IS_IMAGE_BORDER_FIX_ENABLED,Boolean.TRUE);
        exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_GRAPHICS,Boolean.FALSE);
        exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jpe);
        exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, fosXLS);

        exporterXLS.exportReport();

        fosXLS.close();

    }
    
    public void generarCobranzasTXTComercios(List<PlanCuotaSP> cobranzasXComercio, String tipoReporte, String titulo) {

        jp = new JasperPrint();
        jr = null;
        params = new HashMap();
        dr = new DynamicReport();
        ds = null;
        String pathLogo = "classpath:/img/EdoCuenta.png", pathPetro = "classpath:/img/petro.png", fechaFin, nombreMes;
        Integer mesReporte;
        System.setProperty("java.awt.headless", "true");
        Fecha fechaEjecucion = new Fecha();
        
         //Buscamos el ultimo dia del mes seleccionado
        
        mesReporte = Integer.parseInt(fechaEjecucion.getString("MM"))-1;
        fechaFin = Utilidades.ultimoDiaMes(mesReporte.toString(), fechaEjecucion.getString("yyyy")); //los meses van de 0 a 11 por eso se le resta 1 al mes en curso
        nombreMes = formateador.format(Utilidades.deStringToDate(fechaFin));
        
        Numero numero = new Numero();
        Archivo archivo;
        Integer canTerminalesComercio = 0, canTerminalesGeneral = 0;
        BigDecimal montoCuotasTotalComercio = new BigDecimal(0)
                 , montoCuotasPendientesComercio = new BigDecimal(0)
                 , montoCuotasPagadasComercio = new BigDecimal(0)
                 , montoCuotasVencidasComercio = new BigDecimal(0)
                 , montoCuotasTotalGeneral = new BigDecimal(0)
                 , montoCuotasPendientesTotalGeneral = new BigDecimal(0)
                 , montoCuotasPagadasTotalGeneral = new BigDecimal(0)
                 , montoCuotasVencidasTotalGeneral = new BigDecimal(0);
        Boolean pagadas = false, pendientes = false, vencidas = false, pagadasGeneral = false, pendientesGeneral = false, vencidasGeneral = false;
        String prevTerminal = "";
        String prevComercio = "";
        //Buscamos el ultimo dia del mes seleccionado
        //fechaFin = Utilidades.ultimoDiaMes(mes, ano);
        //nombreMes = formateador.format(Utilidades.deStringToDate(fechaFin));

        try {
            String nombreArchivo = "1000Pagos_cobranzas_";
            if(tipoReporte.equals("1")){
                nombreArchivo += "resumen_";
            }else{
                nombreArchivo += "detalle_";
            }
            archivo = new Archivo(new File(rutaCobranzaComercios + "/" +fechaEjecucion.getString("yyyy")+ "/" + nombreMes.toUpperCase() + "/" + nombreArchivo + fechaEjecucion.getString("yyyyMMddHHmmss") + ".txt"));
            
            //archivo = new Archivo(new File(rutaCobranzaComercios+ File.separator +fechaEjecucion.getString("yyyy")+ File.separator +"rep_cobranzas_" + fechaEjecucion.getString("yyyyMMdd") + ".txt"));
            archivo.openFileWriter(Boolean.FALSE);
            archivo.escribir(StringUtils.repeat(" ", 143));
            archivo.escribir(StringUtils.leftPad("FECHA DE PROCESO: "+fechaEjecucion.getString("yyyy-MM-dd"), 143));
            archivo.escribir(StringUtils.repeat(" ", 143));        
            archivo.escribir(StringUtils.leftPad(titulo, 85));
            archivo.escribir(StringUtils.repeat(" ", 143));
            for(PlanCuotaSP registros: cobranzasXComercio){
                String linea;
                if(!registros.getComerDesc().equals(prevComercio)){
                    
                    if(!"".equals(prevComercio)){
                        //totales del comercio previo
                        montoCuotasTotalComercio = montoCuotasTotalComercio.add(montoCuotasPendientesComercio).add(montoCuotasVencidasComercio).add(montoCuotasPagadasComercio);
                        archivo.escribir(StringUtils.repeat(" ", 143));
                        if(pagadas){ 
                            archivo.escribir("Total Pagadas:     "+StringUtils.leftPad(numero.formatNumero_(montoCuotasPagadasComercio.setScale(2, RoundingMode.HALF_UP).doubleValue()), 28));
                        }
                        if(pendientes){
                            archivo.escribir("Total Pendientes:  "+StringUtils.leftPad(numero.formatNumero_(montoCuotasPendientesComercio.setScale(2, RoundingMode.HALF_UP).doubleValue()), 28));
                        }
                        if(vencidas){
                            archivo.escribir("Total Vencidas:    "+StringUtils.leftPad(numero.formatNumero_(montoCuotasVencidasComercio.setScale(2, RoundingMode.HALF_UP).doubleValue()), 28));
                        }
                        archivo.escribir("Total Comercio:    "+StringUtils.leftPad(numero.formatNumero_(montoCuotasTotalComercio.setScale(2, RoundingMode.HALF_UP).doubleValue()), 28));
                        archivo.escribir("Cant.Term.Comer.:  "+StringUtils.leftPad(canTerminalesComercio.toString(), 28));
                    }
                    //encabezado dle comercio
                    archivo.escribir(StringUtils.repeat(" ", 143));
                    archivo.escribir(StringUtils.repeat(" ", 143));
                    linea = StringUtils.rightPad(StringUtils.substring(registros.getComerDesc(), 0, 37),40); 
                    linea += StringUtils.rightPad(StringUtils.substring(registros.getAboCodAfi(), 0, 15),20);
                    linea += StringUtils.rightPad("RIF.CLTE.: " + registros.getComerRif(),22);
                    linea += StringUtils.rightPad("NRO.TELF.CLTE.: " + registros.getContTelefMov(),27);
                    archivo.escribir(linea);
                    linea = StringUtils.rightPad("NOMBRE.ACI: " + StringUtils.substring(registros.getAciDesc(), 0, 45),60); 
                    linea += StringUtils.rightPad("RIF.ACI:   " + registros.getAciRif(),22);
                    linea += StringUtils.rightPad("NRO.TELF.ACI:   " + registros.getAciTLF(),27);
                    archivo.escribir(linea);
                    archivo.escribir(StringUtils.repeat(" ", 143));
                    //encabezado de los registros
                    linea = StringUtils.rightPad("NRO: DE TERM.", 15);
                    linea += StringUtils.rightPad("ESTATUS CUOTAS", 16);

                    if(tipoReporte.equals("1")){
                        linea += (StringUtils.rightPad("NRO.DE CUOTAS", 14));
                    }
                    linea += (StringUtils.rightPad("MONTO $ CUOTA", 17));
                    linea += (StringUtils.rightPad("MONTO EN BS.", 21));
                    linea += (StringUtils.rightPad("IVA SOBRE MANT.", 16));
                    linea += (StringUtils.rightPad("TASA DICOM APLICADA", 20));
                    if(tipoReporte.equals("0")){
                        linea += (StringUtils.rightPad("NOMBRE DEL PLAN", 25));
                    }
                    linea += (StringUtils.rightPad("FECHA PROC.INI.", 16));
                    if(tipoReporte.equals("1")){
                        linea += (StringUtils.rightPad("FECHA PROC.FIN.", 16));
                    }
                    linea += (StringUtils.rightPad("FECHA PAGO.", 16));
                    linea += (StringUtils.rightPad("FECHA DESACTIVACION", 19));
                    archivo.escribir(linea);
                  //inicializacion de variables del comercio
                    prevComercio = registros.getComerDesc();
                    montoCuotasTotalComercio = new BigDecimal(0);
                    montoCuotasPendientesComercio = new BigDecimal(0);
                    montoCuotasPagadasComercio = new BigDecimal(0);
                    montoCuotasVencidasComercio = new BigDecimal(0);
                    pagadas = false; 
                    pendientes = false; 
                    vencidas = false;
                    canTerminalesComercio = 0;
                }
                //linea de registro por terminal y estatus
                linea = StringUtils.rightPad(registros.getAboTerminal(),15);
                linea += StringUtils.rightPad(StringUtils.substring(registros.getEstatusDesc(), 0, 15),16);
                if(tipoReporte.equals("1")){
                    linea += StringUtils.leftPad(StringUtils.leftPad(registros.getCantCuotas(), 3, "0"),14);
                }
                linea += StringUtils.leftPad(registros.getMontoTotal().setScale(9, RoundingMode.HALF_UP).toString(),16);
                linea += StringUtils.leftPad(numero.formatNumero_(registros.getMontoComision().setScale(2, RoundingMode.HALF_UP).doubleValue()),21);
                linea += StringUtils.leftPad(numero.formatNumero_(registros.getMontoIVA().setScale(2, RoundingMode.HALF_UP).doubleValue()),16);
                linea += StringUtils.leftPad(numero.formatNumero_(registros.getTasaValor().setScale(2, RoundingMode.HALF_UP).doubleValue()),20);
                linea += StringUtils.repeat(" ", 1);
                if(tipoReporte.equals("0")){
                    linea += StringUtils.leftPad(StringUtils.leftPad(registros.getPlanId(), 4, "0"),4);
                    linea += StringUtils.leftPad("-",1);
                    linea += StringUtils.rightPad(StringUtils.substring(registros.getPlanNombre(), 0, 19),20);
                }
                linea += StringUtils.leftPad(new Fecha(registros.getFechaProcesoIni()).getString("yyyy-MM-dd"),15);
                if(tipoReporte.equals("1")){
                    linea += StringUtils.leftPad(new Fecha(registros.getFechaProcesoFin()).getString("yyyy-MM-dd"),16);
                }
                if(null != registros.getFechaPagoUlt()){
                    linea += StringUtils.leftPad(new Fecha(registros.getFechaPagoUlt()).getString("yyyy-MM-dd"),16);
                }else{
                    linea += StringUtils.leftPad("",16);
                }
                //fecha de desactivacion?
                linea +=  StringUtils.repeat(" ", 19);
                
                archivo.escribir(linea);
                //acumulado por estatus
                switch (registros.getEstatusDesc()){
                    case "Pagada": montoCuotasPagadasComercio = montoCuotasPagadasComercio.add(registros.getMontoComision()).add(registros.getMontoIVA());
                                   montoCuotasPagadasTotalGeneral = montoCuotasPagadasTotalGeneral.add(registros.getMontoComision()).add(registros.getMontoIVA());
                                    pagadas = true;
                                    pagadasGeneral = true;
                                    break;
                    case "Vencida": montoCuotasVencidasComercio = montoCuotasVencidasComercio.add(registros.getMontoComision()).add(registros.getMontoIVA());
                                    montoCuotasVencidasTotalGeneral = montoCuotasVencidasTotalGeneral.add(registros.getMontoComision()).add(registros.getMontoIVA());
                                    vencidas = true;
                                    vencidasGeneral = true;
                                    break;
                    case "Pendiente": montoCuotasPendientesComercio = montoCuotasPendientesComercio.add(registros.getMontoComision()).add(registros.getMontoIVA());
                                    montoCuotasPendientesTotalGeneral = montoCuotasPendientesTotalGeneral.add(registros.getMontoComision()).add(registros.getMontoIVA());
                                    pendientes = true;
                                    pendientesGeneral = true;
                                    break;
                }
                //cuenta los terminales distintos
                if(!prevTerminal.equals(registros.getAboTerminal())){
                    canTerminalesComercio++;
                    canTerminalesGeneral++;
                    prevTerminal = registros.getAboTerminal();
                }
                
            }
            //totales del ultimo comercio
            montoCuotasTotalComercio = montoCuotasTotalComercio.add(montoCuotasPendientesComercio).add(montoCuotasVencidasComercio).add(montoCuotasPagadasComercio);
            archivo.escribir(StringUtils.repeat(" ", 143));
            if(pagadas){ 
                archivo.escribir("Total Pagadas:     "+StringUtils.leftPad(numero.formatNumero_(montoCuotasPagadasComercio.setScale(2, RoundingMode.HALF_UP).doubleValue()), 28));
            }
            if(pendientes){
                archivo.escribir("Total Pendientes:  "+StringUtils.leftPad(numero.formatNumero_(montoCuotasPendientesComercio.setScale(2, RoundingMode.HALF_UP).doubleValue()), 28));
            }
            if(vencidas){
                archivo.escribir("Total Vencidas:    "+StringUtils.leftPad(numero.formatNumero_(montoCuotasVencidasComercio.setScale(2, RoundingMode.HALF_UP).doubleValue()), 28));
            }
            archivo.escribir("Total Comercio:    "+StringUtils.leftPad(numero.formatNumero_(montoCuotasTotalComercio.setScale(2, RoundingMode.HALF_UP).doubleValue()), 28));
            archivo.escribir("Cant.Term.Comer.:  "+StringUtils.leftPad(canTerminalesComercio.toString(), 28));

            //totales generales de todas las cobranzas
            montoCuotasTotalGeneral = montoCuotasTotalGeneral.add(montoCuotasPendientesTotalGeneral).add(montoCuotasVencidasTotalGeneral).add(montoCuotasPagadasTotalGeneral);
            archivo.escribir(StringUtils.repeat(" ", 143));
            archivo.escribir(StringUtils.repeat(" ", 143));
            if(pagadasGeneral){ 
                archivo.escribir("Total General Pagadas:     "+StringUtils.leftPad(numero.formatNumero_(montoCuotasPagadasTotalGeneral.setScale(2, RoundingMode.HALF_UP).doubleValue()), 20));
            }
            if(pendientesGeneral){
                archivo.escribir("Total General Pendientes:  "+StringUtils.leftPad(numero.formatNumero_(montoCuotasPendientesTotalGeneral.setScale(2, RoundingMode.HALF_UP).doubleValue()), 20));
            }
            if(vencidasGeneral){
                archivo.escribir("Total General Vencidas:    "+StringUtils.leftPad(numero.formatNumero_(montoCuotasVencidasTotalGeneral.setScale(2, RoundingMode.HALF_UP).doubleValue()), 20));
            }
            archivo.escribir("Total General:             "+StringUtils.leftPad(numero.formatNumero_(montoCuotasTotalGeneral.setScale(2, RoundingMode.HALF_UP).doubleValue()), 20));
            archivo.escribir("Cant.Total.Term.:          "+StringUtils.leftPad(canTerminalesGeneral.toString(), 20));
            archivo.closeFileWriter();
        } catch (Exception cve) {
            logger.error(cve.getMessage());
        }

    }
    
    public void generarCobranzasExcelComercios(List<PlanCuotaSP> cobranzasXComercio, String tipoReporte, String titulo) {

        jpe = new JasperPrint();
        jre = null;
        params = new HashMap();
        dre = new DynamicReport();
        drbe = new DynamicReportBuilder();
        dse = null;
        String pathLogo = "classpath:/img/EdoCuenta.png", pathPetro = "classpath:/img/petro.png", fechaFin, nombreMes;
        Integer mesReporte;
        System.setProperty("java.awt.headless", "true");
        Fecha fechaEjecucion = new Fecha();
        //Buscamos el ultimo dia del mes seleccionado
        mesReporte = Integer.parseInt(fechaEjecucion.getString("MM"))-1;
        fechaFin = Utilidades.ultimoDiaMes(mesReporte.toString(), fechaEjecucion.getString("yyyy")); //los meses van de 0 a 11 por eso se le resta 1 al mes en curso
        nombreMes = formateador.format(Utilidades.deStringToDate(fechaFin));
        
        // Definimos un estilo para el detalle de los registros
        Style styleColumns = new Style();
        styleColumns.setHorizontalAlign(HorizontalAlign.CENTER);
        styleColumns.setVerticalAlign(VerticalAlign.MIDDLE);
        styleColumns.setTextColor(Color.BLACK);
        styleColumns.setBorder(Border.NO_BORDER());

        // Definimos un estilo para el titulo
        Style styleTitle = new Style();
        styleTitle.setHorizontalAlign(HorizontalAlign.CENTER);

        // Definimos un estilo para el subtitulo
        Style styleSubTitle = new Style();

        // Definimos un estilo para la cabecera de los registros
        Style styleColumnsHeader = new Style();
        styleColumnsHeader.setBorder(Border.PEN_1_POINT());
        styleColumnsHeader.setHorizontalAlign(HorizontalAlign.CENTER);
        styleColumnsHeader.setVerticalAlign(VerticalAlign.MIDDLE);

        try {
            String nombreArchivo = "1000Pagos_cobranzas_";
            if(tipoReporte.equals("1")){
                nombreArchivo += "resumen_";
            }else{
                nombreArchivo += "detalle_";
            }
            Cobranzas elementToInclude;
            Collection<Cobranzas> dataResultE=new ArrayList<>();
            String montoTotalFacturadoFormato, montoTotalDeduccionesFormato, totalAbonoFormato, montoTotalFacturadoPetroFormato, montoTotalDeduccionesPetroFormato, totalAbonoPetroFormato;
            BigDecimal montoTotalFacturado = new BigDecimal(0), montoTotalDeducciones = new BigDecimal(0), totalAbono = new BigDecimal(0), montoPetro = new BigDecimal(utils.parametro("monto.petro").get(0).getValor()), montoTotalPetro = new BigDecimal(0), montoTotalFacturadoPetro = new BigDecimal(0), montoTotalDeduccionesPetro = new BigDecimal(0), totalAbonoPetro = new BigDecimal(0);
            montoTotalDeducciones = montoTotalDeducciones.setScale(2, RoundingMode.CEILING);
            montoTotalFacturado = montoTotalFacturado.setScale(2, RoundingMode.CEILING);
            montoPetro = montoPetro.setScale(2, RoundingMode.CEILING);
            montoTotalPetro = montoTotalPetro.setScale(6, RoundingMode.CEILING);
            totalAbono = totalAbono.setScale(2, RoundingMode.CEILING);
            totalAbonoPetro = totalAbonoPetro.setScale(6, RoundingMode.CEILING);
            //JasperCompileManager.compileReport(getClass().getResourceAsStream("/templates/estadosCuentaComercioExcel.jrxml"));
            //List<HistoricoEdoCuenta> resultado = comercioService.getHistoricoEdoCuentaComercios(Utilidades.convierteFechaSqlsinHora(fechaFin), comercio, terminal);
            List<PlanCuotaSP> resultado = cobranzasXComercio;
            if(!resultado.isEmpty()){

                //Parametros del Reporte
/*                
                params.put("fechaInicioMes", "01" + fechaFin.substring(2));
                
 		            params.put("terminal", terminal);
                params.put("nombreComercio", resultado.get(0).getDescripcionComercio());
                params.put("rif", resultado.get(0).getRifComercio().substring(0, 1) + "-" + resultado.get(0).getRifComercio().substring(1));
                params.put("direccion", resultado.get(0).getDireccionComercio());
                if(resultado.get(0).getTelefonoHabitacion()==null || resultado.get(0).getTelefonoHabitacion().isEmpty()){
                    params.put("telefono", resultado.get(0).getTelefonoCelular());
                }else{
                    params.put("telefono", resultado.get(0).getTelefonoHabitacion());
                }
                params.put("banco", resultado.get(0).getNombreBanco());
*/              
                params.put("fecha", fechaEjecucion.getString("yyyy-MM-dd"));
                params.put("logo", pathLogo);


                //Columnas del reporte
                AbstractColumn columnTerminal = ColumnBuilder.getNew()
                        .setColumnProperty("aboTerminal", String.class.getName())
                        .setTitle("NRO: DE TERM.")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(125)
                        .build();

                AbstractColumn columnEstatus = ColumnBuilder.getNew()
                        .setColumnProperty("estatusDesc", String.class.getName())
                        .setTitle("ESTATUS CUOTAS")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(130)
                        .build();

                AbstractColumn columnPlanId = ColumnBuilder.getNew()
                        .setColumnProperty("planId", String.class.getName())
                        .setTitle("ID PLAN")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(130)
                        .build();

                AbstractColumn columnPlanName = ColumnBuilder.getNew()
                        .setColumnProperty("planNombre", String.class.getName())
                        .setTitle("NOMBRE PLAN")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(130)
                        .build();

                AbstractColumn columnaCantCuotas = ColumnBuilder.getNew()
                        .setColumnProperty("cantCuotas", String.class.getName())
                        .setTitle("NRO.DE CUOTAS")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(130)
                        .build();

                AbstractColumn columnaMontoCuota = ColumnBuilder.getNew()
                        .setColumnProperty("montoTotal", BigDecimal.class.getName())
                        .setTitle("MONTO $ CUOTA")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(130)
                        .build();

                AbstractColumn columnaComGtosAdm = ColumnBuilder.getNew()
                        .setColumnProperty("montoComision", BigDecimal.class.getName())
                        .setTitle("MONTO BS")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(130)
                        .build();

                AbstractColumn columnaIvaGtosAdm = ColumnBuilder.getNew()
                        .setColumnProperty("montoIVA", BigDecimal.class.getName())
                        .setTitle("IVA SOBRE MANT.")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(130)
                        .build();

                AbstractColumn columnaTasaAplicada = ColumnBuilder.getNew()
                        .setColumnProperty("tasaValor", BigDecimal.class.getName())
                        .setTitle("TASA DICOM APLICADA")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(130)
                        .build();
                
                        
                AbstractColumn columnaFechaIniCuotas = ColumnBuilder.getNew()
                        .setColumnProperty("fechaProcesoIni", String.class.getName())
                        .setTitle("FECHA PROC.INI")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(130)
                        .build();

                AbstractColumn columnaFechaFinCuotas = ColumnBuilder.getNew()
                        .setColumnProperty("fechaProcesoFin", String.class.getName())
                        .setTitle("FECHA PROC.FIN")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(130)
                        .build();

                AbstractColumn columnaFechaUltPago = ColumnBuilder.getNew()
                        .setColumnProperty("fechaPagoUlt", String.class.getName())
                        .setTitle("FECHA ULT.PAGO")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(130)
                        .build();

                AbstractColumn columnaComerDesc = ColumnBuilder.getNew()
                        .setColumnProperty("comerDesc", String.class.getName())
                        .setTitle("NOMBRE DEL CLITE.")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(130)
                        .build();

                AbstractColumn columnaComerRif = ColumnBuilder.getNew()
                        .setColumnProperty("comerRif", String.class.getName())
                        .setTitle("RIF DEL CLITE.")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(130)
                        .build();

                AbstractColumn columnaContTelefMov = ColumnBuilder.getNew()
                        .setColumnProperty("contTelefMov", String.class.getName())
                        .setTitle("NRO.TELF.CLTE.")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(130)
                        .build();

                AbstractColumn columnaAciDesc = ColumnBuilder.getNew()
                        .setColumnProperty("aciDesc", String.class.getName())
                        .setTitle("NOMBRE DEL ACI")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(130)
                        .build();

                AbstractColumn columnaAciRif = ColumnBuilder.getNew()
                        .setColumnProperty("aciRif", String.class.getName())
                        .setTitle("RIF DEL ACI")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(130)
                        .build();

                AbstractColumn columnaAciTlf = ColumnBuilder.getNew()
                        .setColumnProperty("aciTLF", String.class.getName())
                        .setTitle("NRO.TELF.ACI")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(130)
                        .build();

                drbe.addColumn(columnTerminal)
                    .addColumn(columnPlanId)
                    .addColumn(columnPlanName)
                    .addColumn(columnEstatus)
                    .addColumn(columnaCantCuotas)
                    .addColumn(columnaMontoCuota)
                    .addColumn(columnaComGtosAdm)
                    .addColumn(columnaIvaGtosAdm)
                    .addColumn(columnaTasaAplicada)
                    .addColumn(columnaFechaIniCuotas)
                    .addColumn(columnaFechaFinCuotas)
                    .addColumn(columnaFechaUltPago)
                    .addColumn(columnaComerDesc)
                    .addColumn(columnaComerRif)
                    .addColumn(columnaContTelefMov)
                    .addColumn(columnaAciDesc)
                    .addColumn(columnaAciRif)
                    .addColumn(columnaAciTlf)
                    .setReportName("Gestion de Cobranzas")
                    .setUseFullPageWidth(true)
                    .setDetailHeight(10)
                    //.setLeftMargin(40)
                    //.setRightMargin(40)
                    .setWhenNoDataNoPages()
                    .setIgnorePagination(true)
                    .setDefaultStyles(styleTitle, styleSubTitle, styleColumnsHeader, styleColumns);

                drbe.setTemplateFile("/templates/cobranzasComercioExcelP.jrxml");

                for(PlanCuotaSP registros: resultado){
                    elementToInclude = new Cobranzas();
                    elementToInclude.setAboTerminal(registros.getAboTerminal());
                    elementToInclude.setPlanId(registros.getPlanId());
                    elementToInclude.setPlanNombre(registros.getPlanNombre());
                    elementToInclude.setEstatusDesc(registros.getEstatusDesc());
                    elementToInclude.setCantCuotas(registros.getCantCuotas());
                    elementToInclude.setMontoTotal(registros.getMontoTotal());
                    elementToInclude.setMontoComision(registros.getMontoComision());
                    elementToInclude.setTasaValor(registros.getTasaValor());
                    //elementToInclude.setMontoIVA(Utilidades.FormatearNumero(registros.getMontoIVA().toString()));
                    elementToInclude.setMontoIVA(registros.getMontoIVA());
                    /* //con tipo date
                    elementToInclude.setFechaProcesoIni(registros.getFechaProcesoIni());
                    elementToInclude.setFechaProcesoFin(registros.getFechaProcesoFin());
                    if(null != registros.getFechaPagoUlt()){
                        elementToInclude.setFechaPagoUlt(registros.getFechaPagoUlt());
                    }else{
                        elementToInclude.setFechaPagoUlt(null);
                    }*/
                    //old, formato fecha en string
                    elementToInclude.setFechaProcesoIni(new Fecha(registros.getFechaProcesoIni()).getString("dd-MM-yyyy"));
                    elementToInclude.setFechaProcesoFin(new Fecha(registros.getFechaProcesoFin()).getString("dd-MM-yyyy"));
                    if(null != registros.getFechaPagoUlt()){
                        elementToInclude.setFechaPagoUlt(new Fecha(registros.getFechaPagoUlt()).getString("dd-MM-yyyy"));
                    }else{
                        elementToInclude.setFechaPagoUlt("");
                    }
                    elementToInclude.setComerDesc(registros.getComerDesc());
                    elementToInclude.setComerRif(registros.getComerRif());
                    elementToInclude.setContTelefMov(registros.getContTelefMov());
                    elementToInclude.setAciDesc(registros.getAciDesc());
                    elementToInclude.setAciRif(registros.getAciRif());
                    elementToInclude.setAciTLF(registros.getAciTLF());
/*                    elementToInclude.setMontoFacturado(Utilidades.FormatearNumero(registros.getMontoFacturado().toString()));
                    elementToInclude.setComisionMilPagos(Utilidades.FormatearNumero(registros.getComisionMilPagos().toString()));
                    elementToInclude.setMontoTotal(Utilidades.FormatearNumero(registros.getMontoTotal().toString()));
                    montoTotalPetro = registros.getMontoTotal().divide(montoPetro, 6, RoundingMode.HALF_UP);
                    elementToInclude.setMontoTotalPetro(Utilidades.FormatearNumeroPetro(montoTotalPetro.toString()));
                    montoTotalFacturado = montoTotalFacturado.add(registros.getMontoFacturado());
                    montoTotalDeducciones = montoTotalDeducciones.add(registros.getComisionMilPagos());
                    totalAbono = totalAbono.add(registros.getMontoTotal());*/
                    dataResultE.add(elementToInclude);
                }

                montoTotalFacturadoFormato = Utilidades.FormatearNumero(montoTotalFacturado.toString());
                montoTotalDeduccionesFormato = Utilidades.FormatearNumero(montoTotalDeducciones.toString());
                totalAbonoFormato = Utilidades.FormatearNumero(totalAbono.toString());

                //Petro
                montoTotalFacturadoPetroFormato = Utilidades.FormatearNumeroPetro(montoTotalFacturado.divide(montoPetro, 6, RoundingMode.HALF_UP).toString());
                montoTotalDeduccionesPetroFormato = Utilidades.FormatearNumeroPetro(montoTotalDeducciones.divide(montoPetro, 6, RoundingMode.HALF_UP).toString());
                totalAbonoPetroFormato = Utilidades.FormatearNumeroPetro(totalAbono.divide(montoPetro, 6, RoundingMode.HALF_UP).toString());

                params.put("titulo",titulo);
                params.put("montoTotalFacturado", montoTotalFacturadoFormato);
                params.put("montoTotalDeducciones", montoTotalDeduccionesFormato);
                params.put("totalAbono", totalAbonoFormato);

                //Petro
                params.put("montoTotalFacturadoPetro", montoTotalFacturadoPetroFormato);
                params.put("montoTotalDeduccionesPetro", montoTotalDeduccionesPetroFormato);
                params.put("totalAbonoPetro", totalAbonoPetroFormato);

                Collection<Cobranzas> dataOneReportE = dataResultE;

                dre = drbe.build();

                dataOneReportE = SortUtils.sortCollection(dataOneReportE,dre.getColumns());

                //Crea el JRDataSource a partir de la coleccion de datos
                dse = new JRBeanCollectionDataSource(dataOneReportE);

                /**
                 * Creamos el objeto JasperReport que pasamos como parametro a
                 * DynamicReport,junto con una nueva instancia de ClassicLayoutManager
                 * y el JRDataSource
                 */
                jre = DynamicJasperHelper.generateJasperReport(dre, getLayoutManager(), params);

                /**
                 * Creamos el objeto que imprimiremos pasando como parametro
                 * el JasperReport object, y el JRDataSource
                 */
                if (dse != null){
                        jpe = JasperFillManager.fillReport(jre, params, dse);
                }else{
                        jpe = JasperFillManager.fillReport(jre, params);
                }

                //exportReportXLS(rutaCobranzaComercios+"/2020/"+nombreMes.toUpperCase()+"/"+terminal.toUpperCase(),rifComercio.toUpperCase()+"_"+terminal+"_"+fechaFin.substring(3,5)+ano);
                //exportReportXLS(rutaCobranzaComercios+"/"+fechaEjecucion.getString("yyyy")+"/","rep_cobranzas_" + fechaEjecucion.getString("yyyyMMdd"));
                exportReportXLS(rutaCobranzaComercios + "/" + fechaEjecucion.getString("yyyy")+ "/" + nombreMes.toUpperCase(),nombreArchivo + fechaEjecucion.getString("yyyyMMddHHmmss"));
            }

        } catch (Exception cve) {
            logger.error(cve.getMessage());
        }

    }
    
    public void generarDataentryCobranzaExcel(List<PlanCuotaSP> cobranzasXComercio){
        String fechaFin, nombreMes;
        
        Integer mesReporte;
        System.setProperty("java.awt.headless", "true");
        Fecha fechaEjecucion = new Fecha();
        //Buscamos el ultimo dia del mes seleccionado
        mesReporte = Integer.parseInt(fechaEjecucion.getString("MM"))-1;
        fechaFin = Utilidades.ultimoDiaMes(mesReporte.toString(), fechaEjecucion.getString("yyyy")); //los meses van de 0 a 11 por eso se le resta 1 al mes en curso
        nombreMes = formateador.format(Utilidades.deStringToDate(fechaFin));
        
        Workbook wb = new HSSFWorkbook();  
        CreationHelper createHelper = wb.getCreationHelper();  
        try(OutputStream os = new FileOutputStream(rutaCobranzaComercios + "/" + fechaEjecucion.getString("yyyy")+ "/" + nombreMes.toUpperCase()+"/100PagosDataentryCuotasPendientes_"+ fechaEjecucion.getString("yyyyMMddHHmmss")+".xls")){  
            Sheet sheet = wb.createSheet("New Sheet");  
            //encabezados
            Row row     = sheet.createRow(0);
            row.createCell(0).setCellValue("#"); // id  
            row.createCell(1).setCellValue("COD.AFILIADO"); // aboCodAfi  
            row.createCell(2).setCellValue("COD.COMERCIO");// aboCodComercio
            row.createCell(3).setCellValue("NRO: DE TERM."); // aboTerminal    
            row.createCell(4).setCellValue("RIF.COMERCIO"); // comerRif  
            row.createCell(5).setCellValue("NOMBRE COMERCIO");// comerDesc
            row.createCell(6).setCellValue("TLF.COMERCIO"); // contTelefMov   
            row.createCell(7).setCellValue("ID.PLAN"); // planId   
            row.createCell(8).setCellValue("NOMBRE DEL PLAN");// planNombre  
            row.createCell(9).setCellValue("DESC.TIPO PLAN"); // descTipoPlan 
            row.createCell(10).setCellValue("ID CUOTA"); // idPlanCuota 
            row.createCell(11).setCellValue("ESTATUS CUOTA"); // estatusDesc  
            row.createCell(12).setCellValue("MONTO $ CUOTA"); // montoTotal  
            row.createCell(13).setCellValue("MONTO EN BS."); // montoComision  
            row.createCell(14).setCellValue("IVA SOBRE MANT."); // montoIVA   
            row.createCell(15).setCellValue("TASA DICOM APLICADA"); // tasaValor    
            row.createCell(16).setCellValue("TASA DICOM FECHA"); //  tasa dicom fecha para consutlar en tabla de tasa_dicom del servidor 19 por el id
            row.createCell(17).setCellValue("FECHA PROC.INI."); // fechaProcesoIni
            row.createCell(18).setCellValue("FECHA PAGO."); // fechaPago    
            row.createCell(19).setCellValue("RIF.ACI"); // aciRif  
            row.createCell(20).setCellValue("NOMBRE.ACI");// aciDesc
            row.createCell(21).setCellValue("TLF.ACI"); // aciTLF  

            Integer i = 1;
            CellStyle cellStyle = wb.createCellStyle();  
            cellStyle.setDataFormat(  
                createHelper.createDataFormat().getFormat("dd/mm/yyyy"));
            for(PlanCuotaSP cuota : cobranzasXComercio){
                row = sheet.createRow(i);
                row.createCell(0).setCellValue(cuota.getId()); // id  
                row.createCell(1).setCellValue(cuota.getAboCodAfi()); // aboCodAfi  
                row.createCell(2).setCellValue(cuota.getAboCodComercio());// aboCodComercio
                row.createCell(3).setCellValue(cuota.getAboTerminal()); // aboTerminal    
                row.createCell(4).setCellValue(cuota.getComerRif()); // comerRif  
                row.createCell(5).setCellValue(cuota.getComerDesc());// comerDesc
                row.createCell(6).setCellValue(cuota.getContTelefMov()); // contTelefMov   
                row.createCell(7).setCellValue(cuota.getPlanId()); // planId   
                row.createCell(8).setCellValue(cuota.getPlanNombre());// planNombre  
                row.createCell(9).setCellValue(cuota.getDescTipoPlan()); // descTipoPlan 
                row.createCell(10).setCellValue(cuota.getIdPlanCuota()); // idPlanCuota 
                row.createCell(11).setCellValue(cuota.getEstatusDesc()); // estatusDesc  
                row.createCell(12).setCellValue(cuota.getMontoTotal().doubleValue()); // montoTotal
                Cell cell   = row.createCell(17);  
                //row.createCell(17).setCellValue(cuota.getFechaProcesoIni()); // fechaProcesoIni 
                cell.setCellValue(cuota.getFechaProcesoIni());  
                cell.setCellStyle(cellStyle);
                //row.createCell(17).setCellStyle(cellStyle);
                row.createCell(19).setCellValue(cuota.getAciRif()); // aciRif  
                row.createCell(20).setCellValue(cuota.getAciDesc());// aciDesc
                row.createCell(21).setCellValue(cuota.getAciTLF()); // aciTLF  
                i++;
            }
            /*row     = sheet.createRow(0);  
            Cell cell   = row.createCell(0);  
            CellStyle cellStyle = wb.createCellStyle();  
            cellStyle.setDataFormat(  
                createHelper.createDataFormat().getFormat("d/m/yy h:mm"));  
            cell = row.createCell(1);  
            cell.setCellValue(new Date());  
            cell.setCellStyle(cellStyle);  */
            wb.write(os);  
        }catch(Exception e) {  
            System.out.println(e.getMessage());  
        }  
    }
    
    public void generarConsultaPlanesTXTComercios(List<PlanesConsulta> planesXComercio){

        jp = new JasperPrint();
        jr = null;
        params = new HashMap();
        dr = new DynamicReport();
        ds = null;
        String pathLogo = "classpath:/img/EdoCuenta.png", pathPetro = "classpath:/img/petro.png", fechaFin, nombreMes;
        Integer mesReporte;
        System.setProperty("java.awt.headless", "true");
        Fecha fechaEjecucion = new Fecha();
        
         //Buscamos el ultimo dia del mes seleccionado
        
        mesReporte = Integer.parseInt(fechaEjecucion.getString("MM"))-1;
        fechaFin = Utilidades.ultimoDiaMes(mesReporte.toString(), fechaEjecucion.getString("yyyy")); //los meses van de 0 a 11 por eso se le resta 1 al mes en curso
        nombreMes = formateador.format(Utilidades.deStringToDate(fechaFin));
        
        Numero numero = new Numero();
        Archivo archivo;
        Integer canTerminalesGeneral = 0;
        String prevTerminal = "";
        String prevComercio = "";
        //Buscamos el ultimo dia del mes seleccionado
        //fechaFin = Utilidades.ultimoDiaMes(mes, ano);
        //nombreMes = formateador.format(Utilidades.deStringToDate(fechaFin));

        try {
            archivo = new Archivo(new File(rutaCobranzaComercios + "/" +fechaEjecucion.getString("yyyy")+ "/" + nombreMes.toUpperCase() + "/" + "1000Pagos_clientes_x_plan_" + fechaEjecucion.getString("yyyyMMddHHmmss") + ".txt"));
            archivo.openFileWriter(Boolean.FALSE);
            archivo.escribir(StringUtils.repeat(" ", 143));
            archivo.escribir(StringUtils.leftPad("FECHA DE PROCESO: "+fechaEjecucion.getString("yyyy-MM-dd"), 143));
            archivo.escribir(StringUtils.repeat(" ", 143));        
            archivo.escribir(StringUtils.leftPad("REPORTE DE PLANES POR CLIENTES", 85));
            archivo.escribir(StringUtils.repeat(" ", 143));
            archivo.escribir(StringUtils.repeat(" ", 143));
            String titulos;
            titulos = StringUtils.rightPad("#",4);
            titulos += StringUtils.rightPad("Comercio",31);
            titulos += StringUtils.rightPad("Comercio.RIF",16);
            titulos += StringUtils.rightPad("Comercio.TLF",16);
            titulos += StringUtils.rightPad("Terminal",11);
            titulos += StringUtils.rightPad("Tipo.Plan",16);
            titulos += StringUtils.rightPad("Id.Plan",11);
            titulos += StringUtils.rightPad("Nombre.Plan",26);
            titulos += StringUtils.rightPad("Estatus",11);
            titulos += StringUtils.leftPad("M.Tarifa",13);
            titulos += StringUtils.leftPad("M.Inicial",13);
            titulos += StringUtils.repeat(" ", 2);
            titulos += StringUtils.rightPad("Moneda",16);
            titulos += StringUtils.rightPad("Fecha.Inicio",16);
            titulos += StringUtils.rightPad("Fecha.Fin",16);
            titulos += StringUtils.rightPad("Frecuencia",16);
            titulos += StringUtils.rightPad("ACI",31);
            titulos += StringUtils.rightPad("ACI.RIF",16);
            titulos += StringUtils.rightPad("ACI.TLF",16);
            archivo.escribir(titulos);
                    
            for(PlanesConsulta registros: planesXComercio){
                String linea;
                if(!registros.getComerDesc().equals(prevComercio)){
                    //inicializacion de variables del comercio
                    prevComercio = registros.getComerDesc();
                }
                //linea de registro por terminal y estatus
                linea = StringUtils.rightPad(registros.getId().toString(),4);
                linea += StringUtils.rightPad(StringUtils.substring(registros.getComerDesc(), 0, 30),31);
                linea += StringUtils.rightPad(StringUtils.substring(registros.getComerRif(), 0, 15),16);
                linea += StringUtils.rightPad(StringUtils.substring(registros.getContTelefMov(), 0, 15),16);
                linea += StringUtils.rightPad(StringUtils.substring(registros.getAboTerminal(), 0, 10),11);
                linea += StringUtils.rightPad(StringUtils.substring(registros.getDescTipoPlan(), 0, 15),16);
                linea += StringUtils.rightPad(StringUtils.substring(registros.getPlanId().toString(), 0, 25),11);
                linea += StringUtils.rightPad(StringUtils.substring(registros.getPlanNombre(), 0, 25),26);
                linea += StringUtils.rightPad(StringUtils.substring(registros.getEstatusDesc(), 0, 10),11);
                if(null != registros.getMontoTarifa()){
                    linea += StringUtils.leftPad(registros.getMontoTarifa(),13);
                }else{
                    linea += StringUtils.leftPad("",13);
                }
                if(null != registros.getMontoInicial()){
                    linea += StringUtils.leftPad(registros.getMontoInicial(),13);
                }else{
                    linea += StringUtils.leftPad("",13);
                }
                linea += StringUtils.leftPad("",2);
                linea += StringUtils.rightPad(StringUtils.substring(registros.getMonedaDesc(), 0, 15),16);
                if(null != registros.getFechaInicio()){
                    linea += StringUtils.rightPad(new Fecha(registros.getFechaInicio()).getString("yyyy-MM-dd"),16);
                }else{
                    linea += StringUtils.leftPad("",16);
                }
                if(null != registros.getFechaFin()){
                    linea += StringUtils.rightPad(new Fecha(registros.getFechaFin()).getString("yyyy-MM-dd"),16);
                }else{
                    linea += StringUtils.leftPad("",16);
                }
                linea += StringUtils.rightPad(StringUtils.substring(registros.getFrecuenciaDesc(), 0, 15),16);
                linea += StringUtils.rightPad(StringUtils.substring(registros.getAciDesc(), 0, 30),31);
                linea += StringUtils.rightPad(StringUtils.substring(registros.getAciRif(), 0, 15),16);
                linea += StringUtils.rightPad(StringUtils.substring(registros.getAciTLF(), 0, 15),16);
                
                archivo.escribir(linea);
                
                //cuenta los terminales distintos
                if(!prevTerminal.equals(registros.getAboTerminal())){
                    canTerminalesGeneral++;
                    prevTerminal = registros.getAboTerminal();
                }
                
            }
            
            //totales generales de todas las cobranzas
            archivo.escribir(StringUtils.repeat(" ", 143));
            archivo.escribir("Cant.Total.Term.:          "+StringUtils.leftPad(canTerminalesGeneral.toString(), 20));
            archivo.closeFileWriter();
        } catch (Exception cve) {
            logger.error(cve.getMessage());
        }

    }

    
    @Override
    public void generarConsultaPlanesExcelComercios(List<PlanesConsulta> planesXComercio) {

        jpe = new JasperPrint();
        jre = null;
        params = new HashMap();
        dre = new DynamicReport();
        drbe = new DynamicReportBuilder();
        dse = null;
        String pathLogo = "classpath:/img/EdoCuenta.png", pathPetro = "classpath:/img/petro.png", fechaFin, nombreMes;
        Integer mesReporte;
        System.setProperty("java.awt.headless", "true");

        Fecha fechaEjecucion = new Fecha();
        
         //Buscamos el ultimo dia del mes seleccionado
        //fechaFin = Utilidades.ultimoDiaMes(fechaEjecucion.getString("MM"), fechaEjecucion.getString("yyyy"));
        mesReporte = Integer.parseInt(fechaEjecucion.getString("MM"))-1;
        fechaFin = Utilidades.ultimoDiaMes(mesReporte.toString(), fechaEjecucion.getString("yyyy")); //los meses van de 0 a 11 por eso se le resta 1 al mes en curso
        nombreMes = formateador.format(Utilidades.deStringToDate(fechaFin));
        

        // Definimos un estilo para el detalle de los registros
        Style styleColumns = new Style();
        styleColumns.setHorizontalAlign(HorizontalAlign.CENTER);
        styleColumns.setVerticalAlign(VerticalAlign.MIDDLE);
        styleColumns.setTextColor(Color.BLACK);
        styleColumns.setBorder(Border.NO_BORDER());

        // Definimos un estilo para el titulo
        Style styleTitle = new Style();
        styleTitle.setHorizontalAlign(HorizontalAlign.CENTER);

        // Definimos un estilo para el subtitulo
        Style styleSubTitle = new Style();

        // Definimos un estilo para la cabecera de los registros
        Style styleColumnsHeader = new Style();
        styleColumnsHeader.setBorder(Border.PEN_1_POINT());
        styleColumnsHeader.setHorizontalAlign(HorizontalAlign.CENTER);
        styleColumnsHeader.setVerticalAlign(VerticalAlign.MIDDLE);

        try {
            
            PlanesConsultaRep elementToInclude;
            Collection<PlanesConsultaRep> dataResultE=new ArrayList<>();
            String montoTotalFacturadoFormato, montoTotalDeduccionesFormato, totalAbonoFormato, montoTotalFacturadoPetroFormato, montoTotalDeduccionesPetroFormato, totalAbonoPetroFormato;
            BigDecimal montoTotalFacturado = new BigDecimal(0), montoTotalDeducciones = new BigDecimal(0), totalAbono = new BigDecimal(0), montoPetro = new BigDecimal(utils.parametro("monto.petro").get(0).getValor()), montoTotalPetro = new BigDecimal(0), montoTotalFacturadoPetro = new BigDecimal(0), montoTotalDeduccionesPetro = new BigDecimal(0), totalAbonoPetro = new BigDecimal(0);
            montoTotalDeducciones = montoTotalDeducciones.setScale(2, RoundingMode.CEILING);
            montoTotalFacturado = montoTotalFacturado.setScale(2, RoundingMode.CEILING);
            montoPetro = montoPetro.setScale(2, RoundingMode.CEILING);
            montoTotalPetro = montoTotalPetro.setScale(6, RoundingMode.CEILING);
            totalAbono = totalAbono.setScale(2, RoundingMode.CEILING);
            totalAbonoPetro = totalAbonoPetro.setScale(6, RoundingMode.CEILING);
            //JasperCompileManager.compileReport(getClass().getResourceAsStream("/templates/estadosCuentaComercioExcel.jrxml"));
            //List<HistoricoEdoCuenta> resultado = comercioService.getHistoricoEdoCuentaComercios(Utilidades.convierteFechaSqlsinHora(fechaFin), comercio, terminal);
            List<PlanesConsulta> resultado = planesXComercio;
            if(!resultado.isEmpty()){

                //Parametros del Reporte
/*                
                params.put("fechaInicioMes", "01" + fechaFin.substring(2));
                
 		            params.put("terminal", terminal);
                params.put("nombreComercio", resultado.get(0).getDescripcionComercio());
                params.put("rif", resultado.get(0).getRifComercio().substring(0, 1) + "-" + resultado.get(0).getRifComercio().substring(1));
                params.put("direccion", resultado.get(0).getDireccionComercio());
                if(resultado.get(0).getTelefonoHabitacion()==null || resultado.get(0).getTelefonoHabitacion().isEmpty()){
                    params.put("telefono", resultado.get(0).getTelefonoCelular());
                }else{
                    params.put("telefono", resultado.get(0).getTelefonoHabitacion());
                }
                params.put("banco", resultado.get(0).getNombreBanco());
*/              
                params.put("fecha", fechaEjecucion.getString("yyyy-MM-dd"));
                params.put("logo", pathLogo);


                //Columnas del reporte

                AbstractColumn columnaComerDesc = ColumnBuilder.getNew()
                        .setColumnProperty("comerDesc", String.class.getName())
                        .setTitle("NOMBRE DEL CLITE.")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(130)
                        .build();

                AbstractColumn columnaComerRif = ColumnBuilder.getNew()
                        .setColumnProperty("comerRif", String.class.getName())
                        .setTitle("RIF DEL CLITE.")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(130)
                        .build();

                AbstractColumn columnaContTelefMov = ColumnBuilder.getNew()
                        .setColumnProperty("contTelefMov", String.class.getName())
                        .setTitle("NRO.TELF.CLTE.")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(130)
                        .build();
                
                AbstractColumn columnTerminal = ColumnBuilder.getNew()
                        .setColumnProperty("aboTerminal", String.class.getName())
                        .setTitle("NRO: DE TERM.")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(115)
                        .build();

                AbstractColumn columnTipoPlan = ColumnBuilder.getNew()
                        .setColumnProperty("descTipoPlan", String.class.getName())
                        .setTitle("TIPO PLAN")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(130)
                        .build();
                AbstractColumn columnaPlanId = ColumnBuilder.getNew()
                        .setColumnProperty("planId", Integer.class.getName())
                        .setTitle("ID PLAN")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(50)
                        .build();
                AbstractColumn columnaPlanNombre = ColumnBuilder.getNew()
                        .setColumnProperty("planNombre", String.class.getName())
                        .setTitle("NOMBRE DE PLAN")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(160)
                        .build();

                AbstractColumn columnaEstatus = ColumnBuilder.getNew()
                        .setColumnProperty("estatusDesc", String.class.getName())
                        .setTitle("ESTATUS PLAN")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(75)
                        .build();

                AbstractColumn columnaTarifa = ColumnBuilder.getNew()
                        .setColumnProperty("montoTarifa", String.class.getName())
                        .setTitle("MONTO TARIFA.")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(130)
                        .build();

                AbstractColumn columnaMontoInicial = ColumnBuilder.getNew()
                        .setColumnProperty("montoInicial", String.class.getName())
                        .setTitle("MONTO INICIAL.")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(130)
                        .build();

                AbstractColumn columnaMoneda = ColumnBuilder.getNew()
                        .setColumnProperty("monedaDesc", String.class.getName())
                        .setTitle("MONEDA")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(130)
                        .build();

                AbstractColumn columnaFechaIni = ColumnBuilder.getNew()
                        .setColumnProperty("fechaInicio", String.class.getName())
                        .setTitle("FECHA INI")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(130)
                        .build();

                AbstractColumn columnaFechaFin = ColumnBuilder.getNew()
                        .setColumnProperty("fechaFin", String.class.getName())
                        .setTitle("FECHA FIN")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(130)
                        .build();

                AbstractColumn columnaFrecuencia = ColumnBuilder.getNew()
                        .setColumnProperty("frecuenciaDesc", String.class.getName())
                        .setTitle("FRECUENCIA")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(130)
                        .build();

                AbstractColumn columnaAciDesc = ColumnBuilder.getNew()
                        .setColumnProperty("aciDesc", String.class.getName())
                        .setTitle("NOMBRE DEL ACI")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(130)
                        .build();

                AbstractColumn columnaAciRif = ColumnBuilder.getNew()
                        .setColumnProperty("aciRif", String.class.getName())
                        .setTitle("RIF DEL ACI")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(130)
                        .build();

                AbstractColumn columnaAciTlf = ColumnBuilder.getNew()
                        .setColumnProperty("aciTLF", String.class.getName())
                        .setTitle("NRO.TELF.ACI")
                        .setHeaderStyle(styleColumnsHeader)
                        .setStyle(styleColumns)
                        .setWidth(130)
                        .build();

                drbe.addColumn(columnaComerDesc)
                    .addColumn(columnaComerRif)
                    .addColumn(columnaContTelefMov)
                    .addColumn(columnTerminal)
                    .addColumn(columnTipoPlan)
                    .addColumn(columnaPlanId)
                    .addColumn(columnaPlanNombre)
                    .addColumn(columnaEstatus)
                    .addColumn(columnaTarifa)
                    .addColumn(columnaMontoInicial)
                    .addColumn(columnaMoneda)
                    .addColumn(columnaFechaIni)
                    .addColumn(columnaFechaFin)
                    .addColumn(columnaFrecuencia)
                    .addColumn(columnaAciDesc)
                    .addColumn(columnaAciRif)
                    .addColumn(columnaAciTlf)
                    .setReportName("Reporte de Planes por Terminal")
                    .setUseFullPageWidth(true)
                    .setDetailHeight(10)
                    //.setLeftMargin(40)
                    //.setRightMargin(40)
                    .setWhenNoDataNoPages()
                    .setIgnorePagination(true)
                    .setDefaultStyles(styleTitle, styleSubTitle, styleColumnsHeader, styleColumns);

                drbe.setTemplateFile("/templates/consultaPlanesComercioExcel.jrxml");

                for(PlanesConsulta registros: resultado){
                    elementToInclude = new PlanesConsultaRep();
                    elementToInclude.setAboTerminal(registros.getAboTerminal());
                    elementToInclude.setPlanId(registros.getPlanId());
                    elementToInclude.setDescTipoPlan(registros.getDescTipoPlan());
                    elementToInclude.setPlanNombre(registros.getPlanNombre());
                    elementToInclude.setEstatusDesc(registros.getEstatusDesc());
                    elementToInclude.setMontoTarifa(registros.getMontoTarifa());
                    elementToInclude.setMontoInicial(registros.getMontoInicial());
                    elementToInclude.setMonedaDesc(registros.getMonedaDesc());
                    elementToInclude.setPlanPlazo(registros.getPlanPlazo());
                    if(null != registros.getFechaInicio()){
                        elementToInclude.setFechaInicio(new Fecha(registros.getFechaInicio()).getString("dd-MM-yyyy"));
                    }else{
                        elementToInclude.setFechaInicio("");
                    }
                    if(null != registros.getFechaFin()){
                        elementToInclude.setFechaFin(new Fecha(registros.getFechaFin()).getString("dd-MM-yyyy"));
                    }else{
                        elementToInclude.setFechaFin("");
                    }
                    elementToInclude.setFrecuenciaDesc(registros.getFrecuenciaDesc());
                    elementToInclude.setComerDesc(registros.getComerDesc());
                    elementToInclude.setComerRif(registros.getComerRif());
                    elementToInclude.setContTelefMov(registros.getContTelefMov());
                    elementToInclude.setAciDesc(registros.getAciDesc());
                    elementToInclude.setAciRif(registros.getAciRif());
                    elementToInclude.setAciTLF(registros.getAciTLF());
                    dataResultE.add(elementToInclude);
                }

                montoTotalFacturadoFormato = Utilidades.FormatearNumero(montoTotalFacturado.toString());
                montoTotalDeduccionesFormato = Utilidades.FormatearNumero(montoTotalDeducciones.toString());
                totalAbonoFormato = Utilidades.FormatearNumero(totalAbono.toString());

                //Petro
                montoTotalFacturadoPetroFormato = Utilidades.FormatearNumeroPetro(montoTotalFacturado.divide(montoPetro, 6, RoundingMode.HALF_UP).toString());
                montoTotalDeduccionesPetroFormato = Utilidades.FormatearNumeroPetro(montoTotalDeducciones.divide(montoPetro, 6, RoundingMode.HALF_UP).toString());
                totalAbonoPetroFormato = Utilidades.FormatearNumeroPetro(totalAbono.divide(montoPetro, 6, RoundingMode.HALF_UP).toString());

                params.put("montoTotalFacturado", montoTotalFacturadoFormato);
                params.put("montoTotalDeducciones", montoTotalDeduccionesFormato);
                params.put("totalAbono", totalAbonoFormato);

                //Petro
                params.put("montoTotalFacturadoPetro", montoTotalFacturadoPetroFormato);
                params.put("montoTotalDeduccionesPetro", montoTotalDeduccionesPetroFormato);
                params.put("totalAbonoPetro", totalAbonoPetroFormato);

                Collection<PlanesConsultaRep> dataOneReportE = dataResultE;

                dre = drbe.build();

                dataOneReportE = SortUtils.sortCollection(dataOneReportE,dre.getColumns());

                //Crea el JRDataSource a partir de la coleccion de datos
                dse = new JRBeanCollectionDataSource(dataOneReportE);

                /**
                 * Creamos el objeto JasperReport que pasamos como parametro a
                 * DynamicReport,junto con una nueva instancia de ClassicLayoutManager
                 * y el JRDataSource
                 */
                jre = DynamicJasperHelper.generateJasperReport(dre, getLayoutManager(), params);

                /**
                 * Creamos el objeto que imprimiremos pasando como parametro
                 * el JasperReport object, y el JRDataSource
                 */
                if (dse != null){
                        jpe = JasperFillManager.fillReport(jre, params, dse);
                }else{
                        jpe = JasperFillManager.fillReport(jre, params);
                }

                exportReportXLS(rutaCobranzaComercios + "/" + fechaEjecucion.getString("yyyy")+ "/" + nombreMes.toUpperCase(),"1000Pagos_clientes_x_plan_" + fechaEjecucion.getString("yyyyMMddHHmmss"));
                
            }

        } catch (Exception cve) {
            logger.error(cve.getMessage());
        }

    }
}
