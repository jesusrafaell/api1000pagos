package com.tranred.milpagosapp.web;

import com.tranred.milpagosapp.domain.Afiliado;
import com.tranred.milpagosapp.domain.Banco;
import com.tranred.milpagosapp.domain.Comercio;
import com.tranred.milpagosapp.domain.Historico;
import com.tranred.milpagosapp.domain.HistoricoAliado;
import com.tranred.milpagosapp.domain.LoteDetalle;
import com.tranred.milpagosapp.domain.Lotes;
import com.tranred.milpagosapp.domain.PlanCuota;
import com.tranred.milpagosapp.domain.Usuario;
import com.tranred.milpagosapp.repository.IHistoricoDAO;
import com.tranred.milpagosapp.repository.JPABancoDAO;
import com.tranred.milpagosapp.repository.JPALoteDetalleDAO;
import com.tranred.milpagosapp.repository.JPALotesDAO;
import com.tranred.milpagosapp.repository.JPAPlanCuotaDAO;
import com.tranred.milpagosapp.service.ConsultaLoteForm;
import com.tranred.milpagosapp.service.GenerarLoteForm;
import com.tranred.milpagosapp.service.IBitacoraService;
import com.tranred.milpagosapp.service.IComercioService;
import com.tranred.milpagosapp.service.PagoCuotaManualForm;
import com.tranred.milpagosapp.util.Utilidades;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import org.springframework.web.multipart.MultipartFile;
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
public class PagoCuotaManualController{    
    
    protected final Log logger = LogFactory.getLog(getClass());
    
    @Autowired
    private Utilidades utils;
    
    @Autowired
    private JPALotesDAO loteDAO;
    
    @Autowired
    private JPALoteDetalleDAO loteDetalleDAO;
    
    @Autowired
    private JPABancoDAO bancoDAO;
    
    @Autowired
    private JPAPlanCuotaDAO planCuotaDAO;
    
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
    
    @Value("${ruta.cobranzaComercios}")
    private String rutaArchivos;
    
    /* Estado inicial del formulario Pagar Cuotas Manual */
    @RequestMapping(value="/cargarCuotasManual.htm", method = RequestMethod.GET)
    public @ModelAttribute("cargarCuotasManual") PagoCuotaManualForm pagoCuotasManual() {
        return new PagoCuotaManualForm();
    }
    
