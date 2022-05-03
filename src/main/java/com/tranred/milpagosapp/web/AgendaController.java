package com.tranred.milpagosapp.web;

import com.tranred.milpagosapp.domain.Agenda;
import com.tranred.milpagosapp.domain.Aliado;
import com.tranred.milpagosapp.domain.CategoriaComercio;
import com.tranred.milpagosapp.domain.Comercio;
import com.tranred.milpagosapp.domain.ComercioConsulta;
import com.tranred.milpagosapp.domain.Contacto;
import com.tranred.milpagosapp.domain.Eventos;
import com.tranred.milpagosapp.domain.Usuario;
import com.tranred.milpagosapp.domain.ZonaAtencion;
import com.tranred.milpagosapp.repository.JPAZonaAtencionDAO;
import com.tranred.milpagosapp.service.ConsultaComercioForm;
import com.tranred.milpagosapp.service.ConsultaVisitasForm;
import com.tranred.milpagosapp.service.IAliadosService;
import com.tranred.milpagosapp.service.IBitacoraService;
import com.tranred.milpagosapp.service.IComercioService;
import com.tranred.milpagosapp.service.ProgVisitaForm;
import com.tranred.milpagosapp.util.Utilidades;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 *  Clase actua como controlador para el módulo de Agenda
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 * 
 */

@Controller
public class AgendaController {
    
    protected final Log logger = LogFactory.getLog(getClass());
    
    @Autowired
    private IAliadosService aliadosService;
    
    @Autowired
    private IComercioService comercioService;
    
    @Autowired
    private IBitacoraService bitacora;
    
    @Autowired
    private JPAZonaAtencionDAO zonasDAO;
    
    private Map<String, CategoriaComercio> categoriaCache;
    
    //Asignamos el estado inicial de los formularios crear, consultar y editar con las propiedades de la clase Agenda
    @ModelAttribute("consultaAgenda")
    public Agenda createModelConsulta() {
        return new Agenda();
    }
    
    @ModelAttribute("consultaVisitasAliado")
    public ConsultaVisitasForm createModelConsultaVisitas() {
        return new ConsultaVisitasForm();
    }
    
    @ModelAttribute("programarVisita")
    public ProgVisitaForm createModelProgramar() {
        return new ProgVisitaForm();
    }
    
    @ModelAttribute("programarVisitaConsulta")
    public ConsultaComercioForm createModelProgramarConsulta() {
        return new ConsultaComercioForm();
    }
    
    @RequestMapping(value="/consultaAgenda.htm", method = RequestMethod.GET)
    protected ModelAndView consultaAgenda(HttpServletRequest request, Model modelo) throws ServletException {
        
        return new ModelAndView("consultaAgenda");
    } 
    
    @RequestMapping(value="/consultaVisitasAliado.htm", method = RequestMethod.GET)
    protected ModelAndView consultaVisitasAliado(HttpServletRequest request, Model modelo) throws ServletException {
        
        return new ModelAndView("consultaVisitasAliado");
    }
    
    @RequestMapping(value="/programarVisitaConsulta.htm", method = RequestMethod.GET)
    protected ModelAndView programarVisitaConsulta(HttpServletRequest request, Model modelo) throws ServletException {
        
        
            return new ModelAndView("programarVisitaConsulta");                       
    }
    
    @RequestMapping(value="/programarVisita.htm", method = RequestMethod.GET)
    protected ModelAndView programarVisita(@RequestParam(value = "messageError") String messageError,@RequestParam(value = "tipo") String tipo, @RequestParam(value = "identifica") String identifica, HttpServletRequest request, Model modelo) throws ServletException {
        
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuarioLogin = (List<Usuario>) misession.getAttribute("usuario.datos");
        List<Aliado> aliado;
        List<ComercioConsulta> resultado;                                
        ProgVisitaForm comercio = new ProgVisitaForm();       
        String existeAliado;
                
        if("0".equals(messageError)){
            messageError = "Visita Programada Satisfactoriamente";            
        }else{
            messageError = "";
        }                
        
        try{
            
            aliado = aliadosService.getAliadoByIdUsuario(usuarioLogin.get(0).getId());
        
            if(aliado.isEmpty()){
                existeAliado = "0";            
            }else{
                existeAliado = "1";
            }
            
            //Si el comercio existe buscamos su informacion
            if(!tipo.isEmpty()){                                
                
                resultado = comercioService.getComercioByRIF(tipo + identifica);
                comercio.setTipoIdentificacion(tipo);
                comercio.setRifComercio(identifica);
                comercio.setCodigoComercio(resultado.get(0).getCodigoComercio());                
                comercio.setDescripcionComercio(resultado.get(0).getDescripcionComercio());
                comercio.setTelefonoLocal(resultado.get(0).getTelefonoHabitacion());
                comercio.setTelefonoMovil(resultado.get(0).getTelefonoCelular());
                comercio.setEmail(resultado.get(0).getEmail());
                comercio.setCodigoCategoria(resultado.get(0).getCategoriaComercio());
                comercio.setContactoNombres(resultado.get(0).getNombreContacto());
                comercio.setContactoApellidos(resultado.get(0).getApellidoContacto());
                comercio.setDireccionComercio(resultado.get(0).getDireccionComercio());                
                
            }
            
            comercio.setMessageError(messageError);
            comercio.setExisteAliado(existeAliado);            
            return new ModelAndView("programarVisita", "programarVisita", comercio);
            
        }catch (Exception cve) {            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");                        
            logger.error(cve.getMessage());
            return new ModelAndView("programarVisita", "model", modelo); 
        }        
                
    }
    
