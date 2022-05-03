package com.tranred.milpagosapp.web;

import com.tranred.milpagosapp.domain.*;
import com.tranred.milpagosapp.repository.IAbonoDAO;
import com.tranred.milpagosapp.repository.IEstatusDAO;
import com.tranred.milpagosapp.repository.IPerfilDAO;
import com.tranred.milpagosapp.repository.JPACodigoAreaDAO;
import com.tranred.milpagosapp.service.ComercioForm;
import com.tranred.milpagosapp.service.ConsultaComercioForm;
import com.tranred.milpagosapp.service.IAdminMantenimiento;
import com.tranred.milpagosapp.service.IAliadosService;
import com.tranred.milpagosapp.service.IBitacoraService;
import com.tranred.milpagosapp.service.IComercioService;
import com.tranred.milpagosapp.domain.Plan;
import com.tranred.milpagosapp.repository.JPAPlanDAO;
import com.tranred.milpagosapp.repository.JPAPlanPagoDAO;
import com.tranred.milpagosapp.service.AbonoForm;
import com.tranred.milpagosapp.util.Utilidades;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
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
import org.springframework.web.servlet.view.RedirectView;

/**
 *  Clase actua como controlador para el módulo de Comercios.
 *
 * @author mggy@sistemasemsys.com
 * @version 0.1
 * 
 */

@Controller
public class ComercioController {
    
    protected final Log logger = LogFactory.getLog(getClass());
    
    @Autowired
    private IComercioService comercioService;
    
    @Autowired
    private IAliadosService aliadosService;
    
    @Autowired
    private JPACodigoAreaDAO codigosDAO;
    
    @Autowired
    private IEstatusDAO estatusDAO;       
    
    @Autowired
    private IBitacoraService bitacora;
    
    @Autowired
    private IAdminMantenimiento mantenimientoAdmin;

    @Autowired
    private IAbonoDAO abonoDao;

    @Autowired
    private IPerfilDAO perfilDao;
    
    @Autowired
    private JPAPlanDAO plan;
    
    @Autowired
    private JPAPlanPagoDAO planpago;
    
    Locale español = new Locale("es");
    
    //Asignamos el estado inicial de los formularios crear, consultar y editar con las propiedades de la clase Aliado
    @ModelAttribute("crearComercio")
    public ComercioForm createModelCrear() {
        return new ComercioForm();
    }        
    
    @ModelAttribute("consultaComercio")
    public ConsultaComercioForm createModelConsulta() {
        return new ConsultaComercioForm();
    }
    
    @ModelAttribute("resultadoConsultaComercio")
    public ComercioForm createModelConsultaAliado() {
        return new ComercioForm();
    }
    
    @ModelAttribute("guardarTerminalComercio")
    public AbonoForm createModelGuardarTerminal() {
        return new AbonoForm();
    }
    
    @RequestMapping(value="/crearComercio.htm", method = RequestMethod.GET)
    protected ModelAndView crearComercio(@RequestParam(value = "messageError") String messageError, HttpServletRequest request, Model modelo) throws ServletException {
        
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuarioLogin = (List<Usuario>) misession.getAttribute("usuario.datos");
        List<Aliado> aliado;
        Map<String, Object> myModel = new HashMap<>();
        String existeAliado;
        
        if("0".equals(messageError)){
            messageError = "Comercio Creado Satisfactoriamente";
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

            myModel.put("existeAliado", existeAliado);        
            myModel.put("messageError", messageError);
            return new ModelAndView("crearComercio", "model", myModel);
        
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 16, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("crearComercio" ,"model" ,modelo);
        }
                
    }        
    
    @RequestMapping(value="/consultaComercio.htm", method = RequestMethod.GET)
    protected ConsultaComercioForm consultaComercio(HttpServletRequest request, Model modelo) throws ServletException {
                
        ConsultaComercioForm consultaComercio = new ConsultaComercioForm();        
        return consultaComercio;
    }
    
    @RequestMapping(value="/resultadoConsultaComercio.htm", method = RequestMethod.GET)
    protected ModelAndView resultadoConsultaComercio(@RequestParam(value = "messageError") String messageError,@RequestParam(value = "tipo") String tipo,@RequestParam(value = "identifica") String identificacion, HttpServletRequest request, Model modelo) throws ServletException {
        
        ComercioForm resultadoConsultaComercio = new ComercioForm();                
        List<ComercioConsulta> resultado;
        List<ComerciosXAfiliado> resultadoComerciosXAfiliado;
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuarioLogin = (List<Usuario>) misession.getAttribute("usuario.datos");
        NumberFormat nf = NumberFormat.getCurrencyInstance(español);
        Perfil perfil;
        
        if("0".equals(messageError)){
           modelo.addAttribute("messageError", "Registro actualizado satisfactoriamente"); 
        }
        try{
          
            resultado = comercioService.getComercioByRIF(tipo + identificacion);
            resultadoComerciosXAfiliado = comercioService.getComerciosXAfiliadoByCodComercio(resultado.get(0).getCodigoComercio());
            resultadoConsultaComercio.setCodigoComercio(resultado.get(0).getCodigoComercio());
            resultadoConsultaComercio.setTipoIdentificacion(tipo);
            resultadoConsultaComercio.setRifComercio(identificacion);        
            resultadoConsultaComercio.setDescripcionComercio(resultado.get(0).getDescripcionComercio());
            resultadoConsultaComercio.setTelefonoMovil(resultado.get(0).getTelefonoCelular());
            resultadoConsultaComercio.setTelefonoLocal(resultado.get(0).getTelefonoHabitacion());
            resultadoConsultaComercio.setEmail(resultado.get(0).getEmail());
            resultadoConsultaComercio.setCodigoCategoria(resultado.get(0).getCategoriaComercio());
            resultadoConsultaComercio.setCodigoContacto(Integer.parseInt(resultado.get(0).getCodigoContacto()));
            resultadoConsultaComercio.setContactoNombres(resultado.get(0).getNombreContacto());
            resultadoConsultaComercio.setContactoApellidos(resultado.get(0).getApellidoContacto());
            resultadoConsultaComercio.setCodigoTipoContrato(resultado.get(0).getTipoContrato());
            if(resultado.get(0).getFechaInicioContrato()!=null){
                resultadoConsultaComercio.setFechaInicioContrato(new SimpleDateFormat("dd-MM-yyyy").format(Date.valueOf(resultado.get(0).getFechaInicioContrato())));
            }
            if(resultado.get(0).getFechaFinContrato()!=null){
                resultadoConsultaComercio.setFechaFinContrato(new SimpleDateFormat("dd-MM-yyyy").format(Date.valueOf(resultado.get(0).getFechaFinContrato())));
            }            
            resultadoConsultaComercio.setExcluirArchivoPago(resultado.get(0).getExcluirArchivoPago());
            resultadoConsultaComercio.setDiasOperacion(resultado.get(0).getDiasOperacion());
            resultadoConsultaComercio.setCodigoBancoCuentaAbono(resultado.get(0).getCodigoBancoCuentaAbono());
            resultadoConsultaComercio.setNumeroCuentaAbono(resultado.get(0).getCuentaAbono());
            resultadoConsultaComercio.setPuntoAdicional(resultado.get(0).getPuntoAdicional());
            resultadoConsultaComercio.setCodigoBancoCuentaAbono2(resultado.get(0).getCodigoBancoCuentaAbono2());
            resultadoConsultaComercio.setNumeroCuentaAbono2(resultado.get(0).getCuentaAbono2());
            resultadoConsultaComercio.setCodigoBancoCuentaAbono3(resultado.get(0).getCodigoBancoCuentaAbono3());
            resultadoConsultaComercio.setNumeroCuentaAbono3(resultado.get(0).getCuentaAbono3());            
            resultadoConsultaComercio.setEntregoGarantiaFianza(resultado.get(0).getEntregoGarantia());
            resultadoConsultaComercio.setModalidadPos(resultado.get(0).getModalidadPos());
            resultadoConsultaComercio.setTipoPos(resultado.get(0).getTipoPos());
            resultadoConsultaComercio.setModalidadGarantia(resultado.get(0).getModalidadGarantia());
            if(resultado.get(0).getMontoGarantia()!=null){
                resultadoConsultaComercio.setMontoGarantiaFianza(Utilidades.FormatearNumero(resultado.get(0).getMontoGarantia()));
            }            
            if(resultado.get(0).getFechaGarantiaFianza()!=null){                
                resultadoConsultaComercio.setFechaGarantiaFianza(new SimpleDateFormat("dd-MM-yyyy").format(Date.valueOf(resultado.get(0).getFechaGarantiaFianza())));
            }            
            resultadoConsultaComercio.setCodigoAliado(resultado.get(0).getCodigoAliado());
            if(resultado.get(0).getCodigoAliado()!=null){
                resultadoConsultaComercio.setAliadoComercial(resultado.get(0).getAliadoNombreCompleto());
            }            
            resultadoConsultaComercio.setObservacionesComercio(resultado.get(0).getObservacionesComercio());
            resultadoConsultaComercio.setDireccionComercio(resultado.get(0).getDireccionComercio());
            resultadoConsultaComercio.setDireccionHabitacion(resultado.get(0).getDireccionHabitacion());
            resultadoConsultaComercio.setDireccionPos(resultado.get(0).getDireccionPos());
            resultadoConsultaComercio.setRecaudosComercio(resultado.get(0).getRecaudos());        
            resultadoConsultaComercio.setEstatusComercio(resultado.get(0).getEstatus());
            resultadoConsultaComercio.setPagaIVA(resultado.get(0).getPagaIVA());
            resultadoConsultaComercio.setComisionComercio(resultado.get(0).getComisionComercio());
            modelo.addAttribute("resultadoConsultaComercio", resultadoConsultaComercio);

            perfil = perfilDao.getById(Integer.parseInt(usuarioLogin.get(0).getPerfilId().trim()));
            modelo.addAttribute("afiliadosComercio", resultadoComerciosXAfiliado);
            modelo.addAttribute("editACI", Arrays.asList(perfil.getOpciones().split(",")).contains("31") ? Boolean.TRUE : Boolean.FALSE);
            modelo.addAttribute("editCA", Arrays.asList(perfil.getOpciones().split(",")).contains("36") ? Boolean.TRUE : Boolean.FALSE);

            return new ModelAndView("resultadoConsultaComercio", "model" ,modelo);
            
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 17, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("resultadoConsultaComercio" ,"model" ,modelo);
        }        
    }
    
