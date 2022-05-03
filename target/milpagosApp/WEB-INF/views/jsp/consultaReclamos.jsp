<%-- 
    Document   : consultaReclamos
    Created on : 10/02/2017
    Author     : mggy@sistemasemsys.com
--%>
<%@ include file="/WEB-INF/views/jsp/include.jsp" %>
<html>
<head>    
</head>
<body>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.1.0.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.datetimepicker.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/rainbow-custom.min.js"></script>
<script type="text/javascript">// <![CDATA[
    jQuery(function(){
        
        var logicDesde = function( ct ){
            this.setOptions({                
                maxDate:jQuery('#fechaHasta').val()?jQuery('#fechaHasta').val().substr(6,4) + "/" + jQuery('#fechaHasta').val().substr(3,2) + "/" + jQuery('#fechaHasta').val().substr(0,2):false
               });            
        };
        
        var logicHasta = function( ct ){        
               this.setOptions({                
                minDate:jQuery('#fechaDesde').val()?jQuery('#fechaDesde').val().substr(6,4) + "/" + jQuery('#fechaDesde').val().substr(3,2) + "/" + jQuery('#fechaDesde').val().substr(0,2):false
               });                        
        };
        
        jQuery('#fechaDesde').datetimepicker({
              format:'d-m-Y',
              onShow:logicDesde,
              onChangeDateTime:logicDesde,
              timepicker:false
        });       
                
        jQuery('#fechaHasta').datetimepicker({
              format:'d-m-Y',
              onShow:logicHasta,
              onChangeDateTime:logicHasta,
              timepicker:false
         });               
                
});    
</script>    
    <br>
    <div class="row">    
        <div id="header-form">
            <h1>Gesti�n de Reclamos</h1>
        </div>
        <label class="error-message" style="align-content: center">${model.messageError}</label>
        <div id="box-form">
            <form:form method="post" autocomplete="off" commandName="consultaReclamos">
                <div class="contenedor_formulario">
                  <table class="formulario">
                    <tr>
                        <td class="etiqueta_black">Desde:</td>
                        <td class="campo">
                            <form:input type="text" id="fechaDesde" path="fechaDesde" cssClass="textbox" readonly="true"/>
                        </td>                        
                    </tr>
                    <tr>
                        <td colspan="2">
                            <form:errors path="fechaDesde" cssClass="error-message"/>
                        </td>
                    </tr>
                    <tr>
                    <td class="etiqueta_black">Hasta:</td>
                        <td class="campo">
                            <form:input type="text" id="fechaHasta" path="fechaHasta" cssClass="textbox" readonly="true"/>
                        </td>                   
                    </tr>
                    <tr>
                        <td colspan="2">
                            <form:errors path="fechaHasta" cssClass="error-message"/>
                        </td>
                    </tr>  
                    <tr>
                        <td class="etiqueta_black">Tipo de Reclamo:</td>
                        <td class="campo">
                            <form:select path="tipoReclamo" cssClass="select" multiple="false">
                                <form:option value="%" label="<--- Seleccione --->"/>
                                <form:options items="${tipoReclamoList}" itemLabel="descripcionReclamo" itemValue="id"/>
                            </form:select>                             
                        </td>                        
                    </tr>
                    <tr>
                        <td colspan="2">
                            <form:errors path="tipoReclamo" cssClass="error-message"/>                            
                        </td>
                    </tr>                                                                                                                     
                    <tr>
                        <td class="botonera" colspan="2">
                            <input type="image" name="consultaReclamo" src="${pageContext.request.contextPath}/resources/img/buscar.png" width='56' height='56'/>                            
                        </td>
                    </tr>                    
                  </table>                                 
                </div>  
            </form:form>
        </div>
    </div>
</body>
</html>