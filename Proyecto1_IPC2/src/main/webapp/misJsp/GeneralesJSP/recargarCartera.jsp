<%-- 
    Document   : recargarCartera
    Created on : 16 sept 2026, 11:28:16
    Author     : dar333n
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="ClasesDTO.Cuentas.CuentaDTO"%>

<% 
    CuentaDTO usuario= (CuentaDTO) session.getAttribute("usuarioLogueado");
    if (usuario==null) {
        response.sendRedirect("/login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:include page="/misJsp/includes/header.jsp"/>
        
        
        <% if (request.getAttribute("exitoBK") != null) { %>
            <p>  <%= request.getAttribute("exitoBK") %>  </p>
        <% } %>
        
        <% if (request.getAttribute("errorBK") != null) { %>
            <p> <%= request.getAttribute("error") %> </p>
        <% } %>
        
        
        <h1>Billetera</h1>
        <br>
        <h4>Java Cash</h4>
        
        <h2>Q <%= usuario.getSaldo()%></h2>
        <br>
        
        <h3>Recargar cartera</h3>
        <form action="${pageContext.request.contextPath}/recargarCartera" method="POST">
            <p>
                <label for="monto">Monto a recargar (Q):</label>
                <br>
                <input type="number" id="monto" name="monto" required>
            </p>
            
            <p>
                <label for="fecha">Fecha:</label><br>
                <input type="date" id="fecha" name="fecha" required>
            </p>

            <button type="submit">Confirmar Recarga</button>
            <a href="<%= request.getContextPath() %>/misJsp/ClienteJSP/inicio.jsp">Volver al Inicio</a>
        </form>
    </body>
</html>
