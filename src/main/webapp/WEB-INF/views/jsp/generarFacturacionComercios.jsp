<%-- 
    Document   : generarFacturacionComercios
    Created on : 18/10/2017
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
                    $("#tipoIdentificacion").prop( "disabled", false );
                    $("#identificacion").prop( "disabled", false );
                 }else{
                    $("#tipoIdentificacion").prop( "disabled", true );
                    $("#identificacion").prop( "disabled", true );
                    $("#identificacion").val("");
                 }
             });
             
            if($("#tipoConsulta:checked").val()==1){                     
                    $("#tipoIdentificacion").prop( "disabled", false );
                    $("#identificacion").prop( "disabled", false );                   
                 }else{                    
                    $("#tipoIdentificacion").prop( "disabled", true );
                    $("#identificacion").prop( "disabled", true );
                    $("#identificacion").val("");
            }
            
            jQuery('input[name="generarFacturacion"]').click(function() {                
                $.ajax(
                    {
                        url: contexPath + '/generarFacturacionComercios.htm',
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
            <h1>Generar Facturación Comercios</h1>
        </div>
        <label class="error-message" style="align-content: center">${model.messageError}</label>
        <div id="box-form">
            <form:form method="post" autocomplete="off" commandName="generarFacturacionComercios">
                <div class="contenedor_formulario">
                  <table class="formulario">
                    <tr>
                      <td class="etiqueta_black">Facturación:</td>
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
                      <td class="etiqueta_black">Tipo Contrato:</td>
                        <td class="campo">
                            <form:radiobutton path="tipoContrato" id="tipoContrato" value="1" checked="checked"/>Temporal
                            <form:radiobutton path="tipoContrato" id="tipoContrato" value="2"/>Permanente
                        </td>                        
                    </tr>
                    <tr>
                        <td colspan="2">
                            <form:errors path="tipoContrato" cssClass="error-message"/>
                        </td>
                    </tr>                    
                    <tr>
                        <td class="etiqueta_black">Período:</td>
                        <td class="campo">
                            <form:select path="mes" cssClass="select" multiple="false">                                      
                                <form:options items="${mesList}" />
                            </form:select>
                            <form:select path="ano" cssClass="select" multiple="false">                                      
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
                        <td class="etiqueta_black">Número de Control:</td>
                        <td class="campo">
                            <form:input path="numeroControl" cssClass="textbox" cssStyle="width: 80px"/>
                        </td>                       
                    </tr>
                    <tr>
                        <td colspan="2">
                            <form:errors path="numeroControl" cssClass="error-message"/>                            
                        </td>
                    </tr>
                    <tr>
                            <td class="etiqueta_black">Identificación:</td>
                            <td>
                              <form:select path="tipoIdentificacion" cssClass="select" multiple="false">                                      
                                <form:options items="${tipoIdentificacionList}" />
                              </form:select>
                              <form:input path="identificacion" cssClass="textbox" cssStyle="width: 80px" maxlength="9"/>                                 
                            </td>                                
                        </tr>
                        <tr>
                            <td></td>
                            <td>
                                <form:errors path="tipoIdentificacion" cssClass="error-message"/>
                                <form:errors path="identificacion" cssClass="error-message"/>
                            </td>
                        </tr>
                    <tr>
                        <td class="botonera" colspan="2">
                            <input type="image" name="generarFacturacion" src="${pageContext.request.contextPath}/resources/img/exportar.png" width='76' height='76'/>
                        </td>
                    </tr>                    
                  </table>                                 
                </div>  
            </form:form>
        </div>        
    </div>
</body>
</html>