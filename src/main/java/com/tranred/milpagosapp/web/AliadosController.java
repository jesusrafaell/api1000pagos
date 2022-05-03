package com.tranred.milpagosapp.web;

import com.tranred.milpagosapp.domain.Aliado;
import com.tranred.milpagosapp.domain.CodigoArea;
import com.tranred.milpagosapp.domain.Estadistica;
import com.tranred.milpagosapp.domain.Estatus;
import com.tranred.milpagosapp.domain.Recaudo;
import com.tranred.milpagosapp.domain.TipoPagoAliado;
import com.tranred.milpagosapp.domain.Usuario;
import com.tranred.milpagosapp.domain.ZonaAtencion;
import com.tranred.milpagosapp.repository.IEstatusDAO;
import com.tranred.milpagosapp.repository.JPACodigoAreaDAO;
import com.tranred.milpagosapp.repository.JPAZonaAtencionDAO;
import com.tranred.milpagosapp.service.ConsultaAliadoForm;
import com.tranred.milpagosapp.service.EstadisticaForm;
import com.tranred.milpagosapp.service.IAdminUsuario;
import com.tranred.milpagosapp.service.IAliadosService;
import com.tranred.milpagosapp.service.IBitacoraService;
import com.tranred.milpagosapp.util.Utilidades;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JTable;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 *  Clase actua como controlador para el módulo de Aliados Comerciales.
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 * 
 */

@Controller
public class AliadosController {
    
    protected final Log logger = LogFactory.getLog(getClass());
    
    @Autowired
    private IAdminUsuario usuarioAdmin;
    
    @Autowired
    private IAliadosService aliadosService;
    
    @Autowired
    private JPACodigoAreaDAO codigosDAO;
    
    @Autowired
    private IEstatusDAO estatusDAO;
    
    @Autowired
    private JPAZonaAtencionDAO zonasDAO;
    
    @Autowired
    private IBitacoraService bitacora;
    
    //Asignamos el estado inicial de los formularios crear, consultar y editar con las propiedades de la clase Aliado
    @ModelAttribute("crearAliado")
    public Aliado createModelCrear() {
        return new Aliado();
    }
    
    @ModelAttribute("resultadoConsultaAliado")
    public Aliado createModelConsultaAliado() {
        return new Aliado();
    }
    
    @ModelAttribute("consultaAliado")
    public ConsultaAliadoForm createModelConsulta() {
        return new ConsultaAliadoForm();
    }
    
    @ModelAttribute("consultaEstadisticas")
    public EstadisticaForm createModelConsultaEstadisticas() {
        return new EstadisticaForm();
    }
    
    @RequestMapping(value="/crearAliado.htm", method = RequestMethod.GET)
    protected ModelAndView crearAliado(@RequestParam(value = "messageError") String messageError, HttpServletRequest request, Model modelo) throws ServletException {
        
        Map<String, Object> myModel = new HashMap<>();
        if("0".equals(messageError)){
            messageError = "Aliado Creado Satisfactoriamente";
        }else{
            messageError = "";
        }
        myModel.put("messageError", messageError);
        return new ModelAndView("crearAliado", "model", myModel);
    }
    
