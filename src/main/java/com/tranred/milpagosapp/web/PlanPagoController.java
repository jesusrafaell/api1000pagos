package com.tranred.milpagosapp.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tranred.milpagosapp.domain.Abono;
import com.tranred.milpagosapp.domain.PlanPago;
import com.tranred.milpagosapp.domain.Plan;
import com.tranred.milpagosapp.domain.Frecuencia;
import com.tranred.milpagosapp.domain.Usuario;
import com.tranred.milpagosapp.domain.Estatus;
import com.tranred.milpagosapp.domain.Afiliado;
import com.tranred.milpagosapp.domain.GenerarCuotas;
import com.tranred.milpagosapp.domain.Moneda;
import com.tranred.milpagosapp.domain.PlanCuota;
import com.tranred.milpagosapp.domain.PlanPagoDetail;
import com.tranred.milpagosapp.domain.Planes;
import com.tranred.milpagosapp.repository.ICobranzasDAO;
import com.tranred.milpagosapp.repository.JPAPlanPagoDAO;
import com.tranred.milpagosapp.repository.JPAPlanDAO;
import com.tranred.milpagosapp.repository.JPAFrecuenciaDAO;
import com.tranred.milpagosapp.repository.IEstatusDAO;
import com.tranred.milpagosapp.repository.JPAAfiliadoDAO;
import com.tranred.milpagosapp.repository.JPACobranzasDAO;
import com.tranred.milpagosapp.repository.JPAMonedaDAO;
import com.tranred.milpagosapp.repository.JPAPlanCuotaDAO;
import com.tranred.milpagosapp.service.IAdminMantenimiento;
import com.tranred.milpagosapp.service.IBitacoraService;
import com.tranred.milpagosapp.service.ICobranzaService;
import com.tranred.milpagosapp.service.IComercioService;
import com.tranred.milpagosapp.service.IGenerarCuotasService;
import com.tranred.milpagosapp.service.PlanPagoForm;
import com.tranred.milpagosapp.util.Utilidades;
import com.tranred.utils.Fecha;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import static java.util.Calendar.YEAR;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Locale;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import static org.hibernate.annotations.common.util.StringHelper.truncate;
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
 * Clase actua como controlador para las opciones Editar y Eliminar Plan Pago.
 * @author jperez@emsys-solution.com
 * @version 0.1
 */

@Controller
public class PlanPagoController {
    
    protected final Log logger = LogFactory.getLog(getClass());
    
    @Autowired
    private JPAPlanDAO plan;
    
    
    @Autowired
    private JPAPlanPagoDAO planpago;
   
    @Autowired
    private ICobranzasDAO cobranza;
    
    @Autowired
    private IGenerarCuotasService cuotas;
      
    @Autowired
    private IAdminMantenimiento mantenimientoAdmin;
    
    @Autowired
    private IBitacoraService bitacora;
    
    @Autowired
    private JPAFrecuenciaDAO frecuencia;
    
    @Autowired
    private JPAPlanCuotaDAO cuotasBean;
    
    @Autowired
    private IEstatusDAO estatusDAO;   
    
    @Autowired
    private IComercioService comercioService;
   
    @Autowired
    private JPAMonedaDAO moneda;
    
    Locale español = new Locale("es");
    
    //Asignamos el estado inicial de los formularios con las propiedades de cada clase
    @ModelAttribute("crearPlanPago")
    public PlanPagoForm createModelCrear() {
        return new PlanPagoForm();
    }
    
    @ModelAttribute("editarPlanPago")
    public PlanPagoForm  createModelEditar() {
        return new PlanPagoForm();
    }
    
    @ModelAttribute("eliminarPlanPago")
    public PlanPagoForm createModelPlan() {
        return new PlanPagoForm();
    }
    
    
    @RequestMapping(value="/crearPlanPago.htm", method = RequestMethod.GET)
    protected ModelAndView crearPlanPago(HttpServletRequest request, Model modelo, @RequestParam String codigoTerm) throws ServletException, JsonProcessingException {
        
        Map<String, Object> myModel = new HashMap<>(); 
        List<Planes> planes = this.InformationPlanList();
        List<PlanPago> planesActivos = this.planpago.getPlanPagoByTerminalList(codigoTerm, 23);
        Map<Integer, BigDecimal> cobrados = new HashMap<>(); 
        
        for(PlanPago p : planesActivos){
            BigDecimal montoCobrados = this.cobranza.getMontoCuotasPagadas(codigoTerm,  27, p.getId());
            cobrados.put(p.getId(), montoCobrados);
            int cantPlanes = planes.size();
            for (int i = 0; i < cantPlanes; i++) {
                if(Objects.equals(planes.get(i).getId(), p.getIdplan())){
                    planes.remove(i);
                    i = cantPlanes; 
                }
            }
        }
        //planpago.get(0).
        //jackson convierte tu objeto en json
        ObjectMapper mapper = new ObjectMapper();
        String jsonPlanes = mapper.writeValueAsString(planes);
        String jsonPlanesActivos = mapper.writeValueAsString(planesActivos);
        String jsonMontoCuotasCobradas = mapper.writeValueAsString(cobrados);
        //pintamos el json en la consola para ver el resultado
        //System.out.println(jsonPlanes);
        //System.out.println(jsonPlanesActivos);
        //System.out.println(jsonMontoCuotasCobradas);
        
        PlanPagoForm crearPlanPago = new PlanPagoForm();
        crearPlanPago.setCodigoTerminal(codigoTerm);
        crearPlanPago.setJsonPlanes(jsonPlanes);
        crearPlanPago.setJsonPlanesActivos(jsonPlanesActivos);
        crearPlanPago.setJsonMontoCuotasCobradas(jsonMontoCuotasCobradas);
        return new ModelAndView("crearPlanPago", "crearPlanPago", crearPlanPago);               
                
    }
    
