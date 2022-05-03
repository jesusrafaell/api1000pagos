<%-- 
    Document   : listadoAliados
    Created on : 07/01/2017
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
	   Listado de Aliados registrados en la aplicación
	</td>
	</tr>
	</table>
        <br>        
        <br>             
        <display:table name="${model.listadoAliados}" id="row" pagesize="10" requestURI="/listadoAliados.htm" export="true" class="tabla" cellpadding="0" cellspacing="0" style="margin:0 auto; width:750px;">            
            <display:setProperty name="export.excel.filename" value="ListadoAliados.xls" />
            <display:setProperty name="export.xml.filename" value="ListadoAliados.xml" />
            <display:setProperty name="export.pdf.filename" value="ListadoAliados.pdf" />            
            <display:column title="Identificación" class="texto_centro" headerClass="headerClass_table">
                <a href="resultadoConsultaAliado.htm?messageError=&tipo=${row.tipoIdentificacion}&identifica=${row.identificacion}">${row.tipoIdentificacion}-${row.identificacion}</a>
            </display:column>    
            <display:column property="contactoNombreCompleto" title="Nombre y Apellido" class="texto_centro" headerClass="headerClass_table"/>
            <display:column property="contactoTelefono" title="Teléfono Contacto" class="texto_centro" headerClass="headerClass_table"/>
            <display:column property="email" title="Email" class="texto_centro" headerClass="headerClass_table"/>            
        </display:table>        
        <table class="tabla" cellpadding="0" cellspacing="0" style="margin:0 auto; width:750px;">            
            <tr>
              <td class="botonera" colspan="5">                  
                  <a href="consultaAliado.htm"><img src="${pageContext.request.contextPath}/resources/img/volver.png" width='56' height='56'/></a>
              </td>
            </tr>
	</table>        
    </body>
</html>
