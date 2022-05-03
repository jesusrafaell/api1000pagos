<%-- 
    Document   : cargarCuotasMasivo
    Created on : 01-dic-2020
    Author     : mcaraballo@emsys-solutions.net
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
        <script src="${pageContext.request.contextPath}/resources/js/rainbow-custom.min.js"></script>
        <script type="text/javascript">                       
            
            jQuery(function(){                
                jQuery('#fechaVisitaInicio').datetimepicker({
                      format:'d-m-Y H:i',
                      onShow:function( ct ){
                        this.setOptions({                
                            maxDate:jQuery('#fechaVisitaFin').val()?jQuery('#fechaVisitaFin').val().substr(6,4) + "/" + jQuery('#fechaVisitaFin').val().substr(3,2) + "/" + jQuery('#fechaVisitaFin').val().substr(0,2):false,
                            maxTime:jQuery('#fechaVisitaFin').val()?jQuery('#fechaVisitaFin').val().substr(11,2) + ":" + jQuery('#fechaVisitaFin').val().substr(14,2):false
                        });
                      },
                      timepicker:true                      
                 });                 
            });
            
            jQuery(function(){
                jQuery('#fechaVisitaFin').datetimepicker({
                      format:'d-m-Y H:i',
                      onShow:function( ct ){
                        this.setOptions({                
                            minDate:jQuery('#fechaVisitaInicio').val()?jQuery('#fechaVisitaInicio').val().substr(6,4) + "/" + jQuery('#fechaVisitaInicio').val().substr(3,2) + "/" + jQuery('#fechaVisitaInicio').val().substr(0,2):false,
                            minTime:jQuery('#fechaVisitaInicio').val()?jQuery('#fechaVisitaInicio').val().substr(11,2) + ":" + jQuery('#fechaVisitaInicio').val().substr(14,2):false
                        });
                      },
                      timepicker:true
                 });                 
            });
        </script>    
        <br>
        <div class="row" style="width: 70%">
            <div id="header-form">
                <h1>Carga Masiva de Cuotas </h1>
            </div>
            <label class="error-message" style="align-content: center">${model.messageError}</label>
            <div id="box-form">
                <form:form method="post" autocomplete="off" commandName="cargarCuotasManual" enctype="multipart/form-data">
                    <div class="contenedor_formulario">
                      <table class="formulario">                        
                        
                        <tr>                            
                            <td class="etiqueta_black">Archivo de Cuotas:</td>                            
                        </tr>
                        <tr>
                            <td class="campo">
                                <input type="file" name="archivo" id="archivo" accept=".csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel" multiple="false"/>
                                <br>
                                <form:errors path="archivo" cssClass="error-message"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="botonera" colspan="2">                                
                                <input type="image" name="cargarCuotas" src="${pageContext.request.contextPath}/resources/img/guardar.png" width='56' height='56'/>
                            </td>
                        </tr>
                      </table>                    
                    </div>  
                </form:form>
            </div>
        </div>
    </body>
</html>
