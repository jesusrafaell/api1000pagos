<%-- 
    Document   : listadoMensajes
    Created on : 16-jun-2017
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
	   Listado de Mensajes registrados en la aplicación
	</td>
	</tr>
	</table>
        <br>
        <label class="error-message" style="align-content: center">${model.messageError}</label>
        <br>     
        <display:table name="${model.listadoMensajes}" pagesize="5" requestURI="/listadoMensajes.htm" export="true" class="tabla" cellpadding="0" cellspacing="0" style="margin:0 auto; width:750px;">
            <display:setProperty name="export.excel.filename" value="ListadoMensajes.xls" />
            <display:setProperty name="export.xml.filename" value="ListadoMensajes.xml" />
            <display:setProperty name="export.csv.filename" value="ListadoMensajes.csv" />
            <display:setProperty name="export.pdf.filename" value="ListadoMensajes.pdf" />            
            <display:column property="mensajeClave" title="Código del Mensaje" class="texto_centro" headerClass="headerClass_table"/>
            <display:column property="mensajeTexto" title="Texto del Mensaje" class="texto_centro" headerClass="headerClass_table"/>
            <display:column paramId="idMensaje" paramProperty="codigoMensaje" title="Editar" href="editarMensaje.htm" class="texto_centro" headerClass="headerClass_table" media="html"><img src="${pageContext.request.contextPath}/resources/img/actualizar.png" width='36' height='36'/></display:column>            
       </display:table>        
    </body>
</html>
