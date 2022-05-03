package com.tranred.milpagosapp.web;

import com.tranred.milpagosapp.domain.Banco;
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
 * Clase actua como controlador para las opciones Editar y Eliminar TipoPosForm.
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */

@Controller
public class MantBancoController {
    
    protected final Log logger = LogFactory.getLog(getClass());
    
    @Autowired
    private IAdminMantenimiento mantenimientoAdmin;
    
    @Autowired
    private IBitacoraService bitacora;
    
    //Asignamos el estado inicial de los formularios con las propiedades de cada clase
    @ModelAttribute("crearBanco")
    public Banco createModelCrear() {
        return new Banco();
    }
    
    @ModelAttribute("editarBanco")
    public Banco createModelEditar() {
        return new Banco();
    }
    
    @ModelAttribute("eliminarBanco")
    public Banco createModelBanco() {
        return new Banco();
    }
    
    @RequestMapping(value="/crearBanco.htm", method = RequestMethod.GET)
    protected ModelAndView crearBanco(HttpServletRequest request, Model modelo) throws ServletException {
                
        Banco crearBanco = new Banco();
        
        return new ModelAndView("crearBanco", "crearBanco", crearBanco);
                            
    }
    
    @RequestMapping(value="/editarBanco.htm", method = RequestMethod.GET)
    protected ModelAndView editarBanco(@RequestParam(value = "idBanco") String id,HttpServletRequest request, Model modelo) throws ServletException {
                
        Banco editarBanco = new Banco();
        Banco resultado = new Banco();
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        try{
            
            resultado = mantenimientoAdmin.getBancoById(id);            

            bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "Ingreso Mantenimiento Editar Banco", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");

            editarBanco.setCodigo(resultado.getCodigo());
            editarBanco.setCodigoSwift(resultado.getCodigoSwift());
            editarBanco.setDescripcion(resultado.getDescripcion());
            return new ModelAndView("editarBanco", "editarBanco", editarBanco);
            
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("editarBanco", "model", modelo);
        }        
    }
    
    @RequestMapping(value="/eliminarBanco.htm", method = RequestMethod.GET)
    protected ModelAndView eliminarBanco(@RequestParam(value = "idBanco") String id,HttpServletRequest request, Model modelo) throws ServletException {
                
        Banco eliminarBanco = new Banco();
        Banco resultado = new Banco();
        resultado = mantenimientoAdmin.getBancoById(id);
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        try{
            
            bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "Ingreso Mantenimiento Eliminar Banco", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
        
            eliminarBanco.setCodigo(resultado.getCodigo());
            eliminarBanco.setCodigoSwift(resultado.getCodigoSwift());
            eliminarBanco.setDescripcion(resultado.getDescripcion());

            return new ModelAndView("eliminarBanco", "eliminarBanco", eliminarBanco);
        
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("eliminarBanco", "model", modelo);
        }
                
    }
    //Fin estado incial de los formularios
    
    //Accion post de los formularios
    @RequestMapping(value="/crearBanco.htm", method = RequestMethod.POST)
    public ModelAndView crearBancoSubmit(@ModelAttribute("crearBanco") @Valid Banco crearBanco, BindingResult result, Model modelo, HttpServletRequest request)
    {
        
        if (result.hasErrors()) {
            return new ModelAndView("crearBanco");
        }                
                
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        try{
            
            bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "Crear Banco ID: "+ crearBanco.getCodigo() +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
        
            //Guarda los datos ingresados por el usuario
            mantenimientoAdmin.saveBanco(crearBanco);

            Map<String, Object> myModel = new HashMap<>();        
            myModel.put("listadoBancos", this.mantenimientoAdmin.getBancoList());
            myModel.put("messageError", "Registro Creado Satisfactoriamente");

            return new ModelAndView("listadoBancos", "model", myModel);  
        
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("crearBanco", "model", modelo);
        }        
        
    }
    
    @RequestMapping(value="/editarBanco.htm", method = RequestMethod.POST)
    public ModelAndView editarBancoSubmit(@ModelAttribute("editarBanco") @Valid Banco editarBanco, BindingResult result, Model modelo, HttpServletRequest request)
    {
        
        if (result.hasErrors()) {
            return new ModelAndView("editarBanco");
        }                               
                      
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        String oldValue, newValue;
        
        try{                                    
        
            //Guarda los datos modificados por el usuario
            oldValue = ToStringBuilder.reflectionToString(mantenimientoAdmin.getBancoById(editarBanco.getCodigo()));
            mantenimientoAdmin.updateBanco(editarBanco);
            newValue = ToStringBuilder.reflectionToString(mantenimientoAdmin.getBancoById(editarBanco.getCodigo()));
            bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "Mantenimiento Editar Banco ID: "+ editarBanco.getCodigo() +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), oldValue, newValue);

            Map<String, Object> myModel = new HashMap<>();        
            myModel.put("listadoBancos", this.mantenimientoAdmin.getBancoList());
            myModel.put("messageError", "Registro Actualizado Satisfactoriamente");

            return new ModelAndView("listadoBancos", "model", myModel);  
        
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("editarBanco", "model", modelo);
        }        
        
    }
    
    @RequestMapping(value="/eliminarBanco.htm", method = RequestMethod.POST)
    public ModelAndView eliminarBancoSubmit(@ModelAttribute("eliminarBanco") @Valid Banco eliminarBanco, BindingResult result, Model modelo, HttpServletRequest request)
    {
        
        if (result.hasErrors()) {
            return new ModelAndView("eliminarBanco");
        }                
        
        Banco resultado = new Banco();
        resultado.setCodigo(eliminarBanco.getCodigo());
        resultado.setDescripcion(eliminarBanco.getDescripcion());      
        
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        try{
            
            bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "Mantenimiento Eliminar Banco ID: "+ eliminarBanco.getCodigo() +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
        
            //Eliminar el pos seleccionado por el usuario
            mantenimientoAdmin.removeBanco(resultado);

            Map<String, Object> myModel = new HashMap<>();        
            myModel.put("listadoBanco", this.mantenimientoAdmin.getBancoList());
            myModel.put("messageError", "Registro Eliminado Satisfactoriamente");

            return new ModelAndView("listadoBancos", "model", myModel);
        
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("eliminarBanco", "model", modelo);
        }        
        
    }
}
