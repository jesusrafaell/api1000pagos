<%-- 
    Document   : consultaVisita
    Created on : 05-dic-2016
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
                jQuery('#fechaVisitaInicio').datetimepicker({
                      format:'d-m-Y H:i',
                      onShow:function( ct ){
                        this.setOptions({                
                            maxDate:jQuery('#fechaVisitaFin').val()?jQuery('#fechaVisitaFin').val().substr(6,4) + "/" + jQuery('#fechaVisitaFin').val().substr(3,2) + "/" + jQuery('#fechaVisitaFin').val().substr(0,2):false
                        });
                      },
                      timepicker:true
                 });                 
            });
            
            jQuery(function(){
                jQuery('#fechaVisitaFin').datetimepicker({
                      format:'d-m-Y H:i',
                      onShow:function( ct ){
                        this.setOptions({                
                            maxDate:jQuery('#fechaVisitaInicio').val()?jQuery('#fechaVisitaInicio').val().substr(6,4) + "/" + jQuery('#fechaVisitaInicio').val().substr(3,2) + "/" + jQuery('#fechaVisitaInicio').val().substr(0,2):false
                        });
                      },
                      timepicker:true
                 });                 
            });
        </script>    
        <br>
        <div class="row" style="width: 70%">
            <div id="header-form">
                <h1>Programar Visita a Comercio</h1>
            </div>
            <label class="error-message" style="align-content: center">${messageError}</label>
            <div id="box-form">
                <form:form method="post" autocomplete="off" commandName="consultaVisita">
                    
                    <div class="contenedor_formulario">
                      <table class="formulario">                        
                        <tr>
                            <td class="etiqueta_black">Identificación:</td>                            
                            <td class="etiqueta_black">Nombre Comercio:</td>                            
                        </tr>
                        <tr>
                            <td class="campo">
                              <input id="codigoComercio" name="codigoComercio" type="hidden" value="${consultaVisita.codigoComercio}"/>
                              <input id="codigoContacto" name="codigoContacto" type="hidden" value="${consultaVisita.codigoContacto}"/>
                              <form:select path="tipoIdentificacion" cssClass="select" multiple="false">
                                <form:options items="${tipoIdentificacionList}" />
                              </form:select>
                              <form:input path="rifComercio" cssClass="textbox" cssStyle="width: 80px" maxlength="9"/>
                              <br>                              
                              <form:errors path="rifComercio" cssClass="error-message"/>
                            </td>                            
                            <td class="campo">                                    
                                <form:input path="descripcionComercio" cssClass="textbox" cssStyle="width: 250px" maxlength="100"/>
                                <br>
                                <form:errors path="descripcionComercio" cssClass="error-message"/>
                            </td>                            
                        </tr>                        
                        <tr>
                            <td class="etiqueta_black">Teléfono Habitación:</td>
                            <td class="etiqueta_black">Teléfono Celular:</td>
                        </tr>                       
                        <tr>                            
                            <td class="campo">                                
                                <form:input path="telefonoLocal" cssClass="textbox" cssStyle="width: 80px" onKeyPress="return digitos (event)"/>
                                <br>                                
                                <form:errors path="telefonoLocal" cssClass="error-message"/>	
                            </td>                                
                            <td class="campo">                                
                                <form:input path="telefonoMovil" cssClass="textbox" cssStyle="width: 80px" onKeyPress="return digitos (event)"/>
                                <br>                                
                                <form:errors path="telefonoMovil" cssClass="error-message"/>	
                            </td>
                        </tr>
                        <tr>
                            <td class="etiqueta_black">Correo Electrónico:</td>
                            <td class="etiqueta_black">Actividad Comercial:</td>
                        </tr>                        
                        <tr>                            
                            <td class="campo">                                    
                                <form:input path="email" cssClass="textbox" cssStyle="width: 250px" maxlength="100"/>
                                <br>
				<form:errors path="email" cssClass="error-message"/>								
                            </td>
                            <td class="campo">                                
                                <form:select path="codigoCategoria" cssClass="select" multiple="false">
                                  <form:option value="0" label="<--- Seleccione --->"/>
                                  <form:options items="${actividadComercialList}" itemLabel="descripcionCategoria" itemValue="codigoCategoria"/>
                              </form:select>
                                <br>
                                <form:errors path="codigoCategoria" cssClass="error-message"/>                                
                            </td>
                        </tr>
                        <tr>
                            <td class="etiqueta_black">Persona Contacto:</td>
                            <td class="etiqueta_black">Titulo Visita:</td>                                                        
                        </tr>
                        <tr>
                            <td class="campo">                                    
                                Nombre: <form:input path="contactoNombres" cssClass="textbox" maxlength="100"/> 
                                <br>
                                Apellido: <form:input path="contactoApellidos" cssClass="textbox" maxlength="100"/>
                                <br>
				<form:errors path="contactoNombres" cssClass="error-message"/>
                                <br>
                                <form:errors path="contactoApellidos" cssClass="error-message"/>
                            </td> 
                            <td class="campo">
                                <form:input path="tituloVisita" cssClass="textbox" cssStyle="width: 250px" maxlength="20"/>
                                <br>
                                <form:errors path="tituloVisita" cssClass="error-message"/>
                            </td>
                        </tr>                        
                        <tr>                            
                            <td class="etiqueta_black">Fecha Hora Inicio:</td>
                            <td class="etiqueta_black">Fecha Hora Fin:</td>    
                        </tr>                                                            
                        <tr>                            
                            <td class="campo">                                    
                                <form:input path="fechaVisitaInicio" id="fechaVisitaInicio" cssClass="textbox" readonly="true"/>
                                <br>
				<form:errors path="fechaVisitaInicio" cssClass="error-message"/>                                
                            </td>
                            <td class="campo">                                    
                                <form:input path="fechaVisitaFin" id="fechaVisitaFin" cssClass="textbox" readonly="true"/>
                                <br>
				<form:errors path="fechaVisitaFin" cssClass="error-message"/>                                
                            </td>
                        </tr>
                        <c:if test="${model.existeAliado=='0'}">
                        <tr>                            
                            <td class="etiqueta_black">Aliado Comercial:</td>                            
                        </tr>                                                            
                        <tr>                            
                            <td class="campo">
                                <form:select path="aliadoComercial" cssClass="select" multiple="false">
                                    <form:option value="" label="<--- Seleccione --->"/>
                                    <form:options items="${aliadosList}" itemLabel="ContactoNombreCompleto" itemValue="id" />                                        
                                </form:select>
                                <br>
                                <form:errors path="aliadoComercial" cssClass="error-message"/>
                            </td>                            
                        </tr>
                        </c:if> 
                        <tr>
                            <td class="etiqueta_black">Dirección Habitación:</td>                                               
                        </tr>
                        <tr>
                            <td class="campo" colspan="2">                                    
                                <form:textarea path="direccionComercio" rows="3" cols="100" cssClass="textbox"/>
                                <br>
				<form:errors path="direccionComercio" cssClass="error-message"/>
                            </td>                           
                        </tr>                        
                        <tr>                            
                            <td class="etiqueta_black">Observaciones (Opcional):</td>                    
                        </tr>
                        <tr>                                                        
                            <td class="campo" colspan="2">                                    
                                <form:textarea path="observacionesComercio" rows="3" cols="100" cssClass="textbox"/>
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td class="botonera">                                
                                <a href="consultaAgenda.htm"><img src="${pageContext.request.contextPath}/resources/img/volver.png" width='56' height='56'/></a>
                            </td>                            
                            <td></td>
                            <td class="botonera">                                
                                <input type="image" name="actualizaVisita" src="${pageContext.request.contextPath}/resources/img/actualizar.png" width='52' height='52'/>
                            </td>
                            <td></td>    
                        </tr>
                      </table>                    
                    </div>  
                </form:form>
            </div>
        </div>
    </body>
</html>
