<%-- 
    Document   : listadoBancos
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
	   Listado de Bancos registrados en la aplicación
	</td>
	</tr>
	</table>
        <br>
        <label class="error-message" style="align-content: center">${model.messageError}</label>
        <br>     
        <display:table name="${model.listadoBancos}" pagesize="5" requestURI="/listadoBancos.htm" export="true" class="tabla" cellpadding="0" cellspacing="0" style="margin:0 auto; width:750px;">
            <display:setProperty name="export.excel.filename" value="ListadoBancos.xls" />
            <display:setProperty name="export.xml.filename" value="ListadoBancos.xml" />
            <display:setProperty name="export.csv.filename" value="ListadoBancos.csv" />
            <display:setProperty name="export.pdf.filename" value="ListadoBancos.pdf" />
            <display:column property="codigo" title="Código" class="texto_centro" headerClass="headerClass_table"/>
            <display:column property="codigoSwift" title="Código Swift" class="texto_centro" headerClass="headerClass_table"/>
            <display:column property="descripcion" title="Descripción" class="texto_centro" headerClass="headerClass_table"/>
            <display:column paramId="idBanco" paramProperty="codigo" title="Editar" href="editarBanco.htm" class="texto_centro" headerClass="headerClass_table" media="html"><img src="${pageContext.request.contextPath}/resources/img/actualizar.png" width='36' height='36'/></display:column>
            <display:column paramId="idBanco" paramProperty="codigo" title="Eliminar" href="eliminarBanco.htm" class="texto_centro" headerClass="headerClass_table" media="html"><img src="${pageContext.request.contextPath}/resources/img/eliminar.gif" width='36' height='36'/></display:column>
       </display:table>        
    </body>
</html>
