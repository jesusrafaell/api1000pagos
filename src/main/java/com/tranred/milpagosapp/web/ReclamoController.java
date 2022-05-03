package com.tranred.milpagosapp.web;

import com.tranred.milpagosapp.domain.Estatus;
import com.tranred.milpagosapp.domain.Logs;
import com.tranred.milpagosapp.domain.Reclamo;
import com.tranred.milpagosapp.domain.TipoReclamo;
import com.tranred.milpagosapp.domain.Usuario;
import com.tranred.milpagosapp.repository.IEstatusDAO;
import com.tranred.milpagosapp.service.BitacoraForm;
import com.tranred.milpagosapp.service.ConsultaReclamoForm;
import com.tranred.milpagosapp.service.IBitacoraService;
import com.tranred.milpagosapp.service.IReclamoService;
import com.tranred.milpagosapp.util.Utilidades;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 *  Clase que actua como controlador para la opción de reclamos.
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 * 
 */

@Controller
public class ReclamoController{    
    
    protected final Log logger = LogFactory.getLog(getClass());
    
    @Autowired
    private IReclamoService reclamoService;
        
    @Autowired
    private IBitacoraService bitacora;                       
    
    @Autowired
    private IEstatusDAO estatusDAO;
    
    private final Date ahora = new Date();
    
    @ModelAttribute("crearReclamo")
    public Reclamo createModelCrear() {
        return new Reclamo();
    }
    
    @ModelAttribute("editarReclamo")
    public Reclamo createModelEditar() {
        return new Reclamo();
    }
    
    @ModelAttribute("consultaReclamos")
    public ConsultaReclamoForm createModelConsultar() {
        return new ConsultaReclamoForm();
    }
    
    /* Estado inicial del formulario Crear Reclamo */
    @RequestMapping(value="/crearReclamo.htm", method = RequestMethod.GET)
    protected ModelAndView crearReclamo(@RequestParam(value = "messageError") String messageError, HttpServletRequest request, Model modelo) throws ServletException {
        
        Map<String, Object> myModel = new HashMap<>();
        
        if("0".equals(messageError)){
            messageError = "Reclamo Creado Satisfactoriamente";
        }else{
            messageError = "";
        }
                        
        myModel.put("messageError", messageError);
        return new ModelAndView("crearReclamo", "model", myModel);
        
    }    
    
    /* Estado inicial del formulario Editar Reclamo */
    @RequestMapping(value="/editarReclamo.htm", method = RequestMethod.GET)
    protected ModelAndView editarReclamo(@RequestParam(value = "idReclamo") Integer idReclamo, HttpServletRequest request, Model modelo) throws ServletException, Exception {
                        
        Reclamo editarReclamo;
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuarioLogin = (List<Usuario>) misession.getAttribute("usuario.datos");       
        int diasRespuesta = 0;
        
        try{
            
            editarReclamo = reclamoService.getById(idReclamo);
        
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 19, "Consulta Reclamo Número: "+ editarReclamo.getId() +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
            diasRespuesta = editarReclamo.getCodTipoReclamo().getTiempoRespuesta() - (Utilidades.diferenciasDeFechas(editarReclamo.getFechaRecepcion(), ahora));

            if(diasRespuesta<0){
                diasRespuesta = 0;
            }
            editarReclamo.setFechaTransaccion(Utilidades.cambiaFormatoFecha2(editarReclamo.getFechaTransaccion()));
            editarReclamo.setDiasRespuesta(diasRespuesta);
            return new ModelAndView("editarReclamo", "editarReclamo", editarReclamo);
            
        }catch (Exception cve) { 
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 19, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("editarReclamo", "model", modelo);        
        }        
    }
    
    @RequestMapping(value="/consultaReclamos.htm", method = RequestMethod.GET)
    protected ModelAndView consultaReclamos(@RequestParam(value = "messageError") String messageError,HttpServletRequest request, Model modelo) throws ServletException {
        
        Map<String, Object> myModel = new HashMap<>();
        
        if("0".equals(messageError)){
            messageError = "Reclamo Actualizado Satisfactoriamente";
        }else{
            messageError = "";
        }
                        
        myModel.put("messageError", messageError);
        return new ModelAndView("consultaReclamos", "model", myModel);
                
    } 
    
