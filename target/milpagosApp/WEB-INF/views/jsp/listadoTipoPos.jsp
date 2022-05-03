<%-- 
    Document   : listadoTipoPos
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
	   Listado de Tipo Pos registrados en la aplicación
	</td>
	</tr>
	</table>
        <br>
        <label class="error-message" style="align-content: center">${model.messageError}</label>
        <br>     
        <display:table name="${model.listadoTipoPos}" pagesize="5" requestURI="/listadoTipoPos.htm" export="true" class="tabla" cellpadding="0" cellspacing="0" style="margin:0 auto; width:750px;">
            <display:setProperty name="export.excel.filename" value="ListadoTipoPos.xls" />
            <display:setProperty name="export.xml.filename" value="ListadoTipoPos.xml" />
            <display:setProperty name="export.csv.filename" value="ListadoTipoPos.csv" />
            <display:setProperty name="export.pdf.filename" value="ListadoTipoPos.pdf" />
            <display:column property="id" title="Id" class="texto_centro" headerClass="headerClass_table"/>
            <display:column property="descripcion" title="Descripcion" class="texto_centro" headerClass="headerClass_table"/>
            <display:column paramId="id" paramProperty="id" title="Editar" href="editarTipoPos.htm" class="texto_centro" headerClass="headerClass_table" media="html"><img src="${pageContext.request.contextPath}/resources/img/actualizar.png" width='36' height='36'/></display:column>
            <display:column paramId="id" paramProperty="id" title="Eliminar" href="eliminarTipoPos.htm" class="texto_centro" headerClass="headerClass_table" media="html"><img src="${pageContext.request.contextPath}/resources/img/eliminar.gif" width='36' height='36'/></display:column>
       </display:table>        
    </body>
</html>
