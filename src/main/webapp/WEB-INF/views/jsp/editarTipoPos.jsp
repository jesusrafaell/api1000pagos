<%-- 
    Document   : EditarTipoPos
    Created on : 30-mar-2020, 16:03:55
    Author     : jperez@emsys-solutions.net
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
                <h1>Formulario para modificar un Tipo Pos</h1>
            </div>
            <label class="error-message" style="align-content: center">${model.messageError}</label>
            <div id="box-form">
                <form:form method="post" autocomplete="off" commandName="editarTipoPos">
                    <div class="contenedor_formulario">
                      <table class="formulario">  
                             <tr>
                            <td class="etiqueta_black">Código:</td>
                            <td class="campo">                   
                                 <form:input path="id" cssClass="textbox" size="10"/>  
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <form:errors path="id" cssClass="error-message"/>
                            </td>
                        </tr> 
                        <tr>
                            <td class="etiqueta_black">Descripción:</td>
                            <td class="campo">                   
                                 <form:input path="descripcion" cssClass="textbox" size="20"/>  
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <form:errors path="descripcion" cssClass="error-message"/>
                            </td>
                        </tr> 
                         <tr>
                            <td class="etiqueta_black">Costo:</td>
                            <td class="campo">
                                 <form:input path="costo" cssClass="textbox" size="10"/> 
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <form:errors path="costo" cssClass="error-message"/>
                            </td>
                        </tr> 
                         <tr>
                            <td class="etiqueta_black">Moneda:</td>
                            <td class="campo">
                                <form:select path="moneda" cssClass="select" multiple="false">                                      
                                    <form:options items="${MonedaList}" itemLabel="descripcion" itemValue="id"/>
                                </form:select>
                            </td>
                        </tr>                
                        <tr>
                            <td></td>
                            <td class="botonera">                                
                                <a href="listadoTipoPos.htm"><img src="${pageContext.request.contextPath}/resources/img/volver.png" width='56' height='56'/></a>
                            </td>
                            <td></td>
                            <td class="botonera" colspan="2">                                    
                                <input type="image" name="actualizatipopos" src="${pageContext.request.contextPath}/resources/img/actualizar.png" width='52' height='52'/>
                            </td>
                        </tr>
                      </table>                    
                    </div>  
                </form:form>
            </div>
        </div>        
    </body>
</html>