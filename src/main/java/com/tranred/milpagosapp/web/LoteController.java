package com.tranred.milpagosapp.web;

import com.tranred.milpagosapp.domain.Afiliado;
import com.tranred.milpagosapp.domain.Banco;
import com.tranred.milpagosapp.domain.Comercio;
import com.tranred.milpagosapp.domain.Historico;
import com.tranred.milpagosapp.domain.HistoricoAliado;
import com.tranred.milpagosapp.domain.LoteDetalle;
import com.tranred.milpagosapp.domain.Lotes;
import com.tranred.milpagosapp.domain.Usuario;
import com.tranred.milpagosapp.repository.IAfiliadoDAO;
import com.tranred.milpagosapp.repository.IHistoricoDAO;
import com.tranred.milpagosapp.repository.JPAAfiliadoDAO;
import com.tranred.milpagosapp.repository.JPABancoDAO;
import com.tranred.milpagosapp.repository.JPALoteDetalleDAO;
import com.tranred.milpagosapp.repository.JPALotesDAO;
import com.tranred.milpagosapp.service.ConsultaLoteForm;
import com.tranred.milpagosapp.service.GenerarLoteForm;
import com.tranred.milpagosapp.service.IBitacoraService;
import com.tranred.milpagosapp.service.IComercioService;
import com.tranred.milpagosapp.util.Utilidades;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 *  Clase que actua como controlador para la opción de carga de lotes.
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 * 
 */

@PropertySource("classpath:app.properties")
@Controller
public class LoteController{    
    
    protected final Log logger = LogFactory.getLog(getClass());
    
    @Autowired
    private Utilidades utils;
    
    @Autowired
    private JPALotesDAO loteDAO;
    
    @Autowired
    private IAfiliadoDAO afiliadoDAO;
    
    @Autowired
    private JPALoteDetalleDAO loteDetalleDAO;
    
    @Autowired
    private JPABancoDAO bancoDAO;
    
    @Autowired
    private IHistoricoDAO historicoDAO;
    
    @Autowired
    private IComercioService comercioService;
    
    @Autowired
    private IBitacoraService bitacora;                    
    
    @Value("${ruta.archivoPagos.BVC}")
    private String rutaBVC;
    
    @Value("${ruta.archivoPagos.BNC}")
    private String rutaBNC;
    
    @Value("${ruta.archivoPagos.100Banco}")
    private String ruta100Banco;
    
    @Value("${ruta.archivoPagos.BPL}")
    private String rutaBPL;
    
    /* Estado inicial del formulario Cargar Lote */
    @RequestMapping(value="/lotePagos.htm", method = RequestMethod.GET)
    public @ModelAttribute("lotePagos") GenerarLoteForm lotePagos() {
        return new GenerarLoteForm();
    }
    
    /* Estado inicial del formulario Consulta Lote */
    @RequestMapping(value="/consultaLotes.htm", method = RequestMethod.GET)
    public @ModelAttribute("consultaLotes") ConsultaLoteForm consultaLote() {
        return new ConsultaLoteForm();
    }
    
