<%-- 
    Document   : generarCobranzasComercios
    Created on : 31/05/2017
    Author     : mggy@sistemasemsys.com
--%>
<%@ include file="/WEB-INF/views/jsp/include.jsp" %>
<html>
<head>    
</head>
<body>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/utils.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.1.0.js"></script>        
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.blockUI.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/rainbow-custom.min.js"></script>
    <script type="text/javascript">
        
        var contexPath = "<%=request.getContextPath() %>";
        
        jQuery(function(){

            jQuery('input[name="tipoConsulta"]').click(function() {                 
                if($("#tipoConsulta:checked").val()==1){      
                    //$("#terminal").prop( "disabled", false );    
                    $(".ci").prop( "disabled", false );  
                     
                    //setea las variables
                    $("#tipoIdentificacionComercio").val("V");
                    $("#identificacionComercio").val("");
                    $("#tipoIdentificacionACI").val("V");
                    $("#identificacionACI").val("");
                    $("#terminal").val("");        
                    
                    //habilita las opciones
                    switch($("#tipoConsultaIndividual:checked").val()) {
                        case "0":
                            // code block
                            $(".ci-comercio").prop( "disabled", false ); 
                            $(".ci-terminal").prop( "disabled", true );
                            $(".ci-aci").prop( "disabled", true );               
                            break;
                        case "1":
                            // code block
                            $(".ci-comercio").prop( "disabled", true ); 
                            $(".ci-terminal").prop( "disabled", false );
                            $(".ci-aci").prop( "disabled", true ); 
                            break;
                        case "2":
                            // code block
                            $(".ci-comercio").prop( "disabled", true ); 
                            $(".ci-terminal").prop( "disabled", true );
                            $(".ci-aci").prop( "disabled", false ); 
                            break;
                        default:
                            // code block
                            $(".ci-comercio").prop( "disabled", true ); 
                            $(".ci-terminal").prop( "disabled", true );
                            $(".ci-aci").prop( "disabled", true ); 
                    }
                    
                }else{
                    //$("#terminal").prop( "disabled", true );                    
                    //$("#terminal").val("");
                    $(".ci").prop( "disabled", true );
                    
                }
            });
            
            jQuery('input[name="tipoPeriodo"]').click(function() {
                if($("#tipoPeriodo:checked").val()==1){      
                    $(".periodo").prop( "disabled", false );    
                }else{
                    $(".periodo").prop( "disabled", true ); 
                }
            });
            
            jQuery('input[name="tipoConsultaIndividual"]').click(function() { 
                //setea las variables
                $("#tipoIdentificacionComercio").val("V");
                $("#identificacionComercio").val("");
                $("#tipoIdentificacionACI").val("V");
                $("#identificacionACI").val("");
                $("#terminal").val("");   
                switch($("#tipoConsultaIndividual:checked").val()) {
                    case "0":
                        // code block
                        console.log('entro');
                        $(".ci-comercio").prop( "disabled", false ); 
                        $(".ci-terminal").prop( "disabled", true );
                        $(".ci-aci").prop( "disabled", true ); 
                        
                        break;
                    case "1":
                        // code block
                        $(".ci-comercio").prop( "disabled", true ); 
                        $(".ci-terminal").prop( "disabled", false );
                        $(".ci-aci").prop( "disabled", true ); 
                        break;
                    case "2":
                        // code block
                        $(".ci-comercio").prop( "disabled", true ); 
                        $(".ci-terminal").prop( "disabled", true );
                        $(".ci-aci").prop( "disabled", false ); 
                        break;
                    default:
                        // code block
                        $(".ci-comercio").prop( "disabled", true ); 
                        $(".ci-terminal").prop( "disabled", true );
                        $(".ci-aci").prop( "disabled", true ); 
                }
            });
             
            /*if($("#tipoConsulta:checked").val()==1){                     
                $("#terminal").prop( "disabled", false );                    
            }else{                    
                $("#terminal").prop( "disabled", true );
                $("#terminal").val("");
            }*/
            /*se ejecuta al cargar la pagina*/
            if($("#tipoConsulta:checked").val()==1){
                $(".ci").prop( "disabled", false );     
                switch($("#tipoConsultaIndividual:checked").val()) {
                    case "0":
                        // code block
                        $(".ci-comercio").prop( "disabled", false ); 
                        $(".ci-terminal").prop( "disabled", true );
                        $(".ci-aci").prop( "disabled", true ); 
                        break;
                    case "1":
                        // code block
                        $(".ci-comercio").prop( "disabled", true ); 
                        $(".ci-terminal").prop( "disabled", false );
                        $(".ci-aci").prop( "disabled", true ); 
                        break;
                    case "2":
                        // code block
                        $(".ci-comercio").prop( "disabled", true ); 
                        $(".ci-terminal").prop( "disabled", true );
                        $(".ci-aci").prop( "disabled", false ); 
                        break;
                    default:
                        // code block
                        $(".ci-comercio").prop( "disabled", true ); 
                        $(".ci-terminal").prop( "disabled", true );
                        $(".ci-aci").prop( "disabled", true );
                }
            }else{                    
                $(".ci").prop( "disabled", true );
            }
            
            

            /*if($("#tipoConsultaIndividual:checked").val()==0){
                $(".ci-opciones").prop( "disabled", false );     
            }else{                    
                $(".ci-opciones").prop( "disabled", true );
            }*/
            
            
            jQuery('input[name="generarCobranzasComercios"]').click(function() {                
                $.ajax(
                    {
                        url: contexPath + '/generarCobranzasComercios.htm',
                        type:'POST',
                        dataType: 'json', 
                        beforeSend:function(objeto){                            
                            $.blockUI({ 
                                message: 'PROCESANDO, POR FAVOR ESPERE...',
                                css: { 
                                border:	'3px solid #aaa', 
                                padding: '15px', 
                                backgroundColor: '#000', 
                                '-webkit-border-radius': '10px', 
                                '-moz-border-radius': '10px', 
                                opacity: .5, 
                                color: '#fff'                                
                            } });
                        },
                        complete:function(){$.unblockUI;},                        
                        error:
                        function(e){                    
                        } 
                    }
                );                                         
             });
        });                
    </script>
    <br>
    <div class="row">    
        <div id="header-form">
            <h1>Generar Cobranzas Comercios</h1>
        </div>
        <label class="error-message" style="align-content: center">${model.messageError}</label>
        <div id="box-form">
            <form:form method="post" autocomplete="off" commandName="generarCobranzasComercios">
                <div class="contenedor_formulario">
                  <table class="formulario">
                    <tr>
                        <td class="etiqueta_black">Tipo de Plan:</td>
                        <td colspan="1" class="campo">     
                            <form:select path="tipoPlan" cssClass="select" multiple="true">                                      
                                <form:options items="${tipoPlanList}" itemValue="codigoTipoPlan" itemLabel="descripcion"/>
                            </form:select>
                        </td>
                    </tr> 
                    <tr>
                        <td colspan="2">
                            <form:errors path="tipoPlan" cssClass="error-message"/>                            
                        </td>
                    </tr>
                    <tr>
                        <td class="etiqueta_black">Estatus de las cuotas:</td>
                        <td colspan="1" class="campo">     
                            <form:select path="estatus" cssClass="select" multiple="true">                                      
                                <form:options items="${estatusList}" itemValue="id" itemLabel="descripcion"/>
                            </form:select>
                        </td>
                    </tr> 
                    <tr>
                        <td colspan="2">
                            <form:errors path="estatus" cssClass="error-message"/>                            
                        </td>
                    </tr>
                    <tr>
                      <td class="etiqueta_black">Cobranzas:</td>
                      <td class="campo">
                        <form:radiobutton path="tipoConsulta" id="tipoConsulta" value="0" checked="checked"/>General
                        <form:radiobutton path="tipoConsulta" id="tipoConsulta" value="1"/>Individual
                      </td>                        
                    </tr>
                    <tr>
                        <td colspan="2">                          
                        </td>
                    </tr>  
                    <tr>
                        <td class="etiqueta_black"><form:radiobutton path="tipoConsultaIndividual" id="tipoConsultaIndividual" cssClass="ci-comercio-radio ci" value="0" checked="checked"/>Comercio:</td>
                        <td class="campo">           
                            <form:select path="tipoIdentificacionComercio" cssClass="select ci-comercio ci" multiple="false">                                      
                                <form:options items="${tipoIdentificacionList}" />
                            </form:select>
                            <form:input path="identificacionComercio" cssClass="textbox ci-comercio ci" cssStyle="width: 80px" maxlength="30"/>                                                        
                        </td>                        
                    </tr>
                    <tr>
                        <td colspan="2">
                            <form:errors path="identificacionComercio" cssClass="error-message"/>                            
                        </td>
                    </tr>    
                    <tr>
                        <td class="etiqueta_black"><form:radiobutton path="tipoConsultaIndividual" id="tipoConsultaIndividual" cssClass="ci-terminal-radio ci" value="1"/>Terminal:</td>
                        <td class="campo">                            
                            <form:input path="terminal" cssClass="textbox ci-terminal ci" cssStyle="width: 80px" maxlength="8"/>                                                        
                        </td>                        
                    </tr>
                    <tr>
                        <td colspan="2">
                            <form:errors path="terminal" cssClass="error-message"/>                            
                        </td>
                    </tr>    
                    <tr>
                        <td class="etiqueta_black"><form:radiobutton path="tipoConsultaIndividual" id="tipoConsultaIndividual" cssClass="ci-aci-radio ci" value="2"/>ACI:</td>
                        <td class="campo">       
                            <form:select path="tipoIdentificacionACI" cssClass="select ci-aci ci" multiple="false">                                      
                                <form:options items="${tipoIdentificacionList}" />
                            </form:select>
                            <form:input path="identificacionACI" cssClass="textbox ci-aci ci" cssStyle="width: 80px" maxlength="30"/>                                                        
                        </td>                        
                    </tr>
                    <tr>
                        <td colspan="2">
                            <form:errors path="identificacionACI" cssClass="error-message"/>                            
                        </td>
                    </tr>     
                    <tr>
                      <td class="etiqueta_black">Seleccione un Rango:</td>
                      <td class="campo">
                        <input type="radio" id="tipoPeriodo" name="tipoPeriodo" value="0" checked="checked"/>Al Corte
                        <input type="radio" id="tipoPeriodo" name="tipoPeriodo" value="1"/>Mensual
                      </td>                        
                    </tr>
                    <tr>
                        <td class="etiqueta_black">Período:</td>
                        <td class="campo">
                            <form:select path="mes" cssClass="select periodo" multiple="false" disabled="true">                                      
                                <form:options items="${mesList}" />
                            </form:select>
                            <form:select path="ano" cssClass="select periodo" multiple="false" disabled="true">                                      
                                <form:options items="${anoList}" />
                            </form:select>
                        </td>                       
                    </tr>
                    <tr>
                        <td colspan="2">
                            <form:errors path="mes" cssClass="error-message"/>
                            <form:errors path="ano" cssClass="error-message"/>
                        </td>
                    </tr>                    
                    <tr>
                        <td class="botonera" colspan="2">
                            <input type="image" name="generarCobranzasComercios" src="${pageContext.request.contextPath}/resources/img/exportar.png" width='76' height='76'/>
                        </td>
                    </tr>                    
                  </table>                                 
                </div>  
            </form:form>
        </div>        
    </div>
</body>
</html>