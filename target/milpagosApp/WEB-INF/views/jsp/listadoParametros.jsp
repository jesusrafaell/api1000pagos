<%-- 
    Document   : listadoParametros
    Created on : 26/08/2016
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
	   Listado de Parámetros registrados en la aplicación
	</td>
	</tr>
	</table>
        <br>
        <label class="error-message" style="align-content: center">${modelFalla.messageError}</label>
        <br>     
        <display:table name="${model.listadoParametros}" pagesize="5" requestURI="/listadoParametros.htm" export="true" class="tabla" cellpadding="0" cellspacing="0" style="margin:0 auto; width:750px;">
            <display:setProperty name="export.excel.filename" value="ListadoParametros.xls" />
            <display:setProperty name="export.xml.filename" value="ListadoParametros.xml" />
            <display:setProperty name="export.csv.filename" value="ListadoParametros.csv" />
            <display:setProperty name="export.pdf.filename" value="ListadoParametros.pdf" />
            <display:column property="codigo" title="Código" class="texto_centro" headerClass="headerClass_table"/>            
            <display:column property="nombre" title="Nombre" class="texto_centro" headerClass="headerClass_table"/>
            <display:column property="descripcion" title="Descripción" class="texto_centro" headerClass="headerClass_table"/>
            <display:column property="valor" title="Valor" class="texto_centro" headerClass="headerClass_table"/>            
            <display:column paramId="idParametro" paramProperty="id" title="Editar" href="editarParametro.htm" class="texto_centro" headerClass="headerClass_table" media="html"><img src="${pageContext.request.contextPath}/resources/img/actualizar.png" width='36' height='36'/></display:column>            
       </display:table>        
    </body>
</html>