    @RequestMapping(value="/guardarTerminalComercio.htm", method = RequestMethod.GET)
    protected ModelAndView guardarTerminalComercio(@RequestParam(value = "codComercio") String codComercio,@RequestParam(value = "codBancoCuentaAbono") String codBancoCuentaAbono,@RequestParam(value = "numCuentaAbono") String numCuentaAbono,HttpServletRequest request, Model modelo) throws ServletException {
        
        AbonoForm abono = new AbonoForm();
        abono.setCodigoComercio(codComercio);
        abono.setCodigoBancoCuentaAbono(codBancoCuentaAbono);
        abono.setNumeroCuentaAbono(numCuentaAbono);
        /*Map<String, Object> myModel = new HashMap<>();        
                            
        myModel.put("codComercio", codComercio);        
        myModel.put("codBancoCuentaAbono", codBancoCuentaAbono);
        myModel.put("numCuentaAbono", numCuentaAbono);*/
  
        return new ModelAndView("guardarTerminalComercio", "guardarTerminalComercio", abono);               
                
    }
    
    
    @RequestMapping(value="/editarTerminalComercio.htm", method = RequestMethod.GET)
    protected ModelAndView editarTerminalComercio(@RequestParam(value = "codTerminal") String codTerminal, HttpServletRequest request, Model modelo) throws ServletException {
                      
        List<Abono> resultado;
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuarioLogin = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        List<Planes> planes = this.InformationPlanList();
        List<PlanPago> planesActivos = this.planpago.getPlanPagoByTerminalList(codTerminal, 23);
        
        //marca del abono para saber si ya tiene un plan asociado de equipo, si posee un plan de equipo no se permitira pagar de contado
        Boolean poseePlanEquipo = false;
        for(PlanPago p : planesActivos){
            for(Planes pl : planes){
                if(Objects.equals(pl.getId(), p.getIdplan()) && Objects.equals(pl.getCodtipoplan(), "2")){
                    poseePlanEquipo = true;
                }
            }
            
        }
        
        try{
        
            resultado = comercioService.getAbonoByTerminal(codTerminal);        
            AbonoForm abono = new AbonoForm();
            abono.setId(resultado.get(0).getId());
            abono.setCodigoTerminal(resultado.get(0).getCodigoTerminal());
            abono.setCodigoAfiliado(resultado.get(0).getCodigoAfiliado());
            abono.setCodigoComercio(resultado.get(0).getCodigoComercio());
            abono.setCodigoBancoCuentaAbono(resultado.get(0).getCodigoBancoCuentaAbono());
            abono.setNumeroCuentaAbono(resultado.get(0).getNumeroCuentaAbono());
            abono.setTipoCuentaAbono(resultado.get(0).getTipoCuentaAbono());
            abono.setFreg(resultado.get(0).getFreg());
            abono.setEstatus(resultado.get(0).getEstatus());
            abono.setPoseePlanEquipo(poseePlanEquipo);
            if(resultado.get(0).getPagoContado() == null || resultado.get(0).getPagoContado() == 0){
                //no hay pago de contado
                abono.setPagoContado(0);
                abono.setPagoContadoBD(0);
                abono.setFechaPago("");
                abono.setMontoEquipoUSD("0");
                abono.setMontoEquipoBs("0");
                abono.setIvaEquipoBs("0");
                abono.setMontoTotalEquipoBs("0");
            }else{
                //ya hubo pago de contado
                abono.setPagoContado(resultado.get(0).getPagoContado());
                abono.setPagoContadoBD(resultado.get(0).getPagoContado());
                DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                df.setLenient(false);
                String strDate = df.format(resultado.get(0).getFechaPago());
                abono.setFechaPago(strDate);
                abono.setMontoEquipoUSD(resultado.get(0).getMontoEquipoUSD().setScale(2, BigDecimal.ROUND_UP).toString());
                abono.setMontoEquipoBs(resultado.get(0).getMontoEquipoBs().setScale(2, BigDecimal.ROUND_UP).toString());
                abono.setIvaEquipoBs(resultado.get(0).getIvaEquipoBs().setScale(2, BigDecimal.ROUND_UP).toString());
                abono.setMontoTotalEquipoBs(resultado.get(0).getMontoTotalEquipoBs().setScale(2, BigDecimal.ROUND_UP).toString());
            }
                
            
            
            return new ModelAndView("editarTerminalComercio", "editarTerminalComercio", abono);  
            
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 17, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("listadoTerminalesComercio" ,"model" ,modelo);
        }                       
                
    }

