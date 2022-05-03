<%-- 
    Document   : programarVisitaConsulta
    Created on : 06/03/2017
    Author     : mggy@sistemasemsys.com
--%>
<%@ include file="/WEB-INF/views/jsp/include.jsp" %>
<html>
<head>    
</head>
<body>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/utils.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.1.0.js"></script>        
    <script src="${pageContext.request.contextPath}/resources/js/rainbow-custom.min.js"></script>
    <script type="text/javascript">
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

        });    
    </script>
    <br>
    <div class="row">    
        <div id="header-form">
            <h1>Programar Visita a Comercio</h1>
        </div>
        <label class="error-message" style="align-content: center">${model.messageError}</label>
        <div id="box-form">
            <form:form method="post" autocomplete="off" commandName="programarVisitaConsulta">
                <div class="contenedor_formulario">
                  <table class="formulario">
                    <tr>
                      <td class="etiqueta_black">Comercio:</td>
                        <td class="campo">
                            <form:radiobutton path="tipoConsulta" id="tipoConsulta" value="0" checked="checked"/>Nuevo
                            <form:radiobutton path="tipoConsulta" id="tipoConsulta" value="1"/>Afiliado                            
                        </td>                        
                    </tr>
                    <tr>
                        <td colspan="2">                          
                        </td>
                    </tr>  
                    <tr>
                        <td class="etiqueta_black">Identificación:</td>
                        <td class="campo">
                            <form:select path="tipoIdentificacion" cssClass="select" multiple="false" disabled="true">                                      
                                <form:options items="${tipoIdentificacionList}" />
                            </form:select>
                            <form:input path="identificacion" cssClass="textbox" cssStyle="width: 80px" maxlength="9" disabled="true"/>                                                        
                        </td>                        
                    </tr>
                    <tr>
                        <td colspan="2">
                            <form:errors path="tipoIdentificacion" cssClass="error-message"/>
                            <form:errors path="identificacion" cssClass="error-message"/>
                        </td>
                    </tr>                                                                                                    
                    <tr>
                        <td class="botonera" colspan="2">
                            <input type="image" name="programarVisitaConsulta" src="${pageContext.request.contextPath}/resources/img/buscar.png" width='56' height='56'/>
                        </td>
                    </tr>                    
                  </table>                                 
                </div>  
            </form:form>
        </div>
    </div>
</body>
</html>