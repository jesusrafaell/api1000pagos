package com.tranred.milpagosapp.web;

import com.tranred.milpagosapp.domain.Aliado;
import com.tranred.milpagosapp.domain.Contacto;
import com.tranred.milpagosapp.domain.Estatus;
import com.tranred.milpagosapp.domain.GeneradorContrasena;
import com.tranred.milpagosapp.domain.Mensaje;
import com.tranred.milpagosapp.domain.Perfil;
import com.tranred.milpagosapp.domain.Usuario;
import com.tranred.milpagosapp.domain.UsuarioWeb;
import com.tranred.milpagosapp.repository.IEstatusDAO;
import com.tranred.milpagosapp.repository.IMensajeDAO;
import com.tranred.milpagosapp.service.AdministrarUsuarioWebForm;
import com.tranred.milpagosapp.service.ConsultaUsuarioForm;
import com.tranred.milpagosapp.service.ConsultaUsuarioWebForm;
import com.tranred.milpagosapp.service.CrearUsuarioForm;
import com.tranred.milpagosapp.service.IAdminUsuario;
import com.tranred.milpagosapp.service.IAliadosService;
import com.tranred.milpagosapp.service.IBitacoraService;
import com.tranred.milpagosapp.service.IComercioService;
import com.tranred.milpagosapp.service.ICorreoService;
import com.tranred.milpagosapp.service.IPerfilesService;
import com.tranred.milpagosapp.util.Utilidades;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 *  Clase actua como controlador para la administración de Usuarios MilPagos y Usuarios Sitio Web.
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 * 
 */

@Controller
public class UsuariosController {
    
    protected final Log logger = LogFactory.getLog(getClass());
    
    @Autowired
    private IAdminUsuario usuarioAdmin;
    
    @Autowired
    private IAliadosService aliadosService;
    
    @Autowired
    private IPerfilesService perfilesService;        
    
    @Autowired
    private Utilidades metodos;
    
    @Autowired
    private IBitacoraService bitacora;
    
    @Autowired
    private IEstatusDAO estatusDAO;
    
    @Autowired
    private IMensajeDAO mensajeDAO;
    
    @Resource
    private ICorreoService mailService;
    
    @Autowired
    private IComercioService comercioService;
        
    private Map<String, Perfil> perfilCache; 
    private java.sql.Date fechaExpira;
    
    //Asignamos el estado inicial de los formularios
    @ModelAttribute("crearUsuario")
    public CrearUsuarioForm createModelCrear() {
        return new CrearUsuarioForm();
    }
    
    @ModelAttribute("editarUsuario")
    public CrearUsuarioForm createModelEditar() {
        return new CrearUsuarioForm();
    }
    
    @ModelAttribute("consultaUsuarios")
    public ConsultaUsuarioForm createModelConsulta() {
        return new ConsultaUsuarioForm();
    }
    
    @ModelAttribute("administrarUsuarioWeb")
    public AdministrarUsuarioWebForm createModelAdministrar() {
        return new AdministrarUsuarioWebForm();
    }
    
    @ModelAttribute("consultaUsuarioWeb")
    public ConsultaUsuarioWebForm createModelConsultaWeb() {
        return new ConsultaUsuarioWebForm();
    }
    
    @RequestMapping(value="/crearUsuario.htm", method = RequestMethod.GET)
    protected ModelAndView crearUsuario(HttpServletRequest request, Model modelo) throws ServletException {
                
        CrearUsuarioForm crearUsuario = new CrearUsuarioForm();                 
        
        crearUsuario.setContrasena(GeneradorContrasena.getPassword(GeneradorContrasena.ESPECIALES 
                + GeneradorContrasena.MAYUSCULAS + GeneradorContrasena.NUMEROS, 12));
        return new ModelAndView("crearUsuario", "crearUsuario", crearUsuario);
    }
    
    @RequestMapping(value="/consultaUsuarios.htm", method = RequestMethod.GET)
    protected ConsultaUsuarioForm consultaUsuarios(HttpServletRequest request, Model modelo) throws ServletException {
                
        ConsultaUsuarioForm consultaUsuarios = new ConsultaUsuarioForm();        
        return consultaUsuarios;
    }
    
