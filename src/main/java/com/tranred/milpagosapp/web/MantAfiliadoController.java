package com.tranred.milpagosapp.web;

import com.tranred.milpagosapp.domain.Afiliado;
import com.tranred.milpagosapp.domain.Banco;
import com.tranred.milpagosapp.domain.TipoPersona;
import com.tranred.milpagosapp.domain.Usuario;
import com.tranred.milpagosapp.repository.JPABancoDAO;
import com.tranred.milpagosapp.repository.JPATipoPersonaDAO;
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
 * Clase actua como controlador para las opciones Agreagr y Editar un Afiliado.
 * @author mggy@sistemasemsys.com
 * @version 0.1
 */

@Controller
public class MantAfiliadoController {
    
    protected final Log logger = LogFactory.getLog(getClass());
    
    @Autowired
    private IAdminMantenimiento mantenimientoAdmin;
    
    @Autowired
    private IBitacoraService bitacora;
    
    @Autowired
    private JPATipoPersonaDAO tipoPersona;
    
    @Autowired
    private JPABancoDAO bancoDAO;
    
    //Asignamos el estado inicial de los formularios con las propiedades de la clase Afilaido
    @ModelAttribute("crearAfiliado")
    public Afiliado createModelCrear() {
        return new Afiliado();
    }
    
    @ModelAttribute("editarAfiliado")
    public Afiliado createModelEditar() {
        return new Afiliado();
    }        
    
    @RequestMapping(value="/crearAfiliado.htm", method = RequestMethod.GET)
    protected ModelAndView crearAfiliado(HttpServletRequest request, Model modelo) throws ServletException {
                
        Afiliado crearAfiliado = new Afiliado();                
                
        return new ModelAndView("crearAfiliado", "crearAfiliado", crearAfiliado);
    }
    
    @RequestMapping(value="/editarAfiliado.htm", method = RequestMethod.GET)
    protected ModelAndView editarAfiliado(@RequestParam(value = "idAfiliado") String idAfiliado,HttpServletRequest request, Model modelo) throws ServletException {
                
        Afiliado editarAfiliado = new Afiliado();        
        editarAfiliado = mantenimientoAdmin.getAfiliadoById(idAfiliado);
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "Ingreso Mantenimiento Editar Afiliado", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
                
        return new ModelAndView("editarAfiliado", "editarAfiliado", editarAfiliado);
    }       
    //Fin estado incial de los formularios
    
    //Accion post de los formularios
    @RequestMapping(value="/crearAfiliado.htm", method = RequestMethod.POST)
    public ModelAndView crearAfiliadoSubmit(@ModelAttribute("crearAfiliado") @Valid Afiliado crearAfiliado, BindingResult result, Model modelo, HttpServletRequest request)
    {
        
        if (result.hasErrors()) {
            return new ModelAndView("crearAfiliado");
        }                                
        
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        try{
           bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "Mantenimiento Crear Afiliado ID: "+ crearAfiliado.getCodigoAfiliado() +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
        
            //Guarda los datos registrados por el usuario
            mantenimientoAdmin.saveAfiliado(crearAfiliado);

            Map<String, Object> myModel = new HashMap<String, Object>();        
            myModel.put("listadoAfiliados", this.mantenimientoAdmin.getAfiliadoList());
            myModel.put("messageError", "Registro Creado Satisfactoriamente");

            return new ModelAndView("listadoAfiliados", "model", myModel);
            
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuario.get(0).getId(), 2, 12, "No se ha podido insertar el registro debido al siguiente error: " + cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");                        
            logger.error(cve.getMessage());
            return new ModelAndView("crearAfiliado", "model", modelo); 
        }
                 
    }
    
    @RequestMapping(value="/editarAfiliado.htm", method = RequestMethod.POST)
    public ModelAndView editarAfiliadoSubmit(@ModelAttribute("editarAfiliado") @Valid Afiliado editarAfiliado, BindingResult result, Model modelo, HttpServletRequest request)
    {
        
        if (result.hasErrors()) {
            return new ModelAndView("editarAfiliado");
        }                                
        
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");               
        String oldValue, newValue;
        
        try{
            //Guarda los datos modificados por el usuario
            oldValue = ToStringBuilder.reflectionToString(mantenimientoAdmin.getAfiliadoById(editarAfiliado.getCodigoAfiliado()));
            mantenimientoAdmin.updateAfiliado(editarAfiliado);
            newValue = ToStringBuilder.reflectionToString(mantenimientoAdmin.getAfiliadoById(editarAfiliado.getCodigoAfiliado()));
            bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "Mantenimiento Editar Afiliado ID: "+ editarAfiliado.getCodigoAfiliado() +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), oldValue, newValue);

            Map<String, Object> myModel = new HashMap<String, Object>();        
            myModel.put("listadoAfiliados", this.mantenimientoAdmin.getAfiliadoList());
            myModel.put("messageError", "Registro Actualizado Satisfactoriamente");

            return new ModelAndView("listadoAfiliados", "model", myModel); 
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuario.get(0).getId(), 2, 12, "No se ha podido actualizar el registro debido al siguiente error: " + cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");                        
            logger.error(cve.getMessage());
            return new ModelAndView("editarAfiliado", "model", modelo); 
        }         
        
    }
    
    @ModelAttribute("tipoPersonaList")
    public List tipoPersonaList() {
        
        List<TipoPersona> tipoPersonaList = null;
        
        try{
            tipoPersonaList = tipoPersona.getTipoPersonaList();
        }catch (Exception cve) {                                   
            logger.error(cve.getMessage());            
        }
                                     		         
        return tipoPersonaList;
    }
     
    @ModelAttribute("bancosList")
    public List bancosList() {
        
        List<Banco> bancosList = null;
        
        try{
            bancosList = bancoDAO.getBancoList();
        }catch (Exception cve) {                       
            logger.error(cve.getMessage());                       
        } 
                        		         
        return bancosList;
    }
}
