package com.tranred.milpagosapp.web;

import com.tranred.milpagosapp.domain.ComercioConsulta;
import com.tranred.milpagosapp.domain.HistoricoFacturacion;
import com.tranred.milpagosapp.domain.Usuario;
import com.tranred.milpagosapp.service.GenerarArchivosService;
import com.tranred.milpagosapp.service.GenerarFacturacionForm;
import com.tranred.milpagosapp.service.IBitacoraService;
import com.tranred.milpagosapp.service.IComercioService;
import com.tranred.milpagosapp.util.Utilidades;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.tranred.milpagosapp.service.IGenerarArchivosService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 *  Clase actua como controlador para el módulo de Facturacion
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 * 
 */
@Controller
@EnableAsync
public class FacturacionController {
    
    protected final Log logger = LogFactory.getLog(getClass());    
    
    @Autowired
    private IBitacoraService bitacora;
    
    @Autowired
    private IComercioService comercioService;
    
    @Autowired
    private IGenerarArchivosService generarFacturacion;
    
    //Asignamos el estado inicial del formulario generarFacturacionComercios
    @ModelAttribute("generarFacturacionComercios")
    public GenerarFacturacionForm createModelCrear() {
        return new GenerarFacturacionForm();
    }
    
    /* Generar Facturacion a Comercios */
    @RequestMapping(value="/generarFacturacionComercios.htm", method = RequestMethod.GET)
    public ModelAndView generarFacturacionComercios(HttpServletRequest request, HttpServletResponse response, Model modelo)
            throws ServletException, IOException, ParseException {
                        
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuarioLogin = (List<Usuario>) misession.getAttribute("usuario.datos");                  
        
        try{
             
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 28, "Ingreso a opción Facturación Comercios", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");                        
            return new ModelAndView("generarFacturacionComercios");
        
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 28, "No se ha podido realizar la operacion debido al siguiente error: "+cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());           
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("generarFacturacionComercios");
        }        
        
    }        
    
    //Accion post del formulario generarFacturacionComercios
    @RequestMapping(value="/generarFacturacionComercios.htm", method = RequestMethod.POST)
    public ModelAndView generarFacturacionComerciosSubmit(@ModelAttribute("generarFacturacionComercios") @Valid GenerarFacturacionForm generarFacturacionComercios, BindingResult result, Model modelo, HttpServletRequest request) throws ParseException
    {                
        if (result.hasErrors()) {
            return new ModelAndView("generarFacturacionComercios");
        }
        
        if("1".equals(generarFacturacionComercios.getTipoConsulta()) && generarFacturacionComercios.getIdentificacion().isEmpty()){
            result.rejectValue("identificacion","invalid", new Object[]{ generarFacturacionComercios.getIdentificacion()}, "Debe ingresar la identificación del Comercio");
            return new ModelAndView("generarFacturacionComercios");
        }
        
        Map<String, Object> myModel = new HashMap<>();
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuarioLogin = (List<Usuario>) misession.getAttribute("usuario.datos");                        
        List<HistoricoFacturacion> resultado, terminales;
        List<ComercioConsulta> infoComercio;
        String fechaFin, contrato;
        
        try{
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 28, "Genera Facturación Comercios", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
      
            //Para un Comercio en especifico
            if("1".equals(generarFacturacionComercios.getTipoConsulta())){               
                
                fechaFin = Utilidades.ultimoDiaMes(generarFacturacionComercios.getMes(), generarFacturacionComercios.getAno()); 
                
                infoComercio = comercioService.getComercioByRIF(generarFacturacionComercios.getTipoIdentificacion()+generarFacturacionComercios.getIdentificacion());
                
                if(infoComercio.isEmpty()){
                    
                    myModel.put("messageError", "No se encontraron registros con los parametros seleccionados");
                    return new ModelAndView("generarFacturacionComercios","model", myModel);
                        
                }else{
                    
                    resultado = comercioService.getHistoricoFacturacionComercios(1, generarFacturacionComercios.getTipoContrato(), Utilidades.convierteFechaSqlsinHora(fechaFin), infoComercio.get(0).getCodigoComercio());
                    
                    terminales = comercioService.getHistoricoFacturacionComercios(3, generarFacturacionComercios.getTipoContrato(), Utilidades.convierteFechaSqlsinHora(fechaFin), infoComercio.get(0).getCodigoComercio());
                    
                    if(resultado.isEmpty()){
                        myModel.put("messageError", "No se encontraron registros con los parametros seleccionados");
                        return new ModelAndView("generarFacturacionComercios","model", myModel);
                    }else{  
                        
                        if(generarFacturacionComercios.getTipoContrato()==1){
                            contrato = "Temporal";
                        }else{
                            contrato = "Permanente";
                        }                                                
                        
                        generarFacturacion.generarFacturacionByComercio(generarFacturacionComercios.getMes(), generarFacturacionComercios.getAno(), contrato, Integer.parseInt(generarFacturacionComercios.getNumeroControl()), resultado, terminales);
                                                
                        myModel.put("messageError", "Ha iniciado el proceso para generar la Facturación y el Libro de Ventas, por favor verifique en 30 Minutos aproximadamente");
                        return new ModelAndView("generarFacturacionComercios","model", myModel);
                    }
                    
                }                                                                            
                                                       
            //Generar todas las Facturas segun el tipo de Contrato seleccionado   
            }else{
                                
                fechaFin = Utilidades.ultimoDiaMes(generarFacturacionComercios.getMes(), generarFacturacionComercios.getAno());                                 
                                                   
                resultado = comercioService.getHistoricoFacturacionComercios(2, generarFacturacionComercios.getTipoContrato(), Utilidades.convierteFechaSqlsinHora(fechaFin), 0);                

                if(resultado.isEmpty()){
                    myModel.put("messageError", "No se encontraron registros con los parametros seleccionados");
                    return new ModelAndView("generarFacturacionComercios","model", myModel);
                }else{  

                    if(generarFacturacionComercios.getTipoContrato()==1){
                        contrato = "Temporal";
                    }else{
                        contrato = "Permanente";
                    }
                    
                    generarFacturacion.generarFacturacionComercios(generarFacturacionComercios.getMes(), generarFacturacionComercios.getAno(), fechaFin, contrato, generarFacturacionComercios.getTipoContrato(), Integer.parseInt(generarFacturacionComercios.getNumeroControl()), resultado);                                                            
                    
                    myModel.put("messageError", "Ha iniciado el proceso para generar la Facturación y el Libro de Ventas, por favor verifique en 30 Minutos aproximadamente");
                    return new ModelAndView("generarFacturacionComercios","model", myModel);
                }              
                
            }
        
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 28, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("generarFacturacionComercios", "model", modelo);
        }                                
        
    }
    
    @ModelAttribute("tipoIdentificacionList")
    public Map<String,String> tipoIdentificacionList() {
   
        Map<String,String> tipo = new LinkedHashMap<String,String>();
        tipo.put("V", "V");
        tipo.put("E", "E");
        tipo.put("P", "P");
        tipo.put("J", "J");
        tipo.put("G", "G");
        
        return tipo;
    }
    
    @ModelAttribute("mesList")
    protected Map<Integer,String> mesList(HttpServletRequest request) throws Exception {
	
        Map<Integer,String> mesList = new LinkedHashMap<>();
	mesList = Utilidades.mesesAno();
	
	return mesList;
    }
    
    @ModelAttribute("anoList")
    protected List anoList(HttpServletRequest request) throws Exception {
	                
	List<Integer> anoList = new ArrayList<>();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = year; i>= year-4; i--) {
            anoList.add(i);
        }
	
	return anoList;
    }
    
}