    @RequestMapping(value="/resultadoConsultaAliado.htm", method = RequestMethod.GET)
    protected ModelAndView resultadoConsultaAliado(@RequestParam(value = "tipo") String tipo,@RequestParam(value = "identifica") String identificacion,@RequestParam(value = "messageError") String messageError, HttpServletRequest request, Model modelo) throws ServletException {
        
        Aliado resultadoConsultaAliado = new Aliado();        
        List<Aliado> resultado;
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuarioLogin = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        if("0".equals(messageError)){
            modelo.addAttribute("messageError", "Registro actualizado satisfactoriamente");
        }        
        
        try{
            
            resultado = aliadosService.getAliadoByIdentificacion(tipo, identificacion);
            resultadoConsultaAliado.setId(resultado.get(0).getId());
            resultadoConsultaAliado.setApellidos(resultado.get(0).getApellidos());
            resultadoConsultaAliado.setNombres(resultado.get(0).getNombres());        
            resultadoConsultaAliado.setCodZonaAtencion(resultado.get(0).getCodZonaAtencion());            
            resultadoConsultaAliado.setCodigoCelular(resultado.get(0).getCodigoCelular());
            resultadoConsultaAliado.setCelular(resultado.get(0).getCelular());
            resultadoConsultaAliado.setCodigoTelHabitacion(resultado.get(0).getCodigoTelHabitacion());
            resultadoConsultaAliado.setTelefonoHabitacion(resultado.get(0).getTelefonoHabitacion());           
            resultadoConsultaAliado.setEmail(resultado.get(0).getEmail());
            resultadoConsultaAliado.setFechaNacimiento(Utilidades.cambiaFormatoFecha3(resultado.get(0).getFechaNacimiento()));
            resultadoConsultaAliado.setTipoIdentificacion(resultado.get(0).getTipoIdentificacion());                
            resultadoConsultaAliado.setIdentificacion(resultado.get(0).getIdentificacion());                    
            resultadoConsultaAliado.setProfesion(resultado.get(0).getProfesion());            
            resultadoConsultaAliado.setSexo(resultado.get(0).getSexo());                
            resultadoConsultaAliado.setEstatus(resultado.get(0).getEstatus());
            resultadoConsultaAliado.setCodModalidadPago(resultado.get(0).getCodModalidadPago());
            resultadoConsultaAliado.setCuentaAbono(resultado.get(0).getCuentaAbono());
            resultadoConsultaAliado.setDireccion(resultado.get(0).getDireccion());
            resultadoConsultaAliado.setRecaudos(resultado.get(0).getRecaudos());
            resultadoConsultaAliado.setObservaciones(resultado.get(0).getObservaciones());
            
            modelo.addAttribute("resultadoConsultaAliado", resultadoConsultaAliado);
            return new ModelAndView("resultadoConsultaAliado", "model", modelo);
        
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 12, "No se ha podido consultar el registro debido al siguiente error: " + cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");                        
            logger.error(cve.getMessage());
            return new ModelAndView("resultadoConsultaAliado", "model", modelo); 
        }
        
    }
    
    @RequestMapping(value="/consultaAliado.htm", method = RequestMethod.GET)
    protected ConsultaAliadoForm consultaAliado(HttpServletRequest request, Model modelo) throws ServletException {
                
        ConsultaAliadoForm consultaAliado = new ConsultaAliadoForm();        
        return consultaAliado;
    }
    
    @RequestMapping(value="/consultaEstadisticas.htm", method = RequestMethod.GET)
    protected EstadisticaForm consultaEstadistica(HttpServletRequest request, Model modelo) throws ServletException {
                
        EstadisticaForm consultaEstadisticas = new EstadisticaForm();        
        return consultaEstadisticas;
    }
    
    @RequestMapping(value="/resultadoEstadistica.htm", method = RequestMethod.GET)    
    protected ModelAndView resultadoEstadistica(HttpServletRequest request, Model modelo) throws ServletException {
        
        
        return new ModelAndView("resultadoEstadistica");
    }
    
    //Listado de Aliados
    @RequestMapping(value="/listadoAliados.htm")
    public ModelAndView listadoAliados(@RequestParam(value = "zona") String zona,HttpServletRequest request, HttpServletResponse response, Model modelo)
            throws ServletException, IOException {
        
        List<Aliado> resultado;
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuarioLogin = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        try{
        
            resultado = aliadosService.getAliadosByZonaAtencion(zona);        
            modelo.addAttribute("listadoAliados", resultado);
            return new ModelAndView("listadoAliados","model",modelo);
        
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 12, "No se ha podido consultar el registro debido al siguiente error: " + cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");                        
            logger.error(cve.getMessage());
            return new ModelAndView("listadoAliados", "model", modelo); 
        }
                
        
    }
    //Fin estado inicial
    
