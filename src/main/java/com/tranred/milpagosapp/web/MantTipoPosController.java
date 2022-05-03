/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tranred.milpagosapp.web;
import com.tranred.milpagosapp.domain.TipoPos;
import com.tranred.milpagosapp.domain.Moneda;
import com.tranred.milpagosapp.domain.Usuario;
import com.tranred.milpagosapp.repository.JPAMonedaDAO;
import com.tranred.milpagosapp.repository.JPAPosDAO;
import com.tranred.milpagosapp.service.IAdminMantenimiento;
import com.tranred.milpagosapp.service.IBitacoraService;
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
 * Clase actua como controlador para las opciones Editar y Eliminar Tipo Pos.
 * @author jperez@emsys-solution.com
 * @version 0.1
 */

@Controller
public class MantTipoPosController {
    
    protected final Log logger = LogFactory.getLog(getClass());
    
  
    @Autowired
    private IAdminMantenimiento mantenimientoAdmin;
    
    @Autowired
    private IBitacoraService bitacora;
    
    @Autowired
    private JPAMonedaDAO moneda;
  
     
    
    //Asignamos el estado inicial de los formularios con las propiedades de cada clase
    @ModelAttribute("crearTipoPos")
    public TipoPos createModelCrear() {
        return new TipoPos();
    }
    
    @ModelAttribute("editarTipoPos")
    public TipoPos  createModelEditar() {
        return new TipoPos();
    }
    
    @ModelAttribute("eliminarTipoPOs")
    public TipoPos createModelPlan() {
        return new TipoPos();
    }
    
    @RequestMapping(value="/crearTipoPos.htm", method = RequestMethod.GET)
    protected ModelAndView crearTipoPos(HttpServletRequest request, Model modelo) throws ServletException {
                
        TipoPos crearTipoPos = new TipoPos();
        
        return new ModelAndView("crearTipoPos", "crearTipoPos", crearTipoPos);
                            
    }
    
    @ModelAttribute("MonedaList")
    public List MonedaList() {
        
        List<Moneda> MonedaList = null;
        
        try{
            MonedaList = moneda.getMonedaList();
        }catch (Exception cve) {                                   
            logger.error(cve.getMessage());            
        }
                                     		         
        return MonedaList;
    }
    
    @RequestMapping(value="/listadoTipoPos.htm")
    public ModelAndView listadoTipoPos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Map<String, Object> myModel = new HashMap<String, Object>();        
        myModel.put("listadoTipoPos", this.mantenimientoAdmin.getTipoPosList());
        
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "Mantenimiento Tipo Pos", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
        
