<%-- 
    Document   : listadoComercios
    Created on : 05/01/2017
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
	   Listado de Comercios
	</td>
	</tr>
	</table>
        <br>        
        <br>             
        <display:table name="${model.listadoComercios}" id="row" pagesize="10" requestURI="/listadoComercios.htm" export="true" class="tabla" cellpadding="0" cellspacing="0" style="margin:0 auto; width:750px;">            
            <display:setProperty name="export.excel.filename" value="ListadoComercios.xls" />
            <display:setProperty name="export.xml.filename" value="ListadoComercios.xml" />
            <display:setProperty name="export.pdf.filename" value="ListadoComercios.pdf" />            
            <display:column title="Identificación" class="texto_centro" headerClass="headerClass_table">
                <a href="resultadoConsultaComercio.htm?messageError=&tipo=${fn:substring(row.rifComercio,0,1)}&identifica=${fn:substring(row.rifComercio,1,10)}">${fn:substring(row.rifComercio,0,1)}-${fn:substring(row.rifComercio,1,10)}</a>
            </display:column>    
            <display:column property="descripcionComercio" title="Nombre" class="texto_centro" headerClass="headerClass_table"/>            
        </display:table>
        <table class="tabla" cellpadding="0" cellspacing="0" style="margin:0 auto; width:750px;">            
            <tr>
              <td class="botonera" colspan="5">                  
                  <a href="consultaComercio.htm"><img src="${pageContext.request.contextPath}/resources/img/volver.png" width='56' height='56'/></a>
              </td>
            </tr>
	</table>       
    </body>
</html>
