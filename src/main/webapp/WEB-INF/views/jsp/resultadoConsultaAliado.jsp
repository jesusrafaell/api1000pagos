<%-- 
    Document   : resultadoConsultaAliado
    Created on : 13-nov-2016
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
                jQuery('#fechaNacimiento').datetimepicker({
                      format:'d-m-Y',                      
                      timepicker:false
                 });                 

                 jQuery('#estatus').change(function() {                     
                     if($("#estatus").val()==1){                         
                        $("#codModalidadPago").val(""); 
                        $("#codModalidadPago").prop('disabled', true);
                        $("#cuentaAbono").val(""); 
                        $("#cuentaAbono").prop('disabled', true);
                     }else{
                         $("#codModalidadPago").removeAttr("disabled");
                         $("#cuentaAbono").removeAttr("disabled");
                     }
                 }); 
                 
                 if($("#estatus").val()==1){
                    $("#codModalidadPago").val(""); 
                    $("#codModalidadPago").prop('disabled', true);
                    $("#cuentaAbono").val(""); 
                    $("#cuentaAbono").prop('disabled', true);
                 }else{
                     $("#codModalidadPago").removeAttr("disabled");
                     $("#cuentaAbono").removeAttr("disabled");
                 };                                                      
            });    
        </script>    
        <br>
        <div class="row" style="width: 70%">
            <div id="header-form">
                <h1>Datos Personales</h1>
            </div>
            <label class="error-message" style="align-content: center">${messageError}</label>
            <div id="box-form">
                <form:form method="post" autocomplete="off" commandName="resultadoConsultaAliado">
                    <div class="contenedor_formulario">
                      <table class="formulario">
                        <tr>
                            <input id="id" name="id" type="hidden" value="${resultadoConsultaAliado.id}"/>
                            <td class="etiqueta_black">Identificación:</td>                            
                            <td class="etiqueta_black">Apellidos:</td>                            
                        </tr>
                        <tr>
                            <td class="campo">
                              <form:select path="tipoIdentificacion" cssClass="select" multiple="false">                                      
                                <form:options items="${tipoIdentificacionList}" />
                              </form:select>
                              <form:input path="identificacion" cssClass="textbox" cssStyle="width: 80px" maxlength="9"/>
                              <br>
                              <form:errors path="tipoIdentificacion" cssClass="error-message"/>
                              <form:errors path="identificacion" cssClass="error-message"/>
                            </td>                            
                            <td class="campo">                                    
                                <form:input path="apellidos" cssClass="textbox" cssStyle="width: 250px" maxlength="100"/>
                                <br>
                                <form:errors path="apellidos" cssClass="error-message"/>
                            </td>                            
                        </tr>                        
                        <tr>
                            <td class="etiqueta_black">Nombres:</td>
                            <td class="etiqueta_black">Teléfono Habitación:</td>
                        </tr>                       
                        <tr>
                            <td class="campo">                                    
                                <form:input path="nombres" cssClass="textbox" cssStyle="width: 250px" maxlength="100"/>
                                <br>
				<form:errors path="nombres" cssClass="error-message"/>								
                            </td>
                            <td class="campo">
                                <form:select path="codigoTelHabitacion" cssClass="select" multiple="false">                                      
                                    <form:options items="${codigoTelHabitacionList}" itemLabel="codigo" itemValue="codigo"/>
                                </form:select>
                                <form:input path="telefonoHabitacion" cssClass="textbox" cssStyle="width: 80px" onKeyPress="return digitos (event)" maxlength="7"/>
                                <br>
                                <form:errors path="codigoTelHabitacion" cssClass="error-message"/>
                                <form:errors path="telefonoHabitacion" cssClass="error-message"/>	
                            </td>                                
                        </tr>
                        <tr>
                            <td class="etiqueta_black">Teléfono Celular:</td>
                            <td class="etiqueta_black">Correo Electrónico:</td>                                                              
                        </tr>                        
                        <tr>
                            <td class="campo">
                                <form:select path="codigoCelular" cssClass="select" multiple="false">                                      
                                    <form:options items="${codigoCelularList}" itemLabel="codigo" itemValue="codigo"/>
                                </form:select>
                                <form:input path="celular" cssClass="textbox" cssStyle="width: 80px" onKeyPress="return digitos (event)" maxlength="7"/>
                                <br>
                                <form:errors path="codigoCelular" cssClass="error-message"/>
                                <form:errors path="celular" cssClass="error-message"/>	
                            </td>
                            <td class="campo">                                    
                                <form:input path="email" cssClass="textbox" cssStyle="width: 250px" maxlength="100"/>
                                <br>
				<form:errors path="email" cssClass="error-message"/>								
                            </td>                                   
                        </tr>
                        <tr>
                            <td class="etiqueta_black">Fecha Nacimiento:</td>
                            <td class="etiqueta_black">Sexo:</td>                                                        
                        </tr>
                        <tr>
                             <td class="campo">                                    
                                <form:input path="fechaNacimiento" id="fechaNacimiento" cssClass="textbox" maxlength="10" readonly="true"/>
                                <br>
				<form:errors path="fechaNacimiento" cssClass="error-message"/>								
                            </td> 
                            <td class="campo">
                                <form:select path="sexo" cssClass="select" multiple="false">                                    
                                    <form:option value="M" label="M"/>
                                    <form:option value="F" label="F"/>
                                </form:select>
                                <br>
				<form:errors path="sexo" cssClass="error-message"/>
                            </td>                                    
                        </tr>
                        <tr>
                            <td class="etiqueta_black">Profesión:</td>
                            <td class="etiqueta_black">Zona de Atención Comercial:</td>							                                                          
                        </tr>                        
                        <tr>
                            <td class="campo">                                    
                                <form:input path="profesion" cssClass="textbox" cssStyle="width: 250px" maxlength="50"/>
                                <br>
                                <form:errors path="profesion" cssClass="error-message"/>								
                            </td>
                            <td class="campo">
                              <form:select path="codZonaAtencion" cssClass="select" multiple="false">                                      
                                  <form:option value="" label="<--- Seleccione --->"/>
                                  <form:options items="${zonasAtencionList}" itemLabel="descripcion" itemValue="id"/>
                              </form:select>
                              <br>  
                              <form:errors path="codZonaAtencion" cssClass="error-message"/>	
                            </td>                            
                        </tr>
                        <tr>
                            <td class="etiqueta_black">Estatus:</td>
                            <td class="etiqueta_black">Modalidad de Pago:</td>
                        </tr>
                        <tr>
                            <td class="campo">                                    
                                <form:select id="estatus" path="estatus" cssClass="select" multiple="false">                                                                        
                                  <form:options items="${estatusList}" itemLabel="descripcion" itemValue="id"/>
                                </form:select>
                                <br>
                                <form:errors path="estatus" cssClass="error-message"/>								
                            </td>
                            <td class="campo">
                              <form:select id="codModalidadPago" path="codModalidadPago" cssClass="select" multiple="false">                                      
                                  <form:option value="" label="<--- Seleccione --->"/>
                                  <form:options items="${tipoPagoList}" itemLabel="descripcionPago" itemValue="codigoTipoPago"/>
                              </form:select>
                              <br>  
                              <form:errors path="codModalidadPago" cssClass="error-message"/>	
                            </td> 
                        </tr>      
                        <tr>
                            <td class="etiqueta_black">Cuenta Abono:</td>                            
                        </tr>
                        <tr>
                            <td class="campo">                                    
                                <form:input id="cuentaAbono" path="cuentaAbono" cssClass="textbox" cssStyle="width: 250px" maxlength="20" onKeyPress="return digitos (event)"/>
                                <br>
                                <form:errors path="cuentaAbono" cssClass="error-message"/>								
                            </td>                           
                        </tr>                        
                        <tr>
                            <td class="etiqueta_black">Dirección Habitación:</td>                                               
                        </tr>
                        <tr>
                            <td class="campo" colspan="2">                                    
                                <form:textarea path="direccion" rows="3" cols="100" cssClass="textbox"/>
                                <br>
				<form:errors path="direccion" cssClass="error-message"/>
                            </td>                           
                        </tr>
                        <tr>
                            <td class="etiqueta_black"><strong>Recaudos:</strong></td>                            
                        </tr>                                                
                        <tr>
                            <td colspan="2" class="checkboxes">
                                <c:set var="recaudosCliente" value="${fn:split(resultadoConsultaAliado.recaudos, ',')}" />
                                <c:forEach items="${recaudosList}" var="recaudosAliado">                                        
                                    <c:set var="checked" value=""/>                                         
                                    <c:forEach items="${recaudosCliente}" var="recaudo">                                        
                                        <c:if test="${recaudo==recaudosAliado.id}">
                                           <c:set var="checked" value="checked"/> 
                                        </c:if>                                            
                                    </c:forEach>
                                    <form:checkbox path="recaudos" label="${recaudosAliado.descripcion}" id="${recaudosAliado.id}" value="${recaudosAliado.id}" checked="${checked}" />
                                    <br/>
                                </c:forEach>                                
                            </td>
                            <td>
                                <form:errors path="recaudos" cssClass="error-message"/>
                            </td>
                        </tr> 
                        <tr>                            
                            <td class="etiqueta_black">Observaciones (Opcional):</td>                    
                        </tr>
                        <tr>                                                        
                            <td class="campo" colspan="2">                                    
                                <form:textarea path="observaciones" rows="3" cols="100" cssClass="textbox"/>
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td class="botonera">                                
                                <a href="consultaAliado.htm"><img src="${pageContext.request.contextPath}/resources/img/volver.png" width='56' height='56'/></a>
                            </td>
                            <td></td>
                            <td class="botonera" colspan="2">                                
                                <input type="image" name="actualizaAliado" src="${pageContext.request.contextPath}/resources/img/actualizar.png" width='52' height='52'/>
                            </td>
                        </tr>
                      </table>                    
                    </div>  
                </form:form>
            </div>
        </div>
    </body>
</html>
