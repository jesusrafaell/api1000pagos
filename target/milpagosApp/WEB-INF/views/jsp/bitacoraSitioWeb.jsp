<%-- 
    Document   : bitacoraSitioWeb
    Created on : 09/06/2017
    Author     : mggy@sistemasemsys.com
--%>
<%@ include file="/WEB-INF/views/jsp/include.jsp" %>
<!DOCTYPE html>
<html>
    <head>       
    </head>
    <body>
        <h3 ></h3>
        <table cellpadding="0" cellspacing="0" style="width:750px; border: none">
	<tr>
	<td colspan="2" class="titulo_tabla">
	   Bitácora Sitio Web 1000Pagos
	</td>
	</tr>
	</table>
        <br>
        <display:table name="${model}" pagesize="5" requestURI="/bitacoraSitioWeb.htm" export="true" class="tabla" cellpadding="0" cellspacing="0" style="margin:0 auto; width:750px;">
            <display:setProperty name="export.excel.filename" value="ListadoBitacora.xls" />
            <display:setProperty name="export.xml.filename" value="ListadoBitacora.xml" />
            <display:setProperty name="export.csv.filename" value="ListadoBitacora.csv" />
            <display:setProperty name="export.pdf.filename" value="ListadoBitacora.pdf" />
            <display:column property="fechaLogWeb" title="Fecha" class="texto_centro" headerClass="headerClass_table"/>
            <display:column property="usuarioWeb.loginDesencriptado" title="Login" class="texto_centro" headerClass="headerClass_table"/>
            <display:column property="usuarioWeb.usuarioWebIdentificacion" title="Usuario" class="texto_centro" headerClass="headerClass_table"/>            
            <display:column property="descripcionLogWeb" title="Descripción" class="texto_centro" headerClass="headerClass_table"/>
            <display:column property="ipLogWeb" title="IP" class="texto_centro" headerClass="headerClass_table"/>
        </display:table>  
        <table class="tabla" cellpadding="0" cellspacing="0" style="margin:0 auto; width:750px;">            
            <tr>
              <td class="botonera" colspan="5">                  
                  <a href="consultaBitacoraWeb.htm"><img src="${pageContext.request.contextPath}/resources/img/volver.png" width='56' height='56'/></a>
              </td>
            </tr>
	</table>
    </body>
</html>

