<%-- 
    Document   : EditarPlanPago
    Created on : 30-mar-2020, 16:03:55
    Author     : jperez@emsys-solutions.net
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
            var planes = JSON.parse('${editarPlanPago.jsonPlanes}');
            var submit_status = false;
            
            $(document).on( "submit", function(e) {
                
                if(!submit_status){
                    submit_status = true;
                    return true;
                }else{
                    e.preventDefault();
                }
	    });
            
            $(document).on('click','.volver',function(e){
                if(!submit_status){
                    return true;
                }else{
                    e.preventDefault();
                }
            });
            
            $(document).keydown(function(e) {
                //para prevenir que usen alguna tecla especial, como el F5
		switch(e.keyCode) {
                    case 116://F5
                        e.preventDefault();
                        break;
		}
            });
            $(document).on('change','#idplan',function(e){
                $('#mensajeMonto').text('');
                var encontrado =  false;
                $.each(planes, function (i, item) {
                    if(item.id == $('#idplan').val()){
                        $('#tipoPlan').val(item.descTipoPlan);
                        $('#codTipoPlan').val(item.codtipoplan);
                        $('#codTipoPlan').change();
                        if(item.codtipoplan == 2){
                            var idPlanActivo = null;
                            var idPlanPlanActivo = null;
                            $.each(planesActivos,function(j, pa){
                                $.each(planes, function (x, plan) {
                                    if((plan.codtipoplan == 2)&&(plan.id == pa.idplan)){
                                        idPlanActivo = pa.id;
                                        idPlanPlanActivo = plan.id
                                    }
                                });
                            });
                        }
                        if((item.codtipoplan == 2)&&(idPlanPlanActivo == item.id)){
                            if(!montoCuotasCobradas[idPlanActivo]){
                                $('#montoTarifa').val(item.montoTarifa.toFixed(2));
                            }else{
                                $('#montoTarifa').val((item.montoTarifa - montoCuotasCobradas[idPlanActivo]).toFixed(2));
                                $('#mensajeMonto').text('NOTA IMPORTANTE: Se Descuenta ' + (montoCuotasCobradas[idPlanActivo]).toFixed(2) + ' del Monto Base del Plan (' + item.montoTarifa + ') Proveniente de Cuotas Pagadas del Plan de Equipo Activo.');
                            }
                        }else{
                            $('#montoTarifa').val(item.montoTarifa.toFixed(2));
                        }
                        $('#montoInicial').val((item.montoInicial)?item.montoInicial.toFixed(2):0.00);
                        if(item.montoFijo)
                            $("input[name=montoFijo]").prop("checked",true);
                        else
                            $("input[name=montoFijo]").prop("checked",false);
                        $('#porcComisionBancaria').val((item.porcComisionBancaria)?item.porcComisionBancaria.toFixed(2):0.00);
                        $('#moneda').val(item.moneda);
                        
                        $('#frecuencia').val(item.frecuencia);
                        encontrado = true;
                        if(item.tipoPagoMantenimiento == "porcComisionBancaria"){
                            $('input[name=tipoPagoMantenimiento][value=porcComisionBancaria]').prop("checked",true);
                        }else{
                            $('input[name=tipoPagoMantenimiento][value=montoTarifa]').prop("checked",true);
                        }
                        
                        
                    }
                });
                if (!encontrado){
                    $('#montoTarifa').val(0);
                    $('#montoInicial').val(0);
                    $('#porcComisionBancaria').val(0);
                    $("input[name=montoFijo]").prop("checked",false);
                    $('#frecuencia').val(0);
                    $('#tipoPlan').val('');
                }
                $('#montoTarifa').keyup();
                $('#porcComisionBancaria').keyup();
                $('#montoInicial').keyup();
                $('input[name=tipoPagoMantenimiento]:checked').change();
            });
            
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
                        /*
                        $("input[name=montoTarifa]").prop("readonly",true);
                        $("input[name=montoFijo]").prop("disabled",true);
                        $("input[name=porcComisionBancaria]").prop("readonly",false);
                        $("input[name=montoFijo]").prop("checked",false);
                        $("input[name=montoTarifa]").val("");
                        */
                        $(".monto-tarifa-td").hide();
                        $(".monto-fijo-td").hide();
                        $(".procentaje-td").show();
                        $("input[name=montoTarifa]").val("");
                        break;
                    case "montoTarifa":
                        /*
                        $("input[name=montoTarifa]").prop("readonly",false);
                        $("input[name=montoFijo]").prop("disabled",false);
                        $("input[name=porcComisionBancaria]").prop("readonly",true);
                        $("input[name=porcComisionBancaria]").val("");
                        */
                        $(".monto-tarifa-td").show();
                        $(".monto-fijo-td").show();
                        $(".procentaje-td").hide();
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
            
            $(document).ready(function(e){
                $('#nombrePlan').val(planes.planNombre); 
                $('#tipoPlan').val(planes.descTipoPlan);
                
                $("#montoTarifa").keyup();
                $("#montoInicial").keyup();
                $("#porcComisionBancaria").keyup();
                $('#codtipoplan').change();
                $('input[name=tipoPagoMantenimiento]:checked').change();
                $("input[name=montoTarifa]").prop("readonly",true);
                $("input[name=montoFijo]").prop("disabled",true);
                $("input[name=porcComisionBancaria]").prop("readonly",true);
                //$("input[name=frecuencia]").prop("readonly",true);
            });
        </script> 
        <br>
        <div class="row">
            <div id="header-form">
                <h1>Formulario para modificar un Plan de Pago</h1>
            </div>
            <label class="error-message" style="align-content: center">${model.messageError}</label>
            <div id="box-form">
                <form:form method="post" autocomplete="off" commandName="editarPlanPago">
                    
                    <div class="contenedor_formulario">
                      <table class="formulario">  
                        <tr>
                            <td class="etiqueta_black" style="width: 200px;">Código Terminal:</td>
                            <td class="campo" style="width: 300px;">
                                <form:input path="codigoTerminal" cssClass="textbox" maxlength="8" readonly="true"/>   
                                <form:hidden path="codigoAfiliado"/>   
                                <form:hidden path="codigoComercio"/>                                    
                                <form:input type="hidden" path="jsonPlanes"/>
                                <form:input type="hidden" path="id"/>
                            </td>        
                        </tr>
                        <tr>
                            <td colspan="2">
                            </td>
                        </tr>
                        <tr>
                            <td class="etiqueta_black">Plan:</td>
                        
                            <td class="campo">
                                <form:input type="hidden" path="idplan"/>
                                <input id="nombrePlan" name="nombrePlan" class="textbox" readonly="true"/>
                            </td>
                        </tr> 
                        <tr>
                            <td colspan="2">
                                <form:errors path="idplan" cssClass="error-message"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="etiqueta_black">Tipo de Plan:</td>
                        
                            <td class="campo">   
                                <input class="textbox" id="tipoPlan" name="tipoPlan" readonly="true"/> 
                                <form:hidden path="codtipoplan"/> 
                            </td>
                        </tr> 
                        
                        <tr>
                            <td colspan="2">
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
                                <form:radiobutton path="tipoPagoMantenimiento" value="montoTarifa" checked="checked"/>
                                Monto Tarifa:</td>
                            <td class="campo">
                                 <form:input path="montoTarifa" cssClass="textbox" onKeyPress="return digitos (event)" onkeyup="puntitos(this,this.value.charAt(this.value.length-1))"/> 
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
                        <tr class="tipoMantenimiento tipoUso">
                            <td class="etiqueta_black">Monto Fijo:</td>  
                            <td class="campo">                                                                    
                                <form:checkbox path="montoFijo"/>
                                <br>
                                <form:errors path="montoFijo" cssClass="error-message"/>
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
                        
                        <tr>
                            <td class="etiqueta_black">Frecuencia:</td>
                        
                            <td class="campo">
                                <form:select path="frecuencia" cssClass="select" multiple="false">   
                                    <form:option value="0" label="Seleccione"/>
                                    <form:options items="${FrecuenciaList}" itemLabel="descripcion" itemValue="id"/>
                                </form:select>	
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
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
                            <td></td>
                            <td></td>
                             <td>
                               <a class="volver" href="listadoTerminalPlanes.htm?messageError=&codigoTerminal=${editarPlanPago.codigoTerminal}"><img src="${pageContext.request.contextPath}/resources/img/volver.png" class="volver" width='56' height='56'/></a>                                
                            </td>
                            <td class="botonera" colspan="2">                                    
                                <input type="image" name="editarPlanPago" id="crearPlanPagoButton" src="${pageContext.request.contextPath}/resources/img/guardar.png" width='52' height='52'/>
                            </td>
                        </tr>
                      </table>                    
                    </div>  
                </form:form>
            </div>
        </div>        
    </body>
</html>