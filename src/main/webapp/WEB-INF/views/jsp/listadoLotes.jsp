<%-- 
    Document   : listadoLotes
    Created on : 09/01/2017
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
	   Listado de lotes registrados en la aplicación
	</td>
	</tr>
	</table>
        <br>        
        <br>             
        <display:table name="${model.listadoLotes}" pagesize="10" requestURI="/listadoLotes.htm" export="true" class="tabla" cellpadding="0" cellspacing="0" style="margin:0 auto; width:750px;">            
            <display:setProperty name="export.excel.filename" value="ListadoLotes.xls" />
            <display:setProperty name="export.xml.filename" value="ListadoLotes.xml" />
            <display:setProperty name="export.pdf.filename" value="ListadoLotes.pdf" />            
            <display:column property="codigoCompania" title="Compañia" class="texto_centro" headerClass="headerClass_table"/>
            <display:column property="numeroLote" title="Numero Lote" class="texto_centro" headerClass="headerClass_table"/>
            <display:column property="cantidadPagos" title="Cantidad de Registros" class="texto_centro" headerClass="headerClass_table"/>
            <display:column property="codigoMonedaDebito" title="Codigo Moneda" class="texto_centro" headerClass="headerClass_table"/>
            <display:column property="cuentaDebito" title="Cuenta Debito" class="texto_centro" headerClass="headerClass_table"/>
            <display:column property="fechaValor" title="Fecha Transacción" class="texto_centro" headerClass="headerClass_table"/>
            <display:column property="montoTotalFormato" title="Monto Total" class="texto_centro" headerClass="headerClass_table"/>            
        </display:table>
        <table class="tabla" cellpadding="0" cellspacing="0" style="margin:0 auto; width:750px;">            
            <tr>
              <td class="botonera" colspan="5">                  
                  <a href="consultaLotes.htm"><img src="${pageContext.request.contextPath}/resources/img/volver.png" width='56' height='56'/></a>
              </td>
            </tr>
	</table>
    </body>
</html>
