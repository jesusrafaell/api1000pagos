<%-- 
    Document   : EliminarPlanPago
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
        </script> 
        <br>
        <div class="row">
            <div id="header-form">
                <h1>Confirme la información del Plan Pago de Cobranza a eliminar</h1>
            </div>
            <label class="error-message" style="align-content: center">${model.messageError}</label>
            <div id="box-form">
                <form:form method="post" autocomplete="off" commandName="eliminarPlanPago">
                   <div class="contenedor_formulario">
                      <table class="formulario">                    
                           <tr>
                            <td class="etiqueta_black">Plan:</td>
                            <br> 
                              <td class="etiqueta_black">Monto Tarifa:</td>
                            </tr>
                           <tr>
                            <td class="campo">
                                <form:input path="codigoComercio" cssClass="textbox" size="15" type="hidden"/> 
                                <form:input path="codigoAfiliado" cssClass="textbox" size="15" type="hidden"/> 
                                <form:input path="codigoTerminal" cssClass="textbox" size="15" type="hidden"/> 
                                <form:select path="idplan" cssClass="select" multiple="false">                                      
                                    <form:options items="${PlanTerminalList}" itemLabel="nombre" itemValue="id"/>
                                </form:select>
                            </td>
                            <td class="campo">   
                                 <form:input path="montoTarifa" cssClass="textbox" size="15"/> 
                                 <br>
                                  <form:errors path="montoTarifa" cssClass="error-message"/>
                               </td>
                            </tr>    
                        <tr>
                            <td class="etiqueta_black">Frecuencia:</td>
                            <td class="etiqueta_black">Estatus:</td>
                        </tr>
                        <tr>
                            <td class="campo">
                                <form:select path="frecuencia" cssClass="select" multiple="false">                                      
                                    <form:options items="${FrecuenciaList}" itemLabel="descripcion" itemValue="id"/>
                                </form:select>
                                 <br>
                              <form:errors path="frecuencia" cssClass="error-message"/>	
                            </td>
                            <td class="campo">                                    
                                <form:select path="estatus" cssClass="select" multiple="false">                                                                        
                                  <form:options items="${estatusList}" itemLabel="descripcion" itemValue="id"/>
                                </form:select>
                                <br>
                                <form:errors path="estatus" cssClass="error-message"/>								
                            </td>
                        </tr>
                               </tr>  
                         <tr id="fechas">
                            <td class="etiqueta_black">Fecha Inicio:</td>
                            <td class="etiqueta_black">Fecha Fin:</td>	
                        </tr>
                           <tr id="fechascampos">
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
                            <td></td>
                            <td></td>
                            <td>
                               <a href="listadoTerminalPlanes.htm?messageError=&codigoTerminal=${eliminarPlanPago.codigoTerminal}"><img src="${pageContext.request.contextPath}/resources/img/volver.png" width='56' height='56'/></a>                                  
                            </td>
                            <td class="botonera" colspan="2">                                    
                                <input type="image" name="eliminarPlanPago" src="${pageContext.request.contextPath}/resources/img/guardar.png" width='52' height='52'/>
                            </td>
                        </tr>
                      </table>                    
                    </div>  
                </form:form>
            </div>
        </div>        
    </body>
</html>