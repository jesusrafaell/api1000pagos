<%-- 
    Document   : listadoTerminalesComercio
    Created on : 20-abr-2017
    Author     : mggy@sistemasemsys.com
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
               Listado de Terminales asociados al Comercio
            </td>            
            <td colspan="9" class="etiqueta" style="width: 100px"></td>
            <td colspan="10">                                            
                <a href="guardarTerminalComercio.htm?codComercio=${model.codigoComercio}&codBancoCuentaAbono=${model.codBancoCuentaAbono}&numCuentaAbono=${model.numCuentaAbono}"><img src="${pageContext.request.contextPath}/resources/img/terminalNuevo.png" width='46' height='46'/></a>
            </td>
	</tr>
	</table>
        <br>
        <label class="error-message" style="align-content: center">${model.messageError}</label>
        <br>     
        <display:table name="${model.listadoTerminalesComercio}" pagesize="5" requestURI="/listadoTerminalesComercio.htm" export="true" class="tabla" cellpadding="0" cellspacing="0" style="margin:0 auto; width:750px;">
            <display:setProperty name="export.excel.filename" value="ListadoTerminalesComercio.xls" />
            <display:setProperty name="export.xml.filename" value="ListadoTerminalesComercio.xml" />
            <display:setProperty name="export.csv.filename" value="ListadoTerminalesComercio.csv" />
            <display:setProperty name="export.pdf.filename" value="ListadoTerminalesComercio.pdf" />
            <display:column property="codigoAfiliado" title="Código Afiliado" class="texto_centro" headerClass="headerClass_table"/>            
            <display:column property="codigoTerminal" title="Código Terminal" class="texto_centro" headerClass="headerClass_table"/>
            <c:if test="${editTerminal}">
                <display:column paramId="codigoTerminal" paramProperty="codigoTerminal" title="Planes" href="listadoTerminalPlanes.htm?messageError=" class="texto_centro" headerClass="headerClass_table" media="html"><img src="${pageContext.request.contextPath}/resources/img/plan.jpeg" width='36' height='36'/></display:column>
                <display:column paramId="codTerminal" paramProperty="codigoTerminal" title="Editar" href="editarTerminalComercio.htm" class="texto_centro" headerClass="headerClass_table" media="html"><img src="${pageContext.request.contextPath}/resources/img/actualizar.png" width='36' height='36'/></display:column>
            </c:if>
            <c:if test="${deleteTerminal}">
                <display:column paramId="codTerminal" paramProperty="codigoTerminal" title="Eliminar" href="eliminarTerminalComercio.htm" class="texto_centro" headerClass="headerClass_table" media="html"><img src="${pageContext.request.contextPath}/resources/img/eliminar.gif" width='36' height='36'/></display:column>
            </c:if>
        </display:table>
        <table class="tabla" cellpadding="0" cellspacing="0" style="margin:0 auto; width:750px;">            
            <tr>
              <td class="botonera" colspan="5">                  
                  <a href="resultadoConsultaComercio.htm?messageError=&tipo=${model.tipo}&identifica=${model.identifica}"><img src="${pageContext.request.contextPath}/resources/img/volver.png" width='56' height='56'/></a>
              </td>
            </tr>
	</table>
    </body>
</html>
