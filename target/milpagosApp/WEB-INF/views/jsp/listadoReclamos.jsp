<%-- 
    Document   : listadoReclamos
    Created on : 09/02/2017
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
	   Listado de Reclamos registrados
	</td>
	</tr>
	</table>
        <br>
        <label class="error-message" style="align-content: center">${model.messageError}</label>
        <br>             
        <display:table name="${model.listadoReclamos}" id="row" pagesize="10" requestURI="/listadoReclamos.htm" export="true" class="tabla" cellpadding="0" cellspacing="0" style="margin:0 auto; width:750px;">            
            <display:setProperty name="export.excel.filename" value="ListadoReclamos.xls" />
            <display:setProperty name="export.xml.filename" value="ListadoReclamos.xml" />
            <display:setProperty name="export.pdf.filename" value="ListadoReclamos.pdf" />            
            <display:column title="Fecha Recepción" class="texto_centro" headerClass="headerClass_table">
                <fmt:parseDate value="${row.fechaRecepcion}" pattern="yyyy-MM-dd HH:mm" var="dateValue"/>                
                <fmt:formatDate value="${dateValue}" pattern="dd-MM-yyyy HH:mm"/>               
            </display:column>
            <display:column property="codTipoReclamo.descripcionReclamo" title="Tipo de Reclamo" class="texto_centro" headerClass="headerClass_table"/>
            <display:column property="codEstatus.descripcion" title="Estatus" class="texto_centro" headerClass="headerClass_table"/>            
            <display:column paramId="idReclamo" paramProperty="id" title="Editar" href="editarReclamo.htm" class="texto_centro" headerClass="headerClass_table" media="html"><img src="${pageContext.request.contextPath}/resources/img/actualizar.png" width='36' height='36'/></display:column>            
       </display:table>
       <table class="tabla" cellpadding="0" cellspacing="0" style="margin:0 auto; width:750px;">            
            <tr>
              <td class="botonera" colspan="5">                  
                  <a href="consultaReclamos.htm?messageError="><img src="${pageContext.request.contextPath}/resources/img/volver.png" width='56' height='56'/></a>
              </td>
            </tr>
	</table>     
    </body>
</html>
