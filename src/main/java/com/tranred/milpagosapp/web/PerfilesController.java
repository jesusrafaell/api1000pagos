package com.tranred.milpagosapp.web;

import com.tranred.milpagosapp.domain.Opcion;
import com.tranred.milpagosapp.domain.Perfil;
import com.tranred.milpagosapp.domain.Usuario;
import com.tranred.milpagosapp.service.IBitacoraService;
import com.tranred.milpagosapp.service.IPerfilesService;
import com.tranred.milpagosapp.util.Utilidades;
import java.io.IOException;
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

/**
 *  Clase actua como controlador para el módulo de administración opción Accesos/Perfiles.
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 * 
 */

@Controller
public class PerfilesController {
    
    protected final Log logger = LogFactory.getLog(getClass());
    
    @Autowired
    private IPerfilesService perfilesService;
    
    @Autowired
    private IBitacoraService bitacora;
    
    //Asignamos el estado inicial del formulario con las propiedades de la clase Perfil
    @ModelAttribute("crearPerfil")
    public Perfil createModelEditar() {
        return new Perfil();
    }
            
    @RequestMapping(value="/crearPerfil.htm", method = RequestMethod.GET)
    protected Perfil crearPerfil(HttpServletRequest request, Model modelo) throws ServletException {
                
        Perfil crearPerfil = new Perfil();        
        return crearPerfil;
    }        
    //Fin estado inicial
    
