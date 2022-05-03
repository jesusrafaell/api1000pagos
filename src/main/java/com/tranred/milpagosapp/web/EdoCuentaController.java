package com.tranred.milpagosapp.web;

import com.tranred.milpagosapp.domain.TerminalXComercio;
import com.tranred.milpagosapp.domain.Usuario;
import com.tranred.milpagosapp.service.GenerarEdoCuentaForm;
import com.tranred.milpagosapp.service.IBitacoraService;
import com.tranred.milpagosapp.service.IComercioService;
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
import com.tranred.milpagosapp.service.IGenerarArchivosService;

/**
 *  Clase actua como controlador para el módulo de Estados de Cuenta
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 * 
 */
@Controller
public class EdoCuentaController {
    
    protected final Log logger = LogFactory.getLog(getClass());    
    
    @Autowired
    private IBitacoraService bitacora;
    
    @Autowired
    private IComercioService comercioService;
    
    @Autowired
    private IGenerarArchivosService generarEdoCuenta;
    
    //Asignamos el estado inicial del formulario generarEdoCuentaComercios
    @ModelAttribute("generarEdoCuentaComercios")
    public GenerarEdoCuentaForm createModelCrear() {
        return new GenerarEdoCuentaForm();
    }
    
    /* Generar Estados de Cuenta a Comercios */
    @RequestMapping(value="/generarEdoCuentaComercios.htm", method = RequestMethod.GET)
    public ModelAndView generarEdoCuentaComercios(HttpServletRequest request, HttpServletResponse response, Model modelo)
            throws ServletException, IOException, ParseException {
                        
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuarioLogin = (List<Usuario>) misession.getAttribute("usuario.datos");                  
        
        try{
             
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 25, "Ingreso a opción Generar Estados de Cuenta", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");                        
            return new ModelAndView("generarEdoCuentaComercios");
        
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 25, "No se ha podido realizar la operacion debido al siguiente error: "+cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());           
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("generarEdoCuentaComercios");
        }        
        
    }        
    
    //Accion post del formulario generarEdoCuentaComercios
    @RequestMapping(value="/generarEdoCuentaComercios.htm", method = RequestMethod.POST)
    public ModelAndView generarEdoCuentaComerciosSubmit(@ModelAttribute("generarEdoCuentaComercios") @Valid GenerarEdoCuentaForm generarEdoCuentaComercios, BindingResult result, Model modelo, HttpServletRequest request)
    {                
        if (result.hasErrors()) {
            return new ModelAndView("generarEdoCuentaComercios");
        }
        
        if("1".equals(generarEdoCuentaComercios.getTipoConsulta()) && generarEdoCuentaComercios.getTerminal().isEmpty()){
            result.rejectValue("terminal","invalid", new Object[]{ generarEdoCuentaComercios.getTerminal()}, "Debe ingresar el terminal");
            return new ModelAndView("generarEdoCuentaComercios");
        }
        
        Map<String, Object> myModel = new HashMap<>();
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuarioLogin = (List<Usuario>) misession.getAttribute("usuario.datos");                        
        List<TerminalXComercio> terminalesXComercio;
        
        try{
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 25, "Genera Estados de Cuenta", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
            
            //Para un Terminal en especifico
            if("1".equals(generarEdoCuentaComercios.getTipoConsulta())){               
                               
                terminalesXComercio = comercioService.getTerminalInfoList(generarEdoCuentaComercios.getTerminal());

                if(terminalesXComercio.isEmpty()){
                    myModel.put("messageError", "No se encontraron registros con los parametros seleccionados");
                    return new ModelAndView("generarEdoCuentaComercios","model", myModel);
                }else{                        
                    for(TerminalXComercio registros: terminalesXComercio){
                        generarEdoCuenta.generarEdoCuentaPDFComercios(generarEdoCuentaComercios.getMes(), generarEdoCuentaComercios.getAno(), registros.getCodigoComercio(),registros.getRifComercio(), registros.getTerminal());
                        generarEdoCuenta.generarEdoCuentaExcelComercios(generarEdoCuentaComercios.getMes(), generarEdoCuentaComercios.getAno(), registros.getCodigoComercio(),registros.getRifComercio(), registros.getTerminal());
                    }

                    myModel.put("messageError", "Estados de cuenta generados satisfactoriamente");
                    return new ModelAndView("generarEdoCuentaComercios","model", myModel);
                }                                                            
                                                       
            //Generar todos los Edo de Cuenta    
            }else{
                                
                terminalesXComercio = comercioService.getTerminalXComercioList();
                
                if(terminalesXComercio.isEmpty()){                    
                    myModel.put("messageError", "No se encontraron registros con los parametros seleccionados");
                    return new ModelAndView("generarEdoCuentaComercios","model", myModel);               
                }else{
                    for(TerminalXComercio registros: terminalesXComercio){
                        generarEdoCuenta.generarEdoCuentaPDFComercios(generarEdoCuentaComercios.getMes(), generarEdoCuentaComercios.getAno(), registros.getCodigoComercio(),registros.getRifComercio(), registros.getTerminal());
                        generarEdoCuenta.generarEdoCuentaExcelComercios(generarEdoCuentaComercios.getMes(), generarEdoCuentaComercios.getAno(), registros.getCodigoComercio(),registros.getRifComercio(), registros.getTerminal());
                    }                    
                    myModel.put("messageError", "Estados de cuenta generados satisfactoriamente");
                    return new ModelAndView("generarEdoCuentaComercios","model", myModel); 
                }                
                
            }
        
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 25, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("generarEdoCuentaComercios", "model", modelo);
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
    
}
