<%-- 
    Document   : listadoPlanes
    Created on : 09-abr-2020, 16:20:52
    Author     : jcperez@empsys-solutions.net
--%>

<%@ include file="/WEB-INF/views/jsp/include.jsp" %>
<!DOCTYPE html>
<html>
    <head>        
    </head>
    <body>
        <table cellpadding="0" cellspacing="0" style="width:750px; border: none">
	<tr>
	<td colspan="2" class="titulo_tabla">
	   Listado de Planes por Terminal Nro: ${model.codigoTerminal} registrados en la aplicación
	</td>
        <td colspan="10">                                            
            <a href="crearPlanPago.htm?codigoTerm=${model.codigoTerminal}"><img src="${pageContext.request.contextPath}/resources/img/planNuevo.jpeg" width='46' height='46'/></a>
        </td>
	</tr>
	</table>
        <br>
        <label class="error-message" style="align-content: center">${model.messageError}</label>
        <br>     
        <display:table name="${model.listadoTerminalPlanes}" id="row" pagesize="5" requestURI="/listadoTerminalPlanes.htm" export="true" class="tabla" cellpadding="0" cellspacing="0" style="margin:0 auto; width:800px;">
            <display:setProperty name="export.excel.filename" value="ListadoTerminalPlanes.xls" />
            <display:setProperty name="export.xml.filename" value="ListadoTerminalPlanes.xml" />
            <display:setProperty name="export.csv.filename" value="ListadoTerminalPlanes.csv" />
            <display:setProperty name="export.pdf.filename" value="ListadoTerminalPlanes.pdf" />
            <display:column property="descTipoPlan" title="Tipo Plan" class="texto_centro" headerClass="headerClass_table"/>
            <display:column property="planNombre" title="Plan" class="texto_centro" headerClass="headerClass_table"/>
            <display:column property="fechaInicio"  title="Fecha Inicio" class="texto_centro" style="height:40px; width:70px" headerClass="headerClass_table"/>
            <display:column property="fechaFin" title="Fecha Fin" class="texto_centro" style="height:40px; width:70px" headerClass="headerClass_table"/>            
            <display:column property="frecuenciaDescripcion" title="Frecuencia" class="texto_centro" headerClass="headerClass_table"/>
            <display:column property="montoTarifa" title="Monto Tarifa" class="texto_centro" headerClass="headerClass_table"/>
           
            <c:choose>
         
                <c:when test = "${(row.montoFijo==1)}">
                    <display:column  title="Monto Fijo?" class="texto_centro" headerClass="headerClass_table" value="Si"/>
                </c:when>

                <c:otherwise>
                   <display:column  title="Monto Fijo?" class="texto_centro" headerClass="headerClass_table" value="No"/>
                </c:otherwise>
             </c:choose>
            <display:column property="porcComisionBancaria" title="Porcentaje de Comision Bancaria" class="texto_centro" headerClass="headerClass_table"/>
            <display:column property="montoInicial" title="Monto Inicial" class="texto_centro" headerClass="headerClass_table"/>
            <display:column property="estatusDescripcion" title="Estatus" class="texto_centro" headerClass="headerClass_table"/>
            <c:if test="${(row.editable=='editable')}">
                <display:column paramId="id" paramProperty="id" title="Editar" href="editarPlanPago.htm?codigoTerm=${model.codigoTerminal}" class="texto_centro" headerClass="headerClass_table" media="html"><img src="${pageContext.request.contextPath}/resources/img/actualizar.png" width='36' height='36'/></display:column>
            </c:if>
        </display:table>    
             <table class="tabla" cellpadding="0" cellspacing="0" style="margin:0 auto; width:750px;">            
            <tr>
              <td class="botonera" colspan="5">                  
                  <a href="editarTerminalComercio.htm?codTerminal=${model.codigoTerminal}"><img src="${pageContext.request.contextPath}/resources/img/volver.png" width='56' height='56'/></a>
              </td>
            </tr>
	</table>
    </body>
</html>
