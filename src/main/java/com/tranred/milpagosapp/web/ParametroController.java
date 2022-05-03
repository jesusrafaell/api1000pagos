package com.tranred.milpagosapp.web;

import com.tranred.milpagosapp.domain.Parametro;
import com.tranred.milpagosapp.domain.Usuario;
import com.tranred.milpagosapp.service.IBitacoraService;
import com.tranred.milpagosapp.service.ParametroForm;
import com.tranred.milpagosapp.service.ParametroService;
import com.tranred.milpagosapp.util.Utilidades;
import java.io.IOException;
import java.util.List;
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
 *  Clase actua como controlador para el módulo de administración opción Parametros.
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 * 
 */

@Controller
public class ParametroController {
    
    protected final Log logger = LogFactory.getLog(getClass());
    
    @Autowired
    private ParametroService parametroService;
    
    @Autowired
    private IBitacoraService bitacora;
    
    //Asignamos el estado inicial de los formularios con las propiedades de la clase ParametroForm (servicio)    
    @ModelAttribute("editarParametro")
    public ParametroForm createModelEditar() {
        return new ParametroForm();
    }    
            
    @RequestMapping(value="/editarParametro.htm", method = RequestMethod.GET)
    protected ModelAndView editarParametro(@RequestParam(value = "idParametro") String id,HttpServletRequest request, Model modelo) throws ServletException {
                
        ParametroForm editarParametro = new ParametroForm();
        Parametro resultado = new Parametro();
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        try{
            
            resultado = parametroService.getParametroById(Integer.parseInt(id));
            bitacora.saveLogs(usuario.get(0).getId(), 1, 7, "Ingreso Editar Parametro ID: "+ id +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");

            editarParametro.setId(Integer.toString(resultado.getId()));
            editarParametro.setCodigo(resultado.getCodigo());        
            editarParametro.setNombre(resultado.getNombre());        
            editarParametro.setDescripcion(resultado.getDescripcion());
            editarParametro.setValor(resultado.getValor());
            return new ModelAndView("editarParametro", "editarParametro", editarParametro);
        
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuario.get(0).getId(), 1, 7, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("editarParametro", "model", modelo);
        }
        
    }
    //Fin estado inicial del formulario
    
    //Accion post del formulario editarParametro     
    @RequestMapping(value="/editarParametro.htm", method = RequestMethod.POST)
    public ModelAndView editarParametroSubmit(@ModelAttribute("editarParametro") @Valid ParametroForm editarParametro, BindingResult result, Model modelo, HttpServletRequest request)
    {
        
        if (result.hasErrors()) {
            return new ModelAndView("editarParametro");
        }                
                     
        Parametro resultado = new Parametro();
        String oldValue, newValue;
        
        resultado.setId(Integer.parseInt(editarParametro.getId()));
        resultado.setCodigo(editarParametro.getCodigo());        
        resultado.setNombre(editarParametro.getNombre());        
        resultado.setDescripcion(editarParametro.getDescripcion());
        resultado.setValor(editarParametro.getValor());
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        try{                        
        
            //Guarda los datos modificados por el usuario
            oldValue = ToStringBuilder.reflectionToString(parametroService.getParametroById(resultado.getId()));
            parametroService.updateParametro(resultado);
            newValue = ToStringBuilder.reflectionToString(parametroService.getParametroById(resultado.getId()));
            bitacora.saveLogs(usuario.get(0).getId(), 1, 7, "Modifica Parametro ID: "+ editarParametro.getId() +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), oldValue, newValue);
            
            modelo.addAttribute("messageError", "Registro Actualizado Satisfactoriamente");
            modelo.addAttribute("listadoParametros", this.parametroService.getParametroList());       

            return new ModelAndView("listadoParametros", "model", modelo);
        
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuario.get(0).getId(), 1, 7, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("editarParametro", "model", modelo);
        }          
        
    }
    
    //Listado de Parametros
    @RequestMapping(value="/listadoParametros.htm")
    public ModelAndView listadoParametros(HttpServletRequest request, HttpServletResponse response, Model modelo)
            throws ServletException, IOException {
        
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        try{
            
            bitacora.saveLogs(usuario.get(0).getId(), 1, 7, "Consulta Parametros", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
        
            modelo.addAttribute("listadoParametros", this.parametroService.getParametroList());
            return new ModelAndView("listadoParametros", "model", modelo);
        
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuario.get(0).getId(), 1, 7, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("listadoParametros", "model", modelo);
        }                    
        
    }
    
}
