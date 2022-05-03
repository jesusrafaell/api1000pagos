/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tranred.milpagosapp.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tranred.milpagosapp.domain.Plan;
import com.tranred.milpagosapp.domain.TipoPlan;
import com.tranred.milpagosapp.domain.Moneda;
import com.tranred.milpagosapp.domain.Frecuencia;
import com.tranred.milpagosapp.domain.Usuario;
import com.tranred.milpagosapp.domain.Estatus;
import com.tranred.milpagosapp.domain.Perfil;
import com.tranred.milpagosapp.domain.PlanCuotaSP;
import com.tranred.milpagosapp.domain.PlanPago;
import com.tranred.milpagosapp.domain.Planes;
import com.tranred.milpagosapp.domain.PlanesConsulta;
import com.tranred.milpagosapp.repository.JPAPlanDAO;
import com.tranred.milpagosapp.repository.JPAFrecuenciaDAO;
import com.tranred.milpagosapp.repository.JPAMonedaDAO;
import com.tranred.milpagosapp.repository.JPATipoPlanDAO;
import com.tranred.milpagosapp.repository.IEstatusDAO;
import com.tranred.milpagosapp.repository.IPerfilDAO;
import com.tranred.milpagosapp.repository.JPAPlanPagoDAO;
import com.tranred.milpagosapp.service.GenerarCobranzaForm;
import com.tranred.milpagosapp.service.GenerarConsultaPlanesForm;
import com.tranred.milpagosapp.service.IAdminMantenimiento;
import com.tranred.milpagosapp.service.IBitacoraService;
import com.tranred.milpagosapp.service.IGenerarArchivosService;
import com.tranred.milpagosapp.service.IPlanesConsultaService;
import com.tranred.milpagosapp.service.PlanForm;
import com.tranred.milpagosapp.util.Utilidades;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Locale;
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
import org.springframework.web.servlet.view.RedirectView;
/**
 * Clase actua como controlador para las opciones Editar y Eliminar Planes.
 * @author jperez@emsys-solution.com
 * @version 0.1
 */

@Controller
public class MantPlanController {
    
    protected final Log logger = LogFactory.getLog(getClass());
    
    @Autowired
    private JPAPlanDAO plan;
    
    @Autowired
    private JPAPlanPagoDAO planpago;
    
    @Autowired
    private IAdminMantenimiento mantenimientoAdmin;
    
    @Autowired
    private IBitacoraService bitacora;
    
    @Autowired
    private JPAMonedaDAO moneda;
    
    @Autowired
    private JPATipoPlanDAO tipoPlan;
    
    @Autowired
    private JPAFrecuenciaDAO frecuencia;
    
    @Autowired
    private IEstatusDAO estatusDAO;   
    
    @Autowired
    private IPerfilDAO perfilDAO;   
    
    @Autowired
    private IPlanesConsultaService planesConsultaService;
    
    @Autowired
    private IGenerarArchivosService archivoConsultaPlanes;
    
    Locale español = new Locale("es");
    
    //Asignamos el estado inicial de los formularios con las propiedades de cada clase
    @ModelAttribute("crearPlan")
    public PlanForm createModelCrear() {
        return new PlanForm();
    }
    
    @ModelAttribute("editarPlan")
    public PlanForm  createModelEditar() {
        return new PlanForm();
    }
    
    @ModelAttribute("eliminarPlan")
    public PlanForm createModelPlan() {
        return new PlanForm();
    }
    
    //Asignamos el estado inicial del formulario generarConsultaPlanes
    @ModelAttribute("generarConsultaPlanes")
    public GenerarConsultaPlanesForm createModelConsulta() {
        return new GenerarConsultaPlanesForm();
    }
    
    @RequestMapping(value="/crearPlan.htm", method = RequestMethod.GET)
    protected ModelAndView crearPlan (HttpServletRequest request, Model modelo) throws ServletException {
                
        PlanForm crearPlan = new PlanForm();
        
        return new ModelAndView("crearPlan", "crearPlan", crearPlan);
                            
    }
    
    @RequestMapping(value="/listadoPlanes.htm")
    public ModelAndView listadoPlanes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Map<String, Object> myModel = new HashMap<String, Object>();       
        List<Planes> planes = this.plan.getInformationPlanList(2);
        myModel.put("listadoPlanes", planes);
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        Integer perfilId = new Integer(usuario.get(0).getPerfilId());
        Perfil perfil = perfilDAO.getById(perfilId);
        if (Arrays.asList(perfil.getOpciones().split(",")).contains(Integer.toString(40))) {                                
            myModel.put("permisoActualizacion", 1);
        }else{
            myModel.put("permisoActualizacion", 0);
        }
        bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "Planes", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
        
