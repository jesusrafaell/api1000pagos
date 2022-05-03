<%-- 
    Document   : listadoAfiliados
    Created on : 12-nov-2018
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
	   Listado de Afiliados registrados en la aplicación
	</td>
	</tr>
	</table>
        <br>
        <label class="error-message" style="align-content: center">${model.messageError}</label>
        <br>     
        <display:table name="${model.listadoAfiliados}" pagesize="5" requestURI="/listadoAfiliados.htm" export="true" class="tabla" cellpadding="0" cellspacing="0" style="margin:0 auto; width:750px;">
            <display:setProperty name="export.excel.filename" value="ListadoAfiliados.xls" />
            <display:setProperty name="export.xml.filename" value="ListadoAfiliados.xml" />
            <display:setProperty name="export.csv.filename" value="ListadoAfiliados.csv" />
            <display:setProperty name="export.pdf.filename" value="ListadoAfiliados.pdf" />
            <display:column property="codigoAfiliado" title="Código" class="texto_centro" headerClass="headerClass_table"/>            
            <display:column property="descripcionAfiliado" title="Descripción" class="texto_centro" headerClass="headerClass_table"/>
            <display:column paramId="idAfiliado" paramProperty="codigoAfiliado" title="Editar" href="editarAfiliado.htm" class="texto_centro" headerClass="headerClass_table" media="html"><img src="${pageContext.request.contextPath}/resources/img/actualizar.png" width='36' height='36'/></display:column>            
       </display:table>        
    </body>
</html>
