package com.tranred.milpagosapp.web;

import com.tranred.milpagosapp.domain.Logs;
import com.tranred.milpagosapp.domain.LogsWeb;
import com.tranred.milpagosapp.domain.Usuario;
import com.tranred.milpagosapp.service.IBitacoraService;
import com.tranred.milpagosapp.util.Utilidades;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *  Clase actua como controlador para el módulo de administración opción bitácora (Admin y Terminales).
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 * 
 */

@Controller
public class BitacoraController {
    
    protected final Log logger = LogFactory.getLog(getClass());    
    
    @Autowired
    private IBitacoraService bitacora;    
    
    /* Bitacora Administracion */
    @RequestMapping(value="/bitacoraAdmin.htm", method = RequestMethod.GET)
    public ModelAndView bitacoraAdmin(@RequestParam(value = "desde") String desde, @RequestParam(value = "hasta") String hasta, @RequestParam(value = "opcion") String opcion, @RequestParam(value = "modulo") String modulo,@RequestParam(value = "usuario") String usuario, HttpServletRequest request, HttpServletResponse response, Model modelo)
            throws ServletException, IOException, ParseException {
        
        List<Logs> resultado;
        String opcionSeleccionada;
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuarioLogin = (List<Usuario>) misession.getAttribute("usuario.datos");
        java.sql.Timestamp fechaDesdesql = Utilidades.convierteFechaSql(desde);
        java.sql.Timestamp fechaHastasql = Utilidades.convierteFechaSql(hasta);  
       
        if("".equals(opcion)){
            opcionSeleccionada = "%";
        }else{
            opcionSeleccionada = opcion;
        }
        
        try{
            
            if(usuario.isEmpty()){
                resultado = bitacora.getLogsList(fechaDesdesql,fechaHastasql,Integer.parseInt(modulo),opcionSeleccionada);
            }else{
                resultado = bitacora.getLogsListByUsuario(fechaDesdesql,fechaHastasql,Integer.parseInt(modulo),opcionSeleccionada,usuario);
            }                

            //Valida si existen registros con los parametros seleccionados
            if(resultado.isEmpty()){            
                    modelo.addAttribute("messageError", "No se encontraron registros con los parámetros seleccionados");
                    return new ModelAndView("consultaBitacora");
            }else{                  
                    return new ModelAndView("bitacoraAdmin","model",resultado);        
            }
        
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 1, 5, "No se ha podido realizar la consulta debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("bitacoraAdmin");
        }        
        
    }
    
    /* Bitacora 1000Pagos */
    @RequestMapping(value="/bitacoraMilPagos.htm")
    public ModelAndView bitacoraMilPagos(@RequestParam(value = "desde") String desde, @RequestParam(value = "hasta") String hasta, @RequestParam(value = "opcion") String opcion,@RequestParam(value = "usuario") String usuario, HttpServletRequest request, HttpServletResponse response, Model modelo)
            throws ServletException, IOException, ParseException {
        
        List<Logs> resultado;
        String opcionSeleccionada;
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuarioLogin = (List<Usuario>) misession.getAttribute("usuario.datos");
        java.sql.Timestamp fechaDesdesql = Utilidades.convierteFechaSql(desde);
        java.sql.Timestamp fechaHastasql = Utilidades.convierteFechaSql(hasta);  
       
        if("".equals(opcion)){
            opcionSeleccionada = "%";
        }else{
            opcionSeleccionada = opcion;
        }
        
        try{
            
            if(usuario.isEmpty()){
                resultado = bitacora.getLogsList(fechaDesdesql,fechaHastasql,2,opcionSeleccionada);
            }else{
                resultado = bitacora.getLogsListByUsuario(fechaDesdesql,fechaHastasql,2,opcionSeleccionada,usuario);
            } 

            //Valida si existen registros con los parametros seleccionados
            if(resultado.isEmpty()){            
                    modelo.addAttribute("messageError", "No se encontraron registros con los parámetros seleccionados");
                    return new ModelAndView("consultaBitacora");
            }else{                  
                    return new ModelAndView("bitacoraMilPagos","model",resultado);        
            }
        
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 1, 5, "No se ha podido realizar la consulta debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("bitacoraMilPagos");
        }                        
        
    }
    
    /* Bitacora Sitio Web */
    @RequestMapping(value="/bitacoraSitioWeb.htm")
    public ModelAndView bitacoraSitioWeb(@RequestParam(value = "desde") String desde, @RequestParam(value = "hasta") String hasta, @RequestParam(value = "opcion") String opcion,@RequestParam(value = "usuario") String usuario, HttpServletRequest request, HttpServletResponse response, Model modelo)
            throws ServletException, IOException, ParseException {
        
        List<LogsWeb> resultado;
        String opcionSeleccionada;
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuarioLogin = (List<Usuario>) misession.getAttribute("usuario.datos");
        java.sql.Timestamp fechaDesdesql = Utilidades.convierteFechaSql(desde);
        java.sql.Timestamp fechaHastasql = Utilidades.convierteFechaSql(hasta);  
       
        if("".equals(opcion)){
            opcionSeleccionada = "%";
        }else{
            opcionSeleccionada = opcion;
        }
        
        try{
            
            if(usuario.isEmpty()){
                resultado = bitacora.getLogWebList(fechaDesdesql,fechaHastasql,opcionSeleccionada);
            }else{
                resultado = bitacora.getLogsWebListByUsuario(fechaDesdesql,fechaHastasql,opcionSeleccionada,Utilidades.Encriptar(usuario));
            } 

            //Valida si existen registros con los parametros seleccionados
            if(resultado.isEmpty()){            
                    modelo.addAttribute("messageError", "No se encontraron registros con los parámetros seleccionados");
                    return new ModelAndView("bitacoraSitioWeb","model",modelo);
            }else{                  
                    return new ModelAndView("bitacoraSitioWeb","model",resultado);        
            }
        
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 1, 26, "No se ha podido realizar la consulta debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("bitacoraSitioWeb");
        }                        
        
    }
}
