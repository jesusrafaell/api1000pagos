<%-- 
    Document   : crearBanco
    Created on : 12-nov-2016
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
                <h1>Formulario para crear un Banco</h1>
            </div>
            <label class="error-message" style="align-content: center">${model.messageError}</label>
            <div id="box-form">
                <form:form method="post" autocomplete="off" commandName="crearBanco">
                    <div class="contenedor_formulario">
                      <table class="formulario">
                        <tr>
                            <td class="etiqueta_black">Código:</td>
                            <td class="campo">                                
                                <form:input path="codigo" cssClass="textbox"/>                                    
                            </td>        
                        </tr>
                        <tr>
                            <td colspan="2">
                                <form:errors path="codigo" cssClass="error-message"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="etiqueta_black">Código Swift:</td>
                            <td class="campo">                                
                                <form:input path="codigoSwift" cssClass="textbox"/>                                    
                            </td>        
                        </tr>
                        <tr>
                            <td colspan="2">
                                <form:errors path="codigoSwift" cssClass="error-message"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="etiqueta_black">Descripción:</td>
                            <td class="campo">
                                <form:input path="descripcion" cssClass="textbox" size="80"/>                  
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <form:errors path="descripcion" cssClass="error-message"/>
                            </td>
                        </tr>                         
                        <tr>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td class="botonera" colspan="2">                                    
                                <input type="image" name="crearBanco" src="${pageContext.request.contextPath}/resources/img/guardar.png" width='52' height='52'/>
                            </td>
                        </tr>
                      </table>                    
                    </div>  
                </form:form>
            </div>
        </div>        
    </body>
</html>
