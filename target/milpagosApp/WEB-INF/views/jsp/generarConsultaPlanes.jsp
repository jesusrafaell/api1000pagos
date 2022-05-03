<%-- 
    Document   : generarConsultaPlanes
    Created on : 10/08/20
    Author     : mcaraballo@emsys-solutions.net
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
                    $("#terminal").val("");        
                    
                    //habilita las opciones
                    switch($("#tipoConsultaIndividual:checked").val()) {
                        case "0":
                            // code block
                            $(".ci-comercio").prop( "disabled", false ); 
                            $(".ci-terminal").prop( "disabled", true );              
                            break;
                        case "1":
                            // code block
                            $(".ci-comercio").prop( "disabled", true ); 
                            $(".ci-terminal").prop( "disabled", false );
                            break;
                        default:
                            // code block
                            $(".ci-comercio").prop( "disabled", true ); 
                            $(".ci-terminal").prop( "disabled", true );
                    }
                    
                }else{
                    //$("#terminal").prop( "disabled", true );                    
                    //$("#terminal").val("");
                    $(".ci").prop( "disabled", true );
                    
                }
            });
            
            jQuery('input[name="tipoReporte"]').click(function() {
                if($("#tipoReporte:checked").val()==1){ 
                    //tipo de reporte Clientes con Plan
                    $(".clientes-con-plan").prop( "disabled", false );
                    $("#tipoConsulta:checked").click();
                }else{
                    //tipo de reporte Clientes sin Plan
                    $("input[name=tipoConsulta][value=0]").prop('checked', 'checked');
                    $(".clientes-con-plan").prop( "disabled", true );
                    $("#tipoConsulta:checked").click();
                }
            });
            
            jQuery('input[name="tipoConsultaIndividual"]').click(function() { 
                //setea las variables
                $("#tipoIdentificacionComercio").val("V");
                $("#identificacionComercio").val("");
                $("#terminal").val("");   
                switch($("#tipoConsultaIndividual:checked").val()) {
                    case "0":
                        // code block
                        $(".ci-comercio").prop( "disabled", false ); 
                        $(".ci-terminal").prop( "disabled", true );
                        break;
                    case "1":
                        // code block
                        $(".ci-comercio").prop( "disabled", true ); 
                        $(".ci-terminal").prop( "disabled", false );
                        break;
                    default:
                        // code block
                        $(".ci-comercio").prop( "disabled", true ); 
                        $(".ci-terminal").prop( "disabled", true );
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
                        break;
                    case "1":
                        // code block
                        $(".ci-comercio").prop( "disabled", true ); 
                        $(".ci-terminal").prop( "disabled", false );
                        break;
                    default:
                        // code block
                        $(".ci-comercio").prop( "disabled", true ); 
                        $(".ci-terminal").prop( "disabled", true );
                }
            }else{                    
                $(".ci").prop( "disabled", true );
            }
            
            

            /*if($("#tipoConsultaIndividual:checked").val()==0){
                $(".ci-opciones").prop( "disabled", false );     
            }else{                    
                $(".ci-opciones").prop( "disabled", true );
            }*/
            
            
            jQuery('input[name="generarConsultaPlanes"]').click(function() {                
                $.ajax(
                    {
                        url: contexPath + '/generarConsultaPlanes.htm',
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
            <h1>Generar Reporte de Planes por Terminales</h1>
        </div>
        <label class="error-message" style="align-content: center">${model.messageError}</label>
        <div id="box-form">
            <form:form method="post" autocomplete="off" commandName="generarConsultaPlanes">
                <div class="contenedor_formulario">
                  <table class="formulario">
                    <tr>
                      <td class="etiqueta_black">Seleccione un Tipo de Reporte:</td>
                      <td class="campo">
                        <input type="radio" id="tipoReporte" name="tipoReporte" value="1" checked="checked"/>Clientes con Plan
                        <input type="radio" id="tipoReporte" name="tipoReporte" value="0"/>Clientes sin Plan
                      </td>                        
                    </tr>
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
                        <td class="etiqueta_black">Estatus de los planes:</td>
                        <td colspan="1" class="campo">     
                            <form:select path="estatus" cssClass="select clientes-con-plan" multiple="true">                                      
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
                      <td class="etiqueta_black">Tipo de Consulta:</td>
                      <td class="campo">
                        <form:radiobutton path="tipoConsulta" id="tipoConsulta" value="0" checked="checked"/>General
                        <form:radiobutton path="tipoConsulta" id="tipoConsulta" value="1" cssClass="clientes-con-plan"/>Individual
                      </td>                        
                    </tr>
                    <tr>
                        <td colspan="2">                          
                        </td>
                    </tr>  
                    <tr>
                        <td class="etiqueta_black"><form:radiobutton path="tipoConsultaIndividual" id="tipoConsultaIndividual" cssClass="ci-comercio-radio ci clientes-con-plan" value="0" checked="checked"/>Comercio:</td>
                        <td class="campo">           
                            <form:select path="tipoIdentificacionComercio" cssClass="select ci-comercio ci clientes-con-plan" multiple="false">                                      
                                <form:options items="${tipoIdentificacionList}" />
                            </form:select>
                            <form:input path="identificacionComercio" cssClass="textbox ci-comercio ci clientes-con-plan" cssStyle="width: 80px" maxlength="30"/>                                                        
                        </td>                        
                    </tr>
                    <tr>
                        <td colspan="2">
                            <form:errors path="identificacionComercio" cssClass="error-message"/>                            
                        </td>
                    </tr>    
                    <tr>
                        <td class="etiqueta_black"><form:radiobutton path="tipoConsultaIndividual" id="tipoConsultaIndividual" cssClass="ci-terminal-radio ci clientes-con-plan" value="1"/>Terminal:</td>
                        <td class="campo">                            
                            <form:input path="terminal" cssClass="textbox ci-terminal ci clientes-con-plan" cssStyle="width: 80px" maxlength="8"/>                                                        
                        </td>                        
                    </tr>
                    <tr>
                        <td colspan="2">
                            <form:errors path="terminal" cssClass="error-message"/>                            
                        </td>
                    </tr>                        
                    <tr>
                        <td class="botonera" colspan="2">
                            <input type="image" name="generarConsultaPlanes" src="${pageContext.request.contextPath}/resources/img/exportar.png" width='76' height='76'/>
                        </td>
                    </tr>                    
                  </table>                                 
                </div>  
            </form:form>
        </div>        
    </div>
</body>
</html>