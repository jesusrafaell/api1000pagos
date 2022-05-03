<%-- 
    Document   : basic-theme
    Created on : 12/08/2016
    Author     : mggy@sistemasemsys.com
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"

   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jsp/include.jsp" %>
<html>

<head>
    <title><decorator:title default="1000Pagos" /></title>
    <meta http-equiv="content-type" content="text/html; charsetUTF-8" />
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/img/logo.ico" />
    <style type="text/css">
        <%@include file="/WEB-INF/css/style.css" %>
    </style>
</head>

<body>
<div class="super-container">   
    <div id="header">
        <div id="top">
            <%-- <div id="logo">&nbsp;&nbsp;&nbsp;&nbsp;<img src="${pageContext.request.contextPath}/resources/img/logo.png" height="100"/></div> --%>
            <div id="slogan"><img src="${pageContext.request.contextPath}/resources/img/slogan.png" height="40"/></div>
        </div>
    </div><!-- DIV DIVTOP -->

    <div id="divContainer" class="container">               
    
        <hr />

        <div id="divMain">           
				
            <div id="divContent">

                <decorator:body />

            </div><!-- DIV DIVCONTENT -->

        </div><!-- DIV DIVMAIN -->               

    </div><!-- DIV DIVCONAINER -->    
    <br>
    <div id="footer">
        <div id="copyright"><span>&nbsp;&nbsp;&nbsp;&nbsp;Todos los derechos reservados. Inversiones Gross C.A. RIF J-00337298-6</span></div>
        <div id="link"><span>Creado por EMSYS&nbsp;&nbsp;&nbsp;&nbsp;</span></div>
    </div><!-- DIV DIVFOOTER -->
</div>    
</body>
</html>