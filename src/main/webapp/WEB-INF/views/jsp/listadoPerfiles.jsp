<%-- 
    Document   : listadoPerfiles
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
	   Listado de perfiles registrados en la aplicación
	</td>
	</tr>
	</table>
        <br>
        <label class="error-message" style="align-content: center">${model.messageError}</label>
        <br>             
        <display:table name="${model.listadoPerfiles}" id="row" pagesize="10" requestURI="/listadoPerfiles.htm" export="true" class="tabla" cellpadding="0" cellspacing="0" style="margin:0 auto; width:750px;">            
            <display:setProperty name="export.excel.filename" value="ListadoPerfiles.xls" />
            <display:setProperty name="export.xml.filename" value="ListadoPerfiles.xml" />
            <display:setProperty name="export.pdf.filename" value="ListadoPerfiles.pdf" />            
            <display:column property="nombre" title="Nombre" class="texto_centro" headerClass="headerClass_table"/>            
            <display:column property="estatus" title="Estatus" class="texto_centro" headerClass="headerClass_table"/>            
            <display:column paramId="idPerfil" paramProperty="id" title="Editar" href="editarPerfil.htm" class="texto_centro" headerClass="headerClass_table" media="html"><img src="${pageContext.request.contextPath}/resources/img/actualizar.png" width='36' height='36'/></display:column>
            <display:column paramId="idPerfil" paramProperty="id" title="Eliminar" class="texto_centro" headerClass="headerClass_table" media="html">
                <c:if test="${!model.perfilesActivos.contains(row.id)}">                    
                    <a href="eliminarPerfil.htm?idPerfil=${row.id}">
                        <img src="${pageContext.request.contextPath}/resources/img/eliminar.gif"/>
                    </a>
                </c:if>
                <c:if test="${model.perfilesActivos.contains(row.id)}">                    
                    <img src="${pageContext.request.contextPath}/resources/img/eliminarInactivo.gif"/>
                </c:if>    
            </display:column>
       </display:table>
       <br>
       <br>
       <br>
       <table cellpadding="0" cellspacing="0" style="width:750px; border: none">
            <tr>
                <td colspan="2">
                   <label><img src="${pageContext.request.contextPath}/resources/img/eliminarInactivo.gif"/> Posee usuarios asociados.</label>
                </td>
            </tr>
	</table>            
    </body>
</html>
