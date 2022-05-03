<%-- 
    Document   : consultaComercio
    Created on : 02/12/2016
    Author     : mggy@sistemasemsys.com
--%>
<%@ include file="/WEB-INF/views/jsp/include.jsp" %>
<html>
<head>    
</head>
<body>
    <br>
    <div class="row">    
        <div id="header-form">
            <h1>Consulta Comercio</h1>
        </div>
        <label class="error-message" style="align-content: center">${model.messageError}</label>
        <div id="box-form">
            <form:form method="post" autocomplete="off" commandName="consultaComercio">
                <div class="contenedor_formulario">
                  <table class="formulario">
                    <tr>
                        <td class="etiqueta_black">Identificación:</td>
                        <td class="campo">
                            <form:select path="tipoIdentificacion" cssClass="select" multiple="false">                                      
                                <form:options items="${tipoIdentificacionList}" />
                            </form:select>
                            <form:input path="identificacion" cssClass="textbox" cssStyle="width: 80px" maxlength="9"/>                                                        
                        </td>                        
                    </tr>
                    <tr>
                        <td colspan="2">
                            <form:errors path="tipoIdentificacion" cssClass="error-message"/>
                            <form:errors path="identificacion" cssClass="error-message"/>
                        </td>
                    </tr>                                        
                    <tr>
                      <td class="etiqueta_black">Actividad Comercial:</td>
                        <td class="campo">
                            <form:select path="codigoCategoria" cssClass="select" multiple="false">                                      
                                <form:option value="" label="<--- Seleccione --->"/>
                                <form:options items="${actividadComercialList}" itemLabel="descripcionCategoria" itemValue="codigoCategoria"/>
                            </form:select>
                        </td>                        
                    </tr>
                    <tr>
                        <td colspan="2">
                          <form:errors path="codigoCategoria" cssClass="error-message"/>
                        </td>
                    </tr>   
                    
                    
                    <tr>
                        <td class="etiqueta_black">Tipo Contrato:</td>
                        <td class="campo">
                            <form:select path="contrato" cssClass="select" multiple="false">                                      
                                <form:option value="" label="<--- Seleccione --->"/>
                                <form:options items="${tipoContratolList}" itemLabel="descripcion" itemValue="codigoContrato"/>
                            </form:select>
                        </td>                        
                    </tr>
                    <tr>
                        <td colspan="2">
                          <form:errors path="contrato" cssClass="error-message"/>
                        </td>
                    </tr>   
                                       
                    <tr>
                        <td class="etiqueta_black">Días de Operación:</td>
                        <td class="campo">
                            <form:select path="dias" cssClass="select" multiple="false">                                      
                                <form:option value="" label="<--- Seleccione --->"/>
                                <form:option value="Lun" label="Lunes"/>
                                <form:option value="Mar" label="Martes"/>
                                <form:option value="Mier" label="Miercoles"/>
                                <form:option value="Jue" label="Jueves"/>
                                <form:option value="Vie" label="Viernes"/>
                                <form:option value="Sab" label="Sabado"/>
                                <form:option value="Dom" label="Domingo"/>
                                <form:option value="Lun,Mar,Mie;Jue,Vie" label="Lunes a Viernes"/>
                                <form:option value="Sab,Dom" label="Sabado y Domingo"/>
                                <form:option value="Lun,Mar,Mie" label="Lunes, Martes y Miercoles"/>
                                <form:option value="Jue,Vie" label="Jueves y Viernes"/>
                            </form:select>
                        </td>                        
                    </tr>
                    <tr>
                        <td colspan="2">
                          <form:errors path="dias" cssClass="error-message"/>
                        </td>
                    </tr> 
                    
                    
                    <tr>
                        <td class="etiqueta_black">Contribuyentes Especiales:</td>
                        <td class="campo">
                            <form:select path="contribuyente" cssClass="select" multiple="false">                                      
                                <form:option value="" label="<--- Seleccione --->"/>
                                <form:option value="SI" label="Es Contribuyente"/>
                                <form:option value="NO" label="No es Contribuyente"/>
                            </form:select>
                        </td>                        
                    </tr>
                    <tr>
                        <td colspan="2">
                          <form:errors path="contribuyente" cssClass="error-message"/>
                        </td>
                    </tr>   
                    
                    
                    <tr>
                          <td class="botonera" colspan="2">
                            <input type="image" name="consultaComercio" src="${pageContext.request.contextPath}/resources/img/buscar.png" width='56' height='56'/>
                          </td>
                    </tr>                    
                  </table>                                 
                </div>  
            </form:form>
        </div>
    </div>
</body>
</html>