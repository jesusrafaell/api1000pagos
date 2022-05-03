<%-- 
    Document   : eliminarPerfil
    Created on : 13-sep-2016
    Author     : mggy@sistemasemsys.com
--%>
<%@ include file="/WEB-INF/views/jsp/include.jsp" %>
<!DOCTYPE html>
<html>
<head>        
</head>
    <body>
        <br>
        <div class="row">
            <div id="header-form">
                <h1>Confirme la información del Perfil a eliminar</h1>
            </div>
            <label class="error-message" style="align-content: center">${model.messageError}</label>
            <div id="box-form">
                <form:form method="post" autocomplete="off" commandName="eliminarPerfil">
                    <div class="contenedor_formulario">
                      <table class="formulario">
                        <tr>
                            <td class="etiqueta_black">Nombre:</td>
                            <td class="campo">
                                <form:hidden path="id" />
                                <form:hidden path="opciones" />                                    
                                <form:input path="nombre" cssClass="textbox" readonly="true"/>                                               
                            </td>        
                        </tr>                                                     
                        <tr>
                            <td class="etiqueta_black">Estatus:</td>
                            <td >
                               <form:input path="estatus" cssClass="textbox" readonly="true"/> 
                            </td>
                        </tr>                        
                        <tr>
                            <td></td>
                            <td class="botonera">                                
                                <a href="listadoPerfiles.htm"><img src="${pageContext.request.contextPath}/resources/img/volver.png" width='56' height='56'/></a>
                            </td>
                            <td></td>
                            <td class="botonera" colspan="2">                                
                                <input type="image" name="eliminaPerfil" src="${pageContext.request.contextPath}/resources/img/actualizar.png" width='52' height='52'/>
                            </td>
                        </tr>
                      </table>                    
                    </div>  
                </form:form>
            </div>
        </div>       
    </body>
</html>
