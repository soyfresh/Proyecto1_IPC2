<%-- 
    Document   : gestionConfiguracion
    Created on : 16 sept 2026, 13:52:14
    Author     : dar333n
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:include page="/misJsp/includes/header.jsp"/>
        
        <h2>Cambiar Configuración del Sistema</h2>
        
        <%
        String error=(String) request.getAttribute("error");
        String mensaje=(String) request.getAttribute("mensaje");

        if (error!= null && !error.isEmpty()) {
        %>
                <p>Error: <%= error %></p>
        <%
            }

            if (mensaje != null && !mensaje.isEmpty()) {
        %>
                <p>Éxito: <%= mensaje %></p>
        <%
            }
        %>

        <section>
            
            
            <form action="${pageContext.request.contextPath}/Configuracion" method="POST">
                <label for="montoPorKm">Nuevo Monto por KM:</label>
                <input type="number" id="montoPorKm" name="montoPorKm">
                <button type="submit">Guardar Nueva Configuración</button>
            </form>
                
        </section>

        <br>
        <p><a href="${pageContext.request.contextPath}/Servicios.jsp">Volver a Servicios</a></p>
    </body>
</html>
