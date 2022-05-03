<%-- 
    Document   : listadoVisitasAliados
    Created on : 09/03/2017
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
	   Listado de Visitas Programadas
	</td>
	</tr>
	</table>
        <br>        
        <br>             
        <display:table name="${model.listadoVisitasAliados}" id="row" pagesize="10" requestURI="/listadoVisitasAliados.htm?zona=${model.zona}&aliado=${model.aliado}" export="true" class="tabla" cellpadding="0" cellspacing="0" style="margin:0 auto; width:750px;">            
            <display:setProperty name="export.excel.filename" value="ListadoVisitas.xls" />
            <display:setProperty name="export.xml.filename" value="ListadoVisitas.xml" />
            <display:setProperty name="export.pdf.filename" value="ListadoVisitas.pdf" />                         
            <display:column property="fechaInicio" title="Fecha Hora Inicio" class="texto_centro" headerClass="headerClass_table"/>
            <display:column property="fechaFin" title="Fecha Hora Fin" class="texto_centro" headerClass="headerClass_table"/>
            <display:column property="informacionComercio.descripcionComercio" title="Comercio" class="texto_centro" headerClass="headerClass_table"/>
            <display:column property="informacionAliado.contactoNombreCompleto" title="Aliado" class="texto_centro" headerClass="headerClass_table"/>
            <display:column property="titulo" title="Titulo" class="texto_centro" headerClass="headerClass_table"/>            
        </display:table>        
        <table class="tabla" cellpadding="0" cellspacing="0" style="margin:0 auto; width:750px;">            
            <tr>
              <td class="botonera" colspan="5">                  
                  <a href="consultaVisitasAliado.htm"><img src="${pageContext.request.contextPath}/resources/img/volver.png" width='56' height='56'/></a>
              </td>
            </tr>
	</table>        
    </body>
</html>
