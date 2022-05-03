<%-- 
    Document   : login
    Created on : 11/08/2016
    Author     : mggy@sistemasemsys.com
--%>
<%@ include file="/WEB-INF/views/jsp/include.jsp" %>
<html>
<head>
  <meta name="decorator" content="externa"/>
</head>
<body>
<br>
<br>
<div class="shadows-ie-135 shadows-ie-315" id="box-login">
    <div class="t_shadow"></div>
    <div class="r_shadow"></div>
    <div class="b_shadow"></div>
    <div class="l_shadow"></div>
    <div class="lt_shadow"></div>
    <div class="rt_shadow"></div>
    <div class="rb_shadow"></div>
    <div class="lb_shadow"></div>
    
    <div>    
        <div>
            <div id="header-login" style="text-align: center;">
                <h1 class="letra_login">Bienvenido a 1000Pagos, un Punto Mil Oportunidades</h1>
            </div>
        </div>
        <div>
            <div>		
		<div class="line_title_black"></div>
            </div>
            <div id="box-form">
                <form:form method="post" name="usuario" commandName="usuario" autocomplete="off">
                <div class="contenedor_login">
                  <table class="formulario">
                    <tr>
                      <td class="etiqueta_black">Usuario:</td>
                        <td>
                          <form:input path="login" cssClass="textbox-login"/>
                        </td>                       
                    </tr>
                    <tr>
                        <td></td>
                        <td>
                            <form:errors path="login" cssClass="error-message"/>
                        </td>
                    </tr>
                    <tr>
                      <td class="etiqueta_black">Contraseña:</td>
                        <td>
                            <form:password path="contrasena" cssClass="textbox-login"/>
                        </td>                        
                    </tr>
                    <tr>
                        <td></td>
                        <td>
                            <form:errors path="contrasena" cssClass="error-message"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <label class="error-message" style="align-content: center">${model.messageError}</label>
                        </td>
                    </tr> 
                    <tr>
                        <td class="botonera" colspan="3">                        
                            <input type="button" value="Aceptar" class="button" onclick="javascript: document.getElementById('usuario').submit()">
                        </td>
                    </tr>
                  </table>                                   
                </div>  
                </form:form>
            </div>    
        </div>    
    </div>
</div>        
</body>
</html>