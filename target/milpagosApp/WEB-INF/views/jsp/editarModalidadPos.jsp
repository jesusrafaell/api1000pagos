<%-- 
    Document   : editarModalidadPos
    Created on : 09-mar-2017
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
                <h1>Formulario para modificar una Modalidad de POS</h1>
            </div>
            <label class="error-message" style="align-content: center">${model.messageError}</label>
            <div id="box-form">
                <form:form method="post" autocomplete="off" commandName="editarModalidadPos">
                    <div class="contenedor_formulario">
                      <table class="formulario">
                        <input id="id" name="id" type="hidden" value="${editarModalidadPos.id}"/>  
                        <tr>
                            <td class="etiqueta_black">Descripción:</td>
                            <td class="campo">
                                <form:input path="descripcion" cssClass="textbox" size="60"/>                  
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <form:errors path="descripcion" cssClass="error-message"/>
                            </td>
                        </tr>                         
                        <tr>
                            <td></td>
                            <td class="botonera">                                
                                <a href="listadoModalidadPos.htm"><img src="${pageContext.request.contextPath}/resources/img/volver.png" width='56' height='56'/></a>
                            </td>
                            <td></td>
                            <td class="botonera" colspan="2">                                
                                <input type="image" name="editarModalidadPos" src="${pageContext.request.contextPath}/resources/img/actualizar.png" width='52' height='52'/>
                            </td>
                        </tr>
                      </table>                    
                    </div>  
                </form:form>
            </div>
        </div>        
    </body>
</html>
