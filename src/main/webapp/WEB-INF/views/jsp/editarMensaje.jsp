<%-- 
    Document   : editarMensaje
    Created on : 16-jun-2017
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
                <h1>Formulario para modificar la información de un Mensaje</h1>
            </div>
            <label class="error-message" style="align-content: center">${model.messageError}</label>
            <div id="box-form">
                <form:form method="post" autocomplete="off" commandName="editarMensaje">
                    <div class="contenedor_formulario">
                      <table class="formulario">
                        <tr>
                            <td class="etiqueta_black">Código Mensaje:</td>                                    
                        </tr>
                        <tr>
                            <td class="campo">
                                <form:hidden path="codigoMensaje" />
                                <form:input path="mensajeClave" cssClass="textbox" readonly="true"/>                                    
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <form:errors path="mensajeClave" cssClass="error-message"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="etiqueta_black">Texto del Mensaje:</td>
                        </tr>
                        <tr>                            
                            <td class="campo">
                                <form:textarea path="mensajeTexto" rows="3" cols="90" cssClass="textbox"/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <form:errors path="mensajeTexto" cssClass="error-message"/>
                            </td>
                        </tr>                         
                        <tr>                            
                            <td class="botonera">                                
                                <a href="listadoMensajes.htm"><img src="${pageContext.request.contextPath}/resources/img/volver.png" width='56' height='56'/></a>
                            </td>
                            <td></td>
                            <td class="botonera">                                    
                                <input type="image" name="actualizaMensaje" src="${pageContext.request.contextPath}/resources/img/actualizar.png" width='52' height='52'/>
                            </td>
                        </tr>
                      </table>                    
                    </div>  
                </form:form>
            </div>
        </div>        
    </body>
</html>
