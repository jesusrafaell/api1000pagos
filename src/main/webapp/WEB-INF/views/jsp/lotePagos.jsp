<%-- 
    Document   : lotesPagos
    Created on : 16/11/2016
    Author     : mggy@sistemasemsys.com
--%>s
<%@ include file="/WEB-INF/views/jsp/include.jsp" %>
<!DOCTYPE html>
<html>
<head>        
</head>
    <body>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.1.0.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.datetimepicker.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/rainbow-custom.min.js"></script>
        <script type="text/javascript">// <![CDATA[
            $(document).ready( function() {                
                
                jQuery('#fechaDesde').datetimepicker({
                      format:'d-m-Y',                      
                      timepicker:false
                });  
            });
        </script>
        <br>
        <div class="row">
            <div id="header-form">
                <h1>Generar archivo Pago a Comercios</h1>
            </div>            
            <label class="error-message" style="align-content: center">${model.messageError}</label>
            <br>
            <div id="box-form" style="width: 700px">                    
                <br>
                <form:form method="post" autocomplete="off" commandName="lotePagos">
                    <div class="contenedor_formulario">
                      <table class="formulario">
                        <tr>
                            <td class="etiqueta_black">Tipo de Pago:</td>
                            <td class="campo">
                                <form:select path="tipoPago" cssClass="select" multiple="false">                                      
                                    <form:option value="" label="<--- Seleccione --->"/>
                                    <form:option value="1" label="Comercio"/>
                                    <form:option value="2" label="Aliado"/>
                                </form:select>
                            </td>                        
                        </tr>
                        <tr>
                            <td colspan="2">
                                <form:errors path="tipoPago" cssClass="error-message"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="etiqueta_black">Banco:</td>
                            <td class="campo">
                                <form:select multiple="false" path="banco" cssClass="select">
                                    <form:option value="" label="<--- Seleccione --->"/>
                                    <form:options items="${bancosList}" itemValue="codigo" itemLabel="descripcion"/>
                                </form:select>
                            </td>                        
                        </tr>
                        <tr>
                            <td colspan="2">
                                <form:errors path="banco" cssClass="error-message"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="etiqueta_black">Fecha:</td>
                            <td class="campo">
                                <form:input type="text" id="fechaDesde" path="fechaDesde" cssClass="textbox"/>
                            </td>                        
                        </tr>
                        <tr>
                            <td colspan="2">
                                <form:errors path="fechaDesde" cssClass="error-message"/>
                            </td>
                        </tr>                                                 
                        <tr>
                            <td class="botonera" colspan="2">                                    
                                <input type="submit" value="Generar Lote" class="button">
                            </td>
                        </tr>                            
                      </table>                    
                    </div>                    
                </form:form>                
            </div>
        </div>         
    </body>
</html>
