package com.tranred.milpagosapp.web;

import com.tranred.milpagosapp.util.Utilidades;
import com.tranred.milpagosapp.domain.Logs;
import com.tranred.milpagosapp.domain.LogsWeb;
import com.tranred.milpagosapp.domain.Modulo;
import com.tranred.milpagosapp.domain.Opcion;
import com.tranred.milpagosapp.domain.OpcionWeb;
import com.tranred.milpagosapp.domain.Usuario;
import com.tranred.milpagosapp.repository.JPAModuloDAO;
import com.tranred.milpagosapp.repository.JPAOpcionDAO;
import com.tranred.milpagosapp.service.BitacoraForm;
import com.tranred.milpagosapp.service.IBitacoraService;
import java.text.ParseException;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 *  Clase actua como controlador para el módulo de administración opción consulta bitácora.
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 * 
 */
@Controller
public class ConsultaBitacoraController {    
    
    protected final Log logger = LogFactory.getLog(getClass());
    
    private Map<String, Modulo> moduloCache;    
    
    @Autowired
    private IBitacoraService bitacoraService;
    
    @Autowired
    private JPAModuloDAO moduloDao;
    
    @Autowired
    private JPAOpcionDAO opcionDao;
    
    @Autowired
    private IBitacoraService bitacora;
    
    //Asignamos el estado inicial del formulario con las propiedasdes de la clase BitacoraForm (Servicio)
    @ModelAttribute("consultaBitacora")
    public BitacoraForm createModel() {
        return new BitacoraForm();
    }
            
    @RequestMapping(value="/consultaBitacora.htm", method = RequestMethod.GET)
    protected BitacoraForm consultaBitacora(HttpServletRequest request, Model modelo) throws ServletException {
        
        BitacoraForm consultaBitacora = new BitacoraForm();        
        return consultaBitacora;
    }         
    
    //Consulta del Log del Sitio Web
    @ModelAttribute("consultaBitacoraWeb")
    public BitacoraForm createModelWeb() {
        return new BitacoraForm();
    }
            
    @RequestMapping(value="/consultaBitacoraWeb.htm", method = RequestMethod.GET)
    protected BitacoraForm consultaBitacoraWeb(HttpServletRequest request, Model modelo) throws ServletException {
        
        BitacoraForm consultaBitacoraWeb = new BitacoraForm();        
        return consultaBitacoraWeb;
    }
    
    /*Se utiliza @Initbinder para evitar los errores de type mismatch al momento de validar
    * las opciones de lista (módulo y opción) con el BindingResult    
    */
    @InitBinder
    protected void initBinder(WebDataBinder binder) throws Exception {
	binder.registerCustomEditor(Set.class, "modulo", new CustomCollectionEditor(Set.class) {
            @Override
            protected Object convertElement(Object element) {
                if (element instanceof Modulo) {                            
                    return element;
                }
                if (element instanceof String) {
                    Modulo modulos = moduloCache.get(element);				
                    return modulos;
                }			
                return null;
            }
	});
    }        
    //Fin estado incial del formulario
    