    @RequestMapping(value="/editarPlanPago.htm", method = RequestMethod.GET)
    protected ModelAndView editarPlanPago(@RequestParam(value = "id") int id, @RequestParam String codigoTerm,HttpServletRequest request, Model modelo) throws ServletException {
                
        PlanPagoForm editarPlanPago= new PlanPagoForm();
        PlanPago resultado = new PlanPago();
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        try{
            List<Plan> planes = this.InformationPlanList();
            List<PlanPago> planesActivos = this.planpago.getPlanPagoByTerminalList(codigoTerm, 23);
            List<PlanPagoDetail> planPagoDetail = this.planpago.getPlanPagoByTerminalDetailList(codigoTerm);
            PlanPagoDetail planPagoDetalle = new PlanPagoDetail();
            for(PlanPagoDetail ppd : planPagoDetail){
                if(ppd.getId()==id){
                    planPagoDetalle = ppd;
                }
            }
            //planpago.get(0).
            //jackson convierte tu objeto en json
            ObjectMapper mapper = new ObjectMapper();
            String jsonPlan = mapper.writeValueAsString(planPagoDetalle);
            
            
            editarPlanPago.setCodigoTerminal(codigoTerm);
            editarPlanPago.setJsonPlanes(jsonPlan);
            
            //resultado = planpago.getById(id);           

            bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "Ingreso Mantenimiento Editar Plan Pago Id:" + Integer.toString(id), Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");

            editarPlanPago.setId(planPagoDetalle.getId());
            editarPlanPago.setIdplan(planPagoDetalle.getIdplan().toString());
            editarPlanPago.setCodtipoplan(planPagoDetalle.getCodTipoPlan().toString());
            editarPlanPago.setCodigoComercio(planPagoDetalle.getCodigoComercio());
            editarPlanPago.setCodigoAfiliado(planPagoDetalle.getCodigoAfiliado());
            editarPlanPago.setCodigoTerminal(planPagoDetalle.getCodigoTerminal());
            editarPlanPago.setTipoPagoMantenimiento(planPagoDetalle.getTipoPagoMantenimiento());
            //if(planPagoDetalle.getMontoTarifa() == null)
            if(planPagoDetalle.getMontoTarifa() == null){
                editarPlanPago.setMontoTarifa("");
            }else{
                editarPlanPago.setMontoTarifa(planPagoDetalle.getMontoTarifa().toString());
            }
            if(planPagoDetalle.getMontoInicial() != null ){
                editarPlanPago.setMontoInicial(planPagoDetalle.getMontoInicial().toString());
            }else{
                editarPlanPago.setMontoInicial("");
            }
            if(planPagoDetalle.getMontoFijo() != null ){
                if(planPagoDetalle.getMontoFijo() == 1 ){
                    editarPlanPago.setMontoFijo(true);
                }else{
                    editarPlanPago.setMontoFijo(false);
                }
            }else{
                editarPlanPago.setMontoFijo(false);
            }
            
            if(planPagoDetalle.getPorcComisionBancaria  () != null ){
                editarPlanPago.setPorcComisionBancaria(planPagoDetalle.getPorcComisionBancaria().toString());
            }else{
                editarPlanPago.setPorcComisionBancaria("");
            }
            editarPlanPago.setMoneda(planPagoDetalle.getMoneda());
            //editarPlanPago.setFechainicio(resultado.getFechainicio().toString());
            //editarPlanPago.setFechafin(resultado.getFechafin().toString());   
            editarPlanPago.setFrecuencia(planPagoDetalle.getFrecuenciaId());
            editarPlanPago.setEstatus(planPagoDetalle.getEstatusId());
            return new ModelAndView("editarPlanPago", "editarPlanPago", editarPlanPago);
            
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("editarPlanPago", "model", modelo);
        }        
    }
    
