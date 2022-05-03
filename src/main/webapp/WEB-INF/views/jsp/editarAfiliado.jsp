<%-- 
    Document   : editarAfiliado
    Created on : 12-nov-2018
    Author     : mggy@sistemasemsys.com
--%>
<%@ include file="/WEB-INF/views/jsp/include.jsp" %>
<!DOCTYPE html>
<html>
<head>        
</head>
    <body>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/utils.js"></script>
        <br>
        <div class="row">
            <div id="header-form">
                <h1>Formulario para editar un Afiliado</h1>
            </div>
            <label class="error-message" style="align-content: center">${model.messageError}</label>
            <div id="box-form">
                <form:form method="post" autocomplete="off" commandName="editarAfiliado">
                    <div class="contenedor_formulario">
                      <table class="formulario">                                       
                        <tr>
                            <td class="etiqueta_black">Código:</td>
                            <td class="campo">
                                <form:input path="codigoAfiliado" cssClass="textbox" size="60"/>                  
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <form:errors path="codigoAfiliado" cssClass="error-message"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="etiqueta_black">Tipo Persona:</td>
                            <td class="campo">
                                <form:select path="tipoPersonaAfiliado" cssClass="select" multiple="false">                                      
                                    <form:options items="${tipoPersonaList}" itemLabel="descripcion" itemValue="codigoTipoPersona"/>
                                </form:select>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <form:errors path="tipoPersonaAfiliado" cssClass="error-message"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="etiqueta_black">Banco:</td>
                            <td class="campo">
                                <form:select path="codigoBanco" cssClass="select" multiple="false">
                                    <form:option value="" label="<--- Seleccione --->"/>
                                    <form:options items="${bancosList}" itemLabel="descripcion" itemValue="codigo"/>
                                </form:select>                                              
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <form:errors path="codigoBanco" cssClass="error-message"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="etiqueta_black">Número de Cuenta:</td>
                            <td class="campo">
                                <form:input path="numeroCuentaAbono" cssClass="textbox" cssStyle="width: 250px" maxlength="20" onKeyPress="return digitos (event)"/>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <form:errors path="numeroCuentaAbono" cssClass="error-message"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="etiqueta_black">Descripción:</td>
                            <td class="campo">
                                <form:textarea path="descripcionAfiliado" rows="3" cols="100" cssClass="textbox"/>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <form:errors path="descripcionAfiliado" cssClass="error-message"/>
                            </td>
                        </tr>                         
                        <tr>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td class="botonera" colspan="2">                                    
                                <input type="image" name="editarAfiliado" src="${pageContext.request.contextPath}/resources/img/guardar.png" width='52' height='52'/>
                            </td>
                        </tr>
                      </table>                    
                    </div>  
                </form:form>
            </div>
        </div>        
    </body>
</html>
