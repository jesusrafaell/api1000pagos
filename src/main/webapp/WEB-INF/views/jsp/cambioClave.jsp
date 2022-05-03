<%-- 
    Document   : cambioClave
    Created on : 15-ago-2016
    Author     : MGGY
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
                <h1>Formulario para Cambio de Clave</h1>
            </div>                
            <div id="box-form">
                <form:form method="post" autocomplete="off" commandName="cambioClave">                        
                    <div class="contenedor_formulario">
                      <table class="formulario">
                        <input id="id" name="id" type="hidden" value="${cambioClave.id}"/>
                        <input id="cambio" name="cambio" type="hidden" value="${cambioClave.cambio}"/>                        
                        <input id="perfil" name="perfilId" type="hidden" value="${cambioClave.perfilId}"/>
                        <input id="estatus" name="estatus" type="hidden" value="${cambioClave.estatus}"/>
                        <input id="email" name="email" type="hidden" value="${cambioClave.email}"/>  
                        <c:if test="${fn:trim(cambioClave.cambio) == 0}">
                            <tr>
                            <td class="etiqueta_black">Identificación:</td>
                            <td style="text-align: left">
                              <form:select path="tipoIdentificacion" cssClass="select" multiple="false">                                      
                                <form:option value="V" label="V"/>
                                <form:option value="E" label="E"/>
                                <form:option value="P" label="P"/>
                                <form:option value="J" label="J"/>
                                <form:option value="G" label="G"/>
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
                        </c:if>
                        <c:if test="${fn:trim(cambioClave.cambio) == 1 || fn:trim(cambioClave.cambio) == 2}">
                            <input id="tipoIdentificacion" name="tipoIdentificacion" type="hidden" value="${cambioClave.tipoIdentificacion}"/>
                            <input id="identificacion" name="identificacion" type="hidden" value="${cambioClave.identificacion}"/>
                        </c:if>   
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
                            <td class="etiqueta_black">Login:</td>
                            <td class="campo">                                    
                                <form:input path="login" cssClass="textbox" maxlength="12" readonly="true"/>                                               
                            </td>        
                        </tr>
                        <tr>
                            <td></td>
                            <td>
                                <form:errors path="login" cssClass="error-message"/>
                            </td>
                        </tr>
                        <tr>
                          <td class="etiqueta_black">Nueva Contraseña:</td>
                            <td class="campo">
                                <form:password path="contrasena" cssClass="textbox"/>                                    
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td>
                                <form:errors path="contrasena" cssClass="error-message"/>
                                <label class="error-message" style="align-content: center">${model.messageContrasena}</label>
                            </td>
                        </tr>
                        <tr>
                            <td class="botonera" colspan="2">                                    
                                <input type="image" name="cambioClave" src="${pageContext.request.contextPath}/resources/img/guardar.png" width='46' height='46'/>
                            </td>
                        </tr>
                      </table>                    
                    </div>  
                </form:form>
            </div>
        </div>        
    </body>
</html>