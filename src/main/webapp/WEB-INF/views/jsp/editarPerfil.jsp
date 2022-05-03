<%-- 
    Document   : editarPerfil
    Created on : 13-sep-2016
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
                <h1>Formulario para modificar la información de un Perfil</h1>
            </div>
            <label class="error-message" style="align-content: center">${model.messageError}</label>
            <div id="box-form">
                <form:form method="post" autocomplete="off" commandName="editarPerfil">
                    <div class="contenedor_formulario">
                      <table class="formulario">
                        <tr>
                            <td class="etiqueta_black">Nombre:</td>
                            <td class="campo">
                                <form:hidden path="id" />
                                <form:input path="nombre" cssClass="textbox" maxlength="50"/>                                               
                            </td>        
                        </tr>
                        <tr>
                            <td></td>
                            <td>
                                <form:errors path="nombre" cssClass="error-message"/>
                            </td>
                        </tr>  
                        <tr>
                            <c:set var="opciones" value="${fn:split(editarPerfil.opciones, ',')}" />                                
                            <td class="etiqueta_black">Opciones Módulo Administración:</td>
                            <td colspan="2" class="checkboxes">                                    
                                <c:forEach items="${opcionesAdmList}" var="opcionesAdm">                                        
                                    <c:set var="checked" value=""/>                                         
                                    <c:forEach items="${opciones}" var="opcion">                                        
                                        <c:if test="${opcion==opcionesAdm.id}">
                                           <c:set var="checked" value="checked"/> 
                                        </c:if>                                            
                                    </c:forEach>
                                    <form:checkbox path="opciones" label="${opcionesAdm.descripcion}" id="${opcionesAdm.id}" value="${opcionesAdm.id}" checked="${checked}" />
                                    <br/>
                                </c:forEach>
                            </td>
                        </tr>                           
                        <tr>
                            <td class="etiqueta_black">Opciones 1000Pagos:</td>
                            <td colspan="2" class="checkboxes">                                    
                                <c:forEach items="${opcionesPagList}" var="opcionesPag">
                                    <c:set var="checked" value=""/>                                         
                                    <c:forEach items="${opciones}" var="opcion">                                        
                                        <c:if test="${opcion==opcionesPag.id}">
                                           <c:set var="checked" value="checked"/> 
                                        </c:if>                                            
                                    </c:forEach>
                                    <form:checkbox path="opciones" label="${opcionesPag.descripcion}" id="${opcionesPag.id}" value="${opcionesPag.id}" checked="${checked}"/>
                                    <br/>
                                </c:forEach>                            
                            </td>
                        </tr>                            
                        <tr>
                            <td></td>
                            <td>
                                <form:errors path="opciones" cssClass="error-message"/>
                            </td>
                        </tr>                            
                        <tr>
                            <td class="etiqueta_black">Estatus:</td>
                            <td class="campo">
                                <form:select path="estatus" cssClass="select" multiple="false">
                                    <form:option value="" label="<--- Seleccione --->"/>
                                    <form:option value="1" label="Activo"/>
                                    <form:option value="0" label="Inactivo"/>
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
                                <a href="listadoPerfiles.htm"><img src="${pageContext.request.contextPath}/resources/img/volver.png" width='56' height='56'/></a>
                            </td>
                            <td></td>
                            <td class="botonera" colspan="2">                                
                                <input type="image" name="actualizaPerfil" src="${pageContext.request.contextPath}/resources/img/actualizar.png" width='52' height='52'/>
                            </td>
                        </tr>
                      </table>                  
                    </div>  
                </form:form>
            </div>
        </div>
    </body>
</html>
