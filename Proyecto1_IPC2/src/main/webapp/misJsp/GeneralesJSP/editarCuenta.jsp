<%-- 
    Document   : editarCuenta
    Created on : 17 sept 2026, 01:59:09
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
        <h1>Editar Información Personal</h1>

    <form action="${pageContext.request.contextPath}/modificarCuenta" method="POST">
        <input type="hidden" name="accion" value="actualizarDatos">
        <input type="hidden" name="contrasena" value="<%= usuario.getContrasena()%>">
        <input type="hidden" name="tipoUsuario" value="<%= usuario.getTipoUsuario()%>">

        <p>
            <label for="dpi">DPI (No editable):</label><br>
            <input type="text" id="dpi" name="dpi" value="<%= usuario.getDpi() %>" required>
        </p>

        <p>
            <label for="nombre">Nombre Completo:</label><br>
            <input type="text" id="nombre" name="nombre" value="<%= usuario.getNombre() %>" required>
        </p>

        <p>
            <label for="correoElectronico">Correo Electrónico:</label><br>
            <input type="email" id="correoElectronico" name="correoElectronico" value="<%= usuario.getCorreoElectronico() %>" required>
        </p>

        <p>
            <label for="telefono">Teléfono:</label><br>
            <input type="text" id="telefono" name="telefono" value="<%= usuario.getTelefono() %>" maxlength="8" required>
        </p>

        <p>
            <label for="nit">NIT:</label><br>
            <input type="text" id="nit" name="nit" value="<%= usuario.getNit() %>" required>
        </p>

        <p>
            <label for="direccion">Dirección:</label><br>
            <input type="text" id="direccion" name="direccion" value="<%= usuario.getDireccion() %>" required>
        </p>

        <button type="submit">Guardar Cambios</button>
        <a href="modificarCuenta.jsp">Cancelar</a>
    </form>
    </body>
</html>
