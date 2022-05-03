<%-- 
    Document   : consultaUsuariosPerfil
    Created on : 14-dic-2016
    Author     : mggy@sistemasemsys.com
--%>

<%@ include file="/WEB-INF/views/jsp/include.jsp" %>
<html>
<head>    
</head>
<body>
    <br>
    <div class="row">    
        <div id="header-form">
            <h1>Consulta Usuarios por Perfil</h1>
        </div>
        <label class="error-message" style="align-content: center">${messageError}</label>
        <div id="box-form">
            <form:form method="post" autocomplete="off" commandName="consultaUsuarios">
                <div class="contenedor_formulario">
                  <table class="formulario">                    
                    <tr>
                      <td class="etiqueta_black">Perfil:</td>
                        <td class="campo">
                          <form:select path="perfil" cssClass="select" multiple="false">
                            <form:option value="" label="<--- Seleccione --->"/>
                            <form:options items="${perfil}" itemLabel="nombre" itemValue="IdAsString" />                                        
                          </form:select>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                          <form:errors path="perfil" cssClass="error-message"/>
                        </td>
                    </tr>                
                    <tr>
                          <td class="botonera" colspan="2">                            
                            <input type="image" name="consultaUsuariosPerfil" src="${pageContext.request.contextPath}/resources/img/buscar.png" width='56' height='56'/>
                          </td>
                    </tr>                    
                  </table>                                 
                </div>  
            </form:form>
        </div>
    </div>
</body>
</html>