    @RequestMapping(value="/consultaVisita.htm", method = RequestMethod.GET)
    protected ModelAndView consultaVisita(@RequestParam(value = "idAgenda") String idAgenda, HttpServletRequest request, Model modelo) throws ServletException, ParseException {
        
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuarioLogin = (List<Usuario>) misession.getAttribute("usuario.datos");
        List<Aliado> aliado;
        List<ComercioConsulta> comercio;       
        String existeAliado;
        
        try{
            
            aliado = aliadosService.getAliadoByIdUsuario(usuarioLogin.get(0).getId());        
        
            if(aliado.isEmpty()){
                existeAliado = "0";            
            }else{
                existeAliado = "1";
            }

            ProgVisitaForm consultaVisita = new ProgVisitaForm();                
            Agenda agenda;
            agenda = aliadosService.getAgendaById(Integer.parseInt(idAgenda));
            comercio = comercioService.getComercioById(agenda.getIdComercio());                

            consultaVisita.setIdAgenda(agenda.getId());
            consultaVisita.setAliadoComercial(String.valueOf(agenda.getIdAliado()));
            consultaVisita.setCodigoCategoria(comercio.get(0).getCategoriaComercio());
            consultaVisita.setCodigoComercio(agenda.getIdComercio());
            consultaVisita.setCodigoContacto(Integer.parseInt(comercio.get(0).getCodigoContacto()));
            consultaVisita.setContactoApellidos(comercio.get(0).getApellidoContacto());
            consultaVisita.setContactoNombres(comercio.get(0).getNombreContacto());
            consultaVisita.setDescripcionComercio(comercio.get(0).getDescripcionComercio());
            consultaVisita.setDireccionComercio(comercio.get(0).getDireccionComercio());
            consultaVisita.setEmail(comercio.get(0).getEmail());
            consultaVisita.setFechaVisitaFin(Utilidades.convierteFechaHoraString(agenda.getFechaFin()));
            consultaVisita.setFechaVisitaInicio(Utilidades.convierteFechaHoraString(agenda.getFechaInicio()));
            consultaVisita.setObservacionesComercio(agenda.getObservacionesAgenda());
            consultaVisita.setRifComercio(comercio.get(0).getRifComercio().substring(1));
            consultaVisita.setTelefonoLocal(comercio.get(0).getTelefonoHabitacion());
            consultaVisita.setTelefonoMovil(comercio.get(0).getTelefonoCelular());
            consultaVisita.setTituloVisita(agenda.getTitulo());
            consultaVisita.setTipoIdentificacion(comercio.get(0).getRifComercio().substring(0,1));
            consultaVisita.setExisteAliado(existeAliado);
            consultaVisita.setEstatusComercio(comercio.get(0).getEstatus());        

            //String imagen = Base64.encodeBase64String(comercio.get(0).getImagenComercio());                   
            StringBuilder sb = new StringBuilder();
            sb.append("data:image/jpeg;base64,");
            sb.append(StringUtils.newStringUtf8(Base64.encodeBase64(comercio.get(0).getImagenComercio(), false)));
            String imagen = sb.toString();
            modelo.addAttribute("imagen", imagen);
            modelo.addAttribute("consultaVisita", consultaVisita);
            return new ModelAndView("consultaVisita", "model", modelo);            
            
        }catch (Exception cve) {            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");                        
            logger.error(cve.getMessage());
            return new ModelAndView("consultaVisita", "model", modelo); 
        }
                                        
    }
    