     //Fin estado incial de los formularios
    

    
     @RequestMapping(value="/eliminarPlanPago.htm", method = RequestMethod.GET)
    protected ModelAndView eliminarPlanPago(@RequestParam(value = "id") int id,HttpServletRequest request, Model modelo) throws ServletException {
                
        PlanPago eliminarPlanPago= new PlanPago();
        PlanPago resultado = new PlanPago();
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        try{
            
            resultado = planpago.getById(id);            

            bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "Ingreso Mantenimiento Eliminar Plan Pago", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
            
            eliminarPlanPago.setId(resultado.getId());
            eliminarPlanPago.setIdplan(resultado.getIdplan());
            eliminarPlanPago.setCodigoComercio(resultado.getCodigoComercio());
            eliminarPlanPago.setCodigoAfiliado(resultado.getCodigoAfiliado());
            eliminarPlanPago.setCodigoTerminal(resultado.getCodigoTerminal());
            eliminarPlanPago.setMontoTarifa(resultado.getMontoTarifa());
           // eliminarPlanPago.setFechainicio(Utilidades.cambiaFormatoFecha((resultado.getFechainicio())));
         //   eliminarPlanPago.setFechafin(Utilidades.cambiaFormatoFecha((resultado.getFechafin())));  
            eliminarPlanPago.setFechainicio(resultado.getFechainicio());
            eliminarPlanPago.setFechafin(resultado.getFechafin());
            eliminarPlanPago.setFrecuencia(resultado.getFrecuencia());
            eliminarPlanPago.setEstatus(resultado.getEstatus());
            
            
            return new ModelAndView("eliminarPlanPago", "eliminarPlanPago", eliminarPlanPago);
            
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("eliminarPlanPago", "model", modelo);
        }        
    }
    
    @RequestMapping(value="/listadoTerminalPlanes.htm")
    public ModelAndView listadoTerminalPlanes(@RequestParam(value = "messageError") String messageError,@RequestParam(value = "codigoTerminal") String codigoTerminal,HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Map<String, Object> myModel = new HashMap<String, Object>();    
        List<PlanPagoDetail> planPagoDetail = this.planpago.getPlanPagoByTerminalDetailList(codigoTerminal);
        myModel.put("codigoTerminal",codigoTerminal);
        
        myModel.put("listadoTerminalPlanes", planPagoDetail);
        
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "Plan Pago", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
        
        return new ModelAndView("listadoTerminalPlanes", "model", myModel);
        
    }
    
