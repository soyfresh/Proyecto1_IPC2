<%-- 
    Document   : modificarCuenta
    Created on : 16 sept 2026, 11:26:34
    Author     : dar333n
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="ClasesDTO.Cuentas.CuentaDTO" %>

    <%
        CuentaDTO usuario= (CuentaDTO) session.getAttribute("usuario");
        if (usuario==null) {
            response.sendRedirect("/login.jsp");
            return;
        }
        String error= (String) request.getAttribute("errorBK");
        String exito= (String) request.getAttribute("exitoBK");
    %>
    
    <!DOCTYPE html>
    <html>
    <head>
        <meta charset="UTF-8">
        <title>Mi Perfil</title>
    </head>
    <body>
        <jsp:include page="/misJsp/includes/header.jsp"/>

        <h1>Perfil de Usuario</h1>

        <% if (error != null) { %>
            <p>Error: <%= error %></p>
        <% } %>
        <% if (exito != null) { %>
            <p>Éxito: <%= exito %></p>
        <% } %>

        <section>
            <h2>Información Personal</h2>
            <p>DPI: <%= usuario.getDpi() %></p>
            <p>Nombre Completo: <%= usuario.getNombre() %></p>
            <p>Correo Electrónico: <%= usuario.getCorreoElectronico() %></p>
            <p>Teléfono: <%= usuario.getTelefono() %></p>
            <p>NIT: <%= usuario.getNit() %></p>
            <p>Dirección: <%= usuario.getDireccion() %></p>
        </section>

        <hr>
        
        <%-- Mis botones para ir a las otras opcoines--%>
        <a href="<%= request.getContextPath() %>/misJsp/GeneralesJSP/editarCuenta.jsp">
            <button type="button">Editar Datos</button>
        </a>
            
        <a href="<%= request.getContextPath() %>/misJsp/GeneralesJSP/cambiarContrasena.jsp">
            <button type="button">Cambiar Contraseña</button>
        </a>
    </body>
