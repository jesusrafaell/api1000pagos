<%-- 
    Document   : eliminarBanco
    Created on : 12-nov-2016
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
                <h1>Confirme la información del Banco a eliminar</h1>
            </div>
            <label class="error-message" style="align-content: center">${model.messageError}</label>
            <div id="box-form">
                <form:form method="post" autocomplete="off" commandName="eliminarBanco">
                    <div class="contenedor_formulario">
                      <table class="formulario">
                        <tr>
                            <td class="etiqueta_black">Código:</td>
                            <td class="campo">                                                                  
                                <form:input path="codigo" cssClass="textbox" readonly="true"/>                                               
                            </td>        
                        </tr>
                        <tr>
                            <td class="etiqueta_black">Código Swift:</td>
                            <td class="campo">                                
                                <form:input path="codigoSwift" cssClass="textbox" readonly="true"/>
                            </td>        
                        </tr>
                        <tr>
                            <td colspan="2">
                                <form:errors path="codigoSwift" cssClass="error-message"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="etiqueta_black">Descripción:</td>
                            <td >
                                <form:input path="descripcion" cssClass="textbox" size="80" readonly="true"/> 
                            </td>
                        </tr>                                                   
                        <tr>
                            <td></td>
                            <td class="botonera">                                
                                <a href="listadoBancos.htm"><img src="${pageContext.request.contextPath}/resources/img/volver.png" width='56' height='56'/></a>
                            </td>
                            <td></td>
                            <td class="botonera" colspan="2">                                    
                                <input type="image" name="eliminaBanco" src="${pageContext.request.contextPath}/resources/img/actualizar.png" width='52' height='52'/>
                            </td>
                        </tr>
                      </table>                    
                    </div>  
                </form:form>
            </div>
        </div>       
    </body>
</html>
