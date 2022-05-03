<%-- 
    Document   : crearUsuario
    Created on : 14/09/2016
    Author     : mggy@sistemasemsys.com
--%>

<%@ include file="/WEB-INF/views/jsp/include.jsp" %>
<!DOCTYPE html>
<html>
<head>        
</head>
    <body>
        <br>
        <div class="row">
            <div id="header-form">
                <h1>Formulario para crear un Usuario</h1>
            </div>
            <label class="error-message" style="align-content: center">${model.messageError}</label>
            <div id="box-form">
                <form:form method="post" autocomplete="off" commandName="crearUsuario">
                    <div class="contenedor_formulario">
                      <table class="formulario">
                        <tr>
                            <td class="etiqueta_black">Login:</td>
                            <td class="campo">                                
                                <input id="estatus" name="estatus" type="hidden" value="0"/>
                                <form:input path="login" cssClass="textbox" maxlength="12"/>                                               
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
                            <td class="campo">
                               <form:input path="contrasena" cssClass="textbox" maxlength="12" value="${crearUsuario.contrasena}" readonly="true"/>                                                            
                            </td>        
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td class="etiqueta_black">Enviar Contraseña por Correo:</td>
                            <td colspan="2" class="checkboxes">                                    
                                <input type="checkbox" name="envioCorreo" value="1">
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                        </tr>
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
                            <td class="etiqueta_black">Nombre:</td>
                            <td class="campo">                                    
                                <form:input path="nombre" cssClass="textbox" maxlength="100"/>                                               
                            </td>        
                        </tr>
                        <tr>
                            <td></td>
                            <td>
                                <form:errors path="nombre" cssClass="error-message"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="etiqueta_black">Correo Electrónico:</td>
                            <td class="campo">                                    
                                <form:input path="email" cssClass="textbox" maxlength="100" cssStyle="width: 200px"/>                                               
                            </td>        
                        </tr>
                        <tr>
                            <td></td>
                            <td>
                                <form:errors path="email" cssClass="error-message"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="etiqueta_black">Perfil:</td>
                            <td class="campo">
                                <form:select path="perfilId" cssClass="select" multiple="false">
                                    <form:option value="" label="<--- Seleccione --->"/>
                                    <form:options items="${perfil}" itemLabel="nombre" itemValue="IdAsString" />                                        
                                </form:select>
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td>
                                <form:errors path="perfilId" cssClass="error-message"/>
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="botonera" colspan="2">                                
                                <input type="image" name="crearUsuario" src="${pageContext.request.contextPath}/resources/img/actualizar.png" width='46' height='46'/>
                            </td>
                        </tr>
                      </table>                    
                    </div>  
                </form:form>
            </div>
        </div>
    </body>
</html>