    /* Accion post del formulario Lote Pagos */
    @RequestMapping(value="/lotePagos.htm", method = RequestMethod.POST)
    public ModelAndView generarLote(@ModelAttribute("lotePagos") @Valid GenerarLoteForm lotePagos, BindingResult result, HttpServletRequest request, HttpServletResponse response, Model modelo) throws IOException, ParseException {
    	
        if (result.hasErrors()) {
            return new ModelAndView("lotePagos");
        }
        
        String nombreArchivo, rutaArchivo = "", line1, line2, line3, email, nombreComercio, tipoCuentaAbono;
        BigDecimal montoTotal = new BigDecimal(0), divisorPorcentaje = new BigDecimal(100), montoTotalArchivos = new BigDecimal(0);
        BigDecimal montoInicialAliado = new BigDecimal(utils.parametro("monto.aliado.inicial").get(0).getValor()), montoMedioAliado = new BigDecimal(utils.parametro("monto.aliado.medio").get(0).getValor()), montoTopeBVC = new BigDecimal(utils.parametro("lote.monto.tope.bvc").get(0).getValor()), montoTopeBNC = new BigDecimal(utils.parametro("lote.monto.tope.bnc").get(0).getValor()), montoTope100Banco = new BigDecimal(utils.parametro("lote.monto.tope.100").get(0).getValor()), montoTopeBPL = new BigDecimal(utils.parametro("lote.monto.tope.bpl").get(0).getValor());        
        montoTotalArchivos = montoTotalArchivos.setScale(2, RoundingMode.CEILING);        
        montoInicialAliado = montoInicialAliado.setScale(2, RoundingMode.CEILING);
        montoMedioAliado = montoMedioAliado.setScale(2, RoundingMode.CEILING);        
        BigDecimal montoInicial = new BigDecimal(0), montoMedio = new BigDecimal(0), montoAlto = new BigDecimal(0), montoAbono = new BigDecimal(0);
        montoInicial = montoInicial.setScale(2, RoundingMode.CEILING);
        montoMedio = montoMedio.setScale(2, RoundingMode.CEILING);
        montoAlto = montoAlto.setScale(2, RoundingMode.CEILING);
        montoAbono = montoAbono.setScale(2, RoundingMode.CEILING);
        BigDecimal porcentajeInicial = new BigDecimal(utils.parametro("porcentaje.aliado.inicial").get(0).getValor()).divide(divisorPorcentaje), porcentajeMedio = new BigDecimal(utils.parametro("porcentaje.aliado.medio").get(0).getValor()).divide(divisorPorcentaje), porcentajeAlto = new BigDecimal(utils.parametro("porcentaje.aliado.alto").get(0).getValor()).divide(divisorPorcentaje);
        int totalRegistros, totalRegistrosGen = 0, numeroLote = 0, totalRegistrosExcluidos = 0, cantidadArchivos = 0;
        SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
        SimpleDateFormat dfFechaValor = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat dfFechaDetalle = new SimpleDateFormat("ddMMyyyy");
        SimpleDateFormat dfsql = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat dfHora = new SimpleDateFormat("hhmmss");
        Locale currentLocale = new Locale("es");
        DecimalFormatSymbols unusualSymbols = new DecimalFormatSymbols(currentLocale);
        unusualSymbols.setDecimalSeparator(',');
        unusualSymbols.setGroupingSeparator('.');
        DecimalFormat dfMonto = new DecimalFormat("###,###.00",unusualSymbols);
        
        Date ahora = new Date();                       
                        
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");                        
        
        List<Afiliado> afiliados;                
        afiliados =  afiliadoDAO.getAfiliadoByBancoList(lotePagos.getBanco());
        java.sql.Date fechaDesdesql = Utilidades.convierteFechaSqlsinHora(lotePagos.getFechaDesde());
        
        if(!afiliados.isEmpty()){
            
            for(Afiliado codigosAfil: afiliados){
                
                if("0104".equals(lotePagos.getBanco())){
                    numeroLote = Integer.parseInt(loteDAO.getNumeroLote("D0U", df.format(ahora))) + 1;
                    rutaArchivo = rutaBVC + "/BVC/";
                }else if("0191".equals(lotePagos.getBanco())){
                    numeroLote = Integer.parseInt(loteDAO.getNumeroLote("BNC", df.format(ahora))) + 1;
                    rutaArchivo = rutaBNC + "/BNC/";
                }else if("0156".equals(lotePagos.getBanco())){
                    numeroLote = Integer.parseInt(loteDAO.getNumeroLote("100%", df.format(ahora))) + 1;
                    rutaArchivo = ruta100Banco + "/100%/";
                }else if("0138".equals(lotePagos.getBanco())){
                    numeroLote = Integer.parseInt(loteDAO.getNumeroLote("BPL", df.format(ahora))) + 1;
                    rutaArchivo = rutaBPL + "/PLAZA/";
                }

                nombreArchivo = df.format(ahora) + StringUtils.leftPad(String.valueOf(numeroLote),2,"0");                
                File fichero = new File(rutaArchivo,""+ nombreArchivo +".txt");
                File archivoXLS = new File(rutaArchivo,""+ nombreArchivo +".xls");
                fichero.getParentFile().mkdirs();
                archivoXLS.getParentFile().mkdirs();
                Lotes loteCabecera = new Lotes(); 
                totalRegistros = 0;
                montoTotal = new BigDecimal(0);
                montoTotal = montoTotal.setScale(2, RoundingMode.CEILING);
                
                //Archivo de pago a comercios
                if("1".equals(lotePagos.getTipoPago())){                                           

                    try {                                

                        List<Historico> resultado;                             
                        resultado =  historicoDAO.getHistoricoPagoComercio(fechaDesdesql,codigosAfil.getCodigoAfiliado());

                        if(!resultado.isEmpty()){

                            //Estructura para Venezolano de Credito
                            if("0104".equals(lotePagos.getBanco())){

                                // A partir del objeto File creamos el fichero físicamente
                                if (fichero.createNewFile()){

                                    List<Banco> codigoSwift;                        
                                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fichero), "Cp1252"));

                                    //Creamos el reporte excel con la data del archivo generado (Cabecera)

                                    archivoXLS.createNewFile();
                                    int x = 0;
                                    Workbook libro = new HSSFWorkbook();
                                    FileOutputStream archivo = new FileOutputStream(archivoXLS);
                                    Sheet hoja = libro.createSheet("Archivo Pago a Comercios");                        
                                    CellStyle style = libro.createCellStyle();
                                    Font font = libro.createFont();                    
                                    font.setColor(HSSFColor.WHITE.index);
                                    style.setFont(font);                    
                                    style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
                                    style.setFillPattern(CellStyle.SOLID_FOREGROUND);
                                    style.setAlignment(HSSFCellStyle.ALIGN_JUSTIFY);
                                    style.setBorderLeft((short)8);

                                    Row fila = hoja.createRow(x);

                                    /*Cada fila tendrá 7 celdas de datos*/
                                    for(int c=0;c<8;c++){
                                        /*Creamos la celda a partir de la fila actual*/
                                        Cell celda = fila.createCell(c);

                                        switch (c) {
                                            case 0:  celda.setCellValue("Numero Pago a Proveedor");
                                                     celda.setCellStyle(style);   
                                                     break;
                                            case 1:  celda.setCellValue("RIF");
                                                     celda.setCellStyle(style);
                                                     break;
                                            case 2:  celda.setCellValue("Beneficiario");
                                                     celda.setCellStyle(style);
                                                     break;                                             
                                            case 3:  celda.setCellValue("Banco Beneficiario");
                                                     celda.setCellStyle(style);
                                                     break;
                                            case 4:  celda.setCellValue("Cuenta Beneficiario");
                                                     celda.setCellStyle(style);
                                                     break;
                                            case 5:  celda.setCellValue("Concepto");
                                                     celda.setCellStyle(style);
                                                     break;
                                            case 6:  celda.setCellValue("Monto");
                                                     celda.setCellStyle(style);
                                                     break;
                                            case 7:  celda.setCellValue("Terminal");
                                                     celda.setCellStyle(style);
                                                     break;         
                                        }
                                    }
                                    x++;
                                    //Fin Excel Cabecera

                                        for(Historico registros: resultado){

                                            //Validar si el comercio no se toma en cuenta para el pago
                                            if(!"1".equals(registros.getExcluirArchivoPago())){

                                                montoTotal = montoTotal.add(registros.getMontoTotal());
                                                montoTotalArchivos = montoTotalArchivos.add(registros.getMontoTotal());

                                                //Valido el monto tope por archivo
                                                if(montoTotal.compareTo(montoTopeBVC)==1){

                                                    montoTotal = montoTotal.subtract(registros.getMontoTotal());

                                                    //registro tipo 9
                                                    line3 = "D0U" + StringUtils.leftPad(nombreArchivo,8,"0") + StringUtils.leftPad("",16,"0") + "9" + dfFechaValor.format(ahora) + StringUtils.leftPad(String.valueOf(montoTotal).replace(".", "").replace(",", ""),15,"0") + StringUtils.leftPad(String.valueOf(totalRegistros),15,"0") + "VES" + StringUtils.rightPad(codigosAfil.getNumeroCuentaAbono(),35," ") + StringUtils.rightPad("",35," ") + StringUtils.leftPad(String.valueOf(montoTotal).replace(".", "").replace(",", ""),15,"0") + "VES" + StringUtils.rightPad("",30," ") +"00000" + StringUtils.rightPad("",307," ") + "X";

                                                    Lotes loteCabeceraTemp = new Lotes();
                                                    //Guarda en la tabla LotesXbanco
                                                    loteCabeceraTemp.setActividadEconomica(00);
                                                    loteCabeceraTemp.setCantidadPagos(totalRegistros);
                                                    loteCabeceraTemp.setCodigoCompania("D0U");
                                                    loteCabeceraTemp.setCodigoAfiliado(registros.getCodigoAfiliado());
                                                    loteCabeceraTemp.setCodigoBanco(lotePagos.getBanco());
                                                    loteCabeceraTemp.setTipoArchivo(lotePagos.getTipoPago());
                                                    loteCabeceraTemp.setCodigoMonedaCredito("VES");
                                                    loteCabeceraTemp.setCodigoMonedaDebito("VES");
                                                    loteCabeceraTemp.setCuentaDebito(registros.getNroCuentaAbono());
                                                    loteCabeceraTemp.setFechaValor(Utilidades.convierteFechaSqlsinHora(dfsql.format(ahora)));
                                                    loteCabeceraTemp.setMontoTotal(montoTotal);
                                                    loteCabeceraTemp.setMotivoOperacion(000);
                                                    loteCabeceraTemp.setNumeroLote(nombreArchivo);
                                                    loteCabeceraTemp.setTipoRegistro(9);
                                                    loteDAO.saveLote(loteCabeceraTemp);
                                                    bw.write(line3.trim());
                                                    bw.close();                                                                                                           

                                                    totalRegistros = 0;
                                                    montoTotal = new BigDecimal(0);
                                                    montoTotal = montoTotal.setScale(2, RoundingMode.CEILING);
                                                    montoTotal = montoTotal.add(registros.getMontoTotal());
                                                    cantidadArchivos++;
                                                    numeroLote++;
                                                    nombreArchivo = df.format(ahora) + StringUtils.leftPad(String.valueOf(numeroLote),2,"0"); 
                                                    fichero = new File(rutaBVC + "/BVC/",""+ nombreArchivo +".txt");
                                                    fichero.getParentFile().mkdirs();

                                                    if (fichero.createNewFile()){                                                                        
                                                        bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fichero), "Cp1252"));

                                                        LoteDetalle loteDetalle4 = new LoteDetalle();
                                                        LoteDetalle loteDetalle5 = new LoteDetalle();

                                                        if(registros.getEmail()==null){
                                                            email = "";
                                                        }else{
                                                            email = registros.getEmail();
                                                        }

                                                        //Registro tipo 4
                                                        line1 = "D0U" + nombreArchivo + StringUtils.leftPad(registros.getId().toString(),8,"0") + StringUtils.leftPad("",8,"0") + "4" + StringUtils.rightPad(email,129," ") + registros.getRifComercio().substring(0, 1) + StringUtils.leftPad(registros.getRifComercio().substring(1),16,"0") + StringUtils.rightPad("",325," ") + "X";                                

                                                        //Guarda en la tabla LoteDetalle                     
                                                        loteDetalle4.setCodigoCompania("D0U");                        
                                                        loteDetalle4.setNumeroLote(nombreArchivo);
                                                        loteDetalle4.setNumeroPagoProveedor(registros.getId().toString());                        
                                                        loteDetalle4.setTipoRegistro(4);
                                                        loteDetalle4.setEmailBeneficiario(registros.getEmail());
                                                        loteDetalle4.setRifBeneficiario(registros.getRifComercio());
                                                        loteDetalle4.setMontoTotal(registros.getMontoTotal());
                                                        loteDetalleDAO.saveLoteDetalle(loteDetalle4);

                                                        bw.write(line1.trim());
                                                        bw.write("\r\n");

                                                        codigoSwift = bancoDAO.getCodigoSwiftList(registros.getCuentaAbono().substring(0, 4));
                                                        if("0104".equals(registros.getCodigoBanco())){
                                                            tipoCuentaAbono = "1";
                                                        }else{
                                                            tipoCuentaAbono = "3";
                                                        }

                                                        int maxLength = (registros.getDescripcionComercio().length() < 35)?registros.getDescripcionComercio().length():35;
                                                        nombreComercio = registros.getDescripcionComercio().substring(0, maxLength);

                                                        //Registro tipo 5
                                                        line2 = "D0U" + nombreArchivo + StringUtils.leftPad(registros.getId().toString(),8,"0") + StringUtils.leftPad("",8,"0") + "5" + StringUtils.leftPad(String.valueOf(registros.getMontoTotal().setScale(2, RoundingMode.CEILING)).replace(".", "").replace(",", ""),15,"0") + StringUtils.rightPad(nombreComercio,60," ") + tipoCuentaAbono + StringUtils.rightPad("",17," ") + "000" + StringUtils.rightPad("",4," ") + StringUtils.rightPad(registros.getCuentaAbono(),35," ") + "VESVES" + StringUtils.rightPad("",28," ") + StringUtils.rightPad("Abono por concepto MILPAGO comercio: " + registros.getRifComercio().trim() + " " + registros.getLote().trim() + " " + registros.getTerminal().trim() + " " + registros.getFecha().toString() + "",105," ") + StringUtils.rightPad("",6," ") + StringUtils.rightPad(codigoSwift.get(0).getCodigoSwift(),12," ") + "BIC" + StringUtils.rightPad("",132," ") + "00000" + StringUtils.rightPad(codigosAfil.getNumeroCuentaAbono(),35," ") + StringUtils.rightPad("",4," ") + "X";                                

                                                        //Guarda en la tabla LoteDetalle                  
                                                        loteDetalle5.setCodigoCompania("D0U");
                                                        loteDetalle5.setNumeroLote(nombreArchivo);
                                                        loteDetalle5.setNumeroPagoProveedor(registros.getId().toString());
                                                        loteDetalle5.setTipoRegistro(5);
                                                        loteDetalle5.setMontoTotal(registros.getMontoTotal());
                                                        loteDetalle5.setNombreBeneficiario(registros.getDescripcionComercio().trim());
                                                        loteDetalle5.setTipoPago(Integer.parseInt(tipoCuentaAbono));
                                                        loteDetalle5.setCuentaBeneficiario(registros.getCuentaAbono());
                                                        loteDetalle5.setCodigoMonedaCredito("VES");
                                                        loteDetalle5.setCodigoMonedaDebito("VES");
                                                        loteDetalle5.setConceptoPago("Abono por concepto MILPAGO comercio: " + registros.getRifComercio().trim() + " " + registros.getLote().trim() + " " + registros.getTerminal().trim() + " " + registros.getFecha().toString() + "");
                                                        loteDetalle5.setCodigoBancoBeneficiario("");
                                                        loteDetalle5.setTipoCodigoBanco("BIC");                        
                                                        loteDetalle5.setMotivoOperacion(000);
                                                        loteDetalle5.setActividadEconomica(00);
                                                        loteDetalle5.setCuentaDebito(codigosAfil.getNumeroCuentaAbono());                        
                                                        loteDetalleDAO.saveLoteDetalle(loteDetalle5);

                                                        bw.write(line2.trim());
                                                        bw.write("\r\n");

                                                        totalRegistros++;

                                                        //Reporte Excel detalle
                                                        fila = hoja.createRow(x);

                                                        /*Cada fila tendrá 7 celdas de datos*/
                                                        for(int c=0;c<8;c++){
                                                            /*Creamos la celda a partir de la fila actual*/
                                                            Cell celda = fila.createCell(c);

                                                            switch (c) {
                                                                case 0:  celda.setCellValue(StringUtils.leftPad(registros.getId().toString(),8,"0"));
                                                                         break;
                                                                case 1:  celda.setCellValue(registros.getRifComercio().trim());
                                                                         break;
                                                                case 2:  celda.setCellValue(registros.getDescripcionComercio().trim());
                                                                         break;                                             
                                                                case 3:  celda.setCellValue(registros.getCodigoBanco());
                                                                         break;
                                                                case 4:  celda.setCellValue(registros.getCuentaAbono());
                                                                         break;
                                                                case 5:  celda.setCellValue("Abono por concepto MILPAGO comercio: " + registros.getRifComercio().trim() + " " + registros.getLote().trim() + " " + registros.getTerminal().trim() + " " + registros.getFecha().toString() + "");
                                                                         break;
                                                                case 6:  celda.setCellValue(dfMonto.format(registros.getMontoTotal()));
                                                                         break;
                                                                case 7:  celda.setCellValue(registros.getTerminal());
                                                                         break;         
                                                            }                            
                                                        }
                                                        x++;
                                                        //Fin Reporte Excel Detalle
                                                    }

                                                }else{

                                                    LoteDetalle loteDetalle4 = new LoteDetalle();
                                                    LoteDetalle loteDetalle5 = new LoteDetalle();

                                                    if(registros.getEmail()==null){
                                                        email = "";
                                                    }else{
                                                        email = registros.getEmail();
                                                    }

                                                    //Registro tipo 4
                                                    line1 = "D0U" + nombreArchivo + StringUtils.leftPad(registros.getId().toString(),8,"0") + StringUtils.leftPad("",8,"0") + "4" + StringUtils.rightPad(email,129," ") + registros.getRifComercio().substring(0, 1) + StringUtils.leftPad(registros.getRifComercio().substring(1),16,"0") + StringUtils.rightPad("",325," ") + "X";                                

                                                    //Guarda en la tabla LoteDetalle                     
                                                    loteDetalle4.setCodigoCompania("D0U");                        
                                                    loteDetalle4.setNumeroLote(nombreArchivo);
                                                    loteDetalle4.setNumeroPagoProveedor(registros.getId().toString());                        
                                                    loteDetalle4.setTipoRegistro(4);
                                                    loteDetalle4.setEmailBeneficiario(registros.getEmail());
                                                    loteDetalle4.setRifBeneficiario(registros.getRifComercio());
                                                    loteDetalle4.setMontoTotal(registros.getMontoTotal());
                                                    loteDetalleDAO.saveLoteDetalle(loteDetalle4);

                                                    bw.write(line1.trim());
                                                    bw.write("\r\n");

                                                    codigoSwift = bancoDAO.getCodigoSwiftList(registros.getCuentaAbono().substring(0, 4));
                                                    if("0104".equals(registros.getCodigoBanco())){
                                                        tipoCuentaAbono = "1";
                                                    }else{
                                                        tipoCuentaAbono = "3";
                                                    }

                                                    int maxLength = (registros.getDescripcionComercio().length() < 35)?registros.getDescripcionComercio().length():35;
                                                    nombreComercio = registros.getDescripcionComercio().substring(0, maxLength);

                                                    //Registro tipo 5
                                                    line2 = "D0U" + nombreArchivo + StringUtils.leftPad(registros.getId().toString(),8,"0") + StringUtils.leftPad("",8,"0") + "5" + StringUtils.leftPad(String.valueOf(registros.getMontoTotal().setScale(2, RoundingMode.CEILING)).replace(".", "").replace(",", ""),15,"0") + StringUtils.rightPad(nombreComercio,60," ") + tipoCuentaAbono + StringUtils.rightPad("",17," ") + "000" + StringUtils.rightPad("",4," ") + StringUtils.rightPad(registros.getCuentaAbono(),35," ") + "VESVES" + StringUtils.rightPad("",28," ") + StringUtils.rightPad("Abono por concepto MILPAGO comercio: " + registros.getRifComercio().trim() + " " + registros.getLote().trim() + " " + registros.getTerminal().trim() + " " + registros.getFecha().toString() + "",105," ") + StringUtils.rightPad("",6," ") + StringUtils.rightPad(codigoSwift.get(0).getCodigoSwift(),12," ") + "BIC" + StringUtils.rightPad("",132," ") + "00000" + StringUtils.rightPad(codigosAfil.getNumeroCuentaAbono(),35," ") + StringUtils.rightPad("",4," ") + "X";                                

                                                    //Guarda en la tabla LoteDetalle                  
                                                    loteDetalle5.setCodigoCompania("D0U");
                                                    loteDetalle5.setNumeroLote(nombreArchivo);
                                                    loteDetalle5.setNumeroPagoProveedor(registros.getId().toString());
                                                    loteDetalle5.setTipoRegistro(5);
                                                    loteDetalle5.setMontoTotal(registros.getMontoTotal());
                                                    loteDetalle5.setNombreBeneficiario(registros.getDescripcionComercio().trim());
                                                    loteDetalle5.setTipoPago(Integer.parseInt(tipoCuentaAbono));
                                                    loteDetalle5.setCuentaBeneficiario(registros.getCuentaAbono());
                                                    loteDetalle5.setCodigoMonedaCredito("VES");
                                                    loteDetalle5.setCodigoMonedaDebito("VES");
                                                    loteDetalle5.setConceptoPago("Abono por concepto MILPAGO comercio: " + registros.getRifComercio().trim() + " " + registros.getLote().trim() + " " + registros.getTerminal().trim() + " " + registros.getFecha().toString() + "");
                                                    loteDetalle5.setCodigoBancoBeneficiario("");
                                                    loteDetalle5.setTipoCodigoBanco("BIC");                        
                                                    loteDetalle5.setMotivoOperacion(000);
                                                    loteDetalle5.setActividadEconomica(00);
                                                    loteDetalle5.setCuentaDebito(codigosAfil.getNumeroCuentaAbono());                        
                                                    loteDetalleDAO.saveLoteDetalle(loteDetalle5);

                                                    bw.write(line2.trim());
                                                    bw.write("\r\n"); 

                                                    totalRegistros++;

                                                    //Reporte Excel detalle
                                                    fila = hoja.createRow(x);

                                                    /*Cada fila tendrá 7 celdas de datos*/
                                                    for(int c=0;c<8;c++){
                                                        /*Creamos la celda a partir de la fila actual*/
                                                        Cell celda = fila.createCell(c);

                                                        switch (c) {
                                                            case 0:  celda.setCellValue(StringUtils.leftPad(registros.getId().toString(),8,"0"));
                                                                     break;
                                                            case 1:  celda.setCellValue(registros.getRifComercio().trim());
                                                                     break;
                                                            case 2:  celda.setCellValue(registros.getDescripcionComercio().trim());
                                                                     break;                                             
                                                            case 3:  celda.setCellValue(registros.getCodigoBanco());
                                                                     break;
                                                            case 4:  celda.setCellValue(registros.getCuentaAbono());
                                                                     break;
                                                            case 5:  celda.setCellValue("Abono por concepto MILPAGO comercio: " + registros.getRifComercio().trim() + " " + registros.getLote().trim() + " " + registros.getTerminal().trim() + " " + registros.getFecha().toString() + "");
                                                                     break;
                                                            case 6:  celda.setCellValue(dfMonto.format(registros.getMontoTotal()));
                                                                     break;
                                                            case 7:  celda.setCellValue(registros.getTerminal());
                                                                     break;          
                                                        }                            
                                                    }
                                                    x++;
                                                    //Fin Reporte Excel Detalle
                                                }

                                            }else{
                                                totalRegistrosExcluidos++;
                                            }
                                        }                           

                                        if(totalRegistros>0){

                                            //registro tipo 9
                                            line3 = "D0U" + StringUtils.leftPad(nombreArchivo,8,"0") + StringUtils.leftPad("",16,"0") + "9" + dfFechaValor.format(ahora) + StringUtils.leftPad(String.valueOf(montoTotal).replace(".", "").replace(",", ""),15,"0") + StringUtils.leftPad(String.valueOf(totalRegistros),15,"0") + "VES" + StringUtils.rightPad(codigosAfil.getNumeroCuentaAbono(),35," ") + StringUtils.rightPad("",35," ") + StringUtils.leftPad(String.valueOf(montoTotal).replace(".", "").replace(",", ""),15,"0") + "VES" + StringUtils.rightPad("",30," ") +"00000" + StringUtils.rightPad("",307," ") + "X";

                                            //Guarda en la tabla LotesXbanco
                                            loteCabecera.setActividadEconomica(00);
                                            loteCabecera.setCantidadPagos(totalRegistros);
                                            loteCabecera.setCodigoCompania("D0U");
                                            loteCabecera.setCodigoAfiliado(codigosAfil.getCodigoAfiliado());
                                            loteCabecera.setCodigoBanco(lotePagos.getBanco());
                                            loteCabecera.setTipoArchivo(lotePagos.getTipoPago());
                                            loteCabecera.setCodigoMonedaCredito("VES");
                                            loteCabecera.setCodigoMonedaDebito("VES");
                                            loteCabecera.setCuentaDebito(codigosAfil.getNumeroCuentaAbono());
                                            loteCabecera.setFechaValor(Utilidades.convierteFechaSqlsinHora(dfsql.format(ahora)));
                                            loteCabecera.setMontoTotal(montoTotal);
                                            loteCabecera.setMotivoOperacion(000);
                                            loteCabecera.setNumeroLote(nombreArchivo);
                                            loteCabecera.setTipoRegistro(9);
                                            loteDAO.saveLote(loteCabecera);
                                            bw.write(line3.trim());
                                            bw.close();

                                            //Escribir en el reporte Excel
                                            for(int j = 0; j < 7; j++) { hoja.autoSizeColumn((short)j); }
                                            /*Escribimos en el libro*/
                                            libro.write(archivo);
                                            /*Cerramos el flujo de datos*/
                                            archivo.close();
                                            //Fin escribir reporte Excel
                                        }else{
                                            bw.close();
                                            fichero.delete();                                    
                                        }                            

                                }else{
                                    modelo.addAttribute("messageError", "El archivo con el nombre " + nombreArchivo +" ya existe, por favor verifique e intente nuevamente");
                                    return new ModelAndView("lotePagos", "model", modelo);
                                }                                                                        
                            }else if("0191".equals(lotePagos.getBanco())){ //Estructura para BNC

                                // A partir del objeto File creamos el fichero físicamente
                                if (fichero.createNewFile()){

                                    List<Banco> codigoSwift;                        
                                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fichero), "Cp1252"));

                                    //Creamos el reporte excel con la data del archivo generado (Cabecera)

                                    archivoXLS.createNewFile();
                                    int x = 0;
                                    Workbook libro = new HSSFWorkbook();
                                    FileOutputStream archivo = new FileOutputStream(archivoXLS);
                                    Sheet hoja = libro.createSheet("Archivo Pago a Comercios");                        
                                    CellStyle style = libro.createCellStyle();
                                    Font font = libro.createFont();                    
                                    font.setColor(HSSFColor.WHITE.index);
                                    style.setFont(font);                    
                                    style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
                                    style.setFillPattern(CellStyle.SOLID_FOREGROUND);
                                    style.setAlignment(HSSFCellStyle.ALIGN_JUSTIFY);
                                    style.setBorderLeft((short)8);

                                    Row fila = hoja.createRow(x);

                                    /*Cada fila tendrá 7 celdas de datos*/
                                    for(int c=0;c<8;c++){
                                        /*Creamos la celda a partir de la fila actual*/
                                        Cell celda = fila.createCell(c);

                                        switch (c) {
                                            case 0:  celda.setCellValue("Numero Pago a Proveedor");
                                                     celda.setCellStyle(style);   
                                                     break;
                                            case 1:  celda.setCellValue("RIF");
                                                     celda.setCellStyle(style);
                                                     break;
                                            case 2:  celda.setCellValue("Beneficiario");
                                                     celda.setCellStyle(style);
                                                     break;                                             
                                            case 3:  celda.setCellValue("Banco Beneficiario");
                                                     celda.setCellStyle(style);
                                                     break;
                                            case 4:  celda.setCellValue("Cuenta Beneficiario");
                                                     celda.setCellStyle(style);
                                                     break;
                                            case 5:  celda.setCellValue("Concepto");
                                                     celda.setCellStyle(style);
                                                     break;
                                            case 6:  celda.setCellValue("Monto");
                                                     celda.setCellStyle(style);
                                                     break;
                                            case 7:  celda.setCellValue("Terminal");
                                                     celda.setCellStyle(style);
                                                     break;         
                                        }
                                    }
                                    x++;
                                    //Fin Excel Cabecera

                                        for(Historico registros: resultado){

                                            //Validar si el comercio no se toma en cuenta para el pago
                                            if(!"1".equals(registros.getExcluirArchivoPago())){

                                                montoTotal = montoTotal.add(registros.getMontoTotal());
                                                montoTotalArchivos = montoTotalArchivos.add(registros.getMontoTotal());

                                                //Valido el monto tope por archivo
                                                if(montoTotal.compareTo(montoTopeBNC)==1){

                                                    montoTotal = montoTotal.subtract(registros.getMontoTotal());                                            
                                                    bw.close();

                                                    BufferedReader read= new BufferedReader(new FileReader(fichero));
                                                    ArrayList list = new ArrayList();

                                                    String dataRow = read.readLine(); 
                                                    while (dataRow != null){
                                                        list.add(dataRow);
                                                        dataRow = read.readLine(); 
                                                    }

                                                    FileWriter writer = new FileWriter(fichero);

                                                    //Registro cabecera
                                                    line3 = "C" + StringUtils.leftPad(String.valueOf(totalRegistros),5,"0") + StringUtils.leftPad(String.valueOf(montoTotal).replace(".", "").replace(",", ""),15,"0") + StringUtils.leftPad(nombreArchivo,10,"0") + "SSN00";

                                                    writer.append(line3);

                                                    for (int i = 0; i < list.size(); i++){
                                                        writer.append("\r\n");
                                                        writer.append(list.get(i).toString());
                                                    }
                                                    writer.flush();
                                                    writer.close();                                                                                                                                                                                

                                                    Lotes loteCabeceraTemp = new Lotes();
                                                    //Guarda en la tabla LotesXbanco
                                                    loteCabeceraTemp.setActividadEconomica(00);
                                                    loteCabeceraTemp.setCantidadPagos(totalRegistros);
                                                    loteCabeceraTemp.setCodigoCompania("BNC");
                                                    loteCabeceraTemp.setCodigoAfiliado(registros.getCodigoAfiliado());
                                                    loteCabeceraTemp.setCodigoBanco(lotePagos.getBanco());
                                                    loteCabeceraTemp.setTipoArchivo(lotePagos.getTipoPago());
                                                    loteCabeceraTemp.setCodigoMonedaCredito("VES");
                                                    loteCabeceraTemp.setCodigoMonedaDebito("VES");
                                                    loteCabeceraTemp.setCuentaDebito(codigosAfil.getNumeroCuentaAbono());
                                                    loteCabeceraTemp.setFechaValor(Utilidades.convierteFechaSqlsinHora(dfsql.format(ahora)));
                                                    loteCabeceraTemp.setMontoTotal(montoTotal);
                                                    loteCabeceraTemp.setMotivoOperacion(000);
                                                    loteCabeceraTemp.setNumeroLote(nombreArchivo);
                                                    loteCabeceraTemp.setTipoRegistro(9);
                                                    loteDAO.saveLote(loteCabeceraTemp);                                                                                                                                                       

                                                    totalRegistros = 0;
                                                    montoTotal = new BigDecimal(0);
                                                    montoTotal = montoTotal.setScale(2, RoundingMode.CEILING);
                                                    montoTotal = montoTotal.add(registros.getMontoTotal());
                                                    cantidadArchivos++;
                                                    numeroLote++;
                                                    nombreArchivo = df.format(ahora) + StringUtils.leftPad(String.valueOf(numeroLote),2,"0"); 
                                                    fichero = new File(rutaBNC + "/BNC/",""+ nombreArchivo +".txt");
                                                    fichero.getParentFile().mkdirs();

                                                    if (fichero.createNewFile()){                                                                        
                                                        bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fichero), "Cp1252"));

                                                        LoteDetalle loteDetalle5 = new LoteDetalle();                                                

                                                        if(registros.getEmail()==null){
                                                            email = "";
                                                        }else{
                                                            email = registros.getEmail();
                                                        }

                                                        codigoSwift = bancoDAO.getCodigoSwiftList(registros.getCuentaAbono().substring(0, 4));
                                                        if("0104".equals(registros.getCodigoBanco())){
                                                            tipoCuentaAbono = "1";
                                                        }else{
                                                            tipoCuentaAbono = "3";
                                                        }

                                                        int maxLength = (registros.getDescripcionComercio().length() < 35)?registros.getDescripcionComercio().length():80;
                                                        nombreComercio = registros.getDescripcionComercio().substring(0, maxLength);

                                                        //Registro tipo detalle
                                                        line1 = "D" + dfFechaDetalle.format(ahora) + codigosAfil.getNumeroCuentaAbono() + registros.getCuentaAbono() + StringUtils.leftPad(String.valueOf(registros.getMontoTotal().setScale(2, RoundingMode.CEILING)).replace(".", "").replace(",", ""),15,"0") + StringUtils.rightPad("Abono MILPAGO comercio: " + registros.getRifComercio().trim() + " " + registros.getLote().trim() + " " + registros.getTerminal().trim() + " " + registros.getFecha().toString() + "",60," ") + registros.getRifComercio().substring(0, 1) + StringUtils.leftPad(registros.getRifComercio().substring(1),9,"0") + StringUtils.rightPad(nombreComercio,80," ") + StringUtils.rightPad(email,100," ") + StringUtils.leftPad(registros.getTerminal().trim(),10,"0");

                                                        //Guarda en la tabla LoteDetalle                  
                                                        loteDetalle5.setCodigoCompania("BNC");
                                                        loteDetalle5.setNumeroLote(nombreArchivo);
                                                        loteDetalle5.setNumeroPagoProveedor(registros.getId().toString());
                                                        loteDetalle5.setTipoRegistro(5);
                                                        loteDetalle5.setMontoTotal(registros.getMontoTotal());
                                                        loteDetalle5.setNombreBeneficiario(registros.getDescripcionComercio().trim());
                                                        loteDetalle5.setTipoPago(Integer.parseInt(tipoCuentaAbono));
                                                        loteDetalle5.setCuentaBeneficiario(registros.getCuentaAbono());
                                                        loteDetalle5.setCodigoMonedaCredito("VES");
                                                        loteDetalle5.setCodigoMonedaDebito("VES");
                                                        loteDetalle5.setConceptoPago("Abono MILPAGO comercio: " + registros.getRifComercio().trim() + " " + registros.getLote().trim() + " " + registros.getTerminal().trim() + " " + registros.getFecha().toString() + "");
                                                        loteDetalle5.setCodigoBancoBeneficiario("");
                                                        loteDetalle5.setTipoCodigoBanco("BIC");                        
                                                        loteDetalle5.setMotivoOperacion(000);
                                                        loteDetalle5.setActividadEconomica(00);
                                                        loteDetalle5.setCuentaDebito(codigosAfil.getNumeroCuentaAbono());                        
                                                        loteDetalleDAO.saveLoteDetalle(loteDetalle5);

                                                        bw.write(line1.trim());
                                                        bw.write("\r\n");

                                                        totalRegistros++;

                                                        //Reporte Excel detalle
                                                        fila = hoja.createRow(x);

                                                        /*Cada fila tendrá 7 celdas de datos*/
                                                        for(int c=0;c<8;c++){
                                                            /*Creamos la celda a partir de la fila actual*/
                                                            Cell celda = fila.createCell(c);

                                                            switch (c) {
                                                                case 0:  celda.setCellValue(StringUtils.leftPad(registros.getId().toString(),8,"0"));
                                                                         break;
                                                                case 1:  celda.setCellValue(registros.getRifComercio().trim());
                                                                         break;
                                                                case 2:  celda.setCellValue(registros.getDescripcionComercio().trim());
                                                                         break;                                             
                                                                case 3:  celda.setCellValue(registros.getCodigoBanco());
                                                                         break;
                                                                case 4:  celda.setCellValue(registros.getCuentaAbono());
                                                                         break;
                                                                case 5:  celda.setCellValue("Abono por concepto MILPAGO comercio: " + registros.getRifComercio().trim() + " " + registros.getLote().trim() + " " + registros.getTerminal().trim() + " " + registros.getFecha().toString() + "");
                                                                         break;
                                                                case 6:  celda.setCellValue(dfMonto.format(registros.getMontoTotal()));
                                                                         break;
                                                                case 7:  celda.setCellValue(registros.getTerminal());
                                                                         break;         
                                                            }                            
                                                        }
                                                        x++;
                                                        //Fin Reporte Excel Detalle
                                                    }

                                                }else{

                                                    LoteDetalle loteDetalle5 = new LoteDetalle();

                                                    if(registros.getEmail()==null){
                                                        email = "";
                                                    }else{
                                                        email = registros.getEmail();
                                                    }

                                                    codigoSwift = bancoDAO.getCodigoSwiftList(registros.getCuentaAbono().substring(0, 4));
                                                    if("0104".equals(registros.getCodigoBanco())){
                                                        tipoCuentaAbono = "1";
                                                    }else{
                                                        tipoCuentaAbono = "3";
                                                    }

                                                    int maxLength = (registros.getDescripcionComercio().length() < 35)?registros.getDescripcionComercio().length():35;
                                                    nombreComercio = registros.getDescripcionComercio().substring(0, maxLength);

                                                    //Registro tipo detalle
                                                    line1 = "D" + dfFechaDetalle.format(ahora) + codigosAfil.getNumeroCuentaAbono() + registros.getCuentaAbono() + StringUtils.leftPad(String.valueOf(registros.getMontoTotal().setScale(2, RoundingMode.CEILING)).replace(".", "").replace(",", ""),15,"0") + StringUtils.rightPad("Abono MILPAGO comercio: " + registros.getRifComercio().trim() + " " + registros.getLote().trim() + " " + registros.getTerminal().trim() + " " + registros.getFecha().toString() + "",60," ") + registros.getRifComercio().substring(0, 1) + StringUtils.leftPad(registros.getRifComercio().substring(1),9,"0") + StringUtils.rightPad(nombreComercio,80," ") + StringUtils.rightPad(email,100," ") + StringUtils.leftPad(registros.getTerminal().trim(),10,"0");

                                                    //Guarda en la tabla LoteDetalle                  
                                                    loteDetalle5.setCodigoCompania("BNC");
                                                    loteDetalle5.setNumeroLote(nombreArchivo);
                                                    loteDetalle5.setNumeroPagoProveedor(registros.getId().toString());
                                                    loteDetalle5.setTipoRegistro(5);
                                                    loteDetalle5.setMontoTotal(registros.getMontoTotal());
                                                    loteDetalle5.setNombreBeneficiario(registros.getDescripcionComercio().trim());
                                                    loteDetalle5.setTipoPago(Integer.parseInt(tipoCuentaAbono));
                                                    loteDetalle5.setCuentaBeneficiario(registros.getCuentaAbono());
                                                    loteDetalle5.setCodigoMonedaCredito("VES");
                                                    loteDetalle5.setCodigoMonedaDebito("VES");
                                                    loteDetalle5.setConceptoPago("Abono MILPAGO comercio: " + registros.getRifComercio().trim() + " " + registros.getLote().trim() + " " + registros.getTerminal().trim() + " " + registros.getFecha().toString() + "");
                                                    loteDetalle5.setCodigoBancoBeneficiario("");
                                                    loteDetalle5.setTipoCodigoBanco("BIC");                        
                                                    loteDetalle5.setMotivoOperacion(000);
                                                    loteDetalle5.setActividadEconomica(00);
                                                    loteDetalle5.setCuentaDebito(codigosAfil.getNumeroCuentaAbono());                        
                                                    loteDetalleDAO.saveLoteDetalle(loteDetalle5);

                                                    bw.write(line1.trim());
                                                    bw.write("\r\n"); 

                                                    totalRegistros++;

                                                    //Reporte Excel detalle
                                                        fila = hoja.createRow(x);

                                                        /*Cada fila tendrá 7 celdas de datos*/
                                                        for(int c=0;c<8;c++){
                                                            /*Creamos la celda a partir de la fila actual*/
                                                            Cell celda = fila.createCell(c);

                                                            switch (c) {
                                                                case 0:  celda.setCellValue(StringUtils.leftPad(registros.getId().toString(),8,"0"));
                                                                         break;
                                                                case 1:  celda.setCellValue(registros.getRifComercio().trim());
                                                                         break;
                                                                case 2:  celda.setCellValue(registros.getDescripcionComercio().trim());
                                                                         break;                                             
                                                                case 3:  celda.setCellValue(registros.getCodigoBanco());
                                                                         break;
                                                                case 4:  celda.setCellValue(registros.getCuentaAbono());
                                                                         break;
                                                                case 5:  celda.setCellValue("Abono por concepto MILPAGO comercio: " + registros.getRifComercio().trim() + " " + registros.getLote().trim() + " " + registros.getTerminal().trim() + " " + registros.getFecha().toString() + "");
                                                                         break;
                                                                case 6:  celda.setCellValue(dfMonto.format(registros.getMontoTotal()));
                                                                         break;
                                                                case 7:  celda.setCellValue(registros.getTerminal());
                                                                         break;         
                                                            }                            
                                                        }
                                                        x++;
                                                        //Fin Reporte Excel Detalle
                                                }

                                            }else{
                                                totalRegistrosExcluidos++;
                                            }
                                        }                           

                                        if(totalRegistros>0){

                                            bw.close();

                                            BufferedReader read= new BufferedReader(new FileReader(fichero));
                                            ArrayList list = new ArrayList();

                                            String dataRow = read.readLine(); 
                                            while (dataRow != null){
                                                list.add(dataRow);
                                                dataRow = read.readLine(); 
                                            }

                                            FileWriter writer = new FileWriter(fichero);

                                            //Registro cabecera
                                            line3 = "C" + StringUtils.leftPad(String.valueOf(totalRegistros),5,"0") + StringUtils.leftPad(String.valueOf(montoTotal).replace(".", "").replace(",", ""),15,"0") + StringUtils.leftPad(nombreArchivo,10,"0") + "SSN00";

                                            writer.append(line3);

                                            for (int i = 0; i < list.size(); i++){
                                                writer.append("\r\n");
                                                writer.append(list.get(i).toString());
                                            }
                                            writer.flush();
                                            writer.close(); 

                                            //Guarda en la tabla LotesXbanco
                                            loteCabecera.setActividadEconomica(00);
                                            loteCabecera.setCantidadPagos(totalRegistros);
                                            loteCabecera.setCodigoCompania("BNC");
                                            loteCabecera.setCodigoAfiliado(codigosAfil.getCodigoAfiliado());
                                            loteCabecera.setCodigoBanco(lotePagos.getBanco());
                                            loteCabecera.setTipoArchivo(lotePagos.getTipoPago());
                                            loteCabecera.setCodigoMonedaCredito("VES");
                                            loteCabecera.setCodigoMonedaDebito("VES");
                                            loteCabecera.setCuentaDebito(codigosAfil.getNumeroCuentaAbono());
                                            loteCabecera.setFechaValor(Utilidades.convierteFechaSqlsinHora(dfsql.format(ahora)));
                                            loteCabecera.setMontoTotal(montoTotal);
                                            loteCabecera.setMotivoOperacion(000);
                                            loteCabecera.setNumeroLote(nombreArchivo);
                                            loteCabecera.setTipoRegistro(9);
                                            loteDAO.saveLote(loteCabecera);                                                                        

                                            //Escribir en el reporte Excel
                                            for(int j = 0; j < 7; j++) { hoja.autoSizeColumn((short)j); }
                                            /*Escribimos en el libro*/
                                            libro.write(archivo);
                                            /*Cerramos el flujo de datos*/
                                            archivo.close();
                                            //Fin escribir reporte Excel
                                        }else{
                                            bw.close();
                                            fichero.delete();                                    
                                        }                            

                                }else{
                                    modelo.addAttribute("messageError", "El archivo con el nombre " + nombreArchivo +" ya existe, por favor verifique e intente nuevamente");
                                    return new ModelAndView("lotePagos", "model", modelo);
                                }                                                                        
                            }else if("0156".equals(lotePagos.getBanco())){ //Estructura para 100%Banco

                                // A partir del objeto File creamos el fichero físicamente
                                if (fichero.createNewFile()){

                                    List<Banco> codigoSwift;                        
                                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fichero), "Cp1252"));

                                    //Creamos el reporte excel con la data del archivo generado (Cabecera)

                                    archivoXLS.createNewFile();
                                    int x = 0;
                                    Workbook libro = new HSSFWorkbook();
                                    FileOutputStream archivo = new FileOutputStream(archivoXLS);
                                    Sheet hoja = libro.createSheet("Archivo Pago a Comercios");                        
                                    CellStyle style = libro.createCellStyle();
                                    Font font = libro.createFont();                    
                                    font.setColor(HSSFColor.WHITE.index);
                                    style.setFont(font);                    
                                    style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
                                    style.setFillPattern(CellStyle.SOLID_FOREGROUND);
                                    style.setAlignment(HSSFCellStyle.ALIGN_JUSTIFY);
                                    style.setBorderLeft((short)8);

                                    Row fila = hoja.createRow(x);

                                    /*Cada fila tendrá 7 celdas de datos*/
                                    for(int c=0;c<8;c++){
                                        /*Creamos la celda a partir de la fila actual*/
                                        Cell celda = fila.createCell(c);

                                        switch (c) {
                                            case 0:  celda.setCellValue("Numero Pago a Proveedor");
                                                     celda.setCellStyle(style);   
                                                     break;
                                            case 1:  celda.setCellValue("RIF");
                                                     celda.setCellStyle(style);
                                                     break;
                                            case 2:  celda.setCellValue("Beneficiario");
                                                     celda.setCellStyle(style);
                                                     break;                                             
                                            case 3:  celda.setCellValue("Banco Beneficiario");
                                                     celda.setCellStyle(style);
                                                     break;
                                            case 4:  celda.setCellValue("Cuenta Beneficiario");
                                                     celda.setCellStyle(style);
                                                     break;
                                            case 5:  celda.setCellValue("Concepto");
                                                     celda.setCellStyle(style);
                                                     break;
                                            case 6:  celda.setCellValue("Monto");
                                                     celda.setCellStyle(style);
                                                     break;
                                            case 7:  celda.setCellValue("Terminal");
                                                     celda.setCellStyle(style);
                                                     break;         
                                        }
                                    }
                                    x++;
                                    //Fin Excel Cabecera

                                        for(Historico registros: resultado){

                                            //Validar si el comercio no se toma en cuenta para el pago
                                            if(!"1".equals(registros.getExcluirArchivoPago())){

                                                montoTotal = montoTotal.add(registros.getMontoTotal());
                                                montoTotalArchivos = montoTotalArchivos.add(registros.getMontoTotal());

                                                //Valido el monto tope por archivo
                                                if(montoTotal.compareTo(montoTope100Banco)==1){

                                                    montoTotal = montoTotal.subtract(registros.getMontoTotal());

                                                    //registro totales
                                                    line3 = "999999" + StringUtils.rightPad("INVERSIONES GROSS, C.A",40,"0") + StringUtils.leftPad(String.valueOf(totalRegistros),6,"0") + StringUtils.leftPad(String.valueOf(montoTotal).replace(".", "").replace(",", ""),15,"0") + StringUtils.leftPad(String.valueOf(montoTotal).replace(".", "").replace(",", ""),15,"0") + "000001" + StringUtils.leftPad(String.valueOf(totalRegistros),6,"0") + StringUtils.rightPad("",76,"0");                                                    

                                                    bw.write(line3.trim());

                                                    bw.close();

                                                    BufferedReader read= new BufferedReader(new FileReader(fichero));
                                                    ArrayList list = new ArrayList();

                                                    String dataRow = read.readLine(); 
                                                    while (dataRow != null){
                                                        list.add(dataRow);
                                                        dataRow = read.readLine(); 
                                                    }

                                                    FileWriter writer = new FileWriter(fichero);

                                                    //Registro cabecera
                                                    line3 = StringUtils.leftPad(String.valueOf(numeroLote),6,"0") + dfFechaValor.format(ahora) + dfHora.format(ahora) + dfFechaValor.format(ahora) + dfHora.format(ahora) + dfFechaValor.format(ahora) + dfHora.format(ahora) + StringUtils.leftPad("1",6,"0") + "000002" + "CC " + "00" + codigosAfil.getNumeroCuentaAbono() + StringUtils.leftPad("",85,"0");

                                                    writer.append(line3);

                                                    for (int i = 0; i < list.size(); i++){
                                                        writer.append("\r\n");
                                                        writer.append(list.get(i).toString());
                                                    }
                                                    writer.flush();
                                                    writer.close();                                                                                                                                                                                

                                                    Lotes loteCabeceraTemp = new Lotes();
                                                    //Guarda en la tabla LotesXbanco
                                                    loteCabeceraTemp.setActividadEconomica(00);
                                                    loteCabeceraTemp.setCantidadPagos(totalRegistros);
                                                    loteCabeceraTemp.setCodigoCompania("100%");
                                                    loteCabeceraTemp.setCodigoAfiliado(registros.getCodigoAfiliado());
                                                    loteCabeceraTemp.setCodigoBanco(lotePagos.getBanco());
                                                    loteCabeceraTemp.setTipoArchivo(lotePagos.getTipoPago());
                                                    loteCabeceraTemp.setCodigoMonedaCredito("VES");
                                                    loteCabeceraTemp.setCodigoMonedaDebito("VES");
                                                    loteCabeceraTemp.setCuentaDebito(codigosAfil.getNumeroCuentaAbono());
                                                    loteCabeceraTemp.setFechaValor(Utilidades.convierteFechaSqlsinHora(dfsql.format(ahora)));
                                                    loteCabeceraTemp.setMontoTotal(montoTotal);
                                                    loteCabeceraTemp.setMotivoOperacion(000);
                                                    loteCabeceraTemp.setNumeroLote(nombreArchivo);
                                                    loteCabeceraTemp.setTipoRegistro(9);
                                                    loteDAO.saveLote(loteCabeceraTemp);                                                                                                                                                       

                                                    totalRegistros = 0;
                                                    montoTotal = new BigDecimal(0);
                                                    montoTotal = montoTotal.setScale(2, RoundingMode.CEILING);
                                                    montoTotal = montoTotal.add(registros.getMontoTotal());
                                                    cantidadArchivos++;
                                                    numeroLote++;
                                                    nombreArchivo = df.format(ahora) + StringUtils.leftPad(String.valueOf(numeroLote),2,"0"); 
                                                    fichero = new File(ruta100Banco + "/100%/",""+ nombreArchivo +".txt");
                                                    fichero.getParentFile().mkdirs();

                                                    if (fichero.createNewFile()){                                                                        
                                                        bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fichero), "Cp1252"));

                                                        LoteDetalle loteDetalle5 = new LoteDetalle();                                                                                                                                                

                                                        if("0156".equals(registros.getCodigoBanco())){
                                                            tipoCuentaAbono = "CC ";
                                                        }else{
                                                            tipoCuentaAbono = "OB ";
                                                        }

                                                        //Registro tipo detalle
                                                        line1 = StringUtils.leftPad(String.valueOf(totalRegistros+1),6,"0") + tipoCuentaAbono + codigosAfil.getNumeroCuentaAbono() + registros.getRifComercio().substring(0, 1) + StringUtils.leftPad(registros.getRifComercio().substring(1),10,"0") + "00002" + "00000" + StringUtils.leftPad(registros.getTerminal(),10,"0") + StringUtils.leftPad(String.valueOf(registros.getMontoTotal().setScale(2, RoundingMode.CEILING)).replace(".", "").replace(",", ""),15,"0") + "C0" + StringUtils.rightPad("Abono MILPAGO: " + registros.getRifComercio().trim() + " " + registros.getLote().trim() + " " + registros.getTerminal().trim() + "",40," ") + StringUtils.leftPad("",53,"0");

                                                        if("0156".equals(registros.getCodigoBanco())){                                                    
                                                            tipoCuentaAbono = "1";
                                                        }else{
                                                            tipoCuentaAbono = "3";
                                                        }

                                                        //Guarda en la tabla LoteDetalle                  
                                                        loteDetalle5.setCodigoCompania("100%");
                                                        loteDetalle5.setNumeroLote(nombreArchivo);
                                                        loteDetalle5.setNumeroPagoProveedor(registros.getId().toString());
                                                        loteDetalle5.setTipoRegistro(5);
                                                        loteDetalle5.setMontoTotal(registros.getMontoTotal());
                                                        loteDetalle5.setNombreBeneficiario(registros.getDescripcionComercio().trim());
                                                        loteDetalle5.setTipoPago(Integer.parseInt(tipoCuentaAbono));
                                                        loteDetalle5.setCuentaBeneficiario(registros.getCuentaAbono());
                                                        loteDetalle5.setCodigoMonedaCredito("VES");
                                                        loteDetalle5.setCodigoMonedaDebito("VES");
                                                        loteDetalle5.setConceptoPago("Abono MILPAGO: " + registros.getRifComercio().trim() + " " + registros.getLote().trim() + " " + registros.getTerminal().trim() + "");
                                                        loteDetalle5.setCodigoBancoBeneficiario("");
                                                        loteDetalle5.setTipoCodigoBanco("BIC");                        
                                                        loteDetalle5.setMotivoOperacion(000);
                                                        loteDetalle5.setActividadEconomica(00);
                                                        loteDetalle5.setCuentaDebito(codigosAfil.getNumeroCuentaAbono());                        
                                                        loteDetalleDAO.saveLoteDetalle(loteDetalle5);

                                                        bw.write(line1.trim());
                                                        bw.write("\r\n");

                                                        totalRegistros++;

                                                        //Reporte Excel detalle
                                                        fila = hoja.createRow(x);

                                                        /*Cada fila tendrá 7 celdas de datos*/
                                                        for(int c=0;c<8;c++){
                                                            /*Creamos la celda a partir de la fila actual*/
                                                            Cell celda = fila.createCell(c);

                                                            switch (c) {
                                                                case 0:  celda.setCellValue(StringUtils.leftPad(registros.getId().toString(),8,"0"));
                                                                         break;
                                                                case 1:  celda.setCellValue(registros.getRifComercio().trim());
                                                                         break;
                                                                case 2:  celda.setCellValue(registros.getDescripcionComercio().trim());
                                                                         break;                                             
                                                                case 3:  celda.setCellValue(registros.getCodigoBanco());
                                                                         break;
                                                                case 4:  celda.setCellValue(registros.getCuentaAbono());
                                                                         break;
                                                                case 5:  celda.setCellValue("Abono por concepto MILPAGO comercio: " + registros.getRifComercio().trim() + " " + registros.getLote().trim() + " " + registros.getTerminal().trim() + " " + registros.getFecha().toString() + "");
                                                                         break;
                                                                case 6:  celda.setCellValue(dfMonto.format(registros.getMontoTotal()));
                                                                         break;
                                                                case 7:  celda.setCellValue(registros.getTerminal());
                                                                         break;         
                                                            }                            
                                                        }
                                                        x++;
                                                        //Fin Reporte Excel Detalle
                                                    }

                                                }else{

                                                    LoteDetalle loteDetalle5 = new LoteDetalle();

                                                    if(registros.getEmail()==null){
                                                        email = "";
                                                    }else{
                                                        email = registros.getEmail();
                                                    }

                                                    if("0156".equals(registros.getCodigoBanco())){
                                                        tipoCuentaAbono = "CC ";
                                                    }else{
                                                        tipoCuentaAbono = "OB ";
                                                    }

                                                    //Registro tipo detalle
                                                    line1 = StringUtils.leftPad(String.valueOf(totalRegistros+1),6,"0") + tipoCuentaAbono + codigosAfil.getNumeroCuentaAbono() + registros.getRifComercio().substring(0, 1) + StringUtils.leftPad(registros.getRifComercio().substring(1),10,"0") + "00002" + "00000" + StringUtils.leftPad(registros.getTerminal(),10,"0") + StringUtils.leftPad(String.valueOf(registros.getMontoTotal().setScale(2, RoundingMode.CEILING)).replace(".", "").replace(",", ""),15,"0") + "C0" + StringUtils.rightPad("Abono MILPAGO: " + registros.getRifComercio().trim() + " " + registros.getLote().trim() + " " + registros.getTerminal().trim() + "",40," ") + StringUtils.leftPad("",53,"0");

                                                    if("0156".equals(registros.getCodigoBanco())){                                                    
                                                        tipoCuentaAbono = "1";
                                                    }else{
                                                        tipoCuentaAbono = "3";
                                                    }

                                                    //Guarda en la tabla LoteDetalle                  
                                                    loteDetalle5.setCodigoCompania("100%");
                                                    loteDetalle5.setNumeroLote(nombreArchivo);
                                                    loteDetalle5.setNumeroPagoProveedor(registros.getId().toString());
                                                    loteDetalle5.setTipoRegistro(5);
                                                    loteDetalle5.setMontoTotal(registros.getMontoTotal());
                                                    loteDetalle5.setNombreBeneficiario(registros.getDescripcionComercio().trim());
                                                    loteDetalle5.setTipoPago(Integer.parseInt(tipoCuentaAbono));
                                                    loteDetalle5.setCuentaBeneficiario(registros.getCuentaAbono());
                                                    loteDetalle5.setCodigoMonedaCredito("VES");
                                                    loteDetalle5.setCodigoMonedaDebito("VES");
                                                    loteDetalle5.setConceptoPago("Abono MILPAGO: " + registros.getRifComercio().trim() + " " + registros.getLote().trim() + " " + registros.getTerminal().trim() + "");
                                                    loteDetalle5.setCodigoBancoBeneficiario("");
                                                    loteDetalle5.setTipoCodigoBanco("BIC");                        
                                                    loteDetalle5.setMotivoOperacion(000);
                                                    loteDetalle5.setActividadEconomica(00);
                                                    loteDetalle5.setCuentaDebito(codigosAfil.getNumeroCuentaAbono());                        
                                                    loteDetalleDAO.saveLoteDetalle(loteDetalle5);

                                                    bw.write(line1.trim());
                                                    bw.write("\r\n"); 

                                                    totalRegistros++;

                                                    //Reporte Excel detalle
                                                        fila = hoja.createRow(x);

                                                        /*Cada fila tendrá 7 celdas de datos*/
                                                        for(int c=0;c<8;c++){
                                                            /*Creamos la celda a partir de la fila actual*/
                                                            Cell celda = fila.createCell(c);

                                                            switch (c) {
                                                                case 0:  celda.setCellValue(StringUtils.leftPad(registros.getId().toString(),8,"0"));
                                                                         break;
                                                                case 1:  celda.setCellValue(registros.getRifComercio().trim());
                                                                         break;
                                                                case 2:  celda.setCellValue(registros.getDescripcionComercio().trim());
                                                                         break;                                             
                                                                case 3:  celda.setCellValue(registros.getCodigoBanco());
                                                                         break;
                                                                case 4:  celda.setCellValue(registros.getCuentaAbono());
                                                                         break;
                                                                case 5:  celda.setCellValue("Abono por concepto MILPAGO comercio: " + registros.getRifComercio().trim() + " " + registros.getLote().trim() + " " + registros.getTerminal().trim() + " " + registros.getFecha().toString() + "");
                                                                         break;
                                                                case 6:  celda.setCellValue(dfMonto.format(registros.getMontoTotal()));
                                                                         break;
                                                                case 7:  celda.setCellValue(registros.getTerminal());
                                                                         break;         
                                                            }                            
                                                        }
                                                        x++;
                                                        //Fin Reporte Excel Detalle
                                                }

                                            }else{
                                                totalRegistrosExcluidos++;
                                            }
                                        }                           

                                        if(totalRegistros>0){

                                            //registro totales
                                            line3 = "999999" + StringUtils.rightPad("INVERSIONES GROSS, C.A",40,"0") + StringUtils.leftPad(String.valueOf(totalRegistros),6,"0") + StringUtils.leftPad(String.valueOf(montoTotal).replace(".", "").replace(",", ""),15,"0") + StringUtils.leftPad(String.valueOf(montoTotal).replace(".", "").replace(",", ""),15,"0") + "000001" + StringUtils.leftPad(String.valueOf(totalRegistros),6,"0") + StringUtils.rightPad("",76,"0");                                                    

                                            bw.write(line3.trim());

                                            bw.close();

                                            BufferedReader read= new BufferedReader(new FileReader(fichero));
                                            ArrayList list = new ArrayList();

                                            String dataRow = read.readLine(); 
                                            while (dataRow != null){
                                                list.add(dataRow);
                                                dataRow = read.readLine(); 
                                            }

                                            FileWriter writer = new FileWriter(fichero);

                                            //Registro cabecera
                                            line3 = StringUtils.leftPad(String.valueOf(numeroLote),6,"0") + dfFechaValor.format(ahora) + dfHora.format(ahora) + dfFechaValor.format(ahora) + dfHora.format(ahora) + dfFechaValor.format(ahora) + dfHora.format(ahora) + StringUtils.leftPad("1",6,"0") + "000002" + "CC " + "00" + codigosAfil.getNumeroCuentaAbono() + StringUtils.leftPad("",85,"0");

                                            writer.append(line3);

                                            for (int i = 0; i < list.size(); i++){
                                                writer.append("\r\n");
                                                writer.append(list.get(i).toString());
                                            }
                                            writer.flush();
                                            writer.close(); 

                                            //Guarda en la tabla LotesXbanco
                                            loteCabecera.setActividadEconomica(00);
                                            loteCabecera.setCantidadPagos(totalRegistros);
                                            loteCabecera.setCodigoCompania("100%");
                                            loteCabecera.setCodigoAfiliado(codigosAfil.getCodigoAfiliado());
                                            loteCabecera.setCodigoBanco(lotePagos.getBanco());
                                            loteCabecera.setTipoArchivo(lotePagos.getTipoPago());
                                            loteCabecera.setCodigoMonedaCredito("VES");
                                            loteCabecera.setCodigoMonedaDebito("VES");
                                            loteCabecera.setCuentaDebito(codigosAfil.getNumeroCuentaAbono());
                                            loteCabecera.setFechaValor(Utilidades.convierteFechaSqlsinHora(dfsql.format(ahora)));
                                            loteCabecera.setMontoTotal(montoTotal);
                                            loteCabecera.setMotivoOperacion(000);
                                            loteCabecera.setNumeroLote(nombreArchivo);
                                            loteCabecera.setTipoRegistro(9);
                                            loteDAO.saveLote(loteCabecera);                                                                        

                                            //Escribir en el reporte Excel
                                            for(int j = 0; j < 7; j++) { hoja.autoSizeColumn((short)j); }
                                            /*Escribimos en el libro*/
                                            libro.write(archivo);
                                            /*Cerramos el flujo de datos*/
                                            archivo.close();
                                            //Fin escribir reporte Excel
                                        }else{
                                            bw.close();
                                            fichero.delete();                                    
                                        }                            

                                }else{
                                    modelo.addAttribute("messageError", "El archivo con el nombre " + nombreArchivo +" ya existe, por favor verifique e intente nuevamente");
                                    return new ModelAndView("lotePagos", "model", modelo);
                                }                                                                        
                            }else if("0138".equals(lotePagos.getBanco())){ //Estructura para Banco Plaza

                                // A partir del objeto File creamos el fichero físicamente
                                if (fichero.createNewFile()){

                                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fichero), "Cp1252"));

                                    //Creamos el reporte excel con la data del archivo generado (Cabecera)

                                    archivoXLS.createNewFile();
                                    int x = 0;
                                    Workbook libro = new HSSFWorkbook();
                                    FileOutputStream archivo = new FileOutputStream(archivoXLS);
                                    Sheet hoja = libro.createSheet("Archivo Pago a Comercios");                        
                                    CellStyle style = libro.createCellStyle();
                                    Font font = libro.createFont();                    
                                    font.setColor(HSSFColor.WHITE.index);
                                    style.setFont(font);                    
                                    style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
                                    style.setFillPattern(CellStyle.SOLID_FOREGROUND);
                                    style.setAlignment(HSSFCellStyle.ALIGN_JUSTIFY);
                                    style.setBorderLeft((short)8);

                                    Row fila = hoja.createRow(x);

                                    /*Cada fila tendrá 7 celdas de datos*/
                                    for(int c=0;c<8;c++){
                                        /*Creamos la celda a partir de la fila actual*/
                                        Cell celda = fila.createCell(c);

                                        switch (c) {
                                            case 0:  celda.setCellValue("Numero Pago a Proveedor");
                                                     celda.setCellStyle(style);   
                                                     break;
                                            case 1:  celda.setCellValue("RIF");
                                                     celda.setCellStyle(style);
                                                     break;
                                            case 2:  celda.setCellValue("Beneficiario");
                                                     celda.setCellStyle(style);
                                                     break;                                             
                                            case 3:  celda.setCellValue("Banco Beneficiario");
                                                     celda.setCellStyle(style);
                                                     break;
                                            case 4:  celda.setCellValue("Cuenta Beneficiario");
                                                     celda.setCellStyle(style);
                                                     break;
                                            case 5:  celda.setCellValue("Concepto");
                                                     celda.setCellStyle(style);
                                                     break;
                                            case 6:  celda.setCellValue("Monto");
                                                     celda.setCellStyle(style);
                                                     break;
                                            case 7:  celda.setCellValue("Terminal");
                                                     celda.setCellStyle(style);
                                                     break;         
                                        }
                                    }
                                    x++;
                                    //Fin Excel Cabecera

                                        for(Historico registros: resultado){

                                            //Validar si el comercio no se toma en cuenta para el pago
                                            if(!"1".equals(registros.getExcluirArchivoPago())){

                                                montoTotal = montoTotal.add(registros.getMontoTotal());
                                                montoTotalArchivos = montoTotalArchivos.add(registros.getMontoTotal());

                                                //Valido el monto tope por archivo
                                                if(montoTotal.compareTo(montoTopeBPL)==1){

                                                    montoTotal = montoTotal.subtract(registros.getMontoTotal());

                                                    bw.close();

                                                    BufferedReader read= new BufferedReader(new FileReader(fichero));
                                                    ArrayList list = new ArrayList();

                                                    String dataRow = read.readLine(); 
                                                    while (dataRow != null){
                                                        list.add(dataRow);
                                                        dataRow = read.readLine(); 
                                                    }

                                                    FileWriter writer = new FileWriter(fichero);                                                                                      

                                                    for (int i = 0; i < list.size(); i++){
                                                        writer.append("\r\n");
                                                        writer.append(list.get(i).toString());
                                                    }
                                                    writer.flush();
                                                    writer.close();                                                                                                                                                                                

                                                    Lotes loteCabeceraTemp = new Lotes();
                                                    //Guarda en la tabla LotesXbanco
                                                    loteCabeceraTemp.setActividadEconomica(00);
                                                    loteCabeceraTemp.setCantidadPagos(totalRegistros);
                                                    loteCabeceraTemp.setCodigoCompania("BPL");
                                                    loteCabeceraTemp.setCodigoAfiliado(registros.getCodigoAfiliado());
                                                    loteCabeceraTemp.setCodigoBanco(lotePagos.getBanco());
                                                    loteCabeceraTemp.setTipoArchivo(lotePagos.getTipoPago());
                                                    loteCabeceraTemp.setCodigoMonedaCredito("VES");
                                                    loteCabeceraTemp.setCodigoMonedaDebito("VES");
                                                    loteCabeceraTemp.setCuentaDebito(codigosAfil.getNumeroCuentaAbono());
                                                    loteCabeceraTemp.setFechaValor(Utilidades.convierteFechaSqlsinHora(dfsql.format(ahora)));
                                                    loteCabeceraTemp.setMontoTotal(montoTotal);
                                                    loteCabeceraTemp.setMotivoOperacion(000);
                                                    loteCabeceraTemp.setNumeroLote(nombreArchivo);
                                                    loteCabeceraTemp.setTipoRegistro(9);
                                                    loteDAO.saveLote(loteCabeceraTemp);                                                                                                                                                       

                                                    totalRegistros = 0;
                                                    montoTotal = new BigDecimal(0);
                                                    montoTotal = montoTotal.setScale(2, RoundingMode.CEILING);
                                                    montoTotal = montoTotal.add(registros.getMontoTotal());
                                                    cantidadArchivos++;
                                                    numeroLote++;
                                                    nombreArchivo = df.format(ahora) + StringUtils.leftPad(String.valueOf(numeroLote),2,"0"); 
                                                    fichero = new File(rutaBPL + "/PLAZA/",""+ nombreArchivo + codigosAfil.getNumeroCuentaAbono().substring(16) + ".txt");
                                                    fichero.getParentFile().mkdirs();

                                                    if (fichero.createNewFile()){                                                                        
                                                        bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fichero), "Cp1252"));

                                                        LoteDetalle loteDetalle5 = new LoteDetalle();                                                                                                                                                

                                                        if("0146".equals(registros.getCodigoBanco())){
                                                            tipoCuentaAbono = "1";
                                                        }else{
                                                            tipoCuentaAbono = "3";
                                                        }

                                                        int maxLength = (registros.getDescripcionComercio().length() < 50)?registros.getDescripcionComercio().length():50;
                                                        nombreComercio = registros.getDescripcionComercio().substring(0, maxLength);

                                                        if(registros.getEmail()==null){
                                                            email = "";
                                                        }else{
                                                            email = registros.getEmail();
                                                        }

                                                        //Registro tipo detalle
                                                        line1 = "J00003103756" + StringUtils.leftPad(nombreComercio,50," ") + registros.getRifComercio().substring(0, 1) + StringUtils.leftPad(registros.getRifComercio().substring(1),11,"0") + StringUtils.leftPad(email,50," ") + "CC" + registros.getCuentaAbono() + StringUtils.leftPad(String.valueOf(registros.getMontoTotal().setScale(2, RoundingMode.CEILING)).replace(".", "").replace(",", ""),17,"0") + StringUtils.leftPad("Abono MILPAGO comercio: " + registros.getRifComercio().trim() + " " + registros.getLote().trim() + " " + registros.getTerminal().trim() + " " + registros.getFecha().toString(),140," ")+ StringUtils.leftPad(registros.getTelefonoCelular(),11,"0");

                                                        //Guarda en la tabla LoteDetalle                  
                                                        loteDetalle5.setCodigoCompania("BPL");
                                                        loteDetalle5.setNumeroLote(nombreArchivo);
                                                        loteDetalle5.setNumeroPagoProveedor(registros.getId().toString());
                                                        loteDetalle5.setTipoRegistro(5);
                                                        loteDetalle5.setMontoTotal(registros.getMontoTotal());
                                                        loteDetalle5.setNombreBeneficiario(registros.getDescripcionComercio().trim());
                                                        loteDetalle5.setTipoPago(Integer.parseInt(tipoCuentaAbono));
                                                        loteDetalle5.setCuentaBeneficiario(registros.getCuentaAbono());
                                                        loteDetalle5.setCodigoMonedaCredito("VES");
                                                        loteDetalle5.setCodigoMonedaDebito("VES");
                                                        loteDetalle5.setConceptoPago("Abono MILPAGO comercio: " + registros.getRifComercio().trim() + " " + registros.getLote().trim() + " " + registros.getTerminal().trim() + " " + registros.getFecha().toString() + "");
                                                        loteDetalle5.setCodigoBancoBeneficiario("");
                                                        loteDetalle5.setTipoCodigoBanco("BIC");                        
                                                        loteDetalle5.setMotivoOperacion(000);
                                                        loteDetalle5.setActividadEconomica(00);
                                                        loteDetalle5.setCuentaDebito(codigosAfil.getNumeroCuentaAbono());                        
                                                        loteDetalleDAO.saveLoteDetalle(loteDetalle5);

                                                        bw.write(line1.trim());
                                                        bw.write("\r\n");

                                                        totalRegistros++;

                                                        //Reporte Excel detalle
                                                        fila = hoja.createRow(x);

                                                        /*Cada fila tendrá 7 celdas de datos*/
                                                        for(int c=0;c<8;c++){
                                                            /*Creamos la celda a partir de la fila actual*/
                                                            Cell celda = fila.createCell(c);

                                                            switch (c) {
                                                                case 0:  celda.setCellValue(StringUtils.leftPad(registros.getId().toString(),8,"0"));
                                                                         break;
                                                                case 1:  celda.setCellValue(registros.getRifComercio().trim());
                                                                         break;
                                                                case 2:  celda.setCellValue(registros.getDescripcionComercio().trim());
                                                                         break;                                             
                                                                case 3:  celda.setCellValue(registros.getCodigoBanco());
                                                                         break;
                                                                case 4:  celda.setCellValue(registros.getCuentaAbono());
                                                                         break;
                                                                case 5:  celda.setCellValue("Abono por concepto MILPAGO comercio: " + registros.getRifComercio().trim() + " " + registros.getLote().trim() + " " + registros.getTerminal().trim() + " " + registros.getFecha().toString() + "");
                                                                         break;
                                                                case 6:  celda.setCellValue(dfMonto.format(registros.getMontoTotal()));
                                                                         break;
                                                                case 7:  celda.setCellValue(registros.getTerminal());
                                                                         break;         
                                                            }                            
                                                        }
                                                        x++;
                                                        //Fin Reporte Excel Detalle
                                                    }

                                                }else{

                                                    LoteDetalle loteDetalle5 = new LoteDetalle();

                                                    if("0146".equals(registros.getCodigoBanco())){
                                                        tipoCuentaAbono = "1";
                                                    }else{
                                                        tipoCuentaAbono = "3";
                                                    }

                                                    int maxLength = (registros.getDescripcionComercio().length() < 50)?registros.getDescripcionComercio().length():50;
                                                    nombreComercio = registros.getDescripcionComercio().substring(0, maxLength);

                                                    if(registros.getEmail()==null){
                                                        email = "";
                                                    }else{
                                                        email = registros.getEmail();
                                                    }

                                                    //Registro tipo detalle
                                                    line1 = "J00003103756" + StringUtils.leftPad(nombreComercio,50," ") + registros.getRifComercio().substring(0, 1) + StringUtils.leftPad(registros.getRifComercio().substring(1),11,"0") + StringUtils.leftPad(email,50," ") + "CC" + registros.getCuentaAbono() + StringUtils.leftPad(String.valueOf(registros.getMontoTotal().setScale(2, RoundingMode.CEILING)).replace(".", "").replace(",", ""),17,"0") + StringUtils.leftPad("Abono MILPAGO comercio: " + registros.getRifComercio().trim() + " " + registros.getLote().trim() + " " + registros.getTerminal().trim() + " " + registros.getFecha().toString(),140," ")+ StringUtils.leftPad(registros.getTelefonoCelular(),11,"0");                                            
                                                    //Guarda en la tabla LoteDetalle                  
                                                    loteDetalle5.setCodigoCompania("BPL");
                                                    loteDetalle5.setNumeroLote(nombreArchivo);
                                                    loteDetalle5.setNumeroPagoProveedor(registros.getId().toString());
                                                    loteDetalle5.setTipoRegistro(5);
                                                    loteDetalle5.setMontoTotal(registros.getMontoTotal());
                                                    loteDetalle5.setNombreBeneficiario(registros.getDescripcionComercio().trim());
                                                    loteDetalle5.setTipoPago(Integer.parseInt(tipoCuentaAbono));
                                                    loteDetalle5.setCuentaBeneficiario(registros.getCuentaAbono());
                                                    loteDetalle5.setCodigoMonedaCredito("VES");
                                                    loteDetalle5.setCodigoMonedaDebito("VES");
                                                    loteDetalle5.setConceptoPago("Abono MILPAGO comercio: " + registros.getRifComercio().trim() + " " + registros.getLote().trim() + " " + registros.getTerminal().trim() + " " + registros.getFecha().toString() + "");
                                                    loteDetalle5.setCodigoBancoBeneficiario("");
                                                    loteDetalle5.setTipoCodigoBanco("BIC");                        
                                                    loteDetalle5.setMotivoOperacion(000);
                                                    loteDetalle5.setActividadEconomica(00);
                                                    loteDetalle5.setCuentaDebito(codigosAfil.getNumeroCuentaAbono());                        
                                                    loteDetalleDAO.saveLoteDetalle(loteDetalle5);

                                                    bw.write(line1.trim());
                                                    bw.write("\r\n"); 

                                                    totalRegistros++;

                                                    //Reporte Excel detalle
                                                        fila = hoja.createRow(x);

                                                        /*Cada fila tendrá 7 celdas de datos*/
                                                        for(int c=0;c<8;c++){
                                                            /*Creamos la celda a partir de la fila actual*/
                                                            Cell celda = fila.createCell(c);

                                                            switch (c) {
                                                                case 0:  celda.setCellValue(StringUtils.leftPad(registros.getId().toString(),8,"0"));
                                                                         break;
                                                                case 1:  celda.setCellValue(registros.getRifComercio().trim());
                                                                         break;
                                                                case 2:  celda.setCellValue(registros.getDescripcionComercio().trim());
                                                                         break;                                             
                                                                case 3:  celda.setCellValue(registros.getCodigoBanco());
                                                                         break;
                                                                case 4:  celda.setCellValue(registros.getCuentaAbono());
                                                                         break;
                                                                case 5:  celda.setCellValue("Abono por concepto MILPAGO comercio: " + registros.getRifComercio().trim() + " " + registros.getLote().trim() + " " + registros.getTerminal().trim() + " " + registros.getFecha().toString() + "");
                                                                         break;
                                                                case 6:  celda.setCellValue(dfMonto.format(registros.getMontoTotal()));
                                                                         break;
                                                                case 7:  celda.setCellValue(registros.getTerminal());
                                                                         break;         
                                                            }                            
                                                        }
                                                        x++;
                                                        //Fin Reporte Excel Detalle
                                                }

                                            }else{
                                                totalRegistrosExcluidos++;
                                            }
                                        }                           

                                        if(totalRegistros>0){

                                            bw.close();

                                            BufferedReader read= new BufferedReader(new FileReader(fichero));
                                            ArrayList list = new ArrayList();

                                            String dataRow = read.readLine(); 
                                            while (dataRow != null){
                                                list.add(dataRow);
                                                dataRow = read.readLine(); 
                                            }

                                            FileWriter writer = new FileWriter(fichero);                                   

                                            for (int i = 0; i < list.size(); i++){
                                                writer.append("\r\n");
                                                writer.append(list.get(i).toString());
                                            }
                                            writer.flush();
                                            writer.close(); 

                                            //Guarda en la tabla LotesXbanco
                                            loteCabecera.setActividadEconomica(00);
                                            loteCabecera.setCantidadPagos(totalRegistros);
                                            loteCabecera.setCodigoCompania("BPL");
                                            loteCabecera.setCodigoAfiliado(codigosAfil.getCodigoAfiliado());
                                            loteCabecera.setCodigoBanco(lotePagos.getBanco());
                                            loteCabecera.setTipoArchivo(lotePagos.getTipoPago());
                                            loteCabecera.setCodigoMonedaCredito("VES");
                                            loteCabecera.setCodigoMonedaDebito("VES");
                                            loteCabecera.setCuentaDebito(codigosAfil.getNumeroCuentaAbono());
                                            loteCabecera.setFechaValor(Utilidades.convierteFechaSqlsinHora(dfsql.format(ahora)));
                                            loteCabecera.setMontoTotal(montoTotal);
                                            loteCabecera.setMotivoOperacion(000);
                                            loteCabecera.setNumeroLote(nombreArchivo);
                                            loteCabecera.setTipoRegistro(9);
                                            loteDAO.saveLote(loteCabecera);                                                                        

                                            //Escribir en el reporte Excel
                                            for(int j = 0; j < 7; j++) { hoja.autoSizeColumn((short)j); }
                                            /*Escribimos en el libro*/
                                            libro.write(archivo);
                                            /*Cerramos el flujo de datos*/
                                            archivo.close();
                                            //Fin escribir reporte Excel
                                        }else{
                                            bw.close();
                                            fichero.delete();                                    
                                        }                            

                                }else{
                                    modelo.addAttribute("messageError", "El archivo con el nombre " + nombreArchivo +" ya existe, por favor verifique e intente nuevamente");
                                    return new ModelAndView("lotePagos", "model", modelo);
                                }                                                                        
                            }
                        }                                

                    } catch (IOException ioe) {

                      bitacora.saveLogs(usuario.get(0).getId(), 2, 20, "No se ha podido realizar la operacion debido al siguiente error: "+ ioe.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");  
                      logger.error(ioe.getMessage());
                      modelo.addAttribute("messageError", "No se pudo generar el archivo");
                      return new ModelAndView("lotePagos", "model", modelo);
                    } catch (Exception cve) {

                      bitacora.saveLogs(usuario.get(0).getId(), 2, 20, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");  
                      logger.error(cve.getMessage());  
                      modelo.addAttribute("messageError", "No se pudo generar el archivo debido al siguiente error: " + cve.getMessage());
                      return new ModelAndView("lotePagos", "model", modelo);  
                    }                            
                //Archivo de pago Aliados Comerciales    
                }else{

                    List<HistoricoAliado> resultado;

                    try {
                        
                        resultado =  historicoDAO.getHistoricoPagoAliado(fechaDesdesql, codigosAfil.getCodigoAfiliado());                                                                                                                

                        if(!resultado.isEmpty()){

                            //Estructura para Venezolano de Credito
                            if("0104".equals(lotePagos.getBanco())){

                                // A partir del objeto File creamos el fichero físicamente
                                if (fichero.createNewFile()){

                                    List<Banco> codigoSwift;                        
                                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fichero), "Cp1252"));

                                        for(HistoricoAliado registros: resultado){

                                            //Validar el estatus y la modalidad de pago del aliado
                                            if(registros.getEstatusAliado()==2 && registros.getCodModalidadPagoAliado() != null && registros.getCuentaAbonoAliado()!= null){

                                                BigDecimal montoXAliado = registros.getMontoTotal();

                                                if(registros.getEmailAliado()==null){
                                                    email = "";
                                                }else{
                                                    email = registros.getEmailAliado();
                                                }

                                                //Modalidad de pago porcentaje 1
                                                if("1".equals(registros.getCodModalidadPagoAliado())){

                                                    if(montoXAliado.doubleValue() <= montoInicialAliado.doubleValue()){
                                                        montoInicial = montoXAliado.multiply(porcentajeInicial);
                                                    }else if(montoXAliado.doubleValue() > montoInicialAliado.doubleValue() && montoXAliado.doubleValue() <= montoMedioAliado.doubleValue()){
                                                        montoInicial = montoInicialAliado.multiply(porcentajeInicial);
                                                        montoMedio = (montoXAliado.subtract(montoInicialAliado)).multiply(porcentajeMedio);
                                                    }else if(montoXAliado.doubleValue() > montoMedioAliado.doubleValue()){
                                                        montoInicial = montoInicialAliado.multiply(porcentajeInicial);
                                                        montoMedio = montoInicialAliado.multiply(porcentajeMedio);
                                                        montoAlto = (montoXAliado.subtract(montoMedioAliado)).multiply(porcentajeAlto);
                                                    }                                    
                                                }

                                                montoAbono = montoAbono.add(montoInicial);
                                                montoAbono = montoAbono.add(montoMedio);
                                                montoAbono = montoAbono.add(montoAlto);

                                                montoTotal = montoTotal.add(montoAbono);
                                                montoTotalArchivos = montoTotalArchivos.add(montoAbono);

                                                //Valido el monto tope por archivo
                                                if(montoTotal.compareTo(montoTopeBVC)==1){

                                                    montoTotal = montoTotal.subtract(montoAbono);

                                                    //registro tipo 9
                                                    line3 = "D0U" + StringUtils.leftPad(nombreArchivo,8,"0") + StringUtils.leftPad("",16,"0") + "9" + dfFechaValor.format(ahora) + StringUtils.leftPad(String.valueOf(montoTotal).replace(".", "").replace(",", ""),15,"0") + StringUtils.leftPad(String.valueOf(totalRegistros),15,"0") + "VES" + StringUtils.rightPad(codigosAfil.getNumeroCuentaAbono(),35," ") + StringUtils.rightPad("",35," ") + StringUtils.leftPad(String.valueOf(montoTotal).replace(".", "").replace(",", ""),15,"0") + "VES" + StringUtils.rightPad("",30," ") +"00000" + StringUtils.rightPad("",307," ") + "X";

                                                    Lotes loteCabeceraTemp = new Lotes();
                                                    //Guarda en la tabla LotesXbanco
                                                    loteCabeceraTemp.setActividadEconomica(00);
                                                    loteCabeceraTemp.setCantidadPagos(totalRegistros);
                                                    loteCabeceraTemp.setCodigoCompania("D0U");
                                                    loteCabeceraTemp.setCodigoAfiliado(codigosAfil.getCodigoAfiliado());
                                                    loteCabeceraTemp.setCodigoBanco(lotePagos.getBanco());
                                                    loteCabeceraTemp.setTipoArchivo(lotePagos.getTipoPago());
                                                    loteCabeceraTemp.setCodigoMonedaCredito("VES");
                                                    loteCabeceraTemp.setCodigoMonedaDebito("VES");
                                                    loteCabeceraTemp.setCuentaDebito(codigosAfil.getNumeroCuentaAbono());
                                                    loteCabeceraTemp.setFechaValor(Utilidades.convierteFechaSqlsinHora(dfsql.format(ahora)));
                                                    loteCabeceraTemp.setMontoTotal(montoTotal);
                                                    loteCabeceraTemp.setMotivoOperacion(000);
                                                    loteCabeceraTemp.setNumeroLote(nombreArchivo);
                                                    loteCabeceraTemp.setTipoRegistro(9);
                                                    loteDAO.saveLote(loteCabeceraTemp);
                                                    bw.write(line3.trim());
                                                    bw.close();                                                                                                           

                                                    totalRegistros = 0;
                                                    montoTotal = new BigDecimal(0);
                                                    montoTotal = montoTotal.setScale(2, RoundingMode.CEILING);
                                                    montoTotal = montoTotal.add(montoAbono);
                                                    cantidadArchivos++;
                                                    numeroLote++;
                                                    nombreArchivo = df.format(ahora) + StringUtils.leftPad(String.valueOf(numeroLote),2,"0"); 
                                                    fichero = new File(rutaBNC + "/BNC/",""+ nombreArchivo +".txt");
                                                    fichero.getParentFile().mkdirs();

                                                    if (fichero.createNewFile()){                                                                        
                                                        bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fichero), "Cp1252"));

                                                        LoteDetalle loteDetalle4 = new LoteDetalle();
                                                        LoteDetalle loteDetalle5 = new LoteDetalle();                                                

                                                        //Registro tipo 4
                                                        line1 = "D0U" + nombreArchivo + StringUtils.leftPad(registros.getId().toString(),8,"0") + StringUtils.leftPad("",8,"0") + "4" + StringUtils.rightPad(email,129," ") + registros.getTipoIdentificacionAliado() + StringUtils.leftPad(registros.getIdentificacionAliado().substring(1),16,"0") + StringUtils.rightPad("",325," ") + "X";                                

                                                        //Guarda en la tabla LoteDetalle                     
                                                        loteDetalle4.setCodigoCompania("D0U");                        
                                                        loteDetalle4.setNumeroLote(nombreArchivo);
                                                        loteDetalle4.setNumeroPagoProveedor(registros.getId().toString());                        
                                                        loteDetalle4.setTipoRegistro(4);
                                                        loteDetalle4.setEmailBeneficiario(registros.getEmailAliado());
                                                        loteDetalle4.setRifBeneficiario(registros.getTipoIdentificacionAliado() + registros.getIdentificacionAliado());
                                                        loteDetalle4.setMontoTotal(montoAbono);
                                                        loteDetalleDAO.saveLoteDetalle(loteDetalle4);

                                                        bw.write(line1.trim());
                                                        bw.write("\r\n");

                                                        codigoSwift = bancoDAO.getCodigoSwiftList(registros.getCuentaAbonoAliado().substring(0, 4));
                                                        if("0104".equals(registros.getCuentaAbonoAliado().substring(0, 4))){
                                                            tipoCuentaAbono = "1";
                                                        }else{
                                                            tipoCuentaAbono = "3";
                                                        }

                                                        int maxLength = (registros.getContactoAliado().length() < 35)?registros.getContactoAliado().length():35;
                                                        nombreComercio = registros.getContactoAliado().substring(0, maxLength);

                                                        //Registro tipo 5
                                                        line2 = "D0U" + nombreArchivo + StringUtils.leftPad(registros.getId().toString(),8,"0") + StringUtils.leftPad("",8,"0") + "5" + StringUtils.leftPad(String.valueOf(montoAbono.setScale(2, RoundingMode.CEILING)).replace(".", "").replace(",", ""),15,"0") + StringUtils.rightPad(nombreComercio,60," ") + tipoCuentaAbono + StringUtils.rightPad("",17," ") + "000" + StringUtils.rightPad("",4," ") + StringUtils.rightPad(registros.getCuentaAbonoAliado(),35," ") + "VESVES" + StringUtils.rightPad("",28," ") + StringUtils.rightPad("Abono por concepto MILPAGO aliado: " + registros.getIdentificacionCompletaAliado().trim() + " " + nombreArchivo + " " + df.format(ahora) + "",105," ") + StringUtils.rightPad("",6," ") + StringUtils.rightPad(codigoSwift.get(0).getCodigoSwift(),12," ") + "BIC" + StringUtils.rightPad("",132," ") + "00000" + StringUtils.rightPad(codigosAfil.getNumeroCuentaAbono(),35," ") + StringUtils.rightPad("",4," ") + "X";                                

                                                        //Guarda en la tabla LoteDetalle                  
                                                        loteDetalle5.setCodigoCompania("D0U");
                                                        loteDetalle5.setNumeroLote(nombreArchivo);
                                                        loteDetalle5.setNumeroPagoProveedor(registros.getId().toString());
                                                        loteDetalle5.setTipoRegistro(5);
                                                        loteDetalle5.setMontoTotal(montoAbono);
                                                        loteDetalle5.setNombreBeneficiario(registros.getContactoAliado().trim());
                                                        loteDetalle5.setTipoPago(Integer.parseInt(tipoCuentaAbono));
                                                        loteDetalle5.setCuentaBeneficiario(registros.getCuentaAbonoAliado());
                                                        loteDetalle5.setCodigoMonedaCredito("VES");
                                                        loteDetalle5.setCodigoMonedaDebito("VES");
                                                        loteDetalle5.setConceptoPago("Abono por concepto MILPAGO aliado: " + registros.getIdentificacionCompletaAliado().trim() + " " + nombreArchivo + " " + df.format(ahora) + "");
                                                        loteDetalle5.setCodigoBancoBeneficiario("");
                                                        loteDetalle5.setTipoCodigoBanco("BIC");                        
                                                        loteDetalle5.setMotivoOperacion(000);
                                                        loteDetalle5.setActividadEconomica(00);
                                                        loteDetalle5.setCuentaDebito(codigosAfil.getNumeroCuentaAbono());                        
                                                        loteDetalleDAO.saveLoteDetalle(loteDetalle5);

                                                        bw.write(line2.trim());
                                                        bw.write("\r\n");                                                

                                                        totalRegistros++;             
                                                    }

                                                }else{

                                                    LoteDetalle loteDetalle4 = new LoteDetalle();
                                                    LoteDetalle loteDetalle5 = new LoteDetalle();

                                                    //Registro tipo 4
                                                    line1 = "D0U" + nombreArchivo + StringUtils.leftPad(registros.getId().toString(),8,"0") + StringUtils.leftPad("",8,"0") + "4" + StringUtils.rightPad(email,129," ") + registros.getTipoIdentificacionAliado() + StringUtils.leftPad(registros.getIdentificacionAliado().substring(1),16,"0") + StringUtils.rightPad("",325," ") + "X";                                

                                                    //Guarda en la tabla LoteDetalle                     
                                                    loteDetalle4.setCodigoCompania("D0U");                        
                                                    loteDetalle4.setNumeroLote(nombreArchivo);
                                                    loteDetalle4.setNumeroPagoProveedor(registros.getId().toString());                        
                                                    loteDetalle4.setTipoRegistro(4);
                                                    loteDetalle4.setEmailBeneficiario(registros.getEmailAliado());
                                                    loteDetalle4.setRifBeneficiario(registros.getTipoIdentificacionAliado() + registros.getIdentificacionAliado());
                                                    loteDetalle4.setMontoTotal(montoAbono);
                                                    loteDetalleDAO.saveLoteDetalle(loteDetalle4);

                                                    bw.write(line1.trim());
                                                    bw.write("\r\n");

                                                    codigoSwift = bancoDAO.getCodigoSwiftList(registros.getCuentaAbonoAliado().substring(0, 4));
                                                    if("0104".equals(registros.getCuentaAbonoAliado().substring(0, 4))){
                                                        tipoCuentaAbono = "1";
                                                    }else{
                                                        tipoCuentaAbono = "3";
                                                    }

                                                    int maxLength = (registros.getContactoAliado().length() < 35)?registros.getContactoAliado().length():35;
                                                    nombreComercio = registros.getContactoAliado().substring(0, maxLength);

                                                    //Registro tipo 5
                                                    line2 = "D0U" + nombreArchivo + StringUtils.leftPad(registros.getId().toString(),8,"0") + StringUtils.leftPad("",8,"0") + "5" + StringUtils.leftPad(String.valueOf(montoAbono.setScale(2, RoundingMode.CEILING)).replace(".", "").replace(",", ""),15,"0") + StringUtils.rightPad(nombreComercio,60," ") + tipoCuentaAbono + StringUtils.rightPad("",17," ") + "000" + StringUtils.rightPad("",4," ") + StringUtils.rightPad(registros.getCuentaAbonoAliado(),35," ") + "VESVES" + StringUtils.rightPad("",28," ") + StringUtils.rightPad("Abono por concepto MILPAGO aliado: " + registros.getIdentificacionCompletaAliado().trim() + " " + nombreArchivo + " " + df.format(ahora) + "",105," ") + StringUtils.rightPad("",6," ") + StringUtils.rightPad(codigoSwift.get(0).getCodigoSwift(),12," ") + "BIC" + StringUtils.rightPad("",132," ") + "00000" + StringUtils.rightPad(codigosAfil.getNumeroCuentaAbono(),35," ") + StringUtils.rightPad("",4," ") + "X";                                

                                                    //Guarda en la tabla LoteDetalle                  
                                                    loteDetalle5.setCodigoCompania("D0U");
                                                    loteDetalle5.setNumeroLote(nombreArchivo);
                                                    loteDetalle5.setNumeroPagoProveedor(registros.getId().toString());
                                                    loteDetalle5.setTipoRegistro(5);
                                                    loteDetalle5.setMontoTotal(montoAbono);
                                                    loteDetalle5.setNombreBeneficiario(registros.getContactoAliado().trim());
                                                    loteDetalle5.setTipoPago(Integer.parseInt(tipoCuentaAbono));
                                                    loteDetalle5.setCuentaBeneficiario(registros.getCuentaAbonoAliado());
                                                    loteDetalle5.setCodigoMonedaCredito("VES");
                                                    loteDetalle5.setCodigoMonedaDebito("VES");
                                                    loteDetalle5.setConceptoPago("Abono por concepto MILPAGO aliado: " + registros.getIdentificacionCompletaAliado().trim() + " " + nombreArchivo + " " + df.format(ahora) + "");
                                                    loteDetalle5.setCodigoBancoBeneficiario("");
                                                    loteDetalle5.setTipoCodigoBanco("BIC");                        
                                                    loteDetalle5.setMotivoOperacion(000);
                                                    loteDetalle5.setActividadEconomica(00);
                                                    loteDetalle5.setCuentaDebito(codigosAfil.getNumeroCuentaAbono());                        
                                                    loteDetalleDAO.saveLoteDetalle(loteDetalle5);

                                                    bw.write(line2.trim());
                                                    bw.write("\r\n");                                            

                                                    totalRegistros++;                   
                                                }                                                   

                                            }else{
                                                totalRegistrosExcluidos++;
                                            }

                                        }                           

                                        if(totalRegistros>0){                                
                                            //registro tipo 9
                                            line3 = "D0U" + StringUtils.leftPad(nombreArchivo,8,"0") + StringUtils.leftPad("",16,"0") + "9" + dfFechaValor.format(ahora) + StringUtils.leftPad(String.valueOf(montoTotal.setScale(2, RoundingMode.CEILING)).replace(".", "").replace(",", ""),15,"0") + StringUtils.leftPad(String.valueOf(totalRegistros),15,"0") + "VES" + StringUtils.rightPad(codigosAfil.getNumeroCuentaAbono(),35," ") + StringUtils.rightPad("",35," ") + StringUtils.leftPad(String.valueOf(montoTotal).replace(".", "").replace(",", ""),15,"0") + "VES" + StringUtils.rightPad("",30," ") +"00000" + StringUtils.rightPad("",307," ") + "X";

                                            //Guarda en la tabla LotesXbanco
                                            loteCabecera.setActividadEconomica(00);
                                            loteCabecera.setCantidadPagos(totalRegistros);
                                            loteCabecera.setCodigoCompania("D0U");
                                            loteCabecera.setCodigoAfiliado(codigosAfil.getCodigoAfiliado());
                                            loteCabecera.setCodigoBanco(lotePagos.getBanco());
                                            loteCabecera.setTipoArchivo(lotePagos.getTipoPago());
                                            loteCabecera.setCodigoMonedaCredito("VES");
                                            loteCabecera.setCodigoMonedaDebito("VES");
                                            loteCabecera.setCuentaDebito(codigosAfil.getNumeroCuentaAbono());
                                            loteCabecera.setFechaValor(Utilidades.convierteFechaSqlsinHora(dfsql.format(ahora)));
                                            loteCabecera.setMontoTotal(montoTotal);
                                            loteCabecera.setMotivoOperacion(000);
                                            loteCabecera.setNumeroLote(nombreArchivo);
                                            loteCabecera.setTipoRegistro(9);
                                            loteDAO.saveLote(loteCabecera);
                                            bw.write(line3.trim());
                                            bw.close();                                                                
                                        }else{
                                            bw.close();
                                            fichero.delete();                                    
                                        }                                                        

                                }else{
                                    modelo.addAttribute("messageError", "El archivo con el nombre " + nombreArchivo +" ya existe, por favor verifique e intente nuevamente");
                                    return new ModelAndView("lotePagos", "model", modelo);
                                }                                                
                            }                                                

                        }

                    } catch (IOException ioe) {
                      bitacora.saveLogs(usuario.get(0).getId(), 2, 20, "No se ha podido realizar la operacion debido al siguiente error: "+ ioe.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");    
                      logger.error(ioe.getMessage());  
                      modelo.addAttribute("messageError", "No se pudo generar el archivo");
                      return new ModelAndView("lotePagos", "model", modelo);
                    } catch (Exception cve) {
                      bitacora.saveLogs(usuario.get(0).getId(), 2, 20, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");    
                      logger.error(cve.getMessage());  
                      modelo.addAttribute("messageError", "No se pudo generar el archivo debido al siguiente error: " + cve.getMessage());
                      return new ModelAndView("lotePagos", "model", modelo);  
                    }
                }
                
                totalRegistrosGen = totalRegistrosGen + totalRegistros;
            }
            
            if(totalRegistrosGen>0){
                return new ModelAndView(new RedirectView("resultadoLoteGenerado.htm?fecha="+ dfsql.format(ahora) +"&banco="+ lotePagos.getBanco() +"&tipoArchivo="+ lotePagos.getTipoPago() +""));
            }else{
                modelo.addAttribute("messageError", "No se encontraron registros para la fecha: " + fechaDesdesql);
                return new ModelAndView("lotePagos", "model", modelo);
            }  
            
        }else{
            modelo.addAttribute("messageError", "No se encontraron registros para el banco seleccionado");
            return new ModelAndView("lotePagos", "model", modelo);
        }                
    }

    //Resultado Lote Generado
    @RequestMapping(value="/resultadoLoteGenerado.htm", method = RequestMethod.GET)
    public ModelAndView resultadoLoteGenerado(@RequestParam(value = "fecha") String fecha,@RequestParam(value = "banco") String banco, @RequestParam(value = "tipoArchivo") String tipoArchivo, HttpServletRequest request, Model modelo)
            throws ServletException, IOException {
        
        List<Lotes> lotesGenerados;        
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");              
        
        try {                      
            java.sql.Date fechaDesdesql = Utilidades.convierteFechaSqlsinHora(fecha);
            
            lotesGenerados =  loteDAO.getLoteByFechaAfiliado(fechaDesdesql,banco,tipoArchivo);
            
            if(!lotesGenerados.isEmpty()){
                modelo.addAttribute("lotesGenerados", lotesGenerados);                
                modelo.addAttribute("messageError", "Archivo creado satisfactoriamente");
                return new ModelAndView("resultadoLoteGenerado", "model", modelo); 
            }else{
                modelo.addAttribute("messageError", "No se pudo generar el archivo o no existe data que cumpla con los parámetros establecidos");
                return new ModelAndView("resultadoLoteGenerado", "model", modelo);
            }                 
                
        } catch (Exception cve) {
            
            bitacora.saveLogs(usuario.get(0).getId(), 2, 20, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("resultadoLoteGenerado", "model", modelo);
        }
    }    
    
    /* Accion post del formulario Consulta Lotes */
    @RequestMapping(value="/consultaLotes.htm", method = RequestMethod.POST)
    public ModelAndView consultaLotesSubmit(@ModelAttribute("consultaLotes") GenerarLoteForm consultaLotes, BindingResult result, HttpServletRequest request, HttpServletResponse response, Model modelo) throws IOException, ParseException {    	                            
        
        List<Lotes> listadoLotes;        
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");                        
        
        if(consultaLotes.getFechaDesde().isEmpty() && consultaLotes.getFechaHasta().isEmpty()){
            result.rejectValue("fechaHasta","invalid", new Object[]{ consultaLotes.getFechaHasta()}, "Debe ingresar la Fecha Desde y Fecha Hasta");
            return new ModelAndView("consultaLotes");
        }
        
        try {
            
          java.sql.Date fechaDesdesql = Utilidades.convierteFechaSqlsinHora(consultaLotes.getFechaDesde());
          java.sql.Date fechaHastasql = Utilidades.convierteFechaSqlsinHora(consultaLotes.getFechaHasta());
          listadoLotes =  loteDAO.getLoteByFecha(fechaDesdesql,fechaHastasql);
          
          if(!listadoLotes.isEmpty()){            
            return new ModelAndView(new RedirectView("listadoLotes.htm?desde="+ consultaLotes.getFechaDesde() +"&hasta="+ consultaLotes.getFechaHasta() +""));
          }else{
            modelo.addAttribute("messageError", "No se encontraron registros para la fecha: " + fechaDesdesql + " y la fecha " + fechaHastasql);
            return new ModelAndView("consultaLotes", "model", modelo);
          }
                          
        } catch (Exception cve) {
            
            bitacora.saveLogs(usuario.get(0).getId(), 2, 21, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("consultaLotes", "model", modelo);
        }
    }
    
    //Listado de Lotes
    @RequestMapping(value="/listadoLotes.htm", method = RequestMethod.GET)
    public ModelAndView listadoLotes(@RequestParam(value = "desde") String desde,@RequestParam(value = "hasta") String hasta, HttpServletRequest request, HttpServletResponse response, Model modelo)
            throws ServletException, IOException {
        
        List<Lotes> listadoLotes;        
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");                                        
        
        try {
            
          java.sql.Date fechaDesdesql = Utilidades.convierteFechaSqlsinHora(desde);
          java.sql.Date fechaHastasql = Utilidades.convierteFechaSqlsinHora(hasta);
          listadoLotes =  loteDAO.getLoteByFecha(fechaDesdesql,fechaHastasql);
          
          if(!listadoLotes.isEmpty()){
            modelo.addAttribute("listadoLotes", listadoLotes);            
            return new ModelAndView("listadoLotes", "model", modelo);            
          }else{
            modelo.addAttribute("messageError", "No se encontraron registros para la fecha: " + fechaDesdesql + " y la fecha " + fechaHastasql);
            return new ModelAndView("consultaLotes", "model", modelo);
          }
                          
        } catch (Exception cve) {
            
            bitacora.saveLogs(usuario.get(0).getId(), 2, 21, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("consultaLotes", "model", modelo);
        }
        
    }
    
    //Listado de Comercios Excluidos del Pago
    @RequestMapping(value="/listadoComerciosExcluidos.htm")
    public ModelAndView listadoComerciosExcluidos(HttpServletRequest request, HttpServletResponse response, Model modelo)
            throws ServletException, IOException {
        
        List<Comercio> resultado;
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuarioLogin = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        try{
            
            resultado = comercioService.getComerciosExcluidosList();
        
            modelo.addAttribute("listadoComerciosExcluidos", resultado);
            return new ModelAndView("listadoComerciosExcluidos","model",modelo);
            
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 17, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("listadoComerciosExcluidos" ,"model" ,modelo);
        }        
        
    }
    
    @ModelAttribute("afiliadosList")
    public List afiliadosList() {
        
        List<Afiliado> afiliadosList = null;
        
        try{
            afiliadosList = comercioService.getAfiliadoList();
        }catch (Exception cve) {                       
            logger.error(cve.getMessage());                       
        } 
                        		         
        return afiliadosList;
    }
    
    @ModelAttribute("bancosList")
    public List bancosList() {
        
        List<Banco> bancosList = null;
        
        try{
            bancosList = bancoDAO.getBancoList();
        }catch (Exception cve) {                       
            logger.error(cve.getMessage());                       
        } 
                        		         
        return bancosList;
    }
}