    //Accion post del formulario Crear Aliado
    @RequestMapping(value="/crearAliado.htm", method = RequestMethod.POST)
    public ModelAndView crearAliadoSubmit(@ModelAttribute("crearAliado") @Valid Aliado crearAliado, BindingResult result, Model modelo, HttpServletRequest request)
    {                
        if (result.hasErrors()) {
            return new ModelAndView("crearAliado");
        }
                
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuarioLogin = (List<Usuario>) misession.getAttribute("usuario.datos");
        List<Usuario> usuarioAliado;
        Map<String, Object> myModel = new HashMap<>();
        crearAliado.setEstatus(1);
        
        try {
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 11, "Crea Aliado: "+ crearAliado.getTipoIdentificacion() + crearAliado.getIdentificacion() +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
            
            usuarioAliado = usuarioAdmin.getByIdentificacion(crearAliado.getTipoIdentificacion(), crearAliado.getIdentificacion());
            
            if(usuarioAliado.isEmpty()){
                crearAliado.setIdUsuario(0);
            }else{
                crearAliado.setIdUsuario(usuarioAliado.get(0).getId());
            } 
            
            crearAliado.setFechaNacimiento(Utilidades.cambiaFormatoFecha(crearAliado.getFechaNacimiento()));
            aliadosService.saveAliado(crearAliado);
            return new ModelAndView(new RedirectView("crearAliado.htm?messageError=0")); 
        
            } catch (javax.persistence.PersistenceException cve) {
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 11, "No se ha podido insertar el registro debido al siguiente error: El Aliado "+ crearAliado.getTipoIdentificacion() + crearAliado.getIdentificacion() +" ya existe.", Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            myModel.put("messageError", "No se ha podido insertar el registro debido al siguiente error: El Aliado "+ crearAliado.getTipoIdentificacion() + crearAliado.getIdentificacion() +" ya existe.");
            return new ModelAndView("crearAliado", "model", myModel);          
        } catch (org.hibernate.exception.ConstraintViolationException cve) {             
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 11, "No se ha podido insertar el registro debido al siguiente error: El Aliado "+ crearAliado.getTipoIdentificacion() + crearAliado.getIdentificacion() +" ya existe.", Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            myModel.put("messageError", "No se ha podido insertar el registro debido al siguiente error: El Aliado "+ crearAliado.getTipoIdentificacion() + crearAliado.getIdentificacion() +" ya existe.");
            return new ModelAndView("crearAliado", "model", myModel);
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 11, "No se ha podido insertar el registro debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            logger.error(cve.getMessage());
            return new ModelAndView("crearAliado", "model", modelo); 
        }                                                                      
        
    }
    
    
    //Accion post del formulario Consulta Aliado
    @RequestMapping(value="/consultaAliado.htm", method = RequestMethod.POST)
    public ModelAndView consultaAliadoSubmit(@ModelAttribute("consultaAliado") @Valid ConsultaAliadoForm consultaAliado, BindingResult result, Model modelo, HttpServletRequest request)
    {                
        if (result.hasErrors()) {
            return new ModelAndView("consultaAliado");
        }
        
        if(consultaAliado.getCodZonaAtencion().isEmpty() && consultaAliado.getIdentificacion().isEmpty()){
            result.rejectValue("codZonaAtencion","invalid", new Object[]{ consultaAliado.getCodZonaAtencion()}, "Debe ingresar por lo menos un parámetro de consulta");
            return new ModelAndView("consultaAliado");
        }
        
        Map<String, Object> myModel = new HashMap<>();
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuarioLogin = (List<Usuario>) misession.getAttribute("usuario.datos");                
        List<Aliado> resultado;
        
        try{
                                 
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 12, "Consulta Aliado: "+ consultaAliado.getTipoIdentificacion() + consultaAliado.getIdentificacion() +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
        
            if(!consultaAliado.getIdentificacion().isEmpty()){

                resultado = aliadosService.getAliadoByIdentificacion(consultaAliado.getTipoIdentificacion(), consultaAliado.getIdentificacion());

                if(resultado.isEmpty()){
                    myModel.put("messageError", "No se encontraron registros con los parametros seleccionados");
                    return new ModelAndView("consultaAliado","model", myModel);
                }else{            
                    return new ModelAndView(new RedirectView("resultadoConsultaAliado.htm?messageError=&tipo="+ consultaAliado.getTipoIdentificacion() +"&identifica="+ consultaAliado.getIdentificacion() +""));
                }        
            }else{

                resultado = aliadosService.getAliadosByZonaAtencion(consultaAliado.getCodZonaAtencion());

                if(resultado.isEmpty()){
                    myModel.put("messageError", "No se encontraron registros con los parametros seleccionados");
                    return new ModelAndView("consultaAliado","model", myModel);
                }else{            
                    return new ModelAndView(new RedirectView("listadoAliados.htm?zona="+ consultaAliado.getCodZonaAtencion()));
                }
            }        
            
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 12, "No se ha podido consultar el registro debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");                        
            logger.error(cve.getMessage());
            return new ModelAndView("consultaAliado", "model", modelo); 
        }
                               
    }
    
    //Accion post del formulario Resultado Consulta Aliado
    @RequestMapping(value="/resultadoConsultaAliado.htm", method = RequestMethod.POST)
    public ModelAndView resultadoConsultaAliadoSubmit(@ModelAttribute("resultadoConsultaAliado") @Valid Aliado resultadoConsultaAliado, BindingResult result, Model modelo, HttpServletRequest request)
    {                
        if (result.hasErrors() || (resultadoConsultaAliado.getEstatus() !=1 && (resultadoConsultaAliado.getCodModalidadPago().isEmpty() || (resultadoConsultaAliado.getCuentaAbono().isEmpty()) || resultadoConsultaAliado.getCuentaAbono() !="" && resultadoConsultaAliado.getCuentaAbono().length()<20))) {
            
            if(resultadoConsultaAliado.getCodModalidadPago().isEmpty()){
                result.rejectValue("codModalidadPago","invalid", new Object[]{ resultadoConsultaAliado.getCodModalidadPago()}, "Debe ingresar la modalidad de pago");
            }
            
            if(resultadoConsultaAliado.getCuentaAbono().isEmpty()){
                result.rejectValue("cuentaAbono","invalid", new Object[]{ resultadoConsultaAliado.getCuentaAbono()}, "Debe ingresar la cuenta para el abono");
            }
            
            if(resultadoConsultaAliado.getCuentaAbono() !="" && resultadoConsultaAliado.getCuentaAbono().length()<20){
            
                result.rejectValue("cuentaAbono","invalid", new Object[]{ resultadoConsultaAliado.getCuentaAbono()}, "Tiene que ser mayor o igual que 20");
                        
            }
            
            return new ModelAndView("resultadoConsultaAliado");
        }                
        
        Map<String, Object> myModel = new HashMap<>();
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuarioLogin = (List<Usuario>) misession.getAttribute("usuario.datos");        
        String oldValue, newValue;        
        try {
            
            oldValue = ToStringBuilder.reflectionToString(aliadosService.getById(resultadoConsultaAliado.getId()));
            resultadoConsultaAliado.setFechaNacimiento(Utilidades.cambiaFormatoFecha(resultadoConsultaAliado.getFechaNacimiento()));            
            aliadosService.updateAliado(resultadoConsultaAliado);
            newValue = ToStringBuilder.reflectionToString(aliadosService.getById(resultadoConsultaAliado.getId()));
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 12, "Modifica Aliado: "+ resultadoConsultaAliado.getTipoIdentificacion() + resultadoConsultaAliado.getIdentificacion() +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), oldValue, newValue);
            return new ModelAndView(new RedirectView("resultadoConsultaAliado.htm?messageError=0&tipo="+ resultadoConsultaAliado.getTipoIdentificacion() +"&identifica="+ resultadoConsultaAliado.getIdentificacion() +""));            
                 
        } catch (Exception cve) {
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 12, "No se ha podido actualizar el registro debido al siguiente error: " + cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");                        
            logger.error(cve.getMessage());
            return new ModelAndView("resultadoConsultaAliado", "model", modelo); 
        }     
                
    }                    
    
    //Accion post del formulario Resultado Consulta Estadistica
    @RequestMapping(value="/resultadoEstadistica.htm", method = RequestMethod.POST)
    public ModelAndView resultadoEstadisticaSubmit(BindingResult result, Model modelo, HttpServletRequest request)
    {                
        if (result.hasErrors()) {
            return new ModelAndView("resultadoEstadistica");
        }
        
        return new ModelAndView("resultadoEstadistica");
                
    }
    //Fin Post
    
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
    
    @ModelAttribute("codigoTelHabitacionList")
    public List codigoTelHabitacionList() {
        
        List<CodigoArea> codigoTelHabitacionList = null;
        
        try{            
            codigoTelHabitacionList = codigosDAO.getCodigosAreaList(1);               
        } catch (Exception cve) {            
            logger.error(cve.getMessage());            
        }                  
                		         
        return codigoTelHabitacionList;
    }
    
    @ModelAttribute("codigoCelularList")
    public List codigoCelularList() {
        
        List<CodigoArea> codigoCelularList = null;
        
        try{            
            codigoCelularList = codigosDAO.getCodigosAreaList(2);            
        } catch (Exception cve) {            
            logger.error(cve.getMessage());            
        }            
                		         
        return codigoCelularList;
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
    
    @ModelAttribute("estatusList")
    public List estatusList() {
        
        List<Estatus> estatusList = null;
        
        try{
            estatusList = estatusDAO.getEstatusByModulo("al");
        }catch (Exception cve) {            
            logger.error(cve.getMessage());            
        }                     
                		         
        return estatusList;
    }
    
    @ModelAttribute("tipoPagoList")
    public List tipoPagoList() {
        
        List<TipoPagoAliado> tipoPagoList = null;
        
        try{
            tipoPagoList = aliadosService.getTipoPagoAliado();
        }catch (Exception cve) {            
            logger.error(cve.getMessage());            
        }       
                		         
        return tipoPagoList;
    }
    
    @ModelAttribute("tipoEstadistica")
    public List tipoEstadistica() {
        
        List<Estadistica> tipoEstadistica = null;
        
        try{
            tipoEstadistica = aliadosService.getTipoEstadistica();
        }catch (Exception cve) {            
            logger.error(cve.getMessage());            
        }
                                   		         
        return tipoEstadistica;
    }
    
    @RequestMapping(value="/loadestadistica.htm", method = RequestMethod.GET)
    public @ResponseBody JTable GetEstadistica(HttpServletRequest request){

        String[] columnNames = {"first name","last name","sports"};
        Object[][] data = {{"faheem","Abbas","Cricket"},{"shazil","Hassan","Soccer"}};

        JTable table = new JTable(data,columnNames);
        
        return table;
		
    }
    //Carga los valores de los checkboxes
    @ModelAttribute("recaudosList")
    protected List<Recaudo> recaudosList(HttpServletRequest request) throws Exception {
	
	List<Recaudo> recaudosList = null;
        
        try{
            recaudosList = aliadosService.getRecaudosList("ac");
        }catch (Exception cve) {            
            logger.error(cve.getMessage());            
        }
        
	return recaudosList;
    }
}
