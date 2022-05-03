<%-- 
    Document   : finSesion
    Created on : 11/08/2016
    Author     : mggy@sistemasemsys.com
--%>
<%@ include file="/WEB-INF/views/jsp/include.jsp" %>
<html>
<head>
  <meta name="decorator" content="externa"/>
</head>
<body>
<br>
<br>
<div class="shadows-ie-135 shadows-ie-315" id="box-login">
    <div class="t_shadow"></div>
    <div class="r_shadow"></div>
    <div class="b_shadow"></div>
    <div class="l_shadow"></div>
    <div class="lt_shadow"></div>
    <div class="rt_shadow"></div>
    <div class="rb_shadow"></div>
    <div class="lb_shadow"></div>
    
    <div>    
        <div>
            <div id="header-login" style="text-align: center;">
                <h1 class="error-message">Su sesión ha finalizado</h1>
            </div>
        </div>
        <div>
            <a href="login.htm"><input type="image" name="finSesion" src="${pageContext.request.contextPath}/resources/img/finSesion.png"/></a>
        </div>    
    </div>
</div>        
</body>
</html>