    //Accion post del formulario Crear Perfil
    @RequestMapping(value="/crearPerfil.htm", method = RequestMethod.POST)
    public ModelAndView crearPerfilSubmit(@ModelAttribute("crearPerfil") @Valid Perfil crearPerfil, BindingResult result, Model modelo, HttpServletRequest request)
    {                
        if (result.hasErrors()) {
            return new ModelAndView("crearPerfil");
        }
        
        Map<String, Object> myModel = new HashMap<String, Object>();
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        try {
            
            perfilesService.savePerfil(crearPerfil);                    
            bitacora.saveLogs(usuario.get(0).getId(), 1, 1, "Crea Perfil "+ crearPerfil.getNombre() +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
            modelo.addAttribute("messageError", "Perfil creado Satisfactoriamente");
            modelo.addAttribute("perfilesActivos", this.perfilesService.getPerfilesActivosList());
            modelo.addAttribute("listadoPerfiles", this.perfilesService.getPerfilesList());            
            return new ModelAndView("listadoPerfiles","model", modelo); 
            
        } catch (javax.persistence.PersistenceException cve) {                                                
             bitacora.saveLogs(usuario.get(0).getId(), 1, 1, "No se ha podido insertar el registro debido al siguiente error: El Perfil con nombre "+ crearPerfil.getNombre() +" ya existe.", Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
             logger.error(cve.getMessage());
             myModel.put("messageError", "No se ha podido insertar el registro debido al siguiente error: El Perfil con nombre "+ crearPerfil.getNombre() +" ya existe.");
             return new ModelAndView("crearPerfil", "model", myModel);           
        } catch (org.hibernate.exception.ConstraintViolationException cve) {             
             bitacora.saveLogs(usuario.get(0).getId(), 1, 1, "No se ha podido insertar el registro debido al siguiente error: El Perfil con nombre "+ crearPerfil.getNombre() +" ya existe.", Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
             logger.error(cve.getMessage());
             myModel.put("messageError", "No se ha podido insertar el registro debido al siguiente error: El Perfil con nombre "+ crearPerfil.getNombre() +" ya existe.");
             return new ModelAndView("crearPerfil", "model", myModel);             
        } catch (Exception cve) {            
            bitacora.saveLogs(usuario.get(0).getId(), 1, 1, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("crearPerfil", "model", modelo);
        }                                     
     
    }
    
    //Listado de Perfiles
    @RequestMapping(value="/listadoPerfiles.htm")
    public ModelAndView listadoPerfiles(HttpServletRequest request, HttpServletResponse response, Model modelo)
            throws ServletException, IOException {
        
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        try{
            
            bitacora.saveLogs(usuario.get(0).getId(), 1, 2, "Consulta Perfiles", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
        
            modelo.addAttribute("perfilesActivos", this.perfilesService.getPerfilesActivosList());
            modelo.addAttribute("listadoPerfiles", this.perfilesService.getPerfilesList());
            return new ModelAndView("listadoPerfiles","model", modelo);
        
        }catch (Exception cve) { 
            
            bitacora.saveLogs(usuario.get(0).getId(), 1, 2, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("listadoPerfiles", "model", modelo);
        }                    
        
    }
    
    //Editar un perfil
    @RequestMapping(value="/editarPerfil.htm", method = RequestMethod.GET)
    protected ModelAndView editarPerfil(@RequestParam(value = "idPerfil") String id,HttpServletRequest request, Model modelo) throws ServletException {
                
        Perfil editarPerfil = new Perfil();
        Perfil resultado = new Perfil();
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        try{
            
            bitacora.saveLogs(usuario.get(0).getId(), 1, 2, "Consulta Perfil ID: "+ id +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
                
            resultado = perfilesService.getById(Integer.parseInt(id));

            editarPerfil.setId(resultado.getId());        
            editarPerfil.setNombre(resultado.getNombre());        
            editarPerfil.setOpciones(resultado.getOpciones());
            editarPerfil.setEstatus(resultado.getEstatus());
            return new ModelAndView("editarPerfil", "editarPerfil", editarPerfil);
        
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuario.get(0).getId(), 1, 2, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("editarPerfil", "model", modelo);
        }
        
    }
    
    //Accion post del formulario Editar Perfil
    @RequestMapping(value="/editarPerfil.htm", method = RequestMethod.POST)
    public ModelAndView editarSubmit(@ModelAttribute("editarPerfil") @Valid Perfil editarPerfil, BindingResult result, Model modelo, HttpServletRequest request)
    {                
        if (result.hasErrors()) {
            return new ModelAndView("editarPerfil");
        }
                
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");        
        String oldValue, newValue;
        
        try {
                        
            oldValue = ToStringBuilder.reflectionToString(perfilesService.getById(editarPerfil.getId()));
            perfilesService.updatePerfil(editarPerfil);
            newValue = ToStringBuilder.reflectionToString(perfilesService.getById(editarPerfil.getId()));
            bitacora.saveLogs(usuario.get(0).getId(), 1, 2, "Modifica Perfil ID: "+ editarPerfil.getNombre() +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), oldValue, newValue);        
            
            modelo.addAttribute("messageError", "Perfil modificado Satisfactoriamente");
            modelo.addAttribute("perfilesActivos", this.perfilesService.getPerfilesActivosList());
            modelo.addAttribute("listadoPerfiles", this.perfilesService.getPerfilesList());
            return new ModelAndView("listadoPerfiles","model", modelo); 
            
        } catch (Exception cve) {
            
            bitacora.saveLogs(usuario.get(0).getId(), 1, 2, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("editarPerfil", "model", modelo);
        }                                       
     
    }
    
    //Eliminar perfil
    @RequestMapping(value="/eliminarPerfil.htm", method = RequestMethod.GET)
    protected ModelAndView eliminarPerfil(@RequestParam(value = "idPerfil") String id, HttpServletRequest request, Model modelo) throws ServletException {
                
        Perfil eliminarPerfil = new Perfil();
        Perfil resultado = new Perfil();
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        try{
           
            bitacora.saveLogs(usuario.get(0).getId(), 1, 2, "Consulta Perfil ID: "+ id +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
        
            resultado = perfilesService.getById(Integer.parseInt(id));            
            eliminarPerfil.setId(resultado.getId());        
            eliminarPerfil.setNombre(resultado.getNombre());
            eliminarPerfil.setOpciones(resultado.getOpciones()); 
            eliminarPerfil.setEstatus(resultado.getEstatus());               
            return new ModelAndView("eliminarPerfil", "eliminarPerfil", eliminarPerfil);
        
        } catch (Exception cve) {
            
            bitacora.saveLogs(usuario.get(0).getId(), 1, 2, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("eliminarPerfil", "model", modelo);
        }
        
    }
    
    // Accion post del formulario eliminarPerfil
    @RequestMapping(value="/eliminarPerfil.htm", method = RequestMethod.POST)
    public ModelAndView eliminarPerfilSubmit(@ModelAttribute("eliminarPerfil") @Valid Perfil eliminarPerfil, BindingResult result, Model modelo, HttpServletRequest request)
    {
        
        if (result.hasErrors()) {
            return new ModelAndView("eliminarPerfil");
        }
        
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        try{
            
            bitacora.saveLogs(usuario.get(0).getId(), 1, 2, "Elimina Perfil ID: "+ eliminarPerfil.getNombre() +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
        
            //Eliminar el perfil seleccionado por el usuario
            perfilesService.removePerfil(perfilesService.getById(eliminarPerfil.getId()));
            
            modelo.addAttribute("perfilesActivos", this.perfilesService.getPerfilesActivosList());
            modelo.addAttribute("listadoPerfiles", this.perfilesService.getPerfilesList());
            modelo.addAttribute("messageError", "Registro Eliminado Satisfactoriamente");
            return new ModelAndView("listadoPerfiles","model", modelo);
        
        } catch (Exception cve) {
            
            bitacora.saveLogs(usuario.get(0).getId(), 1, 2, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("eliminarPerfil", "model", modelo);
        }                                
        
    }
    
    //Carga los valores de los checkboxes
    @ModelAttribute("opcionesAdmList")
    protected List<Opcion> opcionesAdmin(HttpServletRequest request) throws Exception {
	
	List<Opcion> opcionesAdmList = null;
        
        try{
            opcionesAdmList = perfilesService.getOpcionByModuloList("1");
        }catch (Exception cve) {                       
            logger.error(cve.getMessage());            
        }
        
	return opcionesAdmList;
    }        
    
    @ModelAttribute("opcionesPagList")
    protected List<Opcion> opcionesPagina(HttpServletRequest request) throws Exception {
	
	List<Opcion> opcionesPagList = null;
        
        try{
            opcionesPagList = perfilesService.getOpcionByModuloList("2");
        }catch (Exception cve) {                       
            logger.error(cve.getMessage());            
        }
	
	return opcionesPagList;
    }
}
