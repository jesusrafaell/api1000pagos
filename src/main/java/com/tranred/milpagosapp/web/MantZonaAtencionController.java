package com.tranred.milpagosapp.web;

import com.tranred.milpagosapp.domain.Usuario;
import com.tranred.milpagosapp.domain.ZonaAtencion;
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
 * Clase actua como controlador para las opciones Editar y Eliminar Zonas Atencion Comercial.
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */

@Controller
public class MantZonaAtencionController {
    
    protected final Log logger = LogFactory.getLog(getClass());
    
    @Autowired
    private IAdminMantenimiento mantenimientoAdmin;
    
    @Autowired
    private IBitacoraService bitacora;
    
    //Asignamos el estado inicial de los formularios con las propiedades de la clase ZonaAtencion
    @ModelAttribute("editarZonaAtencion")
    public ZonaAtencion createModelEditar() {
        return new ZonaAtencion();
    }
    
    @ModelAttribute("eliminarZonaAtencion")
    public ZonaAtencion createModelBanco() {
        return new ZonaAtencion();
    }
    
    @RequestMapping(value="/editarZonaAtencion.htm", method = RequestMethod.GET)
    protected ModelAndView editarBanco(@RequestParam(value = "idZona") String id,HttpServletRequest request, Model modelo) throws ServletException {
                
        ZonaAtencion editarZona = new ZonaAtencion();
        ZonaAtencion resultado = new ZonaAtencion();
        resultado = mantenimientoAdmin.getZonaById(id);
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "Ingreso Mantenimiento Editar Zona Atención Comercial", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
        
        editarZona.setId(resultado.getId());
        editarZona.setDescripcion(resultado.getDescripcion());
        return new ModelAndView("editarZonaAtencion", "editarZonaAtencion", editarZona);
    }
    
    @RequestMapping(value="/eliminarZonaAtencion.htm", method = RequestMethod.GET)
    protected ModelAndView eliminarBanco(@RequestParam(value = "idZona") String id,HttpServletRequest request, Model modelo) throws ServletException {
                
        ZonaAtencion eliminarZona = new ZonaAtencion();
        ZonaAtencion resultado = new ZonaAtencion();
        resultado = mantenimientoAdmin.getZonaById(id);
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "Ingreso Mantenimiento Eliminar Banco", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
        
        eliminarZona.setId(resultado.getId());
        eliminarZona.setDescripcion(resultado.getDescripcion());
        
        return new ModelAndView("eliminarZonaAtencion", "eliminarZonaAtencion", eliminarZona);
    }
    //Fin estado incial de los formularios
    
    //Accion post de los formularios
    @RequestMapping(value="/editarZonaAtencion.htm", method = RequestMethod.POST)
    public ModelAndView onSubmitEditar(@ModelAttribute("editarZonaAtencion") @Valid ZonaAtencion editarZonaAtencion, BindingResult result, Model modelo, HttpServletRequest request)
    {
        
        if (result.hasErrors()) {
            return new ModelAndView("editarZonaAtencion");
        }                
        
        ZonaAtencion resultado = new ZonaAtencion();
        resultado.setId(editarZonaAtencion.getId());
        resultado.setDescripcion(editarZonaAtencion.getDescripcion());        
        
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");                
        String oldValue, newValue;
        
        //Guarda los datos modificados por el usuario
        oldValue = ToStringBuilder.reflectionToString(mantenimientoAdmin.getZonaById(resultado.getId()));
        mantenimientoAdmin.updateZonaAtencion(resultado);
        newValue = ToStringBuilder.reflectionToString(mantenimientoAdmin.getZonaById(resultado.getId()));
        bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "Mantenimiento Editar Zona Atención ID: "+ editarZonaAtencion.getId() +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), oldValue, newValue);
        
        Map<String, Object> myModel = new HashMap<String, Object>();        
        myModel.put("listadoZonasAtencion", this.mantenimientoAdmin.getZonasAtencionList());
        myModel.put("messageError", "Registro Actualizado Satisfactoriamente");
        
        return new ModelAndView("listadoZonasAtencion", "model", myModel);  
        
    }
    
    @RequestMapping(value="/eliminarZonaAtencion.htm", method = RequestMethod.POST)
    public ModelAndView onSubmitEliminar(@ModelAttribute("eliminarZonaAtencion") @Valid ZonaAtencion eliminarZonaAtencion, BindingResult result, Model modelo, HttpServletRequest request)
    {
        
        if (result.hasErrors()) {
            return new ModelAndView("eliminarZonaAtencion");
        }                
        
        ZonaAtencion resultado = new ZonaAtencion();
        resultado.setId(eliminarZonaAtencion.getId());
        resultado.setDescripcion(eliminarZonaAtencion.getDescripcion());      
        
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "Mantenimiento Eliminar Zona Atención ID: "+ eliminarZonaAtencion.getId() +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
        
        //Eliminar la zona de atención seleccionado por el usuario
        mantenimientoAdmin.removeZonaAtencion(resultado);
        
        Map<String, Object> myModel = new HashMap<String, Object>();        
        myModel.put("listadoZonasAtencion", this.mantenimientoAdmin.getZonasAtencionList());
        myModel.put("messageError", "Registro Eliminado Satisfactoriamente");
        
        return new ModelAndView("listadoZonasAtencion", "model", myModel);  
        
    }
}
