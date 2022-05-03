<%-- 
    Document   : CrearPlan
    Created on : 30-mar-2020, 16:03:55
    Author     : jperez@emsys-solutions.net
    Update  on : 10-oct-2020
    Updated    : mcaraballo@emsys-solutions.net
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
            $(document).on('change','#codtipoplan',function(e){
                switch ($('#codtipoplan').val()) {
                    case "1"://
                        $('.plazo').hide();
                        $(".tipoEquipo").hide();
                        $(".tipoUso").hide();
                        $(".tipoMantenimiento").show();
                        $("input[name=tipoPagoMantenimiento]").hide();
                        break;
                    case "2"://
                        $('.plazo').show();
                        $(".tipoEquipo").show();
                        $(".tipoUso").hide();
                        $(".tipoMantenimiento").hide();
                        $("input[name=tipoPagoMantenimiento]").hide();
                        break;
                    default:
                        $('.plazo').hide();
                        $(".tipoEquipo").hide();
                        $(".tipoMantenimiento").hide();
                        $(".tipoUso").show();
                        $("input[name=tipoPagoMantenimiento]").show();
                }
                
            });
            $(document).on('change','input[name=tipoPagoMantenimiento]',function(e){
                switch (this.value){
                    case "porcComisionBancaria":
                        $("input[name=montoTarifa]").prop("readonly",true);
                        $("input[name=montoFijo]").prop("disabled",true);
                        $("input[name=porcComisionBancaria]").prop("readonly",false);
                        $("input[name=montoFijo]").prop("checked",false);
                        $("input[name=montoTarifa]").val("");
                        break;
                    case "montoTarifa":
                        $("input[name=montoTarifa]").prop("readonly",false);
                        $("input[name=montoFijo]").prop("disabled",false);
                        $("input[name=porcComisionBancaria]").prop("readonly",true);
                        $("input[name=porcComisionBancaria]").val("");
                        break;
                }
            });
            jQuery(function(){
                jQuery('#fechainicio').datetimepicker({
                      format:'d-m-Y',
                      onShow:function( ct ){
                        this.setOptions({                
                            maxDate:jQuery('#fechafin').val()?jQuery('#fechafin').val().substr(6,4) + "/" + jQuery('#fechafin').val().substr(3,2) + "/" + jQuery('#fechafin').val().substr(0,2):false                            
                        });
                      },
                      timepicker:false
                });
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
                if($("#indefinido").val()){  
                    $(".indefinido").hide();
                    $("#indefinido").prop("checked","checked");
                }else{
                    $(".indefinido").show();
                }
                $("#montoTarifa").keyup();
                $("#montopromedio").keyup();
                $('#codtipoplan').change();
                $("#moneda").val(2);
                $("input[name=nombre]").focus();
                $('input[name=tipoPagoMantenimiento]:checked').change();
            });
        </script> 
        <br>
        <div class="row">
            <div id="header-form">
                <h1>Formulario para crear un Plan de Cobranza</h1>
            </div>
            <label class="error-message" style="align-content: center">${model.messageError}</label>
            <div id="box-form">
                <form:form method="post" autocomplete="off" commandName="crearPlan">
                    <div class="contenedor_formulario">
                      <table class="formulario">      
                          <tr>
                             <td class="etiqueta_black"><b>Datos Basicos</b></td>
                             <td class="campo">
                                 &emsp;&ensp;
                             </td>
                        </tr>
                        <tr>
                            <td class="etiqueta_black">Tipo Plan:</td>
                            <td class="campo">
                                <form:select path="codtipoplan" cssClass="select" multiple="false">                                      
                                    <form:options items="${tipoPlanList}" itemLabel="descripcion" itemValue="codigoTipoPlan"/>
                                </form:select>
                                
                            </td>
                        </tr>
                        <tr>
                            <td class="etiqueta_black">Nombre Plan:</td>
                            <td class="campo">
                                 <form:input path="nombre" cssClass="textbox" size="50"/> 
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <form:errors path="nombre" cssClass="error-message"/>
                            </td>
                        </tr>  
                        <tr>
                            <td class="etiqueta_black">Descripción:</td>
                            <td class="campo">                   
                                 <form:textarea path="descripcion" rows="3" cols="50" cssClass="textbox"/>  
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <form:errors path="descripcion" cssClass="error-message"/>
                            </td>
                        </tr> 
                        <tr>
                             <td class="etiqueta_black"><b>Modelo de Pago</b></td>
                             <td class="campo">
                                 &emsp;&ensp;
                             </td>
                        </tr>
                        <tr>
                            <td class="etiqueta_black">
                                <form:radiobutton path="tipoPagoMantenimiento" value="montoTarifa" checked="checked"/>Monto Tarifa:</td>
                            <td class="campo">
                                 <form:input path="montoTarifa" cssClass="textbox"  onKeyPress="return digitos (event)" onkeyup="puntitos(this,this.value.charAt(this.value.length-1))"/> 
                            <br>
                                  <form:errors path="montoTarifa" cssClass="error-message"/>
                            </td>
                        </tr>
                        <tr class="tipoEquipo">
                            <td class="etiqueta_black">Monto Inicial:</td>
                            <td class="campo">
                                <form:input path="montoInicial" cssClass="textbox"  onKeyPress="return digitos (event)" onkeyup="puntitos(this,this.value.charAt(this.value.length-1))"/> 
                            <br>
                                <form:errors path="montoInicial" cssClass="error-message"/>
                            </td>
                        </tr>
                        <tr class="tipoUso tipoMantenimiento">
                            <td class="etiqueta_black">Monto Fijo:</td>  
                            <td class="campo">                                                                    
                                <form:checkbox path="montoFijo"/>
                                <br>
                                <form:errors path="montoFijo" cssClass="error-message"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="etiqueta_black">Moneda:</td>
                            <td class="campo">
                                <form:select path="moneda" cssClass="select" multiple="false">                                      
                                    <form:options items="${MonedaList}" itemLabel="descripcion" itemValue="id"/>
                                </form:select>
                              <br>
                              <form:errors path="moneda" cssClass="error-message"/>	
                            </td>
                        </tr>
                        <tr class="tipoUso">
                            <td class="etiqueta_black">
                                <form:radiobutton path="tipoPagoMantenimiento" value="porcComisionBancaria"/>
                                Porcentaje Comision Bancaria:</td>
                            <td class="campo">
                                <form:input path="porcComisionBancaria" cssClass="textbox"  onKeyPress="return digitos (event)" onkeyup="puntitos(this,this.value.charAt(this.value.length-1))"/> 
                            <br>
                                <form:errors path="porcComisionBancaria" cssClass="error-message"/>
                            </td>
                        </tr>
                        
                        <tr class="plazo" >
                            <td class="etiqueta_black">Plazo:</td>
                            </td>
                            <td class="campo">                                    
                                <form:input type="number" min="0" path="planplazo" cssClass="textbox"/> 
                                 <br>
                                <form:errors path="planplazo" cssClass="error-message"/>							
                            </td>
                        </tr>
                        <tr>
                            <td class="etiqueta_black">Frecuencia:</td>
                            </td>
                            <td class="campo">                                    
                                <form:select path="frecuencia" cssClass="select" multiple="false">                                      
                                    <form:options items="${FrecuenciaList}" itemLabel="descripcion" itemValue="id"/>
                                </form:select>
                                 <br>
                                <form:errors path="frecuencia" cssClass="error-message"/>								
                            </td>
                        </tr>
                        <tr>
                             <td class="etiqueta_black"><b>Otros Datos</b></td>
                             <td class="campo">
                                 &emsp;&ensp;
                             </td>
                        </tr>
                        <tr>
                            <td class="etiqueta_black">Tiempo Indefinido:</td>  
                            <td class="campo">                                                                    
                                <form:checkbox path="indefinido" label="" id="indefinido" value="indefinido"/>
                                <br>
                                <form:errors path="indefinido" cssClass="error-message"/>
                            </td>
                        </tr>
                        <tr id="fechas" class="indefinido">
                            <td class="etiqueta_black">Fecha Inicio:</td>
                            <td class="etiqueta_black">Fecha Fin:</td>	
                        </tr>
                        <tr id="fechascampos"  class="indefinido">
                            <td class="campo">                                    
                                <form:input path="fechainicio" id="fechainicio" cssClass="textbox" maxlength="10" readonly="true"/>
                                <br>
                                <form:errors path="fechainicio" cssClass="error-message"/>								
                            </td>
                            <td class="campo">                                    
                                <form:input path="fechafin"  id="fechafin" cssClass="textbox" maxlength="10" readonly="true"/>
                                <br>
                                <form:errors path="fechafin" cssClass="error-message"/>								
                            </td>
                           
                        </tr>                               
                        <tr>
                            <td class="etiqueta_black">Estatus:</td>
                           </td>
                            <td class="campo">                                    
                                <form:select path="estatus" cssClass="select" multiple="false">                                                                        
                                  <form:options items="${estatusList}" itemLabel="descripcion" itemValue="id"/>
                                </form:select>
                                <br>
                                <form:errors path="estatus" cssClass="error-message"/>								
                            </td>
                         </tr>
                        <tr>
                            <td class="etiqueta_black">Monto Promedio:</td>
                            <td class="etiqueta_black">Cantidad Transacciones:</td>
                        </tr>
                        <tr>
                            <td class="campo">
                                 <form:input path="montopromedio" cssClass="textbox"  onKeyPress="return digitos (event)" onkeyup="puntitos(this,this.value.charAt(this.value.length-1))"/> 
                                 <br>
                                 <form:errors path="montopromedio" cssClass="error-message"/>
                            </td>
                            <td class="campo">
                                 <form:input type="number" min="0" path="transaccion" cssClass="textbox" size="20"/> 
                                  <br>
                                 <form:errors path="transaccion" cssClass="error-message"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="etiqueta_black">Cantidad Dias Impago:</td>
                            <td class="etiqueta_black">Porcentaje Impago:</td> 
                        </tr>
                        <tr>
                             <td class="campo">
                                 <form:input type="number" min="0" path="diasimpago" cssClass="textbox" size="20"/> 
                                 <br>
                                 <form:errors path="diasimpago" cssClass="error-message"/>
                            </td>
                           <td class="campo">
                                 <form:input type="number" min="0" max="100" path="porcentaje" cssClass="textbox"/> 
                                 <br>
                                 <form:errors path="porcentaje" cssClass="error-message"/>
                            </td>
                        </tr>   
                        <tr>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td class="botonera" colspan="2">                                    
                                <input type="image" name="crearPlan" src="${pageContext.request.contextPath}/resources/img/guardar.png" width='52' height='52'/>
                            </td>
                        </tr>
                      </table>                    
                    </div>  
                </form:form>
            </div>
        </div>        
    </body>
</html>
<script type="text/javascript">
    
</script> 