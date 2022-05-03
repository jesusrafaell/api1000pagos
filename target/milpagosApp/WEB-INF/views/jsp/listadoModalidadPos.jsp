<%-- 
    Document   : listadoModalidadPos
    Created on : 08-mar-2017
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
	   Listado de Modalidad de POS registrados en la aplicación
	</td>
	</tr>
	</table>
        <br>
        <label class="error-message" style="align-content: center">${model.messageError}</label>
        <br>     
        <display:table name="${model.listadoModalidadPos}" pagesize="5" requestURI="/listadoModalidadPos.htm" export="true" class="tabla" cellpadding="0" cellspacing="0" style="margin:0 auto; width:750px;">
            <display:setProperty name="export.excel.filename" value="ListadoModalidadPos.xls" />
            <display:setProperty name="export.xml.filename" value="ListadoModalidadPos.xml" />
            <display:setProperty name="export.csv.filename" value="ListadoModalidadPos.csv" />
            <display:setProperty name="export.pdf.filename" value="ListadoModalidadPos.pdf" />
            <display:column property="id" title="Código" class="texto_centro" headerClass="headerClass_table"/>            
            <display:column property="descripcion" title="Descripción" class="texto_centro" headerClass="headerClass_table"/>
            <display:column paramId="idModalidadPos" paramProperty="id" title="Editar" href="editarModalidadPos.htm" class="texto_centro" headerClass="headerClass_table" media="html"><img src="${pageContext.request.contextPath}/resources/img/actualizar.png" width='36' height='36'/></display:column>
            <display:column paramId="idModalidadPos" paramProperty="id" title="Eliminar" href="eliminarModalidadPos.htm" class="texto_centro" headerClass="headerClass_table" media="html"><img src="${pageContext.request.contextPath}/resources/img/eliminar.gif" width='36' height='36'/></display:column>
       </display:table>        
    </body>
</html>