    @InitBinder
    @RequestMapping(value="/programarVisita.htm")
    protected void initBinder(WebDataBinder binder) throws Exception {
	binder.registerCustomEditor(Set.class, "actividadComercialList", new CustomCollectionEditor(Set.class) {
            @Override
            protected Object convertElement(Object element) {
                if (element instanceof CategoriaComercio) {                            
                    return element;
                }
                if (element instanceof String) {
                    CategoriaComercio categorias = categoriaCache.get(element);				
                    return categorias;
                }			
                return null;
            }
	});
    }
    
    //Listado de Visitas Programadas
    @RequestMapping(value="/listadoVisitasAliados.htm")
    public ModelAndView listadoVisitasAliados(@RequestParam(value = "zona") String zona, @RequestParam(value = "aliado") String aliado, HttpServletRequest request, HttpServletResponse response, Model modelo)
            throws ServletException, IOException {
        
        List<Agenda> resultado;
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuarioLogin = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        try{
            if(!aliado.isEmpty()){
                resultado = aliadosService.getAgendaByAliadoList(Integer.parseInt(aliado));
            }else{
                resultado = aliadosService.getAgendaByZonaList(Integer.parseInt(zona));
            }
            
            modelo.addAttribute("listadoVisitasAliados", resultado);
            return new ModelAndView("listadoVisitasAliados","model",modelo);
        
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 12, "No se ha podido consultar el registro debido al siguiente error: " + cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");                        
            logger.error(cve.getMessage());
            return new ModelAndView("listadoAliados", "model", modelo); 
        }
                        
    }
    //Fin estado inicial        
    
