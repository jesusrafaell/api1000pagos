package com.tranred.milpagosapp.web;

import com.tranred.milpagosapp.domain.ModalidadPos;
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
 * Clase actua como controlador para las opciones Editar y Eliminar ModalidadPos.
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */

@Controller
public class MantPosController {
    
    protected final Log logger = LogFactory.getLog(getClass());
    
    @Autowired
    private IAdminMantenimiento mantenimientoAdmin;
    
    @Autowired
    private IBitacoraService bitacora;
    
    //Asignamos el estado inicial de los formularios con las propiedades de la clase ModalidadPosForm (servicio)    
    @ModelAttribute("crearModalidadPos")
    public ModalidadPos createModelCrear() {
        return new ModalidadPos();
    }
    
    @ModelAttribute("editarModalidadPos")
    public ModalidadPos createModelEditar() {
        return new ModalidadPos();
    }
    
    @ModelAttribute("eliminarModalidadPos")
    public ModalidadPos createModelEliminar() {
        return new ModalidadPos();
    }
    
    @RequestMapping(value="/crearModalidadPos.htm", method = RequestMethod.GET)
    protected ModelAndView crearModalidadPos(HttpServletRequest request, Model modelo) throws ServletException {
                
        ModalidadPos crearModalidadPos = new ModalidadPos();                
                
        return new ModelAndView("crearModalidadPos", "crearModalidadPos", crearModalidadPos);
    }
    
    @RequestMapping(value="/editarModalidadPos.htm", method = RequestMethod.GET)
    protected ModelAndView editarModalidadPos(@RequestParam(value = "idModalidadPos") String idModalidadPos,HttpServletRequest request, Model modelo) throws ServletException {
                
        ModalidadPos editarModalidadPos = new ModalidadPos();        
        editarModalidadPos = mantenimientoAdmin.getModalidadPosById(Integer.parseInt(idModalidadPos));
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "Ingreso Mantenimiento Editar Modalidad POS", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
                
        return new ModelAndView("editarModalidadPos", "editarModalidadPos", editarModalidadPos);
    }
    
    @RequestMapping(value="/eliminarModalidadPos.htm", method = RequestMethod.GET)
    protected ModelAndView eliminarModalidadPos(@RequestParam(value = "idModalidadPos") String idModalidadPos,HttpServletRequest request, Model modelo) throws ServletException {
                
        ModalidadPos eliminarModalidadPos = new ModalidadPos();        
        eliminarModalidadPos = mantenimientoAdmin.getModalidadPosById(Integer.parseInt(idModalidadPos));
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "Ingreso Mantenimiento Eliminar Modalidad POS", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
                
        return new ModelAndView("eliminarModalidadPos", "eliminarModalidadPos", eliminarModalidadPos);
    }
    //Fin estado incial de los formularios
    
    //Accion post de los formularios
    @RequestMapping(value="/crearModalidadPos.htm", method = RequestMethod.POST)
    public ModelAndView crearModalidadPosSubmit(@ModelAttribute("crearModalidadPos") @Valid ModalidadPos crearModalidadPos, BindingResult result, Model modelo, HttpServletRequest request)
    {
        
        if (result.hasErrors()) {
            return new ModelAndView("crearModalidadPos");
        }                                
        
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "Mantenimiento Crear Modalidad POS ID: "+ crearModalidadPos.getId() +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
        
        //Guarda los datos registrados por el usuario
        mantenimientoAdmin.saveModalidadPos(crearModalidadPos);
        
        Map<String, Object> myModel = new HashMap<String, Object>();        
        myModel.put("listadoModalidadPos", this.mantenimientoAdmin.getModalidadPosList());
        myModel.put("messageError", "Registro Creado Satisfactoriamente");
        
        return new ModelAndView("listadoModalidadPos", "model", myModel);  
        
    }
    
    @RequestMapping(value="/editarModalidadPos.htm", method = RequestMethod.POST)
    public ModelAndView editarModalidadPosSubmit(@ModelAttribute("editarModalidadPos") @Valid ModalidadPos editarModalidadPos, BindingResult result, Model modelo, HttpServletRequest request)
    {
        
        if (result.hasErrors()) {
            return new ModelAndView("editarModalidadPos");
        }                                
        
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");               
        String oldValue, newValue;
        
        //Guarda los datos modificados por el usuario
        oldValue = ToStringBuilder.reflectionToString(mantenimientoAdmin.getModalidadPosById(editarModalidadPos.getId()));
        mantenimientoAdmin.updateModalidadPos(editarModalidadPos);
        newValue = ToStringBuilder.reflectionToString(mantenimientoAdmin.getModalidadPosById(editarModalidadPos.getId()));
        bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "Mantenimiento Editar Modalidad POS ID: "+ editarModalidadPos.getId() +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), oldValue, newValue);
        
        Map<String, Object> myModel = new HashMap<String, Object>();        
        myModel.put("listadoModalidadPos", this.mantenimientoAdmin.getModalidadPosList());
        myModel.put("messageError", "Registro Actualizado Satisfactoriamente");
        
        return new ModelAndView("listadoModalidadPos", "model", myModel);  
        
    }
    
    @RequestMapping(value="/eliminarModalidadPos.htm", method = RequestMethod.POST)
    public ModelAndView eliminarModalidadPosSubmit(@ModelAttribute("eliminarModalidadPos") @Valid ModalidadPos eliminarModalidadPos, BindingResult result, Model modelo, HttpServletRequest request)
    {
        
        if (result.hasErrors()) {
            return new ModelAndView("eliminarModalidadPos");
        }                
                
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "Mantenimiento Eliminar Modalidad POS ID: "+ eliminarModalidadPos.getId() +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
        
        //Eliminar la modalidad de pos seleccionado por el usuario
        mantenimientoAdmin.removeModalidadPos(eliminarModalidadPos);
        
        Map<String, Object> myModel = new HashMap<String, Object>();        
        myModel.put("listadoModalidadPos", this.mantenimientoAdmin.getModalidadPosList());
        myModel.put("messageError", "Registro Eliminado Satisfactoriamente");
        
        return new ModelAndView("listadoModalidadPos", "model", myModel);  
        
    }
}
