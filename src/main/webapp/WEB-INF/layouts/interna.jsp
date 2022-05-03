<%-- 
    Document   : basic-theme
    Created on : 12/08/2016
    Author     : mggy@sistemasemsys.com
--%>
<%@page import="java.io.InputStream"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.util.Properties"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@page import="com.tranred.milpagosapp.domain.Usuario"%>
<%@page import="com.tranred.milpagosapp.domain.Perfil"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"

   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jsp/include.jsp" %>
<html>

<head>
    <title><decorator:title default="1000Pagos" /></title>
    <meta http-equiv="content-type" content="text/html; charsetUTF-8" />
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/img/logo.ico" />
    <style type="text/css">
            <%@ include file="/WEB-INF/css/style.css" %>
            <%@ include file="/WEB-INF/css/menu.css" %>
            <%@ include file="/WEB-INF/css/jquery.datetimepicker.css" %>
            <%@ include file="/WEB-INF/css/fullcalendar.css" %>              
            <%@ include file="/WEB-INF/css/example-styles.css" %>
            <%@ include file="/WEB-INF/css/alertify.core.css" %>
            <%@ include file="/WEB-INF/css/alertify.default.css" %>
    </style>
</head>
<%                     
        String perfil = "";
        String[] resultado = new String[50];                
        HttpSession misession = (HttpSession) request.getSession(true);        
        List<Usuario> usuario = (List<Usuario>) misession.getAttribute("usuario.datos");
        Properties propiedades = new Properties();                
        
        /**Cargamos el archivo desde la ruta especificada*/
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("app.properties");       	
        propiedades.load(in);
                
        /**Obtenemos los parametros definidos en el archivo*/
        String nombre = propiedades.getProperty("jdbc.username");                
        String password = propiedades.getProperty("jdbc.password");
        String url = propiedades.getProperty("jdbc.url");        
        
        // Conexion con bd
        Class.forName("net.sourceforge.jtds.jdbc.Driver");
        Connection conexion = DriverManager.getConnection(url, nombre , password);
        if (!conexion.isClosed())
        {
            // La consulta
             Statement st = conexion.createStatement();
             ResultSet rs = st.executeQuery("select * from Perfiles where id = " + usuario.get(0).getPerfilId() + "");
             
             while (rs.next())
              {                 
                 perfil = rs.getObject("opciones").toString();
              }
        }                                             
        
        for(int i=1;i<=49;i++){
            if (Arrays.asList(perfil.split(",")).contains(Integer.toString(i))) {                                
                resultado[i] = "1";
            }else{
                resultado[i] = "0";
            }
        }
        
        misession.setAttribute("usuario.opciones",resultado);