    //Accion post del formulario Programar Visita
    @RequestMapping(value="/programarVisita.htm", method = RequestMethod.POST)
    public ModelAndView programarVisitaSubmit(@ModelAttribute("programarVisita") @Valid ProgVisitaForm programarVisita, BindingResult result, Model modelo, HttpServletRequest request) throws ParseException, FileNotFoundException, IOException
    {    
        Map<String, Object> myModel = new HashMap<String, Object>();
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuarioLogin = (List<Usuario>) misession.getAttribute("usuario.datos");
        List<Aliado> aliado;
        
        try{
            
            aliado = aliadosService.getAliadoByIdUsuario(usuarioLogin.get(0).getId());
        
            if(aliado.isEmpty()){
                myModel.put("existeAliado", "0");
            }else{
                myModel.put("existeAliado", "1");
            }                

            if (result.hasErrors() || (aliado.isEmpty() && programarVisita.getAliadoComercial().isEmpty())) {
                
                if(aliado.isEmpty() && programarVisita.getAliadoComercial().isEmpty()){
                    if(programarVisita.getAliadoComercial().isEmpty()){
                        result.rejectValue("aliadoComercial","invalid", new Object[]{ programarVisita.getAliadoComercial()}, "Debe indicar el aliado comercial");                        
                    }            
                }
                return new ModelAndView("programarVisita", "model", myModel);
            }            

            Comercio comercio = new Comercio();
            Agenda visita = new Agenda();
            List<ComercioConsulta> comercioSave;
            Contacto contacto = new Contacto();                
            String tipoIdentificacion = programarVisita.getTipoIdentificacion();

            //Comercio                        
            comercioSave = comercioService.getComercioByRIF(programarVisita.getTipoIdentificacion() + programarVisita.getRifComercio());
            
            if(comercioSave.isEmpty()){
                
//                if(!programarVisita.getImagenComercio().isEmpty()){
//                    File imagen = new File(programarVisita.getImagenComercio().getOriginalFilename());
//                    programarVisita.getImagenComercio().transferTo(imagen); 
//                    InputStream is = new FileInputStream(imagen);
//                    byte[] buffer = new byte[(int) imagen.length()];
//                    long tamano = imagen.length();
//                    if((imagen.length()/1024)>500){
//                        result.rejectValue("imagenComercio","invalid", new Object[]{ programarVisita.getImagenComercio()}, "El tamaño no debe ser superior a 500KB");
//                        return new ModelAndView("programarVisita");
//                    }else{
//                        comercio.setImagenComercio(buffer);
//                    }            
//                }
                comercio.setDescripcionComercio(programarVisita.getDescripcionComercio());
                comercio.setDireccionComercio(programarVisita.getDireccionComercio());                
                comercio.setEstatusComercio(4);                
                comercio.setObservacionesComercio(programarVisita.getObservacionesComercio());        
                switch (tipoIdentificacion) {
                    case "V":
                    case "E":
                    case "P":
                        comercio.setTipoPersonaComercio(1);
                        break;
                    case "J":
                    case "G": 
                    case "C":
                    case "R":     
                        comercio.setTipoPersonaComercio(2);
                        break;
                }
                comercio.setRifComercio(programarVisita.getTipoIdentificacion() + programarVisita.getRifComercio());
                comercio.setCodigoCategoria(Integer.parseInt(programarVisita.getCodigoCategoria()));        
                comercioService.saveComercio(comercio); 
                
                comercioSave = comercioService.getComercioByRIF(programarVisita.getTipoIdentificacion() + programarVisita.getRifComercio());
                
                //Guarda Contacto
                contacto.setCodigoComercio(comercioSave.get(0).getCodigoComercio());
                contacto.setContactoNombres(programarVisita.getContactoNombres());
                contacto.setContactoApellidos(programarVisita.getContactoApellidos());
                contacto.setEmail(programarVisita.getEmail());
                contacto.setTelefonoLocal(programarVisita.getTelefonoLocal());
                contacto.setTelefonoMovil(programarVisita.getTelefonoMovil());
                comercioService.saveContacto(contacto);
                
                //Guarda ComercioXAfiliado
                comercioService.saveComerciosXafiliado("000000720004108", comercioSave.get(0).getCodigoComercio());
                
                bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 16, "Crea Comercio: "+ programarVisita.getRifComercio() +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
            }                                                
                                                    
            //Programar Visita
            visita.setFechaInicio(Utilidades.convierteFechaHoraSql(programarVisita.getFechaVisitaInicio()));
            visita.setFechaFin(Utilidades.convierteFechaHoraSql(programarVisita.getFechaVisitaFin()));
            if("0".equals(programarVisita.getExisteAliado())){
                visita.setIdAliado(Integer.parseInt(programarVisita.getAliadoComercial()));
            }else{
                visita.setIdAliado(usuarioLogin.get(0).getId());
            }
            visita.setIdComercio(comercioSave.get(0).getCodigoComercio());
            visita.setObservacionesAgenda(programarVisita.getObservacionesComercio());
            visita.setTitulo(programarVisita.getTituloVisita());
            aliadosService.saveAgenda(visita);
                                  
            return new ModelAndView(new RedirectView("programarVisita.htm?messageError=0&tipo=&identifica=")); 
            
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 16, "No se ha podido insertar el registro debido al siguiente error: " + cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");                        
            logger.error(cve.getMessage());
            return new ModelAndView("programarVisita", "programarVisita", modelo); 
        }                                                                                                             
        
    }                
    
    //Accion post del formulario Consulta Visita
    @RequestMapping(value="/consultaVisita.htm", method = RequestMethod.POST)
    public ModelAndView consultaVisitaSubmit(@ModelAttribute("consultaVisita") @Valid ProgVisitaForm consultaVisita, BindingResult result, Model modelo, HttpServletRequest request) throws ParseException
    {                
        if (result.hasErrors()) {
            return new ModelAndView("consultaVisita");
        }
                
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuarioLogin = (List<Usuario>) misession.getAttribute("usuario.datos");                                       
        Comercio comercio = new Comercio();
        Agenda visita = new Agenda();        
        Contacto contacto = new Contacto();               
        
        String tipoIdentificacion = consultaVisita.getTipoIdentificacion(), oldValue, newValue;
        
        //Comercio
        comercio.setCodigoComercio(consultaVisita.getCodigoComercio());
        comercio.setDescripcionComercio(consultaVisita.getDescripcionComercio());
        comercio.setDireccionComercio(consultaVisita.getDireccionComercio());        
        comercio.setObservacionesComercio(consultaVisita.getObservacionesComercio());  
        switch (tipoIdentificacion) {
            case "V":
            case "E":
            case "P":
                comercio.setTipoPersonaComercio(1);
                break;
            case "J":
            case "G":
            case "C":
            case "R":     
                comercio.setTipoPersonaComercio(2);
                break;
        }
        comercio.setRifComercio(consultaVisita.getTipoIdentificacion() + consultaVisita.getRifComercio());
        comercio.setCodigoCategoria(Integer.parseInt(consultaVisita.getCodigoCategoria()));
                
        try {
            
            oldValue = ToStringBuilder.reflectionToString(comercioService.getById(comercio.getCodigoComercio()));
            comercioService.updateComercio(comercio);
            newValue = ToStringBuilder.reflectionToString(comercioService.getById(comercio.getCodigoComercio()));
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 16, "Modifica Comercio: "+ consultaVisita.getRifComercio() +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), oldValue, newValue);
                        
