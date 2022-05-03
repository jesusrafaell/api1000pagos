package com.tranred.milpagosapp.web;

import com.tranred.milpagosapp.domain.Usuario;
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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *  Clase actua como controlador para el modulo de administración opción mantenimiento.
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 * 
 */

@Controller
public class MantenimientoController {
    
    protected final Log logger = LogFactory.getLog(getClass());
    
    @Autowired
    private IAdminMantenimiento mantenimientoAdmin;        
    
    @Autowired
    private IBitacoraService bitacora;
    
    /* Mantenimiento de Modalidad POS */
    @RequestMapping(value="/listadoModalidadPos.htm")
    public ModelAndView listadoModalidadPos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Map<String, Object> myModel = new HashMap<String, Object>();        
        myModel.put("listadoModalidadPos", this.mantenimientoAdmin.getModalidadPosList());
        
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "Mantenimiento Consulta Modalidad POS", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
        
        return new ModelAndView("listadoModalidadPos", "model", myModel);
        
    }         
    
    /* Mantenimiento de un Banco */
    @RequestMapping(value="/listadoBancos.htm")
    public ModelAndView listadoBancos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Map<String, Object> myModel = new HashMap<String, Object>();        
        myModel.put("listadoBancos", this.mantenimientoAdmin.getBancoList());
        
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "Mantenimiento Consulta Bancos", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
        
        return new ModelAndView("listadoBancos", "model", myModel);
        
    }
    
    /* Mantenimiento de un Zona de Atención */
    @RequestMapping(value="/listadoZonasAtencion.htm")
    public ModelAndView listadoZonasAtencion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Map<String, Object> myModel = new HashMap<String, Object>();        
        myModel.put("listadoZonasAtencion", this.mantenimientoAdmin.getZonasAtencionList());
        
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "Mantenimiento Consulta Zonas Atención", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
        
        return new ModelAndView("listadoZonasAtencion", "model", myModel);
        
    }
    
    /* Mantenimiento de un Mensaje */
    @RequestMapping(value="/listadoMensajes.htm")
    public ModelAndView listadoMensajes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Map<String, Object> myModel = new HashMap<String, Object>();        
        myModel.put("listadoMensajes", this.mantenimientoAdmin.getMensajesList());
        
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "Mantenimiento Mensajes a Clientes", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
        
        return new ModelAndView("listadoMensajes", "model", myModel);
        
    }
    
    /* Mantenimiento de Afiliados */
    @RequestMapping(value="/listadoAfiliados.htm")
    public ModelAndView listadoAfiliados(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Map<String, Object> myModel = new HashMap<String, Object>();        
        myModel.put("listadoAfiliados", this.mantenimientoAdmin.getAfiliadoList());
        
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "Mantenimiento Consulta Afiliados", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
        
        return new ModelAndView("listadoAfiliados", "model", myModel);
        
    }  
}
