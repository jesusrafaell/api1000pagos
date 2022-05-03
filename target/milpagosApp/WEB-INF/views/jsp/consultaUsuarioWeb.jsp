<%-- 
    Document   : consultaUsuarioWeb
    Created on : 12-jun-2017
    Author     : mggy@sistemasemsys.com
--%>

<%@ include file="/WEB-INF/views/jsp/include.jsp" %>
<html>
<head>    
</head>
<body>
    <br>
    <div class="row">    
        <div id="header-form">
            <h1>Administrar Usuarios Sitio Web</h1>
        </div>
        <label class="error-message" style="align-content: center">${messageError}</label>
        <div id="box-form">
            <form:form method="post" autocomplete="off" commandName="consultaUsuarioWeb">
                <div class="contenedor_formulario">
                  <table class="formulario">                    
                    <tr>
                        <td class="etiqueta_black">Identificación:</td>
                        <td>
                          <form:select path="tipoIdentificacion" cssClass="select" multiple="false">                                      
                            <form:options items="${tipoIdentificacionList}" />
                          </form:select>
                          <form:input path="identificacion" cssClass="textbox" cssStyle="width: 80px" maxlength="9"/>                                 
                        </td>                                
                    </tr>
                    <tr>
                        <td></td>
                        <td>
                            <form:errors path="tipoIdentificacion" cssClass="error-message"/>
                            <form:errors path="identificacion" cssClass="error-message"/>
                        </td>
                    </tr>                
                    <tr>
                          <td class="botonera" colspan="2">                            
                            <input type="image" name="consultaUsuarioWeb" src="${pageContext.request.contextPath}/resources/img/buscar.png" width='56' height='56'/>
                          </td>
                    </tr>                    
                  </table>                                 
                </div>  
            </form:form>
        </div>
    </div>
</body>
</html>