            //Guarda Contacto
            contacto.setCodigoComercio(consultaVisita.getCodigoComercio());
            contacto.setContactoNombres(consultaVisita.getContactoNombres());
            contacto.setContactoApellidos(consultaVisita.getContactoApellidos());
            contacto.setEmail(consultaVisita.getEmail());
            contacto.setTelefonoLocal(consultaVisita.getTelefonoLocal());
            contacto.setTelefonoMovil(consultaVisita.getTelefonoMovil());
            contacto.setCodigoContacto(String.valueOf(consultaVisita.getCodigoContacto()));
            comercioService.updateContacto(contacto);           

            //Guarda Visita
            //Programar Visita
            visita.setFechaInicio(Utilidades.convierteFechaHoraSql(consultaVisita.getFechaVisitaInicio()));
            visita.setFechaFin(Utilidades.convierteFechaHoraSql(consultaVisita.getFechaVisitaFin()));
            if("0".equals(consultaVisita.getExisteAliado())){
                visita.setIdAliado(Integer.parseInt(consultaVisita.getAliadoComercial()));
            }else{
                visita.setIdAliado(usuarioLogin.get(0).getId());
            }
            visita.setIdComercio(consultaVisita.getCodigoComercio());
            visita.setObservacionesAgenda(consultaVisita.getObservacionesComercio());
            visita.setTitulo(consultaVisita.getTituloVisita());
            visita.setId(consultaVisita.getIdAgenda());
            aliadosService.updateAgenda(visita);
            
