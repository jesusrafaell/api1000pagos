package com.tranred.milpagosapp.web;

import com.tranred.milpagosapp.domain.HistoricoValores;
import com.tranred.milpagosapp.domain.Usuario;
import com.tranred.milpagosapp.repository.JPAHistoricoValorDAO;
import com.tranred.milpagosapp.repository.JPAParametroDAO;
import com.tranred.milpagosapp.service.CrearUsuarioForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.tranred.milpagosapp.service.IAdminUsuario;
import com.tranred.milpagosapp.service.IBitacoraService;
import com.tranred.milpagosapp.util.Utilidades;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Clase que actua como controlador para el cambio de clave de un usuario
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 *
 */

@Controller
@RequestMapping(value="/cambioClave.htm")
public class CambioClaveController {
    
    protected final Log logger = LogFactory.getLog(getClass());
    
    @Autowired
    private IAdminUsuario usuarioAdmin;
    
    @Autowired
    private IBitacoraService bitacora;
    
    @Autowired
    private Utilidades metodos;
    
    @Autowired
    private JPAHistoricoValorDAO historico;
    
    @Autowired
    private JPAParametroDAO parametro;
    
    private java.sql.Date fechaExpira;
    
    @ModelAttribute("cambioClave")
    public CrearUsuarioForm createModel() {
        return new CrearUsuarioForm();
    }
            
    @RequestMapping(method = RequestMethod.GET)
    protected ModelAndView cambioClave(@RequestParam(value = "id") String idUsuario, HttpServletRequest request, Model modelo) throws ServletException {       
        
        Usuario user = new Usuario();
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        try{
            
            if(idUsuario.isEmpty()){                                
                user = usuarioAdmin.getById(usuario.get(0).getId());
            }else{
                user = usuarioAdmin.getById(Integer.parseInt(idUsuario));
            }        
            return new ModelAndView("cambioClave","cambioClave",user);
            
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuario.get(0).getId(), 1, 8, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageContrasena", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("cambioClave", "model" , modelo);
        } 
                        
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView cambioClaveSubmit(@ModelAttribute("cambioClave") @Valid CrearUsuarioForm cambioClave, BindingResult result, Model modelo, HttpServletRequest request)
    {                             
        
        if (result.hasErrors()) {            
            return new ModelAndView("cambioClave");        
        }
        
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuarioLogin = (List<Usuario>) misession.getAttribute("usuario.datos");
        Usuario user = new Usuario();
        Usuario usuarioCambio = new Usuario();
        HistoricoValores registro = new HistoricoValores();
        List<HistoricoValores> resultadoHistorico;
        Usuario resultado = new Usuario();
        List<Usuario> resultadocambio;
        
        try{
            
            resultado = usuarioAdmin.getById(usuarioLogin.get(0).getId());
        
            java.sql.Timestamp fechaSQL = Utilidades.getFechaActualSql();
            fechaExpira = Utilidades.sumarFechasDias(fechaSQL, Integer.parseInt(metodos.parametro("tiempo.expira").get(0).getValor()));           

            usuarioCambio.setId(usuarioLogin.get(0).getId());
            usuarioCambio.setIdentificacion(cambioClave.getIdentificacion());
            usuarioCambio.setTipoIdentificacion(cambioClave.getTipoIdentificacion());
            usuarioCambio.setNombre(cambioClave.getNombre());
            usuarioCambio.setLogin(resultado.getLogin());
            usuarioCambio.setContrasena(Utilidades.Encriptar(cambioClave.getContrasena()));
            usuarioCambio.setCambio("1");
            usuarioCambio.setEstatus(resultado.getEstatus());        
            usuarioCambio.setPerfilId(resultado.getPerfilId());
            usuarioCambio.setFechaExpira(fechaExpira);        
            usuarioCambio.setUltimoAcceso(resultado.getUltimoAcceso());
            usuarioCambio.setIntentosFallidos(resultado.getIntentosFallidos());        
            usuarioCambio.setEmail(resultado.getEmail());       
            usuarioCambio.setFechaCreacion(resultado.getFechaCreacion());     

            resultadoHistorico = historico.getHistoricoByUserIdTipo(usuarioLogin.get(0).getId(), "c");
            if(historico.getHistoricoByUserIdValorTipo(usuarioLogin.get(0).getId(), Utilidades.Encriptar(cambioClave.getContrasena()), "c").isEmpty()){
                usuarioAdmin.updateUsuario(usuarioCambio);
                registro.setTipo("c");
                registro.setUsuarioId(usuarioLogin.get(0).getId());
                registro.setValor(Utilidades.Encriptar(cambioClave.getContrasena()));

                if(resultadoHistorico.size() >= Integer.parseInt(parametro.getByCodigo("historico.contrasena").get(0).getValor())){
                    registro.setId(resultadoHistorico.get(0).getId());
                    historico.updateHistorico(registro);
                }else{
                    historico.saveHistorico(registro);
                }                

                bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 8, "Cambio de Clave Exitoso", fechaSQL, 0, request.getRemoteAddr(), "", "");
            } else {           
                user = usuarioAdmin.getById(usuarioLogin.get(0).getId());
                modelo.addAttribute("messageContrasena", "La contraseña coincide con una de las anteriores");            
                return new ModelAndView("cambioClave", "model", modelo);
            }       

            resultadocambio = usuarioAdmin.getByLogin(resultado.getLogin());

            misession.setAttribute("usuario.datos",resultadocambio);
            return new ModelAndView(new RedirectView("presentacion.htm"));
        
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 1, 8, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageContrasena", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("cambioClave", "model", modelo);
        }                 
        
    }       
    
}
