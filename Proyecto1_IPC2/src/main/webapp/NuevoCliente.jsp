<%-- 
    Document   : NuevoCliente
    Created on : 14 sept 2026, 17:09:50
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
        <h1>Empieza a usar la app</h1>
        <h4>Registrate para usar los servicios de bus</h4>
        <br>
            <%
            String errorSv=(String) request.getAttribute("error");
            if(errorSv!=null){  
            %>
            <p><%= errorSv %></p>
            <%
            }
            %>

        <form action="${pageContext.request.contextPath}/NuevaCuenta" method="POST">
            <label for="dpi">DPI:</label>
            <br>
            <input type="text" id="dpi" name="dpi" required>
            <br>
            <br>
            <label for="nit">NIT:</label>
            <br>
            <input type="text" id="nit" name="nit" required>
            <br>
            <br>
            <label for="nombre">Nombre Completo:</label>
            <br>
            <input type="text" id="nombre" name="nombre" required>
            <br>
            <br>
            <label for="correoElectronico">Correo Electrónico:</label>
            <br>
            <input type="email" id="correoElectronico" name="correoElectronico" required>
            <br>
            <br>
            <label for="contrasena">Contraseña:</label>
            <br>
            <input type="password" id="contrasena" name="contrasena" required>
            <br>
            <br>
            <label for="telefono">Teléfono:</label>
            <br>
            <input type="text" id="telefono" name="telefono" required>
            <br>
            <br>
            <label for="direccion">Dirección:</label>
            <br>
            <input type="text" id="direccion" name="direccion" required>
            <br>
            <br>
            
            <button type="submit">Registrarse</button>
            <br>
            <a href="index.jsp">Ya tengo cuenta</a>
        </form>
        
    </body>
</html>