        return new ModelAndView("listadoPlanes", "model", myModel);
        
    }
    
     //Fin estado incial de los formularios
    
  
    
    @RequestMapping(value="/editarPlan.htm", method = RequestMethod.GET)
    protected ModelAndView editarPlan(@RequestParam(value = "id") int id,HttpServletRequest request, Model modelo) throws ServletException {
                
        PlanForm editarPlan= new PlanForm();
        Plan resultado = new Plan();
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        try{
            
            List<TipoPlan> tipoPlanList = this.tipoPlanList();
            //jackson convierte tu objeto en json
            ObjectMapper mapper = new ObjectMapper();
            String jsonTipoPlanList = mapper.writeValueAsString(tipoPlanList);
            resultado = plan.getById(id);           
            
            bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "Ingreso Mantenimiento Editar Plan", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
            //resultado.getMontoInicial().toString()
            for(TipoPlan t : tipoPlanList){
                if (Integer.toString(t.getCodigoTipoPlan()).equals(resultado.getCodtipoplan())){
                    editarPlan.setDescTipoPlan(t.getDescripcion());
                }
            }
            editarPlan.setId(resultado.getId());
            editarPlan.setCodtipoplan(resultado.getCodtipoplan());
            editarPlan.setNombre(resultado.getNombre());
            editarPlan.setDescripcion(resultado.getDescripcion());
            if(resultado.getMontoTarifa() == null){
                editarPlan.setMontoTarifa("0");
            }else{
                editarPlan.setMontoTarifa(resultado.getMontoTarifa().toString());
                editarPlan.setTipoPagoMantenimiento("montoTarifa");
            }
            if(resultado.getMontoInicial() == null){
                editarPlan.setMontoInicial("0");
            }else{
                editarPlan.setMontoInicial(resultado.getMontoInicial().toString());
            }
            if(resultado.getPorcComisionBancaria()== null){
                editarPlan.setPorcComisionBancaria("0");
            }else{
                editarPlan.setPorcComisionBancaria(resultado.getPorcComisionBancaria().toString());
                editarPlan.setTipoPagoMantenimiento("porcComisionBancaria");
            }
            if(resultado.getMontoFijo() == null || resultado.getMontoFijo() == 0){
                editarPlan.setMontoFijo(false);
            }else{
                editarPlan.setMontoFijo(true);
            }
            //
            editarPlan.setPlanplazo(Integer.toString(resultado.getPlanplazo()));
            
            //editarPlan.setMontoFijo(Integer.toString(resultado.getMontoFijo()));
            editarPlan.setFechainicio(resultado.getFechainicio());
            editarPlan.setFechafin(resultado.getFechafin());      
            if(resultado.getIndefinido()=="1")
                editarPlan.setIndefinido(true);
            else editarPlan.setIndefinido(false);
            editarPlan.setFrecuencia(resultado.getFrecuencia());
            editarPlan.setMoneda(resultado.getMoneda());
            editarPlan.setEstatus(resultado.getEstatus());
            editarPlan.setMontopromedio(resultado.getMontopromedio().toString());
            editarPlan.setTransaccion(Integer.toString(resultado.getTransaccion()));
            editarPlan.setDiasimpago(Integer.toString(resultado.getDiasimpago()));
            editarPlan.setPorcentaje(Integer.toString(resultado.getPorcentaje()));
            
            //json de tipo de planes            
            editarPlan.setJsonTipoPlanes(jsonTipoPlanList);
            return new ModelAndView("editarPlan", "editarPlan", editarPlan);
            
        }catch (Exception cve) {
            bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("editarPlan", "model", modelo);
        }        
    }
    
  @RequestMapping(value="/eliminarPlan.htm", method = RequestMethod.GET)
    protected ModelAndView eliminarPlan(@RequestParam(value = "id") int id,HttpServletRequest request, Model modelo) throws ServletException {
                
        PlanForm eliminarPlan= new PlanForm();
        Plan resultado = new Plan();
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        try{
            
            resultado = plan.getById(id);            

            bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "Ingreso Mantenimiento Eliminar Plan", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
            
            List<TipoPlan> tipoPlanList = this.tipoPlanList();
            //jackson convierte tu objeto en json
            ObjectMapper mapper = new ObjectMapper();
            String jsonTipoPlanList = mapper.writeValueAsString(tipoPlanList);           
            
            bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "Ingreso Mantenimiento Editar Plan", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
            //resultado.getMontoInicial().toString()
            for(TipoPlan t : tipoPlanList){
                if (Integer.toString(t.getCodigoTipoPlan()).equals(resultado.getCodtipoplan())){
                    eliminarPlan.setDescTipoPlan(t.getDescripcion());
                }
            }
            eliminarPlan.setId(resultado.getId());
            eliminarPlan.setCodtipoplan(resultado.getCodtipoplan());
            eliminarPlan.setNombre(resultado.getNombre());
            eliminarPlan.setDescripcion(resultado.getDescripcion());
            if(resultado.getMontoTarifa() == null){
                eliminarPlan.setMontoTarifa("0");
            }else{
                eliminarPlan.setMontoTarifa(resultado.getMontoTarifa().toString());
                eliminarPlan.setTipoPagoMantenimiento("montoTarifa");
            }
            if(resultado.getMontoInicial() == null){
                eliminarPlan.setMontoInicial("0");
            }else{
                eliminarPlan.setMontoInicial(resultado.getMontoInicial().toString());
            }
            if(resultado.getPorcComisionBancaria()== null){
                eliminarPlan.setPorcComisionBancaria("0");
            }else{
                eliminarPlan.setPorcComisionBancaria(resultado.getPorcComisionBancaria().toString());
                
                eliminarPlan.setTipoPagoMantenimiento("porcComisionBancaria");
            }
            if(resultado.getMontoFijo() == null || resultado.getMontoFijo() == 0){
                eliminarPlan.setMontoFijo(false);
            }else{
                eliminarPlan.setMontoFijo(true);
            }
            //
            eliminarPlan.setPlanplazo(Integer.toString(resultado.getPlanplazo()));
            
            //editarPlan.setMontoFijo(Integer.toString(resultado.getMontoFijo()));
            eliminarPlan.setFechainicio(resultado.getFechainicio());
            eliminarPlan.setFechafin(resultado.getFechafin());      
            if(resultado.getIndefinido()=="1")
                eliminarPlan.setIndefinido(true);
            else eliminarPlan.setIndefinido(false);
            eliminarPlan.setFrecuencia(resultado.getFrecuencia());
            eliminarPlan.setMoneda(resultado.getMoneda());
            eliminarPlan.setEstatus(resultado.getEstatus());
            eliminarPlan.setMontopromedio(resultado.getMontopromedio().toString());
            eliminarPlan.setTransaccion(Integer.toString(resultado.getTransaccion()));
            eliminarPlan.setDiasimpago(Integer.toString(resultado.getDiasimpago()));
            eliminarPlan.setPorcentaje(Integer.toString(resultado.getPorcentaje()));
            
            return new ModelAndView("eliminarPlan", "eliminarPlan", eliminarPlan);
            
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("eliminarPlan", "model", modelo);
        }        
    }
    
      //Accion post de los formularios
    
    @RequestMapping(value="/crearPlan.htm", method = RequestMethod.POST)
    public ModelAndView crearPlanSubmit(@ModelAttribute("crearPlan") @Valid PlanForm crearPlan, BindingResult result, Model modelo, HttpServletRequest request) throws ParseException
    {
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        Locale español = new Locale("es");

        try{
            NumberFormat nf = NumberFormat.getInstance(español);
            double monto = 0; 
            double montoEquipo = 0; 
            //VALIDACIONES Y MENSAJES DE ERROR
            //Validacion de monto tarifa > 0

            //validacion de monto promedio >= 0
            if(crearPlan.getMontopromedio().isEmpty() || "".equals(crearPlan.getMontopromedio())){
                result.rejectValue("montopromedio","invalid", new Object[]{ crearPlan.getMontopromedio()}, "Debe ingresar un monto promedio de transacciones valido, mayor o igual a cero");
            }else{
                monto = nf.parse(crearPlan.getMontopromedio()).doubleValue();
                if(monto < 0){
                    result.rejectValue("montopromedio","invalid", new Object[]{ crearPlan.getMontopromedio()}, "Debe ingresar un monto promedio de transacciones valido, mayor o igual a cero");
                }
            }
            if(null != crearPlan.getCodtipoplan()) 
                switch (crearPlan.getCodtipoplan()) {
                    case "2":
                        //plan de ecuipo
                        if(crearPlan.getMontoTarifa().isEmpty() || "".equals(crearPlan.getMontoTarifa())){
                            result.rejectValue("montoTarifa","invalid", new Object[]{ crearPlan.getMontoTarifa()}, "Debe ingresar un monto de tarifa superior a 0");
                        }else{
                            monto = nf.parse(crearPlan.getMontoTarifa()).doubleValue();
                            if(monto <= 0){
                                result.rejectValue("montoTarifa","invalid", new Object[]{ crearPlan.getMontoTarifa()}, "Debe ingresar un monto de tarifa superior a 0");
                            }
                        }   
                        if(crearPlan.getPlanplazo().isEmpty() || "".equals(crearPlan.getPlanplazo())){
                            result.rejectValue("planplazo","invalid", new Object[]{ crearPlan.getPlanplazo()}, "Debe ingresar un Plazo valido, Mayor a cero (Meses)");
                        }else{
                            monto = nf.parse(crearPlan.getPlanplazo()).doubleValue();
                            if(monto < 0){
                                result.rejectValue("planplazo","invalid", new Object[]{ crearPlan.getPlanplazo()}, "Debe ingresar un Plazo valido, Mayor a cero (Meses)");
                            }
                        }   
                        if(crearPlan.getMontoInicial().isEmpty() || "".equals(crearPlan.getMontoInicial())){
                            result.rejectValue("montoInicial","invalid", new Object[]{ crearPlan.getMontoInicial()}, "Debe ingresar un monto de incial superioro igual a 0 y menor al monto de la tarifa");
                        }else{
                            monto = nf.parse(crearPlan.getMontoInicial()).doubleValue();
                            montoEquipo = nf.parse(crearPlan.getMontoTarifa()).doubleValue();
                            if(monto < 0 || monto >= montoEquipo){
                                result.rejectValue("montoInicial","invalid", new Object[]{ crearPlan.getMontoInicial()}, "Debe ingresar un monto de tarifa superior o igual a 0 y menor al monto de la tarifa");
                            }
                        }   
                        break;
                    case "3":
                        //plan de mantenimiento
                        if("porcComisionBancaria".equals(crearPlan.getTipoPagoMantenimiento())){
                            if(crearPlan.getPorcComisionBancaria().isEmpty() || "".equals(crearPlan.getPorcComisionBancaria())){
                                result.rejectValue("porcComisionBancaria","invalid", new Object[]{ crearPlan.getPorcComisionBancaria()}, "Debe ingresar un porcentaje de comision Bancaria");
                            }else{
                                monto = nf.parse(crearPlan.getPorcComisionBancaria()).doubleValue();
                                if(monto <= 0 || monto > 100){
                                    result.rejectValue("porcComisionBancaria","invalid", new Object[]{ crearPlan.getPorcComisionBancaria()}, "El porcentaje de Comision Bancaria debe ser mayor a 0 y menor o igual a 100");
                                }
                            }
                        }else{
                            if(crearPlan.getMontoTarifa().isEmpty() || "".equals(crearPlan.getMontoTarifa())){
                                result.rejectValue("montoTarifa","invalid", new Object[]{ crearPlan.getMontoTarifa()}, "Debe ingresar un monto de tarifa superior a 0");
                            }else{
                                monto = nf.parse(crearPlan.getMontoTarifa()).doubleValue();
                                if(monto <= 0){
                                    result.rejectValue("montoTarifa","invalid", new Object[]{ crearPlan.getMontoTarifa()}, "Debe ingresar un monto de tarifa superior a 0");
                                }
                            }
                        }   
                        break;
                    default:
                        //cualquier otro tipo de plan - plan de uso
                        if(crearPlan.getMontoTarifa().isEmpty() || "".equals(crearPlan.getMontoTarifa())){
                            result.rejectValue("montoTarifa","invalid", new Object[]{ crearPlan.getMontoTarifa()}, "Debe ingresar un monto de tarifa superior a 0");
                        }else{
                            monto = nf.parse(crearPlan.getMontoTarifa()).doubleValue();
                            if(monto <= 0){
                                result.rejectValue("montoTarifa","invalid", new Object[]{ crearPlan.getMontoTarifa()}, "Debe ingresar un monto de tarifa superior a 0");
                            }
                        }   
                        break;
                }

            if(crearPlan.getIndefinido().equals(false)){
                //validacion de fechas 
                //- formatos
                Boolean formatosValidos = true;
                try
                {
                    DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    df.setLenient(false);
                    df.parse(crearPlan.getFechainicio());

                }catch (ParseException e) {
                    result.rejectValue("fechainicio","invalid", new Object[]{ crearPlan.getFechainicio()}, "Debe ingresar una fecha de inicio valida");
                }
                try
                {
                    DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    df.setLenient(false);
                    df.parse(crearPlan.getFechafin());

                }catch (ParseException e) {
                    result.rejectValue("fechafin","invalid", new Object[]{ crearPlan.getFechafin()}, "Debe ingresar una fecha fin valida");
                }

            }
            //cant transacciones >=0
            if(crearPlan.getTransaccion().isEmpty() || "".equals(crearPlan.getTransaccion())){
                result.rejectValue("transaccion","invalid", new Object[]{ crearPlan.getTransaccion()}, "Debe ingresar una cantidad de transacciones valido, mayor o igual a cero");
            }else{
                monto = nf.parse(crearPlan.getTransaccion()).doubleValue();
                if(monto < 0){
                    result.rejectValue("transaccion","invalid", new Object[]{ crearPlan.getTransaccion()}, "Debe ingresar  una cantidad de transacciones valido, mayor o igual a cero");
                }
            }
            //diasimpago >=0
            if(crearPlan.getDiasimpago().isEmpty() || "".equals(crearPlan.getDiasimpago())){
                result.rejectValue("diasimpago","invalid", new Object[]{ crearPlan.getDiasimpago()}, "Debe ingresar una cantidad de dias inpago valida, mayor o igual a cero");
            }else{
                monto = nf.parse(crearPlan.getDiasimpago()).doubleValue();
                if(monto < 0){
                    result.rejectValue("diasimpago","invalid", new Object[]{ crearPlan.getDiasimpago()}, "Debe ingresar una cantidad de dias inpago valida, mayor o igual a cero");
                }
            }
            //porcentaje >=0 && <=100
            if(crearPlan.getPorcentaje().isEmpty() || "".equals(crearPlan.getPorcentaje())){
                result.rejectValue("porcentaje","invalid", new Object[]{ crearPlan.getPorcentaje()}, "Debe ingresar un % de cuotas vencidas valido, entre 0 y 100");
            }else{
                monto = nf.parse(crearPlan.getPorcentaje()).doubleValue();
                if(monto < 0 || monto > 100){
                    result.rejectValue("porcentaje","invalid", new Object[]{ crearPlan.getPorcentaje()}, "Debe ingresar un % de cuotas vencidas valido, entre 0 y 100");
                }
            }


            if (result.hasErrors()) {
                return new ModelAndView("crearPlan");
            }                    

        
            Plan resultado = new Plan();

            resultado.setCodtipoplan(crearPlan.getCodtipoplan());
            resultado.setNombre(crearPlan.getNombre());
            resultado.setDescripcion(crearPlan.getDescripcion());
            if(!"".equals(crearPlan.getMontoTarifa()) && crearPlan.getMontoTarifa() != null){
                monto = nf.parse(crearPlan.getMontoTarifa()).doubleValue();
                resultado.setMontoTarifa(new BigDecimal(monto));
            }else{
                resultado.setMontoTarifa(new BigDecimal(0));
            }
            
            if(crearPlan.getMontoFijo())
                resultado.setMontoFijo(1);
            else 
                resultado.setMontoFijo(0);
            
            if(!"".equals(crearPlan.getPorcComisionBancaria()) && crearPlan.getPorcComisionBancaria() != null){
                monto = nf.parse(crearPlan.getPorcComisionBancaria()).doubleValue();
                resultado.setPorcComisionBancaria(new BigDecimal(monto));
            }else{
                resultado.setPorcComisionBancaria(null);
            }
            
            if(!"".equals(crearPlan.getMontoInicial()) && crearPlan.getMontoInicial() != null){
                monto = nf.parse(crearPlan.getMontoInicial()).doubleValue();
                resultado.setMontoInicial(new BigDecimal(monto));
            }else{
                resultado.setMontoInicial(null);
            }
            
            if(crearPlan.getIndefinido())
                resultado.setIndefinido("1");
            else resultado.setIndefinido("0");


            if(crearPlan.getIndefinido().equals(true)){
                resultado.setFechainicio(null);
                resultado.setFechafin(null);
            }
            else
            {
                resultado.setFechainicio((Utilidades.cambiaFormatoFecha((crearPlan.getFechainicio()))));  
                resultado.setFechafin((Utilidades.cambiaFormatoFecha(crearPlan.getFechafin())));
            }
            
            if("2".equals(crearPlan.getCodtipoplan())){
                resultado.setPlanplazo(nf.parse(crearPlan.getPlanplazo()).intValue());
            }else{
                resultado.setPlanplazo(0);
            }
            resultado.setFrecuencia(crearPlan.getFrecuencia());
            resultado.setMoneda(crearPlan.getMoneda());
            resultado.setEstatus(crearPlan.getEstatus());
            monto = nf.parse(crearPlan.getMontopromedio()).doubleValue();
            resultado.setMontopromedio(new BigDecimal(monto));
            resultado.setTransaccion(nf.parse(crearPlan.getTransaccion()).intValue());
            resultado.setDiasimpago(nf.parse(crearPlan.getDiasimpago()).intValue());
            resultado.setPorcentaje(nf.parse(crearPlan.getPorcentaje()).intValue());
            
            bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "Crear Plan ID: "+ resultado.getId() +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
        
            //Guarda los datos ingresados por el usuario
            plan.savePlan(resultado);

            Map<String, Object> myModel = new HashMap<>();        
            myModel.put("listadoPlanes", this.plan.getInformationPlanList(2));
            Integer perfilId = new Integer(usuario.get(0).getPerfilId());
            Perfil perfil = perfilDAO.getById(perfilId);
            if (Arrays.asList(perfil.getOpciones().split(",")).contains(Integer.toString(40))) {                                
                myModel.put("permisoActualizacion", 1);
            }else{
                myModel.put("permisoActualizacion", 0);
            }
            myModel.put("messageError", "Registro Creado Satisfactoriamente");

            return new ModelAndView("listadoPlanes", "model", myModel);  
        
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getCause().getCause().getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getCause().getCause().getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("crearPlan", "model", modelo);
        }        
        
    }
    
    
    @RequestMapping(value="/editarPlan.htm", method = RequestMethod.POST)
    public ModelAndView editarPlanSubmit(@ModelAttribute("editarPlan") @Valid PlanForm editarPlan, BindingResult result, Model modelo, HttpServletRequest request) throws ParseException
    {
                                  
        
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        String oldValue, newValue;
        
        
        try{                                    
        
            NumberFormat nf = NumberFormat.getInstance(español);
            double monto = 0; 
            double montoEquipo = 0; 
            //VALIDACIONES Y MENSAJES DE ERROR
            //Validacion de monto tarifa > 0

            //validacion de monto promedio >= 0
            if(editarPlan.getMontopromedio().isEmpty() || "".equals(editarPlan.getMontopromedio())){
                result.rejectValue("montopromedio","invalid", new Object[]{ editarPlan.getMontopromedio()}, "Debe ingresar un monto promedio de transacciones valido, mayor o igual a cero");
            }else{
                monto = nf.parse(editarPlan.getMontopromedio()).doubleValue();
                if(monto < 0){
                    result.rejectValue("montopromedio","invalid", new Object[]{ editarPlan.getMontopromedio()}, "Debe ingresar un monto promedio de transacciones valido, mayor o igual a cero");
                }
            }
            if(null != editarPlan.getCodtipoplan()) 
                switch (editarPlan.getCodtipoplan()) {
                    case "2":
                        //plan de ecuipo
                        if(editarPlan.getMontoTarifa().isEmpty() || "".equals(editarPlan.getMontoTarifa())){
                            result.rejectValue("montoTarifa","invalid", new Object[]{ editarPlan.getMontoTarifa()}, "Debe ingresar un monto de tarifa superior a 0");
                        }else{
                            monto = nf.parse(editarPlan.getMontoTarifa()).doubleValue();
                            if(monto <= 0){
                                result.rejectValue("montoTarifa","invalid", new Object[]{ editarPlan.getMontoTarifa()}, "Debe ingresar un monto de tarifa superior a 0");
                            }
                        }   
                        if(editarPlan.getPlanplazo().isEmpty() || "".equals(editarPlan.getPlanplazo())){
                            result.rejectValue("planplazo","invalid", new Object[]{ editarPlan.getPlanplazo()}, "Debe ingresar un Plazo valido, Mayor a cero (Meses)");
                        }else{
                            monto = nf.parse(editarPlan.getPlanplazo()).doubleValue();
                            if(monto < 0){
                                result.rejectValue("planplazo","invalid", new Object[]{ editarPlan.getPlanplazo()}, "Debe ingresar un Plazo valido, Mayor a cero (Meses)");
                            }
                        }   
                        if(editarPlan.getMontoInicial().isEmpty() || "".equals(editarPlan.getMontoInicial())){
                            result.rejectValue("montoInicial","invalid", new Object[]{ editarPlan.getMontoInicial()}, "Debe ingresar un monto de incial superioro igual a 0 y menor al monto de la tarifa");
                        }else{
                            monto = nf.parse(editarPlan.getMontoInicial()).doubleValue();
                            montoEquipo = nf.parse(editarPlan.getMontoTarifa()).doubleValue();
                            if(monto < 0 || monto >= montoEquipo){
                                result.rejectValue("montoInicial","invalid", new Object[]{ editarPlan.getMontoInicial()}, "Debe ingresar un monto de tarifa superior o igual a 0 y menor al monto de la tarifa");
                            }
                        }   
                        break;
                    case "3":
                        //plan de mantenimiento
                        if("porcComisionBancaria".equals(editarPlan.getTipoPagoMantenimiento())){
                            if(editarPlan.getPorcComisionBancaria().isEmpty() || "".equals(editarPlan.getPorcComisionBancaria())){
                                result.rejectValue("porcComisionBancaria","invalid", new Object[]{ editarPlan.getPorcComisionBancaria()}, "Debe ingresar un porcentaje de comision Bancaria");
                            }else{
                                monto = nf.parse(editarPlan.getPorcComisionBancaria()).doubleValue();
                                if(monto <= 0 || monto > 100){
                                    result.rejectValue("porcComisionBancaria","invalid", new Object[]{ editarPlan.getPorcComisionBancaria()}, "El porcentaje de Comision Bancaria debe ser mayor a 0 y menor o igual a 100");
                                }
                            }
                        }else{
                            if(editarPlan.getMontoTarifa().isEmpty() || "".equals(editarPlan.getMontoTarifa())){
                                result.rejectValue("montoTarifa","invalid", new Object[]{ editarPlan.getMontoTarifa()}, "Debe ingresar un monto de tarifa superior a 0");
                            }else{
                                monto = nf.parse(editarPlan.getMontoTarifa()).doubleValue();
                                if(monto <= 0){
                                    result.rejectValue("montoTarifa","invalid", new Object[]{ editarPlan.getMontoTarifa()}, "Debe ingresar un monto de tarifa superior a 0");
                                }
                            }
                        }   
                        break;
                    default:
                        //cualquier otro tipo de plan - plan de uso
                        if(editarPlan.getMontoTarifa().isEmpty() || "".equals(editarPlan.getMontoTarifa())){
                            result.rejectValue("montoTarifa","invalid", new Object[]{ editarPlan.getMontoTarifa()}, "Debe ingresar un monto de tarifa superior a 0");
                        }else{
                            monto = nf.parse(editarPlan.getMontoTarifa()).doubleValue();
                            if(monto <= 0){
                                result.rejectValue("montoTarifa","invalid", new Object[]{ editarPlan.getMontoTarifa()}, "Debe ingresar un monto de tarifa superior a 0");
                            }
                        }   
                        break;
                }

            if(editarPlan.getIndefinido().equals(false)){
                //validacion de fechas 
                //- formatos
                Boolean formatosValidos = true;
                try
                {
                    DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    df.setLenient(false);
                    df.parse(editarPlan.getFechainicio());

                }catch (ParseException e) {
                    result.rejectValue("fechainicio","invalid", new Object[]{ editarPlan.getFechainicio()}, "Debe ingresar una fecha de inicio valida");
                }
                try
                {
                    DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    df.setLenient(false);
                    df.parse(editarPlan.getFechafin());

                }catch (ParseException e) {
                    result.rejectValue("fechafin","invalid", new Object[]{ editarPlan.getFechafin()}, "Debe ingresar una fecha fin valida");
                }

            }
            //cant transacciones >=0
            if(editarPlan.getTransaccion().isEmpty() || "".equals(editarPlan.getTransaccion())){
                result.rejectValue("transaccion","invalid", new Object[]{ editarPlan.getTransaccion()}, "Debe ingresar una cantidad de transacciones valido, mayor o igual a cero");
            }else{
                monto = nf.parse(editarPlan.getTransaccion()).doubleValue();
                if(monto < 0){
                    result.rejectValue("transaccion","invalid", new Object[]{ editarPlan.getTransaccion()}, "Debe ingresar  una cantidad de transacciones valido, mayor o igual a cero");
                }
            }
            //diasimpago >=0
            if(editarPlan.getDiasimpago().isEmpty() || "".equals(editarPlan.getDiasimpago())){
                result.rejectValue("diasimpago","invalid", new Object[]{ editarPlan.getDiasimpago()}, "Debe ingresar una cantidad de dias inpago valida, mayor o igual a cero");
            }else{
                monto = nf.parse(editarPlan.getDiasimpago()).doubleValue();
                if(monto < 0){
                    result.rejectValue("diasimpago","invalid", new Object[]{ editarPlan.getDiasimpago()}, "Debe ingresar una cantidad de dias inpago valida, mayor o igual a cero");
                }
            }
            //porcentaje >=0 && <=100
            if(editarPlan.getPorcentaje().isEmpty() || "".equals(editarPlan.getPorcentaje())){
                result.rejectValue("porcentaje","invalid", new Object[]{ editarPlan.getPorcentaje()}, "Debe ingresar un % de cuotas vencidas valido, entre 0 y 100");
            }else{
                monto = nf.parse(editarPlan.getPorcentaje()).doubleValue();
                if(monto < 0 || monto > 100){
                    result.rejectValue("porcentaje","invalid", new Object[]{ editarPlan.getPorcentaje()}, "Debe ingresar un % de cuotas vencidas valido, entre 0 y 100");
                }
            }


            if (result.hasErrors()) {
                return new ModelAndView("editarPlan");
            }                    

        
            Plan resultado = new Plan();

            resultado.setId(editarPlan.getId());
            
            resultado.setCodtipoplan(editarPlan.getCodtipoplan());
            resultado.setNombre(editarPlan.getNombre());
            resultado.setDescripcion(editarPlan.getDescripcion());
            if(!"".equals(editarPlan.getMontoTarifa())  && editarPlan.getMontoTarifa() != null){
                monto = nf.parse(editarPlan.getMontoTarifa()).doubleValue();
                resultado.setMontoTarifa(new BigDecimal(monto));
            }else{
                resultado.setMontoTarifa(new BigDecimal(0));
            }
            
            if(editarPlan.getMontoFijo())
                resultado.setMontoFijo(1);
            else 
                resultado.setMontoFijo(0);
            
            if(!"".equals(editarPlan.getPorcComisionBancaria()) && editarPlan.getPorcComisionBancaria() != null){
                monto = nf.parse(editarPlan.getPorcComisionBancaria()).doubleValue();
                resultado.setPorcComisionBancaria(new BigDecimal(monto));
            }else{
                resultado.setPorcComisionBancaria(null);
            }
            
            if(!"".equals(editarPlan.getMontoInicial()) && editarPlan.getMontoInicial() != null){
                monto = nf.parse(editarPlan.getMontoInicial()).doubleValue();
                resultado.setMontoInicial(new BigDecimal(monto));
            }else{
                resultado.setMontoInicial(null);
            }
            
            if(editarPlan.getIndefinido())
                resultado.setIndefinido("1");
            else resultado.setIndefinido("0");


            if(editarPlan.getIndefinido().equals(true)){
                resultado.setFechainicio(null);
                resultado.setFechafin(null);
            }
            else
            {
                resultado.setFechainicio((Utilidades.cambiaFormatoFecha((editarPlan.getFechainicio()))));  
                resultado.setFechafin((Utilidades.cambiaFormatoFecha(editarPlan.getFechafin())));
            }
            
            if("2".equals(editarPlan.getCodtipoplan())){
                resultado.setPlanplazo(nf.parse(editarPlan.getPlanplazo()).intValue());
            }else{
                resultado.setPlanplazo(0);
            }
            resultado.setFrecuencia(editarPlan.getFrecuencia());
            resultado.setMoneda(editarPlan.getMoneda());
            resultado.setEstatus(editarPlan.getEstatus());
            monto = nf.parse(editarPlan.getMontopromedio()).doubleValue();
            resultado.setMontopromedio(new BigDecimal(monto));
            resultado.setTransaccion(nf.parse(editarPlan.getTransaccion()).intValue());
            resultado.setDiasimpago(nf.parse(editarPlan.getDiasimpago()).intValue());
            resultado.setPorcentaje(nf.parse(editarPlan.getPorcentaje()).intValue());
            //Guarda los datos modificados por el usuario
            oldValue = ToStringBuilder.reflectionToString(plan.getById(editarPlan.getId()));
            
            plan.updatePlan(resultado);
            newValue = ToStringBuilder.reflectionToString(plan.getById(editarPlan.getId()));
            bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "Mantenimiento Editar Plan ID: "+ editarPlan.getId() +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), oldValue, newValue);

            Map<String, Object> myModel = new HashMap<>();        
            myModel.put("listadoPlanes", this.plan.getInformationPlanList(2));
            myModel.put("messageError", "Registro Actualizado Satisfactoriamente");
            Integer perfilId = new Integer(usuario.get(0).getPerfilId());
            Perfil perfil = perfilDAO.getById(perfilId);
            if (Arrays.asList(perfil.getOpciones().split(",")).contains(Integer.toString(40))) {                                
                myModel.put("permisoActualizacion", 1);
            }else{
                myModel.put("permisoActualizacion", 0);
            }
            return new ModelAndView("listadoPlanes", "model", myModel);  
        
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getCause().getCause().getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("editarPlan", "model", modelo);
        }        
        
    }
    
   
    @RequestMapping(value="/eliminarPlan.htm", method = RequestMethod.POST)
    public ModelAndView eliminarPlanSubmit(@ModelAttribute("eliminarPlan") @Valid PlanForm eliminarPlan, BindingResult result, Model modelo, HttpServletRequest request)
    {
        NumberFormat nf = NumberFormat.getInstance(español);
        double monto= 0; 
        
        if (result.hasErrors()) {
            return new ModelAndView("eliminarPlan");
        }                
        
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        try{
            Plan resultado = new Plan();
            
            resultado.setId(eliminarPlan.getId());
            
            resultado.setCodtipoplan(eliminarPlan.getCodtipoplan());
            resultado.setNombre(eliminarPlan.getNombre());
            resultado.setDescripcion(eliminarPlan.getDescripcion());
            if(!"".equals(eliminarPlan.getMontoTarifa())){
                monto = nf.parse(eliminarPlan.getMontoTarifa()).doubleValue();
                resultado.setMontoTarifa(new BigDecimal(monto));
            }else{
                resultado.setMontoTarifa(new BigDecimal(0));
            }
            
            if(eliminarPlan.getMontoFijo())
                resultado.setMontoFijo(1);
            else 
                resultado.setMontoFijo(0);
            
            if(!"".equals(eliminarPlan.getPorcComisionBancaria())){
                monto = nf.parse(eliminarPlan.getPorcComisionBancaria()).doubleValue();
                resultado.setPorcComisionBancaria(new BigDecimal(monto));
            }else{
                resultado.setPorcComisionBancaria(null);
            }
            
            if(!"".equals(eliminarPlan.getMontoInicial())){
                monto = nf.parse(eliminarPlan.getMontoInicial()).doubleValue();
                resultado.setMontoInicial(new BigDecimal(monto));
            }else{
                resultado.setMontoInicial(null);
            }
            
            if(eliminarPlan.getIndefinido())
                resultado.setIndefinido("1");
            else resultado.setIndefinido("0");


            if(eliminarPlan.getIndefinido().equals(true)){
                resultado.setFechainicio(null);
                resultado.setFechafin(null);
            }
            else
            {
                resultado.setFechainicio((Utilidades.cambiaFormatoFecha((eliminarPlan.getFechainicio()))));  
                resultado.setFechafin((Utilidades.cambiaFormatoFecha(eliminarPlan.getFechafin())));
            }
            
            if("2".equals(eliminarPlan.getCodtipoplan())){
                resultado.setPlanplazo(nf.parse(eliminarPlan.getPlanplazo()).intValue());
            }else{
                resultado.setPlanplazo(0);
            }
            resultado.setFrecuencia(eliminarPlan.getFrecuencia());
            resultado.setMoneda(eliminarPlan.getMoneda());
            resultado.setEstatus(eliminarPlan.getEstatus());
            monto = nf.parse(eliminarPlan.getMontopromedio()).doubleValue();
            resultado.setMontopromedio(new BigDecimal(monto));
            resultado.setTransaccion(nf.parse(eliminarPlan.getTransaccion()).intValue());
            resultado.setDiasimpago(nf.parse(eliminarPlan.getDiasimpago()).intValue());
            resultado.setPorcentaje(nf.parse(eliminarPlan.getPorcentaje()).intValue());
            
            List<PlanPago> terminalesAsociados = planpago.getPlanPagoByPlanList(eliminarPlan.getId());
            if (terminalesAsociados.size()>0){
                modelo.addAttribute("messageError", "El Plan no se puede eliminar por que posee terminales asociados.");
                return new ModelAndView("eliminarPlan", "model", modelo);
            }
            
            bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "Mantenimiento Eliminar Plan ID: "+ eliminarPlan.getId() +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
        
            //Eliminar el pos seleccionado por el usuario
            plan.removePlan(resultado);

            Map<String, Object> myModel = new HashMap<>();        
            myModel.put("listadoPlanes", this.plan.getInformationPlanList(2));
            myModel.put("messageError", "Registro Eliminado Satisfactoriamente");
            Integer perfilId = new Integer(usuario.get(0).getPerfilId());
            Perfil perfil = perfilDAO.getById(perfilId);
            if (Arrays.asList(perfil.getOpciones().split(",")).contains(Integer.toString(40))) {                                
                myModel.put("permisoActualizacion", 1);
            }else{
                myModel.put("permisoActualizacion", 0);
            }
            return new ModelAndView("listadoPlanes", "model", myModel);
        
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("eliminarPlan", "model", modelo);
        }        
        
    }
    
    
    /* Generar reporte de Planes de clientes */
    @RequestMapping(value="/generarConsultaPlanes.htm", method = RequestMethod.GET)
    public ModelAndView generarConsultaPlanes(HttpServletRequest request, HttpServletResponse response, Model modelo)
            throws ServletException, IOException, ParseException {
                        
        HttpSession misession= (HttpSession) request.getSession();  
        List<Usuario> usuarioLogin = (List<Usuario>) misession.getAttribute("usuario.datos");                  
        
        try{
             
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 25, "Ingreso a opción Generar Consulta/reporte de Planes", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");                        
            return new ModelAndView("generarConsultaPlanes");
        
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 25, "No se ha podido realizar la operacion debido al siguiente error: "+cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());           
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("generarConsultaPlanes");
        }        
        
    }        
    
    //Accion post del formulario generarConsultaPlanes
    @RequestMapping(value="/generarConsultaPlanes.htm", method = RequestMethod.POST)
    public ModelAndView generarConsultaPlanesSubmit(@ModelAttribute("generarConsultaPlanes") @Valid GenerarConsultaPlanesForm generarConsultaPlanes, BindingResult result, Model modelo, HttpServletRequest request)
    {                
        
        if (result.hasFieldErrors("estatus")) {
            return new ModelAndView("generarConsultaPlanes");        
        }        
        if (result.hasFieldErrors("tipoPlan")) {
            return new ModelAndView("generarConsultaPlanes");
        }
        if (result.hasErrors()) {                    
            return new ModelAndView("generarConsultaPlanes");
        }
        
        
        if("1".equals(generarConsultaPlanes.getTipoReporte())){
            if (generarConsultaPlanes.getEstatus() == null){
                result.rejectValue("estatus","invalid", new Object[]{ generarConsultaPlanes.getEstatus()}, "Debe Seleccionar al menos 1 estatus.");
                return new ModelAndView("generarConsultaPlanes");
            }
        }
        
        if("1".equals(generarConsultaPlanes.getTipoConsulta())){
            if (null != generarConsultaPlanes.getTipoConsultaIndividual())
                switch (generarConsultaPlanes.getTipoConsultaIndividual()) {
                    case "0":
                        if (generarConsultaPlanes.getTipoIdentificacionComercio().isEmpty() || generarConsultaPlanes.getIdentificacionComercio().isEmpty()){
                            result.rejectValue("identificacionComercio","invalid", new Object[]{ generarConsultaPlanes.getIdentificacionComercio()}, "Debe ingresar el numero de identificacion del comercio");
                            return new ModelAndView("generarConsultaPlanes");
                        }   break;
                    case "1":
                        if (generarConsultaPlanes.getTerminal().isEmpty()){
                            result.rejectValue("terminal","invalid", new Object[]{ generarConsultaPlanes.getTerminal()}, "Debe ingresar el terminal");
                            return new ModelAndView("generarConsultaPlanes");
                        }   break;
                    default:
                        break;
                }
            
        }
        
        Map<String, Object> myModel = new HashMap<>();
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuarioLogin = (List<Usuario>) misession.getAttribute("usuario.datos");                        
        List<PlanesConsulta> planesXComercio;
        
        try{
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 25, "Genera Cobranzas Comercios", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
            planesXComercio = planesConsultaService.getPlanesConsultaList( generarConsultaPlanes.getTipoReporte(), generarConsultaPlanes.getEstatus(), generarConsultaPlanes.getTipoPlan(), generarConsultaPlanes.getTipoConsulta(),generarConsultaPlanes.getTipoConsultaIndividual(),generarConsultaPlanes.getTipoIdentificacionComercio(),generarConsultaPlanes.getIdentificacionComercio(),generarConsultaPlanes.getTerminal());
                    
            
            if(planesXComercio.isEmpty()){
                myModel.put("messageError", "No se encontraron registros con los parametros seleccionados");
                return new ModelAndView("generarConsultaPlanes","model", myModel);
            }else{  
                
                archivoConsultaPlanes.generarConsultaPlanesExcelComercios(planesXComercio);
                archivoConsultaPlanes.generarConsultaPlanesTXTComercios(planesXComercio);
                myModel.put("messageError", "Archivo de Planes generado satisfactoriamente");
                return new ModelAndView("generarConsultaPlanes","model", myModel);
            }
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 25, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("generarConsultaPlanes", "model", modelo);
        }                                
        
    }
        
    @ModelAttribute("MonedaList")
    public List MonedaList() {
        
        List<Moneda> MonedaList = null;
        
        try{
            MonedaList = moneda.getMonedaList();
        }catch (Exception cve) {                                   
            logger.error(cve.getMessage());            
        }
                                     		         
        return MonedaList;
    }
    
     @ModelAttribute("tipoPlanList")
    public List tipoPlanList() {
        
        List<TipoPlan> tipoPlanList = null;
        
        try{
            tipoPlanList = tipoPlan.getTipoPlanList();
        }catch (Exception cve) {                                   
            logger.error(cve.getMessage());            
        }
                                     		         
        return tipoPlanList;
    }
    
    @ModelAttribute("FrecuenciaList")
    public List FrecuenciaList() {
        
        List<Frecuencia> FrecuenciaList = null;
        
        try{
            FrecuenciaList = frecuencia.getFrecuenciaList();
        }catch (Exception cve) {                                   
            logger.error(cve.getMessage());            
        }
                                     		         
        return FrecuenciaList;
    }
    
    @ModelAttribute("estatusList")
    public List estatusList() {

        List<Estatus> estatusList = null;

        try {
            estatusList = estatusDAO.getEstatusByModulo("pla");
        } catch (Exception cve) {
            logger.error(cve.getMessage());
        }

        return estatusList;
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