    @RequestMapping(value="/eliminarTerminalComercio.htm", method = RequestMethod.GET)
    protected ModelAndView eliminarTerminalComercio(@RequestParam(value = "codTerminal") String codTerminal, HttpServletRequest request, Model modelo) throws ServletException {
        HttpSession misession = (HttpSession) request.getSession();
        Usuario usuarioLogin = ((List<Usuario>) misession.getAttribute("usuario.datos")).get(0);
        Abono abono = abonoDao.getAbonoByTerminal(codTerminal).get(0);

        try{
            bitacora.saveLogs(usuarioLogin.getId(), 2, 17, "Eliminar Terminal ID: "+ abono.getCodigoTerminal() +" Comercio: "+ abono.getCodigoComercio() +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
            abonoDao.deleteAbono(abono);
            modelo.addAttribute("messageError", "Terminal eliminado exitosamente");
        }catch (Exception cve) {
            bitacora.saveLogs(usuarioLogin.getId(), 2, 17, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
        }

        return new ModelAndView(new RedirectView("listadoTerminalesComercio.htm?codigoComercio="+ abono.getCodigoComercio() +"&codBancoCuentaAbono="+ abono.getCodigoBancoCuentaAbono() +"&numCuentaAbono="+ abono.getNumeroCuentaAbono() +""), "model", modelo);
    }

    //Listado de Comercios
    @RequestMapping(value="/listadoComercios.htm")
    public ModelAndView listadoComercios(@RequestParam(value = "categoria", required = false) String categoria,@RequestParam(value = "contrato", required = false) String contrato,@RequestParam(value = "dias", required = false) String dias,@RequestParam(value = "contribuyente", required = false) String contribuyente,HttpServletRequest request, HttpServletResponse response, Model modelo)
            throws ServletException, IOException {
        
        List<Comercio> resultado;
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuarioLogin = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        try{
            
            if(categoria != null && !categoria.isEmpty()){
                resultado = comercioService.getComerciosByCategoria(Integer.parseInt(categoria));
            }else if(contrato != null && !contrato.isEmpty()){
                resultado = comercioService.getComerciosByContrato(Integer.parseInt(contrato));
            }else if(dias != null && !dias.isEmpty()){
                resultado = comercioService.getComerciosByDias(dias);
            }else {
                resultado = comercioService.getComerciosByContribuyente(contribuyente);
            }
        
            modelo.addAttribute("listadoComercios", resultado);
            return new ModelAndView("listadoComercios","model",modelo);
            
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 17, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("listadoComercios" ,"model" ,modelo);
        }        
        
    }
    
    //Listado de Terminales asociados a un Comercio 
    @RequestMapping(value="/listadoTerminalesComercio.htm")
    public ModelAndView listadoTerminalesComercio(@RequestParam(value = "messageError") String messageError,@RequestParam(value = "codigoComercio") String codigoComercio,@RequestParam(value = "codBancoCuentaAbono") String codBancoCuentaAbono,@RequestParam(value = "numCuentaAbono") String numCuentaAbono,HttpServletRequest request, HttpServletResponse response, Model modelo)
            throws ServletException, IOException {
        
        List<Abono> resultado;
        Comercio comercio;
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuarioLogin = (List<Usuario>) misession.getAttribute("usuario.datos");
        Perfil perfil;
        
        if("0".equals(messageError)){
           modelo.addAttribute("messageError", "Registro Actualizado satisfactoriamente"); 
        }
        
        try{
            System.out.println(Integer.parseInt(codigoComercio));
            resultado = comercioService.getAbonosByComercioList(codigoComercio);
            comercio = comercioService.getById(Integer.parseInt(codigoComercio));
            
            modelo.addAttribute("listadoTerminalesComercio", resultado);
            modelo.addAttribute("codigoComercio", codigoComercio);
            modelo.addAttribute("codBancoCuentaAbono", codBancoCuentaAbono);
            modelo.addAttribute("numCuentaAbono", numCuentaAbono);
            modelo.addAttribute("identifica", comercio.getRifComercio().substring(1));
            modelo.addAttribute("tipo", comercio.getRifComercio().substring(0,1));

            perfil = perfilDao.getById(Integer.parseInt(usuarioLogin.get(0).getPerfilId().trim()));
            modelo.addAttribute("editTerminal", Arrays.asList(perfil.getOpciones().split(",")).contains("29") ? Boolean.TRUE : Boolean.FALSE);
            modelo.addAttribute("deleteTerminal", Arrays.asList(perfil.getOpciones().split(",")).contains("30") ? Boolean.TRUE : Boolean.FALSE);

            return new ModelAndView("listadoTerminalesComercio","model",modelo);
            
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 17, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("listadoTerminalesComercio" ,"model" ,modelo);
        }        
        
    }
    //Fin estado inicial
    
    //Accion post del formulario Crear Comercio
    @RequestMapping(value="/crearComercio.htm", method = RequestMethod.POST)
    public ModelAndView crearComercioSubmit(@ModelAttribute("crearComercio") @Valid ComercioForm crearComercio, BindingResult result, Model modelo, HttpServletRequest request) throws ParseException
    {   
        
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuarioLogin = (List<Usuario>) misession.getAttribute("usuario.datos");
        List<Aliado> aliado;
        Map<String, Object> myModel = new HashMap<>();
        Locale español = new Locale("es");
        
        try {
            
            aliado = aliadosService.getAliadoByIdUsuario(usuarioLogin.get(0).getId());
                        
            if (result.hasErrors() || ("1".equals(crearComercio.getEntregoGarantiaFianza()) && (crearComercio.getModalidadGarantia().isEmpty() || crearComercio.getMontoGarantiaFianza().isEmpty() || crearComercio.getFechaGarantiaFianza().isEmpty())) || (aliado.isEmpty() && crearComercio.getCodigoAliado().isEmpty())
                    || ("".equals(crearComercio.getModalidadPos())) || ("".equals(crearComercio.getTipoPos())) || ("1".equals(crearComercio.getPuntoAdicional()) && (crearComercio.getCodigoBancoCuentaAbono2().isEmpty() || crearComercio.getNumeroCuentaAbono2().isEmpty()))) {                
                
                if("".equals(crearComercio.getModalidadPos())){
                    result.rejectValue("modalidadPos","invalid", new Object[]{ crearComercio.getModalidadPos()}, "Debe ingresar la modalidad del POS");
                }
                
                if("".equals(crearComercio.getTipoPos())){
                    result.rejectValue("tipoPos","invalid", new Object[]{ crearComercio.getTipoPos()}, "Debe ingresar el tipo de POS");
                }
                
                if("1".equals(crearComercio.getEntregoGarantiaFianza()) && (crearComercio.getModalidadGarantia().isEmpty() || crearComercio.getMontoGarantiaFianza().isEmpty() || crearComercio.getFechaGarantiaFianza().isEmpty())){
                    if(crearComercio.getModalidadGarantia().isEmpty()){
                        result.rejectValue("modalidadGarantia","invalid", new Object[]{ crearComercio.getModalidadGarantia()}, "Debe ingresar la modalidad de garantía");
                    }

                    if(crearComercio.getMontoGarantiaFianza().isEmpty()){
                        result.rejectValue("montoGarantiaFianza","invalid", new Object[]{ crearComercio.getMontoGarantiaFianza()}, "Debe ingresar monto de la garantía/fianza");
                    }  
                    
                    if(crearComercio.getFechaGarantiaFianza().isEmpty()){
                        result.rejectValue("fechaGarantiaFianza","invalid", new Object[]{ crearComercio.getFechaGarantiaFianza()}, "Debe ingresar la fecha de pago garantía/fianza");
                    }  

                }

                if(aliado.isEmpty()){
                    if(crearComercio.getCodigoAliado().isEmpty()){
                        result.rejectValue("codigoAliado","invalid", new Object[]{ crearComercio.getCodigoAliado()}, "Debe indicar el aliado comercial");
                    }            
                }
                
                if("1".equals(crearComercio.getPuntoAdicional()) && (crearComercio.getCodigoBancoCuentaAbono2().isEmpty() || crearComercio.getNumeroCuentaAbono2().isEmpty())){
                    result.rejectValue("puntoAdicional","invalid", new Object[]{ crearComercio.getPuntoAdicional()}, "Debe indicar las cuentas para el abono");
                }
                return new ModelAndView("crearComercio");
            }                                                                               
                                                        
            Comercio comercio = new Comercio();
            List<ComercioConsulta> comercioSave;
            Contacto contacto = new Contacto();
            int entregoGarantia = 0;
            NumberFormat nf = NumberFormat.getInstance(español);        
            String tipoIdentificacion = crearComercio.getTipoIdentificacion();

            comercio.setCodigoTipoContrato(Integer.parseInt(crearComercio.getCodigoTipoContrato()));                           
            comercio.setFechaInicioContrato(Utilidades.convierteFechaSqlsinHora(crearComercio.getFechaInicioContrato()));
            comercio.setFechaFinContrato(Utilidades.convierteFechaSqlsinHora(crearComercio.getFechaFinContrato()));                        
            comercio.setDiasOperacion(crearComercio.getDiasOperacion());
            comercio.setDescripcionComercio(crearComercio.getDescripcionComercio());
            comercio.setDireccionComercio(crearComercio.getDireccionComercio());
            comercio.setDireccionHabitacion(crearComercio.getDireccionHabitacion());
            comercio.setDireccionPos(crearComercio.getDireccionPos());            
            if("1".equals(crearComercio.getEntregoGarantiaFianza())){
                comercio.setModalidadGarantia(crearComercio.getModalidadGarantia());  
                comercio.setFechaGarantiaFianza(Utilidades.convierteFechaSqlsinHora(crearComercio.getFechaGarantiaFianza()));  
                if(!crearComercio.getMontoGarantiaFianza().isEmpty()){
                    double montoGarantia = nf.parse(crearComercio.getMontoGarantiaFianza()).doubleValue();
                    comercio.setMontoGarantiaFianza(montoGarantia);
                }
                comercio.setEntregoGarantiaFianza(1);
            }  else  {
                comercio.setEntregoGarantiaFianza(0);
            } 
                        
            comercio.setEstatusComercio(4);
            comercio.setCodigoBancoCuentaAbono(crearComercio.getCodigoBancoCuentaAbono());
            comercio.setNumeroCuentaAbono(crearComercio.getNumeroCuentaAbono());            
            if("1".equals(crearComercio.getPuntoAdicional())){ 
                comercio.setPuntoAdicional(1);
                comercio.setCodigoBancoCuentaAbono2(crearComercio.getCodigoBancoCuentaAbono2());
                comercio.setNumeroCuentaAbono2(crearComercio.getNumeroCuentaAbono2());
                comercio.setCodigoBancoCuentaAbono3(crearComercio.getCodigoBancoCuentaAbono3());
                comercio.setNumeroCuentaAbono3(crearComercio.getNumeroCuentaAbono3());
            }else{
                comercio.setPuntoAdicional(0);
                comercio.setCodigoBancoCuentaAbono2("");
                comercio.setNumeroCuentaAbono2("");
                comercio.setCodigoBancoCuentaAbono3("");
                comercio.setNumeroCuentaAbono3("");
            }                                
            comercio.setModalidadPos(crearComercio.getModalidadPos());
            comercio.setTipoPos(crearComercio.getTipoPos());
            /*Si el usuario logueado es un Aliado guarda su id sino guarda el que selecciono cuando 
            crea el comercio*/
            if(aliado.isEmpty()){
                comercio.setCodigoAliado(crearComercio.getCodigoAliado());
            }else{
                comercio.setCodigoAliado(String.valueOf(aliado.get(0).getId()));
            }
            comercio.setPagaIVA(crearComercio.getPagaIVA());
            comercio.setObservacionesComercio(crearComercio.getObservacionesComercio());
            comercio.setRecaudosComercio(crearComercio.getRecaudosComercio());        
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
            comercio.setRifComercio(crearComercio.getTipoIdentificacion() + crearComercio.getRifComercio());
            comercio.setCodigoCategoria(Integer.parseInt(crearComercio.getCodigoCategoria()));
        
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 16, "Crea Comercio: "+ crearComercio.getRifComercio() +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
            comercioService.saveComercio(comercio);
            comercioSave = comercioService.getComercioByRIF(crearComercio.getTipoIdentificacion() + crearComercio.getRifComercio());
            
            if(!comercioSave.isEmpty()){
                
                //Guarda Contacto
                contacto.setCodigoComercio(comercioSave.get(0).getCodigoComercio());
                contacto.setContactoNombres(crearComercio.getContactoNombres());
                contacto.setContactoApellidos(crearComercio.getContactoApellidos());
                contacto.setEmail(crearComercio.getEmail());
                contacto.setTelefonoLocal(crearComercio.getTelefonoLocal());
                contacto.setTelefonoMovil(crearComercio.getTelefonoMovil());
                comercioService.saveContacto(contacto);
                
                //Guarda ComercioXAfiliado
                if (crearComercio.getAfiliados() != null) {                    
                    for (int i = 0; i < crearComercio.getAfiliados().length; i++) {                        
                        comercioService.saveComerciosXafiliado(crearComercio.getAfiliados()[i], comercioSave.get(0).getCodigoComercio());
                    }
                }                 
            }            
            
            return new ModelAndView(new RedirectView("crearComercio.htm?messageError=0"));     
            
        } catch (javax.persistence.PersistenceException cve) {                                                
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 16, "No se ha podido insertar el registro debido al siguiente error: El Comercio "+ crearComercio.getRifComercio() +" ya existe.", Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());
            myModel.put("messageError", "No se ha podido insertar el registro debido al siguiente error: El Comercio "+ crearComercio.getRifComercio() +" ya existe.");
            return new ModelAndView("crearComercio", "model", myModel);          
        } catch (org.hibernate.exception.ConstraintViolationException cve) {             
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 16, "No se ha podido insertar el registro debido al siguiente error: El Comercio "+ crearComercio.getRifComercio() +" ya existe.", Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());
            myModel.put("messageError", "No se ha podido insertar el registro debido al siguiente error: El Comercio "+ crearComercio.getRifComercio() +" ya existe.");
            return new ModelAndView("crearComercio", "model", myModel);             
        } catch (Exception cve) {
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 16, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("crearComercio", "model", modelo);
        }                                                                  
        
    }
    
    
    //Accion post del formulario Consulta Comercio
    @RequestMapping(value="/consultaComercio.htm", method = RequestMethod.POST)
    public ModelAndView consultarComercioSubmit(@ModelAttribute("consultaComercio") @Valid ConsultaComercioForm consultaComercio, BindingResult result, Model modelo, HttpServletRequest request)
    {                
        if (result.hasErrors()) {
            return new ModelAndView("consultaComercio");
        }
        
        if(consultaComercio.getCodigoCategoria().isEmpty() && consultaComercio.getIdentificacion().isEmpty() && consultaComercio.getContrato().isEmpty() && consultaComercio.getDias().isEmpty() && consultaComercio.getContribuyente().isEmpty()){
            result.rejectValue("contribuyente","invalid", new Object[]{ consultaComercio.getContribuyente()}, "Debe ingresar por lo menos un parámetro de consulta");
            return new ModelAndView("consultaComercio");
        }
        
        Map<String, Object> myModel = new HashMap<>();
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuarioLogin = (List<Usuario>) misession.getAttribute("usuario.datos");                        
        
        try{
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 17, "Consulta Comercio: "+ consultaComercio.getTipoIdentificacion() + consultaComercio.getIdentificacion() +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
        
            if(!consultaComercio.getIdentificacion().isEmpty()){

                List<ComercioConsulta> resultado;

                resultado = comercioService.getComercioByRIF(consultaComercio.getTipoIdentificacion() + consultaComercio.getIdentificacion());

                if(resultado.isEmpty()){
                    myModel.put("messageError", "No se encontraron registros con los parametros seleccionados");
                    return new ModelAndView("consultaComercio","model", myModel);
                }else{            
                    return new ModelAndView(new RedirectView("resultadoConsultaComercio.htm?messageError=&tipo="+ consultaComercio.getTipoIdentificacion() +"&identifica="+ consultaComercio.getIdentificacion() +""));
                }
            }else if(!consultaComercio.getCodigoCategoria().isEmpty()){

                List<Comercio> resultado;

                resultado = comercioService.getComerciosByCategoria(Integer.parseInt(consultaComercio.getCodigoCategoria()));

                if(resultado.isEmpty()){
                    myModel.put("messageError", "No se encontraron registros con los parametros seleccionados");
                    return new ModelAndView("consultaComercio","model", myModel);
                }else{            
                    return new ModelAndView(new RedirectView("listadoComercios.htm?categoria="+ consultaComercio.getCodigoCategoria()));
                }
            }else if(!consultaComercio.getContrato().isEmpty()){

                List<Comercio> resultado;

                resultado = comercioService.getComerciosByContrato(Integer.parseInt(consultaComercio.getContrato()));

                if(resultado.isEmpty()){
                    myModel.put("messageError", "No se encontraron registros con los parametros seleccionados");
                    return new ModelAndView("consultaComercio","model", myModel);
                }else{            
                    return new ModelAndView(new RedirectView("listadoComercios.htm?contrato="+ consultaComercio.getContrato()));
                }
            }else if(!consultaComercio.getDias().isEmpty()){

                List<Comercio> resultado;

                resultado = comercioService.getComerciosByDias(consultaComercio.getDias());

                if(resultado.isEmpty()){
                    myModel.put("messageError", "No se encontraron registros con los parametros seleccionados");
                    return new ModelAndView("consultaComercio","model", myModel);
                }else{            
                    return new ModelAndView(new RedirectView("listadoComercios.htm?dias="+ consultaComercio.getDias()));
                }
            }else {

                List<Comercio> resultado;

                resultado = comercioService.getComerciosByContribuyente(consultaComercio.getContribuyente());

                if(resultado.isEmpty()){
                    myModel.put("messageError", "No se encontraron registros con los parametros seleccionados");
                    return new ModelAndView("consultaComercio","model", myModel);
                }else{            
                    return new ModelAndView(new RedirectView("listadoComercios.htm?contribuyente="+ consultaComercio.getContribuyente()));
                }
            }
        
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 17, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("consultaComercio", "model", modelo);
        }                                
        
    }
    