    //Accion post del formulario
    @RequestMapping(value="/consultaBitacora.htm", method = RequestMethod.POST)
    public ModelAndView consultaBitacoraSubmit(@ModelAttribute("consultaBitacora") @Valid BitacoraForm consultaBitacora, BindingResult result, Model modelo,HttpServletRequest request) throws ParseException
    {
        
        if (result.hasErrors()) {
            return new ModelAndView("consultaBitacora");
        }
       
        List<Logs> resultado;
        String opcionSeleccionada;
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        try{
            
            bitacoraService.saveLogs(usuario.get(0).getId(), 1, 5, "Consulta Bitacora", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
        
            java.sql.Timestamp fechaDesdesql = Utilidades.convierteFechaSql(consultaBitacora.getFechaDesde());
            java.sql.Timestamp fechaHastasql = Utilidades.convierteFechaSql(consultaBitacora.getFechaHasta());  

            if("%".equals(consultaBitacora.getOpcion())){
                opcionSeleccionada = "";
            }else{
                opcionSeleccionada = consultaBitacora.getOpcion();
            }

            if(consultaBitacora.getUsuario().trim().isEmpty()){
                resultado = bitacoraService.getLogsList(fechaDesdesql,fechaHastasql,Integer.parseInt(consultaBitacora.getModulo()),consultaBitacora.getOpcion());
            }else{
                resultado = bitacoraService.getLogsListByUsuario(fechaDesdesql,fechaHastasql,Integer.parseInt(consultaBitacora.getModulo()),consultaBitacora.getOpcion(),consultaBitacora.getUsuario().trim());
            }

            //Valida si existen registros con los parametros seleccionados
            if(resultado.isEmpty()){            
                    modelo.addAttribute("messageError", "No se encontraron registros con los parámetros seleccionados");
                    return new ModelAndView("consultaBitacora");
            }else{  
                    //Dependiendo del tipo de consulta (Módulo) llama al formulario correspondiente
                    if(consultaBitacora.getModulo().equals("1") || consultaBitacora.getModulo().equals("3")){
                        return new ModelAndView(new RedirectView("bitacoraAdmin.htm?desde="+ consultaBitacora.getFechaDesde() +"&hasta="+ consultaBitacora.getFechaHasta() +"&opcion="+ opcionSeleccionada +"&modulo="+ consultaBitacora.getModulo() +"&usuario="+ consultaBitacora.getUsuario().trim() +""));
                    }else {
                        return new ModelAndView(new RedirectView("bitacoraMilPagos.htm?desde="+ consultaBitacora.getFechaDesde() +"&hasta="+ consultaBitacora.getFechaHasta() +"&opcion="+ opcionSeleccionada +"&usuario="+ consultaBitacora.getUsuario().trim() +""));
                    }                
            }
            
        }catch (Exception cve) {                       
            
            bitacora.saveLogs(usuario.get(0).getId(), 2, 5, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("consultaBitacora", "model", modelo);
        }                           
        
    }
    
    //Consulta del Log del Sitio Web
    @RequestMapping(value="/consultaBitacoraWeb.htm", method = RequestMethod.POST)
    public ModelAndView consultaBitacoraWebSubmit(@ModelAttribute("consultaBitacoraWeb") @Valid BitacoraForm consultaBitacoraWeb, BindingResult result, Model modelo,HttpServletRequest request) throws ParseException
    {
        
        if (result.hasErrors()) {
            return new ModelAndView("consultaBitacoraWeb");
        }
       
        List<LogsWeb> resultado;
        String opcionSeleccionada;
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        try{
            
            bitacoraService.saveLogs(usuario.get(0).getId(), 1, 5, "Consulta Bitacora Web", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
        
            java.sql.Timestamp fechaDesdesql = Utilidades.convierteFechaSql(consultaBitacoraWeb.getFechaDesde());
            java.sql.Timestamp fechaHastasql = Utilidades.convierteFechaSql(consultaBitacoraWeb.getFechaHasta());  

            if("%".equals(consultaBitacoraWeb.getOpcion())){
                opcionSeleccionada = "";
            }else{
                opcionSeleccionada = consultaBitacoraWeb.getOpcion();
            }

            if(consultaBitacoraWeb.getUsuario().trim().isEmpty()){
                resultado = bitacoraService.getLogWebList(fechaDesdesql,fechaHastasql,consultaBitacoraWeb.getOpcion());
            }else{
                resultado = bitacoraService.getLogsWebListByUsuario(fechaDesdesql,fechaHastasql,consultaBitacoraWeb.getOpcion(),Utilidades.Encriptar(consultaBitacoraWeb.getUsuario().trim()));
            }

            //Valida si existen registros con los parametros seleccionados
            if(resultado.isEmpty()){            
                modelo.addAttribute("messageError", "No se encontraron registros con los parámetros seleccionados");
                return new ModelAndView("consultaBitacoraWeb");
            }else{  
                    
                return new ModelAndView(new RedirectView("bitacoraSitioWeb.htm?desde="+ consultaBitacoraWeb.getFechaDesde() +"&hasta="+ consultaBitacoraWeb.getFechaHasta() +"&opcion="+ opcionSeleccionada +"&usuario="+ consultaBitacoraWeb.getUsuario().trim() +""));
                                    
            }
            
        }catch (Exception cve) {                       
            
            bitacora.saveLogs(usuario.get(0).getId(), 2, 5, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("consultaBitacoraWeb", "model", modelo);
        }                           
        
    }
    
    @RequestMapping(value="/tabs.htm",method=RequestMethod.POST)    
    public String tabs(@RequestParam("modulo") String moduloId,@RequestParam("opcion") String opciones)
    {                
        return "tabs";
    }
    
    //Asigna los valores a los ListBox
    @ModelAttribute("modulo")
    public List modulo() {
        
        List<Modulo> modulo = null;
        
        try{
            
            modulo = moduloDao.getModuloList();                       
            moduloCache = new HashMap<>();
            for (Modulo modulos : modulo) {
                moduloCache.put(modulos.getIdAsString(), modulos);
            }
        
        }catch (Exception cve) {                       
            logger.error(cve.getMessage());                       
        }
        
        return modulo;                      
    }       
    
    @RequestMapping(value="/loadopciones.htm")
    public @ResponseBody Map<String,Object> getOpciones(HttpServletRequest request,HttpServletResponse response,@RequestParam(value="moduloId",required=false) String modulo)
    throws Exception{
        
        Map<String,Object> map = new HashMap<>();

        List<Opcion> opcion;
        
        try{
            opcion = opcionDao.getOpcionByModuloList(modulo);
            map.put("lstOpciones", opcion);
        }catch (Exception cve) {                       
            logger.error(cve.getMessage());                       
        }        

        return map;
    }
        
    @ModelAttribute("opcionesWeb")
    public List opcionesWeb() {
        
        List<OpcionWeb> opcionesWeb = null;
        
        try{
            opcionesWeb = opcionDao.getOpcionWebList();
        }catch (Exception cve) {                       
            logger.error(cve.getMessage());                        
        }
                                     		         
        return opcionesWeb;                      
    } 
        
}