    @RequestMapping(value="/editarUsuario.htm", method = RequestMethod.GET)
    protected ModelAndView editarUsuario(@RequestParam(value = "idUsuario") Integer id, HttpServletRequest request, Model modelo) throws ServletException, Exception {
                
        Usuario resultado = new Usuario();
        Usuario editarUsuario = new Usuario();
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuarioLogin = (List<Usuario>) misession.getAttribute("usuario.datos");                
        
        try{
            
            resultado = usuarioAdmin.getById(id);
            editarUsuario.setId(resultado.getId());
            editarUsuario.setContrasena(GeneradorContrasena.getPassword(GeneradorContrasena.ESPECIALES 
                    + GeneradorContrasena.MAYUSCULAS + GeneradorContrasena.NUMEROS, 12));        
            editarUsuario.setLogin(resultado.getLogin());
            editarUsuario.setTipoIdentificacion(resultado.getTipoIdentificacion().trim());
            editarUsuario.setIdentificacion(resultado.getIdentificacion().trim());
            editarUsuario.setEmail(resultado.getEmail());
            editarUsuario.setNombre(resultado.getNombre());        
            editarUsuario.setPerfilId(resultado.getPerfilId());
            editarUsuario.setEstatus(resultado.getEstatus());
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 1, 4, "Consulta Usuario Login: "+ resultado.getLogin() +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
        
        return new ModelAndView("editarUsuario", "editarUsuario", editarUsuario);
        
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 1, 4, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("editarUsuario", "model", modelo);
        }
        
    }
    
    @RequestMapping(value="/eliminarUsuario.htm", method = RequestMethod.GET)
    protected ModelAndView eliminarUsuario(@RequestParam(value = "idUsuario") Integer usuario, HttpServletRequest request, Model modelo) throws ServletException, Exception {
                
        Usuario resultado = new Usuario();
        Usuario eliminarUsuario = new Usuario();
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuarioLogin = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        try{
            
            resultado = usuarioAdmin.getById(usuario);
            eliminarUsuario.setId(resultado.getId());        
            eliminarUsuario.setLogin(resultado.getLogin());
            eliminarUsuario.setTipoIdentificacion(resultado.getTipoIdentificacion().trim());
            eliminarUsuario.setIdentificacion(resultado.getIdentificacion().trim());
            eliminarUsuario.setNombre(resultado.getNombre());        
            eliminarUsuario.setPerfilId(resultado.getPerfilId());        
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 1, 4, "Consulta Usuario Login: "+ resultado.getLogin() +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");

            return new ModelAndView("eliminarUsuario", "eliminarUsuario", eliminarUsuario);
        
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 1, 4, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("eliminarUsuario", "model", modelo);
        }
        
    }
    
