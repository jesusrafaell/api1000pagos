<%-- 
    Document   : listadoContribuyentesEspeciales
    Created on : 30/04/2019
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
	   Listado de Comercios Contribuyentes Especiales
	</td>
	</tr>
	</table>
        <br>        
        <br>             
        <display:table name="${model.listadoContribuyentesEspeciales}" id="row" pagesize="10" requestURI="/listadoContribuyentesEspeciales.htm" export="true" class="tabla" cellpadding="0" cellspacing="0" style="margin:0 auto; width:750px;">            
            <display:setProperty name="export.excel.filename" value="ListadoContribuyentesEspeciales.xls" />
            <display:setProperty name="export.xml.filename" value="ListadoContribuyentesEspeciales.xml" />
            <display:setProperty name="export.pdf.filename" value="ListadoContribuyentesEspeciales.pdf" />            
            <display:column title="Identificación" class="texto_centro" headerClass="headerClass_table">
                <a href="resultadoConsultaComercio.htm?messageError=&tipo=${fn:substring(row.rifComercio,0,1)}&identifica=${fn:substring(row.rifComercio,1,10)}">${fn:substring(row.rifComercio,0,1)}-${fn:substring(row.rifComercio,1,10)}</a>
            </display:column>    
            <display:column property="descripcionComercio" title="Nombre" class="texto_centro" headerClass="headerClass_table"/>            
        </display:table>           
    </body>
</html>
