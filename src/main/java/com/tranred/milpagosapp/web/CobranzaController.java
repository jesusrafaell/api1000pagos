package com.tranred.milpagosapp.web;

import com.tranred.milpagosapp.domain.Cobranzas;
import com.tranred.milpagosapp.domain.Estatus;
import com.tranred.milpagosapp.domain.PlanCuotaSP;
import com.tranred.milpagosapp.domain.TerminalXComercio;
import com.tranred.milpagosapp.domain.TipoPlan;
import com.tranred.milpagosapp.domain.Usuario;
import com.tranred.milpagosapp.repository.IEstatusDAO;
import com.tranred.milpagosapp.repository.JPATipoPlanDAO;
import com.tranred.milpagosapp.service.IGenerarArchivosService;
import com.tranred.milpagosapp.service.IGenerarCuotasService;
import com.tranred.milpagosapp.service.GenerarCobranzaForm;
import com.tranred.milpagosapp.service.IBitacoraService;
import com.tranred.milpagosapp.service.ICobranzaService;
import com.tranred.milpagosapp.util.Utilidades;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.ObjectError;

/**
 *  Clase actua como controlador para el módulo de Estados de Cuenta
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 * 
 */
@Controller
public class CobranzaController {
    
    protected final Log logger = LogFactory.getLog(getClass());    
    
    @Autowired
    private IBitacoraService bitacora;
    
    @Autowired
    private ICobranzaService cobranzaService;
    
    @Autowired
    private IGenerarCuotasService generarCuotasService;
    
    @Autowired
    private IGenerarArchivosService generarCobranza;
    
    @Autowired
    private IEstatusDAO estatusDAO;
    
    @Autowired
    private JPATipoPlanDAO tipoPlan;
    
    
    
    //Asignamos el estado inicial del formulario generarCobranzasComercios
    @ModelAttribute("generarCobranzasComercios")
    public GenerarCobranzaForm createModelCrear() {
        return new GenerarCobranzaForm();
    }
    
