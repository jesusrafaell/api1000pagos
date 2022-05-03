<%-- 
    Document   : crearReclamo
    Created on : 31-ene-2017
    Author     : mggy@sistemasemsys.com
--%>

<%@ include file="/WEB-INF/views/jsp/include.jsp" %>
<!DOCTYPE html>
<html>
<head>        
</head>
    <body>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/utils.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.1.0.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.datetimepicker.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/rainbow-custom.min.js"></script>
        <script type="text/javascript">
            jQuery(function(){               

                jQuery('#fechaTransaccion').datetimepicker({
                      format:'d-m-Y',                      
                      timepicker:false
                });       
              
        });    
        </script>
        <br>
        <div class="row" style="width: 70%">
            <div id="header-form">
                <h1>Datos del Reclamo</h1>
            </div>
            <label class="error-message" style="align-content: center">${model.messageError}</label>
            <div id="box-form">
                <form:form method="post" autocomplete="off" commandName="crearReclamo">
                    <div class="contenedor_formulario">
                      <table class="formulario">                        
                        <tr>
                            <td class="etiqueta_black">Tipo de Reclamo:</td>                            
                            <td class="etiqueta_black">Número de Tarjeta:</td>
                        </tr>
                        <tr>
                            <td class="campo">
                              <form:select path="tipoReclamo" cssClass="select" multiple="false">
                                <form:option value="" label="<--- Seleccione --->"/>
                                <form:options items="${tipoReclamoList}" itemLabel="descripcionReclamo" itemValue="id"/>
                              </form:select>                             
                              <br>                              
                              <form:errors path="tipoReclamo" cssClass="error-message"/>
                            </td>                            
                            <td class="campo">                                    
                                <form:input path="numeroTarjeta" cssClass="textbox" maxlength="20" onKeyPress="return digitos (event)"/>
                                <br>
                                <form:errors path="numeroTarjeta" cssClass="error-message"/>
                            </td>                            
                        </tr>
                        <tr>
                            <td class="etiqueta_black">Fecha Transacción:</td>
                        </tr>
                        <tr>
                            <td class="campo">
                                <form:input type="text" id="fechaTransaccion" path="fechaTransaccion" cssClass="textbox" readonly="true"/>
                                <br>
                                <form:errors path="fechaTransaccion" cssClass="error-message"/>
                            </td>                        
                        </tr>
                        <tr>                            
                            <td class="etiqueta_black">Observaciones (Opcional):</td>                    
                        </tr>
                        <tr>                                                        
                            <td class="campo" colspan="2">                                    
                                <form:textarea path="observacionesReclamo" rows="3" cols="100" cssClass="textbox"/>
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="botonera" colspan="2">                                
                                <input type="image" name="crearReclamo" src="${pageContext.request.contextPath}/resources/img/guardar.png" width='56' height='56'/>
                            </td>
                        </tr>
                      </table>                    
                    </div>  
                </form:form>
            </div>
        </div>
    </body>
</html>
