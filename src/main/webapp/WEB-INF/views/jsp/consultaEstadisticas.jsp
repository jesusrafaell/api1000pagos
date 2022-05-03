<%-- 
    Document   : consultaEstadisticas
    Created on : 14-dic-2016
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
            <h1>Consulta Estadísticas</h1>
        </div>
        <label class="error-message" style="align-content: center">${messageError}</label>
        <div id="box-form">
            <form:form method="post" autocomplete="off" commandName="consultaEstadisticas">
                <div class="contenedor_formulario">
                  <table class="formulario">                    
                    <tr>
                      <td class="etiqueta_black">Tipo de Consulta:</td>
                        <td class="campo">
                          <form:select path="tipoEstadistica" name="tipoEstadistica" id="tipoEstadistica" cssClass="select" multiple="false">                            
                            <form:options items="${tipoEstadistica}" itemLabel="tipoEstadistica" itemValue="id" />
                          </form:select>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                          <form:errors path="tipoEstadistica" cssClass="error-message"/>
                        </td>
                    </tr>                
                    <tr>
                          <td class="botonera" colspan="2">                            
                            <input type="image" name="consultaEstadisticas" src="${pageContext.request.contextPath}/resources/img/buscar.png" width='56' height='56'/>
                          </td>
                    </tr>                    
                  </table>                                 
                </div>  
            </form:form>
        </div>
    </div>
</body>
</html>