            modelo.addAttribute("messageError", "Registro actualizado satisfactoriamente");
            return new ModelAndView("consultaVisita", "model", modelo);
                                                     
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 16, "No se ha podido actualizar el registro debido al siguiente error: " + cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");                        
            logger.error(cve.getMessage());
            return new ModelAndView("consultaVisita", "model", modelo); 
        }                               
        
    }
    
    //Accion post del formulario Consulta Visitas Aliado
    @RequestMapping(value="/consultaVisitasAliado.htm", method = RequestMethod.POST)
    public ModelAndView consultaVisitasAliadoSubmit(@ModelAttribute("consultaVisitasAliado") @Valid ConsultaVisitasForm consultaVisitasAliado, BindingResult result, Model modelo, HttpServletRequest request) throws ParseException
    {                
        if (result.hasErrors()) {
            return new ModelAndView("consultaVisitasAliado");
        }
        
        if(consultaVisitasAliado.getAliadoComercial().isEmpty() && consultaVisitasAliado.getCodZonaAtencion().isEmpty()){            
            result.rejectValue("codZonaAtencion","invalid", new Object[]{ consultaVisitasAliado.getCodZonaAtencion()}, "Debe indicar por lo menos un parámetro de busqueda");
            return new ModelAndView("consultaVisitasAliado");                        
        }
        
        List<Agenda> resultado;
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuarioLogin = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        try{
            if(!consultaVisitasAliado.getAliadoComercial().isEmpty()){
                resultado = aliadosService.getAgendaByAliadoList(Integer.parseInt(consultaVisitasAliado.getAliadoComercial()));
            }else{
                resultado = aliadosService.getAgendaByZonaList(Integer.parseInt(consultaVisitasAliado.getCodZonaAtencion()));
            }
            
            modelo.addAttribute("aliado", consultaVisitasAliado.getAliadoComercial());
            modelo.addAttribute("zona", consultaVisitasAliado.getCodZonaAtencion());
            modelo.addAttribute("listadoVisitasAliados", resultado);
            return new ModelAndView("listadoVisitasAliados","model",modelo);
        
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 12, "No se ha podido consultar el registro debido al siguiente error: " + cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");                        
            logger.error(cve.getMessage());
            return new ModelAndView("consultaVisitasAliado", "model", modelo); 
        }                     
        
    }
    
    //Accion post del formulario Programar Visita Consulta
    @RequestMapping(value="/programarVisitaConsulta.htm", method = RequestMethod.POST)
    public ModelAndView programarVisitaConsultaSubmit(@ModelAttribute("programarVisitaConsulta") @Valid ConsultaComercioForm programarVisitaConsulta, BindingResult result, Model modelo, HttpServletRequest request)
    {                
        if (result.hasErrors()) {
            return new ModelAndView("programarVisitaConsulta");
        }
        
        if(programarVisitaConsulta.getTipoConsulta()=="1" && programarVisitaConsulta.getIdentificacion().isEmpty()){
            result.rejectValue("identificacion","invalid", new Object[]{ programarVisitaConsulta.getIdentificacion()}, "Debe ingresar la identificación del Comercio");
            return new ModelAndView("programarVisitaConsulta");
        }
        
        Map<String, Object> myModel = new HashMap<>();
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuarioLogin = (List<Usuario>) misession.getAttribute("usuario.datos");                        
        
        try{                        
        
            if("1".equals(programarVisitaConsulta.getTipoConsulta())){

                List<ComercioConsulta> resultado;
                
                bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 15, "Programar Visita Comercio: "+ programarVisitaConsulta.getTipoIdentificacion() + programarVisitaConsulta.getIdentificacion() +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");

                resultado = comercioService.getComercioByRIF(programarVisitaConsulta.getTipoIdentificacion() + programarVisitaConsulta.getIdentificacion());

                if(resultado.isEmpty()){
                    myModel.put("messageError", "No se encontraron registros con los parametros seleccionados");
                    return new ModelAndView("programarVisitaConsulta","model", myModel);
                }else{            
                    return new ModelAndView(new RedirectView("programarVisita.htm?messageError=&tipo="+ programarVisitaConsulta.getTipoIdentificacion() +"&identifica="+ programarVisitaConsulta.getIdentificacion() +""));
                }
            }else{

                return new ModelAndView(new RedirectView("programarVisita.htm?messageError=&tipo=&identifica="));
            }
        
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 15, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("programarVisitaConsulta", "model", modelo);
        }                                
        
    }
    //Fin Post
    
    @RequestMapping(value="/loadagenda.htm", method = RequestMethod.GET)
    public @ResponseBody List GetEventos(HttpServletRequest request){

        List eventos = new ArrayList();
        List<Agenda> agenda;
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuarioLogin = (List<Usuario>) misession.getAttribute("usuario.datos");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        
        try{
            
            agenda = aliadosService.getAgendaByAliadoList(usuarioLogin.get(0).getId());

            /*Agregando los Eventos*/
            if(!agenda.isEmpty()){
                for(Agenda evento: agenda){                
                    eventos.add(new Eventos(evento.getTitulo(), String.valueOf(sdf.format(evento.getFechaInicio())), String.valueOf(sdf.format(evento.getFechaFin())), "consultaVisita.htm?idAgenda="+ evento.getId() +""));
                }
            }
        
        }catch (Exception cve) {                                    
            logger.error(cve.getMessage());            
        }                
		
        return eventos;
    }
    
    @ModelAttribute("aliadosList")
    public List aliadosList() {
        
        List<Aliado> aliadosList = null;
        
        try{            
            aliadosList = aliadosService.getAliadoList();            
        }catch (Exception cve) {            
            logger.error(cve.getMessage());            
        }        
                		         
        return aliadosList;
    }
    
    @ModelAttribute("tipoIdentificacionList")
    public Map<String,String> tipoIdentificacionList() {
   
        Map<String,String> tipo = new LinkedHashMap<>();
        tipo.put("V", "V");
        tipo.put("E", "E");
        tipo.put("P", "P");
        tipo.put("J", "J");
        tipo.put("G", "G");
        tipo.put("C", "C");
        tipo.put("R", "R");
        
        return tipo;
    }        
    
    @ModelAttribute("actividadComercialList")
    public List actividadComercialList() {
        
        List<CategoriaComercio> actividadComercialList = null;
        
        try{
            
            actividadComercialList = comercioService.getCategoriaComercioList();             
                
            categoriaCache = new HashMap<>();
            for (CategoriaComercio categorias : actividadComercialList) {
                    categoriaCache.put(categorias.getCodigoCategoriaAsString(), categorias);
            }
            
        }catch (Exception cve) {            
            logger.error(cve.getMessage());            
        }
        	         
        return actividadComercialList;
    }
    
    @ModelAttribute("zonasAtencionList")
    public List zonasAtencionList() {
        
        List<ZonaAtencion> zonasAtencionList = null;
        
        try{
            zonasAtencionList = zonasDAO.getZonasList();
        } catch (Exception cve) {            
            logger.error(cve.getMessage());            
        }        
                		         
        return zonasAtencionList;
    }
}
