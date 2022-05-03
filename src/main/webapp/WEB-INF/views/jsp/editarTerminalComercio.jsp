<%-- 
    Document   : editarTerminalComercio
    Created on : 20-abr-2017
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
            const pagoContadoInicial = '${editarTerminalComercio.pagoContadoBD}';
            const poseePlanEquipo = '${editarTerminalComercio.poseePlanEquipo}';
            console.log(poseePlanEquipo)
            $(document).on('change','#pagoContado',function(e){
                if(pagoContadoInicial === '0' && poseePlanEquipo === 'false'){
                    if($('#pagoContado').val() == "1"){//equipo
                        $('.pagoContado').show();
                    }else{
                        $('.pagoContado').hide();
                        //inicializar valores
                        $("#fechaPago").val("");
                        $("#montoEquipoUSD").val(0);
                        $("#montoEquipoBs").val(0);
                        $("#ivaEquipoBs").val(0);
                        $("#montoTotalEquipoBs").val(0);
                        $("#montoEquipoUSD").keyup();
                        $("#montoEquipoBs").keyup();
                        $("#ivaEquipoBs").keyup();
                        $("#montoTotalEquipoBs").keyup();
                    }
                }else{
                    $('#pagoContado').val(pagoContadoInicial);
                }
            });
            jQuery(function(){
                
                jQuery('#fechafin').datetimepicker({
                     format:'d-m-Y',
                     onShow:function( ct ){
                       this.setOptions({                
                           minDate:jQuery('#fechainicio').val()?jQuery('#fechainicio').val().substr(6,4) + "/" + jQuery('#fechainicio').val().substr(3,2) + "/" + jQuery('#fechainicio').val().substr(0,2):false                            
                       });
                     },
                     timepicker:false
                });
                jQuery('#indefinido').click(function() {                     
                     if($("#indefinido").prop('checked')!=true){                        
                        $(".indefinido").show();
                     }else{
                        $(".indefinido").hide();
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
            
            // A $( document ).ready() block.
            $( document ).ready(function() {
                $("#montoEquipoUSD").keyup();
                $("#montoEquipoBs").keyup();
                $("#ivaEquipoBs").keyup();
                $("#montoTotalEquipoBs").keyup();
                
                if($('#pagoContado').val() == "1"){//equipo
                    $(".pagoContado").show();
                    $("#montoEquipoUSD").prop("readonly",true);
                    $("#montoEquipoBs").prop("readonly",true);
                    $("#ivaEquipoBs").prop("readonly",true);
                    $("#montoTotalEquipoBs").prop("readonly",true);
                }else{
                    $(".pagoContado").hide();
                }
                if(pagoContadoInicial === '0'){
                    jQuery('#fechaPago').datetimepicker({
                        format:'d-m-Y',
                        timepicker:false
                    });
                }
                
                
            });
        </script> 
        <br>
        <div class="row">
            <div id="header-form">
                <h1>Formulario para modificar un Terminal</h1>
            </div>
            <label class="error-message" style="align-content: center">${model.messageError}</label>
            <div id="box-form">
                <form:form method="post" autocomplete="off" commandName="editarTerminalComercio">
                    <div class="contenedor_formulario">
                      <table class="formulario">
                        <tr>
                            <td class="etiqueta_black">Afiliado:</td>
                            <td class="campo">
                                <input id="id" name="id" type="hidden" value="${editarTerminalComercio.id}"/>
                                <form:hidden path="poseePlanEquipo" />
                                <input id="codigoComercio" name="codigoComercio" type="hidden" value="${editarTerminalComercio.codigoComercio}"/>
                                <form:select path="codigoAfiliado" cssClass="select" multiple="false">
                                  <form:option value="" label="<--- Seleccione --->"/>
                                  <form:options items="${afiliadoslList}" itemLabel="descripcionAfiliado" itemValue="codigoAfiliado"/>
                                </form:select>                                                               
                            </td>        
                        </tr>
                        <tr>
                            <td colspan="2">
                                <form:errors path="codigoAfiliado" cssClass="error-message"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="etiqueta_black">Código Terminal:</td>
                            <td class="campo">                                
                                <form:input path="codigoTerminal" cssClass="textbox" maxlength="8"/>                                    
                            </td>        
                        </tr>
                        <tr>
                            <td colspan="2">
                                <form:errors path="codigoTerminal" cssClass="error-message"/>
                            </td>
                        </tr>
                          <tr>
                              <td class="etiqueta_black"><b>Informacion Bancaria</b></td>
                              <td class="campo">
                                  &emsp;&ensp;
                              </td>
                          </tr>
                          <tr>
                              <td class="etiqueta_black">Banco:</td>
                              <td class="campo">
                                  <form:select path="codigoBancoCuentaAbono" cssClass="select" multiple="false" value="${editarTerminalComercio.codigoBancoCuentaAbono}">
                                      <form:option value="" label="<--- Seleccione --->"/>
                                      <form:options items="${bancoList}" itemLabel="descripcion" itemValue="codigo"/>
                                  </form:select>
                              </td>
                          </tr>
                          <tr>
                              <td colspan="2">
                                  <form:errors path="codigoBancoCuentaAbono" cssClass="error-message"/>
                              </td>
                          </tr>
                          <tr>
                              <td class="etiqueta_black">Cuenta tipo:</td>
                              <td class="campo">
                                  <form:select path="tipoCuentaAbono" cssClass="select" multiple="false" value="${editarTerminalComercio.tipoCuentaAbono}">
                                      <form:option value="" label="<--- Seleccione --->"/>
                                      <form:option value="01" label="Corriente"/>
                                      <form:option value="02" label="Ahorro"/>
                                  </form:select>
                              </td>
                          </tr>
                          <tr>
                              <td colspan="2">
                                  <form:errors path="tipoCuentaAbono" cssClass="error-message"/>
                              </td>
                          </tr>

                          <tr>
                              <td class="etiqueta_black">Cuenta:</td>
                              <td class="campo">
                                  <form:input path="numeroCuentaAbono" cssClass="textbox" type="text" maxlength="20" value="${editarTerminalComercio.numeroCuentaAbono}"/>
                              </td>
                          </tr>
                          <tr>
                              <td colspan="2">
                                  <form:errors path="numeroCuentaAbono" cssClass="error-message"/>
                              </td>
                          </tr>
                        <tr>
                            <td class="etiqueta_black">Estatus:</td>
                           </td>
                            <td class="campo">                                    
                                <form:select path="estatus" cssClass="select" multiple="false">                                                                        
                                  <form:options items="${TermEstatusList}" itemLabel="descripcion" itemValue="id"/>
                                </form:select>
                                <br>
                                <form:errors path="estatus" cssClass="error-message"/>								
                            </td>
                         </tr>
                        <tr>
                             <td class="etiqueta_black"><b>Otros Datos</b></td>
                             <td class="campo">
                                 &emsp;&ensp;
                             </td>
                        </tr>
                        <tr>
                            <td class="etiqueta_black">Pago de Contado:</td>
                            </td>
                            <td class="campo">                                    
                                <form:select path="pagoContado" cssClass="select" multiple="false">                                                                        
                                    <form:options items="${TermPagoContadoList}"/>
                                </form:select>
                                <form:hidden path="pagoContadoBD"/>
                                <br>
                                <form:errors path="estatus" cssClass="error-message"/>								
                            </td>
                        </tr>
                        <tr class="pagoContado">
                            <td class="etiqueta_black">Fecha de Pago de Contado:</td>
                            </td>
                            <td class="campo">                                    
                                <form:input path="fechaPago" cssClass="textbox" maxlength="10" readonly="true"/>
                        
                                <br>
                                <form:errors path="fechaPago" cssClass="error-message"/>								
                            </td>
                        </tr>
                        <tr class="pagoContado">
                            <td class="etiqueta_black">Monto Equipo USD:</td>
                            </td>
                            <td class="campo">                                    
                                <form:input path="montoEquipoUSD" cssClass="textbox"  onKeyPress="return digitos (event)" onkeyup="puntitos(this,this.value.charAt(this.value.length-1))"/>
                        
                                <br>
                                <form:errors path="montoEquipoUSD" cssClass="error-message"/>								
                            </td>
                        </tr>
                        <tr class="pagoContado">
                            <td class="etiqueta_black">Monto Equipo Bs:</td>
                            </td>
                            <td class="campo">                                    
                                <form:input path="montoEquipoBs" cssClass="textbox"  onKeyPress="return digitos (event)" onkeyup="puntitos(this,this.value.charAt(this.value.length-1))"/>
                        
                                <br>
                                <form:errors path="montoEquipoBs" cssClass="error-message"/>								
                            </td>
                        </tr>
                        <tr class="pagoContado">
                            <td class="etiqueta_black">Monto Iva Equipo Bs:</td>
                            </td>
                            <td class="campo">                                    
                                <form:input path="ivaEquipoBs" cssClass="textbox"  onKeyPress="return digitos (event)" onkeyup="puntitos(this,this.value.charAt(this.value.length-1))"/>
                        
                                <br>
                                <form:errors path="ivaEquipoBs" cssClass="error-message"/>								
                            </td>
                        </tr>
                        <tr class="pagoContado">
                            <td class="etiqueta_black">Monto Total Equipo Bs:</td>
                            </td>
                            <td class="campo">                                    
                                <form:input path="montoTotalEquipoBs" cssClass="textbox"  onKeyPress="return digitos (event)" onkeyup="puntitos(this,this.value.charAt(this.value.length-1))"/>
                        
                                <br>
                                <form:errors path="montoTotalEquipoBs" cssClass="error-message"/>								
                            </td>
                        </tr>
                        <tr>
                            <td class="etiqueta_black"><b>Informacion de Planes</b></td>
                            <td class="campo">
                                &emsp;&ensp;
                            </td>
                        </tr>
                         <tr>
                            <td class="etiqueta_black">Planes:</td>                  
                            <td>                                            
                              <a href="listadoTerminalPlanes.htm?messageError=&codigoTerminal=${editarTerminalComercio.codigoTerminal}"><img src="${pageContext.request.contextPath}/resources/img/plan.jpeg" width='46' height='46'/></a>                    
                            </td>        
                        </tr>
                          <tr>
                            <td></td>
                            <td></td>
                            <td>
                                <a href="listadoTerminalesComercio.htm?messageError=&codigoComercio=${editarTerminalComercio.codigoComercio}&codBancoCuentaAbono=${editarTerminalComercio.codigoBancoCuentaAbono}&numCuentaAbono=${editarTerminalComercio.numeroCuentaAbono}"><img src="${pageContext.request.contextPath}/resources/img/volver.png" width='56' height='56'/></a>
                            </td>
                            <td>                                    
                                <input type="image" name="actualizarTerminal" src="${pageContext.request.contextPath}/resources/img/actualizar.png" width='52' height='52'/>
                            </td>
                        </tr>
                      </table>                    
                    </div>  
                </form:form>
            </div>
        </div>        
    </body>
</html>
