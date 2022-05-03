<%-- 
    Document   : crearAliado
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
            });    
        </script>    
        <br>
        <div class="row" style="width: 70%">
            <div id="header-form">
                <h1>Datos Personales</h1>
            </div>
            <label class="error-message" style="align-content: center">${model.messageError}</label>
            <div id="box-form">
                <form:form method="post" autocomplete="off" commandName="crearAliado">
                    <div class="contenedor_formulario">
                      <table class="formulario">                        
                        <tr>
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
                                <form:checkboxes items="${recaudosList}" path="recaudos" itemLabel="descripcion" itemValue="id" delimiter="<br/><br/>"/>
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
                            <td></td>
                            <td class="botonera" colspan="2">                                
                                <input type="image" name="crearAliado" src="${pageContext.request.contextPath}/resources/img/guardar.png" width='56' height='56'/>
                            </td>
                        </tr>
                      </table>                    
                    </div>  
                </form:form>
            </div>
        </div>
    </body>
</html>
