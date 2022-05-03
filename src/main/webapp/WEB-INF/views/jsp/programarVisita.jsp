<%-- 
    Document   : programarVisita
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
                            maxDate:jQuery('#fechaVisitaFin').val()?jQuery('#fechaVisitaFin').val().substr(6,4) + "/" + jQuery('#fechaVisitaFin').val().substr(3,2) + "/" + jQuery('#fechaVisitaFin').val().substr(0,2):false,
                            maxTime:jQuery('#fechaVisitaFin').val()?jQuery('#fechaVisitaFin').val().substr(11,2) + ":" + jQuery('#fechaVisitaFin').val().substr(14,2):false
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
                            minDate:jQuery('#fechaVisitaInicio').val()?jQuery('#fechaVisitaInicio').val().substr(6,4) + "/" + jQuery('#fechaVisitaInicio').val().substr(3,2) + "/" + jQuery('#fechaVisitaInicio').val().substr(0,2):false,
                            minTime:jQuery('#fechaVisitaInicio').val()?jQuery('#fechaVisitaInicio').val().substr(11,2) + ":" + jQuery('#fechaVisitaInicio').val().substr(14,2):false
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
            <label class="error-message" style="align-content: center">${programarVisita.messageError}</label>
            <div id="box-form">
                <form:form method="post" autocomplete="off" commandName="programarVisita" enctype="multipart/form-data">
                    <input id="existeAliado" name="existeAliado" type="hidden" value="${programarVisita.existeAliado}"/>
                    <div class="contenedor_formulario">
                      <table class="formulario">                        
                        <tr>
                            <td class="etiqueta_black">Identificación:</td>                            
                            <td class="etiqueta_black">Nombre Comercio:</td>                            
                        </tr>
                        <tr>
                            <td class="campo">
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
                                <form:input path="telefonoLocal" cssClass="textbox" cssStyle="width: 80px" onKeyPress="return digitos (event)" maxlength="11"/>
                                <br>                                
                                <form:errors path="telefonoLocal" cssClass="error-message"/>	
                            </td>                                
                            <td class="campo">                                
                                <form:input path="telefonoMovil" cssClass="textbox" cssStyle="width: 80px" onKeyPress="return digitos (event)" maxlength="11"/>
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
                                  <form:option value="" label="<--- Seleccione --->"/>
                                  <form:options items="${actividadComercialList}" itemLabel="descripcionCategoria" itemValue="CodigoCategoriaAsString"/>
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
                                <form:input path="fechaVisitaInicio" id="fechaVisitaInicio" cssClass="textbox" maxlength="10" readonly="true"/>
                                <br>
				<form:errors path="fechaVisitaInicio" cssClass="error-message"/>                                
                            </td>
                            <td class="campo">                                    
                                <form:input path="fechaVisitaFin" id="fechaVisitaFin" cssClass="textbox" maxlength="10" readonly="true"/>
                                <br>
				<form:errors path="fechaVisitaFin" cssClass="error-message"/>                                
                            </td>
                        </tr>
                        <c:if test="${programarVisita.existeAliado=='0'}">
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
                        <%-- 
                        <c:if test="${programarVisita.rifComercio==''}">
                        <tr>                            
                            <td class="etiqueta_black">Adjuntar Imagen (Opcional):</td>                            
                        </tr>
                        <tr>
                            <td class="campo">
                                <input type="file" name="imagenComercio" id="imagenComercio" accept="image/*" multiple="true"/>
                                <br>
                                <form:errors path="imagenComercio" cssClass="error-message"/>
                            </td>
                        </tr>
                        </c:if>
                        --%>
                        <tr>
                            <td class="etiqueta_black">Dirección Fiscal:</td>                                               
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
                                <a href="programarVisitaConsulta.htm"><img src="${pageContext.request.contextPath}/resources/img/volver.png" width='56' height='56'/></a>
                            </td>                            
                            <td></td>
                            <td class="botonera" colspan="2">                                
                                <input type="image" name="crearVisita" src="${pageContext.request.contextPath}/resources/img/guardar.png" width='56' height='56'/>
                            </td>
                        </tr>
                      </table>                    
                    </div>  
                </form:form>
            </div>
        </div>
    </body>
</html>
