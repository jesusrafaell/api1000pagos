<%-- 
    Document   : eliminarUsuario
    Created on : 03/10/2016
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
                <h1>Confirme la información del Usuario a eliminar</h1>
            </div>
            <label class="error-message" style="align-content: center">${model.messageError}</label>
            <div id="box-form">
                <form:form method="post" autocomplete="off" commandName="eliminarUsuario">
                    <div class="contenedor_formulario">
                      <table class="formulario">
                        <tr>
                            <td class="etiqueta_black">Login:</td>
                            <td class="campo">
                                <input id="id" name="id" type="hidden" value="${eliminarUsuario.id}"/>
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
                            <td class="etiqueta_black">Identificación:</td>
                            <td>
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
                            <td class="etiqueta_black">Nombre:</td>
                            <td class="campo">                                    
                                <form:input path="nombre" cssClass="textbox" maxlength="100" readonly="true"/>                                               
                            </td>        
                        </tr>
                        <tr>
                            <td></td>
                            <td>
                                <form:errors path="nombre" cssClass="error-message"/>
                            </td>
                        </tr>                        
                        <tr>
                            <td class="etiqueta_black">Perfil:</td>
                            <td class="campo">
                                <form:select path="perfilId" cssClass="select" multiple="false" readonly="true">
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
                            <td class="botonera">                                
                                <a href="listadoUsuarios.htm"><img src="${pageContext.request.contextPath}/resources/img/volver.png" width='56' height='56'/></a>
                            </td>
                            <td></td>
                            <td class="botonera" colspan="2">                                
                                <input type="image" name="eliminaPerfil" src="${pageContext.request.contextPath}/resources/img/actualizar.png" width='52' height='52'/>
                            </td>
                        </tr>
                      </table>                    
                    </div>  
                </form:form>
            </div>
        </div>        
    </body>
</html>