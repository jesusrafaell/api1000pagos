<%-- 
    Document   : consultaBitacora
    Created on : 22-ago-2016
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
<script type="text/javascript">
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
        
        //Busca la data de las opciones asociadas al modulo seleccionado
        var contexPath = "<%=request.getContextPath() %>";

        $('#modulo').change( function(e) {

        if(jQuery(this).val()!=""){
         $('#opcion').find('option').remove().end();

            e.preventDefault();
            var val = $(this).val();
            jQuery("#opcion").removeAttr("disabled");
                           
                $.ajax({
                   type: "POST",
                   url:  contexPath + '/loadopciones.htm',
                   dataType: 'json',
                   data: { moduloId : val },                   
                   success: function(data) {                   
                   showData(data.lstOpciones);                      
                   },
                   error:
                   function(e){                    
                   } 
                });
        }
            else {            
                $('#opcion').find('option').remove().end();
                $('#opcion').append("<option value='%'><--- Seleccione ---></option>");
            }
        });

        function showData(data) {
            $('#opcion').append("<option value='%'><--- Seleccione ---></option>");
            for ( var i = 0, len = data.length; i < len; ++i) {
                var msajax = data[i];
                $('#opcion').append("<option value=\"" +msajax.id + "\">" + msajax.nombre + "</option>");
            }
        }
});    
</script>
    <br>
    <div class="row">    
        <div id="header-form">
            <h1>Consulta Bitácora</h1>
        </div>
        <label class="error-message" style="align-content: center">${messageError}</label>
        <div id="box-form">
            <form:form method="post" autocomplete="off" commandName="consultaBitacora">
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
                    <form  method="post" action="tabs.htm"> 
                        <tr>
                          <td class="etiqueta_black">Módulo:</td>
                            <td class="campo">
                              <form:select path="modulo" name="modulo" id="modulo" cssClass="select" multiple="false">
                                <form:option value="" label="<--- Seleccione --->"/>
                                <form:options items="${modulo}" itemLabel="nombre" itemValue="IdAsString" />
                              </form:select>
                            </td>                        
                        </tr>
                        <tr>
                            <td colspan="2">
                              <form:errors path="modulo" cssClass="error-message"/>
                            </td>
                        </tr>
                        <tr>
                          <td class="etiqueta_black">Opción:</td>
                            <td class="campo">
                              <form:select path="opcion" name="opcion" id="opcion" cssClass="select" multiple="false">
                                <form:option value="%" label="<--- Seleccione --->"/>                                
                              </form:select>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                              <form:errors path="opcion" cssClass="error-message"/>
                            </td>
                        </tr>
                    </form>
                    <td class="etiqueta_black">Login:</td>
                        <td class="campo">
                            <form:input type="text" path="usuario" cssClass="textbox"/>
                        </td>                   
                    </tr>
                    <tr>
                        <td colspan="2">
                            <form:errors path="usuario" cssClass="error-message"/>
                        </td>
                    </tr>
                    <tr>
                          <td class="botonera" colspan="2">                            
                            <input type="image" name="consultaBitacora" src="${pageContext.request.contextPath}/resources/img/buscar.png" width='56' height='56'/>
                          </td>
                    </tr>                    
                  </table>                                 
                </div>  
            </form:form>
        </div>
    </div>
</body>
</html>
