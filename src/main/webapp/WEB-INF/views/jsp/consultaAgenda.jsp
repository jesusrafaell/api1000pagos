<%-- 
    Document   : consultaAgenda
    Created on : 02/12/2016
    Author     : mggy@sistemasemsys.com
--%>

<%@ include file="/WEB-INF/views/jsp/include.jsp" %>
<!DOCTYPE html>
<html>
    <head>                                             
    </head>
    <body>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/fullcalendar/lib/jquery.min.js"></script>        
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/fullcalendar/lib/moment.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/fullcalendar/fullcalendar.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/fullcalendar/locale/es.js"></script>
        <script>           
            $(document).ready(function() {
                var contexPath = "<%=request.getContextPath() %>";

                $('#calendar').fullCalendar({
                    header: {
                        left: 'prev,next today myCustomButton',
                        center: 'title',
                        right: 'month,agendaWeek,agendaDay'
                    },
                    defaultView: 'month',
                    editable: true,
                    navLinks: true,
                    eventLimit: true,
                    events: {
			url: contexPath + '/loadagenda.htm'
                    },
                    dayClick: function(date, jsEvent, view) {

                        $('#calendar').fullCalendar('changeView', 'agendaDay');
                        $('#calendar').fullCalendar('gotoDate', date);
                      
                        $(this).css('background-color', 'orange');

                    }
                });

            });
            
        </script>
        <br>
        <div class="row" style="width: 70%">
            <table class="titulo" cellpadding="0" cellspacing="0" style="width:550px; border: none">
                <tr>
                    <td colspan="1" class="etiqueta">
                      <div id="header-form">
                        <h1>Consulta Agenda</h1>
                      </div>
                    </td>
                    
                    <td colspan="6" class="etiqueta" style="width: 80px"></td>
                    <td colspan="7" class="etiqueta" style="width: 80px"></td>                    
                    <td colspan="8" class="etiqueta" style="width: 100px"></td>
                    <td colspan="9" class="etiqueta" style="width: 100px"></td>
                    <td colspan="10">                                            
                        <a href="programarVisitaConsulta.htm"><img src="${pageContext.request.contextPath}/resources/img/crearEvento.png" width='46' height='46'/></a>                                          
                    </td>
                </tr>
            </table>           
            <label class="error-message" style="align-content: center">${model.messageError}</label>
            <div id="box-form">
                <form:form method="post" autocomplete="off" commandName="consultaAgenda">
                    <div class="contenedor_formulario">
                        <div id="calendar"></div>
                    </div>  
                </form:form>
            </div>
        </div>
    </body>
</html>