    /* Generar Cobranzas a Comercios */
    @RequestMapping(value="/generarCobranzasComercios.htm", method = RequestMethod.GET)
    public ModelAndView generarCobranzasComercios(HttpServletRequest request, HttpServletResponse response, Model modelo)
            throws ServletException, IOException, ParseException {
                        
        HttpSession misession= (HttpSession) request.getSession();  
        List<Usuario> usuarioLogin = (List<Usuario>) misession.getAttribute("usuario.datos");                  
        
        try{
             
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 25, "Ingreso a opción Generar Cobranza Comercios", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");                        
            return new ModelAndView("generarCobranzasComercios");
        
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 25, "No se ha podido realizar la operacion debido al siguiente error: "+cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());           
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("generarCobranzasComercios");
        }        
        
    }        
    
    //Accion post del formulario generarCobranzasComercios
    @RequestMapping(value="/generarCobranzasComercios.htm", method = RequestMethod.POST)
    public ModelAndView generarCobranzasComerciosSubmit(@ModelAttribute("generarCobranzasComercios") @Valid GenerarCobranzaForm generarCobranzasComercios, BindingResult result, Model modelo, HttpServletRequest request)
    {                
        Map<String, Object> myModel = new HashMap<>();
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuarioLogin = (List<Usuario>) misession.getAttribute("usuario.datos");                        
        List<PlanCuotaSP> cobranzasXComercioResumen, cobranzasXComercioDetail, cobranzasXComercioDataentry;

        try{
            
            if (result.hasFieldErrors("estatus")) {
                return new ModelAndView("generarCobranzasComercios");        
            }        
            if (result.hasFieldErrors("tipoPlan")) {
                return new ModelAndView("generarCobranzasComercios");
            }
            if (result.hasErrors()) {                    
                return new ModelAndView("generarCobranzasComercios");
            }

            if("1".equals(generarCobranzasComercios.getTipoConsulta())){
                if (null != generarCobranzasComercios.getTipoConsultaIndividual())
                    switch (generarCobranzasComercios.getTipoConsultaIndividual()) {
                        case "0":
                            if (generarCobranzasComercios.getTipoIdentificacionComercio().isEmpty() || generarCobranzasComercios.getIdentificacionComercio().isEmpty()){
                                result.rejectValue("identificacionComercio","invalid", new Object[]{ generarCobranzasComercios.getTerminal()}, "Debe ingresar el numero de identificacion del comercio");
                                return new ModelAndView("generarCobranzasComercios");
                            }   break;
                        case "1":
                            if (generarCobranzasComercios.getTerminal().isEmpty()){
                                result.rejectValue("terminal","invalid", new Object[]{ generarCobranzasComercios.getTerminal()}, "Debe ingresar el terminal");
                                return new ModelAndView("generarCobranzasComercios");
                            }   break;
                        case "2":
                            if (generarCobranzasComercios.getTipoIdentificacionACI().isEmpty() || generarCobranzasComercios.getIdentificacionACI().isEmpty()){
                                result.rejectValue("identificacionACI","invalid", new Object[]{ generarCobranzasComercios.getTerminal()}, "Debe ingresar el numero de identificacion del ACI");
                                return new ModelAndView("generarCobranzasComercios");
                            }   break;
                        default:
                            break;
                    }

            }
            String titulo;
            titulo = new String("REPORTE DE CUOTAS ");
            String[] estatus = generarCobranzasComercios.getEstatus().split(",");
            System.out.println(estatus.length);
            Integer i = 1;
            for(String e : estatus){
                if(i > 1 && i < estatus.length){
                    titulo += ", ";
                }else if(i == estatus.length){
                    titulo += " Y ";
                }
                switch (e){
                    case "25" : titulo += "PENDIENTES";break;
                    case "26" : titulo += "VENCIDAS";break;
                    case "27" : titulo += "PAGADAS";break;
                    case "28" : titulo += "ANULADAS";break;
                    case "29" : titulo += "EXONERADAS";break;
                }
                i++;
            }
            
            System.out.println(generarCobranzasComercios.getEstatus());
            System.out.println(titulo);
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 25, "Genera Cobranzas Comercios", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
            cobranzasXComercioResumen = cobranzaService.getCobranzasList( generarCobranzasComercios.getMes(), generarCobranzasComercios.getAno(), generarCobranzasComercios.getEstatus(), generarCobranzasComercios.getTipoPlan(), generarCobranzasComercios.getTipoConsulta(),generarCobranzasComercios.getTipoConsultaIndividual(),generarCobranzasComercios.getTipoIdentificacionComercio(),generarCobranzasComercios.getIdentificacionComercio(),generarCobranzasComercios.getTerminal(),generarCobranzasComercios.getTipoIdentificacionACI(),generarCobranzasComercios.getIdentificacionACI(),"1");
            cobranzasXComercioDetail = cobranzaService.getCobranzasList( generarCobranzasComercios.getMes(), generarCobranzasComercios.getAno(), generarCobranzasComercios.getEstatus(), generarCobranzasComercios.getTipoPlan(), generarCobranzasComercios.getTipoConsulta(),generarCobranzasComercios.getTipoConsultaIndividual(),generarCobranzasComercios.getTipoIdentificacionComercio(),generarCobranzasComercios.getIdentificacionComercio(),generarCobranzasComercios.getTerminal(),generarCobranzasComercios.getTipoIdentificacionACI(),generarCobranzasComercios.getIdentificacionACI(),"0");
            //para dataentry solo cuotas pendientes o vencidas, de tipo plan de equipo
            cobranzasXComercioDataentry = cobranzaService.getCobranzasList( generarCobranzasComercios.getMes(), generarCobranzasComercios.getAno(), "25,26", "2", generarCobranzasComercios.getTipoConsulta(),generarCobranzasComercios.getTipoConsultaIndividual(),generarCobranzasComercios.getTipoIdentificacionComercio(),generarCobranzasComercios.getIdentificacionComercio(),generarCobranzasComercios.getTerminal(),generarCobranzasComercios.getTipoIdentificacionACI(),generarCobranzasComercios.getIdentificacionACI(),"0");
            /*System.out.printf("%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s%n",generarCobranzasComercios.getMes()
                    , generarCobranzasComercios.getAno()
                    , "25,26"
                    , "2"
                    , generarCobranzasComercios.getTipoConsulta()
                    , generarCobranzasComercios.getTipoConsultaIndividual()
                    , generarCobranzasComercios.getTipoIdentificacionComercio()
                    , generarCobranzasComercios.getIdentificacionComercio()
                    , generarCobranzasComercios.getTerminal()
                    , generarCobranzasComercios.getTipoIdentificacionACI()
                    , generarCobranzasComercios.getIdentificacionACI());    */    
            
            if(cobranzasXComercioResumen.isEmpty()){
                myModel.put("messageError", "No se encontraron registros con los parametros seleccionados");
                return new ModelAndView("generarCobranzasComercios","model", myModel);
            }else{  
                generarCobranza.generarCobranzasExcelComercios(cobranzasXComercioResumen,"1",titulo);
                generarCobranza.generarCobranzasTXTComercios(cobranzasXComercioResumen,"1",titulo);
                generarCobranza.generarCobranzasExcelComercios(cobranzasXComercioDetail,"0",titulo);
                generarCobranza.generarCobranzasTXTComercios(cobranzasXComercioDetail,"0",titulo);
                generarCobranza.generarDataentryCobranzaExcel(cobranzasXComercioDataentry);
                myModel.put("messageError", "Archivo de Cobranzas generado satisfactoriamente");
                return new ModelAndView("generarCobranzasComercios","model", myModel);
            }   
            
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 25, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("generarCobranzasComercios", "model", modelo);
        }                                
        
    }
    
    @ModelAttribute("tipoIdentificacionList")
    public Map<String,String> tipoIdentificacionList() {
   
        Map<String,String> tipo = new LinkedHashMap<String,String>();
        tipo.put("V", "V");
        tipo.put("E", "E");
        tipo.put("P", "P");
        tipo.put("J", "J");
        tipo.put("G", "G");
        tipo.put("C", "C");
        tipo.put("R", "R");
        
        return tipo;
    }
    
    @ModelAttribute("mesList")
    protected Map<Integer,String> mesList(HttpServletRequest request) throws Exception {
	
        Map<Integer,String> mesList = new LinkedHashMap<>();
	mesList = Utilidades.mesesAno();
	
	return mesList;
    }
    
    @ModelAttribute("anoList")
    protected List anoList(HttpServletRequest request) throws Exception {
	                
	List<Integer> anoList = new ArrayList<>();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = year; i>= year-4; i--) {
            anoList.add(i);
        }
	
	return anoList;
    }
    
    @ModelAttribute("estatusList")
    public List estatusList() {
        
        List<Estatus> estatusList = null;
        
        try{
            estatusList = estatusDAO.getEstatusByModulo("cob");
        }catch (Exception cve) {                       
            logger.error(cve.getMessage());                       
        } 
                                     		         
        return estatusList;
    }   
    
    @ModelAttribute("tipoPlanList")
    public List tipoPlanList() {
        
        List<TipoPlan> tipoPlanList = null;
        
        try{
            tipoPlanList = tipoPlan.getTipoPlanList();
        }catch (Exception cve) {                                   
            logger.error(cve.getMessage());            
        }
                                     		         
        return tipoPlanList;
    }
}