        return new ModelAndView("listadoTipoPos", "model", myModel);
        
    }
    
        
    @RequestMapping(value="/editarTipoPos.htm", method = RequestMethod.GET)
    protected ModelAndView editarTipoPos(@RequestParam(value = "id") int id,HttpServletRequest request, Model modelo) throws ServletException {
                
        TipoPos editarTipoPos= new TipoPos();
        TipoPos resultado = new TipoPos();
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        try{
            
            resultado = mantenimientoAdmin.getTipoPosById(id);            

            bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "Ingreso Mantenimiento Editar Tipo Pos", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");

            editarTipoPos.setId(resultado.getId());
            editarTipoPos.setDescripcion(resultado.getDescripcion());
            editarTipoPos.setCosto(resultado.getCosto());
            editarTipoPos.setMoneda(resultado.getMoneda());
            return new ModelAndView("editarTipoPos", "editarTipoPos", editarTipoPos);
            
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("editarTipoPos", "model", modelo);
        }        
    }
    
     @RequestMapping(value="/eliminarTipoPos.htm", method = RequestMethod.GET)
    protected ModelAndView eliminarTipoPos(@RequestParam(value = "id") int id,HttpServletRequest request, Model modelo) throws ServletException {
                
        TipoPos eliminarTipoPos= new TipoPos();
        TipoPos resultado = new TipoPos();
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        try{
            
            resultado = mantenimientoAdmin.getTipoPosById(id);            

            bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "Ingreso Mantenimiento Eliminar Tipo Pos", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");

            eliminarTipoPos.setId(resultado.getId());
            eliminarTipoPos.setDescripcion(resultado.getDescripcion());
            eliminarTipoPos.setCosto(resultado.getCosto());
            eliminarTipoPos.setMoneda(resultado.getMoneda());
            return new ModelAndView("eliminarTipoPos", "eliminarTipoPos", eliminarTipoPos);
            
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("eliminarTipoPos", "model", modelo);
        }        
    }
    
     //Fin estado incial de los formularios
    
    //Accion post de los formularios
    @RequestMapping(value="/crearTipoPos.htm", method = RequestMethod.POST)
    public ModelAndView crearTipoPosSubmit(@ModelAttribute("crearTipoPos") @Valid TipoPos crearTipoPos, BindingResult result, Model modelo, HttpServletRequest request)
    {
        
        if (result.hasErrors()) {
            return new ModelAndView("crearTipoPos");
        }                
                
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        try{
            
           
            
            
            bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "Crear Tipo Pos ID: "+ crearTipoPos.getId() +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
        
            //Guarda los datos ingresados por el usuario
            mantenimientoAdmin.saveTipoPos(crearTipoPos);

            Map<String, Object> myModel = new HashMap<>();        
            myModel.put("listadoTipoPos", this.mantenimientoAdmin.getTipoPosList());
            myModel.put("messageError", "Registro Creado Satisfactoriamente");

            return new ModelAndView("listadoTipoPos", "model", myModel);  
        
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("crearTipoPos", "model", modelo);
        }        
        
    }
  
    
    @RequestMapping(value="/editarTipoPos.htm", method = RequestMethod.POST)
    public ModelAndView editarTipoPosSubmit(@ModelAttribute("editarTipoPos") @Valid TipoPos editarTipoPos, BindingResult result, Model modelo, HttpServletRequest request)
    {
        
        if (result.hasErrors()) {
            return new ModelAndView("editarTipoPos");
        }                               
                      
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        String oldValue, newValue;
        
        try{                                    
        
            //Guarda los datos modificados por el usuario
            oldValue = ToStringBuilder.reflectionToString(mantenimientoAdmin.getTipoPosById(editarTipoPos.getId()));
            mantenimientoAdmin.updateTipoPos(editarTipoPos);
            newValue = ToStringBuilder.reflectionToString(mantenimientoAdmin.getTipoPosById(editarTipoPos.getId()));
            bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "Mantenimiento Editar Tipo Pos ID: "+ editarTipoPos.getId() +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), oldValue, newValue);

            Map<String, Object> myModel = new HashMap<>();        
            myModel.put("listadoTipoPos", this.mantenimientoAdmin.getTipoPosList());
            myModel.put("messageError", "Registro Actualizado Satisfactoriamente");

            return new ModelAndView("listadoTipoPos", "model", myModel);  
        
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("editarTipoPos", "model", modelo);
        }        
        
    }
    
  
    
    @RequestMapping(value="/eliminarTipoPos.htm", method = RequestMethod.POST)
    public ModelAndView eliminarTipoPosSubmit(@ModelAttribute("eliminarTipoPos") @Valid TipoPos eliminarTipoPos, BindingResult result, Model modelo, HttpServletRequest request)
    {
        
        if (result.hasErrors()) {
            return new ModelAndView("eliminarTipoPos");
        }                
        
        TipoPos resultado = new TipoPos();
        resultado.setId(eliminarTipoPos.getId());
        resultado.setDescripcion(eliminarTipoPos.getDescripcion());   
        resultado.setCosto(eliminarTipoPos.getCosto());
        resultado.setMoneda(eliminarTipoPos.getMoneda());
        
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        try{
            
            bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "Mantenimiento Eliminar TipoPos ID: "+ eliminarTipoPos.getId() +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
        
            //Eliminar el pos seleccionado por el usuario
            mantenimientoAdmin.removeTipoPos(resultado);

            Map<String, Object> myModel = new HashMap<>();        
            myModel.put("listadoTipoPos", this.mantenimientoAdmin.getTipoPosList());
            myModel.put("messageError", "Registro Eliminado Satisfactoriamente");

            return new ModelAndView("listadoTipoPos", "model", myModel);
        
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("eliminarTipoPos", "model", modelo);
        }        
        
    }
}