    //Accion post de los formularios
    @RequestMapping(value="/crearPlanPago.htm", method = RequestMethod.POST)
    public ModelAndView crearPlanPagoSubmit(@ModelAttribute("crearPlanPago") @Valid PlanPagoForm crearPlanPago, @RequestParam String codigoTerminal, BindingResult result, Model modelo, HttpServletRequest request)
    {
        
        NumberFormat nf = NumberFormat.getInstance(español);
        double monto= 0, montoEquipo = 0; 
        Integer montoFijo;
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        List<Abono> terminales = comercioService.getAbonoByTerminal(crearPlanPago.getCodigoTerminal().toString()); 
        // se leen los planes activos del terminal
        List<PlanPago> planesActivos = this.planpago.getPlanPagoByTerminalList(crearPlanPago.getCodigoTerminal(), 23);
        PlanPago resultado = new PlanPago();
        Map<String, Integer> terminalPlanActivoTipo = new HashMap<String, Integer>();  
        try{
            if(null != crearPlanPago.getCodtipoplan()) 
                switch (crearPlanPago.getCodtipoplan()) {
                    case "2":
                        //plan de ecuipo
                        if(crearPlanPago.getMontoTarifa().isEmpty() || "".equals(crearPlanPago.getMontoTarifa())){
                            result.rejectValue("montoTarifa","invalid", new Object[]{ crearPlanPago.getMontoTarifa()}, "Debe ingresar un monto de tarifa superior a 0");
                        }else{
                            monto = nf.parse(crearPlanPago.getMontoTarifa()).doubleValue();
                            if(monto <= 0){
                                result.rejectValue("montoTarifa","invalid", new Object[]{ crearPlanPago.getMontoTarifa()}, "Debe ingresar un monto de tarifa superior a 0");
                            }
                        }   
                        if(crearPlanPago.getMontoInicial().isEmpty() || "".equals(crearPlanPago.getMontoInicial())){
                            result.rejectValue("montoInicial","invalid", new Object[]{ crearPlanPago.getMontoInicial()}, "Debe ingresar un monto de incial superioro igual a 0 y menor al monto de la tarifa");
                        }else{
                            monto = nf.parse(crearPlanPago.getMontoInicial()).doubleValue();
                            montoEquipo = nf.parse(crearPlanPago.getMontoTarifa()).doubleValue();
                            if(monto < 0 || monto >= montoEquipo){
                                result.rejectValue("montoInicial","invalid", new Object[]{ crearPlanPago.getMontoInicial()}, "Debe ingresar un monto de tarifa superior o igual a 0 y menor al monto de la tarifa");
                            }
                        }   
                        if(terminales.get(0).getPagoContado() != null){
                            if(terminales.get(0).getPagoContado() == 1){
                                result.rejectValue("idplan","invalid", new Object[]{ crearPlanPago.getIdplan()}, "No puede asignar plan de equipo ya que se pago de contado");
                            }
                        }
                        break;
                    case "3":
                        //plan de uso
                        if("porcComisionBancaria".equals(crearPlanPago.getTipoPagoMantenimiento())){
                            if(crearPlanPago.getPorcComisionBancaria().isEmpty() || "".equals(crearPlanPago.getPorcComisionBancaria())){
                                result.rejectValue("porcComisionBancaria","invalid", new Object[]{ crearPlanPago.getPorcComisionBancaria()}, "Debe ingresar un porcentaje de comision Bancaria");
                            }else{
                                monto = nf.parse(crearPlanPago.getPorcComisionBancaria()).doubleValue();
                                if(monto <= 0 || monto > 100){
                                    result.rejectValue("porcComisionBancaria","invalid", new Object[]{ crearPlanPago.getPorcComisionBancaria()}, "El porcentaje de Comision Bancaria debe ser mayor a 0 y menor o igual a 100");
                                }
                            }
                        }else{
                            if(crearPlanPago.getMontoTarifa().isEmpty() || "".equals(crearPlanPago.getMontoTarifa())){
                                result.rejectValue("montoTarifa","invalid", new Object[]{ crearPlanPago.getMontoTarifa()}, "Debe ingresar un monto de tarifa superior a 0");
                            }else{
                                monto = nf.parse(crearPlanPago.getMontoTarifa()).doubleValue();
                                if(monto <= 0){
                                    result.rejectValue("montoTarifa","invalid", new Object[]{ crearPlanPago.getMontoTarifa()}, "Debe ingresar un monto de tarifa superior a 0");
                                }
                            }
                        }   
                        break;
                    default:
                        //cualquier otro tipo de plan - plan de uso
                        if(crearPlanPago.getMontoTarifa().isEmpty() || "".equals(crearPlanPago.getMontoTarifa())){
                            result.rejectValue("montoTarifa","invalid", new Object[]{ crearPlanPago.getMontoTarifa()}, "Debe ingresar un monto de tarifa superior a 0");
                        }else{
                            monto = nf.parse(crearPlanPago.getMontoTarifa()).doubleValue();
                            if(monto <= 0){
                                result.rejectValue("montoTarifa","invalid", new Object[]{ crearPlanPago.getMontoTarifa()}, "Debe ingresar un monto de tarifa superior a 0");
                            }
                        }   
                        break;
                }
            
            
            if (result.hasErrors()) {
                return new ModelAndView("crearPlanPago");
            }
            //carga del objeto a guardar
            resultado.setIdplan(Integer.parseInt(crearPlanPago.getIdplan()));
            resultado.setCodigoComercio(Integer.parseInt(terminales.get(0).getCodigoComercio()));
            resultado.setCodigoAfiliado(terminales.get(0).getCodigoAfiliado());
            resultado.setCodigoTerminal(crearPlanPago.getCodigoTerminal());
                        
            /* Validacion de monto y frecuencia.
             * En caso de que la recibida sea igual al del plan, se deja en NULL en PlanPago si es plan de mantenimiento o uso
             * si el plan es de equipo se Hereda en la tabla PlanPago
            */
            List<Planes> planes = this.InformationPlanList();
            Plan pl = new Plan();
            pl = plan.getById(Integer.parseInt(crearPlanPago.getIdplan()));
            Integer planPagoDummyId = this.planpago.getPlanPagoDummy(crearPlanPago.getCodigoTerminal());
            /*
            String codTipoPlan = null;
            switch (crearPlanPago.getCodtipoplan()) {
                case "3":
                    //plan de uso
                    if("porcComisionBancaria".equals(crearPlanPago.getTipoPagoMantenimiento())){
                        monto = nf.parse(crearPlanPago.getPorcComisionBancaria()).doubleValue();
                        if(pl.getPorcComisionBancaria() == null){
                            resultado.setPorcComisionBancaria(new BigDecimal(monto));
                        }else if(0 != pl.getPorcComisionBancaria().compareTo(new BigDecimal(monto)) ){
                            resultado.setPorcComisionBancaria(new BigDecimal(monto));
                        }
                        resultado.setMontoTarifa(new BigDecimal(0));
                    }else{
                        //monto tarifa
                        monto = nf.parse(crearPlanPago.getMontoTarifa()).doubleValue();
                        if(pl.getMontoTarifa().compareTo(new BigDecimal(monto)) != 0){
                            resultado.setMontoTarifa(new BigDecimal(monto));
                        }
                        //monto fijo
                        montoFijo = 0;
                        if(crearPlanPago.getMontoFijo())
                            montoFijo = 1;
                        //validacion contra el plan
                        if(!Objects.equals(montoFijo, pl.getMontoFijo())){
                            resultado.setMontoFijo(montoFijo);
                        }
                    }   
                    if(crearPlanPago.getFrecuencia() != pl.getFrecuencia()){
                        resultado.setFrecuencia(crearPlanPago.getFrecuencia());
                    }
                    break;
                case "2":
                    //plan de ecuipo
                    //monto tarifa
                    monto = nf.parse(crearPlanPago.getMontoTarifa()).doubleValue();
                    resultado.setMontoTarifa(new BigDecimal(monto));
                    
                    //monto inicial
                    monto = nf.parse(crearPlanPago.getMontoInicial()).doubleValue();
                    resultado.setMontoInicial(new BigDecimal(monto));
                    
                    resultado.setCantCuotas(pl.getPlanplazo());
                    break;
                default:
                    //cualquier otro tipo de plan - plan de uso
                    //monto tarifa
                    monto = nf.parse(crearPlanPago.getMontoTarifa()).doubleValue();
                    if(pl.getMontoTarifa().compareTo(new BigDecimal(monto)) != 0){
                        resultado.setMontoTarifa(new BigDecimal(monto));
                    }
                    break;
            }
            
            */
            for(Planes plan: planes){
                //se recorrerá por cada plan en el array de planes activos, para compilar la informacion necesaria para la creacion del plan
                for(PlanPago pp : planesActivos){
                    if((Objects.equals(pp.getIdplan(), plan.getId())) && !(Objects.equals(pp.getId(), planPagoDummyId))){
                        terminalPlanActivoTipo.put(plan.getCodtipoplan(), pp.getId());
                    }
                    
                }
            }
            
            
            // seteo de estatus activo
            resultado.setEstatus(23);//estatus activo de planes
            
            /*
             *  REVISION DE PLANES ACTIVOS PARA APLICAR LAS SIGUIENTES REGLAS:
             *  PLANES DE MANTENIMIENTO
             *  1.- Si se posee un plan activo de MANTENIMIENTO
             *      - se Inhabilita el Plan actual con fecha fin Ultimo dia del mes en curso
             *      - se crea el nuevo Plan con fecha de inicio del 1er dia del mes siguiente
             *      - NO se cargan cuotas nuevas, de esto se encargara el proceso mensual de creacion de cuotas
             *  2.- Si NO posee plan activo de MANTENIMIENTO 
             *      - se crea el nuevo plan con fecha inicio del dia de ejecucion
             *      - se cargan las cuotas (se ejecuta SP de cuotas)
             *  PLANES DE EQUIPO
             *  1.- Si se posee un plan activo de EQUIPO
             *      - se inhabilita el plan actual
             *      - se cargan cuotas del mes
             *  2.- Si NO posee un plan activo de EQUIPO
             *      - se crea el nuevo plan con fecha inicio del dia de ejecucion
             *      - se cargan cuotas (se ejecuta SP de cuotas)
            */
            //id del plan activo del mismo tipo
            Integer planPagoId = terminalPlanActivoTipo.get(pl.getCodtipoplan());
            //LocalDate fechaEjecucion = LocalDateTime.now().toLocalDate();
            Fecha today = new Fecha();
            LocalDate fechaEjecucion = LocalDateTime.now().toLocalDate();
            java.util.Date fechaActual = new java.util.Date();
            java.util.Date fechaFinMes;        
            Calendar calFin = Calendar.getInstance();	
            calFin.setTime(fechaActual);
            calFin.set(fechaEjecucion.getYear(), fechaEjecucion.getMonthValue()-1, 1);
            calFin.set(fechaEjecucion.getYear(), fechaEjecucion.getMonthValue()-1, calFin.getActualMaximum(Calendar.DAY_OF_MONTH));
            
            fechaFinMes = calFin.getTime();
            calFin.add(Calendar.DATE, 1);
            java.util.Date fechaIniMesSig = calFin.getTime();
            
            //DateFormat fechaHora = new SimpleDateFormat("yyyy-MM-dd");
            //String fechaFin = fechaHora.format(fecha);
            
            java.sql.Date sqlFechaActual = new java.sql.Date(fechaActual.getTime());
            //+1 DAY
            //java.sql.Date sqlFechaInicio = new java.sql.Date(fechaActual.getTime()+86400000);
            java.sql.Date sqlFechaInicio = new java.sql.Date(fechaActual.getTime());
            
            //se coloca la fecha fin del nuevo registro como nulo
            resultado.setFechafin(null); 
            
            //desactivo plan dummy si se insertara un plan de mantenimiento o uso
            if(Integer.parseInt(pl.getCodtipoplan()) != 2){
                if(planPagoDummyId != null){
                    PlanPago planDummy = new PlanPago();
                    planDummy = planpago.getById(planPagoDummyId);
                    planDummy.setEstatus(24);
                    planDummy.setFechafin(sqlFechaActual);
                    planpago.updatePlanPago(planDummy);
                    bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "Se Desactiva Plan Pago Dummy ID: "+ planDummy.getId() +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
                    
                }
            }
            
            if (planPagoId == null){
                //NO POSEE PLAN DEL TIPO SELECCIONADO
                //PLAN DE MANTENIMIENTO, USO Y EQUIPO
                //resultado.setFechafin((java.sql.Date) fecha);  
                resultado.setFechainicio(sqlFechaInicio); 
                bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "Crear Plan Pago ID: "+ resultado.getId() +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
                planpago.savePlanPago(resultado);
                String tipo = "1";
                String terminal = crearPlanPago.getCodigoTerminal().toString(); 
                String inicioMes = "0";
                String mes = Integer.toString(fechaEjecucion.getMonthValue()); 
                String anio = Integer.toString(fechaEjecucion.getYear()); 
                List<GenerarCuotas> resultCuotas = cuotas.setCuotasComerciosList(tipo, terminal , inicioMes, mes, anio);
                if(resultCuotas.get(0).getResult()==1){
                    bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "Creadas Exitosamente las cuotas del Plan Pago ID: "+ resultado.getId() +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
                }else{
                    bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "Fallo la Creacion de las cuotas del Plan Pago ID: "+ resultado.getId() +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
                }
                //ejecutar SP de cuotas
            }else{
                //POSEE PLAN DEL TIPO SELECCIONADO (PARA USO O MANTENIMIENTO) SE DESACTIVA EL ANTERIOR
                if(Integer.parseInt(pl.getCodtipoplan()) != 2){
                    PlanPago oldPlan = new PlanPago();
                    oldPlan = planpago.getById(planPagoId);
                    oldPlan.setEstatus(24); // estatus inactivo
                    oldPlan.setFechafin(sqlFechaActual);  
                    //se guarda el log y el viejo plan
                    bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "Se Desactiva Plan Pago ID: "+ oldPlan.getId() +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
                    planpago.updatePlanPago(oldPlan);
                    //desactiva las cuotas del plan desactivado que sean posteriores al 
                    //Utilidades.convierteFechaSql(String fecha).toString();
                    cobranza.updateStatusCuotas(Integer.toString(oldPlan.getId()),"28",sqlFechaActual.toString());
                    bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "Se Desactiva Plan Pago ID: "+ oldPlan.getId() +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");

                }
                //SE INSERTA NUEVO PLAN
                resultado.setFechainicio(sqlFechaInicio);
                //se guarda el log y el nuevo plan
                bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "Crear Plan Pago ID: "+ resultado.getId() +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
                planpago.savePlanPago(resultado);
                //se cargan las cuotas nuevas para el plan nuevo
                String tipo = "1";
                String terminal = crearPlanPago.getCodigoTerminal().toString(); 
                String inicioMes = "0";
                String mes = Integer.toString(fechaEjecucion.getMonthValue()); 
                String anio = Integer.toString(fechaEjecucion.getYear()); 
                List<GenerarCuotas> resultCuotas = cuotas.setCuotasComerciosList(tipo, terminal , inicioMes, mes, anio);
                System.out.println(resultCuotas.get(0).getMessage());
                if(resultCuotas.get(0).getResult()==1){
                    bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "Creadas Exitosamente las cuotas del Plan Pago ID: "+ resultado.getId() +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
                }else{
                    bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "Fallo la Creacion de las cuotas del Plan Pago ID: "+ resultado.getId() +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
                }
            }
            //Guarda los datos ingresados por el usuario
            //planpago.savePlanPago(resultado);
            Map<String, Object> myModel = new HashMap<>();        
            myModel.put("listadoTerminalPlanes", this.planpago.getPlanPagoByTerminalDetailList(resultado.getCodigoTerminal()));
            myModel.put("codigoTerminal",crearPlanPago.getCodigoTerminal().toString());
          
            myModel.put("messageError", "Registro Creado Satisfactoriamente");

            return new ModelAndView("redirect:/listadoTerminalPlanes.htm?messageError=&codigoTerminal="+crearPlanPago.getCodigoTerminal(), "model", myModel);  
        
        }catch (Exception cve) {
            String str= new String("No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage());
            Integer longitud = str.length();
            if (longitud > 351){
                longitud = 350;
            }
            bitacora.saveLogs(usuario.get(0).getId(), 1, 6, truncate(str,longitud), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("crearPlanPago", "model", modelo);
        }        
        
    }
    
     
    @RequestMapping(value="/editarPlanPago.htm", method = RequestMethod.POST)
    public ModelAndView editarPlanPagoSubmit(@ModelAttribute("editarPlanPago") @Valid PlanPagoForm editarPlanPago, BindingResult result, Model modelo, HttpServletRequest request)
    {
        NumberFormat nf = NumberFormat.getInstance(español);
        double monto= 0;         
        double montoEquipo = 0; 
                      
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        String oldValue, newValue;
        PlanPago resultado = new PlanPago();
        
        List<Abono> terminales = comercioService.getAbonoByTerminal(editarPlanPago.getCodigoTerminal().toString()); 
        
        try{     
            List<PlanCuota> lcuotas = cuotasBean.getPlanCuotaByPlanPagoList(editarPlanPago.getId());
            if(null != editarPlanPago.getCodtipoplan()) 
                switch (editarPlanPago.getCodtipoplan()) {
                    case "2":
                        //plan de ecuipo
                        if(editarPlanPago.getMontoTarifa().isEmpty() || "".equals(editarPlanPago.getMontoTarifa())){
                            result.rejectValue("montoTarifa","invalid", new Object[]{ editarPlanPago.getMontoTarifa()}, "Debe ingresar un monto de tarifa superior a 0");
                        }else{
                            monto = nf.parse(editarPlanPago.getMontoTarifa()).doubleValue();
                            if(monto <= 0){
                                result.rejectValue("montoTarifa","invalid", new Object[]{ editarPlanPago.getMontoTarifa()}, "Debe ingresar un monto de tarifa superior a 0");
                            }
                        }   
                        if(editarPlanPago.getMontoInicial().isEmpty() || "".equals(editarPlanPago.getMontoInicial())){
                            result.rejectValue("montoInicial","invalid", new Object[]{ editarPlanPago.getMontoInicial()}, "Debe ingresar un monto de incial superioro igual a 0 y menor al monto de la tarifa");
                        }else{
                            monto = nf.parse(editarPlanPago.getMontoInicial()).doubleValue();
                            montoEquipo = nf.parse(editarPlanPago.getMontoTarifa()).doubleValue();
                            if(monto < 0 || monto >= montoEquipo){
                                result.rejectValue("montoInicial","invalid", new Object[]{ editarPlanPago.getMontoInicial()}, "Debe ingresar un monto de tarifa superior o igual a 0 y menor al monto de la tarifa");
                            }
                        }   
                        if(terminales.get(0).getPagoContado() != null){
                            if(terminales.get(0).getPagoContado() == 1){
                                result.rejectValue("idplan","invalid", new Object[]{ editarPlanPago.getIdplan()}, "No puede asignar plan de equipo ya que se pago de contado");
                            }
                        }
                        break;
                    case "3":
                        //plan de mantenimiento
                        if("porcComisionBancaria".equals(editarPlanPago.getTipoPagoMantenimiento())){
                            if(editarPlanPago.getPorcComisionBancaria().isEmpty() || "".equals(editarPlanPago.getPorcComisionBancaria())){
                                result.rejectValue("porcComisionBancaria","invalid", new Object[]{ editarPlanPago.getPorcComisionBancaria()}, "Debe ingresar un porcentaje de comision Bancaria");
                            }else{
                                monto = nf.parse(editarPlanPago.getPorcComisionBancaria()).doubleValue();
                                if(monto <= 0 || monto > 100){
                                    result.rejectValue("porcComisionBancaria","invalid", new Object[]{ editarPlanPago.getPorcComisionBancaria()}, "El porcentaje de Comision Bancaria debe ser mayor a 0 y menor o igual a 100");
                                }
                            }
                        }else{
                            if(editarPlanPago.getMontoTarifa().isEmpty() || "".equals(editarPlanPago.getMontoTarifa())){
                                result.rejectValue("montoTarifa","invalid", new Object[]{ editarPlanPago.getMontoTarifa()}, "Debe ingresar un monto de tarifa superior a 0");
                            }else{
                                monto = nf.parse(editarPlanPago.getMontoTarifa()).doubleValue();
                                if(monto <= 0){
                                    result.rejectValue("montoTarifa","invalid", new Object[]{ editarPlanPago.getMontoTarifa()}, "Debe ingresar un monto de tarifa superior a 0");
                                }
                            }
                        }   
                        break;
                    default:
                        //cualquier otro tipo de plan - plan de uso
                        if(editarPlanPago.getMontoTarifa().isEmpty() || "".equals(editarPlanPago.getMontoTarifa())){
                            result.rejectValue("montoTarifa","invalid", new Object[]{ editarPlanPago.getMontoTarifa()}, "Debe ingresar un monto de tarifa superior a 0");
                        }else{
                            monto = nf.parse(editarPlanPago.getMontoTarifa()).doubleValue();
                            if(monto <= 0){
                                result.rejectValue("montoTarifa","invalid", new Object[]{ editarPlanPago.getMontoTarifa()}, "Debe ingresar un monto de tarifa superior a 0");
                            }
                        }   
                        break;
                }
            if (result.hasErrors()) {
                return new ModelAndView("editarPlanPago");
            }
           //editarPlanPago.setFechainicio((Utilidades.cambiaFormatoFecha((editarPlanPago.getFechainicio()))));  
           //editarPlanPago.setFechafin((Utilidades.cambiaFormatoFecha(editarPlanPago.getFechafin())));
            //Guarda los datos modificados por el usuario
            oldValue = ToStringBuilder.reflectionToString(plan.getById(editarPlanPago.getId()));
            resultado = planpago.getById(editarPlanPago.getId());
            
            /*
            resultado.setFrecuencia(editarPlanPago.getFrecuencia());
            if(!"".equals(editarPlanPago.getMontoTarifa())  && editarPlanPago.getMontoTarifa() != null){
                monto = nf.parse(editarPlanPago.getMontoTarifa()).doubleValue();
                resultado.setMontoTarifa(new BigDecimal(monto));
            }else{
                resultado.setMontoTarifa(new BigDecimal(0));
            }
            
            if(editarPlanPago.getMontoFijo())
                resultado.setMontoFijo(1);
            else 
                resultado.setMontoFijo(0);
            
            if(!"".equals(editarPlanPago.getPorcComisionBancaria()) && editarPlanPago.getPorcComisionBancaria() != null){
                monto = nf.parse(editarPlanPago.getPorcComisionBancaria()).doubleValue();
                resultado.setPorcComisionBancaria(new BigDecimal(monto));
            }else{
                resultado.setPorcComisionBancaria(null);
            }
            
            if(!"".equals(editarPlanPago.getMontoInicial()) && editarPlanPago.getMontoInicial() != null){
                monto = nf.parse(editarPlanPago.getMontoInicial()).doubleValue();
                resultado.setMontoInicial(new BigDecimal(monto));
            }else{
                resultado.setMontoInicial(null);
            }
            */
            resultado.setEstatus(editarPlanPago.getEstatus());
            if(editarPlanPago.getEstatus() == 24){
                java.util.Date fechaActual = new java.util.Date();
                java.sql.Date sqlFechaActual = new java.sql.Date(fechaActual.getTime());
                resultado.setFechafin(sqlFechaActual);
            }
            planpago.updatePlanPago(resultado);
            newValue = ToStringBuilder.reflectionToString(planpago.getById(editarPlanPago.getId()));
            bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "Mantenimiento Editar Plan ID: "+ editarPlanPago.getId() +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), oldValue, newValue);
            for(PlanCuota c : lcuotas){
                if(c.getEstatus() == 25){
                    c.setEstatus(28);
                    cuotasBean.updatePlanCuota(c);
                }
            }
            
            List<PlanPagoDetail> planPagoDetail = this.planpago.getPlanPagoByTerminalDetailList(editarPlanPago.getCodigoTerminal());
            /*
            Boolean planDummy = true;
            for(PlanPagoDetail pp : planPagoDetail){
                if(pp.getEstatusId() == 23 && pp.getCodTipoPlan() != 2){
                    planDummy = false;
                }
            }
            if(planDummy){
                //inserta plan dummy si el terminal se quedo sin plan activo de por uso o Mantenimiento
                PlanPago crearPlanDummy = new PlanPago();
                crearPlanDummy.setIdplan(0);
                crearPlanDummy.setCodigoComercio(editarPlanPago.getCodigoComercio());
                crearPlanDummy.setCodigoAfiliado(editarPlanPago.getCodigoAfiliado());
                crearPlanDummy.setCodigoTerminal(editarPlanPago.getCodigoTerminal());
                crearPlanDummy.setEstatus(23);
                java.util.Date fechaActual = new java.util.Date();
                java.sql.Date sqlFechaActual = new java.sql.Date(fechaActual.getTime());
                crearPlanDummy.setFechainicio(sqlFechaActual);
                planpago.savePlanPago(crearPlanDummy);
                bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "Crear Plan Dummy con el Pago ID: "+ resultado.getId() +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
                
            }*/
            Map<String, Object> myModel = new HashMap<>();        
          //  myModel.put("listadoTerminalPlanes", this.planpago.getPlanPagoList());
            myModel.put("listadoTerminalPlanes", this.planpago.getPlanPagoByTerminalDetailList(editarPlanPago.getCodigoTerminal()));
            myModel.put("codigoTerminal",editarPlanPago.getCodigoTerminal().toString());
            myModel.put("messageError", "Registro Actualizado Satisfactoriamente");

            return new ModelAndView("redirect:/listadoTerminalPlanes.htm?messageError=&codigoTerminal="+editarPlanPago.getCodigoTerminal(), "model", myModel);  
        
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("editarPlanPago", "model", modelo);
        }        
        
    }
    
    
   