    //Accion post del formulario Crear Reclamo
    @RequestMapping(value="/crearReclamo.htm", method = RequestMethod.POST)
    public ModelAndView crearSubmit(@ModelAttribute("crearReclamo") @Valid Reclamo crearReclamo, BindingResult result, Model modelo, HttpServletRequest request) throws ParseException
    {             
        
        if (result.hasErrors()) {
            return new ModelAndView("crearReclamo");
        }
        
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuarioLogin = (List<Usuario>) misession.getAttribute("usuario.datos");       
        Map<String, Object> myModel = new HashMap<>();       
        
        try {
            crearReclamo.setCodigoEstatus(11);
            crearReclamo.setFechaRecepcion(Utilidades.getFechaActualSql());
            reclamoService.saveReclamo(crearReclamo);
            int numeroReclamo = reclamoService.getNumeroReclamo();
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 19, "Crea Reclamo: "+ numeroReclamo +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
            return new ModelAndView(new RedirectView("crearReclamo.htm?messageError=0"));
            
        } catch (javax.persistence.PersistenceException cve) {                                                
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 19, "No se ha podido insertar el registro debido al siguiente error: " + cve.getMessage() , Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());
            myModel.put("messageError", "No se ha podido insertar el registro debido al siguiente error: " + cve.getMessage());
            return new ModelAndView("crearReclamo", "model", myModel);           
        } catch (org.hibernate.exception.ConstraintViolationException cve) {             
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 19, "No se ha podido insertar el registro debido al siguiente error: " + cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());
            myModel.put("messageError", "No se ha podido insertar el registro debido al siguiente error: " + cve.getMessage());
            return new ModelAndView("crearReclamo", "model", myModel);             
        } catch (Exception cve) {             
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 19, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("crearReclamo", "model", modelo);        
        }                                       
        
    }
    
    //Accion post del formulario Consulta Reclamo
    @RequestMapping(value="/consultaReclamos.htm", method = RequestMethod.POST)
    public ModelAndView consultaReclamosSubmit(@ModelAttribute("consultaReclamos") @Valid ConsultaReclamoForm consultaReclamos, BindingResult result, Model modelo,HttpServletRequest request) throws ParseException
    {
        
        if (result.hasErrors()) {
            return new ModelAndView("consultaReclamos");
        }
       
        String tipo;    
        List<Reclamo> resultado;        
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        try{
            
            bitacora.saveLogs(usuario.get(0).getId(), 2, 19, "Consulta Reclamo", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
        
            java.sql.Timestamp fechaDesdesql = Utilidades.convierteFechaSql(consultaReclamos.getFechaDesde());
            java.sql.Timestamp fechaHastasql = Utilidades.convierteFechaSql(consultaReclamos.getFechaHasta());  

            if("%".equals(consultaReclamos.getTipoReclamo())){
                resultado = reclamoService.getReclamosList(fechaDesdesql,fechaHastasql);
                tipo = "";
            }else{
                resultado = reclamoService.getReclamosListByTipo(fechaDesdesql,fechaHastasql,consultaReclamos.getTipoReclamo());
                tipo = consultaReclamos.getTipoReclamo();
            }                        

            //Valida si existen registros con los parametros seleccionados
            if(resultado.isEmpty()){            
                modelo.addAttribute("messageError", "No se encontraron registros con los parámetros seleccionados");
                return new ModelAndView("consultaReclamos","model",modelo);

            }else{                  
                return new ModelAndView(new RedirectView("listadoReclamos.htm?desde="+ consultaReclamos.getFechaDesde() +"&hasta="+ consultaReclamos.getFechaHasta() + "&tipo="+ tipo +""));
            }   
        
        } catch (Exception cve) {
            
            bitacora.saveLogs(usuario.get(0).getId(), 2, 19, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("consultaReclamos", "model", modelo);        
        }                          
        
    }        
    
    //Accion post del formulario Editar Reclamo
    @RequestMapping(value="/editarReclamo.htm", method = RequestMethod.POST)
    public ModelAndView editarSubmit(@ModelAttribute("editarReclamo") @Valid Reclamo editarReclamo, BindingResult result, Model modelo, HttpServletRequest request) throws ParseException
    {             
        
        if (result.hasErrors()) {
            return new ModelAndView("editarReclamo");
        }
        
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuarioLogin = (List<Usuario>) misession.getAttribute("usuario.datos");                      
        String oldValue, newValue;
        
        try {
            
            oldValue = ToStringBuilder.reflectionToString(reclamoService.getById(editarReclamo.getId()));
            reclamoService.updateReclamo(editarReclamo);
            newValue = ToStringBuilder.reflectionToString(reclamoService.getById(editarReclamo.getId()));
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 19, "Edita Reclamo: "+ editarReclamo.getId() +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), oldValue, newValue);
            
            return new ModelAndView(new RedirectView("consultaReclamos.htm?messageError=0"));
            
        } catch (Exception cve) {
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 19, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("editarReclamo", "model", modelo);        
        }                        
        
    }
    
    //Listado de Reclamos
    @RequestMapping(value="/listadoReclamos.htm", method = RequestMethod.GET)
    protected ModelAndView listadoReclamos(@RequestParam(value = "desde") String desde, @RequestParam(value = "hasta") String hasta, @RequestParam(value = "tipo") String tipo, HttpServletRequest request, HttpServletResponse response, Model modelo)
            throws ServletException, IOException, ParseException {                
        
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        try{
            
            bitacora.saveLogs(usuario.get(0).getId(), 2, 19, "Consulta Reclamos", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");        
            List<Reclamo> resultado;                

            java.sql.Timestamp fechaDesdesql = Utilidades.convierteFechaSql(desde);
            java.sql.Timestamp fechaHastasql = Utilidades.convierteFechaSql(hasta); 

            if("".equals(tipo)){
                resultado = reclamoService.getReclamosList(fechaDesdesql,fechaHastasql);
            }else{
                resultado = reclamoService.getReclamosListByTipo(fechaDesdesql,fechaHastasql,tipo);
            }                                                       

            modelo.addAttribute("listadoReclamos", resultado);        
            return new ModelAndView("listadoReclamos","model", modelo);
        
        } catch (Exception cve) {
            
            bitacora.saveLogs(usuario.get(0).getId(), 2, 19, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("listadoReclamos", "model", modelo);        
        }                 
        
    }
    
    @ModelAttribute("tipoReclamoList")
    protected List<TipoReclamo> tipoReclamoList(HttpServletRequest request) throws Exception {
	
	List<TipoReclamo> tipoReclamoList = null;
        
        try{
            tipoReclamoList = reclamoService.getTipoReclamosList();
        }catch (Exception cve) {                       
            logger.error(cve.getMessage());                        
        }
	
	return tipoReclamoList;
    }
    
    @ModelAttribute("estatusList")
    public List estatusList() {
        
        List<Estatus> estatusList = null;
        
        try{
            estatusList = estatusDAO.getEstatusByModulo("re");
        }catch (Exception cve) {                       
            logger.error(cve.getMessage());                        
        }
                                     		         
        return estatusList;
    }
}
