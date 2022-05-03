<%-- 
    Document   : listadoComerciosExcluidos
    Created on : 16/03/2017
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
	   Listado de Comercios excluidos del archivo de pagos
	</td>
	</tr>
	</table>
        <br>        
        <br>             
        <display:table name="${model.listadoComerciosExcluidos}" id="row" pagesize="10" requestURI="/listadoComerciosExcluidos.htm" export="true" class="tabla" cellpadding="0" cellspacing="0" style="margin:0 auto; width:750px;">            
            <display:setProperty name="export.excel.filename" value="ListadoComercios.xls" />
            <display:setProperty name="export.xml.filename" value="ListadoComercios.xml" />
            <display:setProperty name="export.pdf.filename" value="ListadoComercios.pdf" />            
            <display:column title="Identificación" class="texto_centro" headerClass="headerClass_table">
                <a href="resultadoConsultaComercio.htm?messageError=&tipo=${fn:substring(row.rifComercio,0,1)}&identifica=${fn:substring(row.rifComercio,1,10)}">${fn:substring(row.rifComercio,0,1)}-${fn:substring(row.rifComercio,1,10)}</a>
            </display:column>    
            <display:column property="descripcionComercio" title="Nombre" class="texto_centro" headerClass="headerClass_table"/>            
        </display:table>           
    </body>
</html>
