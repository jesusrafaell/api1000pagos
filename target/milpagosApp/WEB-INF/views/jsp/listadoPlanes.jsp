<%-- 
    Document   : listadoPlanes
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
	   Listado de Planes registrados en la aplicación
	</td>
	<td colspan="2" class="titulo_tabla">
	</td>
	</tr>
	</table>
        <br>
        <label class="error-message" style="align-content: center">${model.messageError}</label>
        <br>     
        <display:table name="${model.listadoPlanes}" pagesize="5" requestURI="/listadoPlanes.htm" export="true" class="tabla" cellpadding="0" cellspacing="0" style="margin:0 auto; width:750px;">
            <display:setProperty name="export.excel.filename" value="ListadoPlanes.xls" />
            <display:setProperty name="export.xml.filename" value="ListadoPlanes.xml" />
            <display:setProperty name="export.csv.filename" value="ListadoPlanes.csv" />
            <display:setProperty name="export.pdf.filename" value="ListadoPlanes.pdf" />
            <display:setProperty name="basic.msg.empty_list" 
                    value="<span style=\"font-size:12px\">No Hay Planes Creados</span>" />
            <display:column property="id" title="Id" class="texto_centro" headerClass="headerClass_table"/>
            <display:column property="nombre" title="Nombre Plan" class="texto_centro" headerClass="headerClass_table"/>
            <display:column property="descTipoPlan" title="Tipo Plan" class="texto_centro" headerClass="headerClass_table"/>
            <c:if test="${model.permisoActualizacion==1}">
                <display:column paramId="id" paramProperty="id" title="Editar" href="editarPlan.htm" class="texto_centro" headerClass="headerClass_table" media="html"><img src="${pageContext.request.contextPath}/resources/img/actualizar.png" width='36' height='36'/></display:column>
                <display:column paramId="id" paramProperty="id" title="Eliminar" href="eliminarPlan.htm" class="texto_centro" headerClass="headerClass_table" media="html"><img src="${pageContext.request.contextPath}/resources/img/eliminar.gif" width='36' height='36'/></display:column>
            </c:if>
       </display:table>        
    </body>
</html>
