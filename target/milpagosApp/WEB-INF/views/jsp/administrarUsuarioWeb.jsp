<%-- 
    Document   : administrarUsuarioWeb
    Created on : 12/06/2017
    Author     : mggy@sistemasemsys.com
--%>

<%@ include file="/WEB-INF/views/jsp/include.jsp" %>
<!DOCTYPE html>
<html>
<head>        
</head>
    <body>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/utils.js"></script>        
        <br>
        <div class="row">
            <div id="header-form">
                <h1>Formulario para Administrar Usuarios del Sitio Web</h1>
            </div>
            <label class="error-message" style="align-content: center">${model.messageError}</label>
            <div id="box-form">
                <form:form method="post" name="administrarUsuarioWeb" commandName="administrarUsuarioWeb" autocomplete="off">
                    <div class="contenedor_formulario">
                      <table class="formulario">                                                                   
                        <tr>
                            <td class="etiqueta_black">Reset Contraseña:</td>
                            <td colspan="2" class="checkboxes"> 
                                <input id="codigoUsuarioWeb" name="codigoUsuarioWeb" type="hidden" value="${administrarUsuarioWeb.codigoUsuarioWeb}"/>                                
                                <input id="contrasena" name="contrasena" type="hidden" value="${administrarUsuarioWeb.contrasena}"/>
                                <input id="cambio" name="cambio" type="hidden" value="${administrarUsuarioWeb.cambio}"/>                                                                
                                <input type="checkbox" id="resetContrasena" name="resetContrasena" value="1">
                            </td>
                        </tr>
                         <tr>
                            <td class="etiqueta_black">Reset Preguntas de Desafío:</td>
                            <td colspan="2" class="checkboxes">                                 
                                <input type="checkbox" id="resetPreguntas" name="resetPreguntas" value="1">
                            </td>
                        </tr>
                        <tr>
                            <td class="etiqueta_black">Identificación:</td>
                            <td class="campo">
                                <form:select path="tipoIdentificacion" cssClass="select" multiple="false" readonly="true">                                      
                                <form:options items="${tipoIdentificacionList}" />
                              </form:select>
                              <form:input path="identificacion" cssClass="textbox" cssStyle="width: 80px" maxlength="9" readonly="true"/>                                 
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
                            <td class="etiqueta_black">Correo Electrónico:</td>
                            <td class="campo">                                    
                                <form:input path="email" cssClass="textbox" maxlength="100" cssStyle="width: 250px"/>                                               
                            </td>        
                        </tr>
                        <tr>
                            <td></td>
                            <td>
                                <form:errors path="email" cssClass="error-message"/>
                            </td>
                        </tr>                        
                        <tr>
                            <td class="etiqueta_black">Estatus:</td>
                            <td class="campo">
                                <form:select path="estatus" cssClass="select" multiple="false">                                                                        
                                  <form:options items="${estatusWebList}" itemLabel="descripcion" itemValue="id"/>
                                </form:select>                                
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td>
                                <form:errors path="estatus" cssClass="error-message"/>
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td class="botonera">                                
                                <a href="consultaUsuarioWeb.htm"><img src="${pageContext.request.contextPath}/resources/img/volver.png" width='56' height='56'/></a>
                            </td>
                            <td></td>
                            <td class="botonera" colspan="2">                                    
                                <input type="image" name="administrarUsuarioWeb" src="${pageContext.request.contextPath}/resources/img/actualizar.png" width='52' height='52'/>
                            </td>
                        </tr>
                      </table>                    
                    </div>  
                </form:form>
            </div>
        </div>        
    </body>
</html>