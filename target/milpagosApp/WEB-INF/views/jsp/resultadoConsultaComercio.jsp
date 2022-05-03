<%-- 
    Document   : resultadoConsultaComercio
    Created on : 02-dic-2016
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
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.multi-select.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/alertify.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/rainbow-custom.min.js"></script>
        <script type="text/javascript">
            
            function alerta(){
                    //un alert
                    alertify.alert("<b>Blog Reaccion Estudio</b> probando Alertify", function () {
                            //aqui introducimos lo que haremos tras cerrar la alerta.
                            //por ejemplo -->  location.href = 'http://www.google.es/';  <-- Redireccionamos a GOOGLE.
                    });
            }
                        
            jQuery(function(){
                jQuery('#fechaInicioContrato').datetimepicker({
                      format:'d-m-Y',
                      onShow:function( ct ){
                        this.setOptions({                
                            maxDate:jQuery('#fechaFinContrato').val()?jQuery('#fechaFinContrato').val().substr(6,4) + "/" + jQuery('#fechaFinContrato').val().substr(3,2) + "/" + jQuery('#fechaFinContrato').val().substr(0,2):false                            
                        });
                      },
                      timepicker:false
                 });
                 
                 jQuery('#fechaFinContrato').datetimepicker({
                      format:'d-m-Y',
                      onShow:function( ct ){
                        this.setOptions({                
                            minDate:jQuery('#fechaInicioContrato').val()?jQuery('#fechaInicioContrato').val().substr(6,4) + "/" + jQuery('#fechaInicioContrato').val().substr(3,2) + "/" + jQuery('#fechaInicioContrato').val().substr(0,2):false                            
                        });
                      },
                      timepicker:false
                 });
                 
                 jQuery('#fechaGarantiaFianza').datetimepicker({
                      format:'d-m-Y',                      
                      timepicker:false
                 });
                 
                 $('#afiliados').multiSelect();  
                 
                 jQuery('#puntoAdicional').click(function() {                     
                     if($("#puntoAdicional").prop('checked')==true){                        
                        $("#cuentas").show();
                        $("#cuentasCampos").show();
                        $("#cuentas2").show();
                        $("#cuentasCampos2").show();
                     }else{
                        $("#cuentas").hide();
                        $("#cuentasCampos").hide();
                        $("#cuentas2").hide();
                        $("#cuentasCampos2").hide();
                     }
                 }); 
                                 
            });
            
            //Formato para montos
            function puntitos(donde,caracter,campo)
            {
                var decimales = false
                campo = eval("donde.form." + campo)
                dec = 2
                if (dec != 0)
                {decimales = true}

                pat = /[\*,\+,\(,\),\?,\\,\$,\[,\],\^]/
                valor = donde.value
                largo = valor.length
                crtr = true
                if(isNaN(caracter) || pat.test(caracter) == true)
                        {
                        if (pat.test(caracter)==true) 
                                {caracter = "\\" + caracter}
                        carcter = new RegExp(caracter,"g")
                        valor = valor.replace(carcter,"")
                        donde.value = valor
                        crtr = false
                        }
                else
                        {
                        var nums = new Array()
                        cont = 0
                        for(m=0;m<largo;m++)
                                {
                                if(valor.charAt(m) == "." || valor.charAt(m) == " " || valor.charAt(m) == ",")
                                        {continue;}
                                else{
                                        nums[cont] = valor.charAt(m)
                                        cont++
                                        }

                                }
                        }

                if(decimales == true) {
                        ctdd = eval(1 + dec);
                        nmrs = 1
                }
                else {
                        ctdd = 1; nmrs = 3
                }
                var cad1="",cad2="",cad3="",tres=0
                valor_sincoma = valor.replace(",","")
                largo=valor_sincoma.length
                if(largo > nmrs && crtr == true)
                        {	
                        for (k=nums.length-ctdd;k>=0;k--){
                                cad1 = nums[k]
                                cad2 = cad1 + cad2
                                tres++
                                if((tres%3) == 0){
                                        if(k!=0){
                                                cad2 = "." + cad2
                                                }
                                        }
                                }

                        for (dd = dec; dd > 0; dd--)	
                        {cad3 += nums[nums.length-dd] }
                        if(decimales == true)
                        {cad2 += "," + cad3}
                         donde.value = cad2
                        }
                donde.focus()
            }
        </script>    
        <br>
        <div class="row" style="width: 70%">
            <div id="header-form">
                <h1>Datos del Comercio</h1>
            </div>
            <label class="error-message" style="align-content: center">${messageError}</label>
            <div id="box-form">
                <form:form method="post" autocomplete="off" commandName="resultadoConsultaComercio">
                    <c:if test="${resultadoConsultaComercio.estatusComercio==21}">
                        <script type="text/javascript">                        
                            alertify.alert("Este Comercio esta en Lista Negra<br>", function () {});
                        </script>    
                    </c:if>          
                    <div class="contenedor_formulario">
                      <table class="formulario">
                        <tr>
                            <input id="codigoComercio" name="codigoComercio" type="hidden" value="${resultadoConsultaComercio.codigoComercio}"/>
                            <input id="codigoContacto" name="codigoContacto" type="hidden" value="${resultadoConsultaComercio.codigoContacto}"/>
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
                                  <form:options items="${actividadComercialList}" itemLabel="descripcionCategoria" itemValue="codigoCategoria"/>
                              </form:select>
                                <br>
                                <form:errors path="codigoCategoria" cssClass="error-message"/>                                
                            </td>
                        </tr>
                        <tr>
                            <td class="etiqueta_black">Persona Contacto:</td>
                            <td class="etiqueta_black">Tipo Contrato:</td>                                                        
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
                                <form:select path="codigoTipoContrato" cssClass="select" multiple="false">
                                    <form:option value="" label="<--- Seleccione --->"/>
                                    <form:options items="${tipoContratolList}" itemLabel="descripcion" itemValue="codigoContrato"/>
                                </form:select>                                
                                <br>
                                <br>
				<form:errors path="codigoTipoContrato" cssClass="error-message"/>
                            </td>                                    
                        </tr>
                        <tr>
                            <td class="etiqueta_black">Fecha Inicio Contrato:</td>
                            <td class="etiqueta_black">Fecha Fin Contrato:</td>							                                                          
                        </tr>
                        <tr>
                            <td class="campo">                                    
                                <form:input path="fechaInicioContrato" id="fechaInicioContrato" cssClass="textbox" maxlength="10" readonly="true"/>
                                <br>
                                <form:errors path="fechaInicioContrato" cssClass="error-message"/>								
                            </td>
                            <td class="campo">                                    
                                <form:input path="fechaFinContrato" id="fechaFinContrato" cssClass="textbox" maxlength="10" readonly="true"/>
                                <br>
                                <form:errors path="fechaFinContrato" cssClass="error-message"/>								
                            </td>
                        </tr>
                        <tr>
                            <td class="etiqueta_black">Días de Operación:</td>
                            <td class="etiqueta_black"></td>							                                                          
                        </tr>                        
                        <tr>
                            <td class="checkboxes">                                                                                                      
                                <c:set var="dias" value="${fn:split(resultadoConsultaComercio.diasOperacion, ',')}" />
                                <c:forEach items="${diasList}" var="diasOpe">                                        
                                    <c:set var="checked" value=""/>                                         
                                    <c:forEach items="${dias}" var="dia">                                        
                                        <c:if test="${dia==diasOpe.value}">
                                           <c:set var="checked" value="checked"/> 
                                        </c:if>                                            
                                    </c:forEach>
                                    <form:checkbox path="diasOperacion" label="${diasOpe.key}" value="${diasOpe.value}" checked="${checked}" />
                                    &nbsp;
                                </c:forEach>                            
                                <br>
                                <form:errors path="diasOperacion" cssClass="error-message"/>								
                            </td>
                            <td class="campo">                                    
                                							
                            </td>
                        </tr>     
                        <tr>
                            <td class="etiqueta_black">Banco Cuenta Abono:</td>
                            <td class="etiqueta_black">Cuenta Abono:</td>
                        </tr>                        
                        <tr>
                            <td class="campo">
                                <form:select path="codigoBancoCuentaAbono" cssClass="select" multiple="false">
                                    <form:option value="" label="<--- Seleccione --->"/>
                                    <form:options items="${bancoList}" itemLabel="descripcion" itemValue="codigo"/>
                                </form:select>
                                <br>
                                <form:errors path="codigoBancoCuentaAbono" cssClass="error-message"/>                               								
                            </td>
                            <td class="campo">
                                <c:choose>
                                    <c:when test = "${editCA}">
                                        <form:input path="numeroCuentaAbono" cssClass="textbox" cssStyle="width: 250px" maxlength="20" onKeyPress="return digitos (event)"/>
                                        <br>
                                        <form:errors path="numeroCuentaAbono" cssClass="error-message"/>
                                    </c:when>
                                    <c:otherwise>
                                        <form:input path="numeroCuentaAbono" cssClass="textbox" cssStyle="width: 250px" readonly="true"/>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>                         
                        <c:if test="${resultadoConsultaComercio.puntoAdicional==1}">
                           <c:set var="checkedPunto" value="checked"/> 
                           <c:set var="show" value=""/>
                        </c:if>
                         <c:if test="${resultadoConsultaComercio.puntoAdicional==0}">
                            <c:set var="checkedPunto" value=""/> 
                        <c:set var="show" value="style='display: none'"/>
                        </c:if>                       
                        <tr>
                            <td class="etiqueta_black">Punto Adicional:</td>                            
                            <td class="etiqueta_black"></td>                            
                        </tr>   
                        <tr>
                            <td class="campo">                                                                                                    
                                <input type="checkbox" id="puntoAdicional" name="puntoAdicional" value="1" ${checkedPunto}>
                                <br>
                                <form:errors path="puntoAdicional" cssClass="error-message"/>
                            </td>                            
                            <td class="campo"></td>                             
                        </tr>                        
                        <tr id="cuentas" ${show}>
                            <td class="etiqueta_black">Banco Cuenta Abono Punto Adicional 2:</td>
                            <td class="etiqueta_black">Cuenta Abono Punto Adicional 2:</td>                            							                                                          
                        </tr>                        
                        <tr id="cuentasCampos" ${show}>
                            <td class="campo">                                    
                                <form:select path="codigoBancoCuentaAbono2" cssClass="select" multiple="false">
                                    <form:option value="" label="<--- Seleccione --->"/>
                                    <form:options items="${bancoList}" itemLabel="descripcion" itemValue="codigo"/>
                                </form:select>
                                <br>
                                <form:errors path="codigoBancoCuentaAbono2" cssClass="error-message"/>                                    
                            </td>
                            <td class="campo">                                
                                <form:input path="numeroCuentaAbono2" cssClass="textbox" cssStyle="width: 250px" maxlength="20" onKeyPress="return digitos (event)"/>
                                <br>
                                <form:errors path="numeroCuentaAbono2" cssClass="error-message"/>								
                            </td>
                        </tr> 
                        <tr id="cuentas2" ${show}>
                            <td class="etiqueta_black">Banco Cuenta Abono Punto Adicional 3:</td>
                            <td class="etiqueta_black">Cuenta Abono Punto Adicional 3:</td>                            							                                                          
                        </tr>                        
                        <tr id="cuentasCampos2" ${show}>
                            <td class="campo">
                                <form:select path="codigoBancoCuentaAbono3" cssClass="select" multiple="false">
                                    <form:option value="" label="<--- Seleccione --->"/>
                                    <form:options items="${bancoList}" itemLabel="descripcion" itemValue="codigo"/>
                                </form:select>
                                <br>
                                <form:errors path="codigoBancoCuentaAbono3" cssClass="error-message"/>                                								
                            </td>
                            <td class="campo">                                
                                <form:input path="numeroCuentaAbono3" cssClass="textbox" cssStyle="width: 250px" maxlength="20" onKeyPress="return digitos (event)"/>
                                <br>
                                <form:errors path="numeroCuentaAbono3" cssClass="error-message"/>								
                            </td>
                        </tr>                                                 
                        <tr>
                            <td class="etiqueta_black">Modalidad POS:</td>
                            <td class="etiqueta_black">Tipo POS:</td>	                                                        						                                                          
                        </tr>                        
                        <tr>
                            <td class="campo">                                    
                                <form:select path="modalidadPos" cssClass="select" multiple="false">
                                    <form:option value="" label="<--- Seleccione --->"/>
                                    <form:options items="${modalidadPosList}" itemLabel="descripcion" itemValue="id"/>
                                </form:select>
                                <br>
                                <form:errors path="modalidadPos" cssClass="error-message"/>								
                            </td>
                            <td class="campo">                                    
                                <form:select path="tipoPos" cssClass="select" multiple="false">
                                    <form:option value="" label="<--- Seleccione --->"/>
                                    <form:options items="${tipoPosList}" itemLabel="descripcion" itemValue="id"/>
                                </form:select>
                                <br>
                                <form:errors path="tipoPos" cssClass="error-message"/>								
                            </td>                                                                                    
                        </tr>                                                
                        <tr>
                            <td class="etiqueta_black">¿Tiene Garantía?:</td>
                            <td class="etiqueta_black">Modalidad Garantía:</td>	                                                        
                        </tr>                        
                        <tr>
                            <td class="campo">                                
                                <form:radiobutton path="entregoGarantiaFianza" value="1"/>SI
                                <form:radiobutton path="entregoGarantiaFianza" value="0"/>NO
                                <br>
                                <form:errors path="entregoGarantiaFianza" cssClass="error-message"/>								
                            </td>
                            <td class="campo">                                    
                                <form:select path="modalidadGarantia" cssClass="select" multiple="false">
                                    <form:option value="" label="<--- Seleccione --->"/>
                                    <form:options items="${modalidadGarantialList}" itemLabel="descripcionTipoGarantia" itemValue="codigoTipoGarantia"/>
                                </form:select>
                                <br>
                                <form:errors path="modalidadGarantia" cssClass="error-message"/>								
                            </td>                                                        
                        </tr>                        
                        <tr>
                            <td class="etiqueta_black">Monto Garantía:</td>
                            <td class="etiqueta_black">Fecha de Pago (Garantía):</td>                            
                        </tr>                        
                        <tr>
                            <td class="campo">                                    
                                <form:input path="montoGarantiaFianza" cssClass="textbox" onKeyPress="return digitos (event)" onkeyup="puntitos(this,this.value.charAt(this.value.length-1))"/>
                                <br>
                                <form:errors path="montoGarantiaFianza" cssClass="error-message"/>								
                            </td>
                            <td class="campo">
                                <form:input path="fechaGarantiaFianza" id="fechaGarantiaFianza" cssClass="textbox" maxlength="10" readonly="true"/>
                                <br>
                                <form:errors path="fechaGarantiaFianza" cssClass="error-message"/>	                                
                            </td> 
                        </tr>
                        <tr>
                            <td class="etiqueta_black">Aliado Comercial:</td>  
                            <td class="etiqueta_black">Estatus:</td>
                        </tr>
                        <tr>
                            <td class="campo">
                                <c:choose>
                                    <c:when test = "${editACI}">
                                        <form:select path="codigoAliado" cssClass="select" multiple="false">
                                            <form:option value="" label="<--- Seleccione --->"/>
                                            <form:options items="${aliadosList}" itemLabel="ContactoNombreCompleto" itemValue="id" />
                                        </form:select>
                                        <br>
                                        <form:errors path="codigoAliado" cssClass="error-message"/>
                                    </c:when>
                                    <c:otherwise>
                                        <input id="codigoAliado" name="codigoAliado" type="hidden" value="${resultadoConsultaComercio.codigoAliado}"/>
                                        <form:input path="aliadoComercial" cssClass="textbox" readonly="true" cssStyle="width: 250px"/>
                                        <br>
                                        <form:errors path="aliadoComercial" cssClass="error-message"/>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="campo">                                    
                                <form:select path="estatusComercio" cssClass="select" multiple="false">                                                                        
                                  <form:options items="${estatusList}" itemLabel="descripcion" itemValue="id"/>
                                </form:select>
                                <br>
                                <form:errors path="estatusComercio" cssClass="error-message"/>								
                            </td>                            
                        </tr>
                        <tr>
                            <td class="etiqueta_black">¿Es Contribuyente Especial?:</td>   
                            <td class="etiqueta_black">¿Excluir del Archivo de Pagos?:</td>                                                      
                        </tr>
                        <tr>                           
                            <td class="campo">                                
                                <form:radiobutton path="pagaIVA" value="SI"/>SI
                                <form:radiobutton path="pagaIVA" value="NO"/>NO
                                <br>
                                <form:errors path="pagaIVA" cssClass="error-message"/>								
                            </td>   
                             <td class="campo">                                    
                                <form:radiobutton path="excluirArchivoPago" value="1"/>SI
                                <form:radiobutton path="excluirArchivoPago" value="0"/>NO
                                <br>
                                <form:errors path="excluirArchivoPago" cssClass="error-message"/>								
                            </td>                             
                        </tr>                                                                        
                        <tr>                               
                            <td class="etiqueta_black">% Comisión:</td>
                            <td class="etiqueta_black">Afiliados:</td>
                        </tr>
                        <tr>
                            <td class="campo">                                    
                                <form:input path="comisionComercio" value="${fn:replace(resultadoConsultaComercio.comisionComercio,'.0000', '')}" cssClass="textbox" onKeyPress="return digitos (event)" maxlength="3"/>
                                <br>
                                <form:errors path="comisionComercio" cssClass="error-message"/>								
                            </td>
                            <td class="campo">                                
                                <form:select multiple="true" path="afiliados" id="afiliados" cssClass="select">                                    
                                    <c:forEach items="${afiliadoslList}" var="afiliados">
                                        <c:set var="selected" value="1"/>
                                        <c:forEach items="${afiliadosComercio}" var="opcion">                                            
                                            <c:if test="${opcion.codigoAfiliado==afiliados.codigoAfiliado}">
                                                <c:set var="selected" value="0"/> 
                                            </c:if>  
                                        </c:forEach> 
                                        <c:choose>
                                            <c:when test="${selected==0}">
                                                <option value="${afiliados.codigoAfiliado}" selected="selected">${afiliados.descripcionAfiliado}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${afiliados.codigoAfiliado}">${afiliados.descripcionAfiliado}</option>
                                            </c:otherwise>
                                        </c:choose>                                         
                                    </c:forEach>                                                                                                           
                                </form:select>
                                <br>
                                <form:errors path="afiliados" cssClass="error-message"/>							
                            </td>                           
                        </tr>
                        
                        
                        <tr>
                            <td class="etiqueta_black"><strong>Recaudos:</strong></td>                            
                        </tr>                                                
                        <tr>                            
                            <td colspan="2" class="checkboxes">
                                <c:set var="recaudosCliente" value="${fn:split(resultadoConsultaComercio.recaudosComercio, ',')}" />
                                <c:forEach items="${recaudosList}" var="recaudosComercio">                                        
                                    <c:set var="checked" value=""/>                                         
                                    <c:forEach items="${recaudosCliente}" var="recaudo">                                        
                                        <c:if test="${recaudo==recaudosComercio.id}">
                                           <c:set var="checked" value="checked"/> 
                                        </c:if>                                            
                                    </c:forEach>
                                    <form:checkbox path="recaudosComercio" label="${recaudosComercio.descripcion}" id="${recaudosComercio.id}" value="${recaudosComercio.id}" checked="${checked}" />
                                    <br/>
                                </c:forEach>                                
                            </td>                                
                            <td>
                                <form:errors path="recaudosComercio" cssClass="error-message"/>
                            </td>
                        </tr>                        
                        <tr>
                            <td class="etiqueta_black">Dirección Fiscal</td>                                               
                        </tr>
                        <tr>
                            <td class="campo" colspan="2">                                    
                                <form:textarea path="direccionComercio" rows="3" cols="100" cssClass="textbox"/>
                                <br>
				<form:errors path="direccionComercio" cssClass="error-message"/>
                            </td>                           
                        </tr> 
                        <tr>
                            <td class="etiqueta_black">Dirección Habitación:</td>                                               
                        </tr>
                        <tr>
                            <td class="campo" colspan="2">                                    
                                <form:textarea path="direccionHabitacion" rows="3" cols="100" cssClass="textbox"/>
                                <br>
				<form:errors path="direccionHabitacion" cssClass="error-message"/>
                            </td>                           
                        </tr>                                                  
                        <tr>
                            <td class="etiqueta_black">Dirección Fisica del POS:</td>                                               
                        </tr>
                        <tr>
                            <td class="campo" colspan="2">                                    
                                <form:textarea path="direccionPos" rows="3" cols="100" cssClass="textbox"/>
                                <br>
				<form:errors path="direccionPos" cssClass="error-message"/>
                            </td>                           
                        </tr>                                                  
                        <tr>          
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
                                <a href="consultaComercio.htm"><img src="${pageContext.request.contextPath}/resources/img/volver.png" width='56' height='56'/></a>
                            </td>
                            <td></td>
                            <td class="botonera">                                
                                <input type="image" name="actualizaComercio" src="${pageContext.request.contextPath}/resources/img/actualizar.png" width='52' height='52'/>
                            </td>                            
                            <td></td>
                            <td>
                                <a href="listadoTerminalesComercio.htm?messageError=&codigoComercio=${resultadoConsultaComercio.codigoComercio}&codBancoCuentaAbono=${resultadoConsultaComercio.codigoBancoCuentaAbono}&numCuentaAbono=${resultadoConsultaComercio.numeroCuentaAbono}"><img src="${pageContext.request.contextPath}/resources/img/terminal.png" width='46' height='46'/></a>
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