    @RequestMapping(value="/reporteUsuariosPerfil.htm", method = RequestMethod.GET)
    protected ModelAndView reporteUsuariosPerfil(@RequestParam(value = "perfilId") String perfilId, HttpServletRequest request, Model modelo) throws ServletException {
        
        List<Usuario> resultadoConsultaUsuario;
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuarioLogin = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        try{
            
            resultadoConsultaUsuario = usuarioAdmin.getByperfil(perfilId);              
        
            //Valida si existen registros con los parametros seleccionados
            if(resultadoConsultaUsuario.isEmpty()){            
                    modelo.addAttribute("messageError", "No se encontraron registros con los parámetros seleccionados");
                    return new ModelAndView("resultadoConsultaUsuario", "model", modelo);
            }else{  
                    bitacora.saveLogs(usuarioLogin.get(0).getId(), 1, 22, "Reporte Usuarios", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
                    return new ModelAndView("reporteUsuariosPerfil", "reporteUsuariosPerfil", resultadoConsultaUsuario);
            }
            
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 1, 22, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("resultadoConsultaUsuario", "model", modelo);
        }
        
    }
    
    /*Se utiliza @Initbinder para evitar los errores de type mismatch al momento de validar
    * las opciones de lista (perfil) con el BindingResult    
    */
    @InitBinder
    @RequestMapping(value="/crearUsuario.htm")
    protected void initBinder(WebDataBinder binder) throws Exception {
	binder.registerCustomEditor(Set.class, "perfil", new CustomCollectionEditor(Set.class) {
            @Override
            protected Object convertElement(Object element) {
                if (element instanceof Perfil) {                            
                    return element;
                }
                if (element instanceof String) {
                    Perfil perfiles = perfilCache.get(element);				
                    return perfiles;
                }			
                return null;
            }
	});
    }
    
    @InitBinder
    @RequestMapping(value="/editarUsuario.htm")
    protected void initBinderEditar(WebDataBinder binder) throws Exception {
	binder.registerCustomEditor(Set.class, "perfil", new CustomCollectionEditor(Set.class) {
            @Override
            protected Object convertElement(Object element) {
                if (element instanceof Perfil) {                            
                    return element;
                }
                if (element instanceof String) {
                    Perfil perfiles = perfilCache.get(element);				
                    return perfiles;
                }			
                return null;
            }
	});
    }
    
    //Usuarios Sitio Web
    @RequestMapping(value="/consultaUsuarioWeb.htm", method = RequestMethod.GET)
    protected ConsultaUsuarioWebForm consultaUsuarioWeb(HttpServletRequest request, Model modelo) throws ServletException {
                
        ConsultaUsuarioWebForm consultaUsuarioWeb = new ConsultaUsuarioWebForm();        
        return consultaUsuarioWeb;
    }
    
    @RequestMapping(value="/administrarUsuarioWeb.htm", method = RequestMethod.GET)
    protected ModelAndView administrarUsuarioWeb(@RequestParam(value = "tipoGet") String tipo,@RequestParam(value = "identificacionGet") String identificacion, HttpServletRequest request, Model modelo) throws ServletException, Exception {
                
        List<UsuarioWeb> resultado;        
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuarioLogin = (List<Usuario>) misession.getAttribute("usuario.datos");                
        
        try{
            
            resultado = usuarioAdmin.getUsuarioWebByIdentificacion(tipo, identificacion);
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 1, 4, "Consulta Usuario Web Login: "+ resultado.get(0).getLogin() +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
        
            return new ModelAndView("administrarUsuarioWeb", "administrarUsuarioWeb", resultado.get(0));
        
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 1, 4, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("administrarUsuarioWeb", "model", modelo);
        }
        
    }
    //Fin estado inicial
    
    //Accion post del formulario Crear Usuario
    @RequestMapping(value="/crearUsuario.htm", method = RequestMethod.POST)
    public ModelAndView crearUsuarioSubmit(@ModelAttribute("crearUsuario") @Valid CrearUsuarioForm crearUsuario, BindingResult result, Model modelo, HttpServletRequest request)
    {                
        if (result.hasErrors()) {
            return new ModelAndView("crearUsuario");
        }
        
        Map<String, Object> myModel = new HashMap<String, Object>();
        Usuario usuario = new Usuario();
        List<Usuario> usuarioAliado;
        List<Aliado> resultadoAliado;
        List<Mensaje> mensajeCorreo;
        Date fecha = new Date();
        HttpSession misession= (HttpSession) request.getSession();       
        List<Usuario> usuarioLogin = (List<Usuario>) misession.getAttribute("usuario.datos");
        String nuevalinea = System.getProperty("line.separator");
        
        java.sql.Timestamp fechaSQL = new java.sql.Timestamp(fecha.getTime());
        fechaExpira = Utilidades.sumarFechasDias(fechaSQL, Integer.parseInt(metodos.parametro("tiempo.expira").get(0).getValor()));
                
        usuario.setIdentificacion(crearUsuario.getIdentificacion());
        usuario.setTipoIdentificacion(crearUsuario.getTipoIdentificacion());
        usuario.setNombre(crearUsuario.getNombre());
        usuario.setLogin(crearUsuario.getLogin().toLowerCase());
        
        usuario.setContrasena(Utilidades.Encriptar(crearUsuario.getContrasena()));        
        
        usuario.setEmail(crearUsuario.getEmail());
        usuario.setEstatus("7");
        usuario.setCambio("2");
        usuario.setPerfilId(crearUsuario.getPerfilId());
        usuario.setFechaCreacion(fechaSQL);
        usuario.setFechaExpira(fechaExpira);
        usuario.setIntentosFallidos(0);
        
        try {
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 1, 3, "Crea Usuario Login: "+ crearUsuario.getLogin() +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");                        
            usuarioAdmin.saveUsuario(usuario);
            modelo.addAttribute("messageError", "Usuario creado Satisfactoriamente");                                                             
            
            //Valido si existe como aliado para asignarle el codigo del usuario creado
            resultadoAliado = aliadosService.getAliadoByIdentificacion(crearUsuario.getTipoIdentificacion(), crearUsuario.getIdentificacion());
            if(!(resultadoAliado.isEmpty())){
                
                usuarioAliado = usuarioAdmin.getByIdentificacion(crearUsuario.getTipoIdentificacion(), crearUsuario.getIdentificacion());
                aliadosService.updateAliadoIdUsuario(String.valueOf(resultadoAliado.get(0).getId()), String.valueOf(usuarioAliado.get(0).getId()));
            }            
            
            //Verifica si debe enviar correo 
            if(crearUsuario.getEnvioCorreo() != null){
                mensajeCorreo = mensajeDAO.getMensajeByClave("correo.contrasena");
                mailService.send(crearUsuario.getEmail(), "Creación de Usuarios", mensajeCorreo.get(0).getMensajeTexto() + " " + "Login: " + crearUsuario.getLogin() + "\n Contraseña: " + crearUsuario.getContrasena());            
            }
            
            modelo.addAttribute("listadoUsuarios", usuarioAdmin.getUsuarios());
            return new ModelAndView("listadoUsuarios","model", modelo); 
            
        } catch (javax.persistence.PersistenceException cve) {                                                
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 1, 3, "No se ha podido insertar el registro debido al siguiente error: El Usuario "+ crearUsuario.getLogin() +" ya existe.", Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());
            myModel.put("messageError", "No se ha podido insertar el registro debido al siguiente error: El Usuario "+ crearUsuario.getLogin() +" ya existe.");
            return new ModelAndView("crearUsuario", "model", myModel);           
        } catch (org.hibernate.exception.ConstraintViolationException cve) {             
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 1, 3, "No se ha podido insertar el registro debido al siguiente error: El Usuario "+ crearUsuario.getLogin() +" ya existe.", Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());
            myModel.put("messageError", "No se ha podido insertar el registro debido al siguiente error: El Usuario "+ crearUsuario.getLogin() +" ya existe.");
            return new ModelAndView("crearUsuario", "model", myModel);             
        } catch (org.springframework.mail.MailSendException cve){
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 1, 3, "Usuario creado Satisfactoriamente. No se pudo enviar el correo electrónico. Consulte con el Administrador. Error: " + Arrays.toString(cve.getMessageExceptions()), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());
            myModel.put("messageError", "Usuario creado Satisfactoriamente." + nuevalinea + "   No se pudo enviar el correo electrónico. Consulte con el Administrador.");
            return new ModelAndView("crearUsuario", "model", myModel);
        } catch (org.springframework.mail.MailAuthenticationException cve){
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 1, 3, "Usuario creado Satisfactoriamente. No se pudo enviar el correo electrónico. Consulte con el Administrador. Error: " + cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());
            myModel.put("messageError", "Usuario creado Satisfactoriamente." + nuevalinea + "   No se pudo enviar el correo electrónico. Consulte con el Administrador.");
            return new ModelAndView("crearUsuario", "model", myModel);    
        } catch (Exception cve) {            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 1, 3, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("crearUsuario", "model", modelo);
        }                
     
    }
    
    //Accion post del formulario Editar Usuario
    @RequestMapping(value="/editarUsuario.htm", method = RequestMethod.POST)
    public ModelAndView editarUsuarioSubmit(@ModelAttribute("editarUsuario") @Valid CrearUsuarioForm editarUsuario, BindingResult result, Model modelo, HttpServletRequest request)
    {                
        if (result.hasErrors()) {
            return new ModelAndView("editarUsuario");
        }
                
        Usuario usuario = new Usuario();               
        Usuario resultado = new Usuario();
        List<Mensaje> mensajeCorreo;        
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuarioLogin = (List<Usuario>) misession.getAttribute("usuario.datos");
        String nuevalinea = System.getProperty("line.separator");
        String oldValue, newValue;
        String messageError = "";
                
        resultado = usuarioAdmin.getById(Integer.parseInt(editarUsuario.getId()));
        
        usuario.setId(Integer.parseInt(editarUsuario.getId()));
        usuario.setIdentificacion(editarUsuario.getIdentificacion());
        usuario.setTipoIdentificacion(editarUsuario.getTipoIdentificacion());
        usuario.setNombre(editarUsuario.getNombre());
        usuario.setLogin(editarUsuario.getLogin().toLowerCase());
        usuario.setEmail(editarUsuario.getEmail());
        if("1".equals(editarUsuario.getReset())){
            usuario.setContrasena(Utilidades.Encriptar(editarUsuario.getContrasena()));
            usuario.setCambio("2");            
        }else{            
            usuario.setContrasena(resultado.getContrasena());
            if("1".equals(editarUsuario.getCambio())){
                usuario.setCambio("2");
            }else{
                usuario.setCambio(resultado.getCambio());
            }
        }                
        
        if("0".equals(editarUsuario.getEstatus())){
            usuario.setIntentosFallidos(0);            
        }else{
            usuario.setIntentosFallidos(resultado.getIntentosFallidos());
        }
        usuario.setFechaCreacion(resultado.getFechaCreacion());
        usuario.setFechaExpira(resultado.getFechaExpira());
        usuario.setUltimoAcceso(resultado.getUltimoAcceso());        
        usuario.setEstatus(editarUsuario.getEstatus());                
        usuario.setPerfilId(editarUsuario.getPerfilId());        
        
        try {
            
            oldValue = ToStringBuilder.reflectionToString(usuarioAdmin.getById(usuario.getId()));
            usuarioAdmin.updateUsuario(usuario);
            newValue = ToStringBuilder.reflectionToString(usuarioAdmin.getById(usuario.getId()));
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 1, 4, "Modifica Usuario Login: "+ resultado.getLogin() +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), oldValue, newValue);            
            
            if("1".equals(editarUsuario.getEnvioCorreo())){                
                try{
                    mensajeCorreo = mensajeDAO.getMensajeByClave("correo.contrasenaOlvido");
                    mailService.send(editarUsuario.getEmail(), "Envío de nueva Contraseña", mensajeCorreo.get(0).getMensajeTexto() + " " + editarUsuario.getContrasena());
                } catch (org.springframework.mail.MailSendException cve){
                    bitacora.saveLogs(usuarioLogin.get(0).getId(), 1, 3, "No se pudo enviar el correo electrónico. Consulte con el Administrador. Error: " + Arrays.toString(cve.getMessageExceptions()), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
                    logger.error(cve.getMessage());
                    messageError = "   No se pudo enviar el correo electrónico. Consulte con el Administrador.";
                } catch (org.springframework.mail.MailAuthenticationException cve){
                    bitacora.saveLogs(usuarioLogin.get(0).getId(), 1, 3, "No se pudo enviar el correo electrónico. Consulte con el Administrador. Error: " + cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
                    logger.error(cve.getMessage());
                    messageError = "   No se pudo enviar el correo electrónico. Consulte con el Administrador.";                
                }
            }
            
            modelo.addAttribute("messageError", "Usuario modificado Satisfactoriamente." + nuevalinea + messageError);
            modelo.addAttribute("listadoUsuarios", usuarioAdmin.getUsuarios());
            return new ModelAndView("listadoUsuarios","model", modelo);
                        
        } catch (Exception cve) {            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 1, 4, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("editarUsuario", "model", modelo);
        }                
     
    }
    
    // Accion post del formulario eliminarUsuario
    @RequestMapping(value="/eliminarUsuario.htm", method = RequestMethod.POST)
    public ModelAndView eliminarUsuarioSubmit(@ModelAttribute("eliminarUsuario") CrearUsuarioForm eliminarUsuario, BindingResult result, Model modelo, HttpServletRequest request)
    {
        
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuarioLogin = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        try {
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 1, 4, "Elimina Usuario Login: "+ eliminarUsuario.getLogin() +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
            usuarioAdmin.removeUsuario(usuarioAdmin.getById(Integer.parseInt(eliminarUsuario.getId())));
            modelo.addAttribute("listadoUsuarios", usuarioAdmin.getUsuarios());
            modelo.addAttribute("messageError", "Registro Eliminado Satisfactoriamente");
            return new ModelAndView("listadoUsuarios","model", modelo);
            
        } catch (Exception cve) {            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 1, 4, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("eliminarUsuario", "model", modelo);
        }                                 
        
    }
    
    //Consulta Usuarios
    @RequestMapping(value="/consultaUsuarios.htm", method = RequestMethod.POST)
    public ModelAndView consultaUsuariosSubmit(@ModelAttribute("consultaUsuarios") @Valid ConsultaUsuarioForm consultaUsuarios, BindingResult result, Model modelo, HttpServletRequest request) throws ServletException {
                
        if (result.hasErrors()) {
            return new ModelAndView("consultaUsuarios");
        }
        
        List<Usuario> resultadoConsultaUsuario;
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuarioLogin = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        try{
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 1, 22, "Consulta Usuarios", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
            resultadoConsultaUsuario = usuarioAdmin.getByperfil(consultaUsuarios.getPerfil());              
        
            //Valida si existen registros con los parametros seleccionados
            if(resultadoConsultaUsuario.isEmpty()){            
                    modelo.addAttribute("messageError", "No se encontraron registros con los parámetros seleccionados");
                    return new ModelAndView("consultaUsuarios", "model", modelo);
            }else{                  
                    return new ModelAndView(new RedirectView("reporteUsuariosPerfil.htm?perfilId="+ consultaUsuarios.getPerfil() +""));
            }
        
        } catch (Exception cve) {            
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 1, 22, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("consultaUsuarios", "model", modelo);
        }        
        
    }
    
    //Listado de Usuarios
    @RequestMapping(value="/listadoUsuarios.htm")
    public ModelAndView listadoUsuarios(HttpServletRequest request, HttpServletResponse response, Model modelo)
            throws ServletException, IOException {
        
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuarioLogin = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        try{
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 1, 4, "Listado Usuarios", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
        
            modelo.addAttribute("listadoUsuarios", usuarioAdmin.getUsuarios());
            return new ModelAndView("listadoUsuarios","model", modelo);
        
        } catch (Exception cve) {            
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 1, 4, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("listadoUsuarios", "model", modelo);
        }                     
        
    }
    
    //Reporte Usuarios-Perfil
    @RequestMapping(value="/reporteUsuariosPerfil.htm")
    public ModelAndView reporteUsuariosPerfil(HttpServletRequest request, HttpServletResponse response, Model modelo)
            throws ServletException, IOException {
        
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuarioLogin = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        try{
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 1, 22, "Reporte Usuarios", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
        
            modelo.addAttribute("reporteUsuariosPerfil", usuarioAdmin.getUsuarios());
            return new ModelAndView("reporteUsuariosPerfil","model", modelo);
            
        } catch (Exception cve) {            
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 1, 22, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("reporteUsuariosPerfil", "model", modelo);
        }                   
        
    }
    
    //Consulta Usuarios Web
    @RequestMapping(value="/consultaUsuarioWeb.htm", method = RequestMethod.POST)
    public ModelAndView consultaUsuarioWebSubmit(@ModelAttribute("consultaUsuarioWeb") @Valid ConsultaUsuarioWebForm consultaUsuarioWeb, BindingResult result, Model modelo, HttpServletRequest request) throws ServletException {
                
        if (result.hasErrors()) {
            return new ModelAndView("consultaUsuarioWeb");
        }
        
        List<UsuarioWeb> resultadoConsultaUsuarioWeb;
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuarioLogin = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        try{
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 27, "Consulta Usuarios Web", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
            resultadoConsultaUsuarioWeb = usuarioAdmin.getUsuarioWebByIdentificacion(consultaUsuarioWeb.getTipoIdentificacion(),consultaUsuarioWeb.getIdentificacion());              
        
            //Valida si existen registros con los parametros seleccionados
            if(resultadoConsultaUsuarioWeb.isEmpty()){            
                    modelo.addAttribute("messageError", "No se encontraron registros con los parámetros seleccionados");
                    return new ModelAndView("consultaUsuarioWeb", "model", modelo);
            }else{                  
                    return new ModelAndView(new RedirectView("administrarUsuarioWeb.htm?tipoGet="+ consultaUsuarioWeb.getTipoIdentificacion() +"&identificacionGet="+ consultaUsuarioWeb.getIdentificacion()+""));
            }
        
        } catch (Exception cve) {            
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 27, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("consultaUsuarioWeb", "model", modelo);
        }        
        
    }
    
    //Accion post del formulario administrarUsuarioWeb
    @RequestMapping(value="/administrarUsuarioWeb.htm", method = RequestMethod.POST)
    public ModelAndView administrarUsuarioWebSubmit(@ModelAttribute("administrarUsuarioWeb") @Valid AdministrarUsuarioWebForm administrarUsuarioWeb, BindingResult result, Model modelo, HttpServletRequest request) throws Exception
    {                
        if (result.hasErrors()) {
            return new ModelAndView("administrarUsuarioWeb");
        }
        
        UsuarioWeb usuarioWeb = new UsuarioWeb();
        Contacto contacto = new Contacto();
        List<Contacto> contactoList;
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuarioLogin = (List<Usuario>) misession.getAttribute("usuario.datos");
        List<Mensaje> mensajeCorreo;
        String nuevalinea = System.getProperty("line.separator"), contrasenaNueva = GeneradorContrasena.getPassword(GeneradorContrasena.MAYUSCULAS + GeneradorContrasena.NUMEROS, 12);
        String messageError = "";                        
        String oldValue, newValue;
        
        usuarioWeb = usuarioAdmin.getUsuarioWebByIdentificacion(administrarUsuarioWeb.getTipoIdentificacion(), administrarUsuarioWeb.getIdentificacion()).get(0);
        
        if("1".equals(administrarUsuarioWeb.getResetContrasena()) && "1".equals(administrarUsuarioWeb.getResetPreguntas())){
            usuarioWeb.setContrasena(Utilidades.Encriptar(contrasenaNueva));
            usuarioWeb.setCambio("3");               
        }else if("1".equals(administrarUsuarioWeb.getResetContrasena()) && !"1".equals(administrarUsuarioWeb.getResetPreguntas())){
                usuarioWeb.setContrasena(Utilidades.Encriptar(contrasenaNueva));
                usuarioWeb.setCambio("1");
        }else if(!"1".equals(administrarUsuarioWeb.getResetContrasena()) && "1".equals(administrarUsuarioWeb.getResetPreguntas())){                
                usuarioWeb.setCambio("2");
                usuarioWeb.setContrasena(administrarUsuarioWeb.getContrasena());                
        }            
        
        if(!usuarioWeb.getEmail().equals(administrarUsuarioWeb.getEmail())){
            usuarioWeb.setEmail(administrarUsuarioWeb.getEmail());
            usuarioWeb.setLogin(Utilidades.Encriptar(administrarUsuarioWeb.getEmail()));
            contactoList = comercioService.getContactoByCodComercio(usuarioWeb.getCodigoComercio());
            if(!contactoList.isEmpty()){
                contacto = contactoList.get(0);
                contacto.setEmail(administrarUsuarioWeb.getEmail());
                comercioService.updateContacto(contacto);
            }            
        }
        
        usuarioWeb.setEstatus(administrarUsuarioWeb.getEstatus());                        
        
        try {
            
            oldValue = ToStringBuilder.reflectionToString(usuarioAdmin.getUsuarioWebByIdentificacion(administrarUsuarioWeb.getTipoIdentificacion(),administrarUsuarioWeb.getIdentificacion()).get(0));
            usuarioAdmin.updateUsuarioWeb(usuarioWeb);
            newValue = ToStringBuilder.reflectionToString(usuarioAdmin.getUsuarioWebByIdentificacion(administrarUsuarioWeb.getTipoIdentificacion(),administrarUsuarioWeb.getIdentificacion()).get(0));
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 27, "Modifica Usuario Web Login: "+ Utilidades.Desencriptar(usuarioWeb.getLogin()) +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), oldValue, newValue);            
            
            if("1".equals(administrarUsuarioWeb.getResetContrasena())){                
                try{
                    mensajeCorreo = mensajeDAO.getMensajeByClave("correo.contrasenaOlvido.web");
                    mailService.send(administrarUsuarioWeb.getEmail(), "Olvido de Contraseña", mensajeCorreo.get(0).getMensajeTexto() + " " + contrasenaNueva);
                } catch (org.springframework.mail.MailSendException cve){
                    bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 27, "No se pudo enviar el correo electrónico. Consulte con el Administrador. Error: " + Arrays.toString(cve.getMessageExceptions()), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
                    logger.error(cve.getMessage());
                    messageError = "   No se pudo enviar el correo electrónico.";
                } catch (org.springframework.mail.MailAuthenticationException cve){
                    bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 27, "No se pudo enviar el correo electrónico. Consulte con el Administrador. Error: " + cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
                    logger.error(cve.getMessage());
                    messageError = "   No se pudo enviar el correo electrónico.";                
                }
            }
            
            modelo.addAttribute("messageError", "Usuario modificado Satisfactoriamente." + nuevalinea + messageError);            
            return new ModelAndView("administrarUsuarioWeb","model", modelo);
                        
        } catch (Exception cve) {            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 27, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("administrarUsuarioWeb", "model", modelo);
        }                
     
    }
    //Fin Post
    
    @ModelAttribute("perfil")
    public List perfil() {
        
        List<Perfil> perfil = null;
        
        try{
            perfil = perfilesService.getPerfilesList();                             
            perfilCache = new HashMap<>();
            for (Perfil perfiles : perfil) {
		perfilCache.put(perfiles.getIdAsString(), perfiles);
            }	  
        }catch (Exception cve) {                                   
            logger.error(cve.getMessage());            
        } 
               
        return perfil;
    }        
    
    @ModelAttribute("estatusList")
    public List estatusList() {
        
        List<Estatus> estatusList = null;
        
        try{
            estatusList = estatusDAO.getEstatusByModulo("us");
        }catch (Exception cve) {                                   
            logger.error(cve.getMessage());            
        }
                                     		         
        return estatusList;
    }
    
    @ModelAttribute("estatusWebList")
    public List estatusWebList() {
        
        List<Estatus> estatusWebList = null;
        
        try{
            estatusWebList = estatusDAO.getEstatusByModulo("usw");
        }catch (Exception cve) {                                   
            logger.error(cve.getMessage());            
        }
                                     		         
        return estatusWebList;
    }
    
    @ModelAttribute("tipoIdentificacionList")
    public Map<String,String> tipoIdentificacionList() {
   
        Map<String,String> tipo = new LinkedHashMap<String,String>();
        tipo.put("V", "V");
        tipo.put("E", "E");
        tipo.put("P", "P");
        tipo.put("J", "J");
        tipo.put("G", "G");
        tipo.put("C", "C");
        tipo.put("R", "R");
        
        return tipo;
    }
}
