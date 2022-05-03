<%-- 
    Document   : consultaRequerimientos
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
            <h1>Gestión de Requerimientos</h1>
        </div>
        <label class="error-message" style="align-content: center">${model.messageError}</label>
        <div id="box-form">
            <form:form method="post" autocomplete="off" commandName="gestionRequerimientos">
                <div class="contenedor_formulario">
                  <table class="formulario">
                    <tr>
                        <td class="etiqueta_black">Identificación del Comercio:</td>
                        <td class="campo">
                            <form:select path="tipoIdentificacion" cssClass="select" multiple="false">                                      
                                <form:options items="${tipoIdentificacionList}" />
                            </form:select>
                            <form:input path="identificacion" cssClass="textbox" cssStyle="width: 80px"/>                                                        
                        </td>                        
                    </tr>
                    <tr>
                        <td colspan="2">
                            <form:errors path="tipoIdentificacion" cssClass="error-message"/>
                            <form:errors path="identificacion" cssClass="error-message"/>
                        </td>
                    </tr>                                                            
                    <tr>
                      <td class="etiqueta_black">Tipo de Requerimiento:</td>
                        <td class="campo">
                          <form:select path="tipoRequerimiento" name="tipoRequerimiento" cssClass="select" multiple="false">
                            <form:option value="" label="<--- Seleccione --->"/>
                            <form:options items="${tipoRequerimientoList}" itemLabel="descripcionRequerimiento" itemValue="id" />
                          </form:select>
                        </td>                        
                    </tr>
                    <tr>
                        <td colspan="2">
                          <form:errors path="tipoRequerimiento" cssClass="error-message"/>
                        </td>
                    </tr>                                        
                    <tr>
                      <td class="etiqueta_black">Zona de Atención:</td>
                        <td class="campo">
                          <form:select path="codZonaAtencion" name="zona" cssClass="select" multiple="false">
                            <form:option value="" label="<--- Seleccione --->"/>
                            <form:options items="${zonasAtencionList}" itemLabel="descripcion" itemValue="id" />
                          </form:select>
                        </td>                        
                    </tr>
                    <tr>
                        <td colspan="2">
                          <form:errors path="codZonaAtencion" cssClass="error-message"/>
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