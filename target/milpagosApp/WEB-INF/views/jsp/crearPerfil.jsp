<%-- 
    Document   : crearPerfil
    Created on : 25/08/2016
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
                <h1>Formulario para crear un Perfil de acceso a la aplicación</h1>
            </div>
            <label class="error-message" style="align-content: center">${model.messageError}</label>
            <div id="box-form">
                <form:form method="post" autocomplete="off" commandName="crearPerfil">
                    <div class="contenedor_formulario">
                      <table class="formulario">
                        <tr>
                            <td class="etiqueta_black">Nombre:</td>
                            <td class="campo">                                    
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
                            <td class="etiqueta_black">Módulo Administración:</td>
                            <td colspan="2" class="checkboxes">                                    
                                <form:checkboxes items="${opcionesAdmList}" path="opciones" itemLabel="descripcion" itemValue="id" delimiter="<br/>"/>
                            </td>
                        </tr>                            
                        <tr>
                            <td class="etiqueta_black">1000 Pagos:</td> 
                            <td colspan="2" class="checkboxes">                                    
                                <form:checkboxes items="${opcionesPagList}" path="opciones" itemLabel="descripcion" itemValue="id" delimiter="<br/>"/>
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
                            <td class="botonera" colspan="2">                                    
                                <input type="image" name="crearPerfil" src="${pageContext.request.contextPath}/resources/img/guardar.png" width='46' height='46'/>
                            </td>
                        </tr>
                      </table>                    
                    </div>  
                </form:form>
            </div>
        </div>        
    </body>
</html>
