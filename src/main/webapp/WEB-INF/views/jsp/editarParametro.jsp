<%-- 
    Document   : editarParametro
    Created on : 26/08/2016
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
                <h1>Formulario para modificar la información de un Parámetro</h1>
            </div>
            <label class="error-message" style="align-content: center">${model.messageError}</label>
            <div id="box-form">
                <form:form method="post" autocomplete="off" commandName="editarParametro">
                    <div class="contenedor_formulario">
                      <table class="formulario">
                        <tr>
                            <td class="etiqueta_black">Código:</td>
                            <td class="campo">
                                <form:hidden path="id" />
                                <form:input path="codigo" cssClass="textbox" readonly="true"/>                                    
                            </td>        
                        </tr>
                        <tr>
                            <td colspan="2">
                                <form:errors path="codigo" cssClass="error-message"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="etiqueta_black">Nombre:</td>
                            <td class="campo">
                                <form:input path="nombre" cssClass="textbox" readonly="true"/>                  
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <form:errors path="nombre" cssClass="error-message"/>
                            </td>
                        </tr>            
                        <tr>
                          <td class="etiqueta_black">Descripción:</td>
                            <td class="campo">
                                <form:textarea path="descripcion" rows="3" cols="10" cssClass="textbox"/>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <form:errors path="descripcion" cssClass="error-message"/>
                            </td>
                        </tr>
                        <tr>
                          <td class="etiqueta_black">Valor:</td>
                            <td class="campo">
                                <form:input path="valor" cssClass="textbox"/>                    
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <form:errors path="valor" cssClass="error-message"/>
                            </td>
                        </tr> 
                        <tr>
                            <td class="botonera" colspan="2">                                                        
                                <input type="submit" value="Volver" class="button">
                                <input type="submit" value="Aceptar" class="button">
                            </td>
                        </tr>
                      </table>                    
                    </div>  
                </form:form>
            </div>
        </div>        
    </body>
</html>
