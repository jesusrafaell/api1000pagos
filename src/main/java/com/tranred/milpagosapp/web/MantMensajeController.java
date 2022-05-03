package com.tranred.milpagosapp.web;

import com.tranred.milpagosapp.domain.Mensaje;
import com.tranred.milpagosapp.domain.Usuario;
import com.tranred.milpagosapp.service.IAdminMantenimiento;
import com.tranred.milpagosapp.service.IBitacoraService;
import com.tranred.milpagosapp.util.Utilidades;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
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
 * Clase actua como controlador para las opciones Editar un Mensaje.
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */

@Controller
public class MantMensajeController {
    
    protected final Log logger = LogFactory.getLog(getClass());
    
    @Autowired
    private IAdminMantenimiento mantenimientoAdmin;
    
    @Autowired
    private IBitacoraService bitacora;
    
    //Asignamos el estado inicial de los formularios con las propiedades de cada clase    
    @ModelAttribute("editarMensaje")
    public Mensaje createModelEditar() {
        return new Mensaje();
    }          
    
    @RequestMapping(value="/editarMensaje.htm", method = RequestMethod.GET)
    protected ModelAndView editarMensaje(@RequestParam(value = "idMensaje") int id,HttpServletRequest request, Model modelo) throws ServletException {
                
        Mensaje editarMensaje = new Mensaje();       
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        try{
            
            editarMensaje = mantenimientoAdmin.getMensajeById(id);            

            bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "Ingreso Mantenimiento Editar Mensaje", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
           
            return new ModelAndView("editarMensaje", "editarMensaje", editarMensaje);
            
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("editarMensaje", "model", modelo);
        }        
    }       
    //Fin estado incial de los formularios
    
    //Accion post de los formularios   
    @RequestMapping(value="/editarMensaje.htm", method = RequestMethod.POST)
    public ModelAndView editarMensajeSubmit(@ModelAttribute("editarMensaje") @Valid Mensaje editarMensaje, BindingResult result, Model modelo, HttpServletRequest request)
    {
        
        if (result.hasErrors()) {
            return new ModelAndView("editarMensaje");
        }                               
                      
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        String oldValue, newValue;
        
        try{                        
        
            //Guarda los datos modificados por el usuario
            oldValue = ToStringBuilder.reflectionToString(mantenimientoAdmin.getMensajeById(editarMensaje.getCodigoMensaje()));
            mantenimientoAdmin.updateMensaje(editarMensaje);
            newValue = ToStringBuilder.reflectionToString(mantenimientoAdmin.getMensajeById(editarMensaje.getCodigoMensaje()));
            bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "Mantenimiento Editar Mensaje ID: "+ editarMensaje.getCodigoMensaje() +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), oldValue, newValue);
            
            Map<String, Object> myModel = new HashMap<>();        
            myModel.put("listadoMensajes", this.mantenimientoAdmin.getMensajesList());
            myModel.put("messageError", "Registro Actualizado Satisfactoriamente");

            return new ModelAndView("listadoMensajes", "model", myModel);  
        
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("editarMensaje", "model", modelo);
        }        
        
    }
       
}
