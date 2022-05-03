<%-- 
    Document   : resultadoLoteGenerado
    Created on : 16/01/2017
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
	   Resultado Lote Pago a Comercios
	</td>
	</tr>
	</table>
        <br>
        <label class="error-message" style="align-content: center">${model.messageError}</label>
        <br>
        <br>
        <table class="tabla" cellpadding="0" cellspacing="0">
            <thead>
                <tr>
                    <td>Código del Grupo</td>
                    <td>Nombre del Grupo</td>
                    <td>Banco</td>
                </tr>
            </thead>
            <tbody>
            <tr>
                <td class="texto_centro">G000025763</td>
                <td class="texto_centro">INVERSIONES GROSS, C.A.</td>
                <td class="texto_centro">${model.lotesGenerados[0].banco.descripcion}</td>
            </tr>
            </tbody>
	</table>
        <br>
        <br>        
        <display:table name="${model.lotesGenerados}" pagesize="10" requestURI="/resultadoLoteGenerado.htm" export="true" class="tabla" cellpadding="0" cellspacing="0" style="margin:0 auto; width:750px;">
            <display:setProperty name="export.excel.filename" value="ListadoLotes.xls" />
            <display:setProperty name="export.xml.filename" value="ListadoLotes.xml" />
            <display:setProperty name="export.csv.filename" value="ListadoLotes.csv" />
            <display:setProperty name="export.pdf.filename" value="ListadoLotes.pdf" />
            <display:column property="codigoCompania" title="Compañia" class="texto_centro" headerClass="headerClass_table"/>
            <display:column property="numeroLote" title="Lote" class="texto_centro" headerClass="headerClass_table"/>
            <display:column property="cantidadPagos" title="Cantidad de Pagos" class="texto_centro" headerClass="headerClass_table"/>            
            <display:column property="fechaValor" title="Fecha Valor" class="texto_centro" headerClass="headerClass_table"/>
            <display:column property="montoTotalFormato" title="Monto Total Débito" class="texto_centro" headerClass="headerClass_table"/>
            <display:column property="cuentaDebito" title="Cuenta Débito" class="texto_centro" headerClass="headerClass_table"/>
        </display:table>           
        <table class="tabla" cellpadding="0" cellspacing="0" style="margin:0 auto; width:750px;">            
            <tr>
              <td class="botonera" colspan="5">                  
                  <a href="lotePagos.htm"><img src="${pageContext.request.contextPath}/resources/img/volver.png" width='56' height='56'/></a>
              </td>
            </tr>
	</table>
    </body>
</html>
