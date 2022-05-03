<%-- 
    Document   : editarUsuario
    Created on : 03/10/2016
    Author     : mggy@sistemasemsys.com
--%>

<%@ include file="/WEB-INF/views/jsp/include.jsp" %>
<!DOCTYPE html>
<html>
<head>        
</head>
    <body>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/utils.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.1.0.js"></script>        
        <script src="${pageContext.request.contextPath}/resources/js/rainbow-custom.min.js"></script>
        <script type="text/javascript">
            jQuery(function(){
                
                 jQuery('#reset').click(function() {                     
                     if($("#reset").prop('checked')==true){                        
                        $("#contrasena").show();
                        $("#envioCorreo").show();                        
                     }else{
                         $("#contrasena").hide();
                         $("#envioCorreo").hide();
                     }
                 }); 
                                                                                       
            });    
        </script>
        <br>
        <div class="row">
            <div id="header-form">
                <h1>Formulario para modificar un Usuario</h1>
            </div>
            <label class="error-message" style="align-content: center">${model.messageError}</label>
            <div id="box-form">
                <form:form method="post" autocomplete="off" commandName="editarUsuario">
                    <div class="contenedor_formulario">
                      <table class="formulario">
                        <tr>
                            <td class="etiqueta_black">Login:</td>
                            <td class="campo">                                
                                <input id="id" name="id" type="hidden" value="${editarUsuario.id}"/>
                                <form:input path="login" cssClass="textbox" maxlength="12" readonly="true"/>
                            </td>        
                        </tr>
                        <tr>
                            <td></td>
                            <td>
                                <form:errors path="login" cssClass="error-message"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="etiqueta_black">Forzar Cambio de Contraseña:</td>
                            <td colspan="2" class="checkboxes">                                    
                                <input type="checkbox" id="cambio" name="cambio" value="1">
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td class="etiqueta_black">Reset Contraseña:</td>
                            <td colspan="2" class="checkboxes">                                    
                                <input type="checkbox" id="reset" name="reset" value="1">
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td class="etiqueta_black">Nueva Contraseña:</td>
                            <td class="campo">
                               <form:input path="contrasena" cssClass="textbox" maxlength="12" value="${editarUsuario.contrasena}" readonly="true" style="display:none;"/>
                            </td>        
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td class="etiqueta_black">Enviar Contraseña por Correo:</td>
                            <td colspan="2" class="checkboxes">                                    
                                <input type="checkbox" id="envioCorreo" name="envioCorreo" value="1" style="display:none;">
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                        </tr>                                             
                        <tr>
                            <td class="etiqueta_black">Identificación:</td>
                            <td>
                              <form:select path="tipoIdentificacion" cssClass="select" multiple="false">                                      
                                <form:options items="${tipoIdentificacionList}" />
                              </form:select>
                              <form:input path="identificacion" cssClass="textbox" cssStyle="width: 80px" maxlength="9"/>                                 
                            </td>                                
                        </tr>
                        <tr>
                            <td></td>
                            <td>
                                <form:errors path="tipoIdentificacion" cssClass="error-message"/>
                                <form:errors path="identificacion" cssClass="error-message"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="etiqueta_black">Nombre:</td>
                            <td class="campo">                                    
                                <form:input path="nombre" cssClass="textbox" maxlength="100"/>                                               
                            </td>        
                        </tr>
                        <tr>
                            <td></td>
                            <td>
                                <form:errors path="nombre" cssClass="error-message"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="etiqueta_black">Correo Electrónico:</td>
                            <td class="campo">                                    
                                <form:input path="email" cssClass="textbox" maxlength="100" cssStyle="width: 200px"/>                                               
                            </td>        
                        </tr>
                        <tr>
                            <td></td>
                            <td>
                                <form:errors path="email" cssClass="error-message"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="etiqueta_black">Perfil:</td>
                            <td class="campo">
                                <form:select path="perfilId" cssClass="select" multiple="false">
                                    <form:option value="" label="<--- Seleccione --->"/>
                                    <form:options items="${perfil}" itemLabel="nombre" itemValue="IdAsString" />
                                </form:select>
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td>
                                <form:errors path="perfilId" cssClass="error-message"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="etiqueta_black">Estatus:</td>
                            <td class="campo">
                                <form:select path="estatus" cssClass="select" multiple="false">                                                                        
                                  <form:options items="${estatusList}" itemLabel="descripcion" itemValue="id"/>
                                </form:select>                                
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td>
                                <form:errors path="estatus" cssClass="error-message"/>
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td class="botonera">                                
                                <a href="listadoUsuarios.htm"><img src="${pageContext.request.contextPath}/resources/img/volver.png" width='56' height='56'/></a>
                            </td>
                            <td></td>
                            <td class="botonera" colspan="2">                                    
                                <input type="image" name="actualizaPerfil" src="${pageContext.request.contextPath}/resources/img/actualizar.png" width='52' height='52'/>
                            </td>
                        </tr>
                      </table>                    
                    </div>  
                </form:form>
            </div>
        </div>        
    </body>
</html>