<%-- 
    Document   : resultadoEstadistica
    Created on : 14-dic-2016
    Author     : mggy@sistemasemsys.com
--%>
<%@ page session="true" %>
<%@ include file="/WEB-INF/views/jsp/include.jsp" %>
<html>
<head>    
</head>
    <body>
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
    <script type="text/javascript">        
        // Load the Google Visualization Library and Google chart libraries including the corechart package
        google.load('visualization', '1', {'packages':['columnchart']});
        google.setOnLoadCallback(drawChart);
        // Custom drawChart function to create the charts
        function drawChart() {
            var contexPath = "<%=request.getContextPath() %>";
            try {
                 var jsonData = $.ajax({
                    url: contexPath + '/loadestadistica.htm',
                    dataType:"json",
                    async: false
                    }).responseText; 
                 jsonData = JSON.stringify(eval("(" + jsonData + ")"));
                var data = new google.visualization.DataTable(jsonData,false);
                alert(jsonData);
                // 'false' means that the first row contains labels, not data.
                var chart = new google.visualization.ColumnChart(document
                .getElementById( "grafica" ));
                var options = {width: 400, height: 240, is3D: true, title: 'Company Earnings'};
                chart.draw(data, options);
            } catch (err) {
                alert( err.message );
            }
        }
</script>
    </script>    
    <br>
    <div class="row">    
        <div id="header-form">
            <h1>Consulta Estadísticas</h1>
        </div>
        <label class="error-message" style="align-content: center">${messageError}</label>
        <div id="box-form">
            <form:form method="post" autocomplete="off" commandName="resultadoEstadisticas">
                <div class="contenedor_formulario">
                    <div id="grafica"> </div>
                </div>  
            </form:form>
        </div>
    </div>
</body>
</html>
