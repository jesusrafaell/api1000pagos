package com.tranred.milpagosapp.web;

import ar.com.fdvs.dj.domain.DynamicReport;
import com.tranred.milpagosapp.domain.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.tranred.milpagosapp.service.IAdminUsuario;
import com.tranred.milpagosapp.service.IBitacoraService;
import com.tranred.milpagosapp.service.LoginUsuarioForm;
import com.tranred.milpagosapp.util.Utilidades;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;


/**
 * Clase que actua como controlador del modulo Login
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 *
 */

@Controller
public class LoginFormController {
    
    protected final Log logger = LogFactory.getLog(getClass());
    protected DynamicReport dr;
    
    @Autowired
    private IAdminUsuario usuarioAdmin;
    
    @Autowired
    private IBitacoraService bitacora;       
    
    private int diasExpira;
    private final Date ahora = new Date();
    
    @ModelAttribute("usuario")
    public LoginUsuarioForm createModel() {
        return new LoginUsuarioForm();
    }
            
    @RequestMapping(value="/login.htm", method = RequestMethod.GET)
    protected LoginUsuarioForm login() throws ServletException, Exception {
        LoginUsuarioForm usuario = new LoginUsuarioForm();                
        
        return usuario;
    }

    @RequestMapping(value="/login.htm", method = RequestMethod.POST)
    public ModelAndView loginSubmit(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("usuario") LoginUsuarioForm usuario, BindingResult result, Model modelo) throws ParseException
    {        
        
        int intentosFallidos = 0;
        List<Usuario> resultado, resultadoValida;
        
        try{
            
            resultado = usuarioAdmin.getByLogin(usuario.getLogin());        
        
            //Valida si el usuario existe, estatus                 
            if(resultado.isEmpty()){

                modelo.addAttribute("messageError", "Usuario o Contraseña invalido");
                return new ModelAndView("login", "model", modelo);

            }else{

                if(!"7".equals(resultado.get(0).getEstatus())){

                    modelo.addAttribute("messageError", "Usuario Bloqueado, Suspendido o Retirado. Por favor comuniquese con el Administrador");
                    return new ModelAndView("login", "model", modelo);
                }else{

                    resultadoValida = usuarioAdmin.validaUsuario(usuario.getLogin(), Utilidades.Encriptar(usuario.getContrasena()));

                    if(resultadoValida.isEmpty()){

                        intentosFallidos = resultado.get(0).getIntentosFallidos() + 1;
                        usuarioAdmin.updateUsuarioIntentoFallido(resultado.get(0).getId(),intentosFallidos);

                        if(intentosFallidos == 3){
                            usuarioAdmin.updateUsuarioBloqueado(resultado.get(0).getId());
                            modelo.addAttribute("messageError", "Usuario Bloqueado, por favor comuniquese con el Administrador");
                            return new ModelAndView("login", "model", modelo);
                        }else{
                            modelo.addAttribute("messageError", "Usuario o Contraseña invalido");
                            return new ModelAndView("login", "model", modelo);
                        } 

                    }else{
                        
                        HttpSession misession = request.getSession(true);             
                        misession.setAttribute("usuario.datos",resultado);
                        java.util.Date fechaExpira = resultado.get(0).getFechaExpira();
                        diasExpira = Utilidades.diferenciasDeFechas(ahora, fechaExpira);
                        usuarioAdmin.updateUsuarioIntentoFallido(resultado.get(0).getId(),0);
                        
                        if(("2".equals(resultado.get(0).getCambio())) || (diasExpira<=0)){
                            return new ModelAndView(new RedirectView("cambioClave.htm?id=" + resultado.get(0).getId() + ""));
                        }else{
                            usuarioAdmin.updateUsuarioSession(resultado.get(0).getId(),1);
                            bitacora.saveLogs(resultado.get(0).getId(), 3, 9, "Inicio de Sesion", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
                            return new ModelAndView("presentacion");
                        }
                    }  
                }                                              
            } 
            
        }catch (Exception cve) {
            modelo.addAttribute("messageError", "No se ha podido iniciar la aplicación. Por favor consulte con el Administrador.");                        
            logger.error(cve.getMessage());
            return new ModelAndView("login", "model", modelo); 
        }
        
    }
    
    /* Pagina Inicio */
    @RequestMapping(value="/presentacion.htm")
    public ModelAndView presentacion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                
        return new ModelAndView("presentacion");
        
    }
    
    /* Salida de la Aplicación */    
    @RequestMapping(value="/salir.htm", method = RequestMethod.GET)
    public ModelAndView salir(HttpServletRequest request, HttpServletResponse response, Model modelo)
    {            
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        try{
            usuarioAdmin.updateUsuarioSession(usuario.get(0).getId(),0);
            bitacora.saveLogs(usuario.get(0).getId(), 3, 10, "Fin de Sesion", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
            return new ModelAndView(new RedirectView("login.htm"));
        }catch (Exception cve) {            
            logger.error(cve.getMessage());
            return new ModelAndView(new RedirectView("login.htm")); 
        }        
        
    }
    
    /* Fin de Sesion */    
    @RequestMapping(value="/finSesion.htm", method = RequestMethod.GET)
    public ModelAndView finSesion(HttpServletRequest request, HttpServletResponse response, Model modelo)
    {                    
       return new ModelAndView("finSesion");        
    }                  
    
}