    //Accion post del formulario Resultado Consulta Comercio
    @RequestMapping(value="/resultadoConsultaComercio.htm", method = RequestMethod.POST)
    public ModelAndView resultadoConsultaComercioSubmit(@ModelAttribute("resultadoConsultaComercio") @Valid ComercioForm resultadoConsultaComercio, BindingResult result, Model modelo, HttpServletRequest request) throws ParseException
    {  
        
        List<ComerciosXAfiliado> resultadoComerciosXAfiliado;
        resultadoComerciosXAfiliado = comercioService.getComerciosXAfiliadoByCodComercio(resultadoConsultaComercio.getCodigoComercio());
        
        if (result.hasErrors() || ("1".equals(resultadoConsultaComercio.getEntregoGarantiaFianza()) && (resultadoConsultaComercio.getModalidadGarantia().isEmpty() || resultadoConsultaComercio.getMontoGarantiaFianza().isEmpty() || resultadoConsultaComercio.getFechaGarantiaFianza().isEmpty()))                 
                || (resultadoConsultaComercio.getCodigoAliado().isEmpty())
                || (resultadoConsultaComercio.getComisionComercio().isEmpty())
                ) {
                        
            if("1".equals(resultadoConsultaComercio.getEntregoGarantiaFianza()) && (resultadoConsultaComercio.getModalidadGarantia().isEmpty() || resultadoConsultaComercio.getMontoGarantiaFianza().isEmpty() || resultadoConsultaComercio.getFechaGarantiaFianza().isEmpty())){
                if(resultadoConsultaComercio.getModalidadGarantia().isEmpty()){
                    result.rejectValue("modalidadGarantia","invalid", new Object[]{ resultadoConsultaComercio.getModalidadGarantia()}, "Debe ingresar la modalidad de garantía");
                }

                if(resultadoConsultaComercio.getMontoGarantiaFianza().isEmpty()){
                    result.rejectValue("montoGarantiaFianza","invalid", new Object[]{ resultadoConsultaComercio.getMontoGarantiaFianza()}, "Debe ingresar monto de la garantía/fianza");
                } 
                
                if(resultadoConsultaComercio.getFechaGarantiaFianza().isEmpty()){
                    result.rejectValue("fechaGarantiaFianza","invalid", new Object[]{ resultadoConsultaComercio.getFechaGarantiaFianza()}, "Debe ingresar la fecha de Pago garantía/fianza");
                } 
            }            
            
            if(resultadoConsultaComercio.getCodigoAliado().isEmpty()){
                result.rejectValue("codigoAliado","invalid", new Object[]{ resultadoConsultaComercio.getCodigoAliado()}, "Debe seleccionar el Aliado Comercial");
            }
            
            if(resultadoConsultaComercio.getComisionComercio().isEmpty()){
                result.rejectValue("comisionComercio","invalid", new Object[]{ resultadoConsultaComercio.getComisionComercio()}, "Debe indicar el porcentaje de comisión para el comercio");
            }
            
            modelo.addAttribute("afiliadosComercio", resultadoComerciosXAfiliado);
            return new ModelAndView("resultadoConsultaComercio", "model" ,modelo);
        }                
                
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuarioLogin = (List<Usuario>) misession.getAttribute("usuario.datos");
        Comercio comercio = new Comercio();        
        Contacto contacto = new Contacto();
        ComisionMilPagos comision = new ComisionMilPagos();
        double montoGarantia = 0;        
        NumberFormat nf = NumberFormat.getInstance(español);               
        String tipoIdentificacion = resultadoConsultaComercio.getTipoIdentificacion(), oldValue, newValue;
        
        comercio.setCodigoComercio(resultadoConsultaComercio.getCodigoComercio());
        comercio.setCodigoTipoContrato(Integer.parseInt(resultadoConsultaComercio.getCodigoTipoContrato()));
        comercio.setDescripcionComercio(resultadoConsultaComercio.getDescripcionComercio());
        comercio.setDireccionComercio(resultadoConsultaComercio.getDireccionComercio());
        comercio.setDireccionHabitacion(resultadoConsultaComercio.getDireccionHabitacion());
        comercio.setDireccionPos(resultadoConsultaComercio.getDireccionPos());
        comercio.setFechaInicioContrato(Utilidades.convierteFechaSqlsinHora(resultadoConsultaComercio.getFechaInicioContrato()));
        comercio.setFechaFinContrato(Utilidades.convierteFechaSqlsinHora(resultadoConsultaComercio.getFechaFinContrato()));                                          
        comercio.setExcluirArchivoPago(Integer.parseInt(resultadoConsultaComercio.getExcluirArchivoPago()));
        comercio.setDiasOperacion(resultadoConsultaComercio.getDiasOperacion());        
        if("1".equals(resultadoConsultaComercio.getEntregoGarantiaFianza())){            
            comercio.setModalidadGarantia(resultadoConsultaComercio.getModalidadGarantia());  
            comercio.setFechaGarantiaFianza(Utilidades.convierteFechaSqlsinHora(resultadoConsultaComercio.getFechaGarantiaFianza()));  
            if(!resultadoConsultaComercio.getMontoGarantiaFianza().isEmpty()){
                montoGarantia = nf.parse(resultadoConsultaComercio.getMontoGarantiaFianza()).doubleValue();
                comercio.setMontoGarantiaFianza(montoGarantia);
            }  
            comercio.setEntregoGarantiaFianza(1);
        }else{
            comercio.setEntregoGarantiaFianza(0);
        }                                
        comercio.setEstatusComercio(Integer.parseInt(resultadoConsultaComercio.getEstatusComercio()));
        comercio.setCodigoBancoCuentaAbono(resultadoConsultaComercio.getCodigoBancoCuentaAbono());
        comercio.setNumeroCuentaAbono(resultadoConsultaComercio.getNumeroCuentaAbono());
        if("1".equals(resultadoConsultaComercio.getPuntoAdicional())){
            comercio.setPuntoAdicional(1);
            comercio.setCodigoBancoCuentaAbono2(resultadoConsultaComercio.getCodigoBancoCuentaAbono2());
            comercio.setNumeroCuentaAbono2(resultadoConsultaComercio.getNumeroCuentaAbono2());        
            comercio.setCodigoBancoCuentaAbono3(resultadoConsultaComercio.getCodigoBancoCuentaAbono3());
            comercio.setNumeroCuentaAbono3(resultadoConsultaComercio.getNumeroCuentaAbono3());
        }else{
            comercio.setPuntoAdicional(0);
            comercio.setCodigoBancoCuentaAbono2("");
            comercio.setNumeroCuentaAbono2("");
            comercio.setCodigoBancoCuentaAbono3("");
            comercio.setNumeroCuentaAbono3("");
        }                
        comercio.setModalidadPos(resultadoConsultaComercio.getModalidadPos());
        comercio.setTipoPos(resultadoConsultaComercio.getTipoPos());
        comercio.setCodigoAliado(resultadoConsultaComercio.getCodigoAliado());
        comercio.setPagaIVA(resultadoConsultaComercio.getPagaIVA());
        comercio.setObservacionesComercio(resultadoConsultaComercio.getObservacionesComercio());
        comercio.setRecaudosComercio(resultadoConsultaComercio.getRecaudosComercio());        
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
        comercio.setRifComercio(resultadoConsultaComercio.getTipoIdentificacion() + resultadoConsultaComercio.getRifComercio());
        comercio.setCodigoCategoria(Integer.parseInt(resultadoConsultaComercio.getCodigoCategoria()));
        
        //Guarda Contacto
        contacto.setCodigoContacto(String.valueOf(resultadoConsultaComercio.getCodigoContacto()));
        contacto.setCodigoComercio(resultadoConsultaComercio.getCodigoComercio());
        contacto.setContactoNombres(resultadoConsultaComercio.getContactoNombres());
        contacto.setContactoApellidos(resultadoConsultaComercio.getContactoApellidos());
        contacto.setEmail(resultadoConsultaComercio.getEmail());
        contacto.setTelefonoLocal(resultadoConsultaComercio.getTelefonoLocal());
        contacto.setTelefonoMovil(resultadoConsultaComercio.getTelefonoMovil());
        
        //Guarda ComisionMilPagos
        comision.setCodigoComercio(resultadoConsultaComercio.getCodigoComercio());
        comision.setPorcentajeComision(resultadoConsultaComercio.getComisionComercio());
        
        //Guarda ComercioXAfiliado
        if (resultadoConsultaComercio.getAfiliados() != null) { 
            comercioService.deleteComerciosXafiliado(resultadoConsultaComercio.getCodigoComercio());
            for (int i = 0; i < resultadoConsultaComercio.getAfiliados().length; i++) {                        
                comercioService.saveComerciosXafiliado(resultadoConsultaComercio.getAfiliados()[i], resultadoConsultaComercio.getCodigoComercio());
            }
        }  
        
        try {
            
            oldValue = ToStringBuilder.reflectionToString(comercioService.getById(comercio.getCodigoComercio()));
            comercioService.updateComercio(comercio);
            newValue = ToStringBuilder.reflectionToString(comercioService.getById(comercio.getCodigoComercio()));
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 17, "Editar Comercio: "+ resultadoConsultaComercio.getRifComercio() +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), oldValue, newValue);                                                                              
            comercioService.updateContacto(contacto);
            comercioService.updateComision(comision);
            return new ModelAndView(new RedirectView("resultadoConsultaComercio.htm?messageError=0&tipo="+ resultadoConsultaComercio.getTipoIdentificacion() +"&identifica="+ resultadoConsultaComercio.getRifComercio() +""));            
        } catch (Exception cve) {
            
            bitacora.saveLogs(usuarioLogin.get(0).getId(), 2, 17, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("resultadoConsultaComercio", "model", modelo);
        }                                                               
        
    }
    
    @RequestMapping(value="/guardarTerminalComercio.htm", method = RequestMethod.POST)
    public ModelAndView guardarTerminalComercioSubmit(@ModelAttribute("guardarTerminalComercio") @Valid AbonoForm guardarTerminalComercio, BindingResult result, Model modelo, HttpServletRequest request) throws ParseException
    {
        NumberFormat nf = NumberFormat.getInstance(español);
        double monto= 0; 
        
        //VALIDACIONES Y MENSAJES DE ERROR
        if(guardarTerminalComercio.getPagoContado() == 1){
            //fecha de pago
            if(guardarTerminalComercio.getFechaPago().isEmpty() || "".equals(guardarTerminalComercio.getFechaPago())){
                result.rejectValue("fechaPago","invalid", new Object[]{ guardarTerminalComercio.getFechaPago()}, "Debe ingresar una Fecha de Pago");
            }
            //Monto equipo USD
            if(guardarTerminalComercio.getMontoEquipoUSD().isEmpty() || "".equals(guardarTerminalComercio.getMontoEquipoUSD())){
                result.rejectValue("montoEquipoUSD","invalid", new Object[]{ guardarTerminalComercio.getMontoEquipoUSD()}, "Debe ingresar un Monto de Equipo en Dolares superior a 0");
            }else{
                monto = nf.parse(guardarTerminalComercio.getMontoEquipoUSD()).doubleValue();
                if(monto <= 0){
                    result.rejectValue("montoEquipoUSD","invalid", new Object[]{ guardarTerminalComercio.getMontoEquipoUSD()}, "Debe ingresar un monto de Equipo en Dolares superior a 0");
                }
            }
            //Monto equipo Bolivares
            if(guardarTerminalComercio.getMontoEquipoBs().isEmpty() || "".equals(guardarTerminalComercio.getMontoEquipoBs())){
                result.rejectValue("montoEquipoBs","invalid", new Object[]{ guardarTerminalComercio.getMontoEquipoBs()}, "Debe ingresar un Monto de Equipo en Bolivares superior a 0");
            }else{
                monto = nf.parse(guardarTerminalComercio.getMontoEquipoBs()).doubleValue();
                if(monto <= 0){
                    result.rejectValue("montoEquipoBs","invalid", new Object[]{ guardarTerminalComercio.getMontoEquipoBs()}, "Debe ingresar un monto de Equipo en Bolivares superior a 0");
                }
            }
            if(guardarTerminalComercio.getIvaEquipoBs().isEmpty() || "".equals(guardarTerminalComercio.getIvaEquipoBs())){
                result.rejectValue("ivaEquipoBs","invalid", new Object[]{ guardarTerminalComercio.getIvaEquipoBs()}, "Debe ingresar el Iva");
            }else{
                monto = nf.parse(guardarTerminalComercio.getIvaEquipoBs()).doubleValue();
                if(monto < 0){
                    result.rejectValue("ivaEquipoBs","invalid", new Object[]{ guardarTerminalComercio.getIvaEquipoBs()}, "Debe ingresar el Iva");
                }
            }
            if(guardarTerminalComercio.getMontoTotalEquipoBs().isEmpty() || "".equals(guardarTerminalComercio.getMontoTotalEquipoBs())){
                result.rejectValue("montoTotalEquipoBs","invalid", new Object[]{ guardarTerminalComercio.getMontoTotalEquipoBs()}, "Debe ingresar un monto de Equipo Total en Bolivares superior a 0");
            }else{
                monto = nf.parse(guardarTerminalComercio.getMontoTotalEquipoBs()).doubleValue();
                if(monto <= 0){
                    result.rejectValue("montoTotalEquipoBs","invalid", new Object[]{ guardarTerminalComercio.getMontoTotalEquipoBs()}, "Debe ingresar un monto de Equipo Total en Bolivares superior a 0");
                }
            }
        }
        
        if (result.hasErrors()) {            
            return new ModelAndView("guardarTerminalComercio"); 
        }       
                      
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        
        try{
            Abono abono = new Abono();
            
            abono.setCodigoTerminal(guardarTerminalComercio.getCodigoTerminal());
            abono.setCodigoAfiliado(guardarTerminalComercio.getCodigoAfiliado());
            abono.setCodigoComercio(guardarTerminalComercio.getCodigoComercio());
            abono.setCodigoBancoCuentaAbono(guardarTerminalComercio.getCodigoBancoCuentaAbono());
            abono.setNumeroCuentaAbono(guardarTerminalComercio.getNumeroCuentaAbono());
            abono.setTipoCuentaAbono(guardarTerminalComercio.getTipoCuentaAbono());
            abono.setFreg(guardarTerminalComercio.getFreg());
            abono.setEstatus(guardarTerminalComercio.getEstatus());
            if(guardarTerminalComercio.getPagoContado() == null || guardarTerminalComercio.getPagoContado() == 0){
                //no hay pago de contado
                abono.setPagoContado(0);
                abono.setFechaPago(null);
                abono.setMontoEquipoUSD(null);
                abono.setMontoEquipoBs(null);
                abono.setIvaEquipoBs(null);
                abono.setMontoTotalEquipoBs(null);
            }else{
                //ya hubo pago de contado
                abono.setPagoContado(guardarTerminalComercio.getPagoContado());
                abono.setFechaPago(Utilidades.convierteFechaSqlsinHora(guardarTerminalComercio.getFechaPago()));
                monto = nf.parse(guardarTerminalComercio.getMontoEquipoUSD()).doubleValue();
                abono.setMontoEquipoUSD(new BigDecimal(monto));
                monto = nf.parse(guardarTerminalComercio.getMontoEquipoBs()).doubleValue();
                abono.setMontoEquipoBs(new BigDecimal(monto));
                monto = nf.parse(guardarTerminalComercio.getIvaEquipoBs()).doubleValue();
                abono.setIvaEquipoBs(new BigDecimal(monto));
                monto = nf.parse(guardarTerminalComercio.getMontoTotalEquipoBs()).doubleValue();
                abono.setMontoTotalEquipoBs(new BigDecimal(monto));
            }
            bitacora.saveLogs(usuario.get(0).getId(), 2, 17, "Crea Terminal ID: "+ guardarTerminalComercio.getCodigoTerminal() +" Comercio: "+ guardarTerminalComercio.getCodigoComercio() +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), "", "");
        
            //Guarda los datos ingresados por el usuario
            comercioService.saveAbono(abono);                        

            return new ModelAndView(new RedirectView("listadoTerminalesComercio.htm?messageError=0&codigoComercio="+ guardarTerminalComercio.getCodigoComercio() +"&codBancoCuentaAbono="+ guardarTerminalComercio.getCodigoBancoCuentaAbono() +"&numCuentaAbono="+ guardarTerminalComercio.getNumeroCuentaAbono() +""));  
        
        } catch (javax.persistence.PersistenceException cve) {                                                
            
            bitacora.saveLogs(usuario.get(0).getId(), 2, 17, "No se ha podido insertar el registro debido al siguiente error: El Terminal "+ guardarTerminalComercio.getCodigoTerminal() +" ya existe para este comercio.", Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());
            modelo.addAttribute("messageError", "No se ha podido insertar el registro debido al siguiente error: El Terminal "+ guardarTerminalComercio.getCodigoTerminal() +" ya existe para este comercio.");
            return new ModelAndView("guardarTerminalComercio", "model", modelo);          
        } catch (org.hibernate.exception.ConstraintViolationException cve) {             
            
            bitacora.saveLogs(usuario.get(0).getId(), 2, 17, "No se ha podido insertar el registro debido al siguiente error: El Terminal "+ guardarTerminalComercio.getCodigoTerminal() +" ya existe para este comercio.", Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());
            modelo.addAttribute("messageError", "No se ha podido insertar el registro debido al siguiente error: El Terminal "+ guardarTerminalComercio.getCodigoTerminal() +" ya existe para este comercio.");
            return new ModelAndView("guardarTerminalComercio", "model", modelo);
        } catch (Exception cve) {
            
            bitacora.saveLogs(usuario.get(0).getId(), 2, 17, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("guardarTerminalComercio", "model", modelo);
        }        
        
    }
    
    @RequestMapping(value="/editarTerminalComercio.htm", method = RequestMethod.POST)
    public ModelAndView editarTerminalComercioSubmit(@ModelAttribute("editarTerminalComercio") @Valid AbonoForm editarTerminalComercio, BindingResult result, Model modelo, HttpServletRequest request) throws ParseException
    {
        
        NumberFormat nf = NumberFormat.getInstance(español);
        double monto= 0; 
        double montoEquipo = 0; 
        Abono previusTerminal = new Abono();
        previusTerminal = comercioService.getAbonoByTerminal(editarTerminalComercio.getCodigoTerminal()).get(0);
        List<PlanPago> planesActivos = this.planpago.getPlanPagoByTerminalList(editarTerminalComercio.getCodigoTerminal(), 23);
        List<Planes> planes = this.InformationPlanList();
        Map<String, Integer> terminalPlanActivoTipo = new HashMap<String, Integer>(); 
        Boolean planEquipoActivo = false;
        for(Planes plan: planes){
            //se recorrerá por cada plan en el array de planes activos, para compilar la informacion necesaria para la creacion del plan
            for(PlanPago pp : planesActivos){
                if(Objects.equals(pp.getIdplan(), plan.getId())){
                    terminalPlanActivoTipo.put(plan.getCodtipoplan(), pp.getId());
                }

            }
        }
                     
                
        HttpSession misession= (HttpSession) request.getSession();        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        String newValue, oldValue;
        try{                        
            //VALIDACIONES Y MENSAJES DE ERROR
            
            if( previusTerminal.getPagoContado() == null || previusTerminal.getPagoContado() == 0  ){
                if(editarTerminalComercio.getPagoContado() == 1){
                    //se valida que el terminal no tenga un plan de equipo asignado
                    Integer planPagoIdEquipo = terminalPlanActivoTipo.get("2");
                    if(planPagoIdEquipo != null){
                        result.rejectValue("pagoContado","invalid", new Object[]{ editarTerminalComercio.getPagoContado()}, "El Terminal ya posee activo un plan de equipo, no se peude realizar el pago de contado");
                    }
                    //fecha de pago
                    if(editarTerminalComercio.getFechaPago().isEmpty() || "".equals(editarTerminalComercio.getFechaPago())){
                        result.rejectValue("fechaPago","invalid", new Object[]{ editarTerminalComercio.getFechaPago()}, "Debe ingresar una Fecha de Pago");
                    }
                    //Monto equipo USD
                    if(editarTerminalComercio.getMontoEquipoUSD().isEmpty() || "".equals(editarTerminalComercio.getMontoEquipoUSD())){
                        result.rejectValue("montoEquipoUSD","invalid", new Object[]{ editarTerminalComercio.getMontoEquipoUSD()}, "Debe ingresar un Monto de Equipo en Dolares superior a 0");
                    }else{
                        monto = nf.parse(editarTerminalComercio.getMontoEquipoUSD()).doubleValue();
                        if(monto <= 0){
                            result.rejectValue("montoEquipoUSD","invalid", new Object[]{ editarTerminalComercio.getMontoEquipoUSD()}, "Debe ingresar un monto de Equipo en Dolares superior a 0");
                        }
                    }
                    //Monto equipo Bolivares
                    if(editarTerminalComercio.getMontoEquipoBs().isEmpty() || "".equals(editarTerminalComercio.getMontoEquipoBs())){
                        result.rejectValue("montoEquipoBs","invalid", new Object[]{ editarTerminalComercio.getMontoEquipoBs()}, "Debe ingresar un Monto de Equipo en Bolivares superior a 0");
                    }else{
                        monto = nf.parse(editarTerminalComercio.getMontoEquipoBs()).doubleValue();
                        if(monto <= 0){
                            result.rejectValue("montoEquipoBs","invalid", new Object[]{ editarTerminalComercio.getMontoEquipoBs()}, "Debe ingresar un monto de Equipo en Bolivares superior a 0");
                        }
                    }
                    if(editarTerminalComercio.getIvaEquipoBs().isEmpty() || "".equals(editarTerminalComercio.getIvaEquipoBs())){
                        result.rejectValue("ivaEquipoBs","invalid", new Object[]{ editarTerminalComercio.getIvaEquipoBs()}, "Debe ingresar el Iva");
                    }else{
                        monto = nf.parse(editarTerminalComercio.getIvaEquipoBs()).doubleValue();
                        if(monto < 0){
                            result.rejectValue("ivaEquipoBs","invalid", new Object[]{ editarTerminalComercio.getIvaEquipoBs()}, "Debe ingresar el Iva");
                        }
                    }
                    if(editarTerminalComercio.getMontoTotalEquipoBs().isEmpty() || "".equals(editarTerminalComercio.getMontoTotalEquipoBs())){
                        result.rejectValue("montoTotalEquipoBs","invalid", new Object[]{ editarTerminalComercio.getMontoTotalEquipoBs()}, "Debe ingresar un monto de Equipo Total en Bolivares superior a 0");
                    }else{
                        monto = nf.parse(editarTerminalComercio.getMontoTotalEquipoBs()).doubleValue();
                        if(monto <= 0){
                            result.rejectValue("montoTotalEquipoBs","invalid", new Object[]{ editarTerminalComercio.getMontoTotalEquipoBs()}, "Debe ingresar un monto de Equipo Total en Bolivares superior a 0");
                        }
                    }
                }
            }

            if (result.hasErrors()) {            
                return new ModelAndView("editarTerminalComercio"); 
            }   
            Abono abono = new Abono();
            abono.setId(editarTerminalComercio.getId());
            
            abono.setCodigoTerminal(editarTerminalComercio.getCodigoTerminal());
            abono.setCodigoAfiliado(editarTerminalComercio.getCodigoAfiliado());
            abono.setCodigoComercio(editarTerminalComercio.getCodigoComercio());
            abono.setCodigoBancoCuentaAbono(editarTerminalComercio.getCodigoBancoCuentaAbono());
            abono.setNumeroCuentaAbono(editarTerminalComercio.getNumeroCuentaAbono());
            abono.setTipoCuentaAbono(editarTerminalComercio.getTipoCuentaAbono());
            abono.setFreg(editarTerminalComercio.getFreg());
            abono.setEstatus(editarTerminalComercio.getEstatus());
            if( previusTerminal.getPagoContado() == null || previusTerminal.getPagoContado() == 0 ){
                if(editarTerminalComercio.getPagoContado() == null || editarTerminalComercio.getPagoContado() == 0){
                    //no hay pago de contado
                    abono.setPagoContado(0);
                    abono.setFechaPago(null);
                    abono.setMontoEquipoUSD(null);
                    abono.setMontoEquipoBs(null);
                    abono.setIvaEquipoBs(null);
                    abono.setMontoTotalEquipoBs(null);
                }else{
                    //ya hubo pago de contado
                    abono.setPagoContado(editarTerminalComercio.getPagoContado());
                    abono.setFechaPago(Utilidades.convierteFechaSqlsinHora(editarTerminalComercio.getFechaPago()));
                    monto = nf.parse(editarTerminalComercio.getMontoEquipoUSD()).doubleValue();
                    abono.setMontoEquipoUSD(new BigDecimal(monto));
                    monto = nf.parse(editarTerminalComercio.getMontoEquipoBs()).doubleValue();
                    abono.setMontoEquipoBs(new BigDecimal(monto));
                    monto = nf.parse(editarTerminalComercio.getIvaEquipoBs()).doubleValue();
                    abono.setIvaEquipoBs(new BigDecimal(monto));
                    monto = nf.parse(editarTerminalComercio.getMontoTotalEquipoBs()).doubleValue();
                    abono.setMontoTotalEquipoBs(new BigDecimal(monto));
                }
            }
            //Guarda los datos ingresados por el usuario
            oldValue = comercioService.getAbonoByTerminal(editarTerminalComercio.getCodigoTerminal()).get(0).getCodigoTerminal();
            comercioService.updateAbono(abono);
            newValue = editarTerminalComercio.getCodigoTerminal();
            bitacora.saveLogs(usuario.get(0).getId(), 2, 17, "Actualiza Terminal ID: "+ editarTerminalComercio.getCodigoTerminal() +" Comercio: "+ editarTerminalComercio.getCodigoComercio() +"", Utilidades.getFechaActualSql(), 0, request.getRemoteAddr(), oldValue, newValue);            

            return new ModelAndView(new RedirectView("listadoTerminalesComercio.htm?messageError=0&codigoComercio="+ editarTerminalComercio.getCodigoComercio() +"&codBancoCuentaAbono="+ editarTerminalComercio.getCodigoBancoCuentaAbono() +"&numCuentaAbono="+ editarTerminalComercio.getNumeroCuentaAbono() +""));  
        
        }catch (Exception cve) {
            
            bitacora.saveLogs(usuario.get(0).getId(), 2, 17, "No se ha podido realizar la operacion debido al siguiente error: "+ cve.getMessage(), Utilidades.getFechaActualSql(), 1, request.getRemoteAddr(), "", "");
            logger.error(cve.getMessage());            
            modelo.addAttribute("messageError", "Ocurrio un error al procesar la operación. Por favor consulte con el Administrador.");
            return new ModelAndView("editarTerminalComercio", "model", modelo);
        }        
        
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
    
    @ModelAttribute("diasList")
    public Map<String,String> diasList() {
   
        Map<String,String> dias = new LinkedHashMap<String,String>();
        dias.put("Lun", "Lun");
        dias.put("Mar", "Mar");
        dias.put("Mie", "Mie");
        dias.put("Jue", "Jue");
        dias.put("Vie", "Vie");
        dias.put("Sab", "Sab");
        dias.put("Dom", "Dom");
        
        return dias;
    }
    
    @ModelAttribute("codigoTelHabitacionList")
    public List codigoTelHabitacionList() {
        
        List<CodigoArea> codigoTelHabitacionList = null;
        
        try{
            codigoTelHabitacionList = codigosDAO.getCodigosAreaList(1);
        }catch (Exception cve) {                       
            logger.error(cve.getMessage());                       
        }        
                		         
        return codigoTelHabitacionList;
    }
    
    @ModelAttribute("codigoCelularList")
    public List codigoCelularList() {
        
        List<CodigoArea> codigoCelularList = null;
        
        try{
            codigoCelularList = codigosDAO.getCodigosAreaList(2);
        }catch (Exception cve) {                       
            logger.error(cve.getMessage());                       
        } 
        
        return codigoCelularList;
    }        
    
    @ModelAttribute("estatusList")
    public List estatusList() {

        List<Estatus> estatusList = null;

        try {
            estatusList = estatusDAO.getEstatusByModulo("co");
        } catch (Exception cve) {
            logger.error(cve.getMessage());
        }

        return estatusList;
    }
    
    @ModelAttribute("tipoContratolList")
    public List tipoContratolList() {
        
        List<TipoContrato> tipoContratolList = null;
        
        try{
            tipoContratolList = comercioService.getTipoContratoList();
        }catch (Exception cve) {                       
            logger.error(cve.getMessage());                       
        } 
                        		         
        return tipoContratolList;
    }
    
    @ModelAttribute("actividadComercialList")
    public List actividadComercialList() {
        
        List<CategoriaComercio> actividadComercialList = null;
        
        try{
            actividadComercialList = comercioService.getCategoriaComercioList();
        }catch (Exception cve) {                       
            logger.error(cve.getMessage());                       
        }        
                		         
        return actividadComercialList;
    }
    
    @ModelAttribute("modalidadGarantialList")
    public List modalidadGarantialList() {
        
        List<TipoGarantiaComercio> modalidadGarantialList = null;
        
        try{
            modalidadGarantialList = comercioService.getTipoGarantiaList();
        }catch (Exception cve) {                       
            logger.error(cve.getMessage());                       
        } 
                        		         
        return modalidadGarantialList;
    }
    
    @ModelAttribute("modalidadPosList")
    public List modalidadPosList() {
        
        List<ModalidadPos> modalidadPosList = null;
        
        try{
            modalidadPosList = mantenimientoAdmin.getModalidadPosList();
        }catch (Exception cve) {                       
            logger.error(cve.getMessage());                       
        } 
                        		         
        return modalidadPosList;
    }
    
    @ModelAttribute("tipoPosList")
    public List tipoPosList() {
        
        List<TipoPos> tipoPosList = null;
        
        try{
            tipoPosList = mantenimientoAdmin.getTipoPosList();
        }catch (Exception cve) {                       
            logger.error(cve.getMessage());                       
        } 
                        		         
        return tipoPosList;
    }
    
    @ModelAttribute("bancoList")
    public List bancoList() {
        
        List<Banco> bancoList = null;
        
        try{
            bancoList = mantenimientoAdmin.getBancoList();
        }catch (Exception cve) {                       
            logger.error(cve.getMessage());                       
        } 
                        		         
        return bancoList;
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
    
    //Carga los valores de los checkboxes
    @ModelAttribute("recaudosList")
    protected List<Recaudo> recaudosList(HttpServletRequest request) throws Exception {
	
	List<Recaudo> recaudosList = null;
        
        try{
            recaudosList = comercioService.getRecaudosList("co");
        }catch (Exception cve) {                       
            logger.error(cve.getMessage());                       
        } 
	                      
	return recaudosList;
    }
    
    
    @ModelAttribute("TermEstatusList")
    public List TermEstatusList() {

        List<Estatus> TermEstatusList = null;

        try {
            TermEstatusList = estatusDAO.getEstatusByModulo("pla");
        } catch (Exception cve) {
            logger.error(cve.getMessage());
        }

        return TermEstatusList;
    }
    
    @ModelAttribute("TermPagoContadoList")
    public Map<Integer,String> termPagoContadoList() {
   
        Map<Integer,String> pagoContadoOptions = new LinkedHashMap<Integer,String>();
        pagoContadoOptions.put(0, "No");
        pagoContadoOptions.put(1, "Si");
        
        return pagoContadoOptions;
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
}

  
    