    /* Accion post del formulario Lote Pagos */
    @RequestMapping(value="/cargarCuotasManual.htm", method = RequestMethod.POST)
    //@ModelAttribute("cargarCuotasManual") @Valid PagoCuotaManualForm cargarCuotasManual, BindingResult result
    public ModelAndView cargarCuotasManual(@RequestParam("archivo") MultipartFile file, HttpServletRequest request, HttpServletResponse response, Model modelo){
    	HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        Locale español = new Locale("es");
        String rowNumber = "0";
        try{
            if (file.getSize() == 0) {
                modelo.addAttribute("messageError", "El archivo no es valido.");
                return new ModelAndView("cargarCuotasManual", "model", modelo);
            }
            
            if(!"application/vnd.ms-excel".equals(file.getContentType())){
                modelo.addAttribute("messageError", "El archivo no tiene extension valida.");
                return new ModelAndView("cargarCuotasManual", "model", modelo);
            }
        
            byte[] bytes = file.getBytes();
            Path path = Paths.get(rutaArchivos +"/"+ file.getOriginalFilename());
            Files.write(path, bytes);

            File convFile = new File(file.getOriginalFilename());

            System.out.println(path);
            System.out.println(convFile.getName());

            FileInputStream fIP = new FileInputStream(rutaArchivos +"/"+ convFile.getName());

            List<PlanCuota> cuotasDB = planCuotaDAO.getPlanCuotaByEstatusList("25,26","2");
            //Get the workbook instance for XLSX file 

            HashMap<Integer,PlanCuota> cuotas = new HashMap<Integer,PlanCuota>();
            for(PlanCuota registro : cuotasDB){
                cuotas.put(registro.getId(), registro);
            }
            HSSFWorkbook workbook = new HSSFWorkbook(fIP);
            HSSFSheet sheet = workbook.getSheetAt(0);

            int rows = sheet.getLastRowNum();
            for (int i = 1; i < rows; ++i) {
                HSSFRow row = sheet.getRow(i);

                HSSFCell rowNumberHSSFCell = row.getCell(0); // numero de fila 
                HSSFCell codigoAfiliadoHSSFCell = row.getCell(1); // aboCodAfi 
                HSSFCell codigoComercioHSSFCell = row.getCell(2); // aboCodComercio  
                HSSFCell codigoTerminalHSSFCell = row.getCell(3); // aboTerminal  
                HSSFCell idPlanCuotaHSSFCell = row.getCell(10); // idPlanCuota 
                HSSFCell montoTotalHSSFCell = row.getCell(12); // montoTotal  
                HSSFCell montoComisionHSSFCell = row.getCell(13); // montoComision  
                HSSFCell montoIVAHSSFCell = row.getCell(14); // montoIVA   
                HSSFCell tasaValorHSSFCell = row.getCell(15); // tasaValor    
                HSSFCell tasafechaHSSFCell = row.getCell(16); //  fecha e tasa dicom
                HSSFCell fechaProcesoIniHSSFCell = row.getCell(17); // fechaProcesoIni
                HSSFCell fechaPagoHSSFCell = row.getCell(18); // fechaPago

                rowNumber = readCellAsString(rowNumberHSSFCell); //se guarda el dato del numero de registro 
                if(null != fechaPagoHSSFCell  && null != idPlanCuotaHSSFCell 
                && null != montoTotalHSSFCell && null != montoComisionHSSFCell      && null != montoIVAHSSFCell 
                && null != tasaValorHSSFCell  && null != tasafechaHSSFCell          && null != fechaProcesoIniHSSFCell
                && null != codigoAfiliadoHSSFCell && null != codigoComercioHSSFCell && null != codigoTerminalHSSFCell){
                    
                    
                    String idPlanCuotaStr  = readCellAsString(idPlanCuotaHSSFCell); // idPlanCuota 
                    PlanCuota cuota = cuotas.get(new Integer(idPlanCuotaStr));
                    //valida que el ID CUOTA exista en las cuotas pendientes por pagar
                    if (cuota != null){
                        String codigoAfiliadoStr = readCellAsString(codigoAfiliadoHSSFCell); // aboCodAfi
                        String codigoComercioStr = readCellAsString(codigoComercioHSSFCell); // aboCodComercio  
                        String codigoTerminalStr = readCellAsString(codigoTerminalHSSFCell); // aboTerminal   
                        
                        //se valida que la llave del terminal coincida
                        if(cuota.getCodigoAfiliado().equals(codigoAfiliadoStr) 
                        && cuota.getCodigoComercio().toString().equals(codigoComercioStr) 
                        && cuota.getCodigoTerminal().toString().equals(codigoTerminalStr)){
                            //es valido el id cuota coincide con la llave (aboCodAfi, aboCodComercio, aboTerminal)
                            //se procede a convertir los datos
                            Double montoTotalD = montoTotalHSSFCell.getNumericCellValue();
                            Double montoComisionD = montoComisionHSSFCell.getNumericCellValue();
                            Double montoIVAD = montoIVAHSSFCell.getNumericCellValue();
                            Double tasaValorD = tasaValorHSSFCell.getNumericCellValue();
                            Date fechaProcesoIniD =  fechaProcesoIniHSSFCell.getDateCellValue();
                            Date fechaPagoD =  fechaPagoHSSFCell.getDateCellValue();
                            cuota.setMontoComision(new BigDecimal(montoComisionD));
                            cuota.setMontoIVA(new BigDecimal(montoIVAD));
                            cuota.setTasaValor(new BigDecimal(tasaValorD));
                            cuota.setFechaPago(new java.sql.Date(fechaPagoD.getTime()));
                            cuota.setEstatus(27);
                            Integer idPlanCuotaId = new Integer(idPlanCuotaStr);
                            cuotas.replace(idPlanCuotaId, cuota);
                        }else{
                            throw new RuntimeException("unknown key code");
                        }
                    }else{
                        throw new RuntimeException("unknown id cuota");
                    }
                    
                }   
                    
                //Date fechaProcesoIni = fechaProcesoIniHSSFCell.getDateCellValue(); //  fechaProcesoIni
                //BigDecimal montoComision = new BigDecimal(montoComisionHSSFCell.getNumericCellValue()); // montoComision  
                //BigDecimal montoIVA = new BigDecimal(montoIVAHSSFCell.getNumericCellValue()); // montoIVA  
                //BigDecimal tasaValor = new BigDecimal(tasaValorHSSFCell.getNumericCellValue()); // tasaValor  
                //Date tasaFecha = tasafechaHSSFCell.getDateCellValue(); //  fecha e tasa dicom
                //Date fechaPago = fechaPagoHSSFCell.getDateCellValue(); //  fechaPago
                //System.out.printf("%s, $s, $s, $s, $s, $s, $s, %s%n", idPlanCuotaStr, montoTotalStr, montoComisionStr, montoIVAStr, tasaValorStr, tasaFecha, fechaProcesoIni, fechaPago);
            }
            
            Integer count = 0;
            for (Map.Entry<Integer, PlanCuota> entry : cuotas.entrySet()) {
                PlanCuota c = entry.getValue();
                if(c.getEstatus().equals(27)){
                    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue().getId());
                    planCuotaDAO.updatePlanCuota(c);
                    count++;
                }
                
            }
            modelo.addAttribute("messageError", "Cuotas Cargadas correctamente: "+count.toString());
            return new ModelAndView("cargarCuotasManual", "model", modelo);
        }catch (Exception cve) {                       
            logger.error(cve.getMessage());   
            bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");           
            modelo.addAttribute("messageError", "Error en el archivo en el registro "+rowNumber+", por favor valide.");
            return new ModelAndView("cargarCuotasManual", "model", modelo);
        } 
        
    }
    
    //funcion para obtener el valor de la celda en string
    private String readCellAsString(final Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue();
            case Cell.CELL_TYPE_BLANK:
                return "";
            case Cell.CELL_TYPE_BOOLEAN:
                return Boolean.toString(cell.getBooleanCellValue());
            case Cell.CELL_TYPE_NUMERIC:
                final DataFormatter formatter = new DataFormatter();
                return formatter.formatCellValue(cell);
            default:
                throw new RuntimeException("unknown cell type " + cell.getCellType());
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
}
