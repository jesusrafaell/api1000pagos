<%-- 
    Document   : listadoUsuarios
    Created on : 14/09/2016
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
	   Listado de Usuarios registrados en la aplicación
	</td>
	</tr>
	</table>
        <br>
        <label class="error-message" style="align-content: center">${model.messageError}</label>
        <br>             
        <display:table name="${model.listadoUsuarios}" pagesize="10" requestURI="/listadoUsuarios.htm" export="true" class="tabla" cellpadding="0" cellspacing="0" style="margin:0 auto; width:750px;">
            <display:setProperty name="export.excel.filename" value="ListadoUsuarios.xls" />
            <display:setProperty name="export.xml.filename" value="ListadoUsuarios.xml" />
            <display:setProperty name="export.csv.filename" value="ListadoUsuarios.csv" />
            <display:setProperty name="export.pdf.filename" value="ListadoUsuarios.pdf" />
            <display:column property="login" title="Login" class="texto_centro" headerClass="headerClass_table"/>
            <display:column property="nombre" title="Nombre" class="texto_centro" headerClass="headerClass_table"/>
            <display:column property="perfil.nombre" title="Perfil" class="texto_centro" headerClass="headerClass_table"/>            
            <display:column property="estatusId.descripcion" title="Estatus" class="texto_centro" headerClass="headerClass_table"/>
            <display:column property="fechaCreacion" title="Fecha Creación" class="texto_centro" headerClass="headerClass_table"/>
            <display:column paramId="idUsuario" paramProperty="id" title="Editar" href="editarUsuario.htm" class="texto_centro" headerClass="headerClass_table" media="html"><img src="${pageContext.request.contextPath}/resources/img/actualizar.png" width='36' height='36'/></display:column>            
            <display:column paramId="idUsuario" paramProperty="id" title="Eliminar" href="eliminarUsuario.htm" class="texto_centro" headerClass="headerClass_table" media="html"><img src="${pageContext.request.contextPath}/resources/img/eliminar.gif"/></display:column>
       </display:table>        
    </body>
</html>