%>
<body>
<div class="super-container">
    <div id="header">       
        <div id="top">            
            <%-- <div id="logo">&nbsp;&nbsp;&nbsp;&nbsp;<img src="${pageContext.request.contextPath}/resources/img/logo.png" height="100"/></div> --%>
            <div id="slogan"><img src="${pageContext.request.contextPath}/resources/img/slogan.png" height="40"/></div>
            <div id="bienvenida">                
                BIENVENIDO  <%=usuario.get(0).getNombre()%>                        
            </div>
            <div id="salir">
                <a id="salir" class="button_yellow" href="salir.htm">
                Salir
                <div class="ico-salir"></div>
            </a>
            </div>
        </div>        
    </div><!-- DIV DIVTOP -->    
    <div id="divContainer" class="container">
        <hr />

        <div id="divMain">
            <header>
            <div id="menu">
                <ul class="nav">                  
                    <li class="nivel1"><a href="#" class="nivel1">Accesos<span class="flecha">»</span></a>
                    <!--[if lte IE 6]><a href="#" class="nivel1ie">Accesos<span class="flecha">»</span><table class="falsa"><tr><td><![endif]-->
                        <ul class="nivel2">
                            <% if(!"2".equals(usuario.get(0).getCambio())){ %>
                                <% if(resultado[1] == "1" || resultado[2] == "1"){ %>
                                    <li class="nivel2"><a class="nivel2" href="#">Perfiles<span class="flecha">»</span></a>
                                            <!--[if lte IE 6]><a href="#" class="nivel2ie">Perfiles<span class="flecha">»</span><table class="falsa"><tr><td><![endif]-->
                                            <ul class="nivel3">
                                                    <% if(resultado[1] == "1"){ %>
                                                            <li><a class="primera" href="crearPerfil.htm">Crear Perfil<span class="flecha">»</span></a></li>
                                                    <% } %>
                                                    <% if(resultado[2] == "1"){ %>
                                                            <li><a href="listadoPerfiles.htm">Consulta Perfiles<span class="flecha">»</span></a></li>
                                                    <% } %>			
                                            </ul>
                                            <!--[if lte IE 6]></td></tr></table></a><![endif]-->
                                    </li>
                                <% } %>
                                <% if(resultado[3] == "1" || resultado[4] == "1"){ %>
                                    <li class="nivel2"><a class="nivel2" href="#">Usuarios<span class="flecha">»</span></a>
                                            <!--[if lte IE 6]><a href="#" class="nivel2ie">Usuarios<span class="flecha">»</span><table class="falsa"><tr><td><![endif]-->
                                            <ul class="nivel3">
                                                    <% if(resultado[3] == "1"){ %>
                                                            <li><a class="primera" href="crearUsuario.htm">Crear Usuario<span class="flecha">»</span></a></li>
                                                    <% } %>
                                                    <% if(resultado[4] == "1"){ %>
                                                            <li><a href="listadoUsuarios.htm">Consulta Usuarios<span class="flecha">»</span></a></li>
                                                    <% } %>				
                                            </ul>
                                            <!--[if lte IE 6]></td></tr></table></a><![endif]-->
                                    </li>
                                <% } %>                                
                                <% if(resultado[22] == "1"){ %>
                                    <li class="nivel2"><a class="nivel2" href="#">Reportes<span class="flecha">»</span></a>
                                            <!--[if lte IE 6]><a href="#" class="nivel2ie">Reportes<span class="flecha">»</span><table class="falsa"><tr><td><![endif]-->
                                            <ul class="nivel3">
                                                <li><a href="consultaUsuarios.htm">Usuarios por Perfil<span class="flecha">»</span></a></li>
                                            </ul>
                                            <!--[if lte IE 6]></td></tr></table></a><![endif]-->
                                    </li>                                    
                                <% } %>
                            <% } %>
                            <% if(resultado[8] == "1"){ %>                                          
                                    <li><a href="cambioClave.htm?id=">Cambio Contraseña<span class="flecha">»</span></a></li>                                            
                            <% } %>
                        </ul>
                    <!--[if lte IE 6]></td></tr></table></a><![endif]-->
                    </li>
                    <% if(!"2".equals(usuario.get(0).getCambio())){ %>
                      <% if(resultado[5] == "1" || resultado[26] == "1"){ %>                                                                                               
                        <li class="nivel1"><a href="#" class="nivel1">Bitacora<span class="flecha">»</span></a>
                        <!--[if lte IE 6]><a href="#" class="nivel1ie">Bitacora<span class="flecha">»</span><table class="falsa"><tr><td><![endif]-->
                            <ul class="nivel2">
                                    <% if(resultado[5] == "1"){ %>
                                        <li><a href="consultaBitacora.htm">Bitacora MilPagos<span class="flecha">»</span></a></li>
                                    <% } %>
                                    <% if(resultado[26] == "1"){ %>    
                                        <li><a href="consultaBitacoraWeb.htm">Bitacora Sitio Web<span class="flecha">»</span></a></li>                                    
                                    <% } %>
                            </ul>
                        <!--[if lte IE 6]></td></tr></table></a><![endif]-->
                        </li>
                      <% } %>      
                      <% if(resultado[6] == "1"){ %>
                        <li class="nivel1"><a href="#" class="nivel1">Mantenimiento<span class="flecha">»</span></a>
                        <!--[if lte IE 6]><a href="#" class="nivel1ie">Mantenimiento<span class="flecha">»</span><table class="falsa"><tr><td><![endif]-->
                            <ul class="nivel2">
                                    <li><a href="#">Afiliados<span class="flecha">»</span></a>
                                    <!--[if lte IE 6]><a href="#" class="nivel2ie">Afiliados<span class="flecha">»</span><table class="falsa"><tr><td><![endif]-->    
                                        <ul class="nivel3">                                            
                                            <li><a class="primera" href="crearAfiliado.htm">Crear Afiliado<span class="flecha">»</span></a></li>                                            
                                            <li><a href="listadoAfiliados.htm">Consulta Afiliados<span class="flecha">»</span></a></li>                                            
                                        </ul>
                                    <!--[if lte IE 6]></td></tr></table></a><![endif]-->  
                                    <li><a href="#">Bancos<span class="flecha">»</span></a>
                                    <!--[if lte IE 6]><a href="#" class="nivel2ie">Bancos<span class="flecha">»</span><table class="falsa"><tr><td><![endif]-->    
                                        <ul class="nivel3">                                            
                                            <li><a class="primera" href="crearBanco.htm">Crear Banco<span class="flecha">»</span></a></li>                                            
                                            <li><a href="listadoBancos.htm">Consulta Bancos<span class="flecha">»</span></a></li>                                            
                                        </ul>
                                    <!--[if lte IE 6]></td></tr></table></a><![endif]-->                                    
                                    </li>                                                                
                                    <li><a href="#">Mensajes<span class="flecha">»</span></a>
                                    <!--[if lte IE 6]><a href="#" class="nivel2ie">Mensajes<span class="flecha">»</span><table class="falsa"><tr><td><![endif]-->    
                                        <ul class="nivel3">                                                                                        
                                            <li><a href="listadoMensajes.htm">Consulta Mensajes<span class="flecha">»</span></a></li>                                            
                                        </ul>
                                    <!--[if lte IE 6]></td></tr></table></a><![endif]-->                                    
                                    </li>                                
                                    <li><a href="#">Modalidad POS<span class="flecha">»</span></a>
                                        <!--[if lte IE 6]><a href="#" class="nivel2ie">Modalidad POS<span class="flecha">»</span><table class="falsa"><tr><td><![endif]-->    
                                        <ul class="nivel3">                                            
                                            <li><a class="primera" href="crearModalidadPos.htm">Crear Modalidad POS<span class="flecha">»</span></a></li>                                            
                                            <li><a href="listadoModalidadPos.htm">Consulta Modalidad POS<span class="flecha">»</span></a></li>                                            
                                        </ul>
                                        <!--[if lte IE 6]></td></tr></table></a><![endif]-->                                    
                                    </li> 
                                     <li><a href="#">Tipo Pos<span class="flecha">»</span></a>
                                        <!--[if lte IE 6]><a href="#" class="nivel2ie">Tipo Pos<span class="flecha">»</span><table class="falsa"><tr><td><![endif]-->    
                                        <ul class="nivel3">                                            
                                            <li><a class="primera" href="crearTipoPos.htm">Crear Tipo Pos<span class="flecha">»</span></a></li>                                            
                                            <li><a href="listadoTipoPos.htm">Consulta Tipo Pos<span class="flecha">»</span></a></li>                                            
                                        </ul>
                                    <!--[if lte IE 6]></td></tr></table></a><![endif]-->
                                    </li>                             
                                    <li><a href="listadoZonasAtencion.htm">Zonas Atención<span class="flecha">»</span></a></li>
                            </ul>
                        <!--[if lte IE 6]></td></tr></table></a><![endif]-->
                        </li>
                      <% } %>
                      <% if(resultado[7] == "1"){ %>
                        <li class="nivel1"><a href="listadoParametros.htm" class="nivel1">Parámetros Generales<span class="flecha">»</span></a>
                      <% } %>
                      <% if(resultado[11] == "1" || resultado[12] == "1" || resultado[13] == "1"){ %>
                        <li class="nivel1"><a href="#" class="nivel1">Aliado Comercial<span class="flecha">»</span></a>
                        <!--[if lte IE 6]><a href="#" class="nivel1ie">Aliado Comercial<span class="flecha">»</span><table class="falsa"><tr><td><![endif]-->
                            <ul class="nivel2">
                                    <li><a href="crearAliado.htm?messageError=">Selección<span class="flecha">»</span></a></li>
                                    <li><a href="consultaAliado.htm">Consulta y Actualización<span class="flecha">»</span></a></li>
                                    <li><a href="mantenimiento.htm">Estadísticas<span class="flecha">»</span></a></li>
                            </ul>
                        <!--[if lte IE 6]></td></tr></table></a><![endif]-->
                        </li>
                      <% } %>
                      <% if(resultado[15] == "1"){ %>
                        <li class="nivel1"><a href="#" class="nivel1">Agenda<span class="flecha">»</span></a>
                        <!--[if lte IE 6]><a href="#" class="nivel1ie">Agenda<span class="flecha">»</span><table class="falsa"><tr><td><![endif]-->
                            <ul class="nivel2">     
                                    <li><a href="consultaAgenda.htm">Consulta y Actualización<span class="flecha">»</span></a></li>
                                    <li><a href="consultaVisitasAliado.htm">Consulta Visitas Programadas<span class="flecha">»</span></a></li>
                            </ul>
                        <!--[if lte IE 6]></td></tr></table></a><![endif]-->
                        </li>
                      <% } %>
                      <% if(resultado[16] == "1" || resultado[17] == "1" || resultado[18] == "1"){ %>
                        <li class="nivel1"><a href="#" class="nivel1">Comercios<span class="flecha">»</span></a>
                        <!--[if lte IE 6]><a href="#" class="nivel1ie">Comercios<span class="flecha">»</span><table class="falsa"><tr><td><![endif]-->
                            <ul class="nivel2">
                                    <% if(resultado[16] == "1"){ %>    
                                        <li><a href="crearComercio.htm?messageError=">Crear<span class="flecha">»</span></a></li>
                                    <% } %>
                                    <% if(resultado[17] == "1"){ %>     
                                        <li><a href="consultaComercio.htm">Consulta y Actualización<span class="flecha">»</span></a></li>
                                    <% } %>
                                    <% if(resultado[18] == "1"){ %> 
                                        <li><a href="mantenimiento.htm">Gestión de Requerimientos<span class="flecha">»</span></a></li>
                                    <% } %>
                                    <% if(resultado[37] == "1"){ %>
                                        <li><a href="generarReportes.htm">Generar Reportes<span class="flecha">»</span></a></li>
                                    <% } %>
                            </ul>
                        <!--[if lte IE 6]></td></tr></table></a><![endif]-->
                        </li>
                      <% } %>
                      <% if(resultado[25] == "1" || resultado[27] == "1"){ %>
                        <li class="nivel1"><a href="#" class="nivel1">Estados de Cuenta<span class="flecha">»</span></a>
                        <!--[if lte IE 6]><a href="#" class="nivel1ie">Estados de Cuenta<span class="flecha">»</span><table class="falsa"><tr><td><![endif]-->                    
                            <ul class="nivel2">
                                    <% if(resultado[25] == "1"){ %>
                                        <li><a href="generarEdoCuentaComercios.htm">Generar Estados de Cuenta<span class="flecha">»</span></a></li>
                                    <% } %>
                                    <% if(resultado[27] == "1"){ %>    
                                    <li><a href="consultaUsuarioWeb.htm">Administrar Usuarios<span class="flecha">»</span></a></li>                                    
                                    <% } %>  
                            </ul>
                        <!--[if lte IE 6]></td></tr></table></a><![endif]-->
                        </li>
                      <% } %>                                             
                      <% if(resultado[28] == "1"){ %>
                        <li class="nivel1"><a href="#" class="nivel1">Facturación<span class="flecha">»</span></a>
                        <!--[if lte IE 6]><a href="#" class="nivel1ie">Facturación<span class="flecha">»</span><table class="falsa"><tr><td><![endif]-->                    
                            <ul class="nivel2">
                                    <% if(resultado[28] == "1"){ %>
                                        <li><a href="generarFacturacionComercios.htm">Generar Facturación<span class="flecha">»</span></a></li>
                                    <% } %>                                      
                            </ul>
                        <!--[if lte IE 6]></td></tr></table></a><![endif]-->
                        </li>
                      <% } %>     
                      <% if(resultado[38] == "1" || resultado[39] == "1" || resultado[40] == "1"){ %>
                        <li class="nivel1"><a href="#" class="nivel1">Planes<span class="flecha">»</span></a>
                        <!--[if lte IE 6]><a href="#" class="nivel1ie">Facturación<span class="flecha">»</span><table class="falsa"><tr><td><![endif]-->                    
                            <ul class="nivel2">
                                    <% if(resultado[38] == "1"){ %>
                                        <% if(resultado[40] == "1"){ %>
                                            <li><a class="primera" href="crearPlan.htm">Crear Plan<span class="flecha">»</span></a></li>                                            
                                        <% } %> 
                                        <li><a href="listadoPlanes.htm">Consulta Plan<span class="flecha">»</span></a></li>                                            
                                    <% } %>  
                                    <% if(resultado[39] == "1"){ %>
                                        <li><a href="generarConsultaPlanes.htm">Reportes<span class="flecha">»</span></a></li>                                            
                                    <% } %> 
                            </ul>
                        <!--[if lte IE 6]></td></tr></table></a><![endif]-->
                        </li>
                      <% } %> 
                      <% if(resultado[37] == "1"){ %>
                        <li class="nivel1"><a href="#" class="nivel1">Cobranza<span class="flecha">»</span></a>
                        <!--[if lte IE 6]><a href="#" class="nivel1ie">Facturación<span class="flecha">»</span><table class="falsa"><tr><td><![endif]-->                    
                            <ul class="nivel2">
                                    <% if(resultado[37] == "1"){ %>
                                        <li><a href="generarCobranzasComercios.htm">Generar Cobranzas<span class="flecha">»</span></a></li>
                                    <% } %>                                      
                            </ul>
                        <!--[if lte IE 6]></td></tr></table></a><![endif]-->
                        </li>
                      <% } %>   
                      <% if(resultado[19] == "1"){ %>
                        <li class="nivel1"><a href="#" class="nivel1">Operaciones<span class="flecha">»</span></a>
                            <!--[if lte IE 6]><a href="#" class="nivel1ie">Operaciones<span class="flecha">»</span><table class="falsa"><tr><td><![endif]-->
                            <ul class="nivel2">
                                    <li><a href="#">Reclamos<span class="flecha">»</span></a>
                                    <!--[if lte IE 6]><a href="#" class="nivel2ie">Reclamos<span class="flecha">»</span><table class="falsa"><tr><td><![endif]-->    
                                        <ul class="nivel3">                                            
                                            <li><a class="primera" href="crearReclamo.htm?messageError=">Crear Reclamo<span class="flecha">»</span></a></li>                                            
                                            <li><a href="consultaReclamos.htm?messageError=">Consulta Reclamos<span class="flecha">»</span></a></li>                                            
                                        </ul>
                                    <!--[if lte IE 6]></td></tr></table></a><![endif]-->
                                    </li>                                
                            </ul>                            
                        <!--[if lte IE 6]></td></tr></table></a><![endif]-->
                        </li>
                      <% } %>
                      <% if(resultado[20] == "1" || resultado[21] == "1" || resultado[24] == "1"){ %>
                        <li class="nivel1"><a href="#" class="nivel1">Liquidación<span class="flecha">»</span></a>
                        <!--[if lte IE 6]><a href="#" class="nivel1ie">Liquidación<span class="flecha">»</span><table class="falsa"><tr><td><![endif]-->
                            <ul class="nivel2">
                                    <% if(resultado[20] == "1"){ %>
                                        <li><a href="lotePagos.htm">Generar Lote<span class="flecha">»</span></a></li>
                                    <% } %>
                                    <% if(resultado[21] == "1"){ %>
                                        <li><a href="consultaLotes.htm">Consultar Lotes<span class="flecha">»</span></a></li>
                                    <% } %>
                                    <% if(resultado[24] == "1"){ %>
                                        <li><a href="listadoComerciosExcluidos.htm">Listado Comercios Excluidos<span class="flecha">»</span></a></li>
                                    <% } %>
                            </ul>
                        <!--[if lte IE 6]></td></tr></table></a><![endif]-->
                        </li>
                      <% } %>
                      <% if(resultado[41] == "1"){ %>
                        <li class="nivel1"><a href="#" class="nivel1">Pago Manual de Cuotas<span class="flecha">»</span></a>
                        <!--[if lte IE 6]><a href="#" class="nivel1ie">Liquidación<span class="flecha">»</span><table class="falsa"><tr><td><![endif]-->
                            <ul class="nivel2">
                                    <% if(resultado[41] == "1"){ %>
                                        <li><a href="cargarCuotasManual.htm">Carga Cuotas Equipo Manualmente<span class="flecha">»</span></a></li>
                                    <% } %>
                            </ul>
                        <!--[if lte IE 6]></td></tr></table></a><![endif]-->
                        </li>
                      <% } %>
                      
                    <% } %>
                </ul>
            </div>
            </header>           
            <br>
            <br>    
            <div id="divContent" class="content">

                <decorator:body />

            </div><!-- DIV DIVCONTENT -->

        </div><!-- DIV DIVMAIN -->               

    </div><!-- DIV DIVCONAINER -->
    <br>
    <div id="footer">                
        <div id="copyright"><span>&nbsp;&nbsp;&nbsp;&nbsp;Todos los derechos reservados. Inversiones Gross C.A. RIF J-00337298-6</span></div>
        <div id="link"><span>Creado por EMSYS&nbsp;&nbsp;&nbsp;&nbsp;</span></div>        
    </div><!-- DIV DIVFOOTER --> 
</div>
</body>
</html>