    @RequestMapping(value="/eliminarPlanPago.htm", method = RequestMethod.POST)
    public ModelAndView eliminarPlanPagoSubmit(@ModelAttribute("eliminarPlanPago") @Valid PlanPago eliminarPlanPago, BindingResult result, Model modelo, HttpServletRequest request)
    {
        
       if (result.hasErrors()) {
            return new ModelAndView("eliminarPlanPago");
       }                
        
        PlanPago resultado = new PlanPago();
        
        resultado.setId(eliminarPlanPago.getId());
        resultado.setCodigoComercio(eliminarPlanPago.getCodigoComercio());
        resultado.setCodigoAfiliado(eliminarPlanPago.getCodigoAfiliado()); 
        resultado.setCodigoTerminal(eliminarPlanPago.getCodigoTerminal());
        resultado.setMontoTarifa(eliminarPlanPago.getMontoTarifa());
        resultado.setFrecuencia(eliminarPlanPago.getFrecuencia());
        resultado.setEstatus(eliminarPlanPago.getEstatus());
 
       
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        try{
            //resultado.setFechainicio((Utilidades.cambiaFormatoFecha((eliminarPlanPago.getFechainicio()))));  
            //resultado.setFechafin((Utilidades.cambiaFormatoFecha(eliminarPlanPago.getFechafin()))); 
           
            bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "Mantenimiento Eliminar Plan Pago ID: "+ eliminarPlanPago.getId() +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
        
            //Eliminar el pos seleccionado por el usuario
            planpago.removePlanPago(resultado);

            Map<String, Object> myModel = new HashMap<>();        
           // myModel.put("listadoTerminalPlanes", this.planpago.getPlanPagoList());
            myModel.put("listadoTerminalPlanes", this.planpago.getPlanPagoByTerminalDetailList(resultado.getCodigoTerminal()));
            myModel.put("messageError", "Registro Eliminado Satisfactoriamente");

            return new ModelAndView("listadoTerminalPlanes", "model", myModel);
        
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuario.get(0).getId(), 1, 6, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("eliminarPlanPago", "model", modelo);
        }        
        
    }
    
    @ModelAttribute("InformationPlanList")
    public List InformationPlanList() {
        
        List<Planes> InformationPlanList = null;
        
        try{
            InformationPlanList = plan.getInformationPlanList(0); // todos los planes activos = 0, inactivos = 1, todos = 2 
        }catch (Exception cve) {                                   
            logger.error(cve.getMessage());            
        }
                                     		         
        return InformationPlanList;
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
    
  
        
    @ModelAttribute("afiliadoslList")
    public List afiliadoslList() {
        
        List<Afiliado> afiliadoslList = null;
        
        try{
            afiliadoslList = comercioService.getAfiliadoList();
        }catch (Exception cve) {                       
            logger.error(cve.getMessage());                       
        } 
                        		         
        return afiliadoslList;
    }
} 
    
   