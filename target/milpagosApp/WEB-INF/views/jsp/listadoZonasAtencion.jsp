<%-- 
    Document   : listadoZonasAtencion
    Created on : 12-nov-2016
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
	   Listado de Zonas de Atención registradas en la aplicación
	</td>
	</tr>
	</table>
        <br>
        <label class="error-message" style="align-content: center">${model.messageError}</label>
        <br>     
        <display:table name="${model.listadoZonasAtencion}" pagesize="5" requestURI="/listadoZonasAtencion.htm" export="true" class="tabla" cellpadding="0" cellspacing="0" style="margin:0 auto; width:750px;">
            <display:setProperty name="export.excel.filename" value="ListadoZonasAtencion.xls" />
            <display:setProperty name="export.xml.filename" value="ListadoZonasAtencion.xml" />
            <display:setProperty name="export.csv.filename" value="ListadoZonasAtencion.csv" />
            <display:setProperty name="export.pdf.filename" value="ListadoZonasAtencion.pdf" />
            <display:column property="id" title="Código" class="texto_centro" headerClass="headerClass_table"/>            
            <display:column property="descripcion" title="Descripción" class="texto_centro" headerClass="headerClass_table"/>            
            <display:column paramId="idZona" paramProperty="id" title="Editar" href="editarZonaAtencion.htm" class="texto_centro" headerClass="headerClass_table" media="html"><img src="${pageContext.request.contextPath}/resources/img/actualizar.png" width='36' height='36'/></display:column>
            <display:column paramId="idZona" paramProperty="id" title="Eliminar" href="eliminarZonaAtencion.htm" class="texto_centro" headerClass="headerClass_table" media="html"><img src="${pageContext.request.contextPath}/resources/img/eliminar.gif" width='36' height='36'/></display:column>
       </display:table>        
    </body>
</html>
