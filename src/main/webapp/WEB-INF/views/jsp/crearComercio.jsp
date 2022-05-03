<%-- 
    Document   : crearComercio
    Created on : 25-nov-2016
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
        <script src="${pageContext.request.contextPath}/resources/js/rainbow-custom.min.js"></script>
        <script type="text/javascript">
            
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
            <label class="error-message" style="align-content: center">${model.messageError}</label>
            <div id="box-form">
                <form:form method="post" autocomplete="off" commandName="crearComercio">
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
                                <form:select path="codigoTipoContrato" id="codigoTipoContrato" cssClass="select" multiple="false">
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
                                <form:checkboxes items="${diasList}" path="diasOperacion" delimiter=" "/>
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
                                <form:input path="numeroCuentaAbono" cssClass="textbox" cssStyle="width: 250px" maxlength="20" onKeyPress="return digitos (event)"/>
                                <br>
                                <form:errors path="numeroCuentaAbono" cssClass="error-message"/>								
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
                            <td class="etiqueta_black">Punto Adicional:</td>
                            <c:if test="${model.existeAliado=='0'}">
                                <td class="etiqueta_black">Aliado Comercial:</td>
                            </c:if>    
                        </tr>                        
                        <tr>
                            <td class="campo">                                                                    
                                <form:checkbox path="puntoAdicional" label="" id="puntoAdicional" value="1"/>
                                <br>
                                <form:errors path="puntoAdicional" cssClass="error-message"/>
                            </td>
                            <c:if test="${model.existeAliado=='0'}">
                                <td class="campo">
                                    <form:select path="codigoAliado" cssClass="select" multiple="false">
                                        <form:option value="" label="<--- Seleccione --->"/>
                                        <form:options items="${aliadosList}" itemLabel="ContactoNombreCompleto" itemValue="id" />                                        
                                    </form:select>
                                    <br>
                                    <form:errors path="codigoAliado" cssClass="error-message"/>
                                </td> 
                            </c:if>   
                        </tr>                        
                        <tr id="cuentas" style="display: none">
                            <td class="etiqueta_black">Banco Cuenta Abono Punto Adicional 2:</td>
                            <td class="etiqueta_black">Cuenta Abono Punto Adicional 2:</td>                            							                                                          
                        </tr>                        
                        <tr id="cuentasCampos" style="display: none">
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
                        <tr id="cuentas2" style="display: none">
                            <td class="etiqueta_black">Banco Cuenta Abono Punto Adicional 3:</td>
                            <td class="etiqueta_black">Cuenta Abono Punto Adicional 3:</td>                            							                                                          
                        </tr>                        
                        <tr id="cuentasCampos2" style="display: none">
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
                            <td class="etiqueta_black">¿Es Contribuyente Especial?:</td>
                            <td class="etiqueta_black">Afiliados:</td>
                        </tr>                        
                        <tr>                            
                            <td class="campo">                                
                                <form:radiobutton path="pagaIVA" value="SI"/>SI
                                <form:radiobutton path="pagaIVA" value="NO"/>NO
                                <br>
                                <form:errors path="pagaIVA" cssClass="error-message"/>								
                            </td>                                                                                   
                            <td class="campo">    
                                <form class="demo-example">
                                    <form:select multiple="true" path="afiliados" id="afiliados" cssClass="select">
                                        <form:options items="${afiliadoslList}" itemValue="codigoAfiliado" itemLabel="descripcionAfiliado"/>
                                    </form:select>
                                </form>
                                
                                <br>
                                <form:errors path="afiliados" cssClass="error-message"/>								
                            </td>
                        </tr>
                        <tr>
                            <td class="etiqueta_black"><strong>Recaudos:</strong></td>                            
                        </tr>                                                
                        <tr>
                            <td colspan="2" class="checkboxes">                                    
                                <form:checkboxes items="${recaudosList}" path="recaudosComercio" itemLabel="descripcion" itemValue="id" delimiter="<br/><br/>"/>
                            </td>
                            <td>
                                <form:errors path="recaudosComercio" cssClass="error-message"/>
                            </td>
                        </tr>                        
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
                            <td class="etiqueta_black">Observaciones (Opcional):</td>                    
                        </tr>
                        <tr>                                                        
                            <td class="campo" colspan="2">                                    
                                <form:textarea path="observacionesComercio" rows="3" cols="100" cssClass="textbox"/>
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="botonera" colspan="2">                                
                                <input type="image" name="crearComercio" src="${pageContext.request.contextPath}/resources/img/guardar.png" width='56' height='56'/>
                            </td>
                        </tr>
                      </table>                    
                    </div>  
                </form:form>
            </div>
        </div>
    </body>
</html>
