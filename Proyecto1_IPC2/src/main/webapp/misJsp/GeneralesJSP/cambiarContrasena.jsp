<%-- 
    Document   : cambiarContrasena
    Created on : 17 sept 2026, 01:59:21
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
        <jsp:include page= "/misJsp/includes/header.jsp"/>
        <h1>Cambio de Contraseña</h1>

    <form action="${pageContext.request.contextPath}/modificarCuenta" method="POST">
        <input type="hidden" name="accion" value="cambiarContrasena">
        <input type="hidden" name="dpi" value="<%= usuario.getDpi() %>">
        <input type="hidden" name= "contraAnt" value="<%= usuario.getContrasena()%>">

        <p>
            <label for="contrasenaAntigua">Contraseña Antigua:</label><br>
            <input type="password" id="contrasenaAntigua" name="contrasenaAntigua" required>
        </p>

        <p>
            <label for="nuevaContrasena">Nueva Contraseña:</label><br>
            <input type="password" id="nuevaContrasena" name="nuevaContrasena" required>
        </p>

        <button type= "submit">Actualizar Contraseña</button>
        <a href="modificarCuenta.jsp">Cancelar</a>
    </form>
    